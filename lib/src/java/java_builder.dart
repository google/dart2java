// Copyright (c) 2016, the Dart project authors.  Please see the AUTHORS file
// for details. All rights reserved. Use of this source code is governed by a
// BSD-style license that can be found in the LICENSE file.

import 'package:kernel/ast.dart' as dart;

import 'ast.dart' as java;
import 'visitor.dart' as java;
import 'constants.dart';
import '../compiler/compiler_state.dart';

/// Builds a Java class from Dart IR.
///
/// Clients should only call static methods on this class. The fact that
/// this class is a [dart.Visitor] is an implementation detail that callers must
/// not rely on.
class JavaAstBuilder extends dart.Visitor {
  /// Builds a Java class that contains the top-level procedures and fields in
  /// a Dart [Library].
  static java.ClassDecl buildWrapperClass(
      String package, String className, dart.Library library) {
    java.ClassDecl result =
        new java.ClassDecl(package, className, java.Access.Public);
    // TODO(andrewkrieger): visit the Dart library's procedures and fields;
    // add corresponding methods and fields to the Java class.
    return result;
  }

  /// Builds a Java class AST from a kernel class AST.
  static java.ClassDecl buildClass(
      String package, dart.Class node, CompilerState compilerState) {
    var instance = new JavaAstBuilder(package, compilerState);
    return node.accept(instance);
  }

  JavaAstBuilder(this.package, this.compilerState);

  final String package;
  final CompilerState compilerState;

  /// Visits a non-mixin class.
  @override
  java.ClassDecl visitNormalClass(dart.NormalClass node) {
    List<java.FieldDecl> fields =
        node.fields.map((f) => f.accept(this)).toList();
    List<java.MethodDef> methods =
        node.procedures.map((m) => m.accept(this)).toList();

    return new java.ClassDecl(
        package, node.name, java.Access.Public, fields, methods);
  }

  @override
  java.FieldDecl visitField(dart.Field node) {
    return new java.FieldDecl(node.name.name, node.type.accept(this),
        initializer: node.initializer?.accept(this),
        access: java.Access.Public,
        isStatic: node.isStatic,
        isFinal: node.isFinal);
  }

  @override
  java.MethodDef visitProcedure(dart.Procedure node) {
    // TODO: handle other kinds (e.g., getters)
    assert(node.kind == dart.ProcedureKind.Method);

    dart.FunctionNode functionNode = node.function;
    String returnType = functionNode.returnType.accept(this);
    // TODO: handle named parameters, etc.
    List<java.VariableDecl> parameters =
        functionNode.positionalParameters.map((p) => p.accept(this)).toList();
    java.Block body = wrapInJavaBlock(buildStatement(functionNode.body));

    return new java.MethodDef(node.name.name, body, parameters,
        returnType: returnType, isStatic: node.isStatic, isFinal: false);
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
  java.IfStmt visitIfStatement(dart.IfStatement node) {
    return new java.IfStmt(
        node.condition.accept(this),
        wrapInJavaBlock(buildStatement(node.then)),
        node.otherwise == null
            ? null
            : wrapInJavaBlock(buildStatement(node.otherwise)));
  }

  @override
  java.ReturnStmt visitReturnStatement(dart.ReturnStatement node) {
    return new java.ReturnStmt(node.expression?.accept(this));
  }

  @override
  java.ExpressionStmt visitExpressionStatement(dart.ExpressionStatement node) {
    return new java.ExpressionStmt(node.expression.accept(this));
  }

  @override
  java.MethodInvocation visitMethodInvocation(dart.MethodInvocation node) {
    java.Expression recv = node.receiver.accept(this);
    String name = node.name;
    List<java.Expression> args = node.arguments.positional.map((a) => a.accept(this)).toList();

    // Expand operator symbol to Java-compatible method name
    name = Constants.operatorToMethodName[name] ?? name;

    String receiverType = getType(node.receiver).toString();
    if (compilerState.javaClasses.containsKey(receiverType)) {
      // Receiver type is an already existing Java class, genereate static
      // method call.
      var dartClass = compilerState.implementationClasses[receiverType];
      var argsWithSelf = []
        ..add(recv)
        ..addAll(args);
      return new java.MethodInvocation(dartClass, name, argsWithSelf);
    }

    return new java.MethodInvocation(recv, name, args);
  }

  /// Retrieves the [DartType] for a kernel [Expression] node.
  dart.DartType getType(dart.Expression node) {
    // TODO(andrewkrieger): Return DartType for kernel expression node
    return null;
  }

  @override
  java.IdentifierExpr visitVariableGet(dart.VariableGet node) {
    return new java.IdentifierExpr(node.variable.name);
  }

  @override
  java.AssignmentExpr visitVariableSet(dart.VariableSet node) {
    return new java.AssignmentExpr(
        new java.IdentifierExpr(node.variable.name), node.value.accept(this));
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
      // Our AST has VariableDecl and VariableDeclStmt and we want to keep
      // them separate. Kernel AST has only VariableDeclaration as a statement.
      // That kernel class translates to VariableDecl in our builder, but
      // when we expect a statement, we wrap it in a VariableDeclStmt.
      var decl = result as java.VariableDecl;
      return new java.VariableDeclStmt(decl, node.initializer?.accept(this));
    } else {
      return result;
    }
  }

  /// This is the default visitor method for DartType.
  @override
  String defaultDartType(dart.DartType node) {
    String typeName = node.toString();
    if (compilerState.javaClasses.containsKey(typeName)) {
      // Reuse a Java type
      return compilerState.javaClasses[typeName];
    } else {
      // TODO(springerm): generate proper types
      return typeName;
    }
  }

  @override
  java.VariableDecl visitVariableDeclaration(dart.VariableDeclaration node) {
    return new java.VariableDecl(node.name, node.type.accept(this),
        isFinal: node.isFinal);
  }
}
