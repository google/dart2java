int var1 = 10;
int var2 = 0;

int method1() {
  return 5;
}

int getVar1() {
  return var1;
}

void setVar2(int value) {
  var2 = value;
}

int getVar2() {
  return var2;
}

int callVar1Getter() {
  return getVar1();
}
