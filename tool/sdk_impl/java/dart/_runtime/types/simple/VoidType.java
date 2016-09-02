package dart._runtime.types.simple;

/**
 * A singleton class representing the (uninhabited) void type; used for the return type of (some)
 * functions.
 */
public final class VoidType extends Type {
  private VoidType() {}

  /**
   * A singleton instance of the top type.
   * <p>
   * Client code should access this instance through {@link #EXPR}, for consistency with {@code
   * InterfaceTypeExpr} and {@code FunctionTypeExpr}. Code in this package may access the instance
   * directly.
   */
  static final VoidType INSTANCE = new VoidType();

  /**
   * A type expression representing the top type.
   */
  public static final TypeExpr EXPR = new TypeExpr("[void]") {
    @Override
    Type evaluateUncached(TypeEnvironment env) {
      return INSTANCE;
    }
  };

  @Override
  protected boolean isSubtypeOfFunctionType(FunctionType other) {
    return false;
  }

  @Override
  protected boolean isSubtypeOfInterfaceType(InterfaceType other) {
    return false;
  }
}
