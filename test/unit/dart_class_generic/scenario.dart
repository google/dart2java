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

// --- Same number of generics (simple case) ---
abstract class Class1<A> {
  int foo(A a);
  int bar(A a) {
    return foo(a) + 1;
  }
}

class Class2<A> extends Class1<A> {
  int bar(A a) {
    return super.bar(a) + 3;
  }

  int foo(A a) {
    return 10;
  }
}

class Class3<A> extends Class2<A> {
  int foo(A a) {
    return super.foo(a) + 100;
  }
}

int callClass3Bar() {
  return new Class3<int>().bar(15);
}
// --- END ---


// --- Same number of generics (inversed order) ---
abstract class Class4<A, B> {
  int foo(A a);
  int bar(A a) {
    return foo(a) + 1;
  }
}

class Class5<A, B> extends Class4<B, A> {
  int bar(B a) {
    return super.bar(a) + 3;
  }

  int foo(B a) {
    return 10 + (a as int);
  }
}

class Class6<A, B> extends Class5<B, A> {
  int foo(A a) {
    return super.foo(a) + 100;
  }
}

int callClass6Bar() {
  return new Class6<int, String>().bar(15);
}
// --- END ---


// --- Different number of generics (one is fixed) ---
abstract class Class7<A, B> {
  int foo();
  int bar() {
    return foo() + 1;
  }
}

class Class8<A> extends Class7<int, A> {
  int bar() {
    return super.bar() + 3;
  }

  int foo() {
    return 10;
  }
}

class Class9<A, B> extends Class8<B> {
  int foo() {
    return super.foo() + 100;
  }
}

int callClass9Bar() {
  return new Class9<Object, int>().bar();
}
// --- END ---