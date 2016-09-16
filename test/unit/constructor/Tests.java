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

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import scenario.__TopLevel;

public class Tests {
  @Test
  public void testBasicConstructors() {
    assertEquals(12, (int) __TopLevel.createClass1());
    assertEquals(5, (int) __TopLevel.createClass2());
  }

  @Test
  public void testInitializers() {
    assertEquals(5, (int) __TopLevel.createClass3());
    assertEquals(4, (int) __TopLevel.createClass4());
  }

  @Test
  public void testIntializerOrder() {
    assertEquals(101, (int) __TopLevel.createClass6());

    __TopLevel.createB();
    String[] initOrderExpected = new String[] {"fieldB2", "fieldB1", 
      "fieldA3", "fieldA2", "fieldA1", "bodyA", "bodyB"};
    String[] initOrder = ((java.util.List<String>)__TopLevel.initOrder).toArray(new String[] {});
    assertEquals(initOrderExpected, initOrder);
  }
}
