package dart._runtime.helpers;

class StringHelper {

  public static String operatorAt(String self, Integer index) {
    return self.charAt(index).toString();
  }

  public static String operatorPlus(String self, String other) {
    return self + other;
  }

  public static Integer getLength(String self) {
    return self.length;
  }
  
}