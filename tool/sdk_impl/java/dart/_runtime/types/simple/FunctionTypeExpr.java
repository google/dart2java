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
 * A {@link TypeExpr} that evaluates to a Dart function type.
 * <p>
 * Currently, this class is just a wrapper around a {@code FunctionTypeInfo}. This class is included
 * as an analogue to {@code InterfaceTypeExpr}. {@code FunctionTypeExpr} will be more useful in the
 * future if support for universal functions is added.
 */
public final class FunctionTypeExpr extends TypeExpr {
  final FunctionTypeInfo info;

  /**
   * Creates a new {@code FunctionTypeExpr} for the given {@code FunctionTypeInfo}.
   */
  public FunctionTypeExpr(FunctionTypeInfo info) {
    super(makeKey(info));
    this.info = info;
  }

  @Override
  Type evaluateUncached(TypeEnvironment env) {
    Type returnType = env.evaluate(info.returnType);
    Type[] positionalParams = new Type[info.positionalParams.length];
    for (int i = 0; i < info.positionalParams.length; i++) {
      positionalParams[i] = env.evaluate(info.positionalParams[i]);
    }
    Map<String, Type> namedParams = info.namedParams.isEmpty() ? Collections.emptyMap()
        : new HashMap<>(info.namedParams.size());
    for (Map.Entry<String, TypeExpr> e : info.namedParams.entrySet()) {
      namedParams.put(e.getKey(), env.evaluate((e.getValue())));
    }

    return FunctionType.instantiate(returnType, info.requiredParamCount, positionalParams,
        namedParams);
  }

  private static String makeKey(FunctionTypeInfo info) {
    StringBuilder sb = new StringBuilder();
    sb.append('(');
    sb.append(info.requiredParamCount);
    sb.append(';');
    for (int i = 0; i < info.positionalParams.length; i++) {
      if (i > 0) {
        sb.append(',');
      }
      sb.append(info.positionalParams[i].key);
    }
    sb.append('{');
    boolean first = true;
    for (Map.Entry<String, TypeExpr> e : info.namedParams.entrySet()) {
      if (!first) {
        sb.append(',');
      }
      first = false;
      sb.append(e.getKey());
      sb.append(':');
      sb.append(e.getValue().key);
    }
    sb.append("})->");
    sb.append(info.returnType.key);
    return sb.toString();
  }
}
