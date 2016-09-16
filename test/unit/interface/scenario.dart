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

class A {
  int foo() {
    return 1;
  }

  int bar(int a, int b) {
    return a + b;
  }
}

class B implements A {
  int foo() {
    return 2;
  }

  int bar(int a, int b) {
    return a + b * 10;
  }

  int qux(int c) {
    return c + 1;
  }
}

class C extends A implements B {
  int qux(int c) {
    return c + 100;
  }
}

int testA() {
  var a = new A();
  return a.foo() + a.bar(10, 20);   // expect 31
}

int testB() {
  A b1 = new B();
  B b2 = new B();
  int result = b1.foo() + b1.bar(10, 20) + b2.qux(1000);    // expect 1213
  return result;
}

int testC() {
  A c1 = new C();
  B c2 = new C();
  int result = c1.foo() + c1.bar(10, 20) + c2.qux(1000);    // expect 1131
  return result;
}
