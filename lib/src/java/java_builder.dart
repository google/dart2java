// Copyright (c) 2016, the Dart project authors.  Please see the AUTHORS file
// for details. All rights reserved. Use of this source code is governed by a
// BSD-style license that can be found in the LICENSE file.

import 'package:kernel/ast.dart' as dart;

import 'ast.dart' as java;
import 'visitor.dart' as java;
import 'constants.dart';
import '../compiler/compiler_state.dart';
import '../compiler/runner.dart' show CompileErrorException;

/// Builds a Java class that contains the top-level procedures and fields in
/// a Dart [Library].
java.ClassDecl buildWrapperClass(String package, String className,
    dart.Library library, CompilerState compilerState) {
  var type = new java.ClassOrInterfaceType(className);
  var result = new java.ClassDecl(package, type, java.Access.Public, [], []);

  var instance = new _JavaAstBuilder(package, compilerState);
  result.fields = library.fields.map((f) => f.accept(instance)).toList();
  result.methods = library.procedures.map((p) => p.accept(instance)).toList();

  return result;
}

/// Builds a Java class AST from a kernel class AST.
List<java.ClassDecl> buildClass(
    String package, dart.Class node, CompilerState compilerState) {
  // Nothing to do for core abstract classes like `int`.
  if (compilerState.interfaceOnlyCoreClasses.contains(node)) {
    return const <java.ClassDecl>[];
  }

  // TODO(springerm): Use annotation
  var isInterceptor = compilerState.isInterceptorClass(node);
  var instance = new _JavaAstBuilder(package, compilerState,
      isInterceptorClass: isInterceptor);
  return [node.accept(instance)];
}

/// Builds a Java class from Dart IR.
class _JavaAstBuilder extends dart.Visitor<java.Node> {
  _JavaAstBuilder(this.package, this.compilerState,
      {this.isInterceptorClass: false}) {
    if (isInterceptorClass) {
      // TODO(springerm): Fix package name
      this.package = "dart._runtime";
    }
  }

  /// The package that the [java.ClassDecl] being build should be declared in.
  String package;

  /// A reference to the [dart.Class] that is being visited.
  ///
  /// This must be [:null:] before [visitNormalClass] is called, and may be null
  /// afterwards. If it is not [:null:] before calling [visitNormalClass], then
  /// this builder assumes that it is being reused, and throws an error. If it
  /// is [:null:] while building a [dart.Member], then that [dart.Member] is a
  /// top-level field or function in a [dart.Library].
  ///
  /// This reference is used to compute the [dart.DartType] of [:this:] inside
  /// of method definitions.
  dart.Class thisDartClass;

  final CompilerState compilerState;

  /// If this class is an interceptor class, then all its instance methods
  /// should actually be generated as static Java methods (with an extra `self`
  /// parameter).
  ///
  /// This is used for methods added to @JavaClass classes like [int] or
  /// [String]. The methods may have a Dart definition or a @JavaCall metadata.
  /// The compiler will call the generated Java (static) method whenever the
  /// original Dart (instance) method is invoked.
  final bool isInterceptorClass;

  /// Need to know if this is a static method to generate correct
  /// implicit "this".
  bool isInsideStaticMethod;

  /// Visits a non-mixin class.
  @override
  java.ClassDecl visitNormalClass(dart.NormalClass node) {
    assert(thisDartClass == null);

    var type = new java.ClassOrInterfaceType(node.name);
    // TODO(stanm): add type to symbol table once we have a symbol table.

    var result = new java.ClassDecl(package, type, java.Access.Public, [], []);
    thisDartClass = node;

    for (var f in node.fields) {
      result.fields.add(f.accept(this));
    }
    for (var p in node.procedures) {
      result.methods.add(p.accept(this));
    }

    return result;
  }

  @override
  java.FieldDecl visitField(dart.Field node) {
    return new java.FieldDecl(node.name.name, node.type.accept(this),
        initializer: node.initializer?.accept(this),
        access: java.Access.Public,
        isStatic: node.isStatic,
        isFinal: node.isFinal);
  }

  String capitalizeString(String str) =>
      str[0].toUpperCase() + str.substring(1);

  String javaMethodName(String methodName, dart.ProcedureKind kind) {
    switch (kind) {
      case dart.ProcedureKind.Method:
        return methodName;
      case dart.ProcedureKind.Operator:
        var translatedMethodName = Constants.operatorToMethodName[methodName];
        if (translatedMethodName == null) {
          throw new CompileErrorException(
              "Operator ${methodName} not implemented yet.");
        }
        return translatedMethodName;
      case dart.ProcedureKind.Getter:
        return "get" + capitalizeString(methodName);
      default:
        // TODO(springerm): handle remaining kinds
        throw new CompileErrorException(
            "Method kind ${kind} not implemented yet.");
    }
  }

  @override
  java.MethodDef visitProcedure(dart.Procedure procedure) {
    String methodName = javaMethodName(procedure.name.name, procedure.kind);
    java.JavaType returnType = procedure.function.returnType.accept(this);
    // TODO(springerm): handle named parameters, etc.
    List<java.VariableDecl> parameters = procedure.function.positionalParameters
        .map((p) => p.accept(this))
        .toList();
    var isStatic = procedure.isStatic;

    java.Statement body;
    if (procedure.isExternal) {
      // Generate a method call to a static Java method.
      // Every external Dart method must be annotated with "JavaCall".
      // TODO(stanm): add check.
      String externalJavaMethod =
          getSimpleAnnotation(procedure, Constants.javaCallAnnotation);
      List<String> methodTokens = externalJavaMethod.split(".");
      var extReceiver = new java.ClassRefExpr(
          methodTokens.getRange(0, methodTokens.length - 1).join("."));
      var extMethodName = methodTokens.last;

      List<java.Expression> arguments = [];
      if (!isStatic) {
        // First argument is "this"
        arguments
            .add(new java.IdentifierExpr(Constants.javaStaticThisIdentifier));
      }
      // Remaining arguments are parameters of Dart method
      arguments.addAll(parameters.map((p) => new java.IdentifierExpr(p.name)));

      if (procedure.function.returnType is dart.VoidType) {
        body = new java.ExpressionStmt(
            new java.MethodInvocation(extReceiver, extMethodName, arguments));
      } else {
        body = new java.ReturnStmt(
            new java.MethodInvocation(extReceiver, extMethodName, arguments));
      }
    } else {
      assert(isInsideStaticMethod == null);
      isInsideStaticMethod = isStatic;
      body = buildStatement(procedure.function.body);
      isInsideStaticMethod = null;
    }

    if (isInterceptorClass) {
      // All methods in interceptor classes are static
      isStatic = true;
      parameters.insert(
          0,
          new java.VariableDecl(
              Constants.javaStaticThisIdentifier, getThisClassJavaType()));
    }

    if (methodName == "main" && procedure.enclosingClass == null) {
      if (parameters.length > 1) {
        throw new CompileErrorException(
            'Not implemented yet: Cannot handle main functions with more than'
            ' one argument.');
      }
      if (parameters.length == 1 &&
          parameters[0].type != java.JavaType.object) {
        throw new CompileErrorException(
            'Not implemented yet: Cannot handle main functions with an argument'
            ' that is not of dynamic type.');
      }

      var stringArrayType = new java.ArrayType(java.JavaType.string, 1);
      if (parameters.length == 0) {
        parameters.add(new java.VariableDecl("args", stringArrayType));
      } else {
        parameters[0].type = stringArrayType;
      }

      // Ignore the declared/dynamic return type of main and set it to void.
      // TODO(stanm): make sure there are no odd return statements in body, e.g.
      // `return 1337;`: return values from main are ignored in Dart, as it is
      // assumed that main has a `void` type.
      returnType = java.JavaType.void_;
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
  ///
  /// [receiverType] is used to intercept the method invocation and replace it
  /// by a static call to an interceptor class. If it is known that the method
  /// will not be intercepted (e.g., because the method being invoked is a
  /// static method, so the receiver is a [java.ClassRefExpr]), then
  /// [receiverType] may be null.
  java.MethodInvocation buildMethodInvocation(
      java.Expression receiver,
      dart.DartType receiverType,
      String methodName,
      List<dart.Expression> positionalArguments) {
    // TODO(springerm): Handle other argument types
    List<java.Expression> args =
        positionalArguments.map((a) => a.accept(this)).toList();

    // Replace with static method call if necessary
    if (receiverType is dart.InterfaceType &&
        compilerState.interceptorClasses.containsKey(receiverType.classNode)) {
      // Receiver type is an already existing Java class, generate static
      // method call.
      var dartClass = compilerState.interceptorClasses[receiverType.classNode];
      var argsWithSelf = [receiver]..addAll(args);
      return new java.MethodInvocation(
          buildClassReference(dartClass), methodName, argsWithSelf);
    }

    return new java.MethodInvocation(receiver, methodName, args);
  }

  /// Builds a method invocation where the call target is not statically known.
  java.MethodInvocation buildDynamicMethodInvocation(dart.Expression receiver,
      String methodName, List<dart.Expression> positionalArguments) {
    // Translate receiver
    java.Expression recv;
    dart.DartType recvType;
    if (receiver == null) {
      // Implicit "this" receiver
      recv = buildDefaultReceiver();
      recvType = getThisClassDartType();
    } else {
      recv = receiver.accept(this);
      recvType = getType(receiver);
    }

    return buildMethodInvocation(
        recv, recvType, methodName, positionalArguments);
  }

  @override
  java.MethodInvocation visitPropertyGet(dart.PropertyGet node) {
    String methodName =
        javaMethodName(node.name.name, dart.ProcedureKind.Getter);
    return buildDynamicMethodInvocation(node.receiver, methodName, []);
  }

  @override
  java.MethodInvocation visitMethodInvocation(dart.MethodInvocation node) {
    String name = node.name.name;
    // Expand operator symbol to Java-compatible method name
    name = Constants.operatorToMethodName[name] ?? name;
    return buildDynamicMethodInvocation(
        node.receiver, name, node.arguments.positional);
  }

  @override
  java.MethodInvocation visitStaticInvocation(dart.StaticInvocation node) {
    if (node.target is dart.Procedure) {
      // TODO(springerm): Instead of constructing types/classnames from
      // strings, use DartType here (and in compiler_state) eventually
      String libraryName =
          compilerState.getJavaPackageName(node.target.enclosingLibrary);
      String className;
      // TODO(andrewkrieger): Use symbol table for both branches.
      if (node.target.enclosingClass == null) {
        // Target is a top-level member of the library
        className = CompilerState.getClassNameForPackageTopLevel(package);
      } else {
        className = node.target.enclosingClass.name;
      }

      return buildMethodInvocation(
          new java.ClassRefExpr(libraryName + "." + className),
          // receiverType = null, because this invocation cannot be intercepted.
          null,
          node.target.name.name,
          node.arguments.positional);
    } else {
      throw new CompileErrorException(
          'Not implemented yet: Cannot handle ${node.target.runtimeType} '
          'targets in static invocations.');
    }
  }

  /// Retrieves the [DartType] for a kernel [Expression] node.
  dart.DartType getType(dart.Expression node) {
    // TODO(andrewkrieger): Workaround until we get types for implicit "this"
    // working.
    if (node.staticType == null) {
      if (node.toString() == "this") {
        return getThisClassDartType();
      } else {
        throw new CompileErrorException(
            'Unable to retrieve type for Kernel AST expression "$node" of type'
            ' ${node.runtimeType}.');
      }
    } else {
      return node.staticType;
    }
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
  // TODO(andrewkrieger): Symbol table lookup
  java.ClassOrInterfaceType getJavaType(dart.Class dartClass) {
    return new java.ClassOrInterfaceType(dartClass.name);
  }

  /// Build a reference to a Dart class.
  java.ClassRefExpr buildClassReference(dart.Class dartClass) {
    return new java.ClassRefExpr(getJavaType(dartClass).name);
  }

  java.ClassRefExpr buildThisClassRefExpr() {
    return new java.ClassRefExpr(getThisClassJavaType().name);
  }

  /// Build a reference to "this".
  java.Expression buildDefaultReceiver({bool isStatic: null}) {
    isStatic ??= isInsideStaticMethod;

    if (isStatic) {
      return buildThisClassRefExpr();
    } else {
      if (isInterceptorClass) {
        // Replace "this" with "self" (first arg.) inside interceptor methods
        return new java.IdentifierExpr(Constants.javaStaticThisIdentifier);
      } else {
        return new java.IdentifierExpr("this");
      }
    }
  }

  /// Returns the fully-qualified Java class name of the current class.
  java.ClassOrInterfaceType getThisClassJavaType() {
    if (isInterceptorClass) {
      return compilerState.javaClasses[thisDartClass];
    } else {
      return getJavaType(thisDartClass);
    }
  }

  /// Returns the [dart.DartType] for the current class.
  dart.DartType getThisClassDartType() {
    return thisDartClass.thisType;
  }

  @override
  java.IdentifierExpr visitThisExpression(dart.ThisExpression node) {
    return buildDefaultReceiver();
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
  java.JavaType defaultDartType(dart.DartType node) {
    if (node is dart.DynamicType) {
      // TODO(stanm): #implementDynamic: Object is not the best representation
      // of dynamic: implement better.
      return java.JavaType.object;
    }
    throw new CompileErrorException("Unimplemented type: ${node.runtimeType}");
  }

  @override
  java.VoidType visitVoidType(dart.VoidType node) {
    return java.JavaType.void_;
  }

  @override
  java.ClassOrInterfaceType visitInterfaceType(dart.InterfaceType node) {
    // TODO(stanm): look-up in symbol table when we have one.
    // TODO(stanm): You can't simply map Dart generics to Java generics!
    List<java.JavaType> typeArguments =
        node.typeArguments.map((type) => type.accept(this)).toList();
    return new java.ClassOrInterfaceType(node.classNode.name,
        typeArguments: typeArguments);
  }

  @override
  java.VariableDecl visitVariableDeclaration(dart.VariableDeclaration node) {
    return new java.VariableDecl(node.name, node.type.accept(this),
        isFinal: node.isFinal);
  }
}
