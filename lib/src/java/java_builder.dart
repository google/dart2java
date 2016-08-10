// Copyright (c) 2016, the Dart project authors.  Please see the AUTHORS file
// for details. All rights reserved. Use of this source code is governed by a
// BSD-style license that can be found in the LICENSE file.

import 'package:kernel/ast.dart' as dart;

import 'ast.dart' as java;
import 'visitor.dart' as java;
import 'types.dart' as java;
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

/// Builds a Java class AST from a kernel class AST. Kernel does not give names
/// to temporary variables, so we need to keep track of them manually. Also see
/// the comment for visitLet.
List<java.ClassDecl> buildClass(dart.Class node, CompilerState compilerState) {
  // Nothing to do for core abstract classes like `int`.
  if (compilerState.isJavaClass(node)) {
    return const <java.ClassDecl>[];
  }

  return [node.accept(new _JavaAstBuilder(compilerState))];
}

/// A temporary local variable defined in a method.
class TemporaryVariable {
  String name;

  dart.DartType type;

  TemporaryVariable(this.name, this.type);
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

  /// A counter for generating unique identifiers for temporary variables.
  int tempVarCounter = 0;

  /// A map assigning Kernel AST variables to temporary variables.
  Map<dart.VariableDeclaration, TemporaryVariable> tempVars = null;

  /// Generates a unique variable identifier.
  String nextTempVarIdentifier() {
    return "__tempVar_${tempVarCounter++}";
  }

  final CompilerState compilerState;

  /// Default visitor method. Useful to track which AST nodes are not
  /// translated yet.
  @override
  java.Node defaultExpression(dart.Expression node) {
    print("WARNING: java_builder does not handle ${node.runtimeType} yet");
    return null;
  }

  /// Default visitor method. Useful to track which AST nodes are not
  /// translated yet.
  @override
  java.Node defaultStatement(dart.Statement node) {
    print("WARNING: java_builder does not handle ${node.runtimeType} yet");
    return null;
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

    java.ClassOrInterfaceType supertype = node.supertype.accept(this);
    if (supertype == java.JavaType.object) {
      // Make sure that "extends Object" results in "extends DartObject"
      // TODO(springerm): Remove hard-coded special case once we 
      // figure out interop
      supertype = java.JavaType.dartObject;
    }

    return new java.ClassDecl(type,
        access: java.Access.Public, 
        fields: fields, 
        methods: methods, 
        constructors: constructors,
        supertype: supertype,
        isAbstract: node.isAbstract);
  }

  @override
  java.FieldDecl visitField(dart.Field node) {
    return new java.FieldDecl(node.name.name, node.type.accept(this),
        initializer: buildCovariantCompatibleExpression(node.initializer),
        access: java.Access.Public,
        isStatic: node.isStatic,
        isFinal: node.isFinal);
  }

  java.MethodDef buildGetter(dart.Field node) {
    String methodName = javaMethodName(
      node.name.name, dart.ProcedureKind.Getter);
    var body = wrapInJavaBlock(new java.ReturnStmt(
      new java.FieldAccess(buildDefaultReceiver(node.isStatic), 
        new java.IdentifierExpr(node.name.name))));

    return new java.MethodDef(methodName, body, [], 
      returnType: node.type.accept(this),
      isStatic: node.isStatic);
  }

  java.MethodDef buildSetter(dart.Field node) {
    String methodName = javaMethodName(
      node.name.name, dart.ProcedureKind.Setter);
    var fieldAssignment = new java.AssignmentExpr(
      new java.FieldAccess(buildDefaultReceiver(node.isStatic), 
        new java.IdentifierExpr(node.name.name)), 
      new java.IdentifierExpr("value"));
    var returnStmt = new java.ReturnStmt(new java.IdentifierExpr("value"));
    var body = new java.Block([
      new java.ExpressionStmt(fieldAssignment), 
      returnStmt]);
    var param = new java.VariableDecl("value", node.type.accept(this));

    return new java.MethodDef(methodName, body, [param], 
      returnType: node.type.accept(this),
      isStatic: node.isStatic);
  }

  @override
  java.Statement visitFieldInitializer(dart.FieldInitializer node) {
    var fieldAssignment = new java.AssignmentExpr(
      new java.FieldAccess(buildDefaultReceiver(false), 
        new java.IdentifierExpr(node.field.name.name)), 
      buildCovariantCompatibleExpression(node.value));

    return new java.ExpressionStmt(fieldAssignment);
  }

  @override
  java.Statement visitSuperInitializer(dart.SuperInitializer node) {
    return new java.ExpressionStmt(new java.SuperConstructorInvocation(
      node.arguments.positional.map(buildCovariantCompatibleExpression)
        .toList()));
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

  Iterable<java.Statement> buildTempVarDecls() {
    return tempVars.values.map((v) =>
      new java.VariableDeclStmt(
          new java.VariableDecl(v.name, v.type.accept(this))));
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
      // Normal Dart method
      if (!procedure.isAbstract) {
        assert(tempVars == null);
        tempVars = new Map<dart.VariableDeclaration, TemporaryVariable>();

        body = wrapInJavaBlock(buildStatement(procedure.function.body));

        // Insert declarations of temporary variables
        (body as java.Block).statements.insertAll(0, buildTempVarDecls());
        tempVars = null;
      }
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

    if (!procedure.isAbstract) {
      // Make sure body is a [java.Block]
      body = wrapInJavaBlock(body);
    }

    return new java.MethodDef(methodName, body, parameters, 
      returnType: returnType, isStatic: isStatic, isFinal: false,
      isAbstract: procedure.isAbstract);
  }

  @override
  java.Constructor visitConstructor(dart.Constructor node) {
    // TODO(springerm): Handle initializers other than [FieldInitializer]
    Iterable<dart.Initializer> fieldInitializers = 
      node.initializers.where((i) => i.runtimeType == dart.FieldInitializer);
    Iterable<java.Statement> javaFieldInitializers = 
      fieldInitializers.map((i) => i.accept(this));

    // There is either 0 or 1 super initializers
    Iterable<dart.Initializer> superInitializers = 
      node.initializers.where((i) => i.runtimeType == dart.SuperInitializer);
    Iterable<java.Statement> javaSuperInitializers = 
      superInitializers.map((i) => i.accept(this));

    java.Block body;
    if (node.function.body == null) {
      // Constructor may not have a body (sometimes also contains a body
      // with an [EmptyStatement]).
      body = new java.Block(<java.Statement>[]);
    } else {
      body = wrapInJavaBlock(buildStatement(node.function.body));
    }
    body.statements.insertAll(0, javaFieldInitializers);
    body.statements.insertAll(0, javaSuperInitializers);

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
  java.WhileStmt visitWhileStatement(dart.WhileStatement node) {
    return new java.WhileStmt(
      node.condition.accept(this),
      wrapInJavaBlock(buildStatement(node.body)));
  } 

  @override
  java.DoStmt visitDoStatement(dart.DoStatement node) {
    return new java.DoStmt(
      node.condition.accept(this),
      wrapInJavaBlock(buildStatement(node.body)));
  }

  @override
  java.ForStmt visitForStatement(dart.ForStatement node) {
    return new java.ForStmt(
      node.variables.map((v) => v.accept(this)).toList() 
        as List<java.VariableDecl>,
      node.condition.accept(this),
      node.updates.map((u) => u.accept(this)).toList()
        as List<java.Expression>,
      wrapInJavaBlock(buildStatement(node.body)));
  }

  @override
  java.ReturnStmt visitReturnStatement(dart.ReturnStatement node) {
    return new java.ReturnStmt(
      buildCovariantCompatibleExpression(node.expression));
  }

  @override
  java.ExpressionStmt visitExpressionStatement(dart.ExpressionStatement node) {
    return new java.ExpressionStmt(node.expression.accept(this));
  }

  /// Builds a method invocation where the call target is not statically known.
  java.MethodInvocation buildDynamicMethodInvocation(dart.Expression receiver, 
      String methodName, List<dart.Expression> positionalArguments,
      bool isInsideStaticMethod) {
    // Translate receiver
    java.Expression recv;
    dart.DartType recvType;
    if (receiver == null) {
      // Implicit "this" receiver
      recv = buildDefaultReceiver(isInsideStaticMethod);
      recvType = getThisClassDartType();
    } else {
      recv = receiver.accept(this);
      recvType = getType(receiver);
    }

    // TODO(springerm): Handle other argument types
    List<java.Expression> args = positionalArguments
        .map(buildCovariantCompatibleExpression)
        .toList();

    if (Constants.objectMethods.contains(methodName)) {
      // This method is defined on Object and must dispatch to ObjectHelper
      // directly to handle "null" values correctly
      java.ClassOrInterfaceType helperClass =
          compilerState.getHelperClass(compilerState.objectClass);
      // Generate static call to helper function.
      java.ClassRefExpr helperRefExpr = new java.ClassRefExpr(helperClass);

      return new java.MethodInvocation(
        helperRefExpr, methodName, [recv]..addAll(args));
    } 

    if (recvType is! dart.InterfaceType) {
      throw new CompileErrorException(
        "Can only handle method invocation where receiver is an InterfaceType "
        "(found ${recvType.runtimeType})");
    }

    dart.Class classNode = (recvType as dart.InterfaceType).classNode;

    // Change method name if annotated with @JavaMethod
    if (compilerState.hasJavaImpl(classNode)) {
      methodName = compilerState.getJavaMethodName(classNode, methodName);
    }

    // Intercept method call if necessary
    if (compilerState.usesHelperFunction(classNode, methodName)) {
      java.ClassOrInterfaceType helperClass =
          compilerState.getHelperClass(classNode);

      // Generate static call to helper function.
      java.ClassRefExpr helperRefExpr = new java.ClassRefExpr(helperClass);
        // Dynamic method invocation
      return new java.MethodInvocation(
        helperRefExpr, methodName, [recv]..addAll(args));
    }

    return new java.MethodInvocation(recv, methodName, args);
  }

  @override
  java.MethodInvocation visitPropertyGet(dart.PropertyGet node) {
    String methodName =
        javaMethodName(node.name.name, dart.ProcedureKind.Getter);
    return buildDynamicMethodInvocation(node.receiver, methodName, [], 
      isInsideStaticMethod(node));
  }

  @override
  java.MethodInvocation visitPropertySet(dart.PropertySet node) {
    String methodName =
        javaMethodName(node.name.name, dart.ProcedureKind.Setter);
    return buildDynamicMethodInvocation(node.receiver, methodName, 
        [node.value], isInsideStaticMethod(node));
  }

  @override
  java.MethodInvocation visitMethodInvocation(dart.MethodInvocation node) {
    String name = node.name.name;
    // Expand operator symbol to Java-compatible method name
    name = Constants.operatorToMethodName[name] ?? name;
    return buildDynamicMethodInvocation(
        node.receiver, name, node.arguments.positional, 
        isInsideStaticMethod(node));
  }

  @override
  java.SuperMethodInvocation visitSuperMethodInvocation(
    dart.SuperMethodInvocation node) {
    return new java.SuperMethodInvocation(
      node.name.name,
      node.arguments.positional.map(buildCovariantCompatibleExpression)
        .toList());
  }

  @override
  java.MethodInvocation visitStaticInvocation(dart.StaticInvocation node) {
    java.Expression receiver;
    dart.Class receiverClass = null;
    String methodName = node.target.name.name;

    if (node.target.enclosingClass == null) {
      receiver = new java.ClassRefExpr(
        compilerState.getTopLevelClass(node.target.enclosingLibrary));
    } else {
      receiver = new java.ClassRefExpr(
        compilerState.getClass(node.target.enclosingClass));
      receiverClass = node.target.enclosingClass;
    }

    // Change method name if annotated with @JavaMethod
    if (compilerState.hasJavaImpl(receiverClass)) {
      methodName = compilerState.getJavaMethodName(receiverClass, methodName);
    }

    // TODO(springerm): Handle named arguments
    var args = <java.Expression>[];
    // Calling convention: Type arguments as first arguments 
    // for static invocations, then positional arguments
    // TODO(springerm, andrewkrieger): Use proper types once implemented
    args.addAll(node.arguments.types.map(
      (t) => new java.TypeExpr(t.accept(this))));
    // Positional arguments
    args.addAll(node.arguments.positional
        .map(buildCovariantCompatibleExpression));

    // Intercept method call if necessary
    if (compilerState.usesHelperFunction(receiverClass, methodName)) {
      java.ClassOrInterfaceType helperClass =
          compilerState.getHelperClass(receiverClass);

      // Generate static call to helper function.
      java.ClassRefExpr helperRefExpr = new java.ClassRefExpr(helperClass);
      // Static method invocation
      java.FieldAccess staticNested = new java.FieldAccess(
        helperRefExpr, 
        new java.IdentifierExpr(Constants.helperNestedClassForStatic));
      return new java.MethodInvocation(staticNested, methodName, args);
    }

    return new java.MethodInvocation(receiver, methodName, args);
  }

  @override
  java.NewExpr visitConstructorInvocation(dart.ConstructorInvocation node) {
    // TODO(springerm): Check for usesHelperFunction
    // TODO(springerm): Handle other parameter types
    List<java.Expression> args = node.arguments.positional
      .map(buildCovariantCompatibleExpression)
      .toList();

    java.ClassOrInterfaceType type = 
      node.target.enclosingClass.thisType.accept(this);

    if (type == java.JavaType.object) {
      // Make sure that "new Object" results in "new DartObject"
      // TODO(springerm): Remove hard-coded special case once we 
      // figure out interop
      type = java.JavaType.dartObject;
    }

    return new java.NewExpr(new java.ClassRefExpr(type), args);
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
        return new java.FieldAccess(new java.ClassRefExpr(
            node.target.enclosingClass.thisType.accept(this)), 
          new java.IdentifierExpr(field.name.name));
      }
    } else {
      throw new CompileErrorException(
          'Not implemented yet: Cannot handle StaticGet for '
          '${node.target.runtimeType}');
    }
  }

  @override
  java.UnaryExpr visitNot(dart.Not node) {
    return new java.UnaryExpr(node.operand.accept(this), "!");
  }

  @override
  java.Expression visitStringConcatenation(dart.StringConcatenation node) {
    Iterable<java.Expression> strings = node.expressions.map((e) =>
      new java.MethodInvocation(e.accept(this),
        Constants.toStringMethodName));

    return strings.reduce((value, element) =>
      new java.BinaryExpr(value, element, "+"));
  }

  @override
  java.NullLiteral visitNullLiteral(dart.NullLiteral node) {
    return new java.NullLiteral();
  }


  @override
  java.BinaryExpr visitLogicalExpression(dart.LogicalExpression node) {
    return new java.BinaryExpr(
      node.left.accept(this),
      node.right.accept(this),
      node.operator);
  }


  /// Retrieves the [DartType] for a kernel [Expression] node.
  dart.DartType getType(dart.Expression node) {
    if (node.runtimeType == dart.VariableGet) {
      // TODO(springerm, andrewkrieger): Workaround for temporary variables
      // inside let expressions until we get types for let expressions working.
      dart.VariableGet varGetNode = node as dart.VariableGet;
      dart.DartType nodeType = varGetNode.variable.type;

      if (nodeType.runtimeType == dart.DynamicType) {
        if (tempVars.containsKey(varGetNode.variable)) {
          // This is a temporary variable
          return tempVars[varGetNode.variable].type;
        }
      } else {
        return nodeType;
      }
    }

    if (node.staticType == null) {
      if (node.toString() == "this") {
        // TODO(andrewkrieger): Workaround until we get types for implicit 
        // "this" working.
        return getThisClassDartType();
      } else {
        // TODO(springerm, andrewkrieger): Kernel AST does currently not expose
        // types of expressions in let expressions. Assume for the moment that 
        // everything is int to make "i++" etc. working
        print('Warning: Unable to retrieve type for Kernel AST expression '
          '"$node" of type ${node.runtimeType}. Assuming int.');
        return compilerState.intClass.thisType;
        // TODO(springerm): throw new CompileErrorException
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

  /// Determine if an expression occurs in a static method.
  bool isInsideStaticMethod(dart.Expression expression) {
    dart.TreeNode node = expression;

    while (node is! dart.Member) {
      node = node.parent;
    }

    switch (node.runtimeType) {
      case dart.Constructor:
        return false;
      case dart.Procedure:
        return (node as dart.Procedure).isStatic;
      default:
        throw new CompileErrorException(
          "Expression is found under unknown Member ${node.runtimeType}");
    }
  }

  /// Build a reference to "this".
  java.Expression buildDefaultReceiver(bool isStatic) {
    if (isStatic) {
      return buildThisClassRefExpr();
    } else {
      assert(!compilerState.isJavaClass(thisDartClass));
      return new java.IdentifierExpr("this");
    }
  }

  /// Returns the [dart.DartType] for the current class.
  dart.InterfaceType getThisClassDartType() {
    return thisDartClass.thisType;
  }

  @override
  java.IdentifierExpr visitThisExpression(dart.ThisExpression node) {
    return buildDefaultReceiver(isInsideStaticMethod(node));
  }

  @override
  java.IdentifierExpr visitVariableGet(dart.VariableGet node) {
    if (node.variable.name == null) {
      // This must be a temporary variable
      String name = tempVars[node.variable]?.name;

      if (name == null) {
        throw new CompileErrorException("Expected temporary variable.");
      }

      return new java.IdentifierExpr(name);
    } else {
      return new java.IdentifierExpr(node.variable.name);
    }
  }

  @override
  java.AssignmentExpr visitVariableSet(dart.VariableSet node) {
    return new java.AssignmentExpr(
        new java.IdentifierExpr(node.variable.name), 
        buildCovariantCompatibleExpression(node.value));
  }

  /// Java generics are not covariant but Dart generics are. The current 
  /// implementation uses both Java generics and an additional field for 
  /// reified generics in generated Java code. Using Java generics has the
  /// benefit that less explicit casts are necessary, which makes codegen
  /// easier (see DartList.java for an example). For example:
  /// 
  /// List<int> list = ...
  /// int a = list[1]
  /// 
  /// This code snippet does not require a Java cast because it is translated
  /// to the following:
  /// 
  /// DartList<Integer> list = ...
  /// Integer a = list.operatorAt(1) --> returns Integer
  /// 
  /// If List were not generic, the return type would be an Object and the code
  /// generator would have to insert an explicit cast. The following generated
  /// Java code is troublesome:
  /// 
  /// DartList<String> stringList = ...
  /// DartList<Object> list = stringList;
  /// 
  /// In order to make that code compile, this method inserts an additional
  /// cast that does *not* result in a runtime check.
  /// 
  /// DartList<Object> list = (DartList) stringList;
  /// 
  /// Note, that Dart checks types at a different point of time as Java (i.e.,
  /// when adding something to a list etc.), but that is another issue. Afaik,
  /// it is not possible to get rid of the runtime type check when accessing
  /// an element in the list in Java without writing bytecode directly. Even
  /// then, the bytecode verifier might reject the code.
  java.Expression buildCovariantCompatibleExpression(dart.Expression node) {
    if (node == null) {
      // This method is sometimes called on optional nodes, e.g. initializers
      return null;
    }

    dart.DartType type = getType(node);
    java.ClassOrInterfaceType targetType;

    if (type.runtimeType == dart.InterfaceType) {
      var interfaceType = type as dart.InterfaceType;

      if (interfaceType.typeArguments.isNotEmpty) {
        // Insert a cast
        targetType = interfaceType.accept(this);
        targetType.typeArguments.clear();

        return new java.CastExpr(node.accept(this), targetType);
      }
    }

    return node.accept(this);
  }

  @override
  java.Expression visitLet(dart.Let node) {
    // TODO(springerm): Need to seriously optimize this for simple
    // incremenets/decrements

    // Let expressions require two operations: (1) create and assign a 
    // temporary variable and (2) evaluate and return an expression.
    // This is hard to do in an expression because (1) is a statement.
    // Here's a way to do it:
    // Create a temporary variable at the beginning of the method and 
    // assign it Let.variable where the let expression occurs. Pass this
    // assignment expression as an argument to the `comma` method (sequence
    // point method) along with the body of the let expression. That is a way
    // to implement Let expressions without lambdas. `comma` simply returns
    // the second parameter and acts as a sequence point (comma in C/C++).

    String tempIdentifier = nextTempVarIdentifier();
    tempVars[node.variable] = new TemporaryVariable(
      tempIdentifier,
      // TODO(springerm, andrewkrieger): Kernel AST does currently not expose
      // types of expressions in let expressions. Assume for the moment that 
      // everything is int to make "i++" etc. working
      compilerState.intClass.thisType /* node.variable.type */);

    var assignment = new java.AssignmentExpr(
      new java.IdentifierExpr(tempIdentifier),
      buildCovariantCompatibleExpression(node.variable.initializer));

    return new java.MethodInvocation(
      new java.ClassRefExpr(java.JavaType.letHelper),
      Constants.sequencePointMethodName,
      <java.Expression>[
        assignment,
        buildCovariantCompatibleExpression(node.body)]);
  }

  @override
  java.Expression visitListLiteral(dart.ListLiteral node) {
    var args = <java.Expression>[];
    args.add(new java.TypeExpr(node.typeArgument.accept(this)));
    args.addAll(node.expressions.map(buildCovariantCompatibleExpression));

    java.Expression listClass = new java.ClassRefExpr(
      compilerState.getClass(compilerState.listClass));

    return new java.MethodInvocation(
      listClass, 
      Constants.listInitializerMethodName,
      args);
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
      // A variable declaration should sometimes be a statement. In that case,
      // we ensure that the variable is initialized (to null if necessary).
      var decl = result as java.VariableDecl;
      decl.initializer ??= new java.NullLiteral();
      return new java.VariableDeclStmt(decl);
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
    java.ClassOrInterfaceType type = compilerState.getClass(node.classNode);

    if (node.typeArguments != null && node.typeArguments.isNotEmpty) {
      return type.withTypeArguments(
        node.typeArguments.map((t) => t.accept(this)).toList());
    } else {
      return type;
    }
  }

  @override
  java.VariableDecl visitVariableDeclaration(dart.VariableDeclaration node) {
    return new java.VariableDecl(node.name, node.type.accept(this),
        isFinal: node.isFinal, 
        initializer: buildCovariantCompatibleExpression(node.initializer));
  }
}
