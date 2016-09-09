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

import 'dart:collection' show HashMap;

import 'package:kernel/ast.dart' as dart;
import 'package:kernel/class_hierarchy.dart' as dart;
import 'package:kernel/repository.dart' as dart;
import 'package:path/path.dart' as path;

import '../java/types.dart' as java;
import '../java/constants.dart' as java;
import 'compiler.dart' show CompilerOptions;
import 'runner.dart' show CompileErrorException;

/// Describes how a Dart class is implemented (e.g., how to represent it as a
/// Java variable, how to call methods on it, etc.).
///
/// This data structure is only ever created for `@JavaClass` classes, including
/// the core types `Object`, `String`, `bool`, `num`, `int`, and `double`.
/// Ordinary user-defined classes don't need a [ClassImpl] entry.
class ClassImpl {
  /// The Java class that implements this Dart class.
  ///
  /// This is the class that should be extended by subclasses, and it's the Java
  /// classes whose constructors should be called when executing a generative
  /// constructor for this Dart class.
  ///
  /// This field may be [:null:] if this Dart class doesn't have a Java class
  /// implementation (e.g. if the Dart class is a primitive class or a
  /// `@JavaInterface`). If so, then other Dart classes cannot extend this
  /// class.
  final java.ClassOrInterfaceType javaClass;

  /// The Java interface that implements this Dart class.
  ///
  /// This is the interface that all instances of the Dart type must implement.
  /// This is the Java type that should be used when storing a value of this
  /// Dart class (see [getLValueType] for details), and it's the type that
  /// should be added in the Java `implements` clause when another Dart class
  /// implements this Dart class.
  ///
  /// This field may be [:null:] if this Dart class doesn't have a Java
  /// interface implementation (e.g. if the Dart class is a primitive class or a
  /// `@JavaClass`). If so, then other Dart classes cannot implement this class.
  final java.ClassOrInterfaceType javaInterface;

  /// The Java type used when storing a value of this dart type.
  ///
  /// This can be a Java primitive or reference type, or [:null:]. Even for most
  /// `@JavaClass`es and `@JavaInterface`s, it should be [:null:] (in which
  /// case, the Java class/interface from the metadata will be used for storing
  /// values of the dart type). The primitive classes like [int] should use
  /// their Java primitive versions (like `int`) here. There are currently a few
  /// special cases:
  ///
  /// * [num] uses `java.lang.Number` here. Any reference in Dart to a value of
  ///   type [num] should go through `java.lang.Number`.
  /// * [Object] uses `java.lang.Object` here. [Object] is treated as if it has
  ///   both an `@JavaClass` and an `@JavaInterface` annotation, but we use the
  ///   class `java.lang.Object` for lvalue references (whereas we'd normally
  ///   use the interface). The reason is that `@JavaClass`es extend
  ///   `java.lang.Object` but don't implement `Object_interface`, so we can't
  ///   correctly store a reference to an `@JavaClass` in a field of Java static
  ///   type `Object_interface`.
  final java.JavaType javaLValueType;

  /// Like [javaLValueType], but used when we need a reference type (e.g. in
  /// type arguments).
  ///
  /// For the special cases where [javaLValueType] is already a reference type,
  /// this field should point to the same reference type. For the primitive
  /// types like [int] that actually use Java primitive types for
  /// [javaLValueType], this should point to the Java boxed version of that Java
  /// primitive type.
  final java.ClassOrInterfaceType javaBoxedLValueType;

  /// The Java class containing implementations of the instance methods on this
  /// class.
  ///
  /// May be [:null:]. All classes with a [ClassImpl] must be `@JavaClass`
  /// classes. These classes can use helper methods or map Dart method names
  /// to Java method names.
  final java.ClassOrInterfaceType helperClass;

  /// Maps Dart method names to Java method names (using an @JavaMethod
  /// annotation).
  ///
  /// This is sometimes necessary to make methods available in
  /// Dart that are not valid Dart method names.
  final Map<String, String> mappedMethodNames;

  /// Create a [ClassImpl] record for a `@JavaClass`.
  ///
  /// The fields [javaInterface], [javaBoxedClass], and [javaReferenceType] will
  /// all be [:null:]. Unless otherwise specified, [helperClass] will default to
  /// `ObjectHelper`.
  ClassImpl.forJavaClass(this.javaClass,
      {java.ClassOrInterfaceType helperClass})
      : javaInterface = null,
        javaLValueType = null,
        javaBoxedLValueType = null,
        helperClass = helperClass ??
            new java.ClassOrInterfaceType(
                java.Constants.dartHelperPackage, "ObjectHelper"),
        mappedMethodNames = new HashMap();

  /// Create a [ClassImpl] record for a `@JavaInterface`.
  ///
  /// The fields [javaClass], [javaBoxedClass], and [javaReferenceType] will
  /// all be [:null:]. Unless otherwise specified, [helperClass] will default to
  /// `ObjectHelper`.
  ClassImpl.forJavaInterface(this.javaInterface,
      {java.ClassOrInterfaceType helperClass})
      : javaClass = null,
        javaLValueType = null,
        javaBoxedLValueType = null,
        helperClass = helperClass ??
            new java.ClassOrInterfaceType(
                java.Constants.dartHelperPackage, "ObjectHelper"),
        mappedMethodNames = new HashMap();

  /// Create a [ClassImpl] record for a primitive type.
  ///
  /// The fields [javaClass] and [javaInterface] will be [:null:]. Note that
  /// this constructor is not restricted to strict Java primitive types ([int],
  /// [bool], and [double]). It's also used by Dart [num], with
  /// `java.lang.Number` passed for the first two arguments.
  ClassImpl.forPrimitive(
      this.javaLValueType, this.javaBoxedLValueType, this.helperClass)
      : javaClass = null,
        javaInterface = null,
        mappedMethodNames = new HashMap();

  /// Create a ClassImpl record for an unusual type.
  ///
  /// Prefer to use one of the other constructors instead. If we have to use
  /// this constructor in more than a few cases, we should revisit the object
  /// model, and perhaps add another "structured" constructor.
  ClassImpl.fromFields(
      {this.javaClass,
      this.javaInterface,
      this.javaLValueType,
      this.javaBoxedLValueType,
      this.helperClass})
      : mappedMethodNames = new HashMap() {
    assert((javaLValueType == null && javaBoxedLValueType == null) ||
        (javaLValueType != null && javaBoxedLValueType != null));
  }

  /// Intended for debugging only.
  String toString() => '$ClassImpl{\n'
      '  javaClass="$javaClass"\n'
      '  javaInterface="$javaInterface"\n'
      '  javaLValueType="$javaLValueType"\n'
      '  javaBoxedLValueType="$javaBoxedLValueType"\n'
      '  helperClass="$helperClass"\n'
      '  mappedMethodNames="$mappedMethodNames"\n'
      '}';
}

class CompilerState {
  final _classImpls = new HashMap<dart.Class, ClassImpl>();

  final CompilerOptions options;
  final dart.Repository repository;

  dart.Class objectClass;
  dart.Class boolClass;
  dart.Class intClass;
  dart.Class doubleClass;
  dart.Class stringClass;
  dart.Class listClass;
  dart.Class mapClass;
  dart.Class numClass;

  dart.Program _program;
  dart.ClassHierarchy _classHierarchy;

  CompilerState(
      this.options, this.repository, Iterable<dart.Class> classesToCompile) {
    objectClass = getDartClass("dart:core", "Object");
    boolClass = getDartClass("dart:core", "bool");
    intClass = getDartClass("dart:core", "int");
    doubleClass = getDartClass("dart:core", "double");
    stringClass = getDartClass("dart:core", "String");
    listClass = getDartClass("dart:core", "List");
    mapClass = getDartClass("dart:core", "Map");
    numClass = getDartClass("dart:core", "num");

    // Keep _classImpls in sync with DynamicHelper.java!
    _classImpls.addAll({
      objectClass: new ClassImpl.fromFields(
          javaClass: java.JavaType.dartObject,
          javaInterface: new java.ClassOrInterfaceType(
              'dart._runtime.base', 'DartObject_interface', isInterface: true),
          javaLValueType: java.JavaType.object,
          javaBoxedLValueType: java.JavaType.object,
          helperClass: new java.ClassOrInterfaceType(
              java.Constants.dartHelperPackage, "ObjectHelper")),
      boolClass: new ClassImpl.forPrimitive(
          java.JavaType.boolean,
          java.JavaType.javaBooleanClass,
          new java.ClassOrInterfaceType(
              java.Constants.dartHelperPackage, "BoolHelper")),
      intClass: new ClassImpl.forPrimitive(
          java.JavaType.int_,
          java.JavaType.javaIntegerClass,
          new java.ClassOrInterfaceType(
              java.Constants.dartHelperPackage, "IntegerHelper")),
      doubleClass: new ClassImpl.forPrimitive(
          java.JavaType.double_,
          java.JavaType.javaDoubleClass,
          new java.ClassOrInterfaceType(
              java.Constants.dartHelperPackage, "DoubleHelper")),
      stringClass: new ClassImpl.forJavaClass(
          new java.ClassOrInterfaceType("java.lang", "String"),
          helperClass: new java.ClassOrInterfaceType(
              java.Constants.dartHelperPackage, "StringHelper")),
      numClass: new ClassImpl.forPrimitive(
          new java.ClassOrInterfaceType("java.lang", "Number"),
          new java.ClassOrInterfaceType("java.lang", "Number"),
          new java.ClassOrInterfaceType(
              java.Constants.dartHelperPackage, "NumberHelper")),
    });

    initializeJavaClasses(classesToCompile);

    _program = new dart.Program(repository.libraries);
    _classHierarchy = new dart.ClassHierarchy(_program);
  }

  /// Find classes that are annotated with @JavaClass and add them
  /// to _classImpls.
  void initializeJavaClasses(Iterable<dart.Class> classes) {
    for (var cls in classes) {
      initializeJavaClass(cls);
    }

    // Initialize SDK classes
    initializeJavaClass(listClass);
    initializeJavaClass(mapClass);
  }

  /// Find methods that have a Java implementation. Add them to _classImpls.
  /// Then, go through all methods of these classes and look for @JavaMethod
  /// annotations.
  void initializeJavaClass(dart.Class cls) {
    String fullClassName =
        getSimpleAnnotation(cls, java.Constants.javaClassAnnotation);

    if (fullClassName != null) {
      // This class is an @JavaClass
      _classImpls[cls] = new ClassImpl.forJavaClass(
          new java.ClassOrInterfaceType.parseTopLevel(fullClassName));
    }

    bool checkMethods = hasJavaImpl(cls) || fullClassName != null;
    if (checkMethods) {
      // Add method mappings
      for (dart.Procedure proc in cls.procedures) {
        String mappedMethodName =
            getSimpleAnnotation(proc, java.Constants.javaMethodAnnotation);

        if (mappedMethodName != null) {
          // This method should have a different name in Dart
          _classImpls[cls].mappedMethodNames[proc.name.name] = mappedMethodName;
        }
      }
    }
  }

  bool isSubclassOf(dart.Class subclass, dart.Class superclass) {
    return _classHierarchy.isSubclassOf(subclass, superclass);
  }

  /// Assuming that [node] has a single annotation of type [annotation] and
  /// that annotation has a single String parameter, return the parameter,
  /// otherwise return null.
  String getSimpleAnnotation(dart.TreeNode node, String annotation,
      [String fieldName = "name"]) {
    // TODO(springerm): Try to use DartTypes here instead of Strings
    // TODO(springerm): Potential duplication with method in java_builder
    var obj = (node as dynamic)
        .analyzerMetadata
        .firstWhere((i) => i.type.toString() == annotation, orElse: () => null);
    return obj?.getField(fieldName)?.toStringValue();
  }

  /// Get the [Library] object for the library named by [libraryUri], loaded to
  /// at least reference level.
  ///
  /// The [libraryUri] may be a relative or absolute file path, or a URI string
  /// with a `dart:`, `package:` or `file:` scheme.
  dart.Library getDartLibrary(String libraryUri) {
    dart.Library library = repository.getLibrary(libraryUri);
    if (!library.isLoaded) {
      throw new CompileErrorException("Library $libraryUri not loaded");
    }
    return library;
  }

  /// Look up a reference to the class with name [className] in the library
  /// specified by [libraryUri], or [:null:] if the library does not declare a
  /// class by the given name.
  dart.Class getDartClass(String libraryUri, String className) {
    dart.Library lib = getDartLibrary(libraryUri);
    return lib.classes
        .firstWhere((dart.Class c) => c.name == className, orElse: () => null);
  }

  /// Get a Java package name for a Dart Library.
  ///
  /// The package name always has at least one identifier in it (it will never
  /// be the empty string). The package name is essentially just
  /// [options.packagePrefix] plus the relative path from [options.buildRoot]
  /// to the [library] source file (with '/' replaced by '.', of course).
  String getJavaPackageName(dart.Library library) {
    var packageParts = <String>[];

    Uri uri = library.importUri;

    // Add parts to the package name, depending on the URI scheme.
    switch (uri.scheme) {
      case 'file':
        String libraryPath = uri.toFilePath();
        String relativePath =
            path.relative(libraryPath, from: options.buildRoot);

        if (!path.isWithin(options.buildRoot, libraryPath)) {
          throw new CompileErrorException(
              'All sources must be inside build-root.');
        }
        packageParts.addAll(_splitPackagePrefix(options.filePackagePrefix));
        packageParts.addAll(path.split(path.withoutExtension(relativePath)));
        break;

      case 'dart':
        // 'dart:core' will become 'dart.core'.
        packageParts.add('dart');
        packageParts.add(uri.path);
        break;

      case 'package':
        throw new CompileErrorException('Package imports not yet implemented.');

      default:
        throw new CompileErrorException('Unrecognized library URI scheme: '
            '${library.importUri}');
    }

    // TODO(andrewkrieger): Sanitize and validate package names.
    return packageParts.join('.');
  }

  /// Tests whether the given [dart.Class] is a primitive class (i.e. one of
  /// [int], [double], or [bool]).
  bool isPrimitiveClass(dart.Class class_) {
    return _classImpls[class_]?.javaLValueType is java.PrimitiveType;
  }

  /// Tests whether the given [dart.DartType] is one of the primitive class
  /// types ([int], [double], [bool], or [:void:]).
  bool isPrimitiveType(dart.DartType type) {
    if (type is dart.InterfaceType) {
      return isPrimitiveClass(type.classNode);
    } else if (type is dart.VoidType) {
      return true;
    } else {
      return false;
    }
  }

  /// Get the raw Java class used to implement a Dart class.
  ///
  /// For most Dart classes, this is a compiler-generated class. For @JavaClass
  /// classes, it is the class named in the @JavaClass metadata.
  ///
  /// The resulting [java.ClassOrInterfaceType] will not have any type
  /// arguments. This method should be used to retrieve a static reference to
  /// the Java class, e.g. in order to access a static field. For typed
  /// references (for `extend`s clauses or constructor invocations, for
  /// example), see [getClass].
  ///
  /// This method will return [:null:] if there is no Java class implementing
  /// [class_], which can happen if [class_] is a primitive class (like [int])
  /// or if it's a `@JavaInterface`. If [isSpecialClass] returns [:false:] for
  /// [class_], then this method will not return [:null:]
  java.ClassOrInterfaceType getRawClass(dart.Class class_) {
    var impl = _classImpls[class_];
    if (impl != null) {
      return impl.javaClass;
    } else {
      String package = getJavaPackageName(class_.enclosingLibrary);
      return new java.ClassOrInterfaceType(
          package, _sanitizeClassName(class_.name));
    }
  }

  /// Get the raw Java interface used to implement a Dart class.
  ///
  /// For most Dart classes, this is a compiler-generated interface. For
  /// @JavaInterface classes, it is the interface named in the @JavaClass
  /// metadata.
  ///
  /// The resulting [java.ClassOrInterfaceType] will not have any type
  /// arguments. This method should be used to retrieve a static reference to
  /// the Java class, e.g. in order to access a static field. For typed
  /// references (for `implements` clauses, for example), see [getInterface].
  ///
  /// This method will return [:null:] if there is no Java interface
  /// implementing [class_], which can happen if [class_] is a primitive class
  /// (like [int]) or if it's a `@JavaClass`. If [isSpecialClass] returns
  /// [:false:] for [class_], then this method will not return [:null:].
  java.ClassOrInterfaceType getRawInterface(dart.Class class_) {
    var impl = _classImpls[class_];
    if (impl != null) {
      return impl.javaInterface;
    } else {
      String package = getJavaPackageName(class_.enclosingLibrary);
      return new java.ClassOrInterfaceType(
          package, "${_sanitizeClassName(class_.name)}_interface",
          isInterface: true);
    }
  }

  /// Get the generic Java class used to implement a [dart.InterfaceType].
  ///
  /// This returns the same raw Java class as [getRawClass], except possibly
  /// with type arguments. The type arguments are recursively converted to Java
  /// type arguments via [getTypeArgument].
  java.ClassOrInterfaceType getClass(dart.InterfaceType type) {
    var result = getRawClass(type.classNode);
    if (result != null && type.typeArguments.isNotEmpty) {
      result = result
          .withTypeArguments(type.typeArguments.map(getTypeArgument).toList());
    }
    return result;
  }

  /// Get the generic Java interface used to implement a [dart.InterfaceType].
  ///
  /// This returns the same raw Java interface as [getRawInterface], except
  /// possibly with type arguments. The type arguments are recursively converted
  /// to Java type arguments via [getTypeArgument].
  java.ClassOrInterfaceType getInterface(dart.InterfaceType type) {
    var result = getRawInterface(type.classNode);
    if (result != null && type.typeArguments.isNotEmpty) {
      result = result
          .withTypeArguments(type.typeArguments.map(getTypeArgument).toList());
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
  java.JavaType getLValueType(dart.DartType type) {
    return type.accept(new _TypeImplVisitor(this, boxed: false));
  }

  /// Convert the given Dart [type] to the Java type used in a Java
  /// type-argument context.
  ///
  /// In most cases, this will agree with [getLValueType], except that primitive
  /// types like [int] will use their boxed versions here.
  java.ReferenceType getTypeArgument(dart.DartType type) {
    return type.accept(new _TypeImplVisitor(this, boxed: true));
  }

  /// Gets the Java class used to contain the top-level methods and fields in a
  /// Dart library.
  java.ClassOrInterfaceType getTopLevelClass(dart.Library library) {
    String package = getJavaPackageName(library);
    return new java.ClassOrInterfaceType(
        package, java.Constants.topLevelClassName);
  }

  /// Check whether a Dart class is a "special" class: a `@JavaClass`,
  /// `@JavaInterface`, or a primitive class.
  bool isSpecialClass(dart.Class class_) {
    return _classImpls[class_] != null;
  }

  /// Checks whether a Dart class is a @JavaClass.
  bool isJavaClass(dart.Class class_) {
    return _classImpls[class_]?.javaClass != null;
  }

  /// Checks whether a method invocation needs to go through a helper function.
  ///
  /// Currently, all instance methods on `Object`, `String`, `bool`, `num`,
  /// `int`, and `double` go through helper methods, as does any instance method
  /// invocation on a `@JavaClass`. Eventually, some methods on `@JavaClass`
  /// might not go through helper methods; they might use the underlying Java
  /// instance methods instead.
  bool usesHelperFunction(dart.Class receiverClass, String method) {
    // TODO(andrewkrieger): #implementDynamic We'll probably want to use helper
    // functions for dcalls.
    return _classImpls.containsKey(receiverClass) &&
        _classImpls[receiverClass].helperClass != null;
  }

  bool hasJavaImpl(dart.Class receiverClass) {
    return _classImpls.containsKey(receiverClass);
  }

  String getJavaMethodName(dart.Class receiverClass, String dartMethod) {
    if (!_classImpls.containsKey(receiverClass)) {
      throw new CompileErrorException(
          "No implementation class defined for $receiverClass");
    }

    return _classImpls[receiverClass].mappedMethodNames[dartMethod] ??
        dartMethod;
  }

  /// If [receiverClass] has any methods that need to be invoked via a helper
  /// function, returns the Java class enclosing the helper function.
  ///
  /// This method should only be called if
  /// `hasJavaImpl(reveiverClass)` returns true. Always check [hasJavaImpl]
  /// before calling this method!
  java.ClassOrInterfaceType getHelperClass(dart.Class receiverClass) {
    assert(_classImpls[receiverClass] != null);
    return _classImpls[receiverClass].helperClass;
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
  if (clsName.startsWith(java.Constants.topLevelClassName) ||
      clsName.startsWith(java.Constants.reservedWordPattern)) {
    clsName += "_";
  }
  return clsName;
}

Iterable<String> _splitPackagePrefix(String package) {
  // Omit empty parts, to handle cases like `packagePrefix == "org.example."`
  // or `packagePrefix == ""`.
  return package.split('.').where((String part) => part.isNotEmpty);
}

/// Visitor used to recursively construct lvalue types or boxed lvalue types.
///
/// This visitor is called directly by the methods [CompilerState.getLValueType]
/// and [CompilerState.getTypeVariable]. The compiler will usually call
/// [Compilerstate.getLValueType] when creating variables or declaring fields.
///
/// This visitor is called indirectly from [CompilerState.getClass] and
/// [CompilerState.getInterface], since these two methods use
/// [CompilerState.getTypeVariable] internally. These two methods are called
/// when generating `extends` or `implements` clauses in the compiler.
class _TypeImplVisitor extends dart.DartTypeVisitor<java.JavaType> {
  final CompilerState compilerState;
  final bool boxed;

  _TypeImplVisitor(this.compilerState, {this.boxed: false});

  /// Return a [_TypeImplVisitor] with the same state as [:this:], except with
  /// `boxed = true`.
  ///
  /// Used when recursing into type arguments to avoid creating new visitors
  /// when one can be reused.
  _TypeImplVisitor boxedVisitor() =>
      boxed ? this : new _TypeImplVisitor(compilerState, boxed: true);

  @override
  java.JavaType defaultDartType(dart.DartType node) =>
      throw new CompileErrorException('Cannot represent type $node');

  @override
  java.JavaType visitDynamicType(dart.DynamicType node) => java.JavaType.object;

  @override
  java.JavaType visitVoidType(dart.VoidType node) =>
      boxed ? java.JavaType.javaVoidClass : java.JavaType.void_;

  @override
  java.JavaType visitInterfaceType(dart.InterfaceType node) {
    var impl = compilerState._classImpls[node.classNode];
    var lValueType = boxed ? impl?.javaBoxedLValueType : impl?.javaLValueType;
    if (lValueType != null) {
      return lValueType;
    }
    var rawType = compilerState.getRawInterface(node.classNode) ??
        compilerState.getRawClass(node.classNode);
    if (rawType == null) {
      throw new StateError('Class ${node.classNode} has no implementation.\n'
          '_classImpls[] = ${compilerState._classImpls[node.classNode]}');
    }

    if (node.typeArguments != null && node.typeArguments.isNotEmpty) {
      return rawType.withTypeArguments(node.typeArguments
          .map((arg) => arg.accept(this.boxedVisitor()) as java.JavaType)
          .toList());
    } else {
      return rawType;
    }
  }

  // TODO(andrewkrieger,springerm): Will it always work to translate a Dart type
  // parameter type to a Java type parameter type with the same name?
  @override
  java.JavaType visitTypeParameterType(dart.TypeParameterType node) =>
      new java.TypeVariable(node.parameter.name);
}
