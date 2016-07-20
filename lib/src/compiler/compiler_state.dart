// Copyright (c) 2016, the Dart project authors.  Please see the AUTHORS file
// for details. All rights reserved. Use of this source code is governed by a
// BSD-style license that can be found in the LICENSE file.

import 'package:kernel/ast.dart' as dart;
import 'package:kernel/repository.dart' as dart;
import 'package:path/path.dart' as path;

import 'compiler.dart' show CompilerOptions;
import 'runner.dart' show CompileErrorException;

import '../java/ast.dart' as java;

class CompilerState {
  /// Maps Dart classes to Java classes which will be reused in generated code.
  ///
  /// E.g., dart.core.int uses java.lang.Integer. This is required to get
  /// the types right in generated Java code.
  /// TODO(springerm): Try to use mapping from dart.DartType here later.
  final javaClasses = new Map<dart.Class, java.ClassOrInterfaceType>();

  /// Maps Dart SDK classes and interfaces to their runtime implementations,
  /// i.e., "interceptor classes".
  ///
  /// E.g., dart.core.int is implemented by dart class JavaInteger. This
  /// required to find method implementations.
  final interceptorClasses = new Map<dart.Class, dart.Class>();

  /// The set of classes for which no code should be generated.
  ///
  /// This contains only for a few core classes (like int) which are essentially
  /// Dart interfaces. These interfaces are implemented by other (internal)
  /// classes, which are typically @JavaClasses.
  ///
  /// Every class in this set should have corresponding entries in the
  /// [javaClasses] map (so that they compiler knows what Java type to use in
  /// place of this Dart type) and in the [interceptorClasses] map (so that the
  /// compiler knows how to call methods on instances of this interface).
  final interfaceOnlyCoreClasses = new Set<dart.Class>();

  final CompilerOptions options;
  final dart.Repository repository;

  dart.Class objectClass;
  dart.Class boolClass;
  dart.Class intClass;
  dart.Class doubleClass;
  dart.Class stringClass;

  CompilerState(this.options, this.repository) {
    objectClass = getClass("dart:core", "Object");
    boolClass = getClass("dart:core", "bool");
    intClass = getClass("dart:core", "int");
    doubleClass = getClass("dart:core", "double");
    stringClass = getClass("dart:core", "String");

    interfaceOnlyCoreClasses.addAll([intClass, doubleClass, stringClass]);

    // Set up primitive types
    registerPrimitiveCoreClass(objectClass, "java.lang.Object", objectClass);
    registerPrimitiveCoreClass(boolClass, "java.lang.Boolean", boolClass);
    registerPrimitiveCoreClass(intClass, "java.lang.Integer",
        getClass("dart:_internal", "JavaInteger"));
    registerPrimitiveCoreClass(doubleClass, "java.lang.Double",
        getClass("dart:_internal", "JavaDouble"));
    registerPrimitiveCoreClass(stringClass, "java.lang.String",
        getClass("dart:_internal", "JavaString"));
  }

  /// Get the [Library] object for the library named by [libraryUri], loaded to
  /// at least reference level.
  ///
  /// The [libraryUri] may be a relative or absolute file path, or a URI string
  /// with a `dart:`, `package:` or `file:` scheme.
  dart.Library getLibrary(String libraryUri) {
    dart.Library library = repository.getLibrary(libraryUri);
    if (!library.isLoaded) {
      throw new CompileErrorException("Library $libraryUri not loaded");
    }
    return library;
  }

  /// Look up a reference to the class with name [className] in the library
  /// specified by [libraryUri], or [:null:] if the library does not declare a
  /// class by the given name.
  dart.Class getClass(String libraryUri, String className) {
    dart.Library lib = getLibrary(libraryUri);
    return lib.classes
        .firstWhere((dart.Class c) => c.name == className, orElse: () => null);
  }

  void registerPrimitiveCoreClass(
      dart.Class dartName, String javaName, dart.Class interceptorClass) {
    var type = new java.ClassOrInterfaceType(javaName);
    javaClasses[dartName] = type;
    javaClasses[interceptorClass] = type;
    interceptorClasses[dartName] = interceptorClass;
    interceptorClasses[interceptorClass] = interceptorClass;
  }

  /// Check if a certain class is an interceptor class.
  ///
  /// TODO(springerm): Remove once we got annotations working.
  /// TODO(springerm): This should be the fully qualified class name, but
  /// package naming is not fully implemented yet.
  bool isInterceptorClass(dart.Class dartClass) {
    return interceptorClasses.values.contains(dartClass);
  }

  /// Get a Java package name for a Dart Library.
  ///
  /// The package name always has at least one identifier in it (it will never
  /// be the empty string). The package name is essentially just
  /// [options.packagePrefix] plus the relative path from [options.buildRoot]
  /// to the [library] source file (with '/' replaced by '.', of course).
  String getJavaPackageName(dart.Library library) {
    // Omit empty parts, to handle cases like `packagePrefix == "org.example."`
    // or `packagePrefix == ""`.
    List<String> packageParts = options.packagePrefix
        .split('.')
        .where((String part) => part.isNotEmpty)
        .toList();
    Uri uri = library.importUri;

    // Add parts to the package name, depending on the URI scheme.
    switch (library.importUri.scheme) {
      case 'file':
        String libraryPath = uri.toFilePath();

        if (!path.isWithin(options.buildRoot, libraryPath)) {
          throw new CompileErrorException(
              'All sources must be inside build-root.');
        }

        String relativePath =
            path.relative(libraryPath, from: options.buildRoot);
        packageParts.addAll(path.split(path.withoutExtension(relativePath)));
        break;
      case 'dart':
        packageParts.add(uri.path);
        break;
      default:
        throw new CompileErrorException('Unrecognized library URI scheme: '
            '${library.importUri}');
    }

    // TODO(andrewkrieger): Maybe validate the package name?
    return packageParts.join('.');
  }

  static String getClassNameForPackageTopLevel(String package) {
    return package.split('.').last;
  }
}
