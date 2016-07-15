// Copyright (c) 2016, the Dart project authors.  Please see the AUTHORS file
// for details. All rights reserved. Use of this source code is governed by a
// BSD-style license that can be found in the LICENSE file.

import 'package:kernel/ast.dart' as dart;
import 'package:kernel/repository.dart' as dart;
import 'package:path/path.dart' as path;

import 'compiler.dart' show CompilerOptions;
import 'runner.dart' show CompileErrorException;

class CompilerState {
  /// Maps Dart classes to Java classes which will be reused in generated code.
  ///
  /// E.g., dart.core.int uses java.lang.Integer. This is required to get
  /// the types right in generated Java code.
  final javaClasses = new Map<dart.DartType, String>();

  /// Maps Dart SDK classes and interfaces to their runtime implementations,
  /// i.e., "interceptor classes".
  ///
  /// E.g., dart.core.int is implemented by dart class JavaInteger. This
  /// required to find method implementations.
  final interceptorClasses = new Map<dart.DartType, String>();

  final CompilerOptions options;
  final dart.Repository repository;

  // TODO(springerm): Initialize fields with SDK types
  dart.DartType typeInt;
  dart.DartType typeDouble;
  dart.DartType typeBool;
  dart.DartType typeString;

  CompilerState(this.options, this.repository) {
    // Set up primitive types
    registerPrimitiveCoreClass(
        typeBool, "java.lang.Boolean", "dart.core.bool");
    registerPrimitiveCoreClass(
        typeInt, "java.lang.Integer", "dart._runtime.JavaInteger");
    registerPrimitiveCoreClass(
        typeDouble, "java.lang.Double", "dart._runtime.JavaDouble");
    registerPrimitiveCoreClass(
        typeString, "java.lang.String", "dart._runtime.JavaString");
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
    return lib.classes.firstWhere((dart.Class c) => c.name == className,
        orElse: () => null);
  }

  // TODO(springerm): Call this method once we see a method with an @JavaClass
  // annotation to compile SDK implementations correctly
  void registerPrimitiveCoreClass(
      dart.DartType dartType, String javaName, String interceptorClass) {
    javaClasses[dartType] = javaName;
    interceptorClasses[dartType] = interceptorClass;
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
