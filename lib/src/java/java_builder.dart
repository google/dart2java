// Copyright (c) 2016, the Dart project authors.  Please see the AUTHORS file
// for details. All rights reserved. Use of this source code is governed by a
// BSD-style license that can be found in the LICENSE file.

import 'package:kernel/ast.dart' as dart;

import 'ast.dart' as java;
import 'visitor.dart' as java;

/// Builds a Java class from Dart IR.
///
/// Clients should only call static methods on this class. The fact that
/// this class is a [dart.Visitor] is an implementation detail that callers must
/// not rely on.
class JavaAstBuilder extends java.Visitor<java.Node> {
  /// Builds a Java class that contains the top-level procedures and fields in
  /// a Dart [Library].
  static java.ClassDecl buildWrapperClass(
      String package, String className, dart.Library library) {
    java.ClassDecl result = new java.ClassDecl(package, className, java.Access.Public);
    // TODO(andrewkrieger): visit the Dart library's procedures and fields;
    // add corresponding methods and fields to the Java class.
    return result;
  }
}
