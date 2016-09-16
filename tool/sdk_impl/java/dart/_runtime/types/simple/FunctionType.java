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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Representation of a concrete Dart function type.
 * <p>
 * Types represented by a {@code FunctionType} instance cannot have any free type parameters (this
 * class cannot represent universal functions).
 */
public class FunctionType extends Type {
  /**
   * The function's return type.
   */
  final Type returnType;

  /**
   * The number of required parameters.
   */
  final int requiredParamCount;

  /**
   * The types of the positional parameters.
   * <p>
   * Note that any parameters whose (zero-based) index is greater than or equal to
   * {@link #requiredParamCount} are optional.
   */
  final Type[] positionalParams;

  /**
   * The names and types of (optional) named parameters.
   */
  final Map<String, Type> namedParams;

  private static final InterfaceType functionType =
      TypeEnvironment.ROOT.evaluate(new InterfaceTypeExpr(dart.core.Function.dart2java$typeInfo));

  private FunctionType(Type returnType, int requiredParamCount, Type[] positionalParams,
      Map<String, Type> namedParams) {
    this.returnType = returnType;
    this.requiredParamCount = requiredParamCount;
    this.positionalParams = positionalParams;
    this.namedParams = namedParams;
  }

  private static Map<List<?>, FunctionType> instantiationCache = new HashMap<>();

  /**
   * Return the function type with the given signature.
   * <p>
   * Function type representations are canonicalized, so calls to {@code instantiate} with equal
   * arguments with return identical {@code FunctionType} instances. This method should only be
   * called by {@link FunctionTypeExpr#evaluateUncached}.
   */
  static FunctionType instantiate(Type returnType, int requiredParamCount, Type[] positionalParams,
      Map<String, Type> namedParams) {
    List<?> key = Arrays.asList(returnType, requiredParamCount, positionalParams, namedParams);
    FunctionType result = instantiationCache.get(key);
    if (result == null) {
      result = new FunctionType(returnType, requiredParamCount, positionalParams, namedParams);
      instantiationCache.put(key, result);
    }
    return result;
  }

  @Override
  protected boolean isSubtypeOfInterfaceType(InterfaceType other) {
    return functionType.isSubtypeOfInterfaceType(other);
  }

  @Override
  protected boolean isSubtypeOfFunctionType(FunctionType other) {
    if (!this.returnType.isSubtypeOf(other.returnType))
      return false;
    if (this.requiredParamCount > other.requiredParamCount)
      return false;
    if (this.positionalParams.length < other.positionalParams.length)
      return false;
    for (int i = 0; i < other.positionalParams.length; i++) {
      if (!other.positionalParams[i].isSubtypeOf(this.positionalParams[i]))
        return false;
    }
    for (String name : other.namedParams.keySet()) {
      Type type = this.namedParams.get(name);
      if (type == null || !other.namedParams.get(name).isSubtypeOf(type))
        return false;
    }
    return true;
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    int i;
    sb.append('(');
    for (i = 0; i < requiredParamCount; i++) {
      if (i > 0) {
        sb.append(", ");
      }
      sb.append(positionalParams[i]);
    }
    if (positionalParams.length > requiredParamCount) {
      if (requiredParamCount > 0) {
        sb.append(", ");
      }
      sb.append('[');
      for (i = requiredParamCount; i < positionalParams.length; i++) {
        if (i > requiredParamCount) {
          sb.append(", ");
        }
        sb.append(positionalParams[i]);
      }
      sb.append(']');
    }
    if (!namedParams.isEmpty()) {
      if (positionalParams.length > 0) {
        sb.append(", ");
      }
      sb.append('{');
      List<String> names = new ArrayList<>(namedParams.keySet());
      names.sort(null);
      boolean first = true;
      for (String s : names) {
        if (!first) {
          sb.append(", ");
        }
        sb.append(s);
        sb.append(": ");
        sb.append(namedParams.get(s));
        first = false;
      }
      sb.append('}');
    }
    sb.append(") -> ");
    sb.append(returnType);
    return sb.toString();
  }
}
