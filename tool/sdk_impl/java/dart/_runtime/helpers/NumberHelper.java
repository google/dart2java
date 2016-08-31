package dart._runtime.helpers;

import dart._runtime.types.simple.InterfaceTypeExpr;
import dart._runtime.types.simple.InterfaceTypeInfo;

public class NumberHelper {
  public static final InterfaceTypeInfo dart2java$typeInfo
      = new InterfaceTypeInfo(Number.class, null);

  static {
    NumberHelper.dart2java$typeInfo.superclass
        = new InterfaceTypeExpr(ObjectHelper.dart2java$typeInfo);
    // TODO(andrewkrieger): Add Comparable<num> in interfaces
  }

  // --- Methods defined in Object ---

  // TODO(springerm): noSuchMethod
  // TODO(springerm): runtimeType


  // --- Methods defined in Comparable ---


  // --- Methods defined in num ---
  
  public static boolean operatorEqual(Number self, Object other) {
    if (self instanceof Integer) {
      return IntegerHelper.operatorEqual((int) self, other);
    } else {
      // Must be double
      return DoubleHelper.operatorEqual((double) self, other);
    }
  }

  public static Integer getHashCode(Number self) {
    return self.hashCode();
  }

  public static Integer compareTo(Number self, int other) {
    if (self instanceof Integer) {
      return IntegerHelper.compareTo((int) self, other);
    } else {
      // Must be double
      return DoubleHelper.compareTo((double) self, other);
    }
  }

  public static Integer compareTo(Number self, double other) {
    if (self instanceof Integer) {
      return IntegerHelper.compareTo((int) self, other);
    } else {
      // Must be double
      return DoubleHelper.compareTo((double) self, other);
    }
  }

  public static Integer compareTo(Number self, Number other) {
    if (self instanceof Integer) {
      return IntegerHelper.compareTo((int) self, other);
    } else {
      // Must be double
      return DoubleHelper.compareTo((double) self, other);
    }
  }

  public static Number operatorPlus(Number self, int other) {
    if (self instanceof Integer) {
      return IntegerHelper.operatorPlus((int) self, other);
    } else {
      // Must be double
      return DoubleHelper.operatorPlus((double) self, other);
    }
  }

  public static Number operatorPlus(Number self, double other) {
    if (self instanceof Integer) {
      return IntegerHelper.operatorPlus((int) self, other);
    } else {
      // Must be double
      return DoubleHelper.operatorPlus((double) self, other);
    }
  }

  public static Number operatorPlus(Number self, Number other) {
    if (self instanceof Integer) {
      return IntegerHelper.operatorPlus((int) self, other);
    } else {
      // Must be double
      return DoubleHelper.operatorPlus((double) self, other);
    }
  }

  public static Number operatorMinus(Number self, int other) {
    if (self instanceof Integer) {
      return IntegerHelper.operatorMinus((int) self, other);
    } else {
      // Must be double
      return DoubleHelper.operatorMinus((double) self, other);
    }
  }

  public static Number operatorMinus(Number self, double other) {
    if (self instanceof Integer) {
      return IntegerHelper.operatorMinus((int) self, other);
    } else {
      // Must be double
      return DoubleHelper.operatorMinus((double) self, other);
    }
  }

  public static Number operatorMinus(Number self, Number other) {
    if (self instanceof Integer) {
      return IntegerHelper.operatorMinus((int) self, other);
    } else {
      // Must be double
      return DoubleHelper.operatorMinus((double) self, other);
    }
  }

  public static Number operatorStar(Number self, int other) {
    if (self instanceof Integer) {
      return IntegerHelper.operatorStar((int) self, other);
    } else {
      // Must be double
      return DoubleHelper.operatorStar((double) self, other);
    }
  }

  public static Number operatorStar(Number self, double other) {
    if (self instanceof Integer) {
      return IntegerHelper.operatorStar((int) self, other);
    } else {
      // Must be double
      return DoubleHelper.operatorStar((double) self, other);
    }
  }

  public static Number operatorStar(Number self, Number other) {
    if (self instanceof Integer) {
      return IntegerHelper.operatorStar((int) self, other);
    } else {
      // Must be double
      return DoubleHelper.operatorStar((double) self, other);
    }
  }

  public static Number operatorModulus(Number self, int other) {
    if (self instanceof Integer) {
      return IntegerHelper.operatorModulus((int) self, other);
    } else {
      throw new RuntimeException("Modulo not implemented for double");
    }
  }

  // TODO(springerm): operatorModulus for Double
  // TODO(springerm): operatorModulus for Number

  public static Number operatorDivide(Number self, int other) {
    if (self instanceof Integer) {
      return IntegerHelper.operatorDivide((int) self, other);
    } else {
      // Must be double
      return DoubleHelper.operatorDivide((double) self, other);
    }
  }

  public static Number operatorDivide(Number self, double other) {
    if (self instanceof Integer) {
      return IntegerHelper.operatorDivide((int) self, other);
    } else {
      // Must be double
      return DoubleHelper.operatorDivide((double) self, other);
    }
  }

  public static Number operatorDivide(Number self, Number other) {
    if (self instanceof Integer) {
      return IntegerHelper.operatorDivide((int) self, other);
    } else {
      // Must be double
      return DoubleHelper.operatorDivide((double) self, other);
    }
  }

  public static Integer operatorTruncatedDivide(Number self, int other) {
    if (self instanceof Integer) {
      return IntegerHelper.operatorTruncatedDivide((int) self, other);
    } else {
      // Must be double
      return DoubleHelper.operatorTruncatedDivide((double) self, other);
    }
  }

  public static Integer operatorTruncatedDivide(Number self, double other) {
    if (self instanceof Integer) {
      return IntegerHelper.operatorTruncatedDivide((int) self, other);
    } else {
      // Must be double
      return DoubleHelper.operatorTruncatedDivide((double) self, other);
    }
  }

  public static Integer operatorTruncatedDivide(Number self, Number other) {
    if (self instanceof Integer) {
      return IntegerHelper.operatorTruncatedDivide((int) self, other);
    } else {
      // Must be double
      return DoubleHelper.operatorTruncatedDivide((double) self, other);
    }
  }

  public static Number operatorUnaryMinus(Number self) {
    if (self instanceof Integer) {
      return IntegerHelper.operatorUnaryMinus((int) self);
    } else {
      // Must be double
      return DoubleHelper.operatorUnaryMinus((double) self);
    }
  }

  // TODO(springerm): remainder

  public static boolean operatorLess(Number self, int other) {
    if (self instanceof Integer) {
      return IntegerHelper.operatorLess((int) self, other);
    } else {
      // Must be double
      return DoubleHelper.operatorLess((double) self, other);
    }
  }

  public static boolean operatorLess(Number self, double other) {
    if (self instanceof Integer) {
      return IntegerHelper.operatorLess((int) self, other);
    } else {
      // Must be double
      return DoubleHelper.operatorLess((double) self, other);
    }
  }

  public static boolean operatorLess(Number self, Number other) {
    if (self instanceof Integer) {
      return IntegerHelper.operatorLess((int) self, other);
    } else {
      // Must be double
      return DoubleHelper.operatorLess((double) self, other);
    }
  }

  public static boolean operatorLessEqual(Number self, int other) {
    if (self instanceof Integer) {
      return IntegerHelper.operatorLessEqual((int) self, other);
    } else {
      // Must be double
      return DoubleHelper.operatorLessEqual((double) self, other);
    }
  }

  public static boolean operatorLessEqual(Number self, double other) {
    if (self instanceof Integer) {
      return IntegerHelper.operatorLessEqual((int) self, other);
    } else {
      // Must be double
      return DoubleHelper.operatorLessEqual((double) self, other);
    }
  }

  public static boolean operatorLessEqual(Number self, Number other) {
    if (self instanceof Integer) {
      return IntegerHelper.operatorLessEqual((int) self, other);
    } else {
      // Must be double
      return DoubleHelper.operatorLessEqual((double) self, other);
    }
  }

  public static boolean operatorGreater(Number self, int other) {
    if (self instanceof Integer) {
      return IntegerHelper.operatorGreater((int) self, other);
    } else {
      // Must be double
      return DoubleHelper.operatorGreater((double) self, other);
    }
  }

  public static boolean operatorGreater(Number self, double other) {
    if (self instanceof Integer) {
      return IntegerHelper.operatorGreater((int) self, other);
    } else {
      // Must be double
      return DoubleHelper.operatorGreater((double) self, other);
    }
  }

  public static boolean operatorGreater(Number self, Number other) {
    if (self instanceof Integer) {
      return IntegerHelper.operatorGreater((int) self, other);
    } else {
      // Must be double
      return DoubleHelper.operatorGreater((double) self, other);
    }
  }

  public static boolean operatorGreaterEqual(Number self, int other) {
    if (self instanceof Integer) {
      return IntegerHelper.operatorGreaterEqual((int) self, other);
    } else {
      // Must be double
      return DoubleHelper.operatorGreaterEqual((double) self, other);
    }
  }

  public static boolean operatorGreaterEqual(Number self, double other) {
    if (self instanceof Integer) {
      return IntegerHelper.operatorGreaterEqual((int) self, other);
    } else {
      // Must be double
      return DoubleHelper.operatorGreaterEqual((double) self, other);
    }
  }

  public static boolean operatorGreaterEqual(Number self, Number other) {
    if (self instanceof Integer) {
      return IntegerHelper.operatorGreaterEqual((int) self, other);
    } else {
      // Must be double
      return DoubleHelper.operatorGreaterEqual((double) self, other);
    }
  }

  public static boolean isNaN(Number self) {
    if (self instanceof Integer) {
      return IntegerHelper.isNaN((int) self);
    } else {
      // Must be double
      return DoubleHelper.isNaN((double) self);
    }
  }

  public static boolean isNegative(Number self) {
    if (self instanceof Integer) {
      return IntegerHelper.isNegative((int) self);
    } else {
      // Must be double
      return DoubleHelper.isNegative((double) self);
    }
  }

  public static boolean isInfinite(Number self) {
    if (self instanceof Integer) {
      return IntegerHelper.isInfinite((int) self);
    } else {
      // Must be double
      return DoubleHelper.isInfinite((double) self);
    }
  }

  public static boolean isFinite(Number self) {
    if (self instanceof Integer) {
      return IntegerHelper.isFinite((int) self);
    } else {
      // Must be double
      return DoubleHelper.isFinite((double) self);
    }
  }

  public static Number abs(Number self) {
    if (self instanceof Integer) {
      return IntegerHelper.abs((int) self);
    } else {
      // Must be double
      return DoubleHelper.abs((double) self);
    }
  }

  public static Number getSign(Number self) {
    if (self instanceof Integer) {
      return IntegerHelper.getSign((int) self);
    } else {
      // Must be double
      return DoubleHelper.getSign((double) self);
    }
  }

  public static Integer round(Number self) {
    if (self instanceof Integer) {
      return IntegerHelper.round((int) self);
    } else {
      // Must be double
      return DoubleHelper.round((double) self);
    }
  }

  public static Integer floor(Number self) {
    if (self instanceof Integer) {
      return IntegerHelper.floor((int) self);
    } else {
      // Must be double
      return DoubleHelper.floor((double) self);
    }
  }

  public static Integer ceil(Number self) {
    if (self instanceof Integer) {
      return IntegerHelper.ceil((int) self);
    } else {
      // Must be double
      return DoubleHelper.ceil((double) self);
    }
  }

  public static Integer truncate(Number self) {
    if (self instanceof Integer) {
      return IntegerHelper.truncate((int) self);
    } else {
      // Must be double
      return DoubleHelper.truncate((double) self);
    }
  }

  // TODO(springerm): roundToDouble
  // TODO(springerm): floorToDouble
  // TODO(springerm:) ceilToDouble
  // TODO(springerm): truncateToDouble
  // TODO(springerm): clamp

  public static Integer toInt(Number self) {
    if (self instanceof Integer) {
      return IntegerHelper.toInt((int) self);
    } else {
      // Must be double
      return DoubleHelper.toInt((double) self);
    }
  }

  public static Double toDouble(Number self) {
    if (self instanceof Integer) {
      return IntegerHelper.toDouble((int) self);
    } else {
      // Must be double
      return DoubleHelper.toDouble((double) self);
    }
  }

  // TODO(springerm): toStringAsFixed
  // TODO(springerm): toStringAsExponential
  // TODO(springerm): toStringAsPrecision

  public static String toString(Number self) {
    if (self instanceof Integer) {
      return IntegerHelper.toString((int) self);
    } else {
      // Must be double
      return DoubleHelper.toString((double) self);
    }
  }

}
