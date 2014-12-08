library ddc.typechecker;

import 'package:analyzer/analyzer.dart';
import 'package:analyzer/src/generated/ast.dart';
import 'package:analyzer/src/generated/element.dart';
import 'package:analyzer/src/generated/resolver.dart';
import 'package:analyzer/src/generated/source.dart';
import 'package:logging/logging.dart' as logger;

import 'src/resolver.dart';
import 'src/static_info.dart';
import 'src/type_rules.dart';
import 'src/utils.dart';

final _log = new logger.Logger('ddc.checker');

/// Runs the program checker using the restricted type rules on [fileUri].
Results checkProgram(Uri fileUri, {TypeResolver resolver, String sdkDir,
    Map<String, String> mockSdkSources, bool checkSdk: false,
    bool useColors: true}) {

  if (resolver == null) {
    resolver = new TypeResolver(mockSdkSources != null
        ? TypeResolver.sdkResolverFromMock(mockSdkSources)
        : TypeResolver.sdkResolverFromDir(sdkDir));
  }

  // Invoke the checker on the entry point.
  _log.info('running checker...');
  TypeProvider provider = resolver.context.typeProvider;
  var rules = new RestrictedRules(provider);
  final visitor = new ProgramChecker(
      resolver, rules, fileUri, checkSdk);
  visitor.check();
  visitor.finalizeImports();
  return new Results(visitor.libraries, visitor.infoMap, rules,
      visitor.failure);
}

/// Represents a summary of the results collected by running the program
/// checker.
class Results {
  final Map<Uri, Library> libraries;
  final Map<AstNode, List<StaticInfo>> infoMap;
  final TypeRules rules;
  final bool failure;

  Results(this.libraries, this.infoMap, this.rules, this.failure);
}

/// Holds information about a Dart library.
class Library {
  final Uri uri;
  final Source source;
  final CompilationUnit lib;
  final Map<Uri, CompilationUnit> parts = new Map<Uri, CompilationUnit>();
  final Map<Uri, Library> imports = new Map<Uri, Library>();

  Library(this.uri, this.source, this.lib);
}

class ProgramChecker extends RecursiveAstVisitor {
  final TypeResolver _resolver;
  final TypeRules _rules;
  final Uri _root;
  final bool _checkSdk;
  final Map<Uri, CompilationUnit> _unitMap = <Uri, CompilationUnit>{};
  final Map<Uri, Library> libraries = <Uri, Library>{};

  void finalizeImports() {
    libraries.forEach((Uri uri, Library lib) {
      for (Uri key in lib.imports.keys) {
        lib.imports[key] = libraries[key];
      }
    });
  }

  ProgramChecker(this._resolver, this._rules, this._root, this._checkSdk);

  void check() {
    var startLibrary = _resolver.context.computeLibraryElement(
        _resolver.findSource(_root));
    for (var lib in reachableLibraries(startLibrary)) {
      if (!_checkSdk && lib.isInSdk) continue;
      var source = lib.source;
      libraries[source.uri] =
          new Library(source.uri, source, lib.definingCompilationUnit.node);
      for (var unit in lib.units) {
        _rules.setCompilationUnit(unit.node);
        unit.node.visitChildren(this);
      }
    }
  }

  visitComment(Comment node) {
    // skip, no need to do typechecking inside comments (they may contain
    // comment references which would require resolution).
  }

  visitAssignmentExpression(AssignmentExpression node) {
    DartType staticType = _rules.getStaticType(node.leftHandSide);
    checkAssignment(node.rightHandSide, staticType);
    node.visitChildren(this);
  }

  // Check that member declarations soundly override any overridden declarations.
  InvalidOverride findInvalidOverride(AstNode node, ExecutableElement element,
      InterfaceType type, [bool allowFieldOverride = null]) {
    // FIXME: This can be done a lot more efficiently.
    assert(!element.isStatic);

    // TODO(vsm): Move this out.
    FunctionType subType = _rules.elementType(element);

    ExecutableElement baseMethod;
    String memberName = element.name;

    final isGetter = element is PropertyAccessorElement && element.isGetter;
    final isSetter = element is PropertyAccessorElement && element.isSetter;

    if (isGetter) {
      assert(!isSetter);
      // Look for getter or field.
      // FIXME: Verify that this handles fields.
      baseMethod = type.getGetter(memberName);
    } else if (isSetter) {
      baseMethod = type.getSetter(memberName);
    } else {
      if (memberName == '-') {
        // operator- can be overloaded!
        final len = subType.parameters.length;
        for (final method in type.methods) {
          if (method.name == memberName && method.parameters.length == len) {
            baseMethod = method;
            break;
          }
        }
      } else {
        baseMethod = type.getMethod(memberName);
      }
    }
    if (baseMethod != null) {
      // TODO(vsm): Test for generic
      FunctionType baseType = _rules.elementType(baseMethod);
      if (!_rules.isAssignable(subType, baseType)) {
        return new InvalidMethodOverride(node, element, type, subType,
            baseType);
      }

      // Test that we're not overriding a field.
      if (allowFieldOverride == false) {
        for (FieldElement field in type.element.fields) {
          if (field.name == memberName) {
            // TODO(vsm): Is this the right test?
            bool syn = field.isSynthetic;
            if (!syn) {
              return new InvalidFieldOverride(node, element, type);
            }
          }
        }
      }
    }

    if (type.isObject) return null;

    allowFieldOverride = allowFieldOverride == null ? false :
        allowFieldOverride;
    InvalidOverride base =
        findInvalidOverride(node, element, type.superclass, allowFieldOverride);
    if (base != null) return base;

    for (final parent in type.interfaces) {
      base = findInvalidOverride(node, element, parent, true);
      if (base != null) return base;
    }

    for (final parent in type.mixins) {
      base = findInvalidOverride(node, element, parent, true);
      if (base != null) return base;
    }

    return null;
  }

  void checkInvalidOverride(
      AstNode node, ExecutableElement element, InterfaceType type) {
    InvalidOverride invalid = findInvalidOverride(node, element, type);
    record(invalid);
  }

  visitMethodDeclaration(MethodDeclaration node) {
    node.visitChildren(this);
    if (node.isStatic) return;
    final parent = node.parent;
    if (parent is! ClassDeclaration) {
      throw 'Unexpected parent: $parent';
    }
    ClassDeclaration cls = parent as ClassDeclaration;
    // TODO(vsm): Check for generic.
    InterfaceType type = _rules.elementType(cls.element);
    checkInvalidOverride(node, node.element, type);
  }

  visitFieldDeclaration(FieldDeclaration node) {
    node.visitChildren(this);
    // TODO(vsm): Is there always a corresponding synthetic method?  If not, we need to validate here.
    final parent = node.parent;
    if (!node.isStatic && parent is ClassDeclaration) {
      InterfaceType type = _rules.elementType(parent.element);
      for (VariableDeclaration decl in node.fields.variables) {
        final getter = type.getGetter(decl.name.name);
        if (getter != null) checkInvalidOverride(node, getter, type);
        final setter = type.getSetter(decl.name.name);
        if (setter != null) checkInvalidOverride(node, setter, type);
      }
    }
  }

  // Check invocations
  bool checkArgumentList(ArgumentList node, [FunctionType type]) {
    NodeList<Expression> list = node.arguments;
    int len = list.length;
    for (int i = 0; i < len; ++i) {
      Expression arg = list[i];
      ParameterElement element = node.getStaticParameterElementFor(arg);
      if (element == null) {
        element = type.parameters[i];
        // TODO(vsm): When can this happen?
        assert(element != null);
      }
      DartType expectedType = _rules
          .mapGenericType(_rules.elementType(element));
      if (expectedType == null) expectedType = _rules.provider.dynamicType;
      checkAssignment(arg, expectedType);
    }
    return true;
  }

  void checkFunctionApplication(
      Expression node, Expression f, ArgumentList list) {
    DartType type = _rules.getStaticType(f);
    if (type.isDynamic || type.isDartCoreFunction) {
      record(new DynamicInvoke(_rules, node));
    } else if (type is! FunctionType) {
      // TODO(vsm): Function object.  Should still be able to derive a function type
      // from this.
      record(new DynamicInvoke(_rules, node));
    } else {
      assert(type is FunctionType);
      checkArgumentList(list, type);
    }
  }

  visitMethodInvocation(MethodInvocation node) {
    checkFunctionApplication(node, node.methodName, node.argumentList);
    node.visitChildren(this);
  }

  visitFunctionExpressionInvocation(FunctionExpressionInvocation node) {
    checkFunctionApplication(node, node.function, node.argumentList);
    node.visitChildren(this);
  }

  visitRedirectingConstructorInvocation(
      RedirectingConstructorInvocation node) {
    bool checked = checkArgumentList(node.argumentList);
    assert(checked);
    node.visitChildren(this);
  }

  visitSuperConstructorInvocation(SuperConstructorInvocation node) {
    bool checked = checkArgumentList(node.argumentList);
    assert(checked);
    node.visitChildren(this);
  }

  AstNode _getOwnerFunction(AstNode node) {
    var parent = node.parent;
    while (parent is! FunctionExpression && parent is! MethodDeclaration &&
        parent is! ConstructorDeclaration) {
      parent = parent.parent;
    }
    return parent;
  }

  FunctionType _getFunctionType(AstNode node) {
    if (node is Declaration) {
      return _rules.elementType(node.element);
    } else {
      assert(node is FunctionExpression);
      return _rules.getStaticType(node);
    }
  }

  void _checkReturn(Expression expression, AstNode node) {
    var type = _getFunctionType(_getOwnerFunction(node)).returnType;
    // TODO(vsm): Enforce void or dynamic (to void?) when expression is null.
    if (expression != null) {
      checkAssignment(expression, type);
    }
  }

  visitExpressionFunctionBody(ExpressionFunctionBody node) {
    _checkReturn(node.expression, node);
    node.visitChildren(this);
  }

  visitReturnStatement(ReturnStatement node) {
    _checkReturn(node.expression, node);
    node.visitChildren(this);
  }

  visitPropertyAccess(PropertyAccess node) {
    final target = node.realTarget;
    DartType receiverType = _rules.getStaticType(target);
    assert(receiverType != null);
    if (receiverType.isDynamic) {
      record(new DynamicInvoke(_rules, node));
    }
    node.visitChildren(this);
  }

  visitPrefixedIdentifier(PrefixedIdentifier node) {
    final target = node.prefix;
    // Check if the prefix is a library - PrefixElement denotes a library
    // access.
    if (target.staticElement is! PrefixElement) {
      DartType receiverType = _rules.getStaticType(target);
      assert(receiverType != null);
      if (receiverType.isDynamic) {
        record(new DynamicInvoke(_rules, node));
      }
    }
    node.visitChildren(this);
  }

  visitVariableDeclarationList(VariableDeclarationList node) {
    TypeName type = node.type;
    if (type == null) {
      // No checks are needed when the type is var. Although internally the
      // typing rules may have inferred a more precise type for the variable
      // based on the initializer.
    } else {
      var dartType = getType(type);
      for (VariableDeclaration variable in node.variables) {
        var initializer = variable.initializer;
        if (initializer != null) checkAssignment(initializer, dartType);
      }
    }
    node.visitChildren(this);
  }

  void _checkRuntimeTypeCheck(AstNode node, TypeName typeName) {
    var type = getType(typeName);
    if (!_rules.isGroundType(type)) {
      record(new InvalidRuntimeCheckError(node, type));
    }
  }

  visitAsExpression(AsExpression node) {
    _checkRuntimeTypeCheck(node, node.type);
    node.visitChildren(this);
  }

  visitIsExpression(IsExpression node) {
    _checkRuntimeTypeCheck(node, node.type);
    node.visitChildren(this);
  }

  DartType getType(TypeName name) {
    return (name == null) ? _rules.provider.dynamicType : name.type;
  }

  bool checkAssignment(Expression expr, DartType type) {
    StaticInfo result = _rules.checkAssignment(expr, type);
    return !record(result);
  }

  Map<AstNode, List<StaticInfo>> infoMap = new Map<AstNode, List<StaticInfo>>();
  bool failure = false;

  bool record(StaticInfo info) {
    if (info != null) {
      if (info.level >= logger.Level.SEVERE) failure = true;
      if (!infoMap.containsKey(info.node)) {
        infoMap[info.node] = new List<StaticInfo>();
      }
      infoMap[info.node].add(info);
      _log.log(info.level, info.message, info.node);
      return true;
    }
    return false;
  }
}