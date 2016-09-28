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

import '../compiler/compiler_state.dart';
import '../compiler/runner.dart' show CompileErrorException;
import 'types.dart';
import 'constants.dart';
import 'specialization.dart';

import 'package:kernel/ast.dart' as dart;
import 'package:kernel/class_hierarchy.dart' as dart;
import 'package:kernel/repository.dart' as dart;

/// A type factory is responsible for converting Dart types to Java types.
///
/// This class is specific to the class/interface that is currently being
/// built in the java_builder. Specifically, it knows the current
/// specialization and replaces type variables by specialized types, if
/// necessary.
class TypeFactory {
  /// A reference to the Dart class that is being compiled.
  ///
  /// Can be null in case of top-level statements, which do not belong to any
  /// Dart class. Must be set otherwise!
  dart.Class dartClass;

  CompilerState compilerState;

  /// The current type specialization.
  TypeSpecialization specialization;

  TypeFactory(this.compilerState, this.specialization, {this.dartClass});

  /// Get the raw Java class used to implement a Dart class.
  ///
  /// For most Dart classes, this is a compiler-generated class. For @JavaClass
  /// classes, it is the class named in the @JavaClass metadata.
  ///
  /// The resulting [ClassOrInterfaceType] will not have any type
  /// arguments. This method should be used to retrieve a static reference to
  /// the Java class, e.g. in order to access a static field. For typed
  /// references (for `extend`s clauses or constructor invocations, for
  /// example), see [getClass].
  ///
  /// This method will return [:null:] if there is no Java class implementing
  /// [class_], which can happen if [class_] is a primitive class (like [int])
  /// or if it's a `@JavaInterface`. If [isSpecialClass] returns [:false:] for
  /// [class_], then this method will not return [:null:]
  ClassOrInterfaceType getRawClass(dart.Class class_) {
    if (compilerState.isSpecialClass(class_)) {
      return compilerState.getClassImpl(class_).javaClass;
    } else {
      String package =
          compilerState.getJavaPackageName(class_.enclosingLibrary);
      return new ClassOrInterfaceType(package, _sanitizeClassName(class_.name));
    }
  }

  /// Get the raw Java interface used to implement a Dart class.
  ///
  /// For most Dart classes, this is a compiler-generated interface. For
  /// @JavaInterface classes, it is the interface named in the @JavaClass
  /// metadata.
  ///
  /// The resulting [ClassOrInterfaceType] will not have any type
  /// arguments. This method should be used to retrieve a static reference to
  /// the Java class, e.g. in order to access a static field. For typed
  /// references (for `implements` clauses, for example), see [getInterface].
  ///
  /// This method will return [:null:] if there is no Java interface
  /// implementing [class_], which can happen if [class_] is a primitive class
  /// (like [int]) or if it's a `@JavaClass`. If [isSpecialClass] returns
  /// [:false:] for [class_], then this method will not return [:null:].
  ClassOrInterfaceType getRawInterface(dart.Class class_) {
    if (compilerState.isSpecialClass(class_)) {
      return compilerState.getClassImpl(class_).javaInterface;
    } else {
      String package =
          compilerState.getJavaPackageName(class_.enclosingLibrary);
      return new ClassOrInterfaceType(
          package, "${_sanitizeClassName(class_.name)}_interface",
          isInterface: true);
    }
  }

  /// Get the generic Java class used to implement a [dart.InterfaceType].
  ///
  /// This returns the same raw Java class as [getRawClass], except possibly
  /// with type arguments. The type arguments are recursively converted to Java
  /// type arguments via [getTypeArgument].
  ClassOrInterfaceType getClass(dart.InterfaceType type) {
    var result = getRawClass(type.classNode);
    if (result != null && type.typeArguments.isNotEmpty) {
      result =
          result.withTypeArguments(type.typeArguments.map(getTypeArgument));
    }
    return result;
  }

  /// Get the generic Java interface used to implement a [dart.InterfaceType].
  ///
  /// This returns the same raw Java interface as [getRawInterface], except
  /// possibly with type arguments. The type arguments are recursively converted
  /// to Java type arguments via [getTypeArgument].
  ClassOrInterfaceType getInterface(dart.InterfaceType type) {
    var result = getRawInterface(type.classNode);
    if (result != null && type.typeArguments.isNotEmpty) {
      result =
          result.withTypeArguments(type.typeArguments.map(getTypeArgument));
    }
    return result;
  }

  /// Convert the given Dart [type] to the Java type used when storing instances
  /// of [type].
  ///
  /// This is the Java type that should be used for variable declarations,
  /// method return values, and other contexts in which a value of type [type]
  /// is referenced. For example, for ordinary [dart.InterfaceType]s, this is
  /// the compiler-generated interface; for `@JavaClass`es, it's the Java class;
  /// and for primitive types like [int], this is `int` (rather than
  /// `java.lang.Integer`).
  JavaType getLValueType(dart.DartType type) {
    return type.accept(new _TypeImplVisitor(this, boxed: false));
  }

  TypeSpecialization getSpecialization(dart.DartType type) {
    return getLValueType(type).specialization;
  }

  /// Convert the given Dart [type] to the Java type used in a Java
  /// type-argument context.
  ///
  /// In most cases, this will agree with [getLValueType], except that primitive
  /// types like [int] will use their boxed versions here.
  /// TODO(springerm): Fix this!
  JavaType getTypeArgument(dart.DartType type) {
    return type.accept(new _TypeImplVisitor(this, boxed: true));
  }
}

/// Visitor used to recursively construct lvalue types or boxed lvalue types.
///
/// This visitor is called directly by the methods [TypeFactory.getLValueType]
/// and [TypeFactory.getTypeVariable]. The compiler will usually call
/// [TypeFactory.getLValueType] when creating variables or declaring fields.
///
/// This visitor is called indirectly from [TypeFactory.getClass] and
/// [TypeFactory.getInterface], since these two methods use
/// [TypeFactory.getTypeVariable] internally. These two methods are called
/// when generating `extends` or `implements` clauses in the compiler.
class _TypeImplVisitor extends dart.DartTypeVisitor<JavaType> {
  final TypeFactory typeFactory;

  final TypeSpecialization specialization;

  final bool boxed;

  _TypeImplVisitor(TypeFactory typeFactory, {this.boxed: false})
      : this.typeFactory = typeFactory,
        this.specialization = typeFactory.specialization;

  /// Return a [_TypeImplVisitor] with the same state as [:this:], except with
  /// `boxed = true`.
  ///
  /// Used when recursing into type arguments to avoid creating new visitors
  /// when one can be reused.
  _TypeImplVisitor boxedVisitor() =>
      boxed ? this : new _TypeImplVisitor(typeFactory, boxed: true);

  @override
  JavaType defaultDartType(dart.DartType node) =>
      throw new CompileErrorException('Cannot represent type $node');

  @override
  JavaType visitDynamicType(dart.DynamicType node) => JavaType.object;

  @override
  JavaType visitVoidType(dart.VoidType node) =>
      boxed ? JavaType.javaVoidClass : JavaType.void_;

  @override
  JavaType visitBottomType(dart.BottomType node) => JavaType.object;

  @override
  JavaType visitInterfaceType(dart.InterfaceType node) {
    var impl = typeFactory.compilerState.getClassImpl(node.classNode);
    var lValueType = boxed ? impl?.javaBoxedLValueType : impl?.javaLValueType;
    if (lValueType != null) {
      return lValueType;
    }
    var rawType = typeFactory.getRawInterface(node.classNode) ??
        typeFactory.getRawClass(node.classNode);
    if (rawType == null) {
      throw new StateError('Class ${node.classNode} has no implementation.\n'
          '_classImpls[] = '
          '${typeFactory.compilerState.getClassImpl(node.classNode)}');
    }

    if (node.typeArguments != null && node.typeArguments.isNotEmpty) {
      return rawType.withTypeArguments(node.typeArguments
          .map((arg) => arg.accept(this.boxedVisitor()) as JavaType));
    } else {
      return rawType;
    }
  }

  // TODO(andrewkrieger,springerm): Will it always work to translate a Dart type
  // parameter type to a Java type parameter type with the same name?
  @override
  JavaType visitTypeParameterType(dart.TypeParameterType node) {
    // Find index of type parameter in Dart class AST node
    int index = -1;
    if (typeFactory.dartClass != null) {
      // Not a top-level class
      index = typeFactory.dartClass.typeParameters.indexOf(node.parameter);
    }

    if (index == -1) {
      // This is a generic method type parameter
      return new TypeVariable(node.parameter.name);
    }

    // Return specialized type unless generic
    var specializedType = specialization.isSpecialized(index)
        ? specialization.getType(index)
        : new TypeVariable(node.parameter.name);

    return boxed ? specializedType.toBoxedType() : specializedType;
  }
}

// TODO(andrewkrieger): Ensure no name collisions. Currently, there shouldn't be
// any collisions introduced here. for example, even if the user defined two
// classes `__TopLevel` and `__TopLevel_`, we'd rename them like so:
//     __TopLevel  -> __TopLevel_
//     __TopLevel_ -> __TopLevel__
// so the new names wouldn't collide. But, we haven't yet proven that this
// covers all possibilities. Before moving dart2java to production, we ought to
// spend a few minutes making sure we cover all the possible collisions.
String _sanitizeClassName(String clsName) {
  if (clsName.startsWith(Constants.topLevelClassName) ||
      clsName.startsWith(Constants.reservedWordPattern)) {
    clsName += "_";
  }
  return clsName;
}
