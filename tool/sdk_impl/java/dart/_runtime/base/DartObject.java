package dart._runtime.base;

import dart._runtime.helpers.ConstructorHelper;

public class DartObject {
  public DartObject() {
    _constructor();
  }
  
  public DartObject(ConstructorHelper.EmptyConstructorMarker marker) {

  }

  protected void _constructor() {

  }

  public Integer getHashCode() {
    return this.hashCode();
  }

  public Boolean operatorEqual(Object other) {
    return this == other;
  }

  public String toString() {
    // TODO(springerm): Extend for runtimeType
    return "Instance of 'Object'";
  }
}