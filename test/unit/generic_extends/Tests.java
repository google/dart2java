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
  public void testGetterSetter() {
    assertEquals("5610", __TopLevel.testGetterSetter());
  }

  @Test
  public void testExtends() {
    assertEquals(60, __TopLevel.testExtends());
  }

  public void testMixtureGenericNonGeneric() {
    assertEquals(30890, (int) __TopLevel.testFoo());
    assertEquals(30890, (int) __TopLevel.testFooSuperSpec());
    assertEquals(360, (int) __TopLevel.testBaz());
  }
}
