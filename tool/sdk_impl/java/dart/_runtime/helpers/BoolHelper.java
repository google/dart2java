package dart._runtime.helpers;

public class BoolHelper {

  // --- Methods defined in Object ---

  public static Boolean operatorEqual(Boolean self, Object other) {
    return self.equals(other);
  }

  public static Integer getHashCode(Boolean self) {
    return self.hashCode();
  }

  // TODO(springerm): noSuchMethod
  // TODO(springerm): runtimeType


  // --- Methods defined in bool ---

  public static String toString(Boolean self) {
    return self ? "true" : "false";
  }

}