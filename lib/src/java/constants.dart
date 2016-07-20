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
}
