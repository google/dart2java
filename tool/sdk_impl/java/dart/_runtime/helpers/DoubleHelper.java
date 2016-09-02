// Copyright 2016, the Dart project authors.
// 
// Licensed under the Apache License, Version 2.0 (the "License");
// you may not use this file except in compliance with the License.
// You may obtain a copy of the License at
// 
//     http://www.apache.org/licenses/LICENSE-2.0
// 
// Unless required by applicable law or agreed to in writing, software
// distributed under the License is distributed on an "AS IS" BASIS,
// WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// See the License for the specific language governing permissions and
// limitations under the License.

package dart._runtime.helpers;

import dart._runtime.types.simple.InterfaceTypeExpr;
import dart._runtime.types.simple.InterfaceTypeInfo;

public class DoubleHelper {
  public static final InterfaceTypeInfo dart2java$typeInfo
      = new InterfaceTypeInfo(double.class, null);

  static {
    DoubleHelper.dart2java$typeInfo.superclass
        = new InterfaceTypeExpr(NumberHelper.dart2java$typeInfo);
  }

  // --- Methods defined in Object ---

  // TODO(springerm): noSuchMethod
  // TODO(springerm): runtimeType


  // --- Methods defined in Comparable ---


  // --- Methods defined in num ---
  
  public static boolean operatorEqual(Double self, Object other) {
    return self.equals(other);
  }

  public static int getHashCode(Double self) {
    return self.hashCode();
  }

  public static int compareTo(double self, int other) {
    if (self < other) {
      return -1;
    } else if (self > other) {
      return 1;
    } else {
      return 0;
    }
  }

  public static int compareTo(double self, double other) {
    if (self < other) {
      return -1;
    } else if (self > other) {
      return 1;
    } else {
      return 0;
    }
  }

  public static int compareTo(double self, Number other) {
    if (other instanceof Integer) {
      return compareTo(self, (int) other);
    } else {
      // Must be double
      return compareTo(self, (double) other);
    }
  }

  public static double operatorPlus(double self, int other) {
    return self + other;
  }

  public static double operatorPlus(double self, double other) {
    return self + other;
  }

  public static Number operatorPlus(double self, Number other) {
    if (other instanceof Integer) {
      return operatorPlus(self, (int) other);
    } else {
      // Must be double
      return operatorPlus(self, (double) other);
    }
  }

  public static double operatorMinus(double self, int other) {
    return self - other;
  }

  public static double operatorMinus(double self, double other) {
    return self - other;
  }

  public static Number operatorMinus(double self, Number other) {
    if (other instanceof Integer) {
      return operatorMinus(self, (int) other);
    } else {
      // Must be double
      return operatorMinus(self, (double) other);
    }
  }

  public static double operatorStar(double self, int other) {
    return self * other;
  }

  public static double operatorStar(double self, double other) {
    return self * other;
  }

  public static Number operatorStar(double self, Number other) {
    if (other instanceof Integer) {
      return operatorStar(self, (int) other);
    } else {
      // Must be double
      return operatorStar(self, (double) other);
    }
  }

  public static double operatorModulus(double self, int other) {
    return self % other;
  }

  public static double operatorModulus(double self, double other) {
    return self % other;
  }

  public static Number operatorModulus(double self, Number other) {
    if (other instanceof Integer) {
      return operatorModulus(self, (int) other);
    } else {
      // Must be double
      return operatorModulus(self, (double) other);
    }
  }

  public static double operatorDivide(double self, int other) {
    return self / other;
  } 

  public static double operatorDivide(double self, double other) {
    return self / other;
  } 

  public static double operatorDivide(double self, Number other) {
    if (other instanceof Integer) {
      return operatorDivide(self, (int) other);
    } else {
      // Must be double
      return operatorDivide(self, (double) other);
    }
  }

  public static int operatorTruncatedDivide(double self, int other) {
    return (int) (self / other);
  }

  public static int operatorTruncatedDivide(double self, double other) {
    return (int) (self / other);
  }

  public static int operatorTruncatedDivide(double self, Number other) {
    if (other instanceof Integer) {
      return operatorTruncatedDivide(self, (int) other);
    } else {
      // Must be double
      return operatorTruncatedDivide(self, (double) other);
    }
  }

  public static double operatorUnaryMinus(double self) {
    return -self;
  }

  // TODO(springerm): remainder

  public static boolean operatorLess(double self, int other) {
    return self < other;
  }

  public static boolean operatorLess(double self, double other) {
    return self < other;
  }

  public static boolean operatorLess(double self, Number other) {
    if (other instanceof Integer) {
      return operatorLess(self, (int) other);
    } else {
      // Must be double
      return operatorLess(self, (double) other);
    }
  }

  public static boolean operatorLessEqual(double self, int other) {
    return self <= other;
  }

  public static boolean operatorLessEqual(double self, double other) {
    return self <= other;
  }

  public static boolean operatorLessEqual(double self, Number other) {
    if (other instanceof Integer) {
      return operatorLessEqual(self, (int) other);
    } else {
      // Must be double
      return operatorLessEqual(self, (double) other);
    }
  }

  public static boolean operatorGreater(double self, int other) {
    return self > other;
  }

  public static boolean operatorGreater(double self, double other) {
    return self > other;
  }

  public static boolean operatorGreater(double self, Number other) {
    if (other instanceof Integer) {
      return operatorGreater(self, (int) other);
    } else {
      // Must be double
      return operatorGreater(self, (double) other);
    }
  }

  public static boolean operatorGreaterEqual(double self, int other) {
    return self >= other;
  }

  public static boolean operatorGreaterEqual(double self, double other) {
    return self >= other;
  }

  public static boolean operatorGreaterEqual(double self, Number other) {
    if (other instanceof Integer) {
      return operatorGreaterEqual(self, (int) other);
    } else {
      // Must be double
      return operatorGreaterEqual(self, (double) other);
    }
  }

  public static boolean isNaN(Double self) {
    return self.isNaN();
  }

  public static boolean isNegative(double self) {
    return Double.compare(self, 0.0) < 0;
  }

  public static boolean isInfinite(Double self) {
    return self.isInfinite();
  }

  public static boolean isFinite(Double self) {
    return !self.isInfinite();
  }

  public static double abs(double self) {
    return Math.abs(self);
  }

  public static double getSign(double self) {
    return Math.signum(self);
  }

  public static int round(double self) {
    return (int) Math.round(self);
  }

  public static int floor(double self) {
    return (int) Math.floor(self);
  }

  public static int ceil(double self) {
    return (int) Math.ceil(self);
  }

  public static int truncate(Double self) {
    return self.intValue();
  }

  // TODO(springerm): roundTodouble
  // TODO(springerm): floorTodouble
  // TODO(springerm:) ceilTodouble
  // TODO(springerm): truncateTodouble
  // TODO(springerm): clamp

  public static int toInt(Double self) {
    return self.intValue();
  }

  public static double toDouble(double self) {
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
    public static final double NAN = Double.NaN;
    public static final double INFINITY = Double.POSITIVE_INFINITY;
    public static final double NEGATIVE_INFINITY = Double.NEGATIVE_INFINITY;
    public static final double MIN_POSITIVE = Double.MIN_VALUE;
    public static final double MAX_FINITE = Double.MAX_VALUE;

    // TODO(springerm): parse
  }

}
