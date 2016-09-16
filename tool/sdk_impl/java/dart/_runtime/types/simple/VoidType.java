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

/**
 * A singleton class representing the (uninhabited) void type; used for the return type of (some)
 * functions.
 */
public final class VoidType extends Type {
  private VoidType() {}

  /**
   * A singleton instance of the top type.
   * <p>
   * Client code should access this instance through {@link #EXPR}, for consistency with {@code
   * InterfaceTypeExpr} and {@code FunctionTypeExpr}. Code in this package may access the instance
   * directly.
   */
  static final VoidType INSTANCE = new VoidType();

  /**
   * A type expression representing the top type.
   */
  public static final TypeExpr EXPR = new TypeExpr("[void]") {
    @Override
    Type evaluateUncached(TypeEnvironment env) {
      return INSTANCE;
    }
  };

  @Override
  protected boolean isSubtypeOfFunctionType(FunctionType other) {
    return false;
  }

  @Override
  protected boolean isSubtypeOfInterfaceType(InterfaceType other) {
    return false;
  }

  @Override
  public String toString() {
    return "void";
  }
}
