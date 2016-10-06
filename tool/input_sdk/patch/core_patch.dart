// Copyright 2016, the Dart project authors.
// 
// Licensed under the Apache License, Version 2.0 (the "License");
// you may not use this file except in compliance with the License.
// You may obtain a copy of the License at
// 
//     http://www.apache.org/licenses/LICENSE-2.0
// 
// Unless required by applicable law or agreed to in writing, software
// distributed under the License is distributed on an "AS IS" BASIS,
// WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// See the License for the specific language governing permissions and
// limitations under the License.

import 'dart:_internal' show JavaCall, LinkedHashMap;

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
  @JavaCall("dart._runtime.helpers.StringHelper.Static.factory\$fromCharCode")
  external factory String.fromCharCode(int charCode);
}

@patch
class List<E> {
  @patch
  @JavaCall("dart._runtime.base.DartList.<E>factory\$newInstance")
  external factory List([int length = 0]);

  @patch
  @JavaCall("dart._runtime.base.DartList.<E>factory\$filled")
  external factory List.filled(int length, E fill);

  // This patch is for compatibility to java.util.List (add return type bool).
  @patch
  bool add(E value);
}

@patch
class Map<K, V> {
  @patch
  factory Map() {
    return new LinkedHashMap<K, V>();
  }
}

@patch
@JavaCall("java.lang.System.out.println")
external void print(Object o);
