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
   * @param libraryName name of enclosing library
   * @param className name of class
   */
  public InterfaceTypeInfo(String libraryName, String className) {
    this(EMTPY_STRING_ARRAY, libraryName, className);
  }

  /**
   * Creates an {@code InterfaceTypeInfo} for a class with type variables.
   *
   * @param typeVariableNames name of type parameters
   * @param libraryName name of enclosing library
   * @param className name of class
   */
  public InterfaceTypeInfo(String[] typeVariableNames, String libraryName, String className) {
    this.fullName = libraryName + "#" + className;
    this.typeVariables = new TypeVariableExpr[typeVariableNames.length];
    for (int i = 0; i < typeVariables.length; i++) {
      this.typeVariables[i] = new TypeVariableExpr(libraryName, className, typeVariableNames[i]);
    }
  }
}
