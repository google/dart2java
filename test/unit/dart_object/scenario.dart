bool equalObjectObject() {
  return new Object() == new Object();
}

bool equalObjectInt() {
  return new Object() == 123;
}

bool equalIntObject() {
  return 123 == new Object();
}

bool equalIntInt() {
  return 123 == 123;
}

String toStringObject() {
  return new Object().toString();
}

String toStringInteger() {
  return 12.toString();
}

class Class1 {

}

bool equalObjectClass1() {
  return new Object() == new Class1();
}

bool equalClass1Object() {
  return new Class1() == new Object();
}

bool equalClass1Class1() {
  return new Class1() == new Class1();
}

void hashCodeObject() {
  new Object().hashCode;
}

void hashCodeClass1() {
  new Class1().hashCode;
}

void hashCodeInteger() {
  12.hashCode;
}
