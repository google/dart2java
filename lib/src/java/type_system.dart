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
///   execution. There is a top-level type environment (accessed via
///   [getTopLevelTypeEnv]) that should be used in static initializers, but
///   inside the body of a function, the compiler should use [getLocalTypeEnv]
///   instead. This retrieves the correct type environment at the current point
///   in execution (which might belong to the enclosing (generic) class or
///   (generic) function).
/// * Type expressions are objects of the Java class [typeExprType] (or one of
///   its subtypes). They are symbolic representations of a type, analogous to
///   `List<int>` or `Map<String,T>`. Type expressions may involve type
///   variables. Type expressions are evaluated in the context of a type
///   environment (see [evaluateTypeExpr]), and the result is a type
///   representation.
/// * Type representations are objects of the Java class [typeRepType] (or one
///   of its subclasses). These representations are stored on Dart objects, and
///   are used for type testing. In addition, the type representations for
///   classes and functions have an associated type environment. This
///   environment is currently only accessible to the compiler indirectly via
///   [getLocalTypeEnv], but it might need to be exposed in the future for
///   closures.
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
///       empty constructors should take a type representation as their only
///       argument, which they pass to their superconstructors. The constructor
///       for the synthetic base classes (like DartObject, and any other base
///       classes generated for @JavaClasses) should accept and store this type.
///     * The non-synthetic constructors need to receive the actual type
///       parameters for the object under construction, if there are any. The
///       compiler should call [getExtraConstructorDeclArg], which will either
///       return a [VariableDecl] (in which case, this [VariableDecl] should be
///       inserted as the first argument to the constructor) or [:null:] (in
///       which case, no argument should be added to the constructor signature).
///       In either case, the non-synthetic constructor must delegate to the
///       empty constructor, which expects a [typeRepType] as its argument. Call
///       [makeThisTypeInCtor], which returns an [Expression] that should be
///       used as the argument to the empty constructor invocation.
///
/// * When compiling a constructor invocation:
///
///     * There might be an extra parameter needed, which was inserted by the
///       type system (to pass the actual type parameters). Call
///       [getExtraConstructorArg], which will either return an [Expression] or
///       [:null:]. If not null, insert the expression as the first parameter to
///       the constructor call.
///
/// * When compiling a reference to a type (`is` expression, parameterized
///   constructor, etc.):
///
///      * Build the type expression via [makeTypeExpr].
///      * If the type occurs in a top-level environment (such as a top-level
///        variable initializer), retrieve the top-level type environment via
///        [getTopLevelTypeEnv].  Otherwise, if the type occurs inside a class
///        context (including field initializers) or inside a function
///        (including both instance methods and static functions), retrieve the
///        type environment for that context via [getLocalTypeEnv].
///      * Call [evaluateTypeExpr] with these two parameters.
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
    new StringLiteral('${node.enclosingLibrary.importUri}'),
    new StringLiteral(node.name)
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

/// Generate a static intializer block that completes initialization of the
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
            _typeExprType,
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

/// Retrieve the top-level type environment.
///
/// This should be used for evaluating types in a top-level scope, such as a
/// global variable initializer. Most of the time, the compiler should use
/// [getLocalTypeEnv] instead.
Expression getTopLevelTypeEnv() {
  return new FieldAccess(new ClassRefExpr(_typeEnvironmentType), 'ROOT');
}

/// Retrieve the type environment for the current local context.
///
/// Ultimately, every "local context" will be a Java method body (even instance
/// field initializers are moved into a synthetic method body in our current
/// object model; the constructors will call these generated methods). The local
/// type environment will be saved in an instance variable in the function; how
/// that variable is initialized is still TBD.
///
/// This type environment is constant throughout the body of the immediately
/// enclosing function. The environment for nested functions might be an
/// extension of the current function's environment, so it is not generally safe
/// to reuse the outer function's type environment directly in the inner
/// function. For example, in the following Dart code, the expression `Set<S>`
/// must be evaluated in the inner function's type environment:
///
///     void outer<T>(T t) {
///       void inner<S>(S s1, S s2) {
///         foo(new Set<S>([s1, s2]));
///       }
///
///       inner<List<T>>([t], [t, t]);
///     }
// TODO(andrewkrieger): Once function types are implemented, add local type
// environment support (a method for generating and initializing the local
// variable, and appropriate comments here, on that method, and in the library
// documentation comment).
Expression getLocalTypeEnv() {
  return new IdentifierExpr(_localTypeVarName);
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
/// In particular, if [type] does not depend on any type variables, then the
/// expression can be reused in any type environment.
Expression makeTypeExpr(dart.DartType type, CompilerState compilerState) {
  return type.accept(new _TypeExprBuilder(compilerState));
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

class _TypeExprBuilder extends dart.DartTypeVisitor<Expression> {
  final CompilerState compilerState;

  _TypeExprBuilder(this.compilerState);

  @override
  Expression defaultDartType(dart.DartType type) {
    throw new Exception('Unrecognized Dart type: $type');
  }

  @override
  Expression visitInterfaceType(dart.InterfaceType type) {
    var constructorArgs = <Expression>[_makeTypeInfoGetter(type.classNode)];
    if (type.typeArguments.isNotEmpty) {
      constructorArgs.add(new ArrayInitializer(
          _typeExprType,
          type.typeArguments
              .map((dart.DartType t) => t.accept(this) as Expression)
              .toList()));
    }
    return new NewExpr(
        new ClassRefExpr(_interfaceTypeExprType), constructorArgs);
  }

  @override
  Expression visitTypeParameterType(dart.TypeParameterType type) {
    var parent = type.parameter.parent;
    if (parent is dart.Class) {
      var typeInfoGetter = _makeTypeInfoGetter(parent);
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

  Expression _makeTypeInfoGetter(dart.Class forClass) {
    return new FieldAccess(
        new ClassRefExpr(compilerState.hasJavaImpl(forClass)
            ? compilerState.getHelperClass(forClass)
            : compilerState.getClass(forClass)),
        _typeInfoFieldName);
  }
}

// These constants are declared here, rather than in constants., since they are
// type-system specific.
const _localTypeVarName = 'dart2java\$currType';
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
