String helloWorld() {
  return "Hello World!";
}

class Class1 {
  int x = 0;
  int y = 0;

  int getSetVars(int a) {
    return x = y = a;
  }
}

int setReturnMultipleFields(int a) {
  var x = new Class1();
  return x.getSetVars(12);
}
