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

int countForMultiUpdate() {
  int counter = 0;
  int i = 0;
  int j = 0;
  for (; i < 10; i++, j--) {
    counter = counter + i - j;
  }
  return counter;
}

int ternarySideEffectTest() {
  int a = 10;
  int b = 30;
  int c = a == 10 ? a++ : b--;
  return a + b + c;
}