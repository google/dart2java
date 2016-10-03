// Copyright 2016, the Dart project authors.
//
// Licensed under the Apache License, Version 2.0 (the "License");
// you may not use this file except in compliance with the License.
// You may obtain a copy of the License at
//
//     http://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing, software
// distributed under the License is distributed on an "AS IS" BASIS,
// WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// See the License for the specific language governing permissions and
// limitations under the License.

import 'package:kernel/ast.dart' as dart;
import 'package:kernel/type_algebra.dart' as dart_ts;

import 'ast.dart' as java;
import 'specialization.dart' as spzn;
import 'types.dart' as java;
import 'type_factory.dart';
import 'type_system.dart' as ts;
import 'visitor.dart' as java;
import 'constants.dart';
import '../compiler/compiler_state.dart';
import '../compiler/kernel_helpers.dart';
import '../compiler/runner.dart' show CompileErrorException;

/// Builds a Java class that contains the top-level procedures and fields in
/// a Dart [Library].
java.ClassDecl buildWrapperClass(
    dart.Library library, CompilerState compilerState) {
  var type = compilerState.getTopLevelClass(library);
  java.ClassDecl result =
      new java.ClassDecl(type, supertype: java.JavaType.object);

  var instance =
      new _JavaAstBuilder(compilerState, thisClassOrInterfaceType: type);

  result.orderedMembers = instance.maybeBuildStaticFieldInitializerBlock(
      library.fields)..addAll(library.fields.map(instance.visitField));
  result.methods = library.procedures.map(instance.visitProcedure).toList();
  result.orderedMembers
      .insertAll(0, instance.typeSystemState.makeStaticFields());

  return result;
}

/// Builds a Java class AST from a kernel class AST.
///
/// Also generates a Java interface for every Dart class. There are some
/// special cases:
/// * Dart classes with a Java implementation class have an interface only
/// * No interfaces/classes are generated for primitive types (e.g., int)
List<java.PackageMember> buildClass(
    dart.Class node, CompilerState compilerState) {
  if (compilerState.isSpecialClass(node)) {
    // TODO(andrewkrieger,springerm): Handle static members in core classes,
    // such as num.parse.
    return [];
  }

  return spzn
      .buildAllSpecializations(node.typeParameters.length)
      .map((spec) => [
            new _JavaAstBuilder(compilerState).buildClass(node, spec),
            new _JavaAstBuilder(compilerState).buildInterface(node, spec)
          ])
      .expand((c) => c) // flatten
      .toList();
}

/// A class containing arguments to be passed during a Java method call.
class _JavaArguments {
  /// Actual positional arguments in Java code
  List<java.Expression> arguments;

  /// Java generic arguments (when calling generic methods)
  List<java.ClassRefExpr> javaGenerics;

  _JavaArguments(this.arguments, [List<java.ClassRefExpr> generics])
      : this.javaGenerics = generics ?? [];
}

/// A temporary local variable defined in a method.
class TemporaryVariable {
  String name;

  dart.DartType type;

  TemporaryVariable(this.name, this.type);
}

/// Builds a Java class from Dart IR.
class _JavaAstBuilder extends dart.Visitor<java.Node> {
  _JavaAstBuilder(this.compilerState, {this.thisClassOrInterfaceType}) {
    this.typeFactory = new TypeFactory(
        compilerState, new spzn.TypeSpecialization.fullyGeneric(0));
    this.typeSystemState = new ts.ClassState(typeFactory);
  }

  TypeFactory typeFactory;

  /// A reference to the [dart.Class] that is being visited.
  ///
  /// This must be [:null:] before [visitNormalClass] is called, and may be null
  /// afterwards. If it is not [:null:] before calling [visitNormalClass], then
  /// this builder assumes that it is being reused, and throws an error. If it
  /// is [:null:] while building a [dart.Member], then that [dart.Member] is a
  /// top-level field or function in a [dart.Library].
  ///
  /// This reference is used to compute the [dart.DartType] of [:this:] inside
  /// of method definitions.
  dart.Class thisDartClass;

  /// A reference to the [java.ClassOrInterfaceType] of the class/interface
  /// that is being visited.
  ///
  /// Also contains a reference to the current specialization.
  java.ClassOrInterfaceType thisClassOrInterfaceType;

  /// Returns true if this class/interface is fully generic or not generic.
  ///
  /// A class/interface specialization is fully generic if all type arguments
  /// are type parameter types.
  ///
  /// For example:
  /// Map                     true
  /// Map_interface           true
  /// Map__int_gen            false
  /// Map_interface_int_gen   false
  /// Symbol                  true
  /// Symbol_interface        true
  bool get isFullyGenericSpecialization =>
      thisClassOrInterfaceType.isFullyGeneric;

  /// The current type specialization for avoiding boxing primitive types
  /// in generic classes.
  spzn.TypeSpecialization get specialization =>
      thisClassOrInterfaceType.specialization;

  /// A counter for generating unique identifiers for temporary variables.
  int tempVarCounter = 0;

  /// A counter for generating unique identifiers for labels.
  int labelCounter = 0;

  /// A map assigning Kernel AST variables to temporary variables.
  Map<dart.VariableDeclaration, TemporaryVariable> tempVars = null;

  /// A map assigning Kernel AST labeled statements to label identifiers.
  var codeLabels = new Map<dart.LabeledStatement, String>();

  /// Generates a unique variable identifier.
  String nextTempVarIdentifier() {
    return "__tempVar_${tempVarCounter++}";
  }

  /// Generates a unique label identifier.
  String nextCodeLabel() {
    return "__codeLabel_${labelCounter++}";
  }

  /// State related to the type system.
  ts.ClassState typeSystemState;

  final CompilerState compilerState;

  /// Default visitor method. Useful to track which AST nodes are not
  /// translated yet.
  @override
  java.Node defaultExpression(dart.Expression node) {
    print("WARNING: java_builder does not handle ${node.runtimeType} yet");
    return null;
  }

  /// Default visitor method. Useful to track which AST nodes are not
  /// translated yet.
  @override
  java.Node defaultStatement(dart.Statement node) {
    print("WARNING: java_builder does not handle ${node.runtimeType} yet");
    return null;
  }

  /// Visits a non-mixin class.
  @override
  java.ClassDecl visitNormalClass(dart.NormalClass node) {
    throw new Exception("Use buildClass(...) instead of visitor");
  }

  java.ClassDecl buildClass(dart.Class node, spzn.TypeSpecialization spec) {
    // TODO(springerm): No support for mixins planned
    assert(node is dart.NormalClass);
    assert(thisDartClass == null && !compilerState.isSpecialClass(node));

    thisDartClass = node;
    typeFactory.specialization = spec;
    typeFactory.dartClass = node;
    thisClassOrInterfaceType = typeFactory.getClass(node.thisType);

    List<java.OrderedClassMember> orderedMembers =
        maybeBuildStaticFieldInitializerBlock(node.fields)
          ..addAll(node.fields.map(this.visitField));
    List<java.MethodDef> implicitGetters =
        node.fields.map(this.buildGetter).toList();
    List<java.MethodDef> implicitSetters =
        node.fields.where((f) => !f.isFinal).map(this.buildSetter).toList();
    List<java.MethodDef> methods = node.procedures
        .where((p) => p.kind != dart.ProcedureKind.Factory)
        .map(this.visitProcedure)
        .toList();
    List<java.MethodDef> constructors = node.constructors
        .map((c) => this.buildConstructor(c, node.fields))
        .toList();

    // Merge getters/setters
    if (methods.any((m) => implicitGetters.any((g) => m.name == g.name))) {
      // This can happen because we simply prepend getters with "get".
      throw new CompileErrorException("Name clash between getter and method");
    }
    if (methods.any((m) => implicitSetters.any((s) => m.name == s.name))) {
      // This can happen because we simply prepend getters with "set".
      throw new CompileErrorException("Name clash between setter and method");
    }

    methods.addAll(constructors);
    methods.addAll(implicitGetters);
    methods.addAll(implicitSetters);
    methods.addAll(
        new spzn.DelegatorMethodsBuilder(typeFactory).buildForClass(node));

    java.ClassOrInterfaceType supertype = typeFactory.getClass(node.supertype);
    if (supertype == java.JavaType.object) {
      // Make sure that "extends Object" results in "extends DartObject"
      // TODO(springerm): Remove hard-coded special case once we
      // figure out interop
      supertype = java.JavaType.dartObject;
    }

    typeSystemState.generateTypeInfo(node);
    if (isFullyGenericSpecialization) {
      // Build factories
      methods.addAll(node.procedures
          .where((p) => p.kind == dart.ProcedureKind.Factory)
          .map(this.visitProcedure));

      if (!node.isAbstract) {
        // Build constructor factories
        methods.addAll(node.constructors.map((c) => spzn.buildGenericFactory(
            node, typeFactory, c.name.name, c.function)));
      }

      // Add fields and initializers from type system
      orderedMembers.insertAll(0, typeSystemState.makeStaticFields());
    } else {
      orderedMembers.insertAll(
          0, typeSystemState.makeStaticFieldsForSpecialization());
    }

    // TODO(springerm): Think about whether a class should directly implement
    // other interfaces or if it is sufficient for the associated interface to
    // extend all other interfaces
    var implementedInterfaces = [typeFactory.getInterface(node.thisType)];
    implementedInterfaces
        .addAll(node.implementedTypes.map(typeFactory.getInterface));

    return new java.ClassDecl(thisClassOrInterfaceType,
        access: java.Access.Public,
        orderedMembers: orderedMembers,
        methods: methods,
        constructors: [buildEmptyConstructor()],
        supertype: supertype,
        isAbstract: node.isAbstract,
        implementedInterfaces: implementedInterfaces);
  }

  /// If any of the [fields] are static and have initializers, create a static
  /// initializer block containing the compiled initializers.
  ///
  /// If there are any static initializers, the returned list will have one
  /// element (an [java.InitializerBlock]). If there are no static fields with
  /// explicit initializers in [fields], the returned list will be empty. In
  /// both cases, the list will be growable, so it can be used as the
  /// [java.ClassDecl.orderedMembers] field (after adding [java.Field]s and
  /// other elements as needed).
  ///
  /// The primary reason for moving initializers into a static block is so that
  /// we can add local variables to the block. This ability is used by the type
  /// system. Consistently using local variables (both for initializers and for
  /// "regular" code) makes interfacing with the type system easier.
  List<java.OrderedClassMember> maybeBuildStaticFieldInitializerBlock(
      Iterable<dart.Field> fields) {
    var statements = fields
        .where((f) => f.isStatic && f.initializer != null)
        .map/*<java.Statement>*/((f) => new java.ExpressionStmt(
            new java.AssignmentExpr(
                new java.FieldAccess(
                    new java.ClassRefExpr(thisClassOrInterfaceType),
                    f.name.name),
                buildCastedExpression(f.initializer, f.type))))
        .toList();
    var ret = <java.OrderedClassMember>[];
    if (statements.isNotEmpty) {
      statements.insert(0, ts.makeTopLevelEnvVar());
      ret.add(new java.InitializerBlock(new java.Block(statements),
          isStatic: true));
    }
    return ret;
  }

  java.FieldDecl buildClassField(dart.Field node) {
    // Non-static fields are initialized in the constructor
    java.Expression initializer = node.isStatic
        ? buildCastedExpression(node.initializer, node.type)
        : null;

    return new java.FieldDecl(
        node.name.name, typeFactory.getLValueType(node.type),
        initializer: initializer,
        access: java.Access.Public,
        isStatic: node.isStatic,
        isFinal: node.isFinal);
  }

  java.InterfaceDecl buildInterface(
      dart.NormalClass node, spzn.TypeSpecialization spec) {
    assert(!compilerState.isSpecialClass(node));
    typeFactory.specialization = spec;
    typeFactory.dartClass = node;
    thisClassOrInterfaceType = typeFactory.getInterface(node.thisType);

    List<java.MethodDecl> methods = node.procedures
        .where((m) => m.kind != dart.ProcedureKind.Factory && !m.isStatic)
        .map(this.buildMethodDecl)
        .toList();
    Iterable<java.MethodDecl> implicitGetters =
        node.fields.where((f) => !f.isStatic).map(this.buildGetterDecl);
    Iterable<java.MethodDecl> implicitSetters = node.fields
        .where((f) => !f.isFinal && !f.isStatic)
        .map(this.buildSetterDecl);

    methods.addAll(implicitGetters);
    methods.addAll(implicitSetters);

    var defaultMethods =
        new spzn.DelegatorMethodsBuilder(typeFactory).buildForInterface(node);

    var superinterfaces = <java.ClassOrInterfaceType>[];
    if (node.supertype != null) {
      // dart.core::Object does not have a supertype
      java.ClassOrInterfaceType supertype =
          typeFactory.getInterface(node.supertype);
      if (supertype == java.JavaType.object) {
        // Make sure that "extends Object" results in
        // "extends DartObject_interface. Using the raw interface is safe
        // because `DartObject` is not a generic class.
        supertype = typeFactory.getRawInterface(compilerState.objectClass);
      }

      if (supertype.isInterface) {
        // TODO(springerm): Figure out interoperability
        superinterfaces.add(supertype);
      }
    }

    // Add specialized interfaces that this interface should extend
    superinterfaces.addAll(
        specialization.allSupertypeSpecializations(thisClassOrInterfaceType));

    // Add all other declared interfaces that this interface extends
    superinterfaces.addAll(node.implementedTypes.map(typeFactory.getInterface));

    return new java.InterfaceDecl(thisClassOrInterfaceType,
        access: java.Access.Public,
        methods: methods,
        defaultMethods: defaultMethods,
        superinterfaces: superinterfaces);
  }

  java.MethodDecl buildMethodDecl(dart.Procedure procedure) {
    String methodName = compilerState.translatedMethodName(
        procedure.name.name, procedure.kind, thisClassOrInterfaceType);
    var returnType = typeFactory.getLValueType(procedure.function.returnType);
    // TODO(springerm): handle named parameters, etc.
    List<java.VariableDecl> parameters = procedure.function.positionalParameters
        .map(buildPositionalParameter)
        .toList();

    return new java.MethodDecl(methodName, parameters,
        returnType: returnType, isFinal: false);
  }

  @override
  java.FieldDecl visitField(dart.Field node) {
    return new java.FieldDecl(
        node.name.name, typeFactory.getLValueType(node.type),
        access: java.Access.Public,
        isStatic: node.isStatic,
        isFinal: node.isFinal);
  }

  java.MethodDecl buildGetterDecl(dart.Field node) {
    String methodName = compilerState.translatedMethodName(
        node.name.name, dart.ProcedureKind.Getter, thisClassOrInterfaceType);
    return new java.MethodDecl(methodName, [],
        returnType: typeFactory.getLValueType(node.type));
  }

  java.MethodDef buildGetter(dart.Field node) {
    String methodName = compilerState.translatedMethodName(
        node.name.name, dart.ProcedureKind.Getter, thisClassOrInterfaceType);
    var body = wrapInJavaBlock(new java.ReturnStmt(new java.FieldAccess(
        buildDefaultReceiver(node.isStatic), node.name.name)));

    return new java.MethodDef(methodName, body, [],
        returnType: typeFactory.getLValueType(node.type),
        isStatic: node.isStatic);
  }

  java.MethodDecl buildSetterDecl(dart.Field node) {
    String methodName = compilerState.translatedMethodName(
        node.name.name, dart.ProcedureKind.Setter, thisClassOrInterfaceType);
    var param =
        new java.VariableDecl("value", typeFactory.getLValueType(node.type));
    return new java.MethodDecl(methodName, [param],
        returnType: typeFactory.getLValueType(node.type));
  }

  java.MethodDef buildSetter(dart.Field node) {
    String methodName = compilerState.translatedMethodName(
        node.name.name, dart.ProcedureKind.Setter, thisClassOrInterfaceType);
    var fieldAssignment = new java.AssignmentExpr(
        new java.FieldAccess(
            buildDefaultReceiver(node.isStatic), node.name.name),
        new java.IdentifierExpr("value"));
    var returnStmt = new java.ReturnStmt(new java.IdentifierExpr("value"));
    var body =
        new java.Block([new java.ExpressionStmt(fieldAssignment), returnStmt]);
    var param =
        new java.VariableDecl("value", typeFactory.getLValueType(node.type));

    return new java.MethodDef(methodName, body, [param],
        returnType: typeFactory.getLValueType(node.type),
        isStatic: node.isStatic);
  }

  @override
  java.Statement visitFieldInitializer(dart.FieldInitializer node) {
    var fieldAssignment = new java.AssignmentExpr(
        new java.FieldAccess(buildDefaultReceiver(false), node.field.name.name),
        buildCastedExpression(node.value, node.field.type));

    return new java.ExpressionStmt(fieldAssignment);
  }

  /// Translates arguments for method calls and constructor invocations.
  ///
  /// Handles positional arguments, type arguments, and optional arguments,
  /// but not named arguments at the moment.
  ///
  /// This method takes a function node as a parameter determine if a
  /// type cast must be inserted before passing an argument.
  _JavaArguments buildArguments(dart.Arguments node, dart.FunctionNode target,
      {bool buildTypeArgs: false, dart.InterfaceType receiverType}) {
    // TODO(springerm): Handle named arguments
    var result = new List<java.Expression>();
    List<java.ClassRefExpr> javaGenerics;

    // Type arguments should not be built for constructor invocations.
    if (buildTypeArgs && node.types.isNotEmpty) {
      // This is a generic method call. The first argument is a type
      // environment.
      java.Expression genericFuncParam =
          ts.makeGenericFuncParam(target, node.types, typeSystemState);

      javaGenerics = node.types
          .map((t) => new java.ClassRefExpr(typeFactory.getTypeArgument(t)))
          .toList();
      result.add(genericFuncParam);
    }

    // Provided positional parameters
    for (int i = 0; i < node.positional.length; i++) {
      dart.DartType expectedType;

      if (receiverType != null) {
        // Substitute the value of the type variables given in the expected
        // parameter type for the actual types in `receiverType`.
        expectedType = dart_ts.substitutePairwise(
            target.positionalParameters[i].type,
            receiverType.classNode.typeParameters,
            receiverType.typeArguments);
      } else {
        // Not necessary for constructors, super calls, or static invocations.
        // Their types should not contain any type variables.
        expectedType = target.positionalParameters[i].type;
      }

      result.add(buildCastedExpression(node.positional[i], expectedType));
    }

    // Fill up with default values of optional positional parameters
    for (int i = node.positional.length;
        i < target.positionalParameters.length;
        i++) {
      result.add(target.positionalParameters[i].initializer.accept(this));
    }

    return new _JavaArguments(result, javaGenerics);
  }

  @override
  java.Statement visitSuperInitializer(dart.SuperInitializer node) {
    // TODO(springerm): insert correct name of constructor
    String superMethodName = Constants.constructorMethodPrefix;

    return new java.ExpressionStmt(new java.SuperMethodInvocation(
        superMethodName,
        buildArguments(node.arguments, node.target.function).arguments));
  }

  Iterable<java.Statement> buildTempVarDecls() {
    return tempVars.values.map((v) => new java.VariableDeclStmt(
        new java.VariableDecl(v.name, typeFactory.getLValueType(v.type))));
  }

  java.VariableDecl buildPositionalParameter(dart.VariableDeclaration node) {
    java.VariableDecl varDecl = node.accept(this);

    // Default values are passed at call site
    varDecl.initializer = null;

    return varDecl;
  }

  @override
  java.MethodDef visitProcedure(dart.Procedure procedure) {
    String methodName = compilerState.translatedMethodName(
        procedure.name.name, procedure.kind, thisClassOrInterfaceType);
    var returnType = typeFactory.getLValueType(procedure.function.returnType);

    if (procedure.function.typeParameters.isNotEmpty) {
      typeSystemState.generateProcedureTypeInfo(procedure);
    }

    // TODO(springerm): handle named parameters, etc.
    List<java.VariableDecl> parameters = procedure.function.positionalParameters
        .map(buildPositionalParameter)
        .toList();
    var isStatic = procedure.isStatic;

    var typeParameterNames =
        procedure.function.typeParameters.map((t) => t.name).toList();

    // Handling of generic methods (including factory methods)
    var typeSystemVar = ts.makeGenericFuncParamDecl(procedure.function);
    if (typeSystemVar != null) {
      parameters.insert(0, typeSystemVar);
    }

    java.Block body;
    if (procedure.isExternal) {
      // Generate a method call to a static Java method.
      // Every external Dart method must be annotated with "JavaCall".
      // TODO(stanm): add check.
      String externalJavaMethod =
          getSimpleAnnotation(procedure, Constants.javaCallAnnotation);
      List<String> methodTokens = externalJavaMethod.split(".");
      var extReceiver = new java.ClassRefExpr(
          new java.ClassOrInterfaceType.parseTopLevel(
              methodTokens.getRange(0, methodTokens.length - 1).join(".")));
      var extMethodName = methodTokens.last;

      List<java.Expression> arguments = [];
      if (!isStatic) {
        // First argument is "this"
        arguments
            .add(new java.IdentifierExpr(Constants.javaStaticThisIdentifier));
      }
      // Remaining arguments are parameters of Dart method
      arguments.addAll(parameters.map((p) => new java.IdentifierExpr(p.name)));

      if (procedure.function.returnType is dart.VoidType) {
        body = new java.Block([
          new java.ExpressionStmt(
              new java.MethodInvocation(extReceiver, extMethodName, arguments))
        ]);
      } else {
        body = new java.Block([
          new java.ReturnStmt(
              new java.MethodInvocation(extReceiver, extMethodName, arguments))
        ]);
      }
    } else {
      // Normal Dart method
      if (!procedure.isAbstract) {
        assert(tempVars == null);
        tempVars = new Map<dart.VariableDeclaration, TemporaryVariable>();

        body = wrapInJavaBlock(buildStatement(procedure.function.body));

        // Insert declarations of temporary variables
        body.statements.insertAll(0, buildTempVarDecls());

        // Insert variable required by type system
        java.Statement typeEnvVarDeclStmt =
            ts.makeMethodEnvVar(procedure, typeFactory);
        if (typeEnvVarDeclStmt != null) {
          // Only for non-generic methods
          body.statements.insert(0, typeEnvVarDeclStmt);
        }

        tempVars = null;
      }
    }

    if (methodName == "main" && procedure.enclosingClass == null) {
      if (parameters.length > 1) {
        throw new CompileErrorException(
            'Not implemented yet: Cannot handle main functions with more than'
            ' one argument.');
      }
      if (parameters.length == 1 &&
          parameters[0].type != java.JavaType.object) {
        throw new CompileErrorException(
            'Not implemented yet: Cannot handle main functions with an argument'
            ' that is not of dynamic type.');
      }

      var stringArrayType = new java.ArrayType(java.JavaType.string, 1);
      if (parameters.length == 0) {
        parameters.add(new java.VariableDecl("args", stringArrayType));
      } else {
        parameters[0].type = stringArrayType;
      }

      // Ignore the declared/dynamic return type of main and set it to void.
      // TODO(stanm): make sure there are no odd return statements in body, e.g.
      // `return 1337;`: return values from main are ignored in Dart, as it is
      // assumed that main has a `void` type.
      returnType = java.JavaType.void_;
    }

    return new java.MethodDef(methodName, body, parameters,
        typeParameterNames: typeParameterNames,
        returnType: returnType,
        isStatic: isStatic,
        isFinal: false,
        isAbstract: procedure.isAbstract);
  }

  /// Builds an empty constructor invoking the super empty constructor.
  ///
  /// An empty constructor contains an empty body and calls the empty
  /// super constructor. It is necessary to prevent Java from calling the
  /// default super constructor. The first parameter is merely used as a marker
  /// to dispatch to the correct (overloaded) constructor.
  java.Constructor buildEmptyConstructor() {
    java.Statement superCall =
        new java.SuperConstructorInvocation(<java.Expression>[
      new java.IdentifierExpr("arg"),
      new java.IdentifierExpr("type")
    ]);

    return new java.Constructor(thisClassOrInterfaceType,
        wrapInJavaBlock(superCall), <java.VariableDecl>[
      new java.VariableDecl("arg", java.JavaType.emptyConstructorMarker),
      new java.VariableDecl("type", ts.typeRepType)
    ]);
  }

  /// Translates a Kernel AST constructor to a Java constructor and method.
  ///
  /// Creates a Java constructor which is an instance method. This method
  /// is called by a static factory constructor method.
  /// The constructor performs the following steps:
  ///
  /// 1. Direct field initializations (at VariableDecl site)
  /// 2. Field initializer list
  /// 3. Super constructor
  /// 4. Constructor body
  ///
  /// It is hard to implement these semantics with a plain Java constructor
  /// because the super constructor invocation must always be the first
  /// statement in a Java constructor. Therefore, we put the body of the
  /// constructor in an instance method.
  ///
  /// A Java super constructor should only be invoked if that is explicitly
  /// specified in the code. We do not want Java to automatically call the
  /// super constructor (since Dart and Java have different semantics regarding
  /// the execution order). For that reason, every class has an "empty"
  /// constructor, which is invoked at the beginning of the delegator. That
  /// constructor does nothing except for passing the runtime type to the empty
  /// constructor of its superclass.
  java.MethodDef buildConstructor(
      dart.Constructor node, List<dart.Field> fields) {
    Iterable<dart.Initializer> fieldInitializers =
        node.initializers.where((i) => i.runtimeType == dart.FieldInitializer);
    Iterable<java.Statement> javaFieldInitializers =
        fieldInitializers.map((i) => i.accept(this));

    // Field initializers that are part of VariableDecls
    Iterable<java.Statement> javaDirectFieldInitializers = fields
        .where((f) => f.isInstanceMember && f.initializer != null)
        .map((f) => new java.ExpressionStmt(new java.AssignmentExpr(
            new java.FieldAccess(buildDefaultReceiver(false), f.name.name),
            buildCastedExpression(f.initializer, f.type))));

    // There is either 0 or 1 super initializers
    Iterable<dart.Initializer> superInitializers =
        node.initializers.where((i) => i.runtimeType == dart.SuperInitializer);
    Iterable<java.Statement> javaSuperInitializers =
        superInitializers.map((i) => i.accept(this));

    java.Block body;
    if (node.function.body == null) {
      // Constructor may not have a body (sometimes also contains a body
      // with an [EmptyStatement]).
      body = new java.Block(<java.Statement>[]);
    } else {
      body = wrapInJavaBlock(buildStatement(node.function.body));
    }
    body.statements.insertAll(0, javaSuperInitializers);
    body.statements.insertAll(0, javaFieldInitializers);
    body.statements.insertAll(0, javaDirectFieldInitializers);
    // Insert variable required by the type system
    body.statements.insert(0, ts.makeMethodEnvVar(node, typeFactory));

    // TODO(springerm): handle named parameters, etc.
    List<java.VariableDecl> parameters = node.function.positionalParameters
        .map(buildPositionalParameter)
        .toList();

    // Determine name to use for the "type" parameter (the runtime type of the
    // instance under creation).
    var parameterNames = parameters.map((p) => p.name).toSet();
    var typeParamName = "type";
    while (parameterNames.contains(typeParamName)) {
      typeParamName += "\$";
    }

    var methodName = Constants.constructorMethodPrefix + node.name.name;

    java.MethodDef constructorBody =
        new java.MethodDef(methodName, body, parameters);

    return constructorBody;
  }

  /// Wraps a Java statement in a block, if [stmt] is not already a block.
  java.Block wrapInJavaBlock(java.Statement stmt) {
    if (stmt is java.Block) {
      return stmt;
    } else {
      return new java.Block([stmt]);
    }
  }

  @override
  java.Block visitBlock(dart.Block node) {
    return new java.Block(node.statements.map(buildStatement).toList());
  }

  @override
  java.Block visitEmptyStatement(dart.EmptyStatement node) {
    // TODO(springerm): We don't have an empty statement right now,
    // but an empty block has no effect
    return new java.Block(<java.Statement>[]);
  }

  @override
  java.IfStmt visitIfStatement(dart.IfStatement node) {
    return new java.IfStmt(
        node.condition.accept(this),
        wrapInJavaBlock(buildStatement(node.then)),
        node.otherwise == null
            ? null
            : wrapInJavaBlock(buildStatement(node.otherwise)));
  }

  @override
  java.ThrowStmt visitThrow(dart.Throw node) {
    // TODO(springerm): Implement proper exception handling
    // TODO(springerm): "throw" is an expression in Dart but a statement
    // in Java
    return new java.ThrowStmt(node.expression.accept(this));
  }

  @override
  java.ConditionalExpr visitConditionalExpression(
      dart.ConditionalExpression node) {
    return new java.ConditionalExpr(node.condition.accept(this),
        node.then.accept(this), node.otherwise.accept(this));
  }

  @override
  java.WhileStmt visitWhileStatement(dart.WhileStatement node) {
    return new java.WhileStmt(node.condition.accept(this),
        wrapInJavaBlock(buildStatement(node.body)));
  }

  @override
  java.DoStmt visitDoStatement(dart.DoStatement node) {
    return new java.DoStmt(node.condition.accept(this),
        wrapInJavaBlock(buildStatement(node.body)));
  }

  @override
  java.ForStmt visitForStatement(dart.ForStatement node) {
    return new java.ForStmt(
        node.variables.map((v) => v.accept(this)).toList()
        as List<java.VariableDecl>,
        node.condition.accept(this),
        node.updates.map((u) => u.accept(this)).toList()
        as List<java.Expression>,
        wrapInJavaBlock(buildStatement(node.body)));
  }

  @override
  java.Statement visitForInStatement(dart.ForInStatement node) {
    String iteratorName = nextTempVarIdentifier();
    var iteratorVariable = new java.IdentifierExpr(iteratorName);

    var iterableType = node.iterable.staticType as dart.InterfaceType;
    List<java.JavaType> javaTypeArgs =
        iterableType.typeArguments.map(typeFactory.getLValueType).toList();
    java.ClassOrInterfaceType javaIteratorType = typeFactory
        .getRawInterface(compilerState.iteratorClass)
        .withTypeArguments(javaTypeArgs);
    java.ClassOrInterfaceType javaIterableType = typeFactory
        .getRawInterface(compilerState.iterableClass)
        .withTypeArguments(javaTypeArgs);

    var iteratorVarDecl = new java.VariableDeclStmt(new java.VariableDecl(
        node.variable.name, typeFactory.getLValueType(node.variable.type),
        initializer: new java.MethodInvocation(
            iteratorVariable,
            compilerState.translatedMethodName(
                "current", dart.ProcedureKind.Getter, javaIteratorType))));

    var iteratorDecl = new java.VariableDecl(iteratorName, javaIteratorType,
        initializer: new java.CastExpr(
            new java.MethodInvocation(
                node.iterable.accept(this),
                compilerState.translatedMethodName(
                    "iterator", dart.ProcedureKind.Getter, javaIterableType)),
            javaIteratorType));

    var body = new java.Block([iteratorVarDecl]
      ..addAll(wrapInJavaBlock(buildStatement(node.body)).statements));

    return new java.ForStmt(
        [iteratorDecl],
        new java.MethodInvocation(
            iteratorVariable,
            compilerState.translatedMethodName(
                "moveNext", dart.ProcedureKind.Method, javaIteratorType)),
        [],
        body);
  }

  @override
  java.BreakStmt visitBreakStatement(dart.BreakStatement node) {
    String label = codeLabels[node.target];
    return new java.BreakStmt(label);
  }

  @override
  java.LabeledStmt visitLabeledStatement(dart.LabeledStatement node) {
    // TODO(springerm): Handle labels in places other than loops
    String label = nextCodeLabel();
    codeLabels[node] = label;

    return new java.LabeledStmt(
        label, wrapInJavaBlock(buildStatement(node.body)));
  }

  @override
  java.Statement visitReturnStatement(dart.ReturnStatement node) {
    // Find procedure
    dart.TreeNode procNode = node;
    while (procNode is! dart.Procedure) {
      procNode = procNode.parent;
    }

    if (node.expression != null &&
        node.expression.staticType is dart.VoidType) {
      // Return statement with expression of type "void"
      return new java.Block([
        new java.ExpressionStmt(node.expression.accept(this)),
        new java.ReturnStmt()
      ]);
    } else {
      return new java.ReturnStmt(buildCastedExpression(
          node.expression, (procNode as dart.Procedure).function.returnType));
    }
  }

  @override
  java.Statement visitExpressionStatement(dart.ExpressionStatement node) {
    java.Node translated = node.expression.accept(this);
    if (translated is java.Statement) {
      // This is already a statement (e.g., when translating [dart.Throw])
      return translated;
    } else {
      return new java.ExpressionStmt(node.expression.accept(this));
    }
  }

  /// Builds a method invocation where the call target is not statically known.
  java.MethodInvocation buildDynamicMethodInvocation(
      dart.Expression receiver, String methodName, _JavaArguments javaArgs) {
    var args = javaArgs.arguments;
    var genericArgs = javaArgs.javaGenerics;

    // Translate receiver
    java.Expression recv = receiver.accept(this);
    dart.DartType recvType = receiver.staticType;

    if (recvType is! dart.InterfaceType) {
      throw new CompileErrorException(
          "Can only handle method invocation where receiver is an InterfaceType "
          "(found ${recvType.runtimeType} for ${receiver})");
    }

    dart.Class classNode = (recvType as dart.InterfaceType).classNode;

    // Change method name if annotated with @JavaMethod
    if (compilerState.isSpecialClass(classNode)) {
      methodName = compilerState.getJavaMethodName(classNode, methodName);
    }

    // Intercept method call if necessary
    if (compilerState.usesHelperFunction(classNode, methodName)) {
      java.ClassOrInterfaceType helperClass =
          compilerState.getHelperClass(classNode);

      // Generate static call to helper function.
      java.ClassRefExpr helperRefExpr = new java.ClassRefExpr(helperClass);
      // Dynamic method invocation
      return new java.MethodInvocation(
          helperRefExpr, methodName, [recv]..addAll(args), genericArgs);
    }

    return new java.MethodInvocation(recv, methodName, args, genericArgs);
  }

  @override
  java.MethodInvocation visitPropertyGet(dart.PropertyGet node) {
    if (node.receiver.staticType is dart.DynamicType) {
      // Generate dynamic method invocation
      String methodName = compilerState.translatedMethodName(
          node.name.name, dart.ProcedureKind.Getter);

      // Generate static call to helper function
      var helperRefExpr = new java.ClassRefExpr(java.JavaType.dynamicHelper);

      List<java.Expression> javaArgs = [
        new java.StringLiteral(methodName),
        node.receiver.accept(this)
      ];

      return new java.MethodInvocation(
          helperRefExpr, Constants.dynamicHelperInvoke, javaArgs);
    }

    var interfaceType = node.receiver.staticType as dart.InterfaceType;
    var lookupProcedure = findProcedureInClassHierarchy(
        node.name.name, interfaceType,
        kind: dart.ProcedureKind.Getter);
    var lookupField = findFieldInClassHierarchy(node.name.name, interfaceType);

    dart.InterfaceType ownerType;

    if (lookupProcedure != null) {
      ownerType = lookupProcedure.receiverType;
    } else if (lookupField != null) {
      ownerType = lookupField.receiverType;
    } else {
      throw new CompileErrorException(
          "Field or getter not found in receiver class.");
    }

    String methodName = compilerState.translatedMethodName(node.name.name,
        dart.ProcedureKind.Getter, typeFactory.getLValueType(ownerType));

    return buildDynamicMethodInvocation(
        node.receiver, methodName, new _JavaArguments([]));
  }

  @override
  java.MethodInvocation visitPropertySet(dart.PropertySet node) {
    if (node.receiver.staticType is dart.DynamicType) {
      // Generate dynamic method invocation
      String methodName = compilerState.translatedMethodName(
          node.name.name, dart.ProcedureKind.Setter);

      // Generate static call to helper function
      var helperRefExpr = new java.ClassRefExpr(java.JavaType.dynamicHelper);

      List<java.Expression> javaArgs = [
        new java.StringLiteral(methodName),
        node.receiver.accept(this),
        node.value.accept(this)
      ];

      return new java.MethodInvocation(
          helperRefExpr, Constants.dynamicHelperInvoke, javaArgs);
    }

    if (node.receiver.staticType is! dart.InterfaceType) {
      throw new CompileErrorException(
          "Can only handle property set where receiver is an InterfaceType "
          "(found ${node.staticType.runtimeType})");
    }

    dart.DartType expectedType;

    var interfaceType = node.receiver.staticType as dart.InterfaceType;
    var lookupProcedure = findProcedureInClassHierarchy(
        node.name.name, interfaceType,
        kind: dart.ProcedureKind.Setter);
    var lookupField = findFieldInClassHierarchy(node.name.name, interfaceType);

    dart.InterfaceType ownerType;

    if (lookupProcedure != null) {
      expectedType =
          lookupProcedure.member.function.positionalParameters.first.type;
      ownerType = lookupProcedure.receiverType;
    } else if (lookupField != null) {
      expectedType = lookupField.member.type;
      ownerType = lookupField.receiverType;
    } else {
      throw new CompileErrorException(
          "Field or setter not found in receiver class.");
    }

    // Substitute the value of the type variables given in `expectedType` for
    // actual types in `ownerType`.
    expectedType = dart_ts.substitutePairwise(expectedType,
        ownerType.classNode.typeParameters, ownerType.typeArguments);

    String methodName = compilerState.translatedMethodName(node.name.name,
        dart.ProcedureKind.Setter, typeFactory.getLValueType(ownerType));

    return buildDynamicMethodInvocation(node.receiver, methodName,
        new _JavaArguments([buildCastedExpression(node.value, expectedType)]));
  }

  // Returns [:null:] on failure.
  java.Expression tryOptimizingOperatorInvocation(dart.MethodInvocation node) {
    String methodName = node.name.name;
    if (Constants.operatorToMethodName.containsKey(methodName)) {
      if (node.receiver.staticType is! dart.InterfaceType) {
        return null;
      }
      dart.InterfaceType type = node.receiver.staticType;
      java.JavaType lValueType = typeFactory.getLValueType(type);
      if (lValueType is java.PrimitiveType &&
          lValueType.operators.contains(methodName)) {
        // There is an operator for the type of the receiver that has the same
        // name as the invocation being compiled.
        int length = node.arguments.positional.length;
        if (length == 0) {
          if (methodName.startsWith('unary')) {
            // Some unary operators are identified by prepending the string
            // 'unary' to their name. This needs to be removed when outputting
            // Java code.
            methodName = methodName.substring('unary'.length);
          }
          return new java.UnaryExpr(node.receiver.accept(this), methodName);
        }
        if (length != 1) {
          throw new CompileErrorException(
              "Operator $methodName received too many arguments ($length).");
        }
        dart.Expression rhs = node.arguments.positional[0];
        if (rhs.staticType == type) {
          return new java.BinaryExpr(
              node.receiver.accept(this), rhs.accept(this), methodName);
        }
        // If the static types of the left hand side and the right hand side
        // (rhs) are different, then default on the optimization.
      }
    }
    return null;
  }

  @override
  java.Expression visitMethodInvocation(dart.MethodInvocation node) {
    // Try to generate a Java binary or unary expression in case [node] is an
    // operator on a primitive type.
    java.Expression expression = tryOptimizingOperatorInvocation(node);
    if (expression != null) return expression;

    String methodName = node.name.name;

    // Expand operator symbol to Java-compatible method name
    String javaName = Constants.operatorToMethodName[methodName] ?? methodName;

    if (Constants.objectMethods.contains(javaName)) {
      // This method is defined on Object and must dispatch to ObjectHelper
      // directly to handle "null" values correctly
      java.ClassOrInterfaceType helperClass =
          compilerState.getHelperClass(compilerState.objectClass);
      // Generate static call to helper function.
      var helperRefExpr = new java.ClassRefExpr(helperClass);
      List<java.Expression> javaArgs = [node.receiver.accept(this)]
        ..addAll(node.arguments.positional.map((i) => i.accept(this)));

      return new java.MethodInvocation(helperRefExpr, javaName, javaArgs);
    }

    if (node.receiver.staticType is dart.DynamicType) {
      // Generate dynamic method invocation
      // Generate static call to helper function
      var helperRefExpr = new java.ClassRefExpr(java.JavaType.dynamicHelper);

      List<java.Expression> javaArgs = [
        new java.StringLiteral(javaName),
        node.receiver.accept(this)
      ]..addAll(node.arguments.positional.map((i) => i.accept(this)));

      return new java.MethodInvocation(
          helperRefExpr, Constants.dynamicHelperInvoke, javaArgs);
    }

    if (node.receiver.staticType is! dart.InterfaceType) {
      throw new CompileErrorException(
          "Expected InterfaceType in method invocation "
          "(found ${node.staticType.runtimeType})");
    }

    var lookupProcedure = findProcedureInClassHierarchy(
        methodName, node.receiver.staticType as dart.InterfaceType);

    dart.InterfaceType ownerType;

    dart.FunctionNode targetFunction;
    if (lookupProcedure != null) {
      targetFunction = lookupProcedure.member.function;
      ownerType = lookupProcedure.receiverType;
    } else {
      throw new CompileErrorException(
          "Method ${methodName} not found in receiver "
          "class ${node.receiver.staticType}.");
    }

    // Call specialized method
    javaName = compilerState.translatedMethodName(javaName,
        dart.ProcedureKind.Method, typeFactory.getLValueType(ownerType));

    return buildDynamicMethodInvocation(
        node.receiver,
        javaName,
        buildArguments(node.arguments, targetFunction,
            receiverType: ownerType));
  }

  @override
  java.SuperMethodInvocation visitSuperMethodInvocation(
      dart.SuperMethodInvocation node) {

    // Call specialized version if superclass is specialized. This requires
    // determining the binding of the superclass's type parameters with
    // respect to the current (call site) class's type parameters.
    var specializedThisType =
        spzn.buildSpecializedDartType(thisDartClass, specialization);
    var lookupProcedure = findProcedureInClassHierarchy(
        node.name.name, specializedThisType,
        kind: node.target.kind, onlySuper: true);

    dart.InterfaceType ownerType;
    if (lookupProcedure != null) {
      ownerType = lookupProcedure.receiverType;
    } else {
      throw new CompileErrorException(
          "Method ${node.name.name} not found in superclass of receiver"
          " class ${thisDartClass}.");
    }

    String superMethodName = compilerState.translatedMethodName(
        node.name.name, node.target.kind, typeFactory.getLValueType(ownerType));

    return new java.SuperMethodInvocation(superMethodName,
        buildArguments(node.arguments, node.target.function).arguments);
  }

  @override
  java.Expression visitStaticInvocation(dart.StaticInvocation node) {
    java.Expression receiver;
    dart.Class receiverClass = null;
    String methodName = compilerState.translatedMethodName(
        node.target.name.name, node.target.kind);

    if (node.target.enclosingClass == null) {
      receiver = new java.ClassRefExpr(
          compilerState.getTopLevelClass(node.target.enclosingLibrary));
    } else {
      // TODO(andrewkrieger,springerm): Don't use getRawClass here if the
      // enclosing class uses a helper class. Currently, calls like [int.parse]
      // will fail.
      receiver = new java.ClassRefExpr(
          typeFactory.getRawClass(node.target.enclosingClass));
      receiverClass = node.target.enclosingClass;
    }

    var args = buildArguments(node.arguments, node.target.function,
        buildTypeArgs: true);
    var posArgs = args.arguments;
    var genericArgs = args.javaGenerics;

    // Intercept method call if necessary
    if (compilerState.usesHelperFunction(receiverClass, methodName)) {
      java.ClassOrInterfaceType helperClass =
          compilerState.getHelperClass(receiverClass);

      // Generate static call to helper function.
      java.ClassRefExpr helperRefExpr = new java.ClassRefExpr(helperClass);
      // Static method invocation
      java.FieldAccess staticNested = new java.FieldAccess(
          helperRefExpr, Constants.helperNestedClassForStatic);
      return new java.MethodInvocation(
          staticNested, methodName, posArgs, genericArgs);
    }

    // Factory methods always return the fully generic type and have to
    // be casted manually. For example:
    // List.factory$("int")     --> returns object of type List_interface
    //                              should be casted to List_interface__int
    bool isReturnValueGeneric = node.staticType is dart.InterfaceType &&
        (node.staticType as dart.InterfaceType).typeArguments.isNotEmpty;

    if (isSafeToInsertCast(node) && isReturnValueGeneric) {
      return new java.CastExpr(
          new java.MethodInvocation(receiver, methodName, posArgs, genericArgs),
          typeFactory.getLValueType(node.staticType));
    } else {
      return new java.MethodInvocation(
          receiver, methodName, posArgs, genericArgs);
    }
  }

  @override
  java.Expression visitConstructorInvocation(dart.ConstructorInvocation node) {
    // TODO(springerm): Check for usesHelperFunction
    // TODO(springerm): Handle other parameter types
    List<java.Expression> args =
        buildArguments(node.arguments, node.target.function).arguments;

    // Substitute the value of the type variables given in `node.arguments` for
    // the type variables in `node.target.enclosingClass`.
    dart.DartType dartType = dart_ts.substitutePairwise(
        node.target.enclosingClass.thisType,
        node.target.enclosingClass.typeParameters,
        node.arguments.types);
    java.ClassOrInterfaceType javaType =
        typeFactory.getRawClass(node.target.enclosingClass);

    if (javaType == java.JavaType.object) {
      // Make sure that "new Object" results in "new DartObject"
      // TODO(springerm): Remove hard-coded special case once we
      // figure out interop
      javaType = java.JavaType.dartObject;
    }

    var typeExpr = ts.makeTypeExpr(dartType, typeSystemState);
    var runtimeType = ts.evaluateTypeExpr(ts.getTypeEnv(), typeExpr);
    args.insert(0, runtimeType);

    return new java.MethodInvocation(new java.ClassRefExpr(javaType),
        Constants.javaFactoryPrefix + node.name.name, args);
  }

  /// Returns the enclosing class type of a member or the top level class type
  /// if there is no enclosing class.
  java.ClassOrInterfaceType getEnclosingOfMember(dart.Member member) {
    if (member.enclosingClass == null) {
      // Belongs to top top level
      return compilerState.getTopLevelClass(member.enclosingLibrary);
    } else {
      // TODO(andrewkrieger,springerm): Don't use getRawClass here if the
      // enclosing class uses a helper class. Currently, this will fail if
      // [member] is a static property on a "special" class.
      return typeFactory.getRawClass(member.enclosingClass);
    }
  }

  @override
  java.FieldAccess visitStaticGet(dart.StaticGet node) {
    assert(!node.target.isInstanceMember);

    if (node.target is dart.Field) {
      // Static field read
      dart.Field field = node.target;

      // TODO(springerm): Reconsider passing method name here (it is a field!)
      if (compilerState.usesHelperFunction(
          node.target.enclosingClass, field.name.name)) {
        // Access static field in helper class
        java.ClassOrInterfaceType helperClass =
            compilerState.getHelperClass(node.target.enclosingClass);
        java.ClassRefExpr helperRefExpr = new java.ClassRefExpr(helperClass);
        java.FieldAccess staticNested = new java.FieldAccess(
            helperRefExpr, Constants.helperNestedClassForStatic);
        return new java.FieldAccess(staticNested, field.name.name);
      } else {
        // Regular static field access
        java.Expression fieldAccess = new java.FieldAccess(
            new java.ClassRefExpr(getEnclosingOfMember(node.target)),
            field.name.name);
        return fieldAccess;
      }
    } else {
      throw new CompileErrorException(
          'Not implemented yet: Cannot handle StaticGet for '
          '${node.target.runtimeType}');
    }
  }

  @override
  java.AssignmentExpr visitStaticSet(dart.StaticSet node) {
    assert(!node.target.isInstanceMember);

    if (node.target is dart.Field) {
      // Static field read
      dart.Field field = node.target;

      // TODO(springerm): Reconsider passing method name here (it is a field!)
      if (compilerState.usesHelperFunction(
          node.target.enclosingClass, field.name.name)) {
        throw new CompileErrorException(
            'Not implemented yet: Cannot handle StaticSet for '
            'types with helper classes');
      } else {
        // Regular static field access
        java.Expression fieldAccess = new java.FieldAccess(
            new java.ClassRefExpr(getEnclosingOfMember(node.target)),
            field.name.name);

        java.Expression newValue;
        if (node.target is dart.Field) {
          newValue = buildCastedExpression(
              node.value, (node.target as dart.Field).type);
        } else if (node.target is dart.Procedure) {
          // Calling a setter
          newValue = buildCastedExpression(
              node.value,
              (node.target as dart.Procedure)
                  .function
                  .positionalParameters
                  .first
                  .type);
        }

        return new java.AssignmentExpr(fieldAccess, newValue);
      }
    } else {
      throw new CompileErrorException(
          'Not implemented yet: Cannot handle StaticSet for '
          '${node.target.runtimeType}');
    }
  }

  @override
  java.UnaryExpr visitNot(dart.Not node) {
    return new java.UnaryExpr(node.operand.accept(this), "!");
  }

  @override
  java.Expression visitStringConcatenation(dart.StringConcatenation node) {
    // The logic here is as follows:
    // 1. If there is a single expression X that is not of static type String,
    //    then "" is prepended to the list to be combined (makes Java treat all
    //    `+` operations as string concatenations);
    // 2. For every literal or value typed-variable X, just X is generated and
    //    for everything else X.toString() is generated;
    // 3. All are combined by `+`.
    var strings = <java.Expression>[];

    if (node.expressions.first.staticType !=
        compilerState.stringClass.rawType) {
      strings.add(new java.StringLiteral(""));
    }

    for (var e in node.expressions) {
      if (e is dart.BasicLiteral ||
          compilerState.isPrimitiveType(e.staticType)) {
        strings.add(e.accept(this));
      } else {
        strings.add(new java.MethodInvocation(
            e.accept(this), Constants.toStringMethodName));
      }
    }

    return strings.reduce((v, e) => new java.BinaryExpr(v, e, "+"));
  }

  @override
  java.Expression visitIsExpression(dart.IsExpression node) {
    java.Expression operand = node.operand.accept(this);
    java.Expression typeExpr = ts.makeTypeExpr(node.type, typeSystemState);
    java.Expression type = ts.evaluateTypeExpr(ts.getTypeEnv(), typeExpr);
    return ts.makeSubtypeCheck(ts.getTypeOf(operand), type);
  }

  @override
  java.NullLiteral visitNullLiteral(dart.NullLiteral node) {
    return new java.NullLiteral();
  }

  @override
  java.BinaryExpr visitLogicalExpression(dart.LogicalExpression node) {
    return new java.BinaryExpr(
        node.left.accept(this), node.right.accept(this), node.operator);
  }

  /// Assuming that [node] has a single annotation of type [annotation] and
  /// that annotation has a single String parameter, return the parameter
  /// value. If the assumptions do not apply, throw an exception.
  String getSimpleAnnotation(dart.Procedure node, String annotation,
      [String fieldName = "name"]) {
    // TODO(springerm): Try to use DartTypes here instead of Strings
    var obj = node.analyzerMetadata
        .firstWhere((i) => i.type.toString() == annotation);
    if (obj == null) {
      throw new CompileErrorException(
          "Unable to find ${annotation} annotation");
    }

    return obj.getField(fieldName).toStringValue();
  }

  /// Build a reference to "this".
  java.Expression buildDefaultReceiver(bool isStatic) {
    assert(!compilerState.isSpecialClass(thisDartClass));
    if (isStatic) {
      // Refer to fully generic specialization
      return new java.ClassRefExpr(typeFactory.getRawClass(thisDartClass));
    } else {
      return new java.IdentifierExpr("this");
    }
  }

  @override
  java.IdentifierExpr visitThisExpression(dart.ThisExpression node) {
    return buildDefaultReceiver(false);
  }

  @override
  java.IdentifierExpr visitVariableGet(dart.VariableGet node) {
    if (node.variable.name == null) {
      // This must be a temporary variable
      String name = tempVars[node.variable]?.name;

      if (name == null) {
        throw new CompileErrorException("Expected temporary variable.");
      }

      return new java.IdentifierExpr(name);
    } else {
      return new java.IdentifierExpr(node.variable.name);
    }
  }

  @override
  java.AssignmentExpr visitVariableSet(dart.VariableSet node) {
    return new java.AssignmentExpr(new java.IdentifierExpr(node.variable.name),
        buildCastedExpression(node.value, node.variable.type));
  }

  @override
  java.Expression visitAsExpression(dart.AsExpression node) {
    return ts.makeTypeCast(
        node.operand.accept(this), node.type, typeSystemState);
  }

  @override
  java.Expression visitTypeCheckExpression(dart.TypeCheckExpression node) {
    return ts.makeTypeCheck(
        node.operand.accept(this), node.type, typeSystemState);
  }

  /// Translates a node and inserts a cast depending on the expected type.
  ///
  /// Inserts automatic downcasts if an expression is assigned to an lvalue
  /// with a more specific type.
  ///
  /// This method is also used for supporting covariant generics.
  ///
  /// Java generics are not covariant but Dart generics are. The current
  /// implementation uses both Java generics and an additional field for
  /// reified generics in generated Java code. Using Java generics has the
  /// benefit that less explicit casts are necessary, which makes codegen
  /// easier (see DartList.java for an example). For example:
  ///
  /// List<int> list = ...
  /// int a = list[1]
  ///
  /// This code snippet does not require a Java cast because it is translated
  /// to the following:
  ///
  /// DartList<Integer> list = ...
  /// Integer a = list.operatorAt(1) --> returns Integer
  ///
  /// If List were not generic, the return type would be an Object and the code
  /// generator would have to insert an explicit cast. The following generated
  /// Java code is troublesome:
  ///
  /// DartList<String> stringList = ...
  /// DartList<Object> list = stringList;
  ///
  /// In order to make that code compile, this method inserts an additional
  /// cast that does *not* result in a runtime check.
  ///
  /// DartList<Object> list = (DartList) stringList;
  ///
  /// Note, that Dart checks types at a different point of time as Java (i.e.,
  /// when adding something to a list etc.), but that is another issue. Afaik,
  /// it is not possible to get rid of the runtime type check when accessing
  /// an element in the list in Java without writing bytecode directly. Even
  /// then, the bytecode verifier might reject the code.
  ///
  /// No cast is inserted if both object and expected type are specializations,
  /// e.g., assigning a List<int> to a List<int>-typed variable
  java.Expression buildCastedExpression(
      dart.Expression node, dart.DartType expectedType) {
    if (node == null) {
      // This method is sometimes called on optional nodes, e.g. initializers
      return null;
    }

    dart.DartType type = node.staticType;

    // Handle covariant generics
    if (type is dart.InterfaceType && expectedType is dart.InterfaceType) {
      Iterable<java.JavaType> javaTypeArguments =
          type.typeArguments.map(typeFactory.getTypeArgument);
      Iterable<java.JavaType> expectedJavaTypeArguments =
          expectedType.typeArguments.map(typeFactory.getTypeArgument);

      if (javaTypeArguments.isNotEmpty ||
          expectedJavaTypeArguments.isNotEmpty) {
        // Insert a cast
        java.JavaType targetType = typeFactory.getLValueType(expectedType);

        if (targetType is java.ClassOrInterfaceType) {
          targetType =
              (targetType as java.ClassOrInterfaceType).withoutJavaGenerics();
        }

        return new java.CastExpr(node.accept(this), targetType);
      }
    }

    // Handle automatic upcasts
    if (expectedType is dart.InterfaceType) {
      java.JavaType targetType = typeFactory.getLValueType(expectedType);
      targetType = targetType is java.ClassOrInterfaceType
          ? targetType.withTypeArguments([])
          : targetType;

      // Handle assignment of dynamic
      if (type is dart.DynamicType) {
        return new java.CastExpr(node.accept(this), targetType);
      }

      // Handle general case with InterfaceTypes
      if (type is dart.InterfaceType &&
          type.classNode != expectedType.classNode &&
          compilerState.isSubclassOf(expectedType.classNode, type.classNode)) {
        return new java.CastExpr(node.accept(this), targetType);
      }
    }

    return node.accept(this);
  }

  @override
  java.Expression visitLet(dart.Let node) {
    // TODO(springerm): Need to seriously optimize this for simple
    // increments/decrements

    // Let expressions require two operations: (1) create and assign a
    // temporary variable and (2) evaluate and return an expression.
    // This is hard to do in an expression because (1) is a statement.
    // Here's a way to do it:
    // Create a temporary variable at the beginning of the method and
    // assign it Let.variable where the let expression occurs. Pass this
    // assignment expression as an argument to the `comma` method (sequence
    // point method) along with the body of the let expression. That is a way
    // to implement Let expressions without lambdas. `comma` simply returns
    // the second parameter and acts as a sequence point (comma in C/C++).

    String tempIdentifier = nextTempVarIdentifier();
    tempVars[node.variable] =
        new TemporaryVariable(tempIdentifier, node.variable.type);

    var assignment = new java.AssignmentExpr(
        new java.IdentifierExpr(tempIdentifier),
        buildCastedExpression(node.variable.initializer, node.variable.type));

    return new java.MethodInvocation(
        new java.ClassRefExpr(java.JavaType.letHelper),
        Constants.sequencePointMethodName,
        <java.Expression>[assignment, node.body.accept(this)]);
  }

  /// Determines if it is safe to cast this expression.
  bool isSafeToInsertCast(dart.TreeNode node) {
    return node.parent is! dart.ExpressionStatement &&
        (node as dart.Expression).staticType is! dart.VoidType;
  }

  @override
  java.Expression visitListLiteral(dart.ListLiteral node) {
    var args = <java.Expression>[];

    var typeArguments = <java.JavaType>[
      typeFactory.getTypeArgument(node.typeArgument)
    ];
    // Calling convention: Type argument as first argument
    // for static invocations, then positional arguments
    // Class<T> object inserted only to get type inference right
    args.addAll(typeArguments.map((t) => new java.TypeExpr(t)));
    args.addAll(node.expressions
        .map((e) => buildCastedExpression(e, node.typeArgument)));

    var typeExpr = ts.makeTypeExpr(node.staticType, typeSystemState);
    var runtimeType = ts.evaluateTypeExpr(ts.getTypeEnv(), typeExpr);
    args.insert(0, runtimeType);

    java.ClassOrInterfaceType dartListClass =
        new java.ClassOrInterfaceType('dart._runtime.base', 'DartList');

    if (!isSafeToInsertCast(node)) {
      return new java.MethodInvocation(new java.ClassRefExpr(dartListClass),
          Constants.listInitializerMethodName, args);
    } else {
      return new java.CastExpr(
          new java.MethodInvocation(new java.ClassRefExpr(dartListClass),
              Constants.listInitializerMethodName, args),
          typeFactory.getLValueType(node.staticType));
    }
  }

  @override
  java.BoolLiteral visitBoolLiteral(dart.BoolLiteral node) {
    return new java.BoolLiteral(node.value);
  }

  @override
  java.IntLiteral visitIntLiteral(dart.IntLiteral node) {
    return new java.IntLiteral(node.value);
  }

  @override
  java.DoubleLiteral visitDoubleLiteral(dart.DoubleLiteral node) {
    return new java.DoubleLiteral(node.value);
  }

  @override
  java.StringLiteral visitStringLiteral(dart.StringLiteral node) {
    return new java.StringLiteral(node.value);
  }

  /// Convert a Dart statement to a Java statement.
  ///
  /// Some statements require special handling.
  java.Statement buildStatement(dart.Statement node) {
    var result = node.accept(this);

    if (node is dart.VariableDeclaration) {
      // A variable declaration should sometimes be a statement. In that case,
      // we ensure that the variable is initialized (to null if necessary).
      var decl = result as java.VariableDecl;
      decl.initializer ??= new java.NullLiteral();
      return new java.VariableDeclStmt(decl);
    } else {
      return result;
    }
  }

  @override
  java.VariableDecl visitVariableDeclaration(dart.VariableDeclaration node) {
    return new java.VariableDecl(
        node.name, typeFactory.getLValueType(node.type),
        isFinal: node.isFinal,
        initializer: buildCastedExpression(node.initializer, node.type));
  }

  /// We should never call this method; "visiting" a [dart.DartType] is way too
  /// ambiguous, so we should use the explicit methods in [CompilerState]
  /// instead.
  @override
  defaultDartType(dart.DartType node) => throw new StateError(
      '$_JavaAstBuilder unexpectedly visited a Dart type ($node).');
}
