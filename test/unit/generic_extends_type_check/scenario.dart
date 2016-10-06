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

class Foo<T> {
  T method(T obj) {
    return obj;
  }
}

class Bar extends Foo<String> {
  String method(String obj) {
    return obj + " World";
  }
}

class Bar_Gen<U> extends Foo<String> {
  String method(String obj) {
    return obj + " World";
  }
}

String testInvocationOK1() {
  Foo<String> foo = new Bar();
  return foo.method("Hello") as String;
}

String testInvocationOK2() {
  Foo<Object> foo = new Bar();
  return foo.method("Hello") as String;
}

void testInvocationFail() {
  Foo<Object> foo = new Bar();
  foo.method(123);
}

String testInvocationOK1_Generic() {
  Foo<String> foo = new Bar_Gen<Bar>();
  return foo.method("Hello") as String;
}

String testInvocationOK2_Generic() {
  Foo<Object> foo = new Bar_Gen<Bar>();
  return foo.method("Hello") as String;
}

void testInvocationFail_Generic() {
  Foo<Object> foo = new Bar_Gen<Bar>();
  foo.method(123);
}
