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
  final Map<List<Type>, InterfaceType> instantiationCache = new HashMap<>();

  private static final String[] EMTPY_STRING_ARRAY = new String[0];
  private static final InterfaceTypeExpr[] EMPTY_ITE_ARRAY = new InterfaceTypeExpr[0];

  /**
   * Creates an {@code InterfaceTypeInfo} for a class without type variables.
   *
   * The {@code fullyQualifiedJavaName} is used to create a unique key; it is assumed that there
   * will never be two distinct Dart types that are implemented by Java classes with identical
   * fully-qualified names. (This assumption can be violated in the presence of alternate class
   * loaders!)
   *
   * @param fullyQualifiedJavaName fully qualified Java name of the Java class implementing this
   * type!).
   */
  public InterfaceTypeInfo(String fullyQualifiedJavaName) {
    this(EMTPY_STRING_ARRAY, fullyQualifiedJavaName);
  }

  /**
   * Creates an {@code InterfaceTypeInfo} for a class with type variables.
   *
   * @param typeVariableNames name of type parameters
   * @param fullyQualifiedJavaName fully qualified Java name of the Java class implementing this
   * type!).
   */
  public InterfaceTypeInfo(String[] typeVariableNames, String fullyQualifiedJavaName) {
    this.fullName = fullyQualifiedJavaName;
    this.typeVariables = new TypeVariableExpr[typeVariableNames.length];
    for (int i = 0; i < typeVariables.length; i++) {
      this.typeVariables[i] = new TypeVariableExpr(this.fullName, typeVariableNames[i]);
    }
  }
}
