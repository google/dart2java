// Copyright (c) 2016, the Dart project authors.  Please see the AUTHORS file
// for details. All rights reserved. Use of this source code is governed by a
// BSD-style license that can be found in the LICENSE file.

/// A simple AST designed for generating a single Java source file. It is
/// assumed that errors are caught before generating this AST, so there are no
/// designated "error" or "invalid" nodes.

import 'visitor.dart';

import 'types.dart';
export 'types.dart';

/// Java access specifier
class Access {
  static const Access Public = const Access("public");
  static const Access Protected = const Access("protected");
  static const Access Private = const Access("private");

  /// Package-private or default access. In Java code, this is normally written
  /// by omitting a specifier (e.g. `static void foo() {}` instead of
  /// `public static void foo() {}`). For consistency with the other specifiers,
  /// we use a comment rather than the empty string.
  static const Access Package = const Access("/* package */");

  final String modifier;

  const Access(this.modifier);

  @override
  String toString() {
    return modifier;
  }
}

/// The root class for nodes in the Java AST.
abstract class Node {
  /*=R*/ accept/*<R>*/(Visitor/*<R>*/ v);

  /// [toString] is only meant for debugging purposes. For Java code generation,
  /// use an AST visitor.
  String toString() => '[$runtimeType]';

  const Node();
}

/// A Java class declaration.
///
/// Each AST should be rooted at a Java class (since we produce exactly one
/// class per source file). Java also allows for nested and local classes, so
/// this node may appear deeper within the tree as well.
class ClassDecl extends Node {
  /// The [JavaType] that this class declaration corresponds to.
  ///
  /// This includes the package and name of the class and the type parameters.
  ClassOrInterfaceType type;

  ClassOrInterfaceType supertype;

  Access access;

  bool isAbstract;

  List<MethodDef> methods;

  /// The initializers for this class; order matters for these elements!
  ///
  /// Currently, this list includes both the fields in the class (which may have
  /// initializers) and the initializer blocks.
  List<OrderedClassMember> orderedMembers;

  List<Constructor> constructors;

  ClassDecl(this.type,
      {this.access: Access.Public, this.orderedMembers, this.methods, 
        this.constructors, this.supertype, this.isAbstract: false}) {
    // Initialize ClassDecl with (non-const!) empty lists for fields and methods
    methods ??= <MethodDef>[];
    orderedMembers ??= <OrderedClassMember>[];
    constructors ??= <Constructor>[];
    supertype ??= JavaType.dartObject;
  }

  @override
  /*=R*/ accept/*<R>*/(Visitor/*<R>*/ v) => v.visitClassDecl(this);

  String toString() => '${access} class ${type.name}';
}

/// A Java method.
class MethodDef extends Node {
  /// Method name
  String name;

  /// Represent the list of statements, i.e., the method body.
  Block body;

  /// Define the parameter names and types.
  List<VariableDecl> parameters;

  /// Define the return type of the method.
  JavaType returnType;

  bool isStatic;

  bool isFinal;

  bool isAbstract;

  Access access;

  MethodDef(this.name, this.body, this.parameters,
      {this.returnType: JavaType.void_,
      this.isStatic: false,
      this.isFinal: false,
      this.isAbstract: false,
      this.access: Access.Public});

  @override
  /*=R*/ accept/*<R>*/(Visitor/*<R>*/ v) => v.visitMethodDef(this);
}

/// A class constructor.
class Constructor extends Node {
  /// The type of the class.
  ClassOrInterfaceType classType;

  /// Represent the list of statements, i.e., the method body.
  Block body;

  /// Define the parameter names and types.
  List<VariableDecl> parameters;

  Constructor(this.classType, this.body, this.parameters);

  @override
  /*=R*/ accept/*<R>*/(Visitor/*<R>*/ v) => v.visitConstructor(this);
}

/// A common subtype for members of a Java class whose declaration order has an
/// effect on program behavior (fields, initializaer blocks, and static
/// initializer blocks).
abstract class OrderedClassMember extends Node {}

/// An instance variable declaration for fields.
class FieldDecl extends OrderedClassMember {
  String name;
  JavaType type;
  Access access;
  bool isStatic;
  bool isFinal;
  Expression initializer;

  FieldDecl(this.name, this.type,
      {this.access: Access.Private,
      this.isStatic: false,
      this.isFinal: false,
      this.initializer});

  @override
  /*=R*/ accept/*<R>*/(Visitor/*<R>*/ v) => v.visitFieldDecl(this);
}

/// A Java static/instance initializer block.
///
/// If this is a static initializer block, then it is executed while the class
/// is being loaded (see the JLS ed. 8 section 12.4 for the gory details). If
/// this is an instance initializer, it is effectively added to the beginning of
/// every constructor in the class.
class InitializerBlock extends OrderedClassMember {
  bool isStatic;
  Block block;

  InitializerBlock(this.block, {this.isStatic: false});

  @override
  /*=R*/ accept/*<R>*/(Visitor/*<R>*/ v) => v.visitInitializerBlock(this);
}

/// A variable declaration for parameters and local variables.
class VariableDecl extends Node {
  String name;
  JavaType type;
  bool isFinal;
  Expression initializer;

  VariableDecl(this.name, this.type, {this.isFinal: false, this.initializer});

  @override
  /*=R*/ accept/*<R>*/(Visitor/*<R>*/ v) => v.visitVariableDecl(this);
}

/// Abstract class for statements.
abstract class Statement extends Node {}

/// A list of statements enclosed in a block.
class Block extends Statement {
  List<Statement> statements;

  Block([this.statements = const []]);

  @override
  /*=R*/ accept/*<R>*/(Visitor/*<R>*/ v) => v.visitBlock(this);
}

/// A statement wrapping a variable declaration.
class VariableDeclStmt extends Statement {
  VariableDecl decl;

  VariableDeclStmt(this.decl);

  @override
  /*=R*/ accept/*<R>*/(Visitor/*<R>*/ v) => v.visitVariableDeclStmt(this);
}

/// An if statement. The else (false) part is optional.
class IfStmt extends Statement {
  Expression condition;

  // Allow only blocks to avoid ambiguous else part in nested if statements
  Block thenBody;

  Block elseBody;

  /// TODO(springerm) Add support for "else if"

  IfStmt(this.condition, this.thenBody, [this.elseBody]);

  @override
  /*=R*/ accept/*<R>*/(Visitor/*<R>*/ v) => v.visitIfStmt(this);
}

class WhileStmt extends Statement {
  Expression condition;

  Block body;

  WhileStmt(this.condition, this.body);

  @override
  /*=R*/ accept/*<R>*/(Visitor/*<R>*/ v) => v.visitWhileStmt(this);
}

class DoStmt extends Statement {
  Expression condition;

  Block body;

  DoStmt(this.condition, this.body);

  @override
  /*=R*/ accept/*<R>*/(Visitor/*<R>*/ v) => v.visitDoStmt(this);
}

class ForStmt extends Statement {
  List<VariableDecl> variableDeclarations;

  Expression condition;

  List<Expression> updates;

  Block body;

  ForStmt(this.variableDeclarations, this.condition, this.updates, this.body);

  @override
  /*=R*/ accept/*<R>*/(Visitor/*<R>*/ v) => v.visitForStmt(this);
}

/// A method return statement.
class ReturnStmt extends Statement {
  Expression value;

  ReturnStmt([this.value]);

  @override
  /*=R*/ accept/*<R>*/(Visitor/*<R>*/ v) => v.visitReturnStmt(this);
}

/// A throw statement.
class ThrowStmt extends Statement {
  Expression value;

  ThrowStmt(this.value);

  @override
  /*=R*/ accept/*<R>*/(Visitor/*<R>*/ v) => v.visitThrowStmt(this);
}

/// An expression that acts as a statement.
class ExpressionStmt extends Statement {
  Expression expression;

  ExpressionStmt(this.expression);

  @override
  /*=R*/ accept/*<R>*/(Visitor/*<R>*/ v) => v.visitExpressionStmt(this);
}

/// Abstract class for expressions.
abstract class Expression extends Node {}

/// A [JavaType] as an expression.
class TypeExpr extends Expression {
  JavaType type;

  TypeExpr(this.type);

  @override
  /*=R*/ accept/*<R>*/(Visitor/*<R>*/ v) => v.visitTypeExpr(this);
}

class NewExpr extends Expression {
  ClassRefExpr classRef;

  List<Expression> arguments;

  NewExpr(this.classRef, [this.arguments = const <Expression>[]]);

  @override
  /*=R*/ accept/*<R>*/(Visitor/*<R>*/ v) => v.visitNewExpr(this);
}

/// A field access node.
class FieldAccess extends Expression {
  Expression receiver;

  String name;

  FieldAccess(this.receiver, this.name);

  @override
  /*=R*/ accept/*<R>*/(Visitor/*<R>*/ v) => v.visitFieldAccess(this);
}

/// A method invocation.
class MethodInvocation extends Expression {
  Expression receiver;

  String methodName;

  List<Expression> arguments;

  MethodInvocation(this.receiver, this.methodName, [this.arguments = const []]);

  @override
  /*=R*/ accept/*<R>*/(Visitor/*<R>*/ v) => v.visitMethodInvocation(this);
}

/// A super method invocation.
class SuperMethodInvocation extends Expression {
  String methodName;

  List<Expression> arguments;

  SuperMethodInvocation(this.methodName, [this.arguments = const []]);

  @override
  /*=R*/ accept/*<R>*/(Visitor/*<R>*/ v) => v.visitSuperMethodInvocation(this); 
}

/// Invokes a constructor in the superclass.
class SuperConstructorInvocation extends Statement {
  // TODO(springerm): Add support for named constructors
  List<Expression> arguments;

  SuperConstructorInvocation(this.arguments);

  @override
  /*=R*/ accept/*<R>*/(Visitor/*<R>*/ v) => 
  v.visitSuperConstructorInvocation(this);
}

/// An array access expression, i.e. `x[i]`.
class ArrayAccess extends Expression {
  Expression receiver;
  Expression indexExpr;

  ArrayAccess(this.receiver, this.indexExpr);

  @override
  /*=R*/ accept/*<R>*/(Visitor/*<R>*/ v) => v.visitArrayAccess(this);
}

/// A binary expression (e.g., arithmetic operators).
class BinaryExpr extends Expression {
  Expression leftOperand;

  Expression rightOperand;

  String operatorSymbol;

  BinaryExpr(this.leftOperand, this.rightOperand, this.operatorSymbol);

  @override
  /*=R*/ accept/*<R>*/(Visitor/*<R>*/ v) => v.visitBinaryExpr(this);
}

/// A unary expression (e.g., negation of numbers).
class UnaryExpr extends Expression {
  Expression operand;

  String operatorSymbol;

  UnaryExpr(this.operand, this.operatorSymbol);

  @override
  /*=R*/ accept/*<R>*/(Visitor/*<R>*/ v) => v.visitUnaryExpr(this);
}

/// A conditional (ternary) expression.
class ConditionalExpr extends Expression {
  Expression condition;

  Expression thenExpr;

  Expression elseExpr;

  ConditionalExpr(this.condition, this.thenExpr, this.elseExpr);

  @override
  /*=R*/ accept/*<R>*/(Visitor/*<R>*/ v) => v.visitConditionalExpr(this);  
}

/// A type cast.
class CastExpr extends Expression {
  Expression expression;

  JavaType type;

  CastExpr(this.expression, this.type);

  @override
  /*=R*/ accept/*<R>*/(Visitor/*<R>*/ v) => v.visitCastExpr(this);
}

/// References an identifier such as a local variable.
class IdentifierExpr extends Expression {
  String identifier;

  IdentifierExpr(this.identifier);

  @override
  /*=R*/ accept/*<R>*/(Visitor/*<R>*/ v) => v.visitIdentifierExpr(this);
}

/// An expression that writes to a local variable.
class AssignmentExpr extends Expression {
  // Can be an [IdentifierExpr] or a [FieldAccess]
  Expression identifier;

  Expression value;

  AssignmentExpr(this.identifier, this.value);

  @override
  /*=R*/ accept/*<R>*/(Visitor/*<R>*/ v) => v.visitAssignmentExpr(this);
}

/// A class reference. This node can be used as receiver expression when
/// calling static methods.
class ClassRefExpr extends Expression {
  ClassOrInterfaceType type;

  ClassRefExpr(this.type);

  @override
  /*=R*/ accept/*<R>*/(Visitor/*<R>*/ v) => v.visitClassRefExpr(this);
}

/// Abstract class for literals.
abstract class Literal extends Expression {}

/// A boolean literal.
class BoolLiteral extends Literal {
  bool value;

  BoolLiteral(this.value);

  @override
  /*=R*/ accept/*<R>*/(Visitor/*<R>*/ v) => v.visitBoolLiteral(this);
}

/// An integer literal.
class IntLiteral extends Literal {
  int value;

  IntLiteral(this.value);

  @override
  /*=R*/ accept/*<R>*/(Visitor/*<R>*/ v) => v.visitIntLiteral(this);
}

/// A double literal.
class DoubleLiteral extends Literal {
  double value;

  DoubleLiteral(this.value);

  @override
  /*=R*/ accept/*<R>*/(Visitor/*<R>*/ v) => v.visitDoubleLiteral(this);
}

/// A string literal.
class StringLiteral extends Literal {
  String value;

  StringLiteral(this.value);

  @override
  /*=R*/ accept/*<R>*/(Visitor/*<R>*/ v) => v.visitStringLiteral(this);
}

class NullLiteral extends Literal {
  static final instance = new NullLiteral._();
  NullLiteral._();

  @override
  /*=R*/ accept/*<R>*/(Visitor/*<R>*/ v) => v.visitNullLiteral(this);

  factory NullLiteral() {
    return instance;
  }
}

class ArrayInitializer extends Expression {
  JavaType type;
  List<Expression> initializers;

  ArrayInitializer(this.type, List<Expression> initializers)
    : initializers = initializers ?? const[];

  @override
  /*=R*/ accept/*<R>*/(Visitor/*<R>*/ v) => v.visitArrayInitializer(this);
}
