package dart._runtime.base;

public interface DartObject_interface {
  public int getHashCode();

  public boolean operatorEqual(Object other);

  @Override
  public String toString();

  public void _constructor();
}
