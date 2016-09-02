package dart._runtime.types.simple;

import java.util.SortedMap;
import java.util.TreeMap;

/**
 * A representation of a Dart function signature.
 * <p>
 * An instance of {@code FunctionTypeInfo} stores the parameter types and return type of a Dart
 * function type.
 */
public final class FunctionTypeInfo {
  /**
   * The return type of the function.
   */
  public final TypeExpr returnType;

  /**
   * The number of required arguments.
   */
  public final int requiredParamCount;

  /**
   * The types of the positional parameters.
   * <p>
   * Note that a positional parameter is optional if its (zero-based) index is greater than or equal
   * to {@link #requiredParamCount}.
   */
  public final TypeExpr[] positionalParams;

  /**
   * The named parameters to this function.
   * <p>
   * The values in the {@code SortedMap} will be ordered according to the natural order on {@code
   * String}s, i.e. alphabetically. This is part of the contract of {@code FunctionTypeInfo}.
   */
  public final SortedMap<String, TypeExpr> namedParams;

  private static final String[] EMPTY_STRING_ARRAY = new String[0];
  private static final TypeVariableExpr[] EMPTY_TVE_ARRAY = new TypeVariableExpr[0];

  /**
   * Creates a {@code FunctionTypeInfo} for a function signature with no named parameters.
   *
   * @param returnType return type of function
   * @param requiredParamCount number of required parameters
   * @param positionalParams types of positional parameters (must have at least {@code
   * requiredParamCount} elements).
   */
  public FunctionTypeInfo(TypeExpr returnType, int requiredParamCount,
      TypeExpr[] positionalParams) {
    this(returnType, requiredParamCount, positionalParams, EMPTY_STRING_ARRAY, EMPTY_TVE_ARRAY);
  }

  /**
   * Creates a {@code FunctionTypeInfo} for a function signature.
   *
   * @param returnType return type of function
   * @param requiredParamCount number of required parameters
   * @param positionalParams types of positional parameters (must have at least {@code
   * requiredParamCount} elements).
   * @param namedParamNames names of optional named parameters.
   * @param namedParamTypes types of optional named parameters.
   */
  public FunctionTypeInfo(TypeExpr returnType, int requiredParamCount, TypeExpr[] positionalParams,
      String[] namedParamNames, TypeExpr[] namedParamTypes) {
    this.returnType = returnType;
    this.requiredParamCount = requiredParamCount;
    this.positionalParams = positionalParams;
    this.namedParams = makeSortedMap(namedParamNames, namedParamTypes);
  }

  private static SortedMap<String, TypeExpr> makeSortedMap(String[] names, TypeExpr[] types) {
    assert (names.length == types.length);
    SortedMap<String, TypeExpr> result = new TreeMap<>();
    for (int i = 0; i < names.length; i++) {
      result.put(names[i], types[i]);
    }
    return result;
  }
}
