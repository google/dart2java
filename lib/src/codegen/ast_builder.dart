library ddc.src.codegen.ast_builder;

import 'package:analyzer/src/generated/ast.dart';
import 'package:analyzer/src/generated/scanner.dart';
import 'package:analyzer/src/generated/utilities_dart.dart';
import 'package:logging/logging.dart' as logger;

final _log = new logger.Logger('ddc.ast_builder');

// Wrappers around constructors for the dart ast.  The AstBuilder class
// provides a higher-level interface, abstracting both from the lexical
// details and some of helper classes.  The RawAstBuilder class provides
// a low-level wrapper class (below) abstracts from the lexical details
// but otherwise faithfully mirrors the construction API.
class AstBuilder {
  static SimpleIdentifier identifierFromString(String name) {
    return RawAstBuilder.identifierFromString(name);
  }

  static TypeParameter typeParameter(Identifier name, [TypeName bound = null]) {
    return RawAstBuilder.typeParameter(name, bound);
  }

  static TypeParameterList typeParameterList(List<TypeParameter> params) {
    return RawAstBuilder.typeParameterList(params);
  }

  static TypeArgumentList typeArgumentList(List<TypeName> args) {
    return RawAstBuilder.typeArgumentList(args);
  }

  static ArgumentList argumentList(List<Expression> args) {
    return RawAstBuilder.argumentList(args);
  }

  static TypeName typeName(Identifier id, List<TypeName> args) {
    TypeArgumentList argList = null;
    if (args != null && args.length > 0) argList = typeArgumentList(args);
    return RawAstBuilder.typeName(id, argList);
  }

  static FunctionTypeAlias functionTypeAlias(TypeName ret, Identifier name,
      List<TypeParameter> tParams, List<FormalParameter> params) {
    TypeParameterList tps =
        (tParams.length == 0) ? null : typeParameterList(tParams);
    FormalParameterList fps = formalParameterList(params);
    return RawAstBuilder.functionTypeAlias(ret, name, tps, fps);
  }

  static AsExpression asExp(Expression exp, TypeName type) {
    return RawAstBuilder.asExp(exp, type);
  }

  static ParenthesizedExpression parenthesizedExp(Expression exp) {
    return RawAstBuilder.parenthesizedExp(exp);
  }

  static Expression parenthesize(Expression exp) {
    if (exp is Identifier ||
        exp is ParenthesizedExpression ||
        exp is FunctionExpressionInvocation ||
        exp is MethodInvocation) return exp;
    return parenthesizedExp(exp);
  }

  static Expression application(Expression function, List<Expression> es) {
    ArgumentList args = argumentList(es);
    return RawAstBuilder.functionExpressionInvocation(function, args);
  }

  static FormalParameterList formalParameterList(List<FormalParameter> params) {
    return RawAstBuilder.formalParameterList(params);
  }

  static Block block(List<Statement> statements) {
    return RawAstBuilder.block(statements);
  }

  static MethodDeclaration blockMethodDeclaration(TypeName rt, Identifier m,
      List<FormalParameter> params, List<Statement> statements) {
    FormalParameterList fl = formalParameterList(params);
    Block b = block(statements);
    BlockFunctionBody body = RawAstBuilder.blockFunctionBody(b);
    return RawAstBuilder.methodDeclaration(rt, m, fl, body);
  }

  static FunctionDeclaration blockFunctionDeclaration(TypeName rt, Identifier f,
      List<FormalParameter> params, List<Statement> statements) {
    FunctionExpression fexp = blockFunction(params, statements);
    return RawAstBuilder.functionDeclaration(rt, f, fexp);
  }

  static FunctionExpression blockFunction(
      List<FormalParameter> params, List<Statement> statements) {
    FormalParameterList fl = formalParameterList(params);
    Block b = block(statements);
    BlockFunctionBody body = RawAstBuilder.blockFunctionBody(b);
    return RawAstBuilder.functionExpression(fl, body);
  }

  static FunctionExpression expressionFunction(
      List<FormalParameter> params, Expression body, [bool decl = false]) {
    FormalParameterList fl = formalParameterList(params);
    Token semi = (decl) ? new Token(TokenType.SEMICOLON, 0) : null;
    ExpressionFunctionBody b = RawAstBuilder.expressionFunctionBody(body, decl);
    return RawAstBuilder.functionExpression(fl, b);
  }

  static FunctionDeclarationStatement functionDeclarationStatement(
      TypeName rType, Identifier name, FunctionExpression fe) {
    var fd = RawAstBuilder.functionDeclaration(rType, name, fe);
    return RawAstBuilder.functionDeclarationStatement(fd);
  }

  static Statement returnExp([Expression e]) {
    return RawAstBuilder.returnExp(e);
  }

  // let b = e1 in e2 == (\b.e2)(e1)
  static Expression letExp(FormalParameter b, Expression e1, Expression e2) {
    FunctionExpression l = expressionFunction(<FormalParameter>[b], e2);
    return application(parenthesize(l), <Expression>[e1]);
  }

  static SimpleFormalParameter simpleFormal(Identifier v, TypeName t) {
    return RawAstBuilder.simpleFormalParameter(v, t);
  }

  static FunctionTypedFormalParameter functionTypedFormal(
      TypeName ret, Identifier v, List<FormalParameter> params) {
    FormalParameterList ps = formalParameterList(params);
    return RawAstBuilder.functionTypedFormalParameter(ret, v, ps);
  }

  static FormalParameter requiredFormal(NormalFormalParameter fp) {
    return RawAstBuilder.requiredFormalParameter(fp);
  }

  static FormalParameter optionalFormal(NormalFormalParameter fp) {
    return RawAstBuilder.optionalFormalParameter(fp);
  }

  static FormalParameter namedFormal(NormalFormalParameter fp) {
    return RawAstBuilder.namedFormalParameter(fp);
  }

  static NamedExpression namedParameter(String s, Expression e) {
    return namedExpression(s, e);
  }

  static NamedExpression namedExpression(String s, Expression e) {
    return RawAstBuilder.namedExpression(identifierFromString(s), e);
  }
}

// This class provides a low-level wrapper around the constructors for
// the AST.  It mostly simply abstracts from the lexical tokens.
class RawAstBuilder {
  static SimpleIdentifier identifierFromString(String name) {
    StringToken token = new SyntheticStringToken(TokenType.IDENTIFIER, name, 0);
    return new SimpleIdentifier(token);
  }

  static TypeParameter typeParameter(Identifier name, [TypeName bound = null]) {
    Token keyword =
        (bound == null) ? null : new KeywordToken(Keyword.EXTENDS, 0);
    return new TypeParameter(null, null, name, keyword, bound);
  }

  static TypeParameterList typeParameterList(List<TypeParameter> params) {
    Token lb = new Token(TokenType.LT, 0);
    Token rb = new Token(TokenType.GT, 0);
    return new TypeParameterList(lb, params, rb);
  }

  static TypeArgumentList typeArgumentList(List<TypeName> args) {
    Token lb = new Token(TokenType.LT, 0);
    Token rb = new Token(TokenType.GT, 0);
    return new TypeArgumentList(lb, args, rb);
  }

  static ArgumentList argumentList(List<Expression> args) {
    Token lp = new BeginToken(TokenType.OPEN_PAREN, 0);
    Token rp = new Token(TokenType.CLOSE_PAREN, 0);
    return new ArgumentList(lp, args, rp);
  }

  static TypeName typeName(Identifier id, TypeArgumentList l) {
    return new TypeName(id, l);
  }

  static FunctionTypeAlias functionTypeAlias(TypeName ret, Identifier name,
      TypeParameterList tps, FormalParameterList fps) {
    Token semi = new Token(TokenType.SEMICOLON, 0);
    Token td = new KeywordToken(Keyword.TYPEDEF, 0);
    return new FunctionTypeAlias(null, null, td, ret, name, tps, fps, semi);
  }

  static AsExpression asExp(Expression exp, TypeName type) {
    Token token = new KeywordToken(Keyword.AS, 0);
    return new AsExpression(exp, token, type);
  }

  static ParenthesizedExpression parenthesizedExp(Expression exp) {
    Token lp = new BeginToken(TokenType.OPEN_PAREN, 0);
    Token rp = new Token(TokenType.CLOSE_PAREN, 0);
    return new ParenthesizedExpression(lp, exp, rp);
  }

  static Expression functionExpressionInvocation(
      Expression function, ArgumentList es) {
    return new FunctionExpressionInvocation(function, es);
  }

  static FormalParameterList formalParameterList(List<FormalParameter> params) {
    Token lp = new BeginToken(TokenType.OPEN_PAREN, 0);
    Token rp = new Token(TokenType.CLOSE_PAREN, 0);
    bool hasOptional = params.any((p) => p.kind == ParameterKind.POSITIONAL);
    bool hasNamed = params.any((p) => p.kind == ParameterKind.NAMED);
    assert(!(hasOptional && hasNamed));
    Token ld = null;
    Token rd = null;
    if (hasOptional) {
      ld = new BeginToken(TokenType.OPEN_SQUARE_BRACKET, 0);
      rd = new Token(TokenType.CLOSE_SQUARE_BRACKET, 0);
    }
    if (hasNamed) {
      ld = new BeginToken(TokenType.OPEN_CURLY_BRACKET, 0);
      rd = new Token(TokenType.CLOSE_CURLY_BRACKET, 0);
    }
    return new FormalParameterList(lp, params, ld, rd, rp);
  }

  static Block block(List<Statement> statements) {
    Token ld = new BeginToken(TokenType.OPEN_CURLY_BRACKET, 0);
    Token rd = new Token(TokenType.CLOSE_CURLY_BRACKET, 0);
    return new Block(ld, statements, rd);
  }

  static BlockFunctionBody blockFunctionBody(Block b) {
    return new BlockFunctionBody(null, null, b);
  }

  static ExpressionFunctionBody expressionFunctionBody(Expression body,
      [bool decl = false]) {
    Token semi = (decl) ? new Token(TokenType.SEMICOLON, 0) : null;
    return new ExpressionFunctionBody(null, null, body, semi);
  }

  static FunctionDeclaration functionDeclaration(
      TypeName rt, Identifier f, FunctionExpression fexp) {
    return new FunctionDeclaration(null, null, null, rt, null, f, fexp);
  }

  static MethodDeclaration methodDeclaration(
      TypeName rt, Identifier m, FormalParameterList fl, FunctionBody body) {
    Token st = new KeywordToken(Keyword.STATIC, 0);
    return new MethodDeclaration(
        null, null, null, st, rt, null, null, m, fl, body);
  }

  static FunctionExpression functionExpression(
      FormalParameterList fl, FunctionBody body) {
    return new FunctionExpression(fl, body);
  }

  static FunctionDeclarationStatement functionDeclarationStatement(
      FunctionDeclaration fd) {
    return new FunctionDeclarationStatement(fd);
  }

  static Statement returnExp([Expression e]) {
    Token ret = new KeywordToken(Keyword.RETURN, 0);
    Token semi = new Token(TokenType.SEMICOLON, 0);
    return new ReturnStatement(ret, e, semi);
  }

  static SimpleFormalParameter simpleFormalParameter(Identifier v, TypeName t) {
    return new SimpleFormalParameter(null, <Annotation>[], null, t, v);
  }

  static FunctionTypedFormalParameter functionTypedFormalParameter(
      TypeName ret, Identifier v, FormalParameterList ps) {
    return new FunctionTypedFormalParameter(null, <Annotation>[], ret, v, ps);
  }

  static FormalParameter requiredFormalParameter(NormalFormalParameter fp) {
    return fp;
  }

  static FormalParameter optionalFormalParameter(NormalFormalParameter fp) {
    return new DefaultFormalParameter(fp, ParameterKind.POSITIONAL, null, null);
  }

  static FormalParameter namedFormalParameter(NormalFormalParameter fp) {
    return new DefaultFormalParameter(fp, ParameterKind.NAMED, null, null);
  }

  static NamedExpression namedParameter(Identifier s, Expression e) {
    return namedExpression(s, e);
  }

  static NamedExpression namedExpression(Identifier s, Expression e) {
    Label l = new Label(s, new Token(TokenType.COLON, 0));
    return new NamedExpression(l, e);
  }
}