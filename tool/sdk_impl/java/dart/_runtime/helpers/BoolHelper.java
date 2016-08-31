package dart._runtime.helpers;

import dart._runtime.types.simple.InterfaceTypeExpr;
import dart._runtime.types.simple.InterfaceTypeInfo;

public class BoolHelper {
  public static final InterfaceTypeInfo dart2java$typeInfo
      = new InterfaceTypeInfo(boolean.class, null);

  static {
    BoolHelper.dart2java$typeInfo.superclass = new InterfaceTypeExpr(ObjectHelper.dart2java$typeInfo);
  }

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
