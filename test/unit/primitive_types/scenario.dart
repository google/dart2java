// Copyright 2016, the Dart project authors.
//
// Licensed under the Apache License, Version 2.0 (the "License");
// you may not use this file except in compliance with the License.
// You may obtain a copy of the License at
//
//     http://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing, software
// distributed under the License is distributed on an "AS IS" BASIS,
// WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// See the License for the specific language governing permissions and
// limitations under the License.

String getString() {
  return "foo";
}

int getInt() {
  return 42;
}

double getDouble() {
  return 4.2;
}

num getNumberInt() {
  return 43;
}

num getNumberDouble() {
  return 4.3;
}

double getDoubleMin() {
  return double.MIN_POSITIVE;
}

double getDoubleInfinity() {
  return double.INFINITY;
}

String getFromCharCode(int charCode) {
  return new String.fromCharCode(charCode);
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

num getNumberIntSign(num number) {
  return number.sign;
}

num getNumberDoubleSign(num number) {
  return number.sign;
}

num getNumberIntAbs(num number) {
  return number.abs();
}

num getNumberDoubleAbs(num number) {
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

num addNumbers(num v1, num v2) {
  return v1 + v2;
}

num addIntNumber(int v1, num v2) {
  return v1 + v2;
}

num addDoubleNumber(double v1, num v2) {
  return v1 + v2;
}

num addNumberInt(num v1, int v2) {
  return v1 + v2;
}

num addNumberDouble(num v1, double v2) {
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

bool lessEqualNumbers(num v1, num v2) {
  return v1 <= v2;
}

bool lessEqualNumberInt(num v1, int v2) {
  return v1 <= v2;
}

bool lessEqualNumberDouble(num v1, double v2) {
  return v1 <= v2;
}

bool lessEqualIntNumber(int v1, num v2) {
  return v1 <= v2;
}

bool lessEqualDoubleNumber(double v1, num v2) {
  return v1 <= v2;
}

int negateInt(int v) {
  return -v;
}

int unaryTilde(int v) {
  return ~v;
}

String getIntToString() {
  return 42.toString();
}

String getDoubleToString() {
  return (42.12).toString();
}

String getFalseToString() {
  return false.toString();
}

String getTrueToString() {
  return true.toString();
}

String getNumToString(num number) {
  return number.toString();
}

String getStringToString() {
  return "HelloWorld".toString();
}

String getIntStringInterpolation1() {
  return "${42}";
}

String getIntStringInterpolation2() {
  int i = 42;
  return "$i";
}

String getIntStringInterpolation3() {
  int i = 21;
  return "${i + 21}";
}

String getIntStringInterpolation4() {
  int i = 21;
  return "${i + i}";
}

String getIntStringInterpolation5() {
  return "${21 + 21}";
}

String getIntStringInterpolation6() {
  int i = 4;
  int j = 2;
  return "$i$j";
}

String getIntStringInterpolation7() {
  return "${4}${2}";
}

String getDoubleStringInterpolation() {
  return "${(42.12)}";
}

String getFalseStringInterpolation() {
  return "${false}";
}

String getTrueStringInterpolation() {
  return "${true}";
}

String getNumStringInterpolation(num number) {
  return "${number}";
}

String getStringStringInterpolation1() {
  return "${"HelloWorld"}";
}

String getStringStringInterpolation2() {
  String string = "HelloWorld";
  return "$string";
}

bool getEqualsBools(bool v1, bool v2) {
  return v1 == v2;
}

bool getEqualsInts(int v1, int v2) {
  return v1 == v2;
}

bool getEqualsDoubles(double v1, double v2) {
  return v1 == v2;
}

bool getEqualsIntDouble(int v1, double v2) {
  return v1 == v2;
}

bool getEqualsIntNum(int v1, num v2) {
  return v1 == v2;
}

bool getEqualsNumInt(int v1, num v2) {
  return v1 == v2;
}

bool getEqualsNums(num v1, num v2) {
  return v1 == v2;
}

bool getLogicalAnd(bool v1, bool v2) {
  return v1 && v2;
}

bool getLogicalOr(bool v1, bool v2) {
  return v1 || v2;
}

bool getLogicalNot(bool v1) {
  return !v1;
}

int testUnaryArithmetics() {
  int i = 5;
  int a = i++;
  int b = i--;
  int c = ++i;
  int d = --i;
  return a + b + c + d;
}

int testNumberInteraction() {
  int a = 3;
  num b = 5;
  int c = (a + b) as int;
  return c;
}