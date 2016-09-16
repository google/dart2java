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

// import "package:expect/expect.dart";
class Expect {
  int x = 0;

  static void isTrue(bool b) {
    if (!b) {
      print('Expected true, got $b');
      print('*'*80);
      print('(Ignore the NPE; it\'s just used to cause test failure)');
      Expect e = null;
      print(e.x);
    }
  }

  static void isFalse(bool b) {
    if (b) {
      print('Expected false, got $b');
      print('*'*80);
      print('(Ignore the NPE; it\'s just used to cause test failure)');
      Expect e = null;
      print(e.x);
    }
  }
}

class A {
}

class B extends A {
}

class C implements B {
}

int inscrutable(int x) => x == 0 ? 0 : x | inscrutable(x & (x - 1));

main() {
  var things = [new A(), new B(), new C()];

  var a = things[inscrutable(0)];
  Expect.isTrue(a is A);
  Expect.isFalse(a is B);
  Expect.isFalse(a is C);

  var b = things[inscrutable(1)];
  Expect.isTrue(b is A);
  Expect.isTrue(b is B);
  Expect.isFalse(b is C);

  var c = things[inscrutable(2)];
  Expect.isTrue(c is A);
  Expect.isTrue(c is B);
  Expect.isTrue(c is C);
}
