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

  private final Map<List<Type>, InterfaceType> instantiateCache = new HashMap<>();

  /**
   * Instantiates this raw class type with the given {@code actualTypeParams}.
   */
  InterfaceType instantiate(List<Type> actualTypeParams) {
    InterfaceType result = instantiateCache.get(actualTypeParams);
    if (result == null) {
      Type[] actualTypeParamsArray = actualTypeParams.toArray(new Type[actualTypeParams.size()]);
      TypeEnvironment classDeclEnv =
          TypeEnvironment.ROOT.extend(typeVariables, actualTypeParamsArray);
      InterfaceType superclass =
          this.superclass != null ? (InterfaceType) classDeclEnv.evaluate(this.superclass) : null;
      InterfaceType mixin =
          this.mixin != null ? (InterfaceType) classDeclEnv.evaluate(this.mixin) : null;
      InterfaceType[] interfaces = new InterfaceType[this.interfaces.length];
      for (int i = 0; i < interfaces.length; i++) {
        interfaces[i] = (InterfaceType) classDeclEnv.evaluate(this.interfaces[i]);
      }
      result = new InterfaceType(this, superclass, mixin, interfaces, actualTypeParamsArray);
      instantiateCache.put(actualTypeParams, result);
    }
    return result;
  }
}
