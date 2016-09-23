int testForInInt() {
  var intList = [1, 3, 5, 1];
  int accumulator = 0;

  for (int i in intList) {
    accumulator += i;
  }

  return accumulator;
}

String testForInStr() {
  var strList = ["1", "3", "5", "1"];
  String accumulator = "";

  for (String s in strList) {
    accumulator += s;
  }

  return accumulator;
}
