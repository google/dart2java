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
