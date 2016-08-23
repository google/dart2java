package dart._runtime.helpers;

public class MathHelper {
  public static double atan2(Number a, Number b) {
    return Math.atan2(a.doubleValue(), b.doubleValue());
  }

  public static double min(Number a, Number b) {
    return Math.min(a.doubleValue(), b.doubleValue());
  }

  public static double max(Number a, Number b) {
    return Math.max(a.doubleValue(), b.doubleValue());
  }

  public static double pow(Number x, Number exponent) {
    return Math.pow(x.doubleValue(), exponent.doubleValue());
  }

  public static double sin(Number x) {
    return Math.sin(x.doubleValue());
  }

  public static double cos(Number x) {
    return Math.cos(x.doubleValue());
  }

  public static double tan(Number x) {
    return Math.tan(x.doubleValue());
  }

  public static double asin(Number x) {
    return Math.asin(x.doubleValue());
  }

  public static double acos(Number x) {
    return Math.acos(x.doubleValue());
  }

  public static double atan(Number x) {
    return Math.atan(x.doubleValue());
  }

  public static double sqrt(Number x) {
    return Math.sqrt(x.doubleValue());
  }

  public static double exp(Number x) {
    return Math.exp(x.doubleValue());
  }

  public static double log(Number x) {
    return Math.log(x.doubleValue());
  }
}