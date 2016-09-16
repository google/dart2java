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

bool isLessIfThen(int a, int b) {
  if (a < b) {
    return true;
  }

  return false;
}

bool isLessIfThenElse(int a, int b) {
  if (a < b) {
    return true;
  } else {
    return false;
  }
}

bool isLessTernary(int a, int b) {
  return a < b ? true : false;
}

int countWhile() {
  int i = 0;
  while (i < 10) {
    i = i + 1;
  }
  return i;
}

int countDo() {
  int i = 0;
  do {
    i = i + 1;
  } while (i < 10);
  return i;
}


int countFor() {
  int counter = 0;
  for (var i = 0; i < 10; i = i + 1) {
    counter = counter + 1;
  }
  return counter;
}

int countForMultiUpdate() {
  int counter = 0;
  int i = 0;
  int j = 0;
  for (; i < 10; i = i + 1, j = j - 1) {
    counter = counter + i - j;
  }
  return counter;
}
