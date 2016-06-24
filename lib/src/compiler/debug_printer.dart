// Copyright (c) 2016, the Dart project authors.  Please see the AUTHORS file
// for details. All rights reserved. Use of this source code is governed by a
// BSD-style license that can be found in the LICENSE file.

import 'package:kernel/ast.dart';
import 'package:kernel/visitor.dart';

/// AST visitor that dumps the kernel AST to stdout.
///
/// Currently printed the node type for most nodes, with extra information added
/// for a few particular nodes. We can expand this as needed during early
/// development.
///
/// This is for debugging purposes only, and i s nowhere near production level.
/// This class should be removed before releasing ddc-java.
class DebugPrinter extends Visitor {
  int _depth = 0;

  @override
  defaultNode(Node n) {
    _print(n.runtimeType);
    _recurse(n);
  }

  @override
  defaultClassReference(Class c) {
    _print('Class reference: "${c.name}" in '
        '${_libraryToString(c.enclosingLibrary)}');
  }

  @override
  defaultMemberReference(Member m) {
    _print('Member reference: "${m.name}" in '
        '${_libraryToString(m.enclosingLibrary)}');
  }

  @override
  visitLibrary(Library l) {
    _print(_libraryToString(l));
    _recurse(l);
  }

  @override
  defaultClass(Class c) {
    var l = c.enclosingLibrary;
    _print('Class[name="${c.name}", in ${_libraryToString(l)}]');
  }

  void _recurse(Node n) {
    _depth++;
    n.visitChildren(this);
    _depth--;
  }

  String _libraryToString(Library l) {
    if (l == null) {
      return 'Library[null]';
    }
    return 'Library[name="${l.name}", importUri="${l.importUri}", '
        'loaded="${l.isLoaded}"]';
  }

  void _print(Object o) {
    print(' ' * _depth + o.toString());
  }
}
