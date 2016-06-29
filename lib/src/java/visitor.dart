// Copyright (c) 2016, the Dart project authors.  Please see the AUTHORS file
// for details. All rights reserved. Use of this source code is governed by a
// BSD-style license that can be found in the LICENSE file.

import 'ast.dart';

/// A Java AST visitor. Subclasses need only override the methods that they are
/// interested in.
abstract class Visitor<R> {
  R visitClass(Class node) => null;
}
