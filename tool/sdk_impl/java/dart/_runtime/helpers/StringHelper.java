package dart._runtime.helpers;

public class StringHelper {

  public static String operatorAt(String self, Integer index) {
    return String.valueOf(self.charAt(index));
  }

  public static String operatorPlus(String self, String other) {
    return self + other;
  }

  public static Integer getLength(String self) {
    return self.length();
  }
  
}