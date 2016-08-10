bool nullEqualsNull1() {
  return null == null;
}

bool nullEqualsNull2() {
  int val = null;
  return val == null;
}

bool nullEqualsNull3() {
  int val = null;
  return null == val;
}

class Class1 { }

bool nullEqualsNull4() {
  Class1 val;
  return val == null;
}

bool nullEqualsNull5() {
  Class1 val;
  return null == val;
}

String nullToString1() {
  return null.toString();
}

String nullToString2() {
  int val = null;
  return val.toString();
}

String nullToString3() {
  Class1 val;
  return val.toString();
}