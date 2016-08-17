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
  Type evaluateUncached(TypeEnvironment env) {
    List<Type> actualTypeParams = new ArrayList<>(typeArgs.length);
    for (TypeExpr e : typeArgs) {
      actualTypeParams.add(env.evaluate(e));
    }
    InterfaceType result = info.instantiationCache.get(actualTypeParams);
    if (result == null) {
      // We have to be a bit careful here, because of possible circular references in the supertypes
      // of result. For example, consider the class declaration `num implements Comparable<num>`. If
      // we waited until the end of this method to store `num` in the cache, we would reach an
      // infinite cycle where (1) we see the declaration `Comparable<num>`, (2) we evaluate the type
      // parameter `<num>`, which in turn evaluates the interface declaration `Comparable<num>`,
      // bringing us back to (1). To cut short this infinite regress, we store `num` in the cache
      // before evaluating the supertype declaration, so instead of recursing, step (2) will just
      // return the cached instance of `num`. This instance will be incomplete (its superclass,
      // mixin, and interfaces fields will all be null), but it will already be allocated and it
      // will be completed once we get back to the first call to evaluateUncached.

      Type[] actualTypeArgsArray = actualTypeParams.toArray(new Type[typeArgs.length]);
      // Create a temporary type environment that binds the type variables for this new type
      // (but not the type variables of its superclasses or mixins).
      env = env.extend(info.typeVariables, actualTypeArgsArray);
      result = new InterfaceType(info, actualTypeArgsArray);
      info.instantiationCache.put(actualTypeParams, result);
      result.supertype = info.superclass != null ? env.evaluate(info.superclass) : null;
      result.mixin = info.mixin != null ? env.evaluate(info.mixin) : null;
      result.interfaces = new InterfaceType[info.interfaces.length];
      for (int i = 0; i < info.interfaces.length; i++) {
        result.interfaces[i] = env.evaluate(info.interfaces[i]);
      }
      result.finishInitialization();
    }
    return result;
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
