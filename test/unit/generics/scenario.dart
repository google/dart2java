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

class IntWrapper {
  int value = 0;

  IntWrapper(this.value);
}

class IntWrapperSubclass extends IntWrapper {
  IntWrapperSubclass(int value) : super(value);
}

int testSubclassSubtype() {
  List<IntWrapperSubclass> listSub = <IntWrapperSubclass>[
    new IntWrapperSubclass(24), new IntWrapperSubclass(12)];
  List<IntWrapper> listSuper = listSub;

  int result = 0;
  for (int i = 0; i < listSuper.length; i++) {
    result = result + listSub[i].value;
    result = result + listSuper[i].value;
  }

  return result;
}

int testTypeSubtypeInList() {
  List<IntWrapper> list = <IntWrapper>[
    new IntWrapper(24), new IntWrapperSubclass(12)];

  int result = 0;
  for (int i = 0; i < list.length; i++) {
    result = result + list[i].value;
  }

  return result;
}

int testSingleTypeArgumentSpecialization() {
  List<int> list = <int>[10, 20, 30];
  int result = 0;

  for (int i = 0; i < list.length; i++) {
    result = result + list[i];
  }

  return result;
}

String testAssignmentOfSpecialization() {
  List<int> list = <int>[10, 20, 30];
  List<Object> objectList = list;
  String result = "";

  for (int i = 0; i < objectList.length; i++) {
    result = result + objectList[i].toString();
  }

  return result;
}