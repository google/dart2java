package dart._runtime.types.simple;

import java.util.ArrayList;
import java.util.List;

/**
 * A {@link TypeExpr} that evaluates to a Dart interface type.
 */
public final class InterfaceTypeExpr extends TypeExpr {
  private static final TypeExpr[] EMPTY_TYPE_EXPR_ARRAY = new TypeExpr[0];
  private final InterfaceTypeInfo info;
  private final TypeExpr[] typeArgs;

  /**
   * Creates a {@code InterfaceTypeExpr} with no type parameters.
   *
   * @param info base interface type
   */
  public InterfaceTypeExpr(InterfaceTypeInfo info) {
    this(info, EMPTY_TYPE_EXPR_ARRAY);
  }

  /**
   * Creates a {@code InterfaceTypeExpr} with type parameters.
   *
   * @param info base interface type
   * @param typeArgs type arguments
   */
  public InterfaceTypeExpr(InterfaceTypeInfo info, TypeExpr[] typeArgs) {
    super(keyFor(info, typeArgs));

    if (typeArgs.length != info.typeVariables.length) {
      throw new IllegalArgumentException("Wrong number of type arguments in InterfaceTypeExpr");
    }
    this.info = info;
    this.typeArgs = typeArgs;
  }

  @Override
  Type evaluate(TypeEnvironment env) {
    List<Type> actualTypeParams = new ArrayList<>(typeArgs.length);
    for (TypeExpr e : typeArgs) {
      actualTypeParams.add(env.evaluate(e));
    }
    return info.instantiate(actualTypeParams);
  }

  private static String keyFor(InterfaceTypeInfo info, TypeExpr[] typeArgs) {
    StringBuilder sb = new StringBuilder();
    sb.append(info.fullName);
    sb.append('<');
    for (TypeExpr e : typeArgs) {
      sb.append(e.key);
      sb.append(',');
    }
    sb.append('>');
    return sb.toString();
  }
}
