int funcWithOptionalArgs(int a, int b, [int c = 3]) {
  return a + b * 10 + c * 100;
}

int callFuncWithoutOptionalArg() {
  return funcWithOptionalArgs(1, 2);
}

int callFuncWithOptionalArg() {
  return funcWithOptionalArgs(1, 2, 4);
}
