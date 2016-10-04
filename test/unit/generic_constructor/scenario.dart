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

class Foo<A, B> {
  A a;
  B b;
  
  Foo(this.a, this.b);
  Foo.firstParam(this.a);
  Foo.secondParam(this.b);

  factory Foo.callSecond(B b) {
    Foo<A, B> myFoo = new Foo<A, B>.secondParam(b);
    return myFoo;
  }
}

int callConstructor() {
  var result = new Foo<int, bool>(12, true);
  return result.a;
}

int callFirst() {
  var result = new Foo<int, bool>.firstParam(13);
  return result.a;
}

int callSecond() {
  var result = new Foo<int, int>.secondParam(14);
  return result.b;
}

int callCallSecond() {
  var result = new Foo<int, int>.callSecond(15);
  return result.b;
}