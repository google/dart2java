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

class Class1 {
  int foo(int a, int b) {
    return a + b;
  }
}

class Class2 {
  int foo(int a, int b) {
    return a + b * 2;
  }
}

int testDynamicDispatch() {
  int result = 0;
  dynamic d = new Class1();
  result = result + (d.foo(10, 20) as int);
  d = new Class2();
  result = result + (d.foo(100, 200) as int);
  return result;
}

int testDynamicDispatchPrimitive() {
  int result = 0;
  dynamic v1 = 50;
  dynamic v2 = 100;
  result = result + (v1 + v2) as int;

  dynamic v3 = 10.5;
  dynamic v4 = 20.5;
  result = (result + (v3 + v4) as double).toInt();

  return result;
}
