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
  public void testOperatorEqual() {
    assertFalse(__TopLevel.equalObjectObject());
    assertFalse(__TopLevel.equalObjectInt());
    assertFalse(__TopLevel.equalIntObject());
    assertTrue(__TopLevel.equalIntInt());
  }
  
  @Test
  public void testHashCode() {
    // Just want to make sure that it compiles...
    __TopLevel.hashCodeObject();
    __TopLevel.hashCodeClass1();
    __TopLevel.hashCodeInteger();
  }

  @Test
  public void testToString() {
    assertEquals("Instance of 'Object'", __TopLevel.toStringObject());
    assertEquals("12", __TopLevel.toStringInteger());
  }

  @Test
  public void testSubclassOfDartClass() {
    assertFalse(__TopLevel.equalObjectClass1());
    assertFalse(__TopLevel.equalClass1Object());
    assertFalse(__TopLevel.equalClass1Class1());
  }
}