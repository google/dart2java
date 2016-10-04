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
  @Test(expected=ClassCastException.class)
  public void testTypeCheckFailsPrimitive() {
    __TopLevel.testTypeCheckFailsPrimitive();
  }

  @Test(expected=RuntimeException.class)
  public void testTypeCheckFails() {
    __TopLevel.testTypeCheckFails();
  }

  @Test
  public void testTypeCheckSubtypeOK() {
    __TopLevel.testTypeCheckSubtypeOK();
  }

  @Test
  public void testTypeCheckInterfaceOK() {
    __TopLevel.testTypeCheckInterfaceOK();
  }

  @Test(expected=RuntimeException.class)
  public void testTypeCheckInterfaceFails() {
    __TopLevel.testTypeCheckInterfaceFails();
  }

  @Test
  public void testTypeCheckGenericInterfaceOK() {
    __TopLevel.testTypeCheckGenericInterfaceOK();
  }

  @Test(expected=RuntimeException.class)
  public void testTypeCheckGenericInterfaceFails() {
    __TopLevel.testTypeCheckGenericInterfaceFails();
  }

  // Generic parameters
  @Test
  public void testGenericTypeIntOK() {
    assertEquals(5, (int) __TopLevel.testGenericTypeIntOK());
  }

  @Test(expected=RuntimeException.class)
  public void testGenericTypeIntFails() {
    __TopLevel.testGenericTypeIntFails();
  }

  @Test
  public void testGenericTypeStringOK1() {
    assertEquals("B", __TopLevel.testGenericTypeStringOK1());
  }

  @Test
  public void testGenericTypeObjectOK() {
    assertEquals("D", __TopLevel.testGenericTypeObjectOK());
  }

  @Test(expected=RuntimeException.class)
  public void testGenericTypeStringFails() {
    __TopLevel.testGenericTypeStringFails();
  }
}
