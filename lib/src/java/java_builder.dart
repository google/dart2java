// Copyright (c) 2016, the Dart project authors.  Please see the AUTHORS file
// for details. All rights reserved. Use of this source code is governed by a
// BSD-style license that can be found in the LICENSE file.

import 'package:kernel/ast.dart' as dart;

import 'ast.dart' as java;
import 'visitor.dart' as java;
import 'constants.dart';
import '../compiler/compiler_state.dart';
import '../compiler/runner.dart' show CompileErrorException;

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
    // TODO(springerm): Use annotation
    var isInterceptor = compilerState.isInterceptorClass(node.name);
    var instance = new JavaAstBuilder(package, compilerState,
        isInterceptorClass: isInterceptor);
    return node.accept(instance);
  }

  JavaAstBuilder(this.package, this.compilerState,
      {this.isInterceptorClass: false}) {
    if (isInterceptorClass) {
      // TODO(springerm): Fix package name
      this.package = "dart._runtime";
    }
  }

  String package;
  String className;
  final CompilerState compilerState;
  final bool isInterceptorClass;

  /// Visits a non-mixin class.
  @override
  java.ClassDecl visitNormalClass(dart.NormalClass node) {
    className = node.name;

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

  String javaMethodName(String methodName, dart.ProcedureKind kind) {
    switch (kind) {
      case dart.ProcedureKind.Method:
        return methodName;
      case dart.ProcedureKind.Operator:
        translatedMethodName = Constants.operatorToMethodName[methodName];
        if (translatedMethodName == null) {
          throw new CompileErrorException(
              "Operator ${methodName} not implemented yet.");
        }
        return translatedMethodName;
      case dart.ProcedureKind.Getter:
        return "get" + methodName;
      default:
        // TODO(springerm): handle remaining kinds
        throw new CompileErrorException(
            "Method kind ${kind} not implemented yet.");
    }
  }

  @override
  java.MethodDef visitProcedure(dart.Procedure node) {
    String methodName = javaMethodName(node.name.name, node.kind);
    dart.FunctionNode functionNode = node.function;
    String returnType = functionNode.returnType.accept(this);
    // TODO: handle named parameters, etc.
    List<java.VariableDecl> parameters =
        functionNode.positionalParameters.map((p) => p.accept(this)).toList();
    var isStatic = node.isStatic;

    java.Statement body;
    if (node.isExternal) {
      // Generate a method call to a static Java method
      // Every external Dart method must be annotated with "javaCall"!
      String externalJavaMethod =
          getSimpleAnnotation(node, Constants.javaCallAnnotation);
      List<String> methodTokens = externalJavaMethod.split(".");
      var extReceiver = new java.ClassRefExpr(
          methodTokens.getRange(0, methodTokens.length - 1).join("."));
      var extMethodName = methodTokens.last;

      List<java.Expression> arguments = [];
      if (!node.isStatic) {
        // First argument is "this"
        arguments
            .add(new java.IdentifierExpr(Constants.javaStaticThisIdentifier));
      }
      // Remaining arguments are parameters of Dart method
      arguments.addAll(parameters.map((p) => new java.IdentifierExpr(p.name)));

      body = new java.ReturnStmt(
          new java.MethodInvocation(extReceiver, extMethodName, arguments));
    } else {
      body = buildStatement(functionNode.body);
    }

    if (isInterceptorClass) {
      // All methods in interceptor classes are static
      isStatic = true;
      parameters.insert(
          0,
          new java.VariableDecl(
              Constants.javaStaticThisIdentifier, getMyJavaClassName()));
    }

    return new java.MethodDef(methodName, wrapInJavaBlock(body), parameters,
        returnType: returnType, isStatic: isStatic, isFinal: false);
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

  /// Builds a Java MethodInvocation node. This method is reused for "normal"
  /// method and for getter/setters etc.
  java.MethodInvocation buildMethodInvocation(dart.Expression receiver,
      String methodName, List<dart.Expression> positionalArguments) {
    // Translate receiver
    java.Expression recv;
    if (receiver == null) {
      recv = buildThisExpression();
    } else {
      recv = receiver.accept(this);
    }

    // TODO(springerm): Handle other argument types
    List<java.Expression> args =
        positionalArguments.map((a) => a.accept(this)).toList();

    // Replace with static method call if necessary
    String receiverType = getType(receiver).toString();
    if (compilerState.javaClasses.containsKey(receiverType)) {
      // Receiver type is an already existing Java class, generate static
      // method call.
      var dartClass = compilerState.interceptorClasses[receiverType];
      var argsWithSelf = []
        ..add(recv)
        ..addAll(args);
      return new java.MethodInvocation(
          buildClassReference(dartClass), methodName, argsWithSelf);
    }

    return new java.MethodInvocation(recv, methodName, args);
  }

  @override
  java.MethodInvocation visitPropertyGet(dart.PropertyGet node) {
    String methodName =
        javaMethodName(node.name.name, dart.ProcedureKind.Getter);
    return buildMethodInvocation(node.receiver, methodName, []);
  }

  @override
  java.MethodInvocation visitMethodInvocation(dart.MethodInvocation node) {
    String name = node.name.name;
    // Expand operator symbol to Java-compatible method name
    name = Constants.operatorToMethodName[name] ?? name;
    return buildMethodInvocation(
        node.receiver, name, node.arguments.positional);
  }

  /// Retrieves the [DartType] for a kernel [Expression] node.
  dart.DartType getType(dart.Expression node) {
    return node.staticType;
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

  /// Converts a Dart class name to a Java class name.
  String getJavaClassName(String dartClassName) {
    return dartClassName.replaceFirst("::", ".");
  }

  /// Build a reference to a Dart class.
  java.ClassRefExpr buildClassReference(String dartClassName) {
    return new java.ClassRefExpr(getJavaClassName(dartClassName));
  }

  /// Build a reference to "this".
  java.IdentifierExpr buildThisExpression() {
    if (isInterceptorClass) {
      // Replace "this" with "self" (first arg.) inside interceptor methods
      return new java.IdentifierExpr(Constants.javaStaticThisIdentifier);
    } else {
      return new java.IdentifierExpr("this");
    }
  }

  /// Returns the fully-qualified Dart class name of the current class.
  String getMyDartClassName() {
    return package + "::" + className;
  }

  /// Returns the fully-qualified Java class name of the current class.
  String getMyJavaClassName() {
    if (isInterceptorClass) {
      return getJavaClassName(compilerState.javaClasses[getMyDartClassName()]);
    } else {
      return getJavaClassName(getMyDartClassName());
    }
  }

  @override
  java.IdentifierExpr visitThisExpression(dart.ThisExpression node) {
    return buildThisExpression();
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
