// Copyright (c) 2016, the Dart project authors.  Please see the AUTHORS file
// for details. All rights reserved. Use of this source code is governed by a
// BSD-style license that can be found in the LICENSE file.

import 'ast.dart';
import 'constants.dart';
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

  /// The default superclass of all Dart classes.
  static ClassOrInterfaceType dartObject = new ClassOrInterfaceType(
    Constants.dartObjectPackage, Constants.dartObjectClassname);

  /// The Java Object type.
  static ClassOrInterfaceType object =
      new ClassOrInterfaceType("java.lang", "Object");

  /// The Java String type.
  static ClassOrInterfaceType string =
      new ClassOrInterfaceType("java.lang", "String");

  static ClassOrInterfaceType javaBooleanClass =
      new ClassOrInterfaceType("java.lang", "Boolean");

  static ClassOrInterfaceType javaIntegerClass =
      new ClassOrInterfaceType("java.lang", "Integer");

  static ClassOrInterfaceType javaDoubleClass =
      new ClassOrInterfaceType("java.lang", "Double");

  static ClassOrInterfaceType letHelper = new ClassOrInterfaceType(
      Constants.dartHelperPackage, "LetExpressionHelper");

  static ClassOrInterfaceType emptyConstructorMarker =
      new ClassOrInterfaceType.nested(
          new ClassOrInterfaceType(
              Constants.dartHelperPackage, "ConstructorHelper"),
          "EmptyConstructorMarker",
          isStatic: true);

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

  static Map<JavaType, String> genericSpecializations = {
    javaBooleanClass: "_bool",
    int_: "_int",
    javaDoubleClass: "_double"
  };

  static bool hasGenericSpecialization(Iterable<JavaType> typeArguments) {
    return typeArguments.length == 1 
      && genericSpecializations.containsKey(typeArguments.single);
  }

  static String getGenericImplementation(Iterable<JavaType> typeArguments) {
    if (hasGenericSpecialization(typeArguments)) {
      return genericSpecializations[typeArguments.single];
    } else {
      return "Generic";
    }
  }
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

  /// The package containing this class or interface.
  ///
  /// This is only the package; it does not contain any enclosing classes. To
  /// access the qualified name, see [fullyQualifiedName] or
  /// [packageRelativeName].
  final String package;

  /// Indicates whether this type is an interface type. If not, then it is
  /// a class type.
  final bool isInterface;

  /// Indicates whether this class is a static class (either top-level, or a
  /// static nested class).
  ///
  /// Note that static classes cannot be enclosed in non-static inner classes,
  /// so if this flag is true, then it must also be true on [enclosingType],
  /// [enclosingType.enclosingType], etc.
  final bool isStatic;

  /// A reference to the type which this type is a member of or `null` if there
  /// is none.
  final ClassOrInterfaceType enclosingType;

  /// A list of type arguments, in case this is a generic type.
  final List<JavaType> typeArguments;

  /// A top-level type, i.e. a type that isn't nested in any other class or
  /// interface.
  ClassOrInterfaceType(this.package, String name,
      {this.isInterface: false, this.typeArguments: const []})
      : isStatic = true,
        enclosingType = null,
        super(name);

  /// A nested type (either a static nested type or a non-static inner type).
  ///
  /// There are three rules pertaining to nested types:
  ///   * A static member type cannot be nested inside of a non-static inner
  ///     type.
  ///   * An interface cannot contain non-static inner types.
  ///   * A non-static inner type cannot be an interface.
  ClassOrInterfaceType.nested(ClassOrInterfaceType enclosingType, String name,
      {this.isInterface: false,
      this.isStatic: true,
      this.typeArguments: const []})
      : package = enclosingType.package,
        enclosingType = enclosingType,
        super(name) {
    // Require an enclosing class when using this constructor.
    assert(enclosingType != null);

    // Static classes cannot be nested inside of inner classes.
    assert(!(isStatic && !enclosingType.isStatic));

    // Interfaces cannot be non-static.
    assert(!(isInterface && !isStatic));

    // Non-static inner classes cannot be nested inside of interfaces.
    assert(!(!isStatic && enclosingType.isInterface));
  }

  /// Parse a fully-qualified type name, assuming that the type is a top-level
  /// type.
  ///
  /// Everything in [string] before the last '.' will be used as the package
  /// name, and the identifier after the last '.' will be the class name.
  factory ClassOrInterfaceType.parseTopLevel(String string,
      {bool isInterface: false, List<JavaType> typeArguments: const []}) {
    List<String> parts = string.split(".");
    return new ClassOrInterfaceType(
        parts.take(parts.length - 1).join("."), parts.last,
        isInterface: isInterface, typeArguments: typeArguments);
  }

  ClassOrInterfaceType._copy(String name, this.package, this.isInterface, 
    this.isStatic, this.enclosingType, this.typeArguments) : super(name);

  ClassOrInterfaceType withTypeArguments(List<JavaType> typeArgs) {
    return new ClassOrInterfaceType._copy(name, package, isInterface, isStatic,
      enclosingType, typeArgs);
  }

  /// Returns [true] if this type is generic.
  bool get isGeneric => typeArguments.isNotEmpty;

  /// The name of this type, relative to [package].
  ///
  /// For top-level types, this is just [name].
  ///
  /// For member types, it includes the names of the enclosing types.
  String get packageRelativeName => enclosingType == null
      ? name
      : "${enclosingType.packageRelativeName}.$name";

  /// The fully-qualified name of this type.
  String get fullyQualifiedName => "$package.$packageRelativeName";

  /// All type arguments in scope in this class.
  ///
  /// If this class is a non-static inner class, then the type arguments of its
  /// enclosing class(es) are also in scope. Otherwise (if this class is static,
  /// either because it's top-level or is a static nested class), only the
  /// [typeArguments] for the class itself are in scope.
  List<JavaType> get allTypeArguments {
    if (isStatic) {
      return typeArguments;
    } else {
      List<JavaType> args = enclosingType.allTypeArguments.toList();
      return args..addAll(typeArguments);
    }
  }

  @override
  String toString() {
    String identifier = fullyQualifiedName;

    if (isGeneric) {
      if (JavaType.hasGenericSpecialization(typeArguments)) {
        return identifier + "." 
          + JavaType.getGenericImplementation(typeArguments);
      }

      String typeArgs = typeArguments.map(
        (a) => unboxedToBoxedType(a).toString()).join(", ");
      return "$identifier<$typeArgs>";
    } else {
      return identifier;
    }
  }
}

JavaType unboxedToBoxedType(JavaType unboxed) {
  if (unboxed == JavaType.int_) {
    return JavaType.javaIntegerClass;
  } else if (unboxed == JavaType.double_) {
    return JavaType.javaDoubleClass;
  } else if (unboxed == JavaType.boolean) {
    return JavaType.javaBooleanClass;
  } else {
    return unboxed;
  }
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
