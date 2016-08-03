package dart._runtime.base;

public class DartObject {
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