// Copyright (c) 2016, the Dart project authors.  Please see the AUTHORS file
// for details. All rights reserved. Use of this source code is governed by a
// BSD-style license that can be found in the LICENSE file.

import 'dart:collection' show HashMap;

import 'package:kernel/ast.dart' as dart;
import 'package:kernel/repository.dart' as dart;
import 'package:path/path.dart' as path;

import '../java/types.dart' as java;
import '../java/constants.dart' as java;
import 'compiler.dart' show CompilerOptions;
import 'runner.dart' show CompileErrorException;

/// Used as the name of synthetic Java classes used to wrap Dart top-level
/// functions and fields.
const String _topLevelClassName = '__TopLevel';

/// Describes how a Dart class is implemented (e.g., how to represent it as a
/// Java variable, how to call methods on it, etc.).
///
/// This data structure is only ever created for `@JavaClass` classes, including
/// the core types `Object`, `String`, `bool`, `num`, `int`, and `double`.
/// Ordinary user-defined classes don't need a [ClassImpl] entry.
class ClassImpl {
  /// The java class named by the @JavaClass metadata.
  ///
  /// May not be [:null:].
  final java.ClassOrInterfaceType javaClass;

  /// The Java class containing implementations of the instance methods on this
  /// class.
  ///
  /// May be [:null:]. All classes with a [ClassImpl] must be `@JavaClass` 
  /// classes. These classes can use helper methods or map Dart method names
  /// to Java method names.
  final java.ClassOrInterfaceType helperClass;

  /// Maps Dart method names to Java method names (using an @JavaMethod
  /// annotation). This is sometimes necessary to make methods available in
  /// Dart that are not valid Dart method names.
  final Map<String, String> mappedMethodNames;

  ClassImpl(this.javaClass, this.helperClass) : 
    mappedMethodNames = new Map<String, String>();

  /// Intended for debugging only.
  String toString() =>
      '$ClassImpl(javaClass="$javaClass", helperClass="$helperClass")';
}

class CompilerState {
  final classImpls = new HashMap<dart.Class, ClassImpl>();

  final CompilerOptions options;
  final dart.Repository repository;

  dart.Class objectClass;
  dart.Class boolClass;
  dart.Class intClass;
  dart.Class doubleClass;
  dart.Class stringClass;

  CompilerState(this.options, this.repository) {
    objectClass = getDartClass("dart:core", "Object");
    boolClass = getDartClass("dart:core", "bool");
    intClass = getDartClass("dart:core", "int");
    doubleClass = getDartClass("dart:core", "double");
    stringClass = getDartClass("dart:core", "String");
    var numClass = getDartClass("dart:core", "num");

    // Initialize the data structures.
    classImpls.addAll({
      objectClass: new ClassImpl(
          java.JavaType.object,
          new java.ClassOrInterfaceType(
              "dart._runtime.helpers", "ObjectHelper")),
      boolClass: new ClassImpl(
          new java.ClassOrInterfaceType("java.lang", "Boolean"),
          new java.ClassOrInterfaceType("dart._runtime.helpers", "BoolHelper")),
      intClass: new ClassImpl(
          new java.ClassOrInterfaceType("java.lang", "Integer"),
          new java.ClassOrInterfaceType(
              "dart._runtime.helpers", "IntegerHelper")),
      doubleClass: new ClassImpl(
          new java.ClassOrInterfaceType("java.lang", "Double"),
          new java.ClassOrInterfaceType(
              "dart._runtime.helpers", "DoubleHelper")),
      stringClass: new ClassImpl(
          new java.ClassOrInterfaceType("java.lang", "String"),
          new java.ClassOrInterfaceType(
              "dart._runtime.helpers", "StringHelper")),
      numClass: new ClassImpl(
          new java.ClassOrInterfaceType("java.lang", "Number"),
          new java.ClassOrInterfaceType(
              "dart._runtime.helpers", "NumberHelper")),
    });
  }

  /// Find classes that are annotated with @JavaClass and add them
  /// to classImpls.
  void initializeJavaClasses(Iterable<dart.Class> classes) {
    for (var cls in classes) {
      String fullClassName = getSimpleAnnotation(
        cls, java.Constants.javaClassAnnotation);

      if (fullClassName != null) {
        // This class is an @JavaClass
        List<String> classTokens = fullClassName.split(".");
        String package = classTokens.getRange(
          0, classTokens.length - 1).join(".");
        String className = classTokens.last;

        // helperClass == null means no intercepting
        classImpls[cls] = new ClassImpl(
          new java.ClassOrInterfaceType(package, className), null);

        // Add method mappings
        for (dart.Procedure proc in cls.procedures) {
          String mappedMethodName = getSimpleAnnotation(
            proc, java.Constants.javaMethodAnnotation);

          if (mappedMethodName != null) {
            // This method should have a different name in Dart
            classImpls[cls].mappedMethodNames[proc.name.name] = 
              mappedMethodName;
          }
        }
      }
    }
  }

  /// Assuming that [node] has a single annotation of type [annotation] and
  /// that annotation has a single String parameter, return the parameter,
  /// otherwise return null.
  String getSimpleAnnotation(dart.Class node, String annotation,
      [String fieldName = "name"]) {
    // TODO(springerm): Try to use DartTypes here instead of Strings
    // TODO(springerm): Potential duplication with method in java_builder
    var obj = node.analyzerMetadata
        .firstWhere((i) => i.type.toString() == annotation, 
          orElse: () => null);
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

  /// Get the Java class used to implement a Dart class.
  ///
  /// For most Dart classes, this is a compiler-generated class. For @JavaClass
  /// classes, it is the class named in the @JavaClass metadata.
  java.ClassOrInterfaceType getClass(dart.Class class_) {
    var javaClass = classImpls[class_]?.javaClass;
    if (javaClass != null) {
      return javaClass;
    } else {
      String package = getJavaPackageName(class_.enclosingLibrary);
      return new java.ClassOrInterfaceType(
          package, _sanitizeClassName(class_.name));
    }
  }

  /// Gets the Java class used to contain the top-level methods and fields in a
  /// Dart library.
  java.ClassOrInterfaceType getTopLevelClass(dart.Library library) {
    String package = getJavaPackageName(library);
    return new java.ClassOrInterfaceType(package, _topLevelClassName);
  }

  /// Checks whether a Dart class is a @JavaClass.
  bool isJavaClass(dart.Class class_) {
    return classImpls[class_]?.javaClass != null;
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
    return classImpls.containsKey(receiverClass) 
      && classImpls[receiverClass].helperClass != null;
  }

  bool hasJavaImpl(dart.Class receiverClass) {
    return classImpls.containsKey(receiverClass);
  }

  String getJavaMethodName(dart.Class receiverClass, String dartMethod) {
    if (!classImpls.containsKey(receiverClass)) {
      throw new CompileErrorException(
        "No implementation class defined for $receiverClass");
    }

    return classImpls[receiverClass].
      mappedMethodNames[dartMethod] ?? dartMethod;
  }

  /// If [receiverClass] has any methods that need to be invoked via a helper
  /// function, returns the Java class enclosing the helper function.
  ///
  /// This method should only be called if
  /// `usesHelperFunction(receiverClass, someMethod)` returns true. Always check
  /// [usesHelperFunction] before calling this method!
  java.ClassOrInterfaceType getHelperClass(dart.Class receiverClass) {
    assert(classImpls[receiverClass] != null);
    return classImpls[receiverClass].helperClass;
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
  if (clsName.startsWith(_topLevelClassName) ||
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
