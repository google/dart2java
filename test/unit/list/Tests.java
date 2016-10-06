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

import java.util.List;
import org.junit.Test;
import scenario.__TopLevel;

public class Tests {
  @Test
  public void testListMethods() {
    assertEquals(12, (int) __TopLevel.createAndOperatorAt());
    assertEquals(170, (int) __TopLevel.simpleOperations());
    assertEquals(18, (int) __TopLevel.triggerArrayGrowth());
  }

  @Test
  public void testListLiteral() {
    assertEquals(30, (int) __TopLevel.listLiteral());
  }

  @Test
  public void testJavaListInterface() {
    int result = 0;
    List<Integer> list = (List<Integer>) __TopLevel.getIntList();
    
    for (int value : list) {
      result = result + value;
    }

    assertEquals(5 + 8 + 10, result);
  }

  @Test
  public void testListListString() {
    assertEquals("Hello World", __TopLevel.testListListString());
  }

  @Test
  public void testInlineReturn() {
    assertEquals("Dart", __TopLevel.testInlineReturn());
  }

  @Test
  public void testBoolList() {
    assertEquals("truefalsetrue", __TopLevel.testBoolList());
  }

  @Test
  public void testDoubleList() {
    assertEquals(7.6, (double) __TopLevel.testDoubleList(), 0.01);
  }
}
