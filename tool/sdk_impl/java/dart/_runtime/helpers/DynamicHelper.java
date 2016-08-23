package dart._runtime.helpers;

import java.util.Map;
import java.util.HashMap;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.lang.reflect.Method;

public class DynamicHelper {
  private static final Map<Class<?>, Class<?>> classImpls;

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

      if (classImpls.containsKey(receiverClass)) {
        receiverClass = classImpls.get(receiverClass);

        // There may be multiple overloadings for operators, we have to
        // pick the correct one
        checkForOverloads = true;
      }

      // TODO(springerm): Try to use MethodHandles API for lookup
      // TODO(springerm): Look into byte code generation for invokedynamic
      Method method = null;
      
      methodLoop: 
      for (Method m : receiverClass.getMethods()) {
        if (m.getName().equals(methodName)) {
          if (checkForOverloads) {
            // Check if the parameter types for this method match
            Class<?>[] parameterTypes = m.getParameterTypes();
            for (int i = 0; i < parameterTypes.length; i++) {
              if (!parameterTypes[i].isAssignableFrom(
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