// Copyright 2016, the Dart project authors.
//
// Licensed under the Apache License, Version 2.0 (the "License");
// you may not use this file except in compliance with the License.
// You may obtain a copy of the License at
//
//     http://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing, software
// distributed under the License is distributed on an "AS IS" BASIS,
// WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// See the License for the specific language governing permissions and
// limitations under the License.

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

  static const objectMethods = const [
    "toString",
    "operatorEqual",
    "getHashCode",
    "getRuntimeType",
    "noSuchMethod"
  ];

  static const String javaCallAnnotation = "JavaCall";
  static const String javaMethodAnnotation = "JavaMethod";
  static const String javaClassAnnotation = "JavaClass";
  static const String interceptorForAnnotation = "InterceptorFor";
  static const String interceptorSingletonFieldName = "INSTANCE";
  static const String helperNestedClassForStatic = "Static";
  static const String dartObjectPackage = "dart._runtime.base";
  static const String dartHelperPackage = "dart._runtime.helpers";
  static const String dartObjectClassname = "DartObject";
  static const String javaStaticThisIdentifier = "self";
  static const String toStringMethodName = "toString";
  static const String sequencePointMethodName = "comma";
  static const String listInitializerMethodName = "_fromArguments";
  static const String topLevelClassName = "__TopLevel";
  static const String constructorMethodPrefix = "_constructor";
  static const String javaFactoryPrefix = "_new";
  static const String primitiveSpecializationSuffix = "_primitive";
  static const String dynamicHelperInvoke = "invoke";

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
