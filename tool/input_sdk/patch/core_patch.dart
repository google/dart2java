// Copyright (c) 2012, the Dart project authors.  Please see the AUTHORS file
// for details. All rights reserved. Use of this source code is governed by a
// BSD-style license that can be found in the LICENSE file.

import 'dart:_internal' show JavaCall, JavaClass;

@patch
class Object {
  @patch
  @JavaCall("hashCode")
  external int get hashCode;

  @patch
  @JavaCall("toString")
  external String toString();

  @patch
  dynamic noSuchMethod(Invocation invocation) {
    throw new NoSuchMethodError(
        this,
        invocation.memberName,
        invocation.positionalArguments,
        invocation.namedArguments);
  }

  @patch
  @JavaCall("dart._runtime.helpers.ObjectHelper.runtimeType")
  external Type get runtimeType;
}

@patch
abstract class int {
  @patch
  @JavaStatic("dart._runtime.helpers.IntHelper.fromEnvironment")
  external factory int.fromEnvironment();
}

@patch
@JavaClass("java.lang.Boolean")
class bool {}

@patch
@JavaCall("java.lang.System.out.println")
external void print(Object o);
