package dart._runtime.helpers;

public class IntegerHelper {

  // --- Methods defined in Object ---

  // TODO(springerm): noSuchMethod
  // TODO(springerm): runtimeType


  // --- Methods defined in Comparable ---


  // --- Methods defined in num ---
  
  public static Boolean operatorEqual(Integer self, Object other) {
    return self == other;
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

  public static Integer operatorPlus(Integer self, Integer other) {
    return self + other;
  }

  public static Double operatorPlus(Integer self, Double other) {
    return self + other;
  }

  public static Integer operatorMinus(Integer self, Integer other) {
    return self - other;
  }

  public static Double operatorMinus(Integer self, Double other) {
    return self - other;
  }

  public static Integer operatorStar(Integer self, Integer other) {
    return self * other;
  }

  public static Double operatorStar(Integer self, Double other) {
    return self * other;
  }

  public static Integer operatorModulus(Integer self, Integer other) {
    return self % other;
  }

  // TODO(springerm): operatorModulus for Double

  public static Double operatorDivide(Integer self, Integer other) {
    return ((double) self) / other;
  } 

  public static Double operatorDivide(Integer self, Double other) {
    return ((double) self) / other;
  } 

  public static Integer operatorTruncatedDivide(Integer self, Integer other) {
    return (Integer) (self / other);
  }

  public static Integer operatorTruncatedDivide(Integer self, Double other) {
    return (int) (self / other);
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

  public static Boolean operatorLessEqual(Integer self, Integer other) {
    return self <= other;
  }

  public static Boolean operatorLessEqual(Integer self, Double other) {
    return self <= other;
  }

  public static Boolean operatorGreater(Integer self, Integer other) {
    return self > other;
  }

  public static Boolean operatorGreater(Integer self, Double other) {
    return self > other;
  }

  public static Boolean operatorGreaterEqual(Integer self, Integer other) {
    return self >= other;
  }

  public static Boolean operatorGreaterEqual(Integer self, Double other) {
    return self >= other;
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


  // --- Other methods ---

  public static Boolean operatorNotEqual(Integer self, Integer other) {
    return self != other;
  }

}