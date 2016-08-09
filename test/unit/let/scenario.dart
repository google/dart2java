int countForMultiUpdate() {
  int counter = 0;
  int i = 0;
  int j = 0;
  for (; i < 10; i++, j--) {
    counter = counter + i - j;
  }
  return counter;
}

int ternarySideEffectTest() {
  int a = 10;
  int b = 30;
  int c = a == 10 ? a++ : b--;
  return a + b + c;
}