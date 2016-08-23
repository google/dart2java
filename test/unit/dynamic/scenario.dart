class Class1 {
  int foo(int a, int b) {
    return a + b;
  }
}

class Class2 {
  int foo(int a, int b) {
    return a + b * 2;
  }
}

int testDynamicDispatch() {
  int result = 0;
  dynamic d = new Class1();
  result = result + d.foo(10, 20);
  d = new Class2();
  result = result + d.foo(100, 200);
  return result;
}