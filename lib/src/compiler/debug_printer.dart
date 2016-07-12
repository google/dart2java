// Copyright (c) 2016, the Dart project authors.  Please see the AUTHORS file
// for details. All rights reserved. Use of this source code is governed by a
// BSD-style license that can be found in the LICENSE file.

import 'package:kernel/ast.dart';
import 'package:kernel/visitor.dart';

/// AST visitor that dumps the kernel AST to stdout.
///
/// Currently printed the node type for most nodes, with extra information added
/// for a few particular nodes. We can expand this as needed during early
/// development.
///
/// This is for debugging purposes only, and is nowhere near production level.
/// This class should be removed before releasing ddc-java.
class DebugPrinter extends Visitor {
  int _depth = 0;

  // --- The catch-all case, except for references. ---

  /// Print a node, if no other implementation is present.
  /// When overriding a more specialized method:
  ///   1. Call `_print('$nodeType[property=${property.value, ...]');`
  ///   2. Either call `_recurse(node)`, or else increment _depth,
  ///      recursively visit the node's children, then decrement _depth.
  /// See [defaultClass] for an example.
  @override
  defaultNode(Node node) {
    _print(node.runtimeType);
    _recurse(node);
  }

  @override
  defaultTreeNode(TreeNode node) => defaultNode(node);

  // --- Expressions ---
  @override
  defaultExpression(Expression node) {
    _print('${node.runtimeType}[staticType=${node.staticType}]');
    _recurse(node);
  }

  @override
  defaultBasicLiteral(BasicLiteral node) => defaultExpression(node);
  
  @override
  visitInvalidExpression(InvalidExpression node) => defaultExpression(node);

  @override
  visitVariableGet(VariableGet node) => defaultExpression(node);

  @override
  visitVariableSet(VariableSet node) => defaultExpression(node);

  @override
  visitPropertyGet(PropertyGet node) => defaultExpression(node);

  @override
  visitPropertySet(PropertySet node) => defaultExpression(node);

  @override
  visitSuperPropertyGet(SuperPropertyGet node) => defaultExpression(node);

  @override
  visitSuperPropertySet(SuperPropertySet node) => defaultExpression(node);

  @override
  visitStaticGet(StaticGet node) => defaultExpression(node);

  @override
  visitStaticSet(StaticSet node) => defaultExpression(node);

  @override
  visitMethodInvocation(MethodInvocation node) {
    _print("MethodInvocation[name=${node.name}, staticType=${node.staticType}]");
    _depth++;
    _visitWithLabel(node.receiver, 'receiver');
    _visitWithLabel(node.arguments, 'arguments');
    _depth--;
  }

  @override
  visitSuperMethodInvocation(SuperMethodInvocation node) =>
      defaultExpression(node);

  @override
  visitStaticInvocation(StaticInvocation node) => defaultExpression(node);

  @override
  visitConstructorInvocation(ConstructorInvocation node) =>
      defaultExpression(node);

  @override
  visitNot(Not node) => defaultExpression(node);

  @override
  visitLogicalExpression(LogicalExpression node) => defaultExpression(node);

  @override
  visitConditionalExpression(ConditionalExpression node) =>
      defaultExpression(node);

  @override
  visitStringConcatenation(StringConcatenation node) =>
      defaultExpression(node);

  @override
  visitIsExpression(IsExpression node) => defaultExpression(node);

  @override
  visitAsExpression(AsExpression node) => defaultExpression(node);

  @override
  visitSymbolLiteral(SymbolLiteral node) => defaultExpression(node);

  @override
  visitTypeLiteral(TypeLiteral node) => defaultExpression(node);

  @override
  visitThisExpression(ThisExpression node) => defaultExpression(node);

  @override
  visitRethrow(Rethrow node) => defaultExpression(node);

  @override
  visitThrow(Throw node) => defaultExpression(node);

  @override
  visitListLiteral(ListLiteral node) => defaultExpression(node);

  @override
  visitMapLiteral(MapLiteral node) => defaultExpression(node);

  @override
  visitAwaitExpression(AwaitExpression node) => defaultExpression(node);

  @override
  visitFunctionExpression(FunctionExpression node) => defaultExpression(node);

  @override
  visitStringLiteral(StringLiteral node) => defaultBasicLiteral(node);

  @override
  visitIntLiteral(IntLiteral node) => defaultBasicLiteral(node);

  @override
  visitDoubleLiteral(DoubleLiteral node) => defaultBasicLiteral(node);

  @override
  visitBoolLiteral(BoolLiteral node) => defaultBasicLiteral(node);

  @override
  visitNullLiteral(NullLiteral node) => defaultBasicLiteral(node);

  @override
  visitLet(Let node) => defaultExpression(node);
  
  // --- Statements ---
  @override
  defaultStatement(Statement node) => defaultTreeNode(node);

  @override
  visitInvalidStatement(InvalidStatement node) => defaultStatement(node);

  @override
  visitExpressionStatement(ExpressionStatement node) =>
      defaultStatement(node);

  @override
  visitBlock(Block node) => defaultStatement(node);

  @override
  visitEmptyStatement(EmptyStatement node) => defaultStatement(node);

  @override
  visitAssertStatement(AssertStatement node) => defaultStatement(node);

  @override
  visitLabeledStatement(LabeledStatement node) => defaultStatement(node);

  @override
  visitBreakStatement(BreakStatement node) => defaultStatement(node);

  @override
  visitWhileStatement(WhileStatement node) => defaultStatement(node);

  @override
  visitDoStatement(DoStatement node) => defaultStatement(node);

  @override
  visitForStatement(ForStatement node) => defaultStatement(node);

  @override
  visitForInStatement(ForInStatement node) => defaultStatement(node);

  @override
  visitSwitchStatement(SwitchStatement node) => defaultStatement(node);

  @override
  visitContinueSwitchStatement(ContinueSwitchStatement node) =>
      defaultStatement(node);

  @override
  visitIfStatement(IfStatement node) => defaultStatement(node);

  @override
  visitReturnStatement(ReturnStatement node) => defaultStatement(node);

  @override
  visitTryCatch(TryCatch node) => defaultStatement(node);

  @override
  visitTryFinally(TryFinally node) => defaultStatement(node);

  @override
  visitYieldStatement(YieldStatement node) => defaultStatement(node);

  @override
  visitVariableDeclaration(VariableDeclaration node) {
    _print('VariableDeclaration[name=${node.name}]');
    _depth++;
    _visitWithLabel(node.type, 'type');
    _visitWithLabel(node.initializer, 'initializer');
    _depth--;
  }

  @override
  visitFunctionDeclaration(FunctionDeclaration node) =>
      defaultStatement(node);

  // -- Members --
  @override
  defaultMember(Member node) {
    _print(node.runtimeType);
    _recurse(node);
    _depth++;
    _printMetadata(node.analyzerMetadata);
    _depth--;
  }

  @override
  visitConstructor(Constructor node) => defaultMember(node);

  @override
  visitProcedure(Procedure node) => defaultMember(node);

  @override
  visitField(Field node) => defaultMember(node);

  // -- Classes --
  @override
  defaultClass(Class node) {
    var lib = node.enclosingLibrary;
    _print('Class[name="${node.name}", lib=${_libraryToString(lib)}, '
        'abstract=${node.isAbstract}]');
    _depth++;
    _visitListWithLabel(node.typeParameters, 'typeParameter');
    _visitWithLabel(node.supertype, 'supertype');
    _visitListWithLabel(node.implementedTypes, 'implementedType');
    _visitListWithLabel(node.fields, 'field');
    _visitListWithLabel(node.constructors, 'constructor');
    _visitListWithLabel(node.procedures, 'procedure');
    _printMetadata(node.analyzerMetadata);
    _depth--;
  }

  @override
  visitNormalClass(NormalClass node) => defaultClass(node);

  @override
  visitMixinClass(MixinClass node) => defaultClass(node);

  // --- Initializers ---
  @override
  defaultInitializer(Initializer node) => defaultTreeNode(node);

  @override
  visitInvalidInitializer(InvalidInitializer node) =>
      defaultInitializer(node);

  @override
  visitFieldInitializer(FieldInitializer node) => defaultInitializer(node);

  @override
  visitSuperInitializer(SuperInitializer node) => defaultInitializer(node);

  @override
  visitRedirectingInitializer(RedirectingInitializer node) =>
      defaultInitializer(node);

  @override
  visitLocalInitializer(LocalInitializer node) => defaultInitializer(node);

  // --- Other tree nodes ---

  @override
  visitLibrary(Library node) {
    _print(_libraryToString(node));
    _recurse(node);
  }

  @override
  visitTypeParameter(TypeParameter node) => defaultTreeNode(node);

  @override
  visitFunctionNode(FunctionNode node) {
    _print('FunctionNode[asyncMarker=${node.asyncMarker}, '
        'requiredParamCount=${node.requiredParameterCount}]');
    _depth++;
    _visitListWithLabel(node.typeParameters, 'typeParameter');
    _visitListWithLabel(node.positionalParameters, 'positionalParameter');
    _visitListWithLabel(node.namedParameters, 'namedParameter');
    _visitWithLabel(node.returnType, 'returnType');
    _visitWithLabel(node.body, 'body');
    _depth--;
  }

  @override
  visitArguments(Arguments node) => defaultTreeNode(node);

  @override
  visitNamedExpression(NamedExpression node) => defaultTreeNode(node);

  @override
  visitSwitchCase(SwitchCase node) => defaultTreeNode(node);

  @override
  visitCatch(Catch node) => defaultTreeNode(node);

  @override
  visitMapEntry(MapEntry node) => defaultTreeNode(node);

  @override
  visitProgram(Program node) => defaultTreeNode(node);

  // --- DartTypes ---
  @override
  defaultDartType(DartType node) => defaultNode(node);

  @override
  visitInvalidType(InvalidType node) => defaultDartType(node);

  @override
  visitDynamicType(DynamicType node) => defaultDartType(node);

  @override
  visitVoidType(VoidType node) => defaultDartType(node);

  @override
  visitInterfaceType(InterfaceType node) => defaultDartType(node);

  @override
  visitFunctionType(FunctionType node) => defaultDartType(node);

  @override
  visitTypeParameterType(TypeParameterType node) => defaultDartType(node);

  // --- Class references ---
  @override
  defaultClassReference(Class node) {
    _print('Class reference[name="${node.name}", lib='
        '${_libraryToString(node.enclosingLibrary)}]');
  }

  @override
  visitNormalClassReference(NormalClass node) => defaultClassReference(node);

  @override
  visitMixinClassReference(MixinClass node) => defaultClassReference(node);

  // --- Member references ---

  @override
  defaultMemberReference(Member m) {
    _print('Member reference: "${m.name}" in '
        '${_libraryToString(m.enclosingLibrary)}');
  }

  @override
  visitFieldReference(Field node) => defaultMemberReference(node);

  @override
  visitConstructorReference(Constructor node) {
    _print('Constructor reference: ${node.enclosingClass}.${node.name.name}');
  }

  @override
  visitProcedureReference(Procedure node) => defaultMemberReference(node);

  // --- Name ---

  @override
  visitName(Name node) {
    if (node.isPrivate) {
      _print('Name[private, name=${node.name}, '
          'lib=${_libraryToString(node.library)}]');
    } else {
      _print('Name[public, name=${node.name}]');
    }
  }


  // --- Helper methods ---

  /// Set this before visiting, and it will be output for the very next indented
  /// line only.
  String _label = "";

  /// Visit each node in the [list], and output it with the given [label].
  ///
  /// The label for each element will have `"[$i]"` appended to the end of it,
  /// where `i` is the index of the element in the list.
  void _visitListWithLabel(List<Node> list, String label) {
    if (list == null) {
      return;
    }
    for (int i = 0; i < list.length; i++) {
      _visitWithLabel(list[i], label + '[$i]');
    }
  }

  /// Visit a [node], and output it with the given [label].
  ///
  /// [node] may be null, which makes this easier to use than
  /// `node.accept(this)`.
  void _visitWithLabel(Node node, String label) {
    _label = label;
    if (node != null) {
      node.accept(this);
    } else {
      _print("null");
    }
  }

  void _printMetadata(List<DartObject> metadata) {
    if (metadata == null) {
      return;
    }
    for (int i = 0; i < metadata.length; i++) {
      _label = 'metadata[$i]';
      _print(metadata[i]);
    }
  }

  void _recurse(Node n) {
    _depth++;
    n.visitChildren(this);
    _depth--;
  }

  String _libraryToString(Library l) {
    if (l == null) {
      return 'Library[null]';
    }
    return 'Library[name="${l.name}", importUri="${l.importUri}", '
        'loaded="${l.isLoaded}"]';
  }

  String get _indentStr {
    String result = ('│ ' * _depth) + '├ ';
    if (_label.isNotEmpty) {
       result += _label + ': ';
      _label = "";
    }
    return result;
  }

  void _print(Object o) {
    print(_indentStr + o.toString());
  }
}
