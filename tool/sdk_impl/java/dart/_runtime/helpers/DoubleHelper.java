package dart._runtime.helpers;

import dart._runtime.types.simple.InterfaceTypeExpr;
import dart._runtime.types.simple.InterfaceTypeInfo;

public class DoubleHelper {
  public static final InterfaceTypeInfo dart2java$typeInfo
      = new InterfaceTypeInfo("dart:core", "double");

  static {
    DoubleHelper.dart2java$typeInfo.superclass
        = new InterfaceTypeExpr(NumberHelper.dart2java$typeInfo);
  }

  // --- Methods defined in Object ---

  // TODO(springerm): noSuchMethod
  // TODO(springerm): runtimeType


  // --- Methods defined in Comparable ---


  // --- Methods defined in num ---
  
  public static Boolean operatorEqual(Double self, Object other) {
    return self.equals(other);
  }

  public static Integer getHashCode(Double self) {
    return self.hashCode();
  }

  public static Integer compareTo(Double self, Integer other) {
    if (self < other) {
      return -1;
    } else if (self > other) {
      return 1;
    } else {
      return 0;
    }
  }

  public static Integer compareTo(Double self, Double other) {
    if (self < other) {
      return -1;
    } else if (self > other) {
      return 1;
    } else {
      return 0;
    }
  }

  public static Integer compareTo(Double self, Number other) {
    if (other instanceof Integer) {
      return compareTo(self, (Integer) other);
    } else {
      // Must be double
      return compareTo(self, (Double) other);
    }
  }

  public static Double operatorPlus(Double self, Integer other) {
    return self + other;
  }

  public static Double operatorPlus(Double self, Double other) {
    return self + other;
  }

  public static Number operatorPlus(Double self, Number other) {
    if (other instanceof Integer) {
      return operatorPlus(self, (Integer) other);
    } else {
      // Must be double
      return operatorPlus(self, (Double) other);
    }
  }

  public static Double operatorMinus(Double self, Integer other) {
    return self - other;
  }

  public static Double operatorMinus(Double self, Double other) {
    return self - other;
  }

  public static Number operatorMinus(Double self, Number other) {
    if (other instanceof Integer) {
      return operatorMinus(self, (Integer) other);
    } else {
      // Must be double
      return operatorMinus(self, (Double) other);
    }
  }

  public static Double operatorStar(Double self, Integer other) {
    return self * other;
  }

  public static Double operatorStar(Double self, Double other) {
    return self * other;
  }

  public static Number operatorStar(Double self, Number other) {
    if (other instanceof Integer) {
      return operatorStar(self, (Integer) other);
    } else {
      // Must be double
      return operatorStar(self, (Double) other);
    }
  }

  public static Double operatorModulus(Double self, Integer other) {
    return self % other;
  }

  public static Double operatorModulus(Double self, Double other) {
    return self % other;
  }

  public static Number operatorModulus(Double self, Number other) {
    if (other instanceof Integer) {
      return operatorModulus(self, (Integer) other);
    } else {
      // Must be double
      return operatorModulus(self, (Double) other);
    }
  }

  public static Double operatorDivide(Double self, Integer other) {
    return self / other;
  } 

  public static Double operatorDivide(Double self, Double other) {
    return self / other;
  } 

  public static Double operatorDivide(Double self, Number other) {
    if (other instanceof Integer) {
      return operatorDivide(self, (Integer) other);
    } else {
      // Must be double
      return operatorDivide(self, (Double) other);
    }
  }

  public static Integer operatorTruncatedDivide(Double self, Integer other) {
    return (int) (self / other);
  }

  public static Integer operatorTruncatedDivide(Double self, Double other) {
    return (int) (self / other);
  }

  public static Integer operatorTruncatedDivide(Double self, Number other) {
    if (other instanceof Integer) {
      return operatorTruncatedDivide(self, (Integer) other);
    } else {
      // Must be double
      return operatorTruncatedDivide(self, (Double) other);
    }
  }

  public static Double operatorUnaryMinus(Double self) {
    return -self;
  }

  // TODO(springerm): remainder

  public static Boolean operatorLess(Double self, Integer other) {
    return self < other;
  }

  public static Boolean operatorLess(Double self, Double other) {
    return self < other;
  }

  public static Boolean operatorLess(Double self, Number other) {
    if (other instanceof Integer) {
      return operatorLess(self, (Integer) other);
    } else {
      // Must be double
      return operatorLess(self, (Double) other);
    }
  }

  public static Boolean operatorLessEqual(Double self, Integer other) {
    return self <= other;
  }

  public static Boolean operatorLessEqual(Double self, Double other) {
    return self <= other;
  }

  public static Boolean operatorLessEqual(Double self, Number other) {
    if (other instanceof Integer) {
      return operatorLessEqual(self, (Integer) other);
    } else {
      // Must be double
      return operatorLessEqual(self, (Double) other);
    }
  }

  public static Boolean operatorGreater(Double self, Integer other) {
    return self > other;
  }

  public static Boolean operatorGreater(Double self, Double other) {
    return self > other;
  }

  public static Boolean operatorGreater(Double self, Number other) {
    if (other instanceof Integer) {
      return operatorGreater(self, (Integer) other);
    } else {
      // Must be double
      return operatorGreater(self, (Double) other);
    }
  }

  public static Boolean operatorGreaterEqual(Double self, Integer other) {
    return self >= other;
  }

  public static Boolean operatorGreaterEqual(Double self, Double other) {
    return self >= other;
  }

  public static Boolean operatorGreaterEqual(Double self, Number other) {
    if (other instanceof Integer) {
      return operatorGreaterEqual(self, (Integer) other);
    } else {
      // Must be double
      return operatorGreaterEqual(self, (Double) other);
    }
  }

  public static Boolean isNaN(Double self) {
    return self.isNaN();
  }

  public static Boolean isNegative(Double self) {
    return Double.compare(self, 0.0) < 0;
  }

  public static Boolean isInfinite(Double self) {
    return self.isInfinite();
  }

  public static Boolean isFinite(Double self) {
    return !self.isInfinite();
  }

  public static Double abs(Double self) {
    return Math.abs(self);
  }

  public static Double getSign(Double self) {
    return Math.signum(self);
  }

  public static Integer round(Double self) {
    return (int) Math.round(self);
  }

  public static Integer floor(Double self) {
    return (int) Math.floor(self);
  }

  public static Integer ceil(Double self) {
    return (int) Math.ceil(self);
  }

  public static Integer truncate(Double self) {
    return self.intValue();
  }

  // TODO(springerm): roundToDouble
  // TODO(springerm): floorToDouble
  // TODO(springerm:) ceilToDouble
  // TODO(springerm): truncateToDouble
  // TODO(springerm): clamp

  public static Integer toInt(Double self) {
    return self.intValue();
  }

  public static Double toDouble(Double self) {
    return self;
  }

  // TODO(springerm): toStringAsFixed
  // TODO(springerm): toStringAsExponential
  // TODO(springerm): toStringAsPrecision

  public static String toString(Double self) {
    return self.toString();
  }


  // --- Methods defined in double ---

  public static class Static {
    public static final Double NAN = Double.NaN;
    public static final Double INFINITY = Double.POSITIVE_INFINITY;
    public static final Double NEGATIVE_INFINITY = Double.NEGATIVE_INFINITY;
    public static final Double MIN_POSITIVE = Double.MIN_VALUE;
    public static final Double MAX_FINITE = Double.MAX_VALUE;

    // TODO(springerm): parse
  }

}
