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

  TypeVariableExpr(String enclosingLibrary, String enclosingClass, String name) {
    super(enclosingLibrary + "#" + enclosingClass + "#" + name);
  }

  @Override
  Type evaluateUncached(TypeEnvironment env) {
    return env.resolve(this);
  }
}
