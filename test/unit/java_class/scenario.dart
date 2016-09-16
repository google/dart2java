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

import 'dart:_internal';

@JavaClass("java.util.HashMap")
class JavaHashMap {
  external static HashMap();

  @JavaMethod("get")
  external Object at(Object key);

  external Object put(Object key, Object value);
}

class VariableHolder {
  static JavaHashMap javaMap = new JavaHashMap();

  static void setValue(Object key, Object value) {
    javaMap.put(key, value);
  }

  static Object getValue(Object key) {
    return javaMap.at(key);
  }
}

bool testCase() {
  // TODO(springerm): Make this work for types other than Object
  var key = new Object();
  var value = new Object();

  VariableHolder.setValue(key, value);
  return VariableHolder.getValue(key) == value;
}