// Copyright (c) 2016, the Dart project authors.  Please see the AUTHORS file
// for details. All rights reserved. Use of this source code is governed by a
// BSD-style license that can be found in the LICENSE file.

import 'package:kernel/ast.dart' as dart;
import 'package:kernel/repository.dart' as dart;

import '../java/ast.dart' as java;
import '../java/constants.dart' as java;
import 'compiler_state.dart' show CompilerState, ClassImpl;
import 'runner.dart' show CompileErrorException;

class SymbolTableBuilder extends dart.RecursiveVisitor {
  /// This builder will populate the symbol tables in [state].
  final CompilerState _state;

  /// Maps a package name to a set of top-level classes in that package.
  final _packageContents = <String, Set<String>>{};

  SymbolTableBuilder(this._state);

  @override
  void visitNormalClass(dart.NormalClass node) {
    ClassImpl impl = _getImpl(node);

    // Process @JavaClass metadata
    List<dart.DartObject> javaClassMetadata = node.analyzerMetadata
        .where(_metadataIs(java.Constants.javaClassAnnotation))
        .toList();
    if (javaClassMetadata.isEmpty) {
      String package = _state.getJavaPackageName(node.enclosingLibrary);
      Set<String> scope = _getPackageContents(package);
      String name = _generateName(scope, node.name);
      scope.add(name);

      impl.isJavaClass = false;
      impl.class_ = new java.ClassOrInterfaceType(package, name);
    } else if (javaClassMetadata.length == 1) {
      String javaClass = javaClassMetadata[0].getField("name").toStringValue();
      impl.isJavaClass = true;
      impl.class_ = new java.ClassOrInterfaceType.parseTopLevel(javaClass);
    } else {
      throw new CompileErrorException(
          "Class ${node.name} in library ${node.enclosingLibrary.importUri} "
          "has more than one insatnce of "
          "@${java.Constants.javaClassAnnotation} metadata.");
    }

    // Process @InterceptorFor metadata
    List<dart.DartObject> interceptorForMetadata = node.analyzerMetadata
        .where(_metadataIs(java.Constants.interceptorForAnnotation))
        .toList();
    if (interceptorForMetadata.length == 1) {
      dart.DartObject obj = interceptorForMetadata[0];
      String library = obj.getField("library").toStringValue();
      String cls = obj.getField("cls").toStringValue();
      dart.Class intercepted = _state.getClass(library, cls);
      assert(intercepted != null);

      // If this is a @JavaClass and @InterceptorFor, then we need a separate
      // class to put interceptor methods into. Otherwise, we can reuse the
      // "regular" class that would be generated for this class.
      java.ClassOrInterfaceType interceptor;
      if (javaClassMetadata.isEmpty) {
        interceptor = _state.classImpls[node].class_;
      } else {
        String interceptorPackage =
            _state.getJavaPackageName(node.enclosingLibrary);
        Set<String> scope = _getPackageContents(interceptorPackage);
        String interceptorName = _generateName(scope, node.name);
        scope.add(interceptorName);
        interceptor =
            new java.ClassOrInterfaceType(interceptorPackage, interceptorName);
      }

      impl.intercepted = intercepted;
      impl.interceptor = interceptor;
      _getImpl(intercepted).interceptor = interceptor;
    } else if (interceptorForMetadata.length > 1) {
      throw new Exception(
          "Class ${node.name} in library ${node.enclosingLibrary.importUri} "
          "has more than one insatnce of "
          "@${java.Constants.interceptorForAnnotation} metadata.");
    }
  }

  /// Generates a new name based on [name] that does not collide with any names
  /// already in [scope] or with any Java reserved words.
  ///
  /// Does not add the resulting name to scope.
  String _generateName(Set<String> scope, String name) {
    if (_javaReservedWords.contains(name) || scope.contains(name)) {
      String newName;
      int i = 0;
      do {
        newName = "$name\$${i++}";
      } while (scope.contains(newName));
      return newName;
    } else {
      return name;
    }
  }

  Set<String> _getPackageContents(String package) {
    return _packageContents.putIfAbsent(package, () => new Set<String>());
  }

  ClassImpl _getImpl(dart.Class cls) {
    return _state.classImpls.putIfAbsent(cls, () => new ClassImpl());
  }
}

typedef bool _MetadataPredicate(dart.DartObject obj);

_MetadataPredicate _metadataIs(String className) {
  return (dart.DartObject obj) => obj.type.toString() == className;
}

// Source: https://docs.oracle.com/javase/specs/jls/se8/html/jls-3.html#jls-3.8
final _javaReservedWords = new Set<String>.from(const [
  // Keywords
  "abstract",
  "continue",
  "for",
  "new",
  "switch",
  "assert",
  "default",
  "if",
  "package",
  "synchronized",
  "boolean",
  "do",
  "goto",
  "private",
  "this",
  "break",
  "double",
  "implements",
  "protected",
  "throw",
  "byte",
  "else",
  "import",
  "public",
  "throws",
  "case",
  "enum",
  "instanceof",
  "return",
  "transient",
  "catch",
  "extends",
  "int",
  "short",
  "try",
  "char",
  "final",
  "interface",
  "static",
  "void",
  "class",
  "finally",
  "long",
  "strictfp",
  "volatile",
  "const",
  "float",
  "native",
  "super",
  "while",

  // Literals
  "true",
  "false",
  "null",
]);
