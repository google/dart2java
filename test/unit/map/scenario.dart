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

int readWriteMap() {
  var map = new Map<String, int>();
  map["a"] = 10;
  map["b"] = 30;
  map["b"] = 35;
  map["c"] = 40;

  int result = 0;
  result = result + map["a"];
  result = result + 2 * map["b"];
  result = result + 3 * map["c"];
  return result;
}