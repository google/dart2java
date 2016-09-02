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
import java.util.List;
import java.util.Map;

/**
 * A representation of a Dart {@code class} declaration.
 * <p>
 * An instance of {@link InterfaceTypeInfo} stores the direct supertypes (superclass, interfaces,
 * and maybe mixin class) of a Dart class, as well as the type variables that the Dart class
 * declares.
 */
public final class InterfaceTypeInfo {
  public InterfaceTypeExpr superclass;
  public InterfaceTypeExpr mixin;
  public InterfaceTypeExpr[] interfaces = EMPTY_ITE_ARRAY;
  public final TypeVariableExpr[] typeVariables;
  public final String fullName;
  public final Class<?> javaClass;
  public final Class<?> javaInterface;

  final Map<List<Type>, InterfaceType> instantiationCache = new HashMap<>();

  private static final String[] EMTPY_STRING_ARRAY = new String[0];
  private static final InterfaceTypeExpr[] EMPTY_ITE_ARRAY = new InterfaceTypeExpr[0];

  /**
   * Creates an {@code InterfaceTypeInfo} for a class without type variables.
   * <p>
   * A single Dart class induces two Java types: a class (used for instances of the Dart type) and
   * an interface (used when other Dart classes implement the Dart type). Both the class and
   * interface should be passed in to this constructor. In some cases involving interop, one of the
   * two arguments {@code javaClass} and {@code javaInterface} can be null, but they must never both
   * be null.
   *
   * @param javaClass Java class used to implement this Dart class
   * @param javaInterface Java interface used to implement this Dart class
   */
  public InterfaceTypeInfo(Class<?> javaClass, Class<?> javaInterface) {
    this(EMTPY_STRING_ARRAY, javaClass, javaInterface);
  }

  /**
   * Creates an {@code InterfaceTypeInfo} for a class with type variables.
   * <p>
   * A single Dart class induces two Java types: a class (used for instances of the Dart type) and
   * an interface (used when other Dart classes implement the Dart type). Both the class and
   * interface should be passed in to this constructor. In some cases involving interop, one of the
   * two arguments {@code javaClass} and {@code javaInterface} can be null, but they must never both
   * be null.
   *
   * @param typeVariableNames names of type parameters
   * @param javaClass Java class used to implement this Dart class
   * @param javaInterface Java interface used to implement this Dart class
   */
  public InterfaceTypeInfo(String[] typeVariableNames, Class<?> javaClass, Class<?> javaInterface) {
    if (javaClass == null && javaInterface == null) {
      throw new IllegalArgumentException(
          "Cannot create InterfaceTypeInfo when both javaClass and javaInterface are null");
    }
    this.javaClass = javaClass;
    this.javaInterface = javaInterface;
    this.fullName = (javaClass == null ? javaInterface : javaClass).getName();
    this.typeVariables = new TypeVariableExpr[typeVariableNames.length];
    for (int i = 0; i < typeVariables.length; i++) {
      this.typeVariables[i] = new TypeVariableExpr(this.fullName, typeVariableNames[i]);
    }
  }
}
