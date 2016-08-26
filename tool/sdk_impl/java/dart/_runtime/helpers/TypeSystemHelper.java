package dart._runtime.helpers;

import dart._runtime.base.DartObject;
import dart._runtime.types.simple.InterfaceType;
import dart._runtime.types.simple.InterfaceTypeExpr;
import dart._runtime.types.simple.InterfaceTypeInfo;
import dart._runtime.types.simple.Type;
import dart._runtime.types.simple.TypeEnvironment;

import java.util.HashMap;
import java.util.Map;

public class TypeSystemHelper {
  public static final Type getTrueType(Object o) {
    if (o instanceof DartObject) {
      return ((DartObject)o).dart2java$type;
    } else if (o == null) {
      return nullType;
    } else {
      return javaClasses.getOrDefault(o.getClass(), objectType);
    }
  }

  private static InterfaceType eval(InterfaceTypeInfo info) {
    return TypeEnvironment.ROOT.evaluate(new InterfaceTypeExpr(info));
  }

  private static final InterfaceType nullType = eval(dart.core.Null.dart2java$typeInfo);
  private static final InterfaceType objectType = eval(dart._runtime.helpers.ObjectHelper.dart2java$typeInfo);
  private static final Map<Class<?>, InterfaceType> javaClasses = new HashMap<>();

  static {
    javaClasses.put(Object.class, objectType);
    javaClasses.put(Integer.class, eval(dart._runtime.helpers.IntegerHelper.dart2java$typeInfo));
    javaClasses.put(Double.class, eval(dart._runtime.helpers.DoubleHelper.dart2java$typeInfo));
    javaClasses.put(Boolean.class, eval(dart._runtime.helpers.BoolHelper.dart2java$typeInfo));
    javaClasses.put(String.class, eval(dart._runtime.helpers.StringHelper.dart2java$typeInfo));
  }
}
