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
  String content = cls.accept(new _JavaAstEmitter());
  return "package ${cls.type.package};\n\n${content}";
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
    var constructors = cls.constructors.map((m) => 
      indent(m.accept(this))).join("\n");
    var content = indent(fields + "\n\n" + constructors + "\n\n" + methods);
    var extendsClause = cls.supertype == null
        ? ""
        : " extends ${cls.supertype.fullyQualifiedName}";
    var abstractClause = cls.isAbstract ? "abstract " : " ";

    return "${cls.access} ${abstractClause}class ${cls.type.name}${extendsClause}"
      "\n{\n${content}\n}\n";
  }

  @override
  String visitFieldDecl(FieldDecl decl) {
    var parts = <String>[];
    parts.add(decl.access.toString());
    if (decl.isStatic) parts.add("static");
    if (decl.isFinal) parts.add("final");
    parts.add(decl.type.accept(this));
    parts.add(decl.name);

    if (decl.initializer != null) {
      parts.add("=");
      parts.add(decl.initializer.accept(this));
    }

    return parts.join(" ");
  }

  @override
  String visitVariableDecl(VariableDecl decl) {
    var parts = <String>[];
    if (decl.isFinal) parts.add("final");
    parts.add(decl.type.accept(this));
    parts.add(decl.name);

    if (decl.initializer != null) {
      parts.add("=");
      parts.add(decl.initializer.accept(this));
    }

    return parts.join(" ");
  }

  @override
  String visitReturnStmt(ReturnStmt stmt) {
    return stmt.value != null
        ? "return ${stmt.value.accept(this)};"
        : "return;";
  }

  @override
  String visitMethodDef(MethodDef meth) {
    var frontPart = <String>[];
    frontPart.add(meth.access.toString());
    if (meth.isStatic) frontPart.add("static");
    if (meth.isFinal) frontPart.add("final");
    if (meth.isAbstract) frontPart.add("abstract");
    frontPart.add(meth.returnType.accept(this));
    frontPart.add(meth.name);

    var parameterList =
        "(" + meth.parameters.map((p) => p.accept(this)).join(", ") + ")";

    var methodBody = meth.isAbstract
      ? ";"
      : "\n" + meth.body.accept(this);

    return frontPart.join(" ") + parameterList + methodBody;
  }

  @override
  String visitConstructor(Constructor constr) {
    String classType = constr.classType.name;

    var parameterList =
        "(" + constr.parameters.map((p) => p.accept(this)).join(", ") + ")";

    return "public $classType" 
      + parameterList 
      + "\n" 
      + constr.body.accept(this);
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
  String visitVariableDeclStmt(VariableDeclStmt stmt) {
    return "${stmt.decl.accept(this)};";
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
  String visitTypeExpr(TypeExpr expr) {
    return "${expr.type.toString()}.class";
  }

  @override
  String visitWhileStmt(WhileStmt stmt) {
    var conditionCheck = "while (${stmt.condition.accept(this)})\n";
    var body = stmt.body.accept(this);
    return conditionCheck + body;
  }

  @override
  String visitDoStmt(DoStmt stmt) {
    var conditionCheck = "\nwhile (${stmt.condition.accept(this)});";
    var body = stmt.body.accept(this);
    return "do\n" + body + conditionCheck;
  }

  @override
  String visitForStmt(ForStmt stmt) {
    // TODO(springerm): Multiple VarDecls are currently not emitted correctly.
    // The type should only be emitted once (e.g. not: int a = 1, int b = 2).
    var varDecls = stmt.variableDeclarations.map((d) => d.accept(this))
      .join(", ");
    var updates = stmt.updates.map((u) => u.accept(this)).join(", ");
    var condition = stmt.condition.accept(this);
    var body = stmt.body.accept(this);
    return "for ($varDecls; $condition; $updates)\n$body";
  }
  
  @override
  String visitNewExpr(NewExpr expr) {
    var className = expr.classRef.accept(this);
    var arguments =
        expr.arguments.map((arg) => arg.accept(this)).join(", ");
    return "new ${className}(${arguments})";
  }

  @override
  String visitFieldAccess(FieldAccess expr) {
    var receiver = expr.receiver.accept(this);
    var identifier = expr.identifier.accept(this);
    return "${receiver}.${identifier}";
  }

  @override
  String visitMethodInvocation(MethodInvocation invocation) {
    var receiver = invocation.receiver.accept(this);
    var arguments =
        invocation.arguments.map((arg) => arg.accept(this)).join(", ");
    return "${receiver}.${invocation.methodName}(${arguments})";
  }

  @override
  String visitSuperMethodInvocation(SuperMethodInvocation invocation) {
    var arguments =
        invocation.arguments.map((arg) => arg.accept(this)).join(", ");
    return "super.${invocation.methodName}(${arguments})";
  }

  @override
  String visitSuperConstructorInvocation(
    SuperConstructorInvocation invocation) {
    var arguments =
      invocation.arguments.map((arg) => arg.accept(this)).join(", ");
    return "super(${arguments})";
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
  String visitConditionalExpr(ConditionalExpr expr) {
    var condition = expr.condition.accept(this);
    var thenExpr = expr.thenExpr.accept(this);
    var elseExpr = expr.elseExpr.accept(this);
    return "($condition) ? ($thenExpr) : ($elseExpr)";
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
    return expr.type.accept(this);
  }

  @override
  String visitBoolLiteral(BoolLiteral literal) {
    return literal.value.toString();
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
    return type.toString();
  }
}
