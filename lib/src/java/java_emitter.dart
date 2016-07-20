// Copyright (c) 2016, the Dart project authors.  Please see the AUTHORS file
// for details. All rights reserved. Use of this source code is governed by a
// BSD-style license that can be found in the LICENSE file.

import 'ast.dart';
import 'visitor.dart';

/// Emits the full file contents for a Java source file defining a top-level
/// Java [Class].
///
/// This includes a package delcaration, imports, and a full Java class
/// definition.
String emitClassDecl(ClassDecl cls) {
  var content = cls.accept(new _JavaAstEmitter());
  return "package ${cls.package};\n\n${content}";
}

/// Emits Java code for a Java AST.
///
/// As a rule, every statement adds its own semicolons if necessary (e.g.,
/// a block does not need semicolons).
class _JavaAstEmitter extends Visitor<String> {
  /// Indents every line with two spaces for readability reasons.
  static String indent(String str) {
    return str.split("\n").map((s) => "  ${s}").join("\n");
  }

  @override
  String visitClassDecl(ClassDecl cls) {
    var fields = cls.fields.map((v) => indent(v.accept(this)) + ";").join("\n");
    var methods = cls.methods.map((m) => indent(m.accept(this))).join("\n");
    var content = indent(fields + "\n\n" + methods);

    return "${cls.access} class ${cls.type.name}\n{\n${content}\n}\n";
  }

  @override
  String visitFieldDecl(FieldDecl decl) {
    var parts = [];
    parts.add(decl.access.toString());
    if (decl.isStatic) parts.add("static");
    if (decl.isFinal) parts.add("final");
    parts.add(decl.type);
    parts.add(decl.name);

    if (decl.initializer != null) {
      parts.add("=");
      parts.add(decl.initializer.accept(this));
    }

    return parts.join(" ");
  }

  @override
  String visitVariableDecl(VariableDecl decl) {
    var parts = [];
    if (decl.isFinal) parts.add("final");
    parts.add(decl.type);
    parts.add(decl.name);

    return parts.join(" ");
  }

  @override
  String visitVariableDeclStmt(VariableDeclStmt decl) {
    return decl.initializer != null
        ? "${decl.variable.accept(this)} = ${decl.initializer.accept(this)};"
        : "${decl.variable.accept(this)};";
  }

  @override
  String visitReturnStmt(ReturnStmt stmt) {
    return stmt.value != null
        ? "return ${stmt.value.accept(this)};"
        : "return;";
  }

  @override
  String visitMethodDef(MethodDef meth) {
    var frontPart = [];
    frontPart.add(meth.access.toString());
    if (meth.isStatic) frontPart.add("static");
    if (meth.isFinal) frontPart.add("final");
    frontPart.add(meth.returnType);
    frontPart.add(meth.name);

    var parameterList =
        "(" + meth.parameters.map((p) => p.accept(this)).join(", ") + ")";

    return frontPart.join(" ") + parameterList + "\n" + meth.body.accept(this);
  }

  @override
  String visitExpressionStmt(ExpressionStmt stmt) {
    return "${stmt.expression.accept(this)};";
  }

  @override
  String visitBlock(Block block) {
    var stmts = block.statements.map((stmt) => stmt.accept(this)).join("\n");
    return "{\n${indent(stmts)}\n}";
  }

  @override
  String visitIfStmt(IfStmt stmt) {
    var conditionCheck = "if (${stmt.condition.accept(this)})\n";
    var thenPart = stmt.thenBody.accept(this);
    var elsePart =
        stmt.elseBody != null ? "\nelse\n${stmt.elseBody.accept(this)}" : "";
    return conditionCheck + thenPart + elsePart;
  }

  @override
  String visitMethodInvocation(MethodInvocation invocation) {
    var receiver = invocation.receiver.accept(this);
    var arguments =
        invocation.arguments.map((arg) => arg.accept(this)).join(", ");
    return "${receiver}.${invocation.methodName}(${arguments})";
  }

  @override
  String visitBinaryExpr(BinaryExpr expr) {
    var left = expr.leftOperand.accept(this);
    var right = expr.rightOperand.accept(this);
    return "(${left} ${expr.operatorSymbol} ${right})";
  }

  @override
  String visitUnaryExpr(UnaryExpr expr) {
    return "(${expr.operatorSymbol}${expr.operand.accept(this)})";
  }

  @override
  String visitCastExpr(CastExpr expr) {
    return "(${expr.type}) ${expr.expression.accept(this)}";
  }

  @override
  String visitIdentifierExpr(IdentifierExpr expr) {
    return expr.identifier;
  }

  @override
  String visitAssignmentExpr(AssignmentExpr expr) {
    return "${expr.identifier.accept(this)} = ${expr.value.accept(this)}";
  }

  @override
  String visitClassRefExpr(ClassRefExpr expr) {
    return expr.className;
  }

  @override
  String visitIntLiteral(IntLiteral literal) {
    return literal.value.toString();
  }

  @override
  String visitDoubleLiteral(DoubleLiteral literal) {
    return literal.value.toString();
  }

  @override
  String visitStringLiteral(StringLiteral literal) {
    return "\"${literal.value}\"";
  }

  @override
  String visitNullLiteral(NullLiteral literal) {
    return "null";
  }

  @override
  String visitJavaType(JavaType type) {
    return type.name;
  }
}
