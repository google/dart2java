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
      } else if (other instanceof FunctionType) {
        result = Boolean.valueOf(isSubtypeOfFunctionType((FunctionType) other));
      } else if (other == TopType.INSTANCE || other == VoidType.INSTANCE) {
        result = Boolean.TRUE;
      } else {
        throw new IllegalArgumentException("Unrecognized Type of class " + other.getClass());
      }
      isSubtypeOfCache.put(other, result);
    }
    return result.booleanValue();
  }

  protected abstract boolean isSubtypeOfInterfaceType(InterfaceType other);

  protected abstract boolean isSubtypeOfFunctionType(FunctionType other);
}
