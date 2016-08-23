// Copyright (c) 2012, the Dart project authors.  Please see the AUTHORS file
// for details. All rights reserved. Use of this source code is governed by a
// BSD-style license that can be found in the LICENSE file.

import 'dart:_internal' show JavaCall, JavaMethod;

@patch
class Object {
  // These methods now dispatch to ObjectHelper, but without these patches the
  // compiler throws a warning that there was no patch found for these 
  // external methods.
  @patch
  external bool operator ==(other);

  @patch
  external int get hashCode;

  @patch
  external String toString();
}

@patch
class Stopwatch {
  @patch
  @JavaCall("dart._runtime.helpers.StopwatchHelper.initTicker")
  external static void _initTicker();

  @patch
  @JavaCall("dart._runtime.helpers.StopwatchHelper.now")
  external static int _now();
}

@patch
abstract class String {
  @patch
  external factory String.fromCharCode(int charCode);
}

@patch
class List<E> {
  @patch
  @JavaMethod("newInstance")
  external factory List([int length = 0]);
}

@patch
class Map<K, V> {
  @patch
  @JavaMethod("newInstance")
  external factory Map();

  @patch
  external factory Map.unmodifiable(Map other);
}

@patch
@JavaCall("java.lang.System.out.println")
external void print(Object o);
