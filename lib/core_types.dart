// Copyright (c) 2016, the Dart project authors.  Please see the AUTHORS file
// for details. All rights reserved. Use of this source code is governed by a
// BSD-style license that can be found in the LICENSE file.
library kernel.class_table;

import 'ast.dart';
import 'repository.dart';

/// Provides access to the classes and libraries in the core libraries.
class CoreTypes {
  final Map<String, _LibraryIndex> _dartLibraries = <String, _LibraryIndex>{};
  Class objectClass;
  Class nullClass;
  Class boolClass;
  Class intClass;
  Class numClass;
  Class doubleClass;
  Class stringClass;
  Class listClass;
  Class mapClass;
  Class iterableClass;
  Class iteratorClass;
  Class futureClass;
  Class streamClass;
  Class symbolClass;
  Class internalSymbolClass;
  Class typeClass;
  Class functionClass;

  /// A global [CoreTypes] instance, for the common case where only a single
  /// program is being processed at once.
  ///
  /// Make sure to set this property before accessing it, and don't use this if
  /// you are processing more than one source tree at once.
  static CoreTypes instance;

  Library getCoreLibrary(String uri) => _dartLibraries[uri].library;

  Class getCoreClass(String libraryUri, String className) {
    return _dartLibraries[libraryUri].require(className);
  }

  Procedure getCoreProcedure(String libraryUri, String topLevelMemberName) {
    Library library = getCoreLibrary(libraryUri);
    for (Procedure procedure in library.procedures) {
      if (procedure.name.name == topLevelMemberName) return procedure;
    }
    throw 'Missing procedure ${topLevelMemberName} from $libraryUri';
  }

  CoreTypes(Program program) : this.fromLibraries(program.libraries);

  CoreTypes.fromRepository(Repository repository) :
    this.fromLibraries(repository.libraries);

  CoreTypes.fromLibraries(Iterable<Library> libraries) {
    for (var library in libraries) {
      if (library.importUri.scheme == 'dart') {
        _dartLibraries['${library.importUri}'] = new _LibraryIndex(library);
      }
    }
    _LibraryIndex dartCore = _dartLibraries['dart:core'];
    _LibraryIndex dartAsync = _dartLibraries['dart:async'];
    _LibraryIndex dartInternal = _dartLibraries['dart:_internal'];
    objectClass = dartCore.require('Object');
    nullClass = dartCore.require('Null');
    boolClass = dartCore.require('bool');
    intClass = dartCore.require('int');
    numClass = dartCore.require('num');
    doubleClass = dartCore.require('double');
    stringClass = dartCore.require('String');
    listClass = dartCore.require('List');
    mapClass = dartCore.require('Map');
    iterableClass = dartCore.require('Iterable');
    iteratorClass = dartCore.require('Iterator');
    symbolClass = dartCore.require('Symbol');
    typeClass = dartCore.require('Type');
    functionClass = dartCore.require('Function');
    futureClass = dartAsync.require('Future');
    streamClass = dartAsync.require('Stream');
    internalSymbolClass = dartInternal.require('Symbol');
  }
}

/// Provides by-name lookup of classes in a library.
class _LibraryIndex {
  final Library library;
  final Map<String, Class> classes = <String, Class>{};

  _LibraryIndex(this.library) {
    for (Class classNode in library.classes) {
      if (classNode.name != null) {
        classes[classNode.name] = classNode;
      }
    }
  }

  Class require(String name) {
    Class result = classes[name];
    if (result == null) throw 'Missing class $name from ${library.name}';
    return result;
  }
}
