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

// Dart test program for the "is" type test operator.

// import "package:expect/expect.dart";
class Expect {
  int x = 0;

  static void equals(Object a, Object b) {
    if (a != b) {
      print('Expected $a, got $b');
      print('*'*80);
      print('(Ignore the NPE; it\'s just used to cause test failure)');
      Expect e = null;
      print(e.x);
    }
  }
}

abstract class I { }

abstract class AI implements I { }

class A implements AI {
  const A();
}

class B implements I {
  const B();
}

class C extends A {
  const C() : super();
}

class IsOperatorTest {
  static void testMain() {
    var a = new A();
    var b = new B();
    var c = new C();
    var n = null;
    Expect.equals(true,  a is A);
    Expect.equals(false, a is !A);
    Expect.equals(true,  b is B);
    Expect.equals(false, b is !B);
    Expect.equals(true,  c is C);
    Expect.equals(false, c is !C);
    Expect.equals(true,  c is A);
    Expect.equals(false, c is !A);

    Expect.equals(true,  a is AI);
    Expect.equals(false, a is !AI);
    Expect.equals(true,  a is I);
    Expect.equals(false, a is !I);
    Expect.equals(false, b is AI);
    Expect.equals(true,  b is !AI);
    Expect.equals(true,  b is I);
    Expect.equals(false, b is !I);
    Expect.equals(true,  c is AI);
    Expect.equals(false, c is !AI);
    Expect.equals(true,  c is I);
    Expect.equals(false, c is !I);
    Expect.equals(false, n is AI);
    Expect.equals(true,  n is !AI);
    Expect.equals(false, n is I);
    Expect.equals(true,  n is !I);

    Expect.equals(false, a is B);
    Expect.equals(true,  a is !B);
    Expect.equals(false, a is C);
    Expect.equals(true,  a is !C);
    Expect.equals(false, b is A);
    Expect.equals(true,  b is !A);
    Expect.equals(false, b is C);
    Expect.equals(true,  b is !C);
    Expect.equals(false, c is B);
    Expect.equals(true,  c is !B);
    Expect.equals(false, n is A);
    Expect.equals(true,  n is !A);

    Expect.equals(false, null is A);
    Expect.equals(false, null is B);
    Expect.equals(false, null is C);
    Expect.equals(false, null is AI);
    Expect.equals(false, null is I);

    Expect.equals(true, null is !A);
    Expect.equals(true, null is !B);
    Expect.equals(true, null is !C);
    Expect.equals(true, null is !AI);
    Expect.equals(true, null is !I);
  }
}

main() {
  IsOperatorTest.testMain();
}
