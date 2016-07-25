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
    if (javaClassMetadata.length == 1) {
      String javaClass = javaClassMetadata[0].getField("name").toStringValue();
      impl.javaClass = new java.ClassOrInterfaceType.parseTopLevel(javaClass);
    } else if (javaClassMetadata.length > 1) {
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
      dart.Class intercepted = _state.getDartClass(library, cls);
      assert(intercepted != null);

      // If this is a @JavaClass and @InterceptorFor, then we need a separate
      // class to put interceptor methods into. Otherwise, we can reuse the
      // "regular" class that would be generated for this class.
      String interceptorPackage =
          _state.getJavaPackageName(node.enclosingLibrary);
      Set<String> scope = _getPackageContents(interceptorPackage);
      String interceptorName = _generateName(scope, node.name);
      scope.add(interceptorName);
      java.ClassOrInterfaceType interceptor =
            new java.ClassOrInterfaceType(interceptorPackage, interceptorName);

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
    if (java.Constants.reservedWords.contains(name) || scope.contains(name)) {
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
