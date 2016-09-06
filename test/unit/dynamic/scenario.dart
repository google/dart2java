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
  result = result + (d.foo(10, 20) as int);
  d = new Class2();
  result = result + (d.foo(100, 200) as int);
  return result;
}

int testDynamicDispatchPrimitive() {
  int result = 0;
  dynamic v1 = 50;
  dynamic v2 = 100;
  result = result + (v1 + v2) as int;

  dynamic v3 = 10.5;
  dynamic v4 = 20.5;
  result = (result + (v3 + v4) as double).toInt();

  return result;
}
