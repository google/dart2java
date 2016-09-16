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

/// Interface between the compiler and the run-time type system.
///
/// Here is a high-level description of the concepts relevant to the type
/// system:
///
/// * Type environments are objects of the Java class [typeEnvType] (or one of
///   its subtypes). They hold the values of type variables at some point in
///   execution. The compiler should insert code in certain places (described
///   below) to create local variables that describe the current type
///   environment. When needed, the compiler can use the code from [getTypeEnv]
///   to access the type environment (even inside of nested functions, etc.)
/// * Type expressions are objects of the Java class [typeExprType] (or one of
///   its subtypes). They are symbolic representations of a type, analogous to
///   `List<int>` or `Map<String,T>`. Type expressions may involve type
///   variables. Type expressions are evaluated in the context of a type
///   environment (see [evaluateTypeExpr]), and the result is a type
///   representation.
/// * Type representations are objects of the Java class [typeRepType] (or one
///   of its subclasses). These representations are stored on Dart objects, and
///   are used for type testing. In addition, the type representations for
///   classes and functions have an associated type environment, but this is not
///   directly accessible by the compiler.
/// * Type info records are objects of unspecified Java classes. They are used
///   to store semantic information like the signature of a class (i.e. its
///   superclass, interfaces, etc.) or the signature of a function (i.e. its
///   parameters, return type, etc.). These will always be stored as Java
///   `static final` fields, and will be referenced by the type system
///   automatically when needed.
///
/// The compiler should only interface with the type system through the public
/// API of this library, so that it's easier to change the underlying type
/// system.  Similarly, the compiler must take the following actions, to ensure
/// that the type system is properly initialized. Failure to do so might cause
/// Java compile errors, or worse, successful compilation with subtly erroneous
/// behavior.
///
/// * When compiling a class:
///
///     * Create a new [ClassState]. This contains internal state that is local
///       to the Java class under compilation (such as tables of variables that
///       were lifted to static fields). Many of the methods in this library
///       require a [ClassState].
///     * Pass the [dart.Class] node to [ClassState.generateTypeInfo], so that
///       the type system will include import information from the Dart class
///       declaration in the generated Java class.
///     * Under the current class model, each generated class will have one
///       initializing constructor (called an empty constructor, because its
///       body is empty) that just calls the superclass empty constructor. These
///       empty constructors should take two parameters: first, a parameter of
///       type `ConstructorHelper.EmptyConstructorMarker` (typically, the value
///       passed for this parameter will be `null`)  and second, a type rep (or
///       static type [typeRepType]). The empty constructor should pass these
///       arguments verbatim to its super-constructor. The `DartObject`
///       constructor will save the type rep in a field (and ignore the marker).
///     * The non-synthetic constructors need to receive the actual type
///       of the object under construction. The compiler should add an argument
///       of type [typeRepType] as the first formal parameter to every generated
///       constructor (each _delegator_, in the language used in the
///       documentation comment on [JavaBuilder.buildConstructor]). This
///       argument should be passed verbatim to the empty constructor (along
///       with the marker object, which should be passed as
///       `(EmptyConstructorMarker) null`).
///     * Call [ClassState.makeStaticFields] and insert the resulting
///       [FieldDecl]s *before any other static fields or initializer blocks*.
///       Note that the call to [ClassState.makeStaticFields] must happen
///       *after* the other fields have been compiled, but the resulting
///       declarations must come *before* the compiled fields. (This is because
///       [ClassState.makeStaticFields] needs to have information about the
///       other fields at compile time (in order to create the right static
///       variables), but at runtime, the other fields might rely on the fields
///       generated by [ClassState.makeStaticFields].)
///
/// * When compiling the static initialization block that contains all static
///   field initializers (we assume that all static field initializers are moved
///   into a single static initialization block, in order to declare this local
///   variable):
///
///     * Insert the variable declaration returned by [makeTopLevelEnvVar] at
///       the beginning of the block.
///
/// * When compiling a constructor body (which includes instance initializers
///   and Dart code), and when compiling a method body (instance method, static
///   method, or top-level method):
///
///      * Insert the variable declaration returned by [makeMethodEnvVar] at the
///        beginning of the method body.
///
/// * When compiling a constructor invocation:
///
/// TODO(andrewkrieger): That method no longer exists in here
///     * Build a type expression via [makeTypeExprForConstructorInvocation].
///     * Evaluate the type expression as described below.
///
/// * When compiling a reference to a type (`is` expression, constructor, etc.)
///   stored as a [dart.DartType]:
///
///     * Build the type expression via [makeTypeExpr].
///     * Evaluate the type expression as described below.
///
/// * To evaluate a type expression:
///
///     * Call [evaluateTypeExpr] with the type environment (accessed via
///     [getTypeEnv] and the type expression in question.
library dart2java.src.java.type_system;

import 'dart:collection' show HashMap;

import 'package:kernel/ast.dart' as dart;

import 'ast.dart';
import 'constants.dart';
import 'type_factory.dart';

/// The java class used for type representations.
///
/// When storing a type representation in a variable, the compiler should use
/// this type for the variable.
ClassOrInterfaceType typeRepType =
    new ClassOrInterfaceType(_typePackage, 'Type');

/// The java class used for type expressions.
///
/// When storing a type expression in a variable, the compiler should use this
/// type for the variable.
ClassOrInterfaceType typeExprType =
    new ClassOrInterfaceType(_typePackage, 'TypeExpr');

/// The java class used for type environments.
///
/// When storing a type environment in a variable, the compiler should use this
/// type for the variable.
ClassOrInterfaceType typeEnvType =
    new ClassOrInterfaceType(_typePackage, 'TypeEnv');

/// Type-system related state that's local to a generated Java class.
///
/// Note that this type is used for synthetic top-level Java classes and for
/// "ordinary" compiled Java classes. Create an instance of this class every
/// time you start generating a Java class, pass it to the methods that require
/// it, and then make sure to add the fields (and static initializers) from
/// [makeStaticFields] at the beginning of in the list of generated fields in
/// the generated class!
class ClassState {
  final TypeFactory _typeFactory;
  final _typeExprNames = <dart.DartType, String>{};
  final _typeExprNameBuilder = new _TypeNameBuilder();

  /// If set, then this class should have type info fields generated for the
  /// given Dart class declaration.
  dart.Class _dartClass;

  ClassState(TypeFactory typeFactory) : _typeFactory = typeFactory;

  /// Return the name of the static variable used to cache the type expression
  /// for [type].
  ///
  /// This is used by [makeTypeExpr], which returns a variable access for the
  /// static variable. This is why the compiler needs to pass a [ClassState]
  /// instance into [makeTypeExpr] (and any other methods that eventually call
  /// [makeTypeExpr]).
  String _getTypeExprName(dart.DartType type) {
    return _typeExprNames.putIfAbsent(
        type, () => 'dart2java\$typeExpr_' + type.accept(_typeExprNameBuilder));
  }

  /// The compiler should call this after generating code for the class under
  /// compilation, and insert the results at the beginning of the list of class
  /// fields.
  Iterable<OrderedClassMember> makeStaticFields() sync* {
    OrderedClassMember typeInfoInit;
    if (_dartClass != null) {
      yield _makeTypeInfoField(_dartClass, _typeFactory);
      // Call _makeTypeInfoInitializer before emitting the type expressions,
      // since the initializer will generally use some type expressions.
      typeInfoInit = _makeTypeInfoInitializer(_dartClass, this);
    }
    yield* _typeExprNames.keys.map((type) {
      String name = _typeExprNames[type];
      ClassOrInterfaceType javaType =
          type is dart.InterfaceType ? _interfaceTypeExprType : typeExprType;
      return new FieldDecl(name, javaType,
          access: Access.Private,
          isStatic: true,
          isFinal: true,
          initializer: type.accept(new _TypeExprBuilder(_typeFactory)));
    });
    if (_dartClass != null) {
      yield typeInfoInit;
    }
  }

  /// Tell the type system that the Java class corresponding to this
  /// [ClassState] is a translated Dart class.
  ///
  /// The compiler must call this when compiling a [dart.Class] in order for the
  /// type system to inject type info into the generated class. If the compiler
  /// omits the call to [generateTypeInfo], it's likely that references to the
  /// Dart class being compiled (e.g. `is` checks or possibly even constructor
  /// invocations) will fail to compile in Java.
  void generateTypeInfo(dart.Class node) {
    assert(_dartClass == null);
    _dartClass = node;
  }
}

/// Generate the type info field for a Dart class.
///
/// The Dart class should not be an @JavaClass. This method will return a static
/// final field that should be inserted first in the generated class's list of
/// declarations. If other type system related fields are placed before this
/// type info field, the type system may not work correctly; we rely on the
/// initialization order of these fields (as guaranteed by the Java language
/// specification).
FieldDecl _makeTypeInfoField(dart.Class node, TypeFactory typeFactory) {
  assert(typeFactory.getRawClass(node) != null);

  var constructorArgs = <Expression>[
    new TypeExpr(typeFactory.getRawClass(node)),
    new TypeExpr(typeFactory.getRawInterface(node))
  ];
  if (node.typeParameters.isNotEmpty) {
    constructorArgs.insert(
        0,
        new ArrayInitializer(
            JavaType.string,
            node.typeParameters
                .map((dart.TypeParameter t) => new StringLiteral(t.name))
                .toList()));
  }
  var constructorCall =
      new NewExpr(new ClassRefExpr(_interfaceTypeInfoType), constructorArgs);
  return new FieldDecl(_typeInfoFieldName, _interfaceTypeInfoType,
      access: Access.Public,
      isStatic: true,
      isFinal: true,
      initializer: constructorCall);
}

/// Generate a static initializer block that completes initialization of the
/// type info field for a Dart class.
///
/// This initializer block should be inserted directly after the type info field
/// produced by [makeTypeInfoField]. We rely on the order in which these
/// initializers are executed, so failure to order them properly may result in
/// subtle bugs!
InitializerBlock _makeTypeInfoInitializer(dart.Class node, ClassState state) {
  ClassOrInterfaceType rawJavaCls = state._typeFactory.getRawClass(node);
  assert(rawJavaCls != null);
  var statements = <Statement>[];
  if (node.supertype != null) {
    statements.add(new ExpressionStmt(new AssignmentExpr(
        new FieldAccess(
            new FieldAccess(new ClassRefExpr(rawJavaCls), _typeInfoFieldName),
            'superclass'),
        makeTypeExpr(node.supertype, state))));
  }
  if (node.mixedInType != null) {
    statements.add(new ExpressionStmt(new AssignmentExpr(
        new FieldAccess(
            new FieldAccess(new ClassRefExpr(rawJavaCls), _typeInfoFieldName),
            'mixin'),
        makeTypeExpr(node.mixedInType, state))));
  }
  if (node.implementedTypes != null && node.implementedTypes.isNotEmpty) {
    statements.add(new ExpressionStmt(new AssignmentExpr(
        new FieldAccess(
            new FieldAccess(new ClassRefExpr(rawJavaCls), _typeInfoFieldName),
            'interfaces'),
        new ArrayInitializer(
            _interfaceTypeExprType,
            node.implementedTypes
                .map((t) => makeTypeExpr(t, state))
                .toList()))));
  }

  return new InitializerBlock(new Block(statements), isStatic: true);
}

/// Retrieve the "true" run-time type of a Dart object.
///
/// This does not take into account any user overrides of [:runtimeType:], and
/// works correctly whether the [object] is a Dart object or an instance of a
/// @JavaClass.
Expression getTypeOf(Expression object) {
  return new MethodInvocation(
      new ClassRefExpr(_typeHelperType), 'getTrueType', [object]);
}

/// Insert this variable declaration at the top of the static initializer block
/// generated for Dart static field initializers.
///
/// The resulting variable is used by the type system to implement [getTypeEnv].
VariableDeclStmt makeTopLevelEnvVar() {
  return new VariableDeclStmt(new VariableDecl(
      _localTypeEnvVarName, _typeEnvironmentType,
      isFinal: true, initializer: rootEnvironment));
}

/// Insert this variable declaration at the top of each method body.
///
/// The resulting variable is used by the type system to implement [getTypeEnv].
/// The [member] must be either a [dart.Procedure] or a [dart.Constructor].
VariableDeclStmt makeMethodEnvVar(dart.Member member, TypeFactory typeFactory) {
  bool isStatic;
  if (member is dart.Constructor) {
    isStatic = false;
  } else if (member is dart.Procedure) {
    isStatic = member.isStatic;
    if (member.function.typeParameters.isNotEmpty) {
      // TODO(andrewkrieger): Extend the type environment with additional
      // parameters. This will be needed for generic constructors as well.
      throw new UnimplementedError('Generic methods not implemented.');
    }
  } else {
    throw new StateError('Invalid member $member of type ${member.runtimeType} '
        'passed to makeMethodEnvVar');
  }

  Expression initializer;
  if (isStatic) {
    initializer = rootEnvironment;
  } else {
    initializer = new FieldAccess(
        new FieldAccess(new IdentifierExpr('this'), _typeFieldName), 'env');
  }

  return new VariableDeclStmt(new VariableDecl(
      _localTypeEnvVarName, _typeEnvironmentType,
      isFinal: true, initializer: initializer));
}

/// Retrieve the current type environment.
///
/// The compiler will always generate code in blocks (usually function bodies;
/// occasionally initialization blocks) that store the correct type environment
/// in a local variable. The expression returned by this method accesses that
/// variable.
Expression getTypeEnv() {
  return new IdentifierExpr(_localTypeEnvVarName);
}

/// Make a type expression for a Dart type.
///
/// The type passed in may include type variables that are "in scope" (such as
/// those belonging to the immediately enclosing class or any enclosing
/// function). Universal function types are not currently supported.
///
/// The expression returned will be a static variable access, so don't worry
/// about it creating new objects. The static variable in question will
/// be added to [state] if necessary.
Expression makeTypeExpr(dart.DartType type, ClassState state) {
  return new IdentifierExpr(state._getTypeExprName(type));
}

/// Evaluate a type expression in the context of a type environment.
///
/// The resulting type depends on the expression and (potentially) on the type
/// environment. The result can be reused for as long as the environment is
/// still active. For example, it is valid to save the resulting type as a local
/// variable in a function (since the body of that function, excluding the
/// bodies of any nested functions or lambda, is executing entirely in one type
/// environment). However, saving the resulting type as a static variable across
/// calls to an instance method is not generally valid, since different
/// instances of the same class might have been instantiated with different type
/// parameters.
Expression evaluateTypeExpr(Expression env, Expression expr) {
  return new MethodInvocation(env, 'evaluate', [expr]);
}

/// Create an expression that evaluates the check `left isSubtypeOf right`.
Expression makeSubtypeCheck(Expression leftType, Expression rightType) {
  return new MethodInvocation(leftType, 'isSubtypeOf', [rightType]);
}

/// Create an expression that casts [operand] to the given Dart [type].
Expression makeTypeCast(
    Expression operand, dart.DartType type, ClassState state) {
  return _makeCast(operand, type, state, "cast");
}

/// Create an expression that checks that [operand] has the given [type].
Expression makeTypeCheck(
    Expression operand, dart.DartType type, ClassState state) {
  return _makeCast(operand, type, state, "check");
}

/// Common code for [makeTypeCast] and [makeTypeCheck].
Expression _makeCast(Expression operand, dart.DartType type, ClassState state,
    String castMethod) {
  var expr = makeTypeExpr(type, state);
  var rep = evaluateTypeExpr(getTypeEnv(), expr);
  var javaType = state._typeFactory.getLValueType(type);
  // `(javaType) rep.<method>(operand);`
  return new CastExpr(
      new MethodInvocation(rep, castMethod, [operand]), javaType);
}

class _TypeExprBuilder extends dart.DartTypeVisitor<Expression> {
  final TypeFactory typeFactory;

  _TypeExprBuilder(this.typeFactory);

  @override
  Expression defaultDartType(dart.DartType type) {
    throw new Exception('Unrecognized Dart type: $type');
  }

  @override
  Expression visitInterfaceType(dart.InterfaceType type) {
    var typeParamExprs =
        type.typeArguments.map((t) => t.accept(this) as Expression).toList();
    var constructorArgs = <Expression>[
      makeTypeInfoGetter(type.classNode, typeFactory)
    ];
    if (typeParamExprs.isNotEmpty) {
      constructorArgs.add(new ArrayInitializer(_typeExprType, typeParamExprs));
    }
    return new NewExpr(
        new ClassRefExpr(_interfaceTypeExprType), constructorArgs);
  }

  @override
  Expression visitTypeParameterType(dart.TypeParameterType type) {
    var parent = type.parameter.parent;
    if (parent is dart.Class) {
      var typeInfoGetter = makeTypeInfoGetter(parent, typeFactory);
      var typeVarIndex = parent.typeParameters.indexOf(type.parameter);
      if (typeVarIndex < 0) {
        throw new Exception('Type parameter "${type.parameter}" not found in '
            'its parent class.');
      }
      return new ArrayAccess(new FieldAccess(typeInfoGetter, 'typeVariables'),
          new IntLiteral(typeVarIndex));
    } else if (parent is dart.FunctionNode) {
      // TODO(andrewkrieger): Implement function types
      throw new Exception('Function types not implemented yet');
    } else {
      throw new Exception('Type parameter "${type.parameter}" has unrecognized '
          'parent "$parent" of runtime-type ${parent.runtimeType}');
    }
  }
}

class _TypeNameBuilder extends dart.DartTypeVisitor<String> {
  /// Identifiers that cannot be used for new types.
  ///
  /// If a mapping `key -> value` is present in this map, then the [String]
  /// `key` can only be mapped to `value`. All the Java keywords (which can't
  /// be used as the return value for any valid Dart type) are also included in
  /// this map, but are mapped to [dart.InvalidType].
  final _reservedNames = new HashMap<String, dart.DartType>()
    ..['dynamic'] = const dart.DynamicType()
    ..addAll(new Map.fromIterables(
        Constants.reservedWords,
        new Iterable.generate(
            Constants.reservedWords.length, (n) => const dart.InvalidType())));

  /// Avoids collisions with reserved words or with names that have already been
  /// used; call this before returning an identifier.
  String _check(String candidate, dart.DartType type) {
    var reserved = _reservedNames[candidate];
    if (reserved == null || reserved == type) {
      _reservedNames[candidate] = type;
      return candidate;
    } else {
      for (int i = 0;; i++) {
        var newName = '$candidate\$$i';
        reserved = _reservedNames[newName];
        if (reserved == null || reserved == type) {
          _reservedNames[newName] = type;
          return newName;
        }
      }
    }
  }

  @override
  String defaultDartType(dart.DartType type) =>
      throw new Exception('Cannot generate a name for $type.');

  @override
  String visitDynamicType(dart.DynamicType type) => _check("dynamic", type);

  @override
  String visitVoidType(dart.VoidType type) => "void";

  @override
  String visitInterfaceType(dart.InterfaceType type) {
    String candidate = type.classNode.name;
    if (type.typeArguments.isNotEmpty) {
      var typeArgNames = type.typeArguments.map((t) => t.accept(this));
      candidate += '\$lt${typeArgNames.join('\$')}\$gt';
    }
    return _check(candidate, type);
  }

  @override
  String visitFunctionType(dart.FunctionType type) {
    String paramStr =
        type.positionalParameters.take(3).map((t) => t.accept(this)).join('\$');
    String retStr = type.returnType.accept(this);
    return _check('\$lp$paramStr\$rp$retStr', type);
  }

  @override
  String visitTypeParameterType(dart.TypeParameterType type) {
    var parent = type.parameter.parent;
    String candidate;
    if (parent is dart.Class) {
      candidate = '${parent.name}\$${type.parameter.name}';
    } else {
      // We could build a "fancier" name if parent is a FunctionNode, but it's
      // hardly worth it. The hashCode may not be unique, but _check should
      // prevent any conflicts.
      candidate = '_${parent.hashCode}\$${type.parameter.name}';
    }
    return _check(candidate, type);
  }
}

Expression makeTypeInfoGetter(dart.Class forClass, TypeFactory typeFactory) {
  return new FieldAccess(
      new ClassRefExpr(typeFactory.compilerState.isSpecialClass(forClass)
          ? typeFactory.compilerState.getHelperClass(forClass)
          : typeFactory.getRawClass(forClass)),
      _typeInfoFieldName);
}

// TODO(springerm): Refactor!
Expression makeTypeExprForPrimitive(PrimitiveType type) {
  Expression typeExpr;

  if (type == JavaType.boolean) {
    typeExpr = new FieldAccess(
        new ClassRefExpr(
            new ClassOrInterfaceType("dart._runtime.helpers", "BoolHelper")),
        "type");
  } else if (type == JavaType.double_) {
    typeExpr = new FieldAccess(
        new ClassRefExpr(
            new ClassOrInterfaceType("dart._runtime.helpers", "DoubleHelper")),
        "type");
  } else if (type == JavaType.int_) {
    typeExpr = new FieldAccess(
        new ClassRefExpr(
            new ClassOrInterfaceType("dart._runtime.helpers", "IntegerHelper")),
        "type");
  } else {
    throw new Exception("Unknown type: $type");
  }

  return typeExpr;
}

// These constants are declared here, rather than in constants, since they are
// type-system specific.
const _localTypeEnvVarName = 'dart2java\$localTypeEnv';
const _typeFieldName = 'dart2java\$type';
const _typeInfoFieldName = 'dart2java\$typeInfo';
const _typePackage = 'dart._runtime.types.simple';

// Frequently-needed Java types
final _interfaceTypeExprType =
    new ClassOrInterfaceType(_typePackage, 'InterfaceTypeExpr');
final _interfaceTypeInfoType =
    new ClassOrInterfaceType(_typePackage, 'InterfaceTypeInfo');
final _typeEnvironmentType =
    new ClassOrInterfaceType(_typePackage, 'TypeEnvironment');
final _typeExprType = new ClassOrInterfaceType(_typePackage, 'TypeExpr');
final _typeHelperType =
    new ClassOrInterfaceType(Constants.dartHelperPackage, 'TypeSystemHelper');
final rootEnvironment =
    new FieldAccess(new ClassRefExpr(_typeEnvironmentType), 'ROOT');
final typeType = new ClassOrInterfaceType(_typePackage, 'Type');
