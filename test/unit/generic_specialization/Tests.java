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

import org.junit.Test;
import scenario.__TopLevel;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;

public class Tests {
  @Test
  public void testGenericCallPatternsOverride() {
    assertEquals(2, (int) __TopLevel.callPattern_ExactClassExactSpec_C2C2());
    assertEquals(1, (int) __TopLevel.callPattern_ExactClassExactSpec_C1C1());
    assertEquals(2, (int) __TopLevel.callPattern_SuperClassExactSpec_C1C2());
    assertEquals(2, (int) __TopLevel.callPattern_ExactClassSuperSpec_C2C2());
    assertEquals(1, (int) __TopLevel.callPattern_ExactClassSuperSpec_C1C1());
    assertEquals(2, (int) __TopLevel.callPattern_SuperClassSuperSpec_C1C2_1());
    assertEquals(2, (int) __TopLevel.callPattern_SuperClassSuperSpec_C1C2_2());
  }

  @Test
  public void testGenericCallPatternsNoOverride() {
    assertEquals(1, (int) __TopLevel.callPattern_ExactClassExactSpec_C4C4());
    assertEquals(1, (int) __TopLevel.callPattern_ExactClassExactSpec_C3C3());
    assertEquals(1, (int) __TopLevel.callPattern_SuperClassExactSpec_C3C4());
    assertEquals(1, (int) __TopLevel.callPattern_ExactClassSuperSpec_C4C4());
    assertEquals(1, (int) __TopLevel.callPattern_ExactClassSuperSpec_C3C3());
    assertEquals(1, (int) __TopLevel.callPattern_SuperClassSuperSpec_C3C4_1());
    assertEquals(1, (int) __TopLevel.callPattern_SuperClassSuperSpec_C3C4_2());
  }

  @Test
  public void testGenericCallPatternsInterface() {
    assertEquals(2, (int) __TopLevel.callPattern_ExactClassExactSpec_C6C6());
    assertEquals(2, (int) __TopLevel.callPattern_SuperClassExactSpec_C5C6());
    assertEquals(2, (int) __TopLevel.callPattern_SuperClassSuperSpec_C5C6_1());
    assertEquals(2, (int) __TopLevel.callPattern_SuperClassSuperSpec_C5C6_2());
  }

  @Test
  public void testGenericCallPatternsAbstract() {
    assertEquals(2, (int) __TopLevel.callPattern_ExactClassExactSpec_C8C8());
    assertEquals(2, (int) __TopLevel.callPattern_SuperClassExactSpec_C7C8());
    assertEquals(2, (int) __TopLevel.callPattern_SuperClassSuperSpec_C7C8_1());
    assertEquals(2, (int) __TopLevel.callPattern_SuperClassSuperSpec_C7C8_2());
  }
}
