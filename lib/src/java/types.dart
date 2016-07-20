// Copyright (c) 2016, the Dart project authors.  Please see the AUTHORS file
// for details. All rights reserved. Use of this source code is governed by a
// BSD-style license that can be found in the LICENSE file.

import 'ast.dart';
import 'visitor.dart';

abstract class JavaType extends Node {
  final String name;

  @override
  String toString() {
    return this.name;
  }

  const JavaType(this.name);

  /*=R*/ accept/*<R>*/(Visitor/*<R>*/ v) => v.visitJavaType(this);

  /// A convenience object to allow handling of methods that don't return
  /// a value.
  ///
  /// There is no "void" type in Java.
  static const VoidType void_ = const VoidType._();

  /// The boolean type has exactly two values: `true` and `false`.
  static const PrimitiveType boolean = const PrimitiveType._("boolean");

  /// The Java Object type.
  static ClassOrInterfaceType object = new ClassOrInterfaceType("Object");

  /// The Java String type.
  static ClassOrInterfaceType string = new ClassOrInterfaceType("String");

  // Numeric types.
  // Numeric types / Integral types.
  /// 8-bit signed two's complement integer.
  static const PrimitiveType byte = const PrimitiveType._("byte");

  /// 16-bit signed two's complement integer.
  static const PrimitiveType short = const PrimitiveType._("short");

  /// 32-bit signed two's complement integer.
  static const PrimitiveType int_ = const PrimitiveType._("int");

  /// 64-bit signed two's complement integer.
  static const PrimitiveType long = const PrimitiveType._("long");

  /// 16-bit unsigned integers representing UTF-16 code units.
  static const PrimitiveType char = const PrimitiveType._("char");

  // Numeric types / Floating-point types.
  /// 32-bit IEEE 754 floating-point number.
  static const PrimitiveType float = const PrimitiveType._("float");

  /// 64-bit IEEE 754 floating-point number.
  static const PrimitiveType double_ = const PrimitiveType._("double");
}

/// A convenience class to allow handling of methods that don't return a value.
class VoidType extends JavaType {
  const VoidType._() : super("void");
}

/// Predefined types by the Java programming language and named by their
/// reserved keywords.
///
/// Primitive values do not share state with other primitive values.
class PrimitiveType extends JavaType {
  const PrimitiveType._(String name) : super(name);
}

// TODO(stanm): consider the null type.
abstract class ReferenceType extends JavaType {
  ReferenceType(String name) : super(name);
}

class ClassOrInterfaceType extends ReferenceType {
  /// Indicates whether this type is an interface type. If not, then it is
  /// a class type.
  bool isInterface;

  /// A reference to the type which this type is a member of or `null` if there
  /// is none.
  ClassOrInterfaceType enclosingType;

  /// A list of type parameters, in case this is a generic type.
  List<TypeVariable> typeArguments;

  /// Returns [true] if this type is generic.
  bool get isGeneric => typeArguments.isNotEmpty;

  ClassOrInterfaceType(String name,
      {this.isInterface: false,
      this.enclosingType: null,
      this.typeArguments: const []})
      : super(name);
}

class TypeVariable extends ReferenceType {
  TypeVariable(String name) : super(name);
}

class ArrayType extends ReferenceType {
  /// The type of a single element of an instance of this [ArrayType].
  ///
  /// Can be [PrimitiveType], [ClassOrInterfaceType], or [TypeVariable].
  JavaType elementType;

  /// The dimension of this [ArrayType], e.g. 2 for [int[][]].
  int dimension;

  ArrayType(JavaType elementType, int dimension)
      : super("${elementType.name}" + "[]" * dimension) {
    this.elementType = elementType;
    this.dimension = dimension;
  }
}
