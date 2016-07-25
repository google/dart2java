import 'dart:collection' show HashSet;

class Constants {
  static const operatorToMethodName = const {
    "+": "operatorPlus",
    "-": "operatorMinus",
    "*": "operatorMultiply",
    "/": "operatorDivide",
    "%": "operatorModulus",
    "==": "operatorEqual",
    "!=": "operatorNotEqual",
    "<": "operatorLess",
    "<=": "operatorLessEqual",
    ">": "operatorGreater",
    ">=": "operatorGreaterEqual",
    "[]": "operatorAt",
    "unary-": "operatorUnaryMinus"
  };

  static const String javaCallAnnotation = "JavaCall";
  static const String javaClassAnnotation = "JavaClass";
  static const String interceptorForAnnotation = "InterceptorFor";

  static const String javaStaticThisIdentifier = "self";

  // Source: https://docs.oracle.com/javase/specs/jls/se8/html/jls-3.html#jls-3.8
  static final Set<String> reservedWords = new HashSet.from(const [
    // Keywords
    "abstract",
    "continue",
    "for",
    "new",
    "switch",
    "assert",
    "default",
    "if",
    "package",
    "synchronized",
    "boolean",
    "do",
    "goto",
    "private",
    "this",
    "break",
    "double",
    "implements",
    "protected",
    "throw",
    "byte",
    "else",
    "import",
    "public",
    "throws",
    "case",
    "enum",
    "instanceof",
    "return",
    "transient",
    "catch",
    "extends",
    "int",
    "short",
    "try",
    "char",
    "final",
    "interface",
    "static",
    "void",
    "class",
    "finally",
    "long",
    "strictfp",
    "volatile",
    "const",
    "float",
    "native",
    "super",
    "while",

    // Literals
    "true",
    "false",
    "null",
  ]);

  static final RegExp reservedWordPattern = new RegExp(reservedWords.join('|'));
}
