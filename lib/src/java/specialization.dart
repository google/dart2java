import 'package:kernel/ast.dart' as dart;

import 'ast.dart' as java;
import 'constants.dart';
import 'type_factory.dart';
import 'type_system.dart' as ts;
import 'types.dart';
import '../compiler/runner.dart' show CompileErrorException;

/// Builds a list of all possible specializations according to the
/// configuration (static field) in [TypeSpecialization].
Iterable<TypeSpecialization> buildAllSpecializations(int numTypes) {
  if (numTypes > TypeSpecialization.specializationThreshold) {
    // Only generate the fully generic case
    return [new TypeSpecialization.fullyGeneric(numTypes)];
  }

  var genericSpec = new TypeSpecialization.fullyGeneric(numTypes);
  return genericSpec.subSpecializations()..add(genericSpec);
}

/// A type specialization for generic [ClassOrInterfaceType]s for avoiding
/// boxing int, boolean, double.
///
/// [ClassOrInterfaceType]s have a type arguments listing and additionally
/// a specialization field. When emitting a class/interface name, both parts
/// are used: Specialized types become part of the class/interface name and
/// no longer appear in the Java generic type arguments listing (<...>).
class TypeSpecialization {
  /// Types for which specializations are generated.
  static final List<PrimitiveType> specializedTypes = [
    JavaType.int_,
    JavaType.boolean,
    JavaType.double_
  ];

  /// Maximum number of generic type parameters for which specializations
  /// are generated.
  static final specializationThreshold = 2;

  /// A list of primitive Java types for this specialization. [null] indicates
  /// an unspecialized "generic" (Object) type parameter.
  ///
  /// For example:
  /// Map<int, bool>        [int_, boolean]
  /// Map<Object, bool>     [null, boolean]
  /// Map<String, double>   [null, double_]
  /// Map<Object, String>   [null, null]
  /// Map<bool, T>          [bool, *] (depends on what T is)
  List<PrimitiveType> types;

  /// A list of Java types that are considered only when retrieving a type but
  /// not for generating class/interface/method suffices.
  ///
  /// This is required for generating type factories for delegator methods.
  /// Method names should be generated without [delegatorOverrides], but
  /// retrieving a specialized type should return the boxed version.
  ///
  /// For example in Map_interface__bool_int (generated Java code):
  /// void operator_atPut__bool_generic(bool key, Integer value);
  /// The method name contains "generic", but the type of value is boxed int.
  List<JavaType> delegatorOverrides;

  /// Constructs a type specialization from a list of type argument types.
  ///
  /// If a type is not one of the specialized types, it will be replaced with
  /// [null] (generic).
  TypeSpecialization.fromTypes(Iterable<JavaType> allTypes) {
    this.types = allTypes
        .map((t) =>
            t is PrimitiveType && specializedTypes.contains(t) ? t : null)
        .toList();
    this.delegatorOverrides = new List.filled(allTypes.length, null);
  }

  /// Generates a type specialization for a delegator methods, where the class/
  /// interface/method name is generated according to [types], but types
  /// from both [types] and [delegatorOverrides] are used to look up types.
  ///
  /// Also see comments for [delegatorOverrides] and
  /// [implementedSpecializations].
  TypeSpecialization._forDelegator(Iterable<JavaType> delegatorInterface,
      Iterable<JavaType> delegatorOverrides) {
    this.types = delegatorInterface.toList();
    this.delegatorOverrides = delegatorOverrides.toList();
  }

  /// Constructs a fully generic specialization, i.e., all types are generic.
  TypeSpecialization.fullyGeneric(int numTypes) {
    this.types = new List.filled(numTypes, null);
    this.delegatorOverrides = new List.filled(numTypes, null);
  }

  /// Generates a list of specializations that the interfaces must have that
  /// implement this specialization.
  ///
  /// For example:
  /// An interface with specialization <int, bool> must implement corresponding
  /// interfaces with specializations <null, null>, <int, null> and
  /// <null, bool>.
  /// Another example: <int, null> must only implement <null ,null>
  ///
  /// If [withOverrides] is set to [true], boxed types will be inserted instead
  /// of [null] values in [delegatorOverrides].
  /// For example: <int, bool>    =>    <Int, Bool>, <Int, bool>, <int, Bool>
  List<TypeSpecialization> implementedSpecializations({withOverrides: true}) {
    // Get indices of specialized types
    var specializedIndices = <int>[];
    for (int index = 0; index < types.length; index++) {
      if (isSpecialized(index)) {
        specializedIndices.add(index);
      }
    }

    // Generate all subsets
    var result = <TypeSpecialization>[];
    int maxCounter = (1 << specializedIndices.length) - 1;

    // Counter scheme example for <<int, bool>> specialization
    // specializedIndices = [0, 1]
    // maxCounter = 2**specializedIndices.length - 1 = 3    [binary: 11]
    // c = 0 [binary 00]    =>    <null, null>
    // c = 1 [binary 01]    =>    <int, null>
    // c = 2 [binary 10]    =>    <null, bool>
    for (int c = 0; c < maxCounter; c++) {
      List<PrimitiveType> nextSpec = new List.filled(types.length, null);
      List<JavaType> overrides = new List.filled(types.length, null);

      for (int index = 0; index < specializedIndices.length; index++) {
        // Check if specialized is included in this set
        // (check index-th bit in counter variable c)
        int bit = 1 & (c >> index);
        if (bit == 1) {
          nextSpec[specializedIndices[index]] =
              types[specializedIndices[index]];
        } else {
          if (withOverrides) {
            overrides[specializedIndices[index]] =
                types[specializedIndices[index]].toBoxedType();
          }
        }
      }

      result.add(new TypeSpecialization._forDelegator(nextSpec, overrides));
    }

    return result;
  }

  /// Returns a list of classes/interfaces for all specializations that are
  /// a supertype of the current specialization.
  List<ClassOrInterfaceType> allSupertypeSpecializations(
      ClassOrInterfaceType type) {
    var specs = implementedSpecializations(withOverrides: false);
    return specs.map((s) => type.withSpecialization(s)).toList();
  }

  /// Returns a list of specializations that are in a "subtype" relationship
  /// with this specialization.
  ///
  /// This effectively means generating all possibilities of replacing "null"
  /// with one of the values "int", "bool", "double", or "null" (excluding the
  /// current specialization).
  ///
  /// For example:
  /// <int, null>   =>    <int, int>, <int, bool>, <int, double>
  /// <null, null>  =>    <null, int>, <null, bool>, <null, double>,
  ///                     <int, null>, <int, int>, <int, bool>, ...
  List<TypeSpecialization> subSpecializations() {
    var result = [types.toList()];

    // Generate all possibilites to specialize unspecialized types
    for (int i = 0; i < numUnspecializedTypes; i++) {
      var newResult = <List<PrimitiveType>>[];

      for (var spec in result) {
        // Unspecialized (null) case
        newResult.add(spec);

        for (var type in TypeSpecialization.specializedTypes) {
          List<JavaType> newSpec = spec.toList(); // create a copy
          newSpec[unspecializedIndices[i]] = type;
          newResult.add(newSpec);
        }
      }

      result = newResult;
    }

    result.removeAt(0); // Remove this specialization
    return result.map((l) => new TypeSpecialization.fromTypes(l)).toList();
  }

  /// Returns a list of classes/interfaces for all specializations that are
  /// a supertype of the current specialization.
  List<ClassOrInterfaceType> allSubtypeSpecializations(
      ClassOrInterfaceType type) {
    return subSpecializations().map((s) => type.withSpecialization(s)).toList();
  }

  /// Determines if a specialization is fully generic, i.e., all types are
  /// unspecialized ([null] in [types]).
  bool get isFullyGeneric => types.every((t) => t == null);

  /// Generates the suffix that should be appended to all class/interface
  /// names. Fully generic classes have an empty suffix.
  ///
  /// For example:
  /// Map<int, bool>        Map__int_boolean
  /// Map<Object, bool>     Map__gen_boolean
  /// Map<String, double>   Map__gen_double
  /// Map<Object, String>   Map
  /// Map<bool, T>          Map__boolean_* (depends on what T)
  String get classNameSuffix => isFullyGeneric
      ? ""
      : "__" + types.map((t) => t == null ? "generic" : t.toString()).join("_");

  /// Generates the suffix that should be appended to all method names.
  String get methodSuffix => classNameSuffix;

  /// Look up a type in this specialization.
  JavaType getType(int index) {
    return types[index] ?? delegatorOverrides[index];
  }

  /// Check whether a type is specialized.
  bool isSpecialized(int index) {
    return types[index] != null || delegatorOverrides[index] != null;
  }

  /// Returns a list of indicies of all types that are specialized.
  List<int> get specializedIndices {
    var result = <int>[];
    for (int index = 0; index < types.length; index++) {
      if (isSpecialized(index)) {
        result.add(index);
      }
    }
    return result;
  }

  /// Returns a list of indices of all types that are unspecialized.
  List<int> get unspecializedIndices {
    var result = <int>[];
    for (int index = 0; index < types.length; index++) {
      if (!isSpecialized(index)) {
        result.add(index);
      }
    }
    return result;
  }

  int get numSpecializedTypes => specializedIndices.length;

  int get numUnspecializedTypes => types.length - numSpecializedTypes;

  String toString() {
    // Only for debug purposes
    return "<<${types.join(", ")}>>";
  }
}

/// A builder that generates delegator methods to "less specialized" methods.
///
/// For exmaple, there must be the following Java methods generated for
/// Map__int_bool.operator_atPut:
/// void operator_atPut__int_bool(int, bool)        (generated by java_builder)
/// void operator_atPut__generic_generic(Int, Bool) (generated by this builder)
/// void operator_atPut__generic_bool(Int, bool)    (generated by this builder)
/// void operator_atPut__int_generic(int, Bool)     (generated by this builder)
class DelegatorMethodsBuilder {
  /// The type factory generating types for the class/interface for which
  /// this builder is building methods for.
  final TypeFactory typeFactory;

  DelegatorMethodsBuilder(this.typeFactory);

  /// The specialization of the class/interface for which this builder is
  /// building methods for.
  TypeSpecialization get specialization => typeFactory.specialization;

  /// Builds delegator methods for a class.
  List<java.MethodDef> buildForClass(dart.Class class_) {
    var result = <java.MethodDef>[];

    for (var nextSpec in specialization.implementedSpecializations()) {
      var nextFactory = new TypeFactory(typeFactory.compilerState, nextSpec,
          dartClass: class_);

      // TODO(springerm): Handle dynamic in here
      result.addAll(class_.fields.map((f) => buildGetter(f, nextFactory)));
      result.addAll(class_.fields
          .where((f) => !f.isFinal)
          .map((f) => buildSetter(f, nextFactory)));
      result.addAll(class_.procedures
          .where((p) => p.kind != dart.ProcedureKind.Factory)
          .map((p) => buildProcedure(p, nextFactory)));
      result.addAll(
          class_.constructors.map((c) => buildConstructor(c, nextFactory)));
    }

    return result;
  }

  /// Build delegator method declarations for an interface.
  List<java.MethodDecl> buildForInterface(dart.Class class_) {
    var result = <java.MethodDecl>[];

    for (var nextSpec in specialization.implementedSpecializations()) {
      var nextFactory = new TypeFactory(typeFactory.compilerState, nextSpec,
          dartClass: class_);

      // TODO(springerm): Handle dynamic
      result.addAll(class_.fields.map((f) => buildGetterDecl(f, nextFactory)));
      result.addAll(class_.fields
          .where((f) => !f.isFinal)
          .map((f) => buildSetterDecl(f, nextFactory)));
      result.addAll(class_.procedures
          .where((p) => p.kind != dart.ProcedureKind.Factory)
          .map((p) => buildProcedureDecl(p, nextFactory)));
      result.addAll(
          class_.constructors.map((c) => buildConstructorDecl(c, nextFactory)));
    }

    return result;
  }

  java.MethodDef buildGetter(
      dart.Field field, TypeFactory delegatorTypeFactory) {
    var delegatorName = javaMethodName(
        field.name.name, dart.ProcedureKind.Getter, delegatorTypeFactory);
    var delegateeName =
        javaMethodName(field.name.name, dart.ProcedureKind.Getter, typeFactory);

    var body = new java.Block([
      new java.ReturnStmt(
          new java.MethodInvocation(buildDefaultReceiver(), delegateeName))
    ]);

    return new java.MethodDef(delegatorName, body, [],
        returnType: delegatorTypeFactory.getLValueType(field.type));
  }

  java.MethodDecl buildGetterDecl(
      dart.Field field, TypeFactory delegatorTypeFactory) {
    var delegatorName = javaMethodName(
        field.name.name, dart.ProcedureKind.Getter, delegatorTypeFactory);

    return new java.MethodDecl(delegatorName, [],
        returnType: delegatorTypeFactory.getLValueType(field.type));
  }

  java.MethodDef buildSetter(
      dart.Field field, TypeFactory delegatorTypeFactory) {
    var delegatorName = javaMethodName(
        field.name.name, dart.ProcedureKind.Setter, delegatorTypeFactory);
    var delegateeName =
        javaMethodName(field.name.name, dart.ProcedureKind.Setter, typeFactory);

    var body = new java.Block([
      new java.ReturnStmt(
          new java.MethodInvocation(buildDefaultReceiver(), delegateeName, [
        new java.CastExpr(new java.IdentifierExpr("value"),
            typeFactory.getLValueType(field.type))
      ]))
    ]);
    var parameters = [
      new java.VariableDecl(
          "value", delegatorTypeFactory.getLValueType(field.type))
    ];

    return new java.MethodDef(delegatorName, body, parameters,
        returnType: delegatorTypeFactory.getLValueType(field.type));
  }

  java.MethodDecl buildSetterDecl(
      dart.Field field, TypeFactory delegatorTypeFactory) {
    var delegatorName = javaMethodName(
        field.name.name, dart.ProcedureKind.Setter, delegatorTypeFactory);
    var parameters = [
      new java.VariableDecl(
          "value", delegatorTypeFactory.getLValueType(field.type))
    ];

    return new java.MethodDecl(delegatorName, parameters,
        returnType: delegatorTypeFactory.getLValueType(field.type));
  }

  java.MethodDef buildProcedure(
      dart.Procedure node, TypeFactory delegatorTypeFactory) {
    var delegatorName =
        javaMethodName(node.name.name, node.kind, delegatorTypeFactory);
    var delegateeName = javaMethodName(node.name.name, node.kind, typeFactory);

    // TODO(springerm): Handle named parameters and type parameters
    var invocationArgs = node.function.positionalParameters
        .map((p) => new java.CastExpr(
            new java.IdentifierExpr(p.name), typeFactory.getLValueType(p.type)))
        .toList();

    JavaType returnType =
        delegatorTypeFactory.getLValueType(node.function.returnType);
    var methodInvocation = new java.MethodInvocation(
        buildDefaultReceiver(), delegateeName, invocationArgs);
    var body = returnType == JavaType.void_
        ? new java.ExpressionStmt(methodInvocation)
        : new java.ReturnStmt(methodInvocation);

    var methodParams = node.function.positionalParameters
        .map((p) => new java.VariableDecl(
            p.name, delegatorTypeFactory.getLValueType(p.type)))
        .toList();

    return new java.MethodDef(
        delegatorName, new java.Block([body]), methodParams,
        returnType: returnType);
  }

  java.MethodDef buildConstructor(
      dart.Constructor node, TypeFactory delegatorTypeFactory) {
    var delegatorName = Constants.constructorMethodPrefix +
        node.name.name +
        delegatorTypeFactory.specialization.methodSuffix;
    var delegateeName = Constants.constructorMethodPrefix +
        node.name.name +
        typeFactory.specialization.methodSuffix;

    // TODO(springerm): Handle named parameters and type parameters
    var invocationArgs = node.function.positionalParameters
        .map((p) => new java.CastExpr(
            new java.IdentifierExpr(p.name), typeFactory.getLValueType(p.type)))
        .toList();

    JavaType returnType = JavaType.void_;

    var methodInvocation = new java.MethodInvocation(
        buildDefaultReceiver(), delegateeName, invocationArgs);
    var body = new java.ExpressionStmt(methodInvocation);

    var methodParams = node.function.positionalParameters
        .map((p) => new java.VariableDecl(
            p.name, delegatorTypeFactory.getLValueType(p.type)))
        .toList();

    return new java.MethodDef(
        delegatorName, new java.Block([body]), methodParams,
        returnType: returnType);
  }

  java.MethodDecl buildProcedureDecl(
      dart.Procedure node, TypeFactory delegatorTypeFactory) {
    var delegatorName =
        javaMethodName(node.name.name, node.kind, delegatorTypeFactory);
    // TODO(springerm): Handle named parameters and type parameters
    var methodParams = node.function.positionalParameters
        .map((p) => new java.VariableDecl(
            p.name, delegatorTypeFactory.getLValueType(p.type)))
        .toList();
    JavaType returnType =
        delegatorTypeFactory.getLValueType(node.function.returnType);

    return new java.MethodDecl(delegatorName, methodParams,
        returnType: returnType);
  }

  java.MethodDecl buildConstructorDecl(
      dart.Constructor node, TypeFactory delegatorTypeFactory) {
    var delegatorName = Constants.constructorMethodPrefix +
        node.name.name +
        delegatorTypeFactory.specialization.methodSuffix;
    // TODO(springerm): Handle named parameters and type parameters
    var methodParams = node.function.positionalParameters
        .map((p) => new java.VariableDecl(
            p.name, delegatorTypeFactory.getLValueType(p.type)))
        .toList();
    JavaType returnType = JavaType.void_;

    return new java.MethodDecl(delegatorName, methodParams,
        returnType: returnType);
  }

  java.Expression buildDefaultReceiver() {
    return new java.IdentifierExpr("this");
  }

  String javaMethodName(
      String methodName, dart.ProcedureKind kind, TypeFactory typeFactory) {
    switch (kind) {
      case dart.ProcedureKind.Method:
        return methodName + typeFactory.specialization.methodSuffix;
      case dart.ProcedureKind.Operator:
        var translatedMethodName = Constants.operatorToMethodName[methodName];
        if (translatedMethodName == null) {
          throw new CompileErrorException("${methodName} is not an operator.");
        }
        return translatedMethodName + typeFactory.specialization.methodSuffix;
      case dart.ProcedureKind.Getter:
        return "get" +
            capitalizeString(methodName) +
            typeFactory.specialization.methodSuffix;
      case dart.ProcedureKind.Setter:
        return "set" +
            capitalizeString(methodName) +
            typeFactory.specialization.methodSuffix;
      case dart.ProcedureKind.Factory:
        throw new Exception("Factories should not be handled here");
      default:
        throw new CompileErrorException(
            "Method kind ${kind} not implemented yet.");
    }
  }

  String capitalizeString(String str) =>
      str[0].toUpperCase() + str.substring(1);
}

/// Builds an expression that will evaluate to [true] if the [typeArg]
/// expression corresponds to [specialization].
java.Expression _buildSpecializationCheck(
    dart.Class class_,
    TypeFactory typeFactory,
    java.Expression typeArg,
    TypeSpecialization specialization) {
  var checks = <java.Expression>[];

  for (int index in specialization.specializedIndices) {
    var typeExpr = new java.ArrayAccess(
        new java.FieldAccess(
            ts.makeTypeInfoGetter(class_, typeFactory), 'typeVariables'),
        new java.IntLiteral(index));
    var genericType =
        ts.evaluateTypeExpr(new java.FieldAccess(typeArg, 'env'), typeExpr);

    // Cache TypeExpr evaluation
    var cachedVar = new java.IdentifierExpr(
        "cached_${index}_${specialization.getType(index)}");
    var cachedGenericType = new java.ConditionalExpr(
        new java.BinaryExpr(cachedVar, new java.NullLiteral(), "=="),
        new java.AssignmentExpr(cachedVar, genericType),
        cachedVar);
    var expectedGenericType =
        ts.makeTypeExprForPrimitive(specialization.getType(index));

    checks
        .add(new java.BinaryExpr(cachedGenericType, expectedGenericType, "=="));
  }

  return checks.fold(new java.BoolLiteral(true),
      (prev, e) => new java.BinaryExpr(prev, e, "&&"));
}

/// Builds a (generic) static factory for a constructor.
///
/// This factory performs the following steps.
/// 1. Check generic type to choose correct specialization, if applicable.
///    The first argument that is passed to this method is a Type object
///    representing the type of the object to be constructed. Note that the
///    type object is built at the constructor call site, such that Type
///    objects can be cached (hoisted) at that point.
/// 2. Create an instance of the chosen specialization using the "empty"
///    constructor with the "marker" type.
/// 3. Call the actual constructor, which is an instance method.
/// 4. Return the object.
java.MethodDef buildGenericFactory(dart.Class class_, TypeFactory typeFactory,
    String name, dart.FunctionNode function) {
  // TODO(springerm): For performance reasons, we might want to have a separete
  // static method on every specialization and call that one directly.

  ClassOrInterfaceType classType = typeFactory.getRawClass(class_);
  ClassOrInterfaceType interfaceType = typeFactory.getRawInterface(class_);

  // All specializations except for fully generic
  var allSpecializations = <ClassOrInterfaceType>[];

  if (class_.typeParameters.length <=
      TypeSpecialization.specializationThreshold) {
    allSpecializations =
        new TypeSpecialization.fullyGeneric(class_.typeParameters.length)
            .allSubtypeSpecializations(classType);
  }

  // Sort by number of unspecialized types (least generic first)
  allSpecializations.sort((a, b) =>
      a.specialization.numUnspecializedTypes -
      b.specialization.numUnspecializedTypes);

  var cases = <java.Statement>[];
  var typeParam = new java.IdentifierExpr("type");
  var resultVar = new java.IdentifierExpr("result");

  // TODO(springerm): Handle named arguments
  var methodParams = function.positionalParameters
      .map((p) =>
          new java.VariableDecl(p.name, typeFactory.getLValueType(p.type)))
      .toList();
  var constructorArgs = function.positionalParameters
      .map((p) => new java.IdentifierExpr(p.name))
      .toList();
  var constructorCall = new java.MethodInvocation(
      resultVar, Constants.constructorMethodPrefix + name, constructorArgs);

  // Create cache variables for type checks to avoid doing redundant
  // evaluations
  for (int index = 0; index < class_.typeParameters.length; index++) {
    for (var primType in TypeSpecialization.specializedTypes) {
      cases.add(new java.VariableDeclStmt(new java.VariableDecl(
          "cached_${index}_${primType}", ts.typeType,
          initializer: new java.NullLiteral())));
    }
  }

  // Generate if statements to select correct specialization
  for (var specType in allSpecializations) {
    java.Expression check = _buildSpecializationCheck(
        class_, typeFactory, typeParam, specType.specialization);

    var targetClass = new java.ClassRefExpr(specType);
    var newExpr = new java.NewExpr(targetClass, [
      new java.CastExpr(
          new java.NullLiteral(), JavaType.emptyConstructorMarker),
      typeParam
    ]);

    cases.add(new java.IfStmt(
        check,
        new java.Block([
          new java.ExpressionStmt(new java.AssignmentExpr(resultVar, newExpr)),
          new java.ExpressionStmt(constructorCall),
          new java.ReturnStmt(resultVar)
        ])));
  }

  // Instantiate specialization
  var newGenericExpr = new java.NewExpr(new java.ClassRefExpr(classType), [
    new java.CastExpr(new java.NullLiteral(), JavaType.emptyConstructorMarker),
    typeParam
  ]);

  // Last case: fully generic specialization
  var genericCase = [
    new java.ExpressionStmt(new java.AssignmentExpr(resultVar, newGenericExpr)),
    new java.ExpressionStmt(constructorCall),
    new java.ReturnStmt(resultVar)
  ];
  var resultDecl =
      new java.VariableDeclStmt(new java.VariableDecl("result", interfaceType));

  return new java.MethodDef(
      Constants.javaFactoryPrefix + name,
      new java.Block([resultDecl]..addAll(cases)..addAll(genericCase)),
      [new java.VariableDecl("type", ts.typeRepType)]..addAll(methodParams),
      returnType: interfaceType,
      isStatic: true);
}
