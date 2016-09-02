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
