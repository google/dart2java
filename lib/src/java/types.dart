// Copyright (c) 2016, the Dart project authors.  Please see the AUTHORS file
// for details. All rights reserved. Use of this source code is governed by a
// BSD-style license that can be found in the LICENSE file.

import 'ast.dart';
import 'visitor.dart';

abstract class JavaType extends Node {
  String name;

  @override
  String toString() {
    return this.name;
  }

  JavaType(this.name);

  /*=R*/ accept/*<R>*/(Visitor/*<R>*/ v) => v.visitJavaType(this);
}

/// Predefined types by the Java programming language and named by their
/// reserved keywords.
///
/// Primitive values do not share state with other primitive values.
class PrimitiveType extends JavaType {
  /// The boolean type has exactly two values: `true` and `false`.
  static PrimitiveType Boolean = new PrimitiveType._("boolean");

  // Numeric types.
  // Numeric types > Integral types.
  /// 8-bit signed two's complement integer.
  static PrimitiveType Byte = new PrimitiveType._("byte");

  /// 16-bit signed two's complement integer.
  static PrimitiveType Short = new PrimitiveType._("short");

  /// 32-bit signed two's complement integer.
  static PrimitiveType Int = new PrimitiveType._("int");

  /// 64-bit signed two's complement integer.
  static PrimitiveType Long = new PrimitiveType._("long");

  /// 16-bit unsigned integers representing UTF-16 code units.
  static PrimitiveType Char = new PrimitiveType._("char");

  // Numeric types > Floating-point types.
  /// 32-bit IEEE 754 floating-point number.
  static PrimitiveType Float = new PrimitiveType._("float");

  /// 64-bit IEEE 754 floating-point number.
  static PrimitiveType Double = new PrimitiveType._("double");

  PrimitiveType._(String name) : super(name);
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
  ClassOrInterfaceType parentType;

  /// A list of type parameters, in case this is a generic type.
  List<TypeVariable> typeArguments = [];

  /// Returns [true] if this type is generic.
  bool get isGeneric => typeArguments.isNotEmpty;

  ClassOrInterfaceType(String name,
      {this.isInterface, this.parentType, this.typeArguments})
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
