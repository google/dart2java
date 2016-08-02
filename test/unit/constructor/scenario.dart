class Class1 {
  int v;

  Class1() {
    v = 12;
  }
}

int createClass1() {
  var c = new Class1();
  return c.v;
}


class Class2 {
  int v1;
  int v2;

  Class2(int v) {
    v1 = 2;
    v2 = v;
  }
}

int createClass2() {
  var c = new Class2(3);
  return c.v1 + c.v2;
}


class Class3 {
  int v1;
  int v2;

  Class3(this.v1) {
    v2 = 3;
  }
}

int createClass3() {
  var c = new Class3(2);
  return c.v1 + c.v2;
}


class Class4 {
  int v1;
  int v2;

  Class4(this.v1, int v2) {
    this.v2 = v2 + 1;
  }
}

int createClass4() {
  var c = new Class4(1, 2);
  return c.v1 + c.v2;
}
