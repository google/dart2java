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

class Class1 {
  static int x = 12;

  static int callStaticGet() {
    return x;
  }

  static int getGetVarX() {
    return callStaticGet();
  }

  int instGetVarX() {
    return callStaticGet();
  }

  int callStaticGetFromInst() {
    return x;
  }
}

int getVarViaStaticGet() {
  // StaticGet from static method
  return Class1.callStaticGet();
}

int getVarViaStaticGetFromInst() {
  // StaticGet from instance method
  return new Class1().callStaticGetFromInst();
}

int getVarViaStaticGetDirectly() {
  return Class1.x;
}

int getVarViaStaticMethod() {
  // Call static method from static method
  return Class1.getGetVarX();
}

int getVarViaStaticMethodFromInst() {
  // Call static method from instance method
  return new Class1().instGetVarX();
}
