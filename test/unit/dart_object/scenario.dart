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

bool equalObjectObject() {
  return new Object() == new Object();
}

bool equalObjectInt() {
  return new Object() == 123;
}

bool equalIntObject() {
  return 123 == new Object();
}

bool equalIntInt() {
  return 123 == 123;
}

String toStringObject() {
  return new Object().toString();
}

String toStringInteger() {
  return 12.toString();
}

class Class1 {

}

bool equalObjectClass1() {
  return new Object() == new Class1();
}

bool equalClass1Object() {
  return new Class1() == new Object();
}

bool equalClass1Class1() {
  return new Class1() == new Class1();
}

void hashCodeObject() {
  new Object().hashCode;
}

void hashCodeClass1() {
  new Class1().hashCode;
}

void hashCodeInteger() {
  12.hashCode;
}
