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
  Foo1<T> anotherFoo1;

  void createInnerFoo() {
    anotherFoo1 = new Foo1<T>();
  }

  T foo(T t) {
    return t;
  }

  Foo1.newMe();

  factory Foo1() {
    return new Foo1.newMe();
  }

  void writeVariable(T value) {
    variable = value;
  }
}

class Bar2<A, B> {
  A varA;
  B varB;

  A bar(A a, B b) {
    return varA;
  }
  
  Bar2();

  factory Bar2.newBar(A a, B b) {
    var result = new Bar2<A, B>();
    result.varA = a;
    result.varB = b;
    return result;
  }
}

class Qux3<C, D, E> {
  C qux(C c, D d, E e) {
    return c;
  }
}

main() {
  print("Expecting 12, 11, 13, 14.01, 2.3, 50, 51, 50, 51");
  
  Foo1<int> fooFromConstructor = new Foo1<int>.newMe();
  Foo1<int> foo = new Foo1<int>();

  Foo1<Object> fooObject = foo;
  fooObject.variable = 12;
  print(foo.variable);
  foo.variable = 11;
  print(foo.variable);

  Bar2<int, double> bar = new Bar2<int, double>();
  Bar2<Object, double> barObjectDouble = bar;
  Bar2<int, Object> barIntObject = bar;
  barObjectDouble.varB = 14.01;
  barIntObject.varA = 13;
  print(barIntObject.bar(1, 0.0));
  print(barObjectDouble.varB);
  
  Bar2<int, double> factoryBar = new Bar2.newBar(1, 2.3);
  print(factoryBar.varB);

  // Test nested generics
  Foo1<Bar2<int, Object>> nestedGeneric12 = 
    new Foo1<Bar2<int, Object>>();
  nestedGeneric12.variable = new Bar2<int, Object>.newBar(0, 0);
  nestedGeneric12.variable = new Bar2<int, int>.newBar(50, 51);
  print(nestedGeneric12.foo(nestedGeneric12.variable).varA);
  print(nestedGeneric12.variable.varB);

  nestedGeneric12.writeVariable(new Bar2<int, Object>.newBar(0, 0));
  nestedGeneric12.writeVariable(new Bar2<int, int>.newBar(50, 51));
  print(nestedGeneric12.foo(nestedGeneric12.variable).varA);
  print(nestedGeneric12.variable.varB);
}