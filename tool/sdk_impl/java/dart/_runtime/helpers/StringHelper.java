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

package dart._runtime.helpers;

import dart._runtime.types.simple.InterfaceTypeExpr;
import dart._runtime.types.simple.InterfaceTypeInfo;

public class StringHelper {
  public static final InterfaceTypeInfo dart2java$typeInfo
      = new InterfaceTypeInfo(String.class, null);

  static {
    StringHelper.dart2java$typeInfo.superclass
        = new InterfaceTypeExpr(ObjectHelper.dart2java$typeInfo);
  }

  // --- Methods defined in Object ---

  // TODO(springerm): noSuchMethod
  // TODO(springerm): runtimeType

  public static String toString(String self) {
    return self;
  }
  

  // --- Methods defined in String ---

  public static String operatorAt(String self, Integer index) {
    return String.valueOf(self.charAt(index));
  }

  // TODO(springerm): codeUnitAt

  public static Integer getLength(String self) {
    return self.length();
  }

  public static Integer getHashCode(String self) {
    return self.hashCode();
  }

  public static Boolean operatorEqual(String self, Object other) {
    return self.equals(other);
  }

  public static Boolean endsWith(String self, String other) {
    return self.endsWith(other);
  }

  // TODO(springerm): pattern should be a [Pattern] object
  public static Boolean startsWith(String self, String pattern, Integer index) {
    return self.startsWith(pattern, index);
  }

  public static Boolean startsWith(String self, String pattern) {
    return startsWith(self, pattern, 0);
  }

  // TODO(springerm): pattern should be a [Pattern] object
  public static Integer indexOf(String self, String pattern, Integer start) {
    return self.indexOf(pattern, start);
  }

  public static Integer indexOf(String self, String pattern) {
    return indexOf(self, pattern, 0);
  }

  // TODO(springerm): pattern should be a [Pattern] object
  public static Integer lastIndexOf(String self, String pattern, Integer start) {
    return self.lastIndexOf(pattern, start);
  }

  public static Integer lastIndexOf(String self, String pattern) {
    return lastIndexOf(self, pattern, 0);
  }

  public static Boolean getIsEmpty(String self) {
    return self.isEmpty();
  }

  public static Boolean getIsNotEmpty(String self) {
    return !self.isEmpty();
  }

  public static String operatorPlus(String self, String other) {
    return self + other;
  }

  public static String substring(String self, Integer startIndex, Integer endIndex) {
    return self.substring(startIndex, endIndex);
  }

  public static String substring(String self, Integer startIndex) {
    return self.substring(startIndex);
  }

  public static String trim(String self) {
    return self.trim();
  }

  // TODO(springerm): trimLeft
  // TODO(springerm): trimRight

  public static String operatorStar(String self, Integer times) {
    String result = "";
  
    for (int i = 0; i < times; i++) {
      result += self;
    }
  
    return result;
  }

  // TODO(springerm): padLeft
  // TODO(springerm): padRight

  // TODO(springerm): other should be a [Pattern]
  public static Boolean contains(String self, String other, Integer startIndex) {
    return self.indexOf(other, startIndex) != -1;
  }

  public static Boolean contains(String self, String other) {
    return contains(self, other, 0);
  }

  // TODO(springerm): replaceFirst
  // TODO(springerm): replaceFirstMapped
  // TODO(springerm): replaceAll
  // TODO(springerm): replaceAllMapped
  // TODO(springerm): replaceRange
  // TODO(springerm): split
  // TODO(springerm): splitMapJoin
  // TODO(springerm): getCodeUnits
  // TODO(springerm): getRunes

  public static String toLowerCase(String self) {
    return self.toLowerCase();
  }

  public static String toUpperCase(String self) {
    return self.toUpperCase();
  }

  public static class Static {
    public static String fromCharCode(int charCode) {
      return new String(new int[] { charCode }, 0, 1);
    }
  }

}
