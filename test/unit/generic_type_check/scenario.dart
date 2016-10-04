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

// Nicely done, Andrew! Just had to add the type check in java_builder and
// everything else worked out of the box! :)

void testTypeCheckFailsPrimitive() {
  Map<int, int> mapIntInt = new Map<int, int>();
  Map<Object, Object> mapObjectObject = mapIntInt;

  mapObjectObject[3] = "this should fail";
}

void testTypeCheckFails() {
  Map<String, String> mapStringString = new Map<String, String>();
  Map<Object, Object> mapObjectObject = mapStringString;

  mapObjectObject["this should fail"] = new List<int>();
}

void testTypeCheckSubtypeOK() {
  var mapObjectObject = new Map<Object, Object>();
  mapObjectObject[3] = "This is allowed!";
}

class A {

}

class B implements A {

}

void testTypeCheckInterfaceOK() {
  var mapObjectObject = new Map<Object, A>();
  mapObjectObject[3] = new B();
}

void testTypeCheckInterfaceFails() {
  Map<Object, Object> mapObjectObject = new Map<Object, B>();
  mapObjectObject["this should fail"] = new A();
}

class C<X> {

}

class D<Y> implements C<Y> {

}

void testTypeCheckGenericInterfaceOK() {
  var mapObjectObject = new Map<Object, C<A>>();
  mapObjectObject[3] = new D<B>();
}

void testTypeCheckGenericInterfaceFails() {
  Map<Object, Object> mapObjectObject = new Map<Object, C<B>>();
  mapObjectObject[3] = new D<A>();
}


// Test generic parameter types
class E<X> {
  Object getFirst(List<X> list) {
    return list[0];
  }
}

int testGenericTypeIntOK() {
  var e = new E<int>();
  return e.getFirst(<int>[5, 6 , 7]) as int;
}

String testGenericTypeIntFails() {
  E<Object> e = new E<int>();
  return e.getFirst(<String>["A"]) as String;
}

String testGenericTypeStringOK1() {
  var e = new E<String>();
  return e.getFirst(<String>["B", "C"]) as String;
}

String testGenericTypeObjectOK() {
  E<Object> e = new E<Object>();
  return e.getFirst(<String>["D", "E"]) as String;
}

Object testGenericTypeStringFails() {
  E<Object> e = new E<String>();
  return e.getFirst(<A>[new A()]);
}