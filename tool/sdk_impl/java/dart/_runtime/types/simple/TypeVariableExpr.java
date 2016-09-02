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
 * A type variable, that can be used in building {@link TypeExpr}s.
 * <p>
 * Instances of this class are considered equal only if they are identical. Both
 * {@link InterfaceTypeInfo} and {@link FunctionTypeInfo} expose a list of type variables that can
 * be used when forming @{code TypeExpr}s inside of their type scopes. Client code cannot directly
 * create a {@code TypeVariableExpr}, and can only access type variables through a
 * {@code InterfaceTypeInfo} or {@code FunctionTypeInfo}.
 */
public class TypeVariableExpr extends TypeExpr {

  /**
   * Creates a {@code TypeVariableExpr}.
   *
   * Every type variable is declared by some other entity (either a generic class or generic
   * function). {@code declarerName} should be a name that uniquely identifies the entity that
   * declares this variable, such as the fully-qualified name of the Java implementation of a
   * generic class. The local name {@code name} must be unique among the type variables declared in
   * the same class/function (which it should be anyway according to Dart rules).
   *
   * @param declarerName name of the declaring entity
   * @param name local name of the type variable
   */
  TypeVariableExpr(String declarerName, String name) {
    super(declarerName + "#" + name);
  }

  @Override
  Type evaluateUncached(TypeEnvironment env) {
    return env.resolve(this);
  }
}
