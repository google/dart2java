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

import dart._runtime.base.DartObject;
import dart._runtime.types.simple.InterfaceType;
import dart._runtime.types.simple.InterfaceTypeExpr;
import dart._runtime.types.simple.InterfaceTypeInfo;
import dart._runtime.types.simple.Type;
import dart._runtime.types.simple.TypeEnvironment;

import java.util.HashMap;
import java.util.Map;

public class TypeSystemHelper {
  public static final Type getTrueType(Object o) {
    if (o instanceof DartObject) {
      return ((DartObject)o).dart2java$type;
    } else if (o == null) {
      return nullType;
    } else {
      return javaClasses.getOrDefault(o.getClass(), objectType);
    }
  }

  private static InterfaceType eval(InterfaceTypeInfo info) {
    return TypeEnvironment.ROOT.evaluate(new InterfaceTypeExpr(info));
  }

  private static final InterfaceType nullType = eval(dart.core.Null.dart2java$typeInfo);
  private static final InterfaceType objectType = eval(dart._runtime.helpers.ObjectHelper.dart2java$typeInfo);
  private static final Map<Class<?>, InterfaceType> javaClasses = new HashMap<>();

  static {
    javaClasses.put(Object.class, objectType);
    javaClasses.put(Integer.class, eval(dart._runtime.helpers.IntegerHelper.dart2java$typeInfo));
    javaClasses.put(Double.class, eval(dart._runtime.helpers.DoubleHelper.dart2java$typeInfo));
    javaClasses.put(Boolean.class, eval(dart._runtime.helpers.BoolHelper.dart2java$typeInfo));
    javaClasses.put(String.class, eval(dart._runtime.helpers.StringHelper.dart2java$typeInfo));
  }
}
