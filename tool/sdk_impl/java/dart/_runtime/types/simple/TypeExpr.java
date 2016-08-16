package dart._runtime.types.simple;

/**
 * A type expression that can be evaluated to a {@link Type} relative to a {@link TypeEnvironment}.
 */
public abstract class TypeExpr {
  final String key;

  TypeExpr(String key) {
    this.key = key;
  }

  abstract Type evaluate(TypeEnvironment env);
}
