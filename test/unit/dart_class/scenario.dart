abstract class Class1 {
  int foo();
  int bar() {
    return foo() + 1;
  }
}

class Class2 extends Class1 {
  int bar() {
    return super.bar() + 3;
  }

  int foo() {
    return 10;
  }
}

class Class3 extends Class2 {
  int foo() {
    return super.foo() + 100;
  }
}

int callClass3Bar() {
  return new Class3().bar();
}