package dart._runtime.helpers;

public class NumberHelper {

  // --- Methods defined in Object ---

  // TODO(springerm): noSuchMethod
  // TODO(springerm): runtimeType


  // --- Methods defined in Comparable ---


  // --- Methods defined in num ---
  
  public static Boolean operatorEqual(Number self, Object other) {
    return self == other;
  }

  public static Integer getHashCode(Number self) {
    return self.hashCode();
  }

  public static Integer compareTo(Number self, Integer other) {
    if (self instanceof Integer) {
      return IntegerHelper.compareTo((Integer) self, other);
    } else {
      // Must be double
      return DoubleHelper.compareTo((Double) self, other);
    }
  }

  public static Integer compareTo(Number self, Double other) {
    if (self instanceof Integer) {
      return IntegerHelper.compareTo((Integer) self, other);
    } else {
      // Must be double
      return DoubleHelper.compareTo((Double) self, other);
    }
  }

  public static Integer compareTo(Number self, Number other) {
    if (self instanceof Integer) {
      return IntegerHelper.compareTo((Integer) self, other);
    } else {
      // Must be double
      return DoubleHelper.compareTo((Double) self, other);
    }
  }

  public static Number operatorPlus(Number self, Integer other) {
    if (self instanceof Integer) {
      return IntegerHelper.operatorPlus((Integer) self, other);
    } else {
      // Must be double
      return DoubleHelper.operatorPlus((Double) self, other);
    }
  }

  public static Number operatorPlus(Number self, Double other) {
    if (self instanceof Integer) {
      return IntegerHelper.operatorPlus((Integer) self, other);
    } else {
      // Must be double
      return DoubleHelper.operatorPlus((Double) self, other);
    }
  }

  public static Number operatorPlus(Number self, Number other) {
    if (self instanceof Integer) {
      return IntegerHelper.operatorPlus((Integer) self, other);
    } else {
      // Must be double
      return DoubleHelper.operatorPlus((Double) self, other);
    }
  }

  public static Number operatorMinus(Number self, Integer other) {
    if (self instanceof Integer) {
      return IntegerHelper.operatorMinus((Integer) self, other);
    } else {
      // Must be double
      return DoubleHelper.operatorMinus((Double) self, other);
    }
  }

  public static Number operatorMinus(Number self, Double other) {
    if (self instanceof Integer) {
      return IntegerHelper.operatorMinus((Integer) self, other);
    } else {
      // Must be double
      return DoubleHelper.operatorMinus((Double) self, other);
    }
  }

  public static Number operatorMinus(Number self, Number other) {
    if (self instanceof Integer) {
      return IntegerHelper.operatorMinus((Integer) self, other);
    } else {
      // Must be double
      return DoubleHelper.operatorMinus((Double) self, other);
    }
  }

  public static Number operatorStar(Number self, Integer other) {
    if (self instanceof Integer) {
      return IntegerHelper.operatorStar((Integer) self, other);
    } else {
      // Must be double
      return DoubleHelper.operatorStar((Double) self, other);
    }
  }

  public static Number operatorStar(Number self, Double other) {
    if (self instanceof Integer) {
      return IntegerHelper.operatorStar((Integer) self, other);
    } else {
      // Must be double
      return DoubleHelper.operatorStar((Double) self, other);
    }
  }

  public static Number operatorStar(Number self, Number other) {
    if (self instanceof Integer) {
      return IntegerHelper.operatorStar((Integer) self, other);
    } else {
      // Must be double
      return DoubleHelper.operatorStar((Double) self, other);
    }
  }

  public static Number operatorModulus(Number self, Integer other) {
    if (self instanceof Integer) {
      return IntegerHelper.operatorModulus((Integer) self, other);
    } else {
      throw new RuntimeException("Modulo not implemented for double");
    }
  }

  // TODO(springerm): operatorModulus for Double
  // TODO(springerm): operatorModulus for Number

  public static Number operatorDivide(Number self, Integer other) {
    if (self instanceof Integer) {
      return IntegerHelper.operatorDivide((Integer) self, other);
    } else {
      // Must be double
      return DoubleHelper.operatorDivide((Double) self, other);
    }
  }

  public static Number operatorDivide(Number self, Double other) {
    if (self instanceof Integer) {
      return IntegerHelper.operatorDivide((Integer) self, other);
    } else {
      // Must be double
      return DoubleHelper.operatorDivide((Double) self, other);
    }
  }

  public static Number operatorDivide(Number self, Number other) {
    if (self instanceof Integer) {
      return IntegerHelper.operatorDivide((Integer) self, other);
    } else {
      // Must be double
      return DoubleHelper.operatorDivide((Double) self, other);
    }
  }

  public static Integer operatorTruncatedDivide(Number self, Integer other) {
    if (self instanceof Integer) {
      return IntegerHelper.operatorTruncatedDivide((Integer) self, other);
    } else {
      // Must be double
      return DoubleHelper.operatorTruncatedDivide((Double) self, other);
    }
  }

  public static Integer operatorTruncatedDivide(Number self, Double other) {
    if (self instanceof Integer) {
      return IntegerHelper.operatorTruncatedDivide((Integer) self, other);
    } else {
      // Must be double
      return DoubleHelper.operatorTruncatedDivide((Double) self, other);
    }
  }

  public static Integer operatorTruncatedDivide(Number self, Number other) {
    if (self instanceof Integer) {
      return IntegerHelper.operatorTruncatedDivide((Integer) self, other);
    } else {
      // Must be double
      return DoubleHelper.operatorTruncatedDivide((Double) self, other);
    }
  }

  public static Number operatorUnaryMinus(Number self) {
    if (self instanceof Integer) {
      return IntegerHelper.operatorUnaryMinus((Integer) self);
    } else {
      // Must be double
      return DoubleHelper.operatorUnaryMinus((Double) self);
    }
  }

  // TODO(springerm): remainder

  public static Boolean operatorLess(Number self, Integer other) {
    if (self instanceof Integer) {
      return IntegerHelper.operatorLess((Integer) self, other);
    } else {
      // Must be double
      return DoubleHelper.operatorLess((Double) self, other);
    }
  }

  public static Boolean operatorLess(Number self, Double other) {
    if (self instanceof Integer) {
      return IntegerHelper.operatorLess((Integer) self, other);
    } else {
      // Must be double
      return DoubleHelper.operatorLess((Double) self, other);
    }
  }

  public static Boolean operatorLess(Number self, Number other) {
    if (self instanceof Integer) {
      return IntegerHelper.operatorLess((Integer) self, other);
    } else {
      // Must be double
      return DoubleHelper.operatorLess((Double) self, other);
    }
  }

  public static Boolean operatorLessEqual(Number self, Integer other) {
    if (self instanceof Integer) {
      return IntegerHelper.operatorLessEqual((Integer) self, other);
    } else {
      // Must be double
      return DoubleHelper.operatorLessEqual((Double) self, other);
    }
  }

  public static Boolean operatorLessEqual(Number self, Double other) {
    if (self instanceof Integer) {
      return IntegerHelper.operatorLessEqual((Integer) self, other);
    } else {
      // Must be double
      return DoubleHelper.operatorLessEqual((Double) self, other);
    }
  }

  public static Boolean operatorLessEqual(Number self, Number other) {
    if (self instanceof Integer) {
      return IntegerHelper.operatorLessEqual((Integer) self, other);
    } else {
      // Must be double
      return DoubleHelper.operatorLessEqual((Double) self, other);
    }
  }

  public static Boolean operatorGreater(Number self, Integer other) {
    if (self instanceof Integer) {
      return IntegerHelper.operatorGreater((Integer) self, other);
    } else {
      // Must be double
      return DoubleHelper.operatorGreater((Double) self, other);
    }
  }

  public static Boolean operatorGreater(Number self, Double other) {
    if (self instanceof Integer) {
      return IntegerHelper.operatorGreater((Integer) self, other);
    } else {
      // Must be double
      return DoubleHelper.operatorGreater((Double) self, other);
    }
  }

  public static Boolean operatorGreater(Number self, Number other) {
    if (self instanceof Integer) {
      return IntegerHelper.operatorGreater((Integer) self, other);
    } else {
      // Must be double
      return DoubleHelper.operatorGreater((Double) self, other);
    }
  }

  public static Boolean operatorGreaterEqual(Number self, Integer other) {
    if (self instanceof Integer) {
      return IntegerHelper.operatorGreaterEqual((Integer) self, other);
    } else {
      // Must be double
      return DoubleHelper.operatorGreaterEqual((Double) self, other);
    }
  }

  public static Boolean operatorGreaterEqual(Number self, Double other) {
    if (self instanceof Integer) {
      return IntegerHelper.operatorGreaterEqual((Integer) self, other);
    } else {
      // Must be double
      return DoubleHelper.operatorGreaterEqual((Double) self, other);
    }
  }

  public static Boolean operatorGreaterEqual(Number self, Number other) {
    if (self instanceof Integer) {
      return IntegerHelper.operatorGreaterEqual((Integer) self, other);
    } else {
      // Must be double
      return DoubleHelper.operatorGreaterEqual((Double) self, other);
    }
  }

  public static Boolean isNaN(Number self) {
    if (self instanceof Integer) {
      return IntegerHelper.isNaN((Integer) self);
    } else {
      // Must be double
      return DoubleHelper.isNaN((Double) self);
    }
  }

  public static Boolean isNegative(Number self) {
    if (self instanceof Integer) {
      return IntegerHelper.isNegative((Integer) self);
    } else {
      // Must be double
      return DoubleHelper.isNegative((Double) self);
    }
  }

  public static Boolean isInfinite(Number self) {
    if (self instanceof Integer) {
      return IntegerHelper.isInfinite((Integer) self);
    } else {
      // Must be double
      return DoubleHelper.isInfinite((Double) self);
    }
  }

  public static Boolean isFinite(Number self) {
    if (self instanceof Integer) {
      return IntegerHelper.isFinite((Integer) self);
    } else {
      // Must be double
      return DoubleHelper.isFinite((Double) self);
    }
  }

  public static Number abs(Number self) {
    if (self instanceof Integer) {
      return IntegerHelper.abs((Integer) self);
    } else {
      // Must be double
      return DoubleHelper.abs((Double) self);
    }
  }

  public static Number getSign(Number self) {
    if (self instanceof Integer) {
      return IntegerHelper.getSign((Integer) self);
    } else {
      // Must be double
      return DoubleHelper.getSign((Double) self);
    }
  }

  public static Integer round(Number self) {
    if (self instanceof Integer) {
      return IntegerHelper.round((Integer) self);
    } else {
      // Must be double
      return DoubleHelper.round((Double) self);
    }
  }

  public static Integer floor(Number self) {
    if (self instanceof Integer) {
      return IntegerHelper.floor((Integer) self);
    } else {
      // Must be double
      return DoubleHelper.floor((Double) self);
    }
  }

  public static Integer ceil(Number self) {
    if (self instanceof Integer) {
      return IntegerHelper.ceil((Integer) self);
    } else {
      // Must be double
      return DoubleHelper.ceil((Double) self);
    }
  }

  public static Integer truncate(Number self) {
    if (self instanceof Integer) {
      return IntegerHelper.truncate((Integer) self);
    } else {
      // Must be double
      return DoubleHelper.truncate((Double) self);
    }
  }

  // TODO(springerm): roundToDouble
  // TODO(springerm): floorToDouble
  // TODO(springerm:) ceilToDouble
  // TODO(springerm): truncateToDouble
  // TODO(springerm): clamp

  public static Integer toInt(Number self) {
    if (self instanceof Integer) {
      return IntegerHelper.toInt((Integer) self);
    } else {
      // Must be double
      return DoubleHelper.toInt((Double) self);
    }
  }

  public static Double toDouble(Number self) {
    if (self instanceof Integer) {
      return IntegerHelper.toDouble((Integer) self);
    } else {
      // Must be double
      return DoubleHelper.toDouble((Double) self);
    }
  }

  // TODO(springerm): toStringAsFixed
  // TODO(springerm): toStringAsExponential
  // TODO(springerm): toStringAsPrecision

  public static String toString(Number self) {
    if (self instanceof Integer) {
      return IntegerHelper.toString((Integer) self);
    } else {
      // Must be double
      return DoubleHelper.toString((Double) self);
    }
  }

}