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


class A { }
class B extends A { }

class Bar<S, T> { }

bool testAssignment() {
  Bar<Object, Object> oo = new Bar<Object, Object>();
  Bar<Object, int> oi = new Bar<Object, int>();
  Bar<Object, A> oa = new Bar<Object, A>();
  Bar<Object, B> ob = new Bar<Object, B>();

  oo = oo;
  oo = oi;
  oo = oa;
  oo = ob;

  Bar<A, int> ai = new Bar<A, int>();
  Bar<B, int> bi = new Bar<B, int>();
  Bar<int, int> ii = new Bar<int, int>();
  
  oo = ai;
  oo = bi;
  oo = ii;
  oi = ai;
  oi = bi;
  oi = ii;
  ai = bi;

  oo = ai;
  ai = oo;

  oo = ii;
  // TODO(springerm): This should fail at runtime
  ai = oo;

  return true;
}
