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
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;

import org.junit.Test;
import scenario.__TopLevel;

public class Tests {
  @Test
  public void testCovariance() {
    assertEquals(72, (int) __TopLevel.testSubclassSubtype());
  }

  @Test
  public void testSubtype() {
    assertEquals(36, (int) __TopLevel.testTypeSubtypeInList());
  }

  @Test
  public void testSpecialization() {
    // Cannot test if the correct specialization was chosen,
    // have to look at the generated code!
    assertEquals(60, (int) __TopLevel.testSingleTypeArgumentSpecialization());
    assertEquals("102030",  __TopLevel.testAssignmentOfSpecialization());
  }
}
