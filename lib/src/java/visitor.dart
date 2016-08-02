// Copyright (c) 2016, the Dart project authors.  Please see the AUTHORS file
// for details. All rights reserved. Use of this source code is governed by a
// BSD-style license that can be found in the LICENSE file.

import 'ast.dart';

/// A Java AST visitor. Subclasses need only override the methods that they are
/// interested in.
abstract class Visitor<R> {
  // Declarations
  R visitClassDecl(ClassDecl node) => null;
  R visitMethodDef(MethodDef node) => null;
  R visitFieldDecl(FieldDecl node) => null;
  R visitVariableDecl(VariableDecl node) => null;

  // Statements
  R visitBlock(Block node) => null;
  R visitIfStmt(IfStmt node) => null;
  R visitVariableDeclStmt(VariableDeclStmt node) => null;
  R visitReturnStmt(ReturnStmt node) => null;
  R visitExpressionStmt(ExpressionStmt node) => null;

  // Expressions
  R visitNewExpr(NewExpr node) => null;
  R visitFieldRead(FieldRead node) => null;
  R visitMethodInvocation(MethodInvocation node) => null;
  R visitBinaryExpr(BinaryExpr node) => null;
  R visitUnaryExpr(UnaryExpr node) => null;
  R visitConditionalExpr(ConditionalExpr node) => null;
  R visitCastExpr(CastExpr node) => null;
  R visitIdentifierExpr(IdentifierExpr node) => null;
  R visitAssignmentExpr(AssignmentExpr node) => null;
  R visitClassRefExpr(ClassRefExpr node) => null;
  R visitBoolLiteral(BoolLiteral node) => null;
  R visitIntLiteral(IntLiteral node) => null;
  R visitDoubleLiteral(DoubleLiteral node) => null;
  R visitStringLiteral(StringLiteral node) => null;
  R visitNullLiteral(NullLiteral node) => null;

  // Types
  R visitJavaType(JavaType node) => null;
}
