// Copyright (c) 2012, the Dart project authors.  Please see the AUTHORS file
// for details. All rights reserved. Use of this source code is governed by a
// BSD-style license that can be found in the LICENSE file.

import 'dart:_internal' show JavaCall;

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
abstract class String {
  @patch
  external factory String.fromCharCode(int charCode);
}

@patch
@JavaCall("java.lang.System.out.println")
external void print(Object o);
