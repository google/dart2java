import 'dart:collection' show HashSet;

class Constants {
  static const operatorToMethodName = const {
    "+": "operatorPlus",
    "-": "operatorMinus",
    "*": "operatorStar",
    "/": "operatorDivide",
    "~/": "operatorTruncatedDivide",
    "%": "operatorModulus",
    "&": "operatorBitAnd",
    "|": "operatorBitOr",
    "^": "operatorBitXor",
    "<<": "operatorShiftLeft",
    ">>": "operatorShiftRight",
    "==": "operatorEqual",
    "!=": "operatorNotEqual",
    "<": "operatorLess",
    "<=": "operatorLessEqual",
    ">": "operatorGreater",
    ">=": "operatorGreaterEqual",
    "[]": "operatorAt",
    "[]=": "operatorAtPut",
    "unary-": "operatorUnaryMinus",
    "~": "operatorUnaryBitNegate"
  };

  static const String javaCallAnnotation = "JavaCall";
  static const String javaMethodAnnotation = "JavaMethod";
  static const String javaClassAnnotation = "JavaClass";
  static const String interceptorForAnnotation = "InterceptorFor";
  static const String interceptorSingletonFieldName = "INSTANCE";
  static const String helperNestedClassForStatic = "Static";
  static const String dartObjectPackage = "dart._runtime.base";
  static const String dartObjectClassname = "DartObject";
  static const String javaStaticThisIdentifier = "self";
  static const String toStringMethodName = "toString";
    
  // Source: https://docs.oracle.com/javase/specs/jls/se8/html/jls-3.html#jls-3.8
  static final reservedWords = new HashSet<String>.from([
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

  static final reservedWordPattern = new RegExp(reservedWords.join('|'));
}
