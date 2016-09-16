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

import 'dart:math' show Random;

int ITER_PARAM=1;

// Level zero
class A0 {}
class A1 {}
class A2 {}
class A3 {}

// Level one
class B0 {}
class B1 implements A0 {}
class B2 implements A1 {}
class B3 implements A1, A0 {}
class B4 implements A2 {}
class B5 implements A2, A0 {}
class B6 implements A2, A1 {}
class B7 implements A2, A1, A0 {}
class B8 implements A3 {}
class B9 implements A3, A0 {}
class B10 implements A3, A1 {}
class B11 implements A3, A1, A0 {}
class B12 implements A3, A2 {}
class B13 implements A3, A2, A0 {}
class B14 implements A3, A2, A1 {}
class B15 implements A3, A2, A1, A0 {}

// Level two
class C0 implements B0, B1/*, A0*/ {}
class C1 implements B1, B2/*, A0, A1*/ {}
class C2 implements B2, B3/*, A0, A1*/ {}
class C3 implements B3, B4/*, A0, A1, A2*/ {}
class C4 implements B4, B5/*, A0, A2*/ {}
class C5 implements B5, B6/*, A0, A1, A2*/ {}
class C6 implements B6, B7/*, A0, A1, A2*/ {}
class C7 implements B7, B8/*, A0, A1, A2, A3*/ {}
class C8 implements B8, B9/*, A0, A3*/ {}
class C9 implements B9, B10/*, A0, A1, A3*/ {}
class C10 implements B10, B11/*, A0, A1, A3*/ {}
class C11 implements B11, B12/*, A0, A1, A2, A3*/ {}
class C12 implements B12, B13/*, A0, A2, A3*/ {}
class C13 implements B13, B14/*, A0, A1, A2, A3*/ {}
class C14 implements B14, B15/*, A0, A1, A2, A3*/ {}
class C15 implements B15, B0/*, A0, A1, A2, A3*/ {}

Random _rnd = new Random(1337);

/// Returns a random item from the given list.
Object randomFrom(List<Object> l) {
  return l[_rnd.nextInt(l.length)];
}

List<Object> level0 = [new A0(), new A1(), new A2(), new A3()];
List<Object> level1 = [
    new B0(), new B1(), new B2(), new B3(), new B4(), new B5(), new B6(),
    new B7(), new B8(), new B9(), new B10(), new B11(), new B12(), new B13(),
    new B14(), new B15()
];
List<Object> level2 = [
    new C0(), new C1(), new C2(), new C3(), new C4(), new C5(), new C6(),
    new C7(), new C8(), new C9(), new C10(), new C11(), new C12(), new C13(),
    new C14(), new C15()
];

List<Object> others = [
  null,
  3,
  4.5,
  true,
  "string",
  new Object(),
  level0,
];

Object randomObject() {
  // Equal odds of a type from level 0, level 1, level 2, or unrelated.
  int r = _rnd.nextInt(4);
  if (r == 0) {
    return randomFrom(level0);
  }
  if (r == 1) {
    return randomFrom(level1);
  }
  if (r == 2) {
    return randomFrom(level2);
  }
  return randomFrom(others);
}

void main() {
  int a0s = 0;
  int a1s = 0;
  int a2s = 0;
  int a3s = 0;
  int iters = 1000 * 1000 * ITER_PARAM;
  for (int i = 0; i < iters; i++) {
    Object o = randomObject();
    if (o is A0) a0s++;
    if (o is A1) a1s++;
    if (o is A2) a2s++;
    if (o is A3) a3s++;
  }
  print("Percent of objects that are an A0: ${a0s / iters * 100} "
      "(expected "
      "${0.25 * 0.25 + 0.25 * 0.5 + 0.25 * (16.0 / 16.0) + 0.25 * 0 })");
  print("Percent of objects that are an A1: ${a1s / iters * 100} "
      "(expected "
      "${0.25 * 0.25 + 0.25 * 0.5 + 0.25 * (12.0 / 16.0) + 0.25 * 0 })");
  print("Percent of objects that are an A2: ${a2s / iters * 100} "
      "(expected "
      "${0.25 * 0.25 + 0.25 * 0.5 + 0.25 * (10.0 / 16.0) + 0.25 * 0 })");
  print("Percent of objects that are an A3: ${a3s / iters * 100} "
      "(expected "
      "${0.25 * 0.25 + 0.25 * 0.5 + 0.25 * (9.0 / 16.0) + 0.25 * 0 })");
}
