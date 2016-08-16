package dart._runtime.types.simple;

import java.util.HashMap;
import java.util.Map;

/**
 * A representation of a Dart type.
 * <p>
 * This is the base class for all Dart type representations. It supports the basic type operations,
 * such as {@code isSubtypeOf}.
 */
public abstract class Type {
  /**
   * Constructs a {@link Type} instance.
   * <p>
   * This constructor is package-private; all subtypes of {@link Type} must belong to the same
   * package.
   */
  Type() {}

  private final Map<Type, Boolean> isSubtypeOfCache = new HashMap<>();

  /**
   * Tests whether this type is a subtype of {@code other}.
   *
   * @param other other type
   * @return true if this &lt;: other
   */
  public final boolean isSubtypeOf(Type other) {
    Boolean result = isSubtypeOfCache.get(other);
    if (result == null) {
      if (other instanceof InterfaceType) {
        result = Boolean.valueOf(isSubtypeOfInterfaceType((InterfaceType) other));
      } else if (other instanceof TopType) {
        result = Boolean.TRUE;
      } else {
        throw new IllegalArgumentException("Unrecognized Type of class " + other.getClass());
      }
      isSubtypeOfCache.put(other, result);
    }
    return result.booleanValue();
  }

  protected abstract boolean isSubtypeOfInterfaceType(InterfaceType other);
}
