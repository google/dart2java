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

// Scenario 1 + 2: Various getter/setter tests
class Foo1<A> {
  A variable1;
}

class Foo2<B> extends Foo1<B> {
  B variable2;
}

String testGetterSetter() {
  var f1o = new Foo1<Object>();
  var f1i = new Foo1<int>();
  var f1s = new Foo1<String>();
  var f2o = new Foo2<Object>();
  var f2i = new Foo2<int>();
  var f2s = new Foo2<String>();

  f2o.variable1 = 1;
  f2o.variable2 = 2;
  f2i.variable1 = 3;
  f2i.variable2 = 4;
  f2s.variable1 = "5";
  f2s.variable2 = "6";

  int tempResult1 = f2o.variable2 as int;
  tempResult1 += f2o.variable1 as int;
  tempResult1 += f2i.variable2;
  f1i = f2i;
  tempResult1 += f1i.variable1;   // 10;

  return f2s.variable1 + f2s.variable2 + tempResult1.toString();  // 5610
}

class Bar<C, D> {
  C var1;
  D var2;
}

class Qux<E> extends Bar<E, int> {
  E var3;
}

int testExtends() {
  Bar<int, Object> bar = new Qux<int>();
  bar.var1 = 10;
  (bar as Qux<int>).var2 = 20;
  (bar as Qux<int>).var3 = 30;

  return bar.var1 + (bar.var2 as int) + (bar as Qux<int>).var3;
}

// Scenario 3: Mixture of generic and non-generic classes
class C1 {
  int foo() {
    return 10;
  }

  int baz() {
    return 40;
  }
}

class C2<A> extends C1 {
  int foo() {
    return super.foo() + 100;
  }
}

class C3<A, B> extends C2<B> {

}

class C4 extends C3<bool, int> {
  int foo() {
    return super.foo() + 10000;
  }
}

int testBaz() {
  C1 c1 = new C1();
  C2<int> c2 = new C2<int>();
  C3<bool, int> c3 = new C3<bool, int>();
  C4 c4 = new C4();

  int result = 0;
  result += c1.baz();
  result += c2.baz();
  result += c3.baz();
  result += c4.baz();

  // Invocation through superclass
  c1 = c2;
  result += c1.baz();
  c1 = c3;
  result += c1.baz();
  c1 = c4;
  result += c1.baz();

  c2 = c3;
  result += c2.baz();
  c2 = c4;
  result += c2.baz();

  return result;      // 360
}

int testFoo() {
  C1 c1 = new C1();
  C2<int> c2 = new C2<int>();
  C3<bool, int> c3 = new C3<bool, int>();
  C4 c4 = new C4();

  int result = 0;                   // 0
  result += c1.foo();               // 10
  result += c2.foo();               // 120
  result += c3.foo();               // 230
  result += c4.foo();               // 10340

  // Invocation through superclass
  c1 = c2;
  result += c1.foo();               // 10450
  c1 = c3;
  result += c1.foo();               // 10560
  c1 = c4;
  result += c1.foo();               // 20670

  c2 = c3;
  result += c2.foo();               // 20780
  c2 = c4;
  result += c2.foo();               // 30890

  return result;
}

int testFooSuperSpec() {
  C1 c1 = new C1();
  C2<Object> c2 = new C2<Object>();
  C3<Object, int> c3 = new C3<bool, int>();
  C4 c4 = new C4();

  int result = 0;                   // 0
  result += c1.foo();               // 10
  result += c2.foo();               // 120
  result += c3.foo();               // 230
  result += c4.foo();               // 10340

  // Invocation through superclass
  c1 = c2;
  result += c1.foo();               // 10450
  c1 = c3;
  result += c1.foo();               // 10560
  c1 = c4;
  result += c1.foo();               // 20670

  c2 = c3;
  result += c2.foo();               // 20780
  c2 = c4;
  result += c2.foo();               // 30890

  return result;
}
