package dart._runtime.helpers;

public class IntegerHelper {

  public static Integer operatorMinusUnary(Integer self) {
   return -self;
  }

  public static Integer operatorMinus(Integer self, Integer other) {
   return self - other;
  }

  public static Integer operatorPlus(Integer self, Integer other) {
   return self + other;
  }

  public static Integer operatorStar(Integer self, Integer other) {
   return self * other;
  }

  public static Boolean operatorLess(Integer self, Integer other) {
    return self < other;
  }

  public static Boolean operatorEqual(Integer self, Integer other) {
    return self == other;
  }

  public static String toString(Integer self) {
   return self.toString();
  }
  
}