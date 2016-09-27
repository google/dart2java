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

class Foo1<A> {
  A variable1;
}

class Foo2<B> extends Foo1<B> {
  B variable2;
}

String testGetterSetter() {
  var f1o = new Foo1<Object>();
  var f1i = new Foo1<int>();
  var f1s = new Foo1<String>();
  var f2o = new Foo2<Object>();
  var f2i = new Foo2<int>();
  var f2s = new Foo2<String>();

  f2o.variable1 = 1;
  f2o.variable2 = 2;
  f2i.variable1 = 3;
  f2i.variable2 = 4;
  f2s.variable1 = "5";
  f2s.variable2 = "6";

  int tempResult1 = f2o.variable2 as int;
  tempResult1 += f2o.variable1 as int;
  tempResult1 += f2i.variable2;
  f1i = f2i;
  tempResult1 += f1i.variable1;   // 10;

  return f2s.variable1 + f2s.variable2 + tempResult1.toString();  // 5610
}

class Bar<C, D> {
  C var1;
  D var2;
}

class Qux<E> extends Bar<E, int> {
  E var3;
}

int testExtends() {
  Bar<int, Object> bar = new Qux<int>();
  bar.var1 = 10;
  // TODO(springerm): Fix this
  // (bar as Qux<int>).var2 = 20;
  // (bar as Qux<int>).var3 = 30;

  return bar.var1 + (bar.var2 as int) + (bar as Qux<int>).var3;
}