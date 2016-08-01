String getString() {
  return "foo";
}

int getInt() {
  return 42;
}

double getDouble() {
  return 4.2;
}

double getDoubleMin() {
  return double.MIN_POSITIVE;
}

double getDoubleInfinity() {
  return double.INFINITY;
}

bool getBool() {
  return true;
}

int getIntSign(int number) {
  return number.sign;
}

double getDoubleSign(double number) {
  return number.sign;
}

int getIntAbs(int number) {
  return number.abs();
}

double getDoubleAbs(double number) {
  return number.abs();
}

int addInts(int v1, int v2) {
  return v1 + v2;
}

double addDoubles(double v1, double v2) {
  return v1 + v2;
}

double addIntDouble(int v1, double v2) {
  return v1 + v2;
}

double addDoubleInt(double v1, int v2) {
  return v1 + v2;
}

bool lessEqualInts(int v1, int v2) {
  return v1 <= v2;
}

bool lessEqualDoubles(double v1, double v2) {
  return v1 <= v2;
}

bool lessEqualIntDouble(int v1, double v2) {
  return v1 <= v2;
}

bool lessEqualDoubleInt(double v1, int v2) {
  return v1 <= v2;
}

int negateInt(int v) {
  return -v;
}

int unaryTilde(int v) {
  return ~v;
}
