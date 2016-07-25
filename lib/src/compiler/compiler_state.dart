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
import 'symbol_table_builder.dart' show SymbolTableBuilder;

/// Used as the name of synthetic Java classes used to wrap Dart top-level
/// functions and fields.
const String _topLevelClassName = '__TopLevel';

/// Describes how a Dart class is implemented (e.g., how to represent it as a
/// Java variable, how to call methods on it, etc.).
class ClassImpl {
  /// If this class is a @JavaClass, this is the class named by the @JavaClass
  /// metadata; otherwise [javaClass] is [:null:].
  java.ClassOrInterfaceType javaClass;

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
  String toString() =>
      '$ClassImpl(javaClass="$javaClass", interceptor="$interceptor", '
      'intercepted="$intercepted")';
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

    repository.libraries.forEach(new SymbolTableBuilder(this).visitLibrary);
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

  /// Tests whether a Dart class is a @JavaClass.
  bool isJavaClass(dart.Class class_) {
    return classImpls[class_]?.javaClass != null;
  }

  /// If [receiverClass] is an intercepted class, return its interceptor; else,
  /// return null.
  java.ClassOrInterfaceType getInterceptorClassFor(dart.Class receiverClass) {
    return classImpls[receiverClass]?.interceptor;
  }

  /// Check if a certain class is an interceptor class.
  bool isInterceptorClass(dart.Class dartClass) {
    return classImpls[dartClass]?.intercepted != null;
  }

  /// If this class is an interceptor class, return the Java type that should
  /// be used for the `self` params in its methods.
  ///
  /// This is the "intercepted" type. For example, in the current SDK,
  /// dart:_internal::JavaInteger is a (Dart) class that is an interceptor for
  /// dart:core::int. Since dart:core::int is declared with
  /// @JavaClass(java.lang.Integer),
  ///     getInterceptedClass(getDartClass("dart:_internal", "JavaInteger"))
  /// will return "java.lang.Integer".
  ///
  /// This method must only be called for interceptor classes!
  java.ClassOrInterfaceType getInterceptedClass(dart.Class dartClass) {
    assert(isInterceptorClass(dartClass));
    var impl = classImpls[dartClass];
    return getClass(impl.intercepted);
  }
}

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
