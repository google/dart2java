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

class Zero {
  int z = 0;
}

class One<A> {
  A a;
}

class Two<A, B> {
  A a;
  B b;
}

// TODO(springerm): Generate test cases
class Three<A, B, C> {
  A a;
  B b;
  C c;
}

class Four<A, B, C, D> {
  A a;
  B b;
  C c;
  D d;
}

int testZero() {
  var i = new Zero();
  i.z = 10;
  return i.z;
}

int testOne() {
  int result = 0;

  One<Object> g;
  var o = new One<Object>();
  var b = new One<bool>();
  var i = new One<int>();
  var d = new One<double>();

  o.a = 3;
  b.a = true;
  i.a = 5;
  d.a = 10.0;

  result += o.a as int;
  result += b.a ? 1 : 0;
  result += i.a;
  result += (d.a as double).toInt();        // 19

  g = o;
  g.a = 10;
  result += g.a as int;                     // 29

  g = b;
  g.a = false;
  result += (g.a as bool) ? 1 : 0;          // 0

  g = i;
  g.a = 6;
  result += g.a as int;                     // 35

  g = d;
  g.a = 2.0;
  result += (g.a as double).toInt();        // 37

  return result;
}