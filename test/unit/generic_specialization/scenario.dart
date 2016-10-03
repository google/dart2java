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

// Scenario 1: Method is overridden
class C1<A, B> {
  int method1(A a, B b) {
    return 1;
  }
}

class C2<C> extends C1<int, C> {
  int method1(int a, C b) {
    return 2;
  }
}

int callPattern_ExactClassExactSpec_C2C2() {
  C2<int> c = new C2<int>();
  return c.method1(0, 0);
}

int callPattern_ExactClassExactSpec_C1C1() {
  C1<int, int> c = new C1<int, int>();
  return c.method1(0, 0);
}

int callPattern_SuperClassExactSpec_C1C2() {
  C1<int, int> c = new C2<int>();
  return c.method1(0, 0);
}

int callPattern_ExactClassSuperSpec_C2C2() {
  C2<Object> c = new C2<int>();
  return c.method1(0, 0);
}

int callPattern_ExactClassSuperSpec_C1C1() {
  C1<Object, Object> c = new C1<Object, int>();
  return c.method1(0, 0);
}

int callPattern_SuperClassSuperSpec_C1C2_1() {
  C1<Object, int> c = new C2<int>();
  return c.method1(0, 0);
}

int callPattern_SuperClassSuperSpec_C1C2_2() {
  C1<Object, Object> c = new C2<int>();
  return c.method1(0, 0);
}


// Scenario 2: Method is not present in subclass
class C3<A, B> {
  int method1(A a, B b) {
    return 1;
  }
}

class C4<C> extends C3<int, C> {

}

int callPattern_ExactClassExactSpec_C4C4() {
  C4<int> c = new C4<int>();
  return c.method1(0, 0);
}

int callPattern_ExactClassExactSpec_C3C3() {
  C3<int, int> c = new C3<int, int>();
  return c.method1(0, 0);
}

int callPattern_SuperClassExactSpec_C3C4() {
  C3<int, int> c = new C4<int>();
  return c.method1(0, 0);
}

int callPattern_ExactClassSuperSpec_C4C4() {
  C4<Object> c = new C4<int>();
  return c.method1(0, 0);
}

int callPattern_ExactClassSuperSpec_C3C3() {
  C3<Object, Object> c = new C3<Object, int>();
  return c.method1(0, 0);
}

int callPattern_SuperClassSuperSpec_C3C4_1() {
  C3<Object, int> c = new C4<int>();
  return c.method1(0, 0);
}

int callPattern_SuperClassSuperSpec_C3C4_2() {
  C3<Object, Object> c = new C4<int>();
  return c.method1(0, 0);
}


// Scenario 3: Invocation through interface
class C5<A, B> {
  int method1(A a, B b) {
    return 1;
  }
}

class C6<C> implements C5<int, C> {
  int method1(int a, C b) {
    return 2;
  }
}

int callPattern_ExactClassExactSpec_C6C6() {
  C6<int> c = new C6<int>();
  return c.method1(0, 0);
}

int callPattern_SuperClassExactSpec_C5C6() {
  C5<int, int> c = new C6<int>();
  return c.method1(0, 0);
}

int callPattern_SuperClassSuperSpec_C5C6_1() {
  C5<Object, int> c = new C6<int>();
  return c.method1(0, 0);
}

int callPattern_SuperClassSuperSpec_C5C6_2() {
  C5<Object, Object> c = new C6<int>();
  return c.method1(0, 0);
}


// Scenario 4: Invocation through abstract class
abstract class C7<A, B> {
  int method1(A a, B b);
}

class C8<C> extends C7<int, C> {
  int method1(int a, C b) {
    return 2;
  }
}

int callPattern_ExactClassExactSpec_C8C8() {
  C8<int> c = new C8<int>();
  return c.method1(0, 0);
}

int callPattern_SuperClassExactSpec_C7C8() {
  C7<int, int> c = new C8<int>();
  return c.method1(0, 0);
}

int callPattern_SuperClassSuperSpec_C7C8_1() {
  C7<Object, int> c = new C8<int>();
  return c.method1(0, 0);
}

int callPattern_SuperClassSuperSpec_C7C8_2() {
  C7<Object, Object> c = new C8<int>();
  return c.method1(0, 0);
}

