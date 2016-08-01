String getString() {
  return "foo";
}

int getInt() {
  return 42;
}

// TODO(springerm): Implement double type
// double getDouble() {
//   return 4.2;
// }

bool getBool() {
  return true;
}

int getIntSign(int number) {
  return number.sign;
}

int getIntAbs(int number) {
  return number.abs();
}

int addInts(int v1, int v2) {
  return v1 + v2;
}

bool lessEqualInts(int v1, int v2) {
  return v1 <= v2;
}

int negateInt(int v) {
  return -v;
}

int unaryTilde(int v) {
  return ~v;
}
