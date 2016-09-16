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
import dart._runtime.types.simple.Type;
import dart._runtime.types.simple.TypeEnvironment;

public class BoolHelper {
  public static final InterfaceTypeInfo dart2java$typeInfo
      = new InterfaceTypeInfo(boolean.class, null);

  static {
    BoolHelper.dart2java$typeInfo.superclass = new InterfaceTypeExpr(ObjectHelper.dart2java$typeInfo);
  }

  public static final Type type = TypeEnvironment.ROOT.evaluate(
      new InterfaceTypeExpr(dart2java$typeInfo));
  
  // --- Methods defined in Object ---

  public static boolean operatorEqual(Boolean self, Object other) {
    return self.equals(other);
  }

  public static int getHashCode(Boolean self) {
    return self.hashCode();
  }

  // TODO(springerm): noSuchMethod
  // TODO(springerm): runtimeType


  // --- Methods defined in bool ---

  public static String toString(boolean self) {
    return self ? "true" : "false";
  }

}
