package dart._runtime.base;

import dart._runtime.helpers.ConstructorHelper;
import dart._runtime.helpers.ObjectHelper;
import dart._runtime.types.simple.InterfaceType;
import dart._runtime.types.simple.InterfaceTypeExpr;
import dart._runtime.types.simple.Type;
import dart._runtime.types.simple.TypeEnvironment;

public class DartObject implements dart.core.Object_interface {
  public final InterfaceType dart2java$type;

  private static final InterfaceType objectType =
      TypeEnvironment.ROOT.evaluate(new InterfaceTypeExpr(ObjectHelper.dart2java$typeInfo));

  public DartObject() {
    this((ConstructorHelper.EmptyConstructorMarker) null, objectType);
    _constructor();
  }

  public DartObject(Type type) {
    this((ConstructorHelper.EmptyConstructorMarker) null, type);
    _constructor();
  }

  public DartObject(@SuppressWarnings("unused") ConstructorHelper.EmptyConstructorMarker marker,
      Type type) {
    this.dart2java$type = (InterfaceType) type;
  }

  protected void _constructor() {

  }

  public int getHashCode() {
    return this.hashCode();
  }

  public boolean operatorEqual(Object other) {
    return this == other;
  }

  @Override
  public String toString() {
    // TODO(springerm): Extend for runtimeType
    return "Instance of 'Object'";
  }
}
