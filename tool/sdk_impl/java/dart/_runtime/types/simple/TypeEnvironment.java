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

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * A type scope that maps zero or more {@link TypeVariableExpr}s to {@link Type}s.
 * <p>
 * A {@code TypeEnvironment} is basically a {@code Map<TypeVariableExpr, Type>}, except with a
 * simpler interface. We use this interface rather than having generic classes and functions return
 * a {@code Map} so that they can delegate to other {@code TypeEnvironment}s.
 */
public final class TypeEnvironment {
  /**
   * The root {@code TypeEnvironment} cannot resolve any type variables.
   */
  public static final TypeEnvironment ROOT = new TypeEnvironment();

  private final TypeEnvironment parent;
  private final Map<TypeVariableExpr, Type> values;
  final Map<String, Type> cache = new HashMap<>();

  private TypeEnvironment() {
    this.parent = null;
    this.values = Collections.emptyMap();
  }

  private TypeEnvironment(TypeEnvironment parent, Map<TypeVariableExpr, Type> values) {
    this.parent = parent;
    this.values = Collections.unmodifiableMap(values);
  }

  /**
   * Creates a new {@code TypeEnvironment} that binds the listed variables to the given values.
   *
   * @param typeVariables variables to bind
   * @param actualTypeParams values for the variables (in the same order)
   * @return a type environment with the given bindings
   */
  public TypeEnvironment extend(TypeVariableExpr[] typeVariables, Type[] actualTypeParams) {
    if (typeVariables.length != actualTypeParams.length) {
      throw new IllegalArgumentException("Mismatched number of variables and values");
    }
    if (typeVariables.length == 0) {
      return this;
    } else {
      Map<TypeVariableExpr, Type> map = new HashMap<>(typeVariables.length);
      for (int i = 0; i < typeVariables.length; i++) {
        map.put(typeVariables[i], actualTypeParams[i]);
      }
      return new TypeEnvironment(this, map);
    }
  }

  /**
   * Evaluates a {@link TypeExpr} in this {@code TypeEnvironment}.
   *
   * @param expr type expression to evaluate
   * @return value of the type expression
   */
  public Type evaluate(TypeExpr expr) {
    Type result = cache.get(expr.key);
    if (result == null) {
      result = expr.evaluateUncached(this);
      cache.put(expr.key, result);
    }
    return result;
  }

  /**
   * Evaluates a {@link InterfaceTypeExpr} in this {@code TypeEnvironment}.
   *
   * This is a convenience method that calls {@link #evaluate(TypeExpr)} and casts the result.
   *
   * @param expr type expression to evaluate
   * @return value of the type expression
   */
  public InterfaceType evaluate(InterfaceTypeExpr expr) {
    return (InterfaceType) evaluate((TypeExpr) expr);
  }

  /**
   * Resolves a {@link TypeVariableExpr} in this {@code TypeEnvironment}; throws on failure.
   * <p>
   * Clients of a {@link TypeEnvironment} should use this method instead of {@link #tryResolve}.
   * This method verifies that the result is not {@code null}, whereas {@link #tryResolve} will just
   * return null if the value is not resolved.
   */
  Type resolve(TypeVariableExpr var) {
    Type result = tryResolve(var);
    if (result == null) {
      throw new IllegalArgumentException("No type variable " + var.key + " in scope");
    }
    return result;
  }

  /**
   * Attempts to resolve a {@link TypeVariableExpr} in this {@link TypeEnvironment}; returns null on
   * failure.
   * <p>
   * Generally, clients of a {@link TypeEnvironment} should call {@link #resolve} instead. Classes
   * that implement {@link TypeEnvironment} should implement {@link #tryResolve}, and use the
   * default implementation of {@link #resolve}.
   */
  Type tryResolve(TypeVariableExpr var) {
    Type result = values.get(var);
    if (result == null && parent != null) {
      result = parent.tryResolve(var);
    }
    return result;
  }
}
