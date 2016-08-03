class Class1 {
  static int x = 12;

  static int callStaticGet() {
    return x;
  }

  static int getGetVarX() {
    return callStaticGet();
  }

  int instGetVarX() {
    return callStaticGet();
  }

  int callStaticGetFromInst() {
    return x;
  }
}

int getVarViaStaticGet() {
  // StaticGet from static method
  return Class1.callStaticGet();
}

int getVarViaStaticGetFromInst() {
  // StaticGet from instance method
  return new Class1().callStaticGetFromInst();
}

int getVarViaStaticGetDirectly() {
  return Class1.x;
}

int getVarViaStaticMethod() {
  // Call static method from static method
  return Class1.getGetVarX();
}

int getVarViaStaticMethodFromInst() {
  // Call static method from instance method
  return new Class1().instGetVarX();
}
