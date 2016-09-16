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

package dart._runtime.base;

import dart._runtime.helpers.ConstructorHelper.EmptyConstructorMarker;
import dart._runtime.types.simple.InterfaceType;
import dart._runtime.types.simple.InterfaceTypeExpr;
import dart._runtime.types.simple.InterfaceTypeInfo;
import dart._runtime.types.simple.TypeEnvironment;
import java.security.SecureRandom;

public class DartRandom extends dart.math.Random {
  @SuppressWarnings("hiding")
  public static final InterfaceTypeInfo dart2java$typeInfo =
      new InterfaceTypeInfo(DartRandom.class, null);

  private static final InterfaceType thisType =
      TypeEnvironment.ROOT.evaluate(new InterfaceTypeExpr(dart2java$typeInfo));

  private final java.util.Random rnd;

  /**
   * Creates a new, non-cryptographically-secure RNG.
   *
   * @param seed seed value to use
   */
  public DartRandom(int seed) {
    super((EmptyConstructorMarker) null, thisType);
    rnd = new java.util.Random(seed);
  }

  /**
   * Creates a new cryptographically-secure RNG.
   */
  public DartRandom() {
    super((EmptyConstructorMarker) null, thisType);
    rnd = new SecureRandom();
  }

  @Override
  public int nextInt(int max) {
    return rnd.nextInt(max);
  }

  @Override
  public double nextDouble() {
    return rnd.nextDouble();
  }

  @Override
  public boolean nextBool() {
    return rnd.nextBoolean();
  }
}
