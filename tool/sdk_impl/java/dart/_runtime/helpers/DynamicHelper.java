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

import java.util.Map;
import java.util.HashMap;
import java.util.Set;
import java.util.HashSet;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.reflect.Method;

public class DynamicHelper {
  private static final Map<Class<?>, Class<?>> classImpls;
  private static final Set<Class<?>> classesWithJavaInterfaces;
  
  private static final Class<?> boxedInt = Integer.class;
  private static final Class<?> boxedDouble = Double.class;
  private static final Class<?> boxedBool = Boolean.class;
  private static final Class<?> unboxedInt = int.class;
  private static final Class<?> unboxedDouble = double.class;
  private static final Class<?> unboxedBool = boolean.class;

  // TODO(springerm): Consider generating map initialization automatically
  // from compiler_state.dart.
  static {
    // Some method invocations have to go through helper classes
    classImpls = new HashMap<Class<?>, Class<?>>();
    classImpls.put(Object.class, dart._runtime.helpers.ObjectHelper.class);
    classImpls.put(Boolean.class, dart._runtime.helpers.BoolHelper.class);
    classImpls.put(Integer.class, dart._runtime.helpers.IntegerHelper.class);
    classImpls.put(Double.class, dart._runtime.helpers.DoubleHelper.class);
    classImpls.put(String.class, dart._runtime.helpers.StringHelper.class);
    classImpls.put(Number.class, dart._runtime.helpers.NumberHelper.class);

    classesWithJavaInterfaces = new HashSet<Class<?>>();
    classesWithJavaInterfaces.add(dart._runtime.base.DartList.Generic.class);
    classesWithJavaInterfaces.add(dart._runtime.base.DartList._int.class);
    classesWithJavaInterfaces.add(dart._runtime.base.DartMap.Generic.class);
  }

  public static Class<?> boxedToMaybeUnboxedType(Class<?> boxedType) {
    if (boxedType == boxedInt) {
      return unboxedInt;
    } else if (boxedType == boxedDouble) {
      return unboxedDouble;
    } else if (boxedType == boxedBool) {
      return unboxedBool;
    } else {
      return boxedType;
    }
  }

  public static Object invoke(String methodName, Object... recvAndArgs) {
    // TODO(springerm): Proper exception handling
    // TODO(springerm): Need to do Dart type checks for arguments
    // TODO(springerm): Special case methods defined on DartObject

    try {
      MethodHandles.Lookup lookup = MethodHandles.publicLookup();
      Object receiver = recvAndArgs[0];
      Class<?> receiverClass = receiver.getClass();
      boolean checkForOverloads = false;

      // TODO(springerm): Handle static methods properly
      // The index of the first argument. When calling a static method, the
      // first argument starts at index 0, because no "self" object is passed
      int startArgs = 1;

      if (classImpls.containsKey(receiverClass)) {
        receiverClass = classImpls.get(receiverClass);

        // There may be multiple overloadings for operators, we have to
        // pick the correct one
        checkForOverloads = true;
        startArgs = 0;
      } else if (classesWithJavaInterfaces.contains(receiverClass)) {
        checkForOverloads = true;
      }

      // TODO(springerm): Try to use MethodHandles API for lookup
      // TODO(springerm): Look into byte code generation for invokedynamic
      Method method = null;
      
      methodLoop: 
      for (Method m : receiverClass.getMethods()) {
        if (m.getName().equals(methodName)) {
          if (checkForOverloads) {
            Class<?>[] parameterTypes = m.getParameterTypes();

            // Check if the number of parameters match
            if (parameterTypes.length != recvAndArgs.length - startArgs) {
              continue methodLoop;
            }

            // Check if the parameter types for this method match
            for (int i = startArgs; i < parameterTypes.length; i++) {
              if (!parameterTypes[i - startArgs].isAssignableFrom(
                boxedToMaybeUnboxedType(recvAndArgs[i].getClass()))
                && !parameterTypes[i - startArgs].isAssignableFrom(
                recvAndArgs[i].getClass())) {
                // Picked the wrong method, continue searching
                continue methodLoop;
              }
            }
          }

          // Found matching method
          method = m;
          break methodLoop;
        }
      }

      if (method == null) {
        String argTypes = "";
        for (Object o : recvAndArgs) {
          argTypes = argTypes + o.getClass().getName() + ", ";
        }
        throw new NoSuchMethodException(
          receiverClass.getName() + "." + methodName 
            + " (" + argTypes + ") not found");
      }

      MethodHandle handle = lookup.unreflect(method);
      MethodHandle handleSpreader = handle.asSpreader(
        Object[].class, recvAndArgs.length);
      Object result = handleSpreader.invoke(recvAndArgs);

      return result;
    } catch (Throwable t) {
      throw new RuntimeException(t);
    }
  }
}
