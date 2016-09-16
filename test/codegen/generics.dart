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