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

import java.util.SortedMap;
import java.util.TreeMap;

/**
 * A representation of a Dart function signature.
 * <p>
 * An instance of {@code FunctionTypeInfo} stores the parameter types and return type of a Dart
 * function type.
 */
public final class FunctionTypeInfo {
  // For debug purposes
  public final String name;

  public final TypeVariableExpr[] typeVariables;

  /**
   * Creates a {@code FunctionTypeInfo} for a function signature with no named parameters.
   *
   * @param returnType return type of function (TODO(springerm))
   * @param requiredParamCount number of required parameters
   * @param positionalParams types of positional parameters (must have at least {@code
   * requiredParamCount} elements).
   */
  public FunctionTypeInfo(String name, String[] typeVariableNames) {
    this.name = name;
    this.typeVariables = new TypeVariableExpr[typeVariableNames.length];
    for (int i = 0; i < typeVariables.length; i++) {
      this.typeVariables[i] = new TypeVariableExpr(this.name, typeVariableNames[i]);
    }

    // Set final variables (removed some parts of this class)
    this.returnType = null;
    this.requiredParamCount = 0;
    this.positionalParams = null;
    this.namedParams = null;
  }


  // Parts of Andrew's original implementation (not in use as of now)

  private static final String[] EMPTY_STRING_ARRAY = new String[0];
  private static final TypeVariableExpr[] EMPTY_TVE_ARRAY = new TypeVariableExpr[0];

  /**
   * The return type of the function.
   */
  public final TypeExpr returnType;

  /**
   * The number of required arguments.
   */
  public final int requiredParamCount;

  /**
   * The types of the positional parameters.
   * <p>
   * Note that a positional parameter is optional if its (zero-based) index is greater than or equal
   * to {@link #requiredParamCount}.
   */
  public final TypeExpr[] positionalParams;

  /**
   * The named parameters to this function.
   * <p>
   * The values in the {@code SortedMap} will be ordered according to the natural order on {@code
   * String}s, i.e. alphabetically. This is part of the contract of {@code FunctionTypeInfo}.
   */
  public final SortedMap<String, TypeExpr> namedParams;

  private static SortedMap<String, TypeExpr> makeSortedMap(String[] names, TypeExpr[] types) {
    assert (names.length == types.length);
    SortedMap<String, TypeExpr> result = new TreeMap<>();
    for (int i = 0; i < names.length; i++) {
      result.put(names[i], types[i]);
    }
    return result;
  }
}
