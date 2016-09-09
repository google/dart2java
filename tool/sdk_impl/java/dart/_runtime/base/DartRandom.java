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
