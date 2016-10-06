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

import 'ast.dart';

/// A Java AST visitor. Subclasses need only override the methods that they are
/// interested in.
abstract class Visitor<R> {
  // Declarations
  R visitClassDecl(ClassDecl node) => null;
  R visitInterfaceDecl(InterfaceDecl node) => null;
  R visitMethodDef(MethodDef node) => null;
  R visitMethodDecl(MethodDecl node) => null;
  R visitConstructor(Constructor node) => null;
  R visitFieldDecl(FieldDecl node) => null;
  R visitInitializerBlock(InitializerBlock node) => null;
  R visitVariableDecl(VariableDecl node) => null;

  // Statements
  R visitBlock(Block node) => null;
  R visitVariableDeclStmt(VariableDeclStmt node) => null;
  R visitIfStmt(IfStmt node) => null;
  R visitWhileStmt(WhileStmt node) => null;
  R visitDoStmt(DoStmt node) => null;
  R visitForStmt(ForStmt node) => null;
  R visitBreakStmt(BreakStmt node) => null;
  R visitLabeledStmt(LabeledStmt node) => null;
  R visitReturnStmt(ReturnStmt node) => null;
  R visitThrowStmt(ThrowStmt node) => null;
  R visitExpressionStmt(ExpressionStmt node) => null;

  // Expressions
  R visitTypeExpr(TypeExpr node) => null;
  R visitNewExpr(NewExpr node) => null;
  R visitFieldAccess(FieldAccess node) => null;
  R visitMethodInvocation(MethodInvocation node) => null;
  R visitSuperMethodInvocation(SuperMethodInvocation node) => null;
  R visitSuperConstructorInvocation(SuperConstructorInvocation node) => null;
  R visitArrayAccess(ArrayAccess node) => null;
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
  R visitArrayInitializer(ArrayInitializer node) => null;

  // Types
  R visitJavaType(JavaType node) => null;
}
