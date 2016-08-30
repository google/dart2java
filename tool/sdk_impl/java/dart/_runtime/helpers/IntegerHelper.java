package dart._runtime.helpers;

import dart._runtime.types.simple.InterfaceTypeExpr;
import dart._runtime.types.simple.InterfaceTypeInfo;

public class IntegerHelper {
  public static final InterfaceTypeInfo dart2java$typeInfo
      = new InterfaceTypeInfo(int.class, null);

  static {
    IntegerHelper.dart2java$typeInfo.superclass = new InterfaceTypeExpr(NumberHelper.dart2java$typeInfo);
  }

  // --- Methods defined in Object ---

  // TODO(springerm): noSuchMethod
  // TODO(springerm): runtimeType


  // --- Methods defined in Comparable ---


  // --- Methods defined in num ---
  
  public static Boolean operatorEqual(Integer self, Object other) {
    return self.equals(other);
  }

  public static Integer getHashCode(Integer self) {
    return self;
  }

  public static Integer compareTo(Integer self, Integer other) {
    if (self < other) {
      return -1;
    } else if (self > other) {
      return 1;
    } else {
      return 0;
    }
  }

  public static Integer compareTo(Integer self, Double other) {
    if (self < other) {
      return -1;
    } else if (self > other) {
      return 1;
    } else {
      return 0;
    }
  }

  public static Integer compareTo(Integer self, Number other) {
    if (other instanceof Integer) {
      return compareTo(self, (Integer) other);
    } else {
      // Must be double
      return compareTo(self, (Double) other);
    }
  }


  public static Integer operatorPlus(Integer self, Integer other) {
    return self + other;
  }

  public static Double operatorPlus(Integer self, Double other) {
    return self + other;
  }

  public static Number operatorPlus(Integer self, Number other) {
    if (other instanceof Integer) {
      return operatorPlus(self, (Integer) other);
    } else {
      // Must be double
      return operatorPlus(self, (Double) other);
    }
  }

  public static Integer operatorMinus(Integer self, Integer other) {
    return self - other;
  }

  public static Double operatorMinus(Integer self, Double other) {
    return self - other;
  }

  public static Number operatorMinus(Integer self, Number other) {
    if (other instanceof Integer) {
      return operatorMinus(self, (Integer) other);
    } else {
      // Must be double
      return operatorMinus(self, (Double) other);
    }
  }

  public static Integer operatorStar(Integer self, Integer other) {
    return self * other;
  }

  public static Double operatorStar(Integer self, Double other) {
    return self * other;
  }

  public static Number operatorStar(Integer self, Number other) {
    if (other instanceof Integer) {
      return operatorStar(self, (Integer) other);
    } else {
      // Must be double
      return operatorStar(self, (Double) other);
    }
  }

  public static Integer operatorModulus(Integer self, Integer other) {
    return self % other;
  }

  // TODO(springerm): operatorModulus for Double
  // TODO(springerm): operatorModulus for Number

  public static Double operatorDivide(Integer self, Integer other) {
    return ((double) self) / other;
  } 

  public static Double operatorDivide(Integer self, Double other) {
    return ((double) self) / other;
  } 

  public static Double operatorDivide(Integer self, Number other) {
    if (other instanceof Integer) {
      return operatorDivide(self, (Integer) other);
    } else {
      // Must be double
      return operatorDivide(self, (Double) other);
    }
  }

  public static Integer operatorTruncatedDivide(Integer self, Integer other) {
    return (Integer) (self / other);
  }

  public static Integer operatorTruncatedDivide(Integer self, Double other) {
    return (int) (self / other);
  }

  public static Integer operatorTruncatedDivide(Integer self, Number other) {
    if (other instanceof Integer) {
      return operatorTruncatedDivide(self, (Integer) other);
    } else {
      // Must be double
      return operatorTruncatedDivide(self, (Double) other);
    }
  }

  public static Integer operatorUnaryMinus(Integer self) {
    return -self;
  }

  // TODO(springerm): remainder

  public static Boolean operatorLess(Integer self, Integer other) {
    return self < other;
  }

  public static Boolean operatorLess(Integer self, Double other) {
    return self < other;
  }

  public static Boolean operatorLess(Integer self, Number other) {
    if (other instanceof Integer) {
      return operatorLess(self, (Integer) other);
    } else {
      // Must be double
      return operatorLess(self, (Double) other);
    }
  }

  public static Boolean operatorLessEqual(Integer self, Integer other) {
    return self <= other;
  }

  public static Boolean operatorLessEqual(Integer self, Double other) {
    return self <= other;
  }

  public static Boolean operatorLessEqual(Integer self, Number other) {
    if (other instanceof Integer) {
      return operatorLessEqual(self, (Integer) other);
    } else {
      // Must be double
      return operatorLessEqual(self, (Double) other);
    }
  }

  public static Boolean operatorGreater(Integer self, Integer other) {
    return self > other;
  }

  public static Boolean operatorGreater(Integer self, Double other) {
    return self > other;
  }

  public static Boolean operatorGreater(Integer self, Number other) {
    if (other instanceof Integer) {
      return operatorGreater(self, (Integer) other);
    } else {
      // Must be double
      return operatorGreater(self, (Double) other);
    }
  }

  public static Boolean operatorGreaterEqual(Integer self, Integer other) {
    return self >= other;
  }

  public static Boolean operatorGreaterEqual(Integer self, Double other) {
    return self >= other;
  }

  public static Boolean operatorGreaterEqual(Integer self, Number other) {
    if (other instanceof Integer) {
      return operatorGreaterEqual(self, (Integer) other);
    } else {
      // Must be double
      return operatorGreaterEqual(self, (Double) other);
    }
  }
  
  public static Boolean isNaN(Integer self) {
    return false;
  }

  public static Boolean isNegative(Integer self) {
    return self < 0;
  }

  public static Boolean isInfinite(Integer self) {
    return false;
  }

  public static Boolean isFinite(Integer self) {
    return true;
  }

  public static Integer abs(Integer self) {
    return Integer.signum(self) * self;
  }

  public static Integer getSign(Integer self) {
    return Integer.signum(self);
  }

  public static Integer round(Integer self) {
    return self;
  }

  public static Integer floor(Integer self) {
    return self;
  }

  public static Integer ceil(Integer self) {
    return self;
  }

  public static Integer truncate(Integer self) {
    return self;
  }

  // TODO(springerm): roundToDouble
  // TODO(springerm): floorToDouble
  // TODO(springerm:) ceilToDouble
  // TODO(springerm): truncateToDouble
  // TODO(springerm): clamp

  public static Integer toInt(Integer self) {
    return self;
  }

  public static Double toDouble(Integer self) {
    return (double) self;
  }

  // TODO(springerm): toStringAsFixed
  // TODO(springerm): toStringAsExponential
  // TODO(springerm): toStringAsPrecision

  public static String toString(Integer self) {
    return self.toString();
  }


  // -- Methods defined in int --

  public static Integer operatorBitAnd(Integer self, Integer other) {
    return self & other;
  }

  public static Integer operatorBitOr(Integer self, Integer other) {
    return self | other;
  }

  public static Integer operatorBitXor(Integer self, Integer other) {
    return self ^ other;
  }

  public static Integer operatorUnaryBitNegate(Integer self) {
    return ~self;
  }

  public static Integer operatorShiftLeft(Integer self, Integer other) {
    return self << other;
  }

  public static Integer operatorShiftRight(Integer self, Integer other) {
    return self >> other;
  }

  // TODO(springerm): modPow
  // TODO(springerm): modInverse
  // TODO(springerm): gcd

  public static boolean isEven(Integer self) {
    return self % 2 == 0;
  }

  public static boolean isOdd(Integer self) {
    return self % 2 == 1;
  }

  // TODO(springerm): bitLength
  // TODO(springerm): toUnsigned
  // TODO(springerm): toSigned
  // TODO(springerm): toRadixString

  public static class Static {
    // TODO(springerm): parse
    // TODO(springerm): fromEnvironment
  }


  // --- Other methods ---

  public static Boolean operatorNotEqual(Integer self, Integer other) {
    return self != other;
  }

}
