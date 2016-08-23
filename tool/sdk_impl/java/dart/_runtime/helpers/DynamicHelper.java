package dart._runtime.helpers;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.lang.reflect.Method;

public class DynamicHelper {
  public static Object invoke(String methodName, Object... recvAndArgs) {
    // TODO(springerm): Proper exception handling
    try {
      MethodHandles.Lookup lookup = MethodHandles.publicLookup();
      Object receiver = recvAndArgs[0];

      // TODO(springerm): Try to use MethodHandles API for lookup
      // TODO(springerm): Look into byte code generation for invokedynamic
      Method method = null;
      for (Method m : receiver.getClass().getDeclaredMethods()) {
        if (m.getName().equals(methodName)) {
          method = m;
          break;
        }
      }

      if (method == null) {
        throw new NoSuchMethodException();
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