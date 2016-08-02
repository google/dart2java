// Copyright (c) 2012, the Dart project authors.  Please see the AUTHORS file
// for details. All rights reserved. Use of this source code is governed by a
// BSD-style license that can be found in the LICENSE file.

import 'dart:_internal' show JavaCall, JavaClass, InterceptorFor;

@patch
@JavaClass("java.lang.Object")
@InterceptorFor("dart:core", "Object")
class Object {
  @patch
  @JavaCall("java.lang.System.identityHashCode")
  external int get hashCode;

  @patch
  @JavaCall("java.lang.String.valueOf")
  external String toString();

  @patch
  external bool operator ==(other);
}

@patch
@JavaClass("java.lang.Integer")
abstract class int {
}

@patch
@JavaClass("java.lang.Boolean")
@InterceptorFor("dart:core", "bool")
class bool {}

@patch
@JavaClass("java.lang.String")
abstract class String {}

@patch
@JavaCall("java.lang.System.out.println")
external void print(Object o);
