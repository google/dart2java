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

String helloWorld() {
  return "Hello World!";
}

class Class1 {
  int x = 0;
  int y = 0;

  int getSetVars(int a) {
    return x = y = a;
  }
}

int setReturnMultipleFields(int a) {
  var x = new Class1();
  return x.getSetVars(12);
}
