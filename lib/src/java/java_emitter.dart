// Copyright (c) 2016, the Dart project authors.  Please see the AUTHORS file
// for details. All rights reserved. Use of this source code is governed by a
// BSD-style license that can be found in the LICENSE file.

import 'ast.dart';
import 'visitor.dart';

/// Emits Java code for a Java AST.
///
/// Clients should only call static methods on this class. The fact that
/// this class is a [Visitor] is an implementation detail that callers must
/// not rely on.
///
/// As a rule, the various 'visit*' methods should not add terminators like
/// semicolons or newlines at the end of their outputs. For example, when
/// visiting a return statement, the result should be "return" and not either
/// "return;" or "return;\n". This is because a statement might be encountered
/// in a context where it doesn't need a trailing semicolon (such as an
/// assignment statement in the initializer of a for loop). Or, a class
/// declaration might appear inline (such as an anonymous callback class). By
/// adding the terminators in the call to `visit*`, we have a bit more
/// flexibility.
class JavaAstEmitter extends Visitor<String> {
  /// Emits the full file contents for a Java source file defining a top-level
  /// Java [Class].
  ///
  /// This inludes a package delcaration, imports, and a full Java class
  /// definition.
  static String emitClass(Class cls) {
    var sb = new StringBuffer();
    sb.write('package ');
    sb.write(cls.package);
    sb.writeln(';');
    sb.writeln();
    sb.writeln(cls.accept(new JavaAstEmitter()));
    return sb.toString();
  }

  @override
  String visitClass(Class cls) {
    var sb = new StringBuffer();
    sb.write(accessToString(cls.access));
    sb.write('class ');
    sb.write(cls.name);
    sb.writeln(' {');
    sb.write('}');
    return sb.toString();
  }
}
