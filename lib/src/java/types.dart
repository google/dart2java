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

import 'ast.dart';
import 'constants.dart';
import 'specialization.dart';
import 'visitor.dart';

abstract class JavaType extends Node {
  /// The class/interface name/identifier that should be generated when
  /// emitting source code or determining the file name.
  final String name;

  @override
  String toString() {
    return this.name;
  }

  JavaType toBoxedType() {
    return this;
  }

  PrimitiveType toUnboxedType() {
    return null;
  }

  TypeSpecialization get specialization =>
      new TypeSpecialization.fullyGeneric(0);

  const JavaType(this.name);

  /*=R*/ accept/*<R>*/(Visitor/*<R>*/ v) => v.visitJavaType(this);

  /// A convenience object to allow handling of methods that don't return
  /// a value.
  ///
  /// There is no "void" type in Java.
  static const VoidType void_ = const VoidType._();

  static const booleanOperators = const [
    '==',
    '!=',
    '!',
    '&',
    '^',
    '|',
    '&&',
    '||'
  ];

  /// The default superclass of all Dart classes.
  static ClassOrInterfaceType dartObject = new ClassOrInterfaceType(
      Constants.dartObjectPackage, Constants.dartObjectClassname);

  /// The Java Object type.
  static ClassOrInterfaceType object =
      new ClassOrInterfaceType("java.lang", "Object");

  /// The Java String type.
  static ClassOrInterfaceType string =
      new ClassOrInterfaceType("java.lang", "String");

  /// The boxed version of `bool`.
  static ClassOrInterfaceType javaBooleanClass =
      new ClassOrInterfaceType("java.lang", "Boolean");

  /// The boxed version of `int`.
  static ClassOrInterfaceType javaIntegerClass =
      new ClassOrInterfaceType("java.lang", "Integer");

  /// The boxed version of `double`.
  static ClassOrInterfaceType javaDoubleClass =
      new ClassOrInterfaceType("java.lang", "Double");

  /// The boxed version of `void`.
  static ClassOrInterfaceType javaVoidClass =
      new ClassOrInterfaceType("java.lang", "Void");

  /// A marker to avoid null pointer exceptions and to generate more useful
  /// debug information.
  static ClassOrInterfaceType javaNotImplemented =
      new ClassOrInterfaceType("error.class.not.", "implemented");

  static ClassOrInterfaceType letHelper = new ClassOrInterfaceType(
      Constants.dartHelperPackage, "LetExpressionHelper");

  static ClassOrInterfaceType emptyConstructorMarker =
      new ClassOrInterfaceType.nested(
          new ClassOrInterfaceType(
              Constants.dartHelperPackage, "ConstructorHelper"),
          "EmptyConstructorMarker",
          isStatic: true);

  static ClassOrInterfaceType dynamicHelper =
      new ClassOrInterfaceType("dart._runtime.helpers", "DynamicHelper");

  // Numeric types.
  // Numeric types / Integral types.

  static const integerOperators = const [
    '<',
    '<=',
    '>',
    '>=',
    '==',
    '!=',
    'unary+',
    'unary-',
    '*',
//    '/', Dart division is not truncated by default!
    '%',
    '+',
    '-',
    '++',
    '--',
    '<<',
    '>>',
    '>>>',
    '~',
    '&',
    '^',
    '|'
  ];

  /// The boolean type has exactly two values: `true` and `false`.
  static final PrimitiveType boolean =
      new PrimitiveType._("boolean", javaBooleanClass, booleanOperators);

  /// 8-bit signed two's complement integer.
  static final PrimitiveType byte =
      new PrimitiveType._("byte", javaNotImplemented, integerOperators);

  /// 16-bit signed two's complement integer.
  static final PrimitiveType short =
      new PrimitiveType._("short", javaNotImplemented, integerOperators);

  /// 32-bit signed two's complement integer.
  static final PrimitiveType int_ =
      new PrimitiveType._("int", javaIntegerClass, integerOperators);

  /// 64-bit signed two's complement integer.
  static final PrimitiveType long =
      new PrimitiveType._("long", javaNotImplemented, integerOperators);

  /// 16-bit unsigned integers representing UTF-16 code units.
  static final PrimitiveType char =
      new PrimitiveType._("char", javaNotImplemented, integerOperators);

  static const floatingPointOperators = const [
    '<',
    '<=',
    '>',
    '>=',
    '==',
    '!=',
    'unary+',
    'unary-',
    '*',
    '/',
    '%',
    '+',
    '-',
    '++',
    '--'
  ];
  // Numeric types / Floating-point types.
  /// 32-bit IEEE 754 floating-point number.
  static final PrimitiveType float =
      new PrimitiveType._("float", javaNotImplemented, floatingPointOperators);

  /// 64-bit IEEE 754 floating-point number.
  static final PrimitiveType double_ =
      new PrimitiveType._("double", javaDoubleClass, floatingPointOperators);
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
  /// A list of operators this [PrimitiveType] supports in Java. See
  /// [Constants.operatorToMethodNames] for examples.
  final List<String> operators;

  final ClassOrInterfaceType boxedType;

  @override
  ClassOrInterfaceType toBoxedType() {
    return boxedType;
  }

  @override
  PrimitiveType toUnboxedType() {
    return this;
  }

  PrimitiveType._(String name, this.boxedType, this.operators) : super(name) {
    if (boxedType != null && boxedType != JavaType.javaNotImplemented) {
      boxedType.unboxedType = this;
    }
  }
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

  final String unspecializedName;

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

  final bool omitJavaGenerics;

  // Cannot be set in constructor
  PrimitiveType unboxedType;

  /// The type specialization of this Java type.
  ///
  /// All classes (even non-generic ones) have a specialization to reduce
  /// special cases. In that case, the specialization is "fully generic" and
  /// does not append a suffix to the class/interface name.
  TypeSpecialization _specialization = new TypeSpecialization.fullyGeneric(0);

  /// A top-level type, i.e. a type that isn't nested in any other class or
  /// interface.
  ClassOrInterfaceType(this.package, String name, {this.isInterface: false})
      : isStatic = true,
        omitJavaGenerics = false,
        enclosingType = null,
        typeArguments = const [],
        unspecializedName = name,
        super(name);

  /// A nested type (either a static nested type or a non-static inner type).
  ///
  /// There are three rules pertaining to nested types:
  ///   * A static member type cannot be nested inside of a non-static inner
  ///     type.
  ///   * An interface cannot contain non-static inner types.
  ///   * A non-static inner type cannot be an interface.
  ClassOrInterfaceType.nested(ClassOrInterfaceType enclosingType, String name,
      {this.isInterface: false, this.isStatic: true})
      : package = enclosingType.package,
        omitJavaGenerics = false,
        enclosingType = enclosingType,
        typeArguments = const [],
        unspecializedName = name,
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
      {bool isInterface: false}) {
    List<String> parts = string.split(".");
    return new ClassOrInterfaceType(
        parts.take(parts.length - 1).join("."), parts.last,
        isInterface: isInterface);
  }

  ClassOrInterfaceType._copy(
      String name,
      String unspecializedName,
      this.package,
      this.isInterface,
      this.isStatic,
      this.enclosingType,
      this.typeArguments,
      this.omitJavaGenerics,
      TypeSpecialization specialization)
      : this._specialization = specialization,
        this.unspecializedName = unspecializedName,
        super(unspecializedName + specialization.classNameSuffix);

  /// Returns a copy of this [ClassOrInterfaceType] with [typeArgs] in place of
  /// the current type arguments (if any).
  ///
  /// Note that there are no arity checks. [typeArgs] may be [:null:] to
  /// retrieve a reference to the raw Java class or interface.
  ClassOrInterfaceType withTypeArguments(Iterable<JavaType> typeArgs) {
    var spec = new TypeSpecialization.fromBoxedTypes(typeArgs);
    return new ClassOrInterfaceType._copy(
        name,
        unspecializedName,
        package,
        isInterface,
        isStatic,
        enclosingType,
        typeArgs.toList() ?? const [],
        omitJavaGenerics,
        spec);
  }

  /// Returns a copy of this [ClassOrInterfaceType] with a specialization.
  ///
  /// This method may only be used when building a generic class. When
  /// translating a Dart type for an expression, variable declaration, etc.,
  /// [withTypeArguments] should be used to obtain a possibly specialized
  /// class/interface type.
  ClassOrInterfaceType withSpecialization(TypeSpecialization spec) {
    return new ClassOrInterfaceType._copy(
        name,
        unspecializedName,
        package,
        isInterface,
        isStatic,
        enclosingType,
        typeArguments,
        omitJavaGenerics,
        spec);
  }

  /// Returns a copy of this type where calling [toUnboxedType] returns [null].
  ClassOrInterfaceType notUnboxable() {
    return new ClassOrInterfaceType._copy(
        name,
        unspecializedName,
        package,
        isInterface,
        isStatic,
        enclosingType,
        typeArguments,
        omitJavaGenerics,
        specialization);
  }

  /// Returns a copy of this type which will be emitted without Java generics.
  ClassOrInterfaceType withoutJavaGenerics() {
    return new ClassOrInterfaceType._copy(
        name,
        unspecializedName,
        package,
        isInterface,
        isStatic,
        enclosingType,
        typeArguments,
        true,
        specialization);
  }

  @override
  PrimitiveType toUnboxedType() {
    return unboxedType;
  }

  @override
  TypeSpecialization get specialization => _specialization;

  /// Returns [true] if this type is generic.
  bool get isGeneric => typeArguments.isNotEmpty;

  /// Returns [true] if this type's specialization is fully generic.
  bool get isFullyGeneric => specialization.isFullyGeneric;

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

  /// The binary name of this type.
  ///
  /// This name will be unique, as long as the JVM only has one class loader.
  /// Classes from different class loaders could potentially have identical
  /// names.
  String get binaryName => enclosingType == null
      ? "$package.$name"
      : "${enclosingType.binaryName}\$name";

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
  String toString({shouldPrintFullyQualifiedName: true}) {
    String identifier =
        shouldPrintFullyQualifiedName ? fullyQualifiedName : name;

    if (isGeneric) {
      // Determine generic type arguments (the ones that are not specialized)
      var genericTypeArgs = <JavaType>[];
      for (int index = 0; index < typeArguments.length; index++) {
        if (!specialization.isSpecialized(index)) {
          genericTypeArgs.add(typeArguments[index].toBoxedType());
        }
      }

      return genericTypeArgs.isNotEmpty && !omitJavaGenerics
          ? "$identifier<${genericTypeArgs.join(", ")}>"
          : identifier;
    } else {
      return identifier;
    }
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
