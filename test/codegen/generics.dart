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

class Foo1<T> {
  T variable;

  T foo(T t) {
    return t;
  }
}

class Bar2<A, B> {
  A varA;
  B varB;

  A bar(A a, B b) {
    return varA;
  }

  Bar2();

  /* factory Bar2.newBar(A a, B b) {
    var result = new Bar2<A, B>();
    result.varA = a;
    result.varB = b;
    return result;
  } */
}

class Qux3<C, D, E> {
  C qux(C c, D d, E e) {
    return c;
  }
}

main() {
  /*
  print("Expecting 12, 13, 14.01");
  
  Foo1<int> foo = new Foo1<int>();
  Foo1<Object> fooObject = foo;
  fooObject.variable = 12;
  print(foo.variable);

  Bar2<int, double> bar = new Bar2<int, double>();
  Bar2<Object, double> barObjectDouble = bar;
  Bar2<int, Object> barIntObject = bar;
  barObjectDouble.varB = 14.01;
  barIntObject.varA = 13;
  print(barIntObject.bar(1, 0.0));
  print(barObjectDouble.varB);
  */
  
  // Bar2<int, double> factoryBar = new Bar2.newBar(1, 2.3);
}