// Copyright 2016, the Dart project authors.
//
// Licensed under the Apache License, Version 2.0 (the "License");
// you may not use this file except in compliance with the License.
// You may obtain a copy of the License at
//
// http://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing, software
// distributed under the License is distributed on an "AS IS" BASIS,
// WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// See the License for the specific language governing permissions and
// limitations under the License.

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

  public static dart.math.Random newRandom(int seed) {
    return new dart._runtime.base.DartRandom(seed);
  }

  public static dart.math.Random newSecureRandom() {
    return new dart._runtime.base.DartRandom();
  }

}
