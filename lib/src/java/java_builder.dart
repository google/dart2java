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
java.ClassDecl buildWrapperClass(
    dart.Library library, CompilerState compilerState) {
  var type = compilerState.getTopLevelClass(library);
  java.ClassDecl result = new java.ClassDecl(type);

  var instance = new _JavaAstBuilder(compilerState);
  result.fields = library.fields.map(instance.visitField).toList();
  result.methods = library.procedures.map(instance.visitProcedure).toList();

  return result;
}

/// Builds a Java class AST from a kernel class AST.
List<java.ClassDecl> buildClass(dart.Class node, CompilerState compilerState) {
  // Nothing to do for core abstract classes like `int`.
  if (compilerState.isJavaClass(node)) {
    return const <java.ClassDecl>[];
  }

  return [node.accept(new _JavaAstBuilder(compilerState))];
}

/// Builds a Java class from Dart IR.
class _JavaAstBuilder extends dart.Visitor<java.Node> {
  _JavaAstBuilder(this.compilerState);

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

  /// Need to know if this is a static method to generate correct
  /// implicit "this".
  bool isInsideStaticMethod;

  /// Default visitor method. Useful to track which AST nodes are not
  /// translated yet.
  @override
  void defaultExpression(dart.Expression node) {
    print("WARNING: java_builder does not handle ${node.runtimeType} yet");
  }

  /// Default visitor method. Useful to track which AST nodes are not
  /// translated yet.
  @override
  void defaultStatement(dart.Statement node) {
    print("WARNING: java_builder does not handle ${node.runtimeType} yet");
  }

  /// Visits a non-mixin class.
  @override
  java.ClassDecl visitNormalClass(dart.NormalClass node) {
    assert(thisDartClass == null);
    thisDartClass = node;
    java.ClassOrInterfaceType type = compilerState.getClass(node);
    List<java.FieldDecl> fields = node.fields.map(this.visitField).toList();
    List<java.MethodDef> implicitGetters = 
      node.fields.map(this.buildGetter).toList();
    List<java.MethodDef> implicitSetters = node.fields.where((f) => !f.isFinal)
        .map(this.buildSetter).toList();
    List<java.MethodDef> methods =
        node.procedures.map(this.visitProcedure).toList();
    List<java.Constructor> constructors = 
        node.constructors.map(this.visitConstructor).toList();

    // Merge getters/setters
    if (methods.any((m) => implicitGetters.any((g) => m.name == g.name))) {
      // This can happen because we simple prepend getters with "get"
      throw new CompileErrorException("Name clash between getter and method");
    }
    if (methods.any((m) => implicitSetters.any((s) => m.name == s.name))) {
      // This can happen because we simple prepend getters with "set"
      throw new CompileErrorException("Name clash between setter and method");
    }
    methods.addAll(implicitGetters);
    methods.addAll(implicitSetters);

    return new java.ClassDecl(type,
        access: java.Access.Public, 
        fields: fields, 
        methods: methods, 
        constructors: constructors);
  }

  @override
  java.FieldDecl visitField(dart.Field node) {
    return new java.FieldDecl(node.name.name, node.type.accept(this),
        initializer: node.initializer?.accept(this),
        access: java.Access.Public,
        isStatic: node.isStatic,
        isFinal: node.isFinal);
  }

  java.MethodDef buildGetter(dart.Field node) {
    assert(isInsideStaticMethod == null);
    isInsideStaticMethod = node.isStatic;   

    String methodName = javaMethodName(
      node.name.name, dart.ProcedureKind.Getter);
    var body = wrapInJavaBlock(new java.ReturnStmt(
      new java.FieldAccess(buildDefaultReceiver(), 
        new java.IdentifierExpr(node.name.name))));

    isInsideStaticMethod = null;

    return new java.MethodDef(methodName, body, [], 
      returnType: node.type.accept(this),
      isStatic: node.isStatic);
  }

  java.MethodDef buildSetter(dart.Field node) {
    assert(isInsideStaticMethod == null);
    isInsideStaticMethod = node.isStatic;   

    String methodName = javaMethodName(
      node.name.name, dart.ProcedureKind.Setter);
    var fieldAssignment = new java.AssignmentExpr(
      new java.FieldAccess(buildDefaultReceiver(), 
        new java.IdentifierExpr(node.name.name)), 
      new java.IdentifierExpr("value"));
    var returnStmt = new java.ReturnStmt(new java.IdentifierExpr("value"));
    var body = new java.Block([
      new java.ExpressionStmt(fieldAssignment), 
      returnStmt]);
    var param = new java.VariableDecl("value", node.type.accept(this));

    isInsideStaticMethod = null;

    return new java.MethodDef(methodName, body, [param], 
      returnType: node.type.accept(this),
      isStatic: node.isStatic);
  }

  @override
  java.Statement visitFieldInitializer(dart.FieldInitializer node) {
    assert(isInsideStaticMethod == null);
    isInsideStaticMethod = false; 

    var fieldAssignment = new java.AssignmentExpr(
      new java.FieldAccess(buildDefaultReceiver(), 
        new java.IdentifierExpr(node.field.name.name)), 
      node.value.accept(this));

    isInsideStaticMethod = null;

    return new java.ExpressionStmt(fieldAssignment);
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
              "${methodName} is not an operator.");
        }
        return translatedMethodName;
      case dart.ProcedureKind.Getter:
        return "get" + capitalizeString(methodName);
      case dart.ProcedureKind.Setter:
        return "set" + capitalizeString(methodName);
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
        .map(visitVariableDeclaration)
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
          new java.ClassOrInterfaceType.parseTopLevel(
              methodTokens.getRange(0, methodTokens.length - 1).join(".")));
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

  @override
  java.Constructor visitConstructor(dart.Constructor node) {
    // TODO(springerm): Handle initializers other than [FieldInitializer]
    Iterable<dart.Initializer> fieldInitializers = 
      node.initializers.where((i) => i.runtimeType == dart.FieldInitializer);
    Iterable<java.Statement> javaFieldInitializers = 
      fieldInitializers.map((i) => i.accept(this));

    java.Block body;
    if (node.function.body == null) {
      // Constructor may not have a body (sometimes also contains a body
      // with an [EmptyStatement]).
      body = new java.Block(<java.Statement>[]);
    } else {
      body = wrapInJavaBlock(buildStatement(node.function.body));
    }
    body.statements.insertAll(0, javaFieldInitializers);

    // TODO(springerm): handle named parameters, etc.
    List<java.VariableDecl> parameters = node.function.positionalParameters
        .map(visitVariableDeclaration)
        .toList();

    return new java.Constructor(
      getJavaType(thisDartClass), 
      body, 
      parameters);
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
  java.Block visitEmptyStatement(dart.EmptyStatement node) {
    // TODO(springerm): We don't have an empty statement right now,
    // but an empty block has no effect
    return new java.Block(<java.Statement>[]);
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
  java.ConditionalExpr visitConditionalExpression(
    dart.ConditionalExpression node) {
    return new java.ConditionalExpr(
      node.condition.accept(this),
      node.then.accept(this),
      node.otherwise.accept(this));
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
  /// by a static call to a helper function. If it is known that the method
  /// will not be intercepted (e.g., because the method being invoked is a
  /// static method, so the receiver is a [java.ClassRefExpr]), then
  /// [receiverType] may be null.
  java.MethodInvocation buildMethodInvocation(
      java.Expression receiver,
      dart.Class receiverClass,
      String methodName,
      List<dart.Expression> positionalArguments,
      {bool isStaticCall: false}) {

    // TODO(springerm): Handle other argument types
    List<java.Expression> args = positionalArguments
        .map((a) => a.accept(this) as java.Expression)
        .toList();

    // Intercept method call if necessary
    if (compilerState.usesHelperFunction(receiverClass, methodName)) {
      java.ClassOrInterfaceType helperClass =
          compilerState.getHelperClass(receiverClass);

      // Generate static call to helper function.
      java.ClassRefExpr helperRefExpr = new java.ClassRefExpr(helperClass);
      if (isStaticCall) {
        // Static method invocation
        java.FieldAccess staticNested = new java.FieldAccess(
          helperRefExpr, 
          new java.IdentifierExpr(Constants.helperNestedClassForStatic));
        return new java.MethodInvocation(staticNested, methodName, args);
      } else {
        // Dynamic method invocation
        return new java.MethodInvocation(helperRefExpr, 
          methodName, [receiver]..addAll(args));
      }
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

    if (recvType is! dart.InterfaceType) {
      throw new CompileErrorException("Can only handle InterfaceType");
    }

    return buildMethodInvocation(
        recv, recvType.classNode, methodName, positionalArguments);
  }

  @override
  java.MethodInvocation visitPropertyGet(dart.PropertyGet node) {
    String methodName =
        javaMethodName(node.name.name, dart.ProcedureKind.Getter);
    return buildDynamicMethodInvocation(node.receiver, methodName, []);
  }

  @override
  java.MethodInvocation visitPropertySet(dart.PropertySet node) {
    String methodName =
        javaMethodName(node.name.name, dart.ProcedureKind.Setter);
    return buildDynamicMethodInvocation(node.receiver, methodName, 
        [node.value]);
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
    dart.DartType receiverType = null;

    if (node.target is dart.Procedure) {
      java.ClassOrInterfaceType receiver;
      if (node.target.enclosingClass == null) {
        receiver = compilerState.getTopLevelClass(node.target.enclosingLibrary);
      } else {
        receiver = compilerState.getClass(node.target.enclosingClass);
        receiverType = node.target.enclosingClass;
      }

      return buildMethodInvocation(
          new java.ClassRefExpr(receiver),
          receiverType,
          node.target.name.name,
          node.arguments.positional,
          isStaticCall: true);
    } else {
      throw new CompileErrorException(
          'Not implemented yet: Cannot handle ${node.target.runtimeType} '
          'targets in static invocations.');
    }
  }

  @override
  java.NewExpr visitConstructorInvocation(dart.ConstructorInvocation node) {
    // TODO(springerm): Check for usesHelperFunction
    // TODO(springerm): Handle other parameter types
    List<java.Expression> args = node.arguments.positional
      .map((a) => a.accept(this) as java.Expression)
      .toList();

    return new java.NewExpr(new java.ClassRefExpr(
      node.target.enclosingClass.thisType.accept(this)), args);
  }

  @override
  java.FieldAccess visitStaticGet(dart.StaticGet node) {
    assert(!node.target.isInstanceMember);

    if (node.target.runtimeType == dart.Field) {
      // Static field read
      dart.Field field = node.target;

      // TODO(springerm): Reconsider passing method name here (it is a field!)
      if (compilerState.usesHelperFunction(
        node.target.enclosingClass, field.name.name)) {
        // Access static field in helper class
        java.ClassOrInterfaceType helperClass =
            compilerState.getHelperClass(node.target.enclosingClass);
        java.ClassRefExpr helperRefExpr = new java.ClassRefExpr(helperClass);
        java.FieldAccess staticNested = new java.FieldAccess(
          helperRefExpr, 
          new java.IdentifierExpr(Constants.helperNestedClassForStatic));
        return new java.FieldAccess(
          staticNested, new java.IdentifierExpr(field.name.name));
      } else {
        // Regular static field access
        throw new CompileErrorException(
            'Not implemented yet: Cannot handle StaticGet for fields');
      }
    } else {
      throw new CompileErrorException(
          'Not implemented yet: Cannot handle StaticGet for '
          '${node.target.runtimeType}');
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
  java.ClassOrInterfaceType getJavaType(dart.Class dartClass) {
    return compilerState.getClass(dartClass);
  }

  /// Build a reference to a Dart class.
  java.ClassRefExpr buildClassReference(dart.Class dartClass) {
    return new java.ClassRefExpr(getJavaType(dartClass));
  }

  java.ClassRefExpr buildThisClassRefExpr() {
    return buildClassReference(thisDartClass);
  }

  /// Build a reference to "this".
  java.Expression buildDefaultReceiver({bool isStatic: null}) {
    isStatic ??= isInsideStaticMethod;

    if (isStatic) {
      return buildThisClassRefExpr();
    } else {
      assert(!compilerState.isJavaClass(thisDartClass));
      return new java.IdentifierExpr("this");
    }
  }

  /// Returns the fully-qualified Java class name of the current class.
  java.ClassOrInterfaceType getThisClassJavaType() {
    return getJavaType(thisDartClass);
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
  java.BoolLiteral visitBoolLiteral(dart.BoolLiteral node) {
    return new java.BoolLiteral(node.value);
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
    throw new CompileErrorException("Unimplemented type: ${node.runtimeType}");
  }

  @override
  java.ReferenceType visitDynamicType(dart.DynamicType node) {
    // TODO(stanm): #implementDynamic: Object is not the best representation
    // of dynamic: implement better.
    return java.JavaType.object;
  }

  @override
  java.VoidType visitVoidType(dart.VoidType node) {
    return java.JavaType.void_;
  }

  @override
  java.ClassOrInterfaceType visitInterfaceType(dart.InterfaceType node) {
    if (node.typeArguments != null && node.typeArguments.isNotEmpty) {
      // TODO(stanm): You can't simply map Dart generics to Java generics!
      throw new CompileErrorException("Generic types not implemented yet!");
    }

    return compilerState.getClass(node.classNode);
  }

  @override
  java.VariableDecl visitVariableDeclaration(dart.VariableDeclaration node) {
    return new java.VariableDecl(node.name, node.type.accept(this),
        isFinal: node.isFinal);
  }
}
