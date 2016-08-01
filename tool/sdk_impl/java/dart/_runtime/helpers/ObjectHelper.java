package dart._runtime.helpers;

public class ObjectHelper {

  public static Integer getHashCode(Object self) {
    return self.hashCode();
  }

  public static Boolean equals(Object self, Object other) {
    return self == null ? other == null : self.equals(other);
  }

  public static String toString(Object self) {
    return self == null ? "null" : self.toString();
  }

}

