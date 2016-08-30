// Copyright (c) 2016, the Dart project authors.  Please see the AUTHORS file
// for details. All rights reserved. Use of this source code is governed by a
// BSD-style license that can be found in the LICENSE file.

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
///     * Add a type info field (created by calling [makeTypeInfoField]), and
///       insert it as the first field in the generated class. This field
///       **must** come before all other type-system related fields in the class
///       to ensure proper initialization order!
///     * Add a static initializer block (created by calling
///       [makeTypeInfoInitializer]), and insert it after the type info field,
///       but before any other type system related fields in the class.
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

import 'package:kernel/ast.dart' as dart;

import '../compiler/compiler_state.dart';
import 'ast.dart';
import 'constants.dart';

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

/// Generate the type info field for a Dart class.
///
/// The Dart class should not be an @JavaClass. This method will return a static
/// final field that should be inserted first in the generated class's list of
/// declarations. If other type system related fields are placed before this
/// type info field, the type system may not work correctly; we rely on the
/// initialization order of these fields (as guaranteed by the Java language
/// specification).
FieldDecl makeTypeInfoField(dart.Class node, CompilerState compilerState) {
  assert(!compilerState.hasJavaImpl(node));
  var constructorArgs = <Expression>[
    new FieldAccess(new ClassRefExpr(compilerState.getClass(node)), 'class'),
    new FieldAccess(new ClassRefExpr(compilerState.getInterface(node)), 'class')
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
InitializerBlock makeTypeInfoInitializer(
    dart.Class node, CompilerState compilerState) {
  assert(!compilerState.hasJavaImpl(node));
  ClassOrInterfaceType javaCls = compilerState.getClass(node);
  var statements = <Statement>[];
  if (node.supertype != null) {
    statements.add(new ExpressionStmt(new AssignmentExpr(
        new FieldAccess(
            new FieldAccess(new ClassRefExpr(javaCls), _typeInfoFieldName),
            'superclass'),
        makeTypeExpr(node.supertype, compilerState))));
  }
  if (node.mixedInType != null) {
    statements.add(new ExpressionStmt(new AssignmentExpr(
        new FieldAccess(
            new FieldAccess(new ClassRefExpr(javaCls), _typeInfoFieldName),
            'mixin'),
        makeTypeExpr(node.mixedInType, compilerState))));
  }
  if (node.implementedTypes != null && node.implementedTypes.isNotEmpty) {
    statements.add(new ExpressionStmt(new AssignmentExpr(
        new FieldAccess(
            new FieldAccess(new ClassRefExpr(javaCls), _typeInfoFieldName),
            'interfaces'),
        new ArrayInitializer(
            _interfaceTypeExprType,
            node.implementedTypes
                .map((t) => makeTypeExpr(t, compilerState))
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
      isFinal: true,
      initializer:
          new FieldAccess(new ClassRefExpr(_typeEnvironmentType), 'ROOT')));
}

/// Insert this variable declaration at the top of each method body.
///
/// The resulting variable is used by the type system to implement [getTypeEnv].
/// The [member] must be either a [dart.Procedure] or a [dart.Constructor].
VariableDeclStmt makeMethodEnvVar(
    dart.Member member, CompilerState compilerState) {
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
    initializer =
        new FieldAccess(new ClassRefExpr(_typeEnvironmentType), 'ROOT');
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
/// The expression may be stored in any variable (including static variables).
/// It may be reused in any type environment in which [type] is valid (i.e., any
/// type context that includes any type parameters on which [type] depends).
Expression makeTypeExpr(dart.DartType type, CompilerState compilerState) {
  return type.accept(new _TypeExprBuilder(compilerState));
}

/// Make a type expression for a Dart constructor invocation.
///
/// The expression may be computed once and the same value used for every
/// execution of the [dart.ConstructorInvocation].
Expression makeTypeExprForConstructorInvocation(
    dart.ConstructorInvocation node, CompilerState compilerState) {
  return _makeInterfaceTypeExpr(
      node.target.enclosingClass, node.arguments.types, compilerState);
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

Expression _makeInterfaceTypeExpr(dart.Class classNode,
    Iterable<dart.DartType> typeParams, CompilerState compilerState) {
  var typeParamExprs =
      typeParams.map((t) => makeTypeExpr(t, compilerState)).toList();
  var constructorArgs = <Expression>[
    _makeTypeInfoGetter(classNode, compilerState)
  ];
  if (typeParamExprs.isNotEmpty) {
    constructorArgs.add(new ArrayInitializer(_typeExprType, typeParamExprs));
  }
  return new NewExpr(new ClassRefExpr(_interfaceTypeExprType), constructorArgs);
}

class _TypeExprBuilder extends dart.DartTypeVisitor<Expression> {
  final CompilerState compilerState;

  _TypeExprBuilder(this.compilerState);

  @override
  Expression defaultDartType(dart.DartType type) {
    throw new Exception('Unrecognized Dart type: $type');
  }

  @override
  Expression visitInterfaceType(dart.InterfaceType type) {
    return _makeInterfaceTypeExpr(
        type.classNode, type.typeArguments, compilerState);
  }

  @override
  Expression visitTypeParameterType(dart.TypeParameterType type) {
    var parent = type.parameter.parent;
    if (parent is dart.Class) {
      var typeInfoGetter = _makeTypeInfoGetter(parent, compilerState);
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

Expression _makeTypeInfoGetter(
    dart.Class forClass, CompilerState compilerState) {
  return new FieldAccess(
      new ClassRefExpr(compilerState.hasJavaImpl(forClass)
          ? compilerState.getHelperClass(forClass)
          : compilerState.getClass(forClass)),
      _typeInfoFieldName);
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
