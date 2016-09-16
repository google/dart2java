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

Object getObject(bool returnAString) {
  if (returnAString) {
    return "a string";
  } else {
    return 8.4;
  }
}

List getList(bool returnAListOfString) {
  if (returnAListOfString) {
    return ["foo", "bar", "baz"];
  } else {
    return [1, 2, 3];
  }
}

void takesString(String s) {}
void takesListString(List<String> l) {}

String downcastStringAtReturnNoError() => getObject(true);
String downcastStringAtReturnError() => getObject(false);
List<String> downcastListAtReturnNoError() => getList(true);
List<String> downcastListAtReturnError() => getList(false);

void downcastStringAtCallNoError() => takesString(getObject(true));
void downcastStringAtCallError() => takesString(getObject(false));
void downcastListAtCallNoError() => takesListString(getList(true));
void downcastListAtCallError() => takesListString(getList(false));

void downcastStringAtLocalNoError() {
  String s = getObject(true);
}

void downcastStringAtLocalError() {
  String s = getObject(false);
}

void downcastListAtLocalNoError() {
  List<String> s = getList(true);
}

void downcastListAtLocalError() {
  List<String> s = getList(false);
}
