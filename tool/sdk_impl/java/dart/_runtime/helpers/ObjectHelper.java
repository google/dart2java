package dart._runtime.helpers;

public class ObjectHelper {

  public static Integer getHashCode(Object self) {
    // Hash code of null in Dart VM is 2011 ;)
    return self == null ? 2011 : self.hashCode();
  }

  public static Boolean operatorEqual(Object self, Object other) {
    return self == null ? other == null : self.equals(other);
  }

  public static String toString(Object self) {
    return self == null ? "null" : self.toString();
  }

}

