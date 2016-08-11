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


// This pattern appears in DeltaBlue
// Field initializers must be run before super constructor
class Class5 {
  int getFooVar() {
    return -10;
  }

  void setFooVar(int value) {

  }

  Class5() {
    setFooVar(getFooVar() + 1);
  }
}

class Class6 extends Class5 {
  Class6(this.foo) : super();

  int foo = -50;

  int getFooVar() {
    return foo;
  }

  void setFooVar(int value) {
    foo = value;
  }
}

int createClass6() {
  var c = new Class6(100);
  return c.foo;
}


// Order of execution:
// 1. Field initializers (B2)
// 2. Field initializers (B1)
// 3. Field initializers (A3)
// 4. Field initializers (A2)
// 5. Field initializers (A1)
// 6. Body (A)
// 7. Body (B)
var initOrder = <String>[];

int getValueForInit(String field) {
  initOrder.add(field);
  return 10;
}

class ClassA {
  int fieldA1;
  int fieldA2 = getValueForInit("fieldA2");
  int fieldA3;

  ClassA(this.fieldA3) : this.fieldA1 = getValueForInit("fieldA1") {
    getValueForInit("bodyA");
  }
}

class ClassB extends ClassA {
  int fieldB1;
  int fieldB2 = getValueForInit("fieldB2");

  ClassB() : 
    this.fieldB1 = getValueForInit("fieldB1"), 
    super(getValueForInit("fieldA3")) {
    getValueForInit("bodyB");
  }
}

void createB() {
  new ClassB();
}