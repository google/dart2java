int readWriteMap() {
  var map = new Map<String, int>();
  map["a"] = 10;
  map["b"] = 30;
  map["b"] = 35;
  map["c"] = 40;

  int result = 0;
  result = result + map["a"];
  result = result + 2 * map["b"];
  result = result + 3 * map["c"];
  return result;
}