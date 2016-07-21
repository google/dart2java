// Copyright (c) 2016, the Dart project authors.  Please see the AUTHORS file
// for details. All rights reserved. Use of this source code is governed by a
// BSD-style license that can be found in the LICENSE file.

import 'package:kernel/ast.dart' as dart;
import 'package:kernel/repository.dart' as dart;
import 'package:path/path.dart' as path;

import '../java/types.dart' as java;
import 'compiler.dart' show CompilerOptions;
import 'runner.dart' show CompileErrorException;
import 'symbol_table_builder.dart' show SymbolTableBuilder;

/// Describes how a Dart class is implemented (e.g., how to represent it as a
/// Java variable, how to call methods on it, etc.).
class ClassImpl {
  /// The Java class used to implement this Dart class.
  java.ClassOrInterfaceType class_;

  /// Whether this is an @JavaClass (if true) or a regular class that the
  /// compiler should generate (if false).
  bool isJavaClass;

  /// The Java class containing interceptor methods for this class, or [:null:]
  /// if this class is not intercepted.
  ///
  /// If this is a core @JavaClass like int (represented via java.lang.Integer),
  /// the compiler uses an interceptor class to implement the instance methods
  /// of this class (such as int.abs()). The interceptor class is a Java class
  /// containing static methods; when client code attempts to invoke an instance
  /// method on this class, the compiler will generate a call to a static method
  /// in [interceptor] with the receiver (which would have been [:this:] inside
  /// the instance method) as its first parameter.
  java.ClassOrInterfaceType interceptor;

  /// If this class is an interceptor class, then [intercepted] is the Dart
  /// class whose methods this class intercepts; otherwise, [intercepted] is
  /// [:null:].
  ///
  /// This is used to determine the correct type for the first `self` parameter
  /// in the interceptor methods.
  dart.Class intercepted;

  /// Intended for debugging only.
  String toString() => 'ClassImpl{class_="$class_", isJavaClass="$isJavaClass", '
    'interceptor="$interceptor", intercepted="$intercepted"}';
}

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

  final classImpls = <dart.Class, ClassImpl>{};

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

    repository.libraries.forEach(new SymbolTableBuilder(this).visitLibrary);

    // Tempoprary step - generate old data structures from [classImpls].
    // TODO(andrewkrieger): remove references to old maps 
    classImpls.forEach((dart.Class class_, ClassImpl impl) {
      if (impl.isJavaClass) {
        // If this class has an @JavaClass annotation, then it is should go in
        // the javaClasses map.
        javaClasses[class_] = impl.class_;
      }

      if (impl.intercepted != null) {
        // In the old data structures, the interceptor classes automatically had
        // @JavaClass applied to them. Also, each interceptor class should
        // intercept itself.
        javaClasses[class_] = classImpls[impl.intercepted].class_;
        interceptorClasses[impl.intercepted] = class_;
        interceptorClasses[class_] = class_;
      }

      if (impl.isJavaClass && impl.intercepted == null) {
        // This rule captures the classes like `int` and `string` which are
        // @JavaClass classes (so they should not go through ordinary codegen)
        // and also abstract (i.e. not interceptors, so no method definitions).
        interfaceOnlyCoreClasses.add(class_);
      }
    });
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
        // 'dart:core' will become 'dart.core'.
        packageParts.add('dart');
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
    return "__TopLevel";
  }
}
