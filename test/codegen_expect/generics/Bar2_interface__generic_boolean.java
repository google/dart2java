package generics;

public interface Bar2_interface__generic_boolean<A> extends dart._runtime.base.DartObject_interface, generics.Bar2_interface<A, java.lang.Boolean>
{
  A bar_Bar2__generic_boolean(A a, boolean b);
  A getVarA_Bar2__generic_boolean();
  boolean getVarB_Bar2__generic_boolean();
  A setVarA_Bar2__generic_boolean(A value);
  boolean setVarB_Bar2__generic_boolean(boolean value);
  default public A getVarA_Bar2()
  {
    return this.getVarA_Bar2__generic_boolean();
  }
  default public java.lang.Boolean getVarB_Bar2()
  {
    return this.getVarB_Bar2__generic_boolean();
  }
  default public A setVarA_Bar2(A value)
  {
    return this.setVarA_Bar2__generic_boolean(((A) value));
  }
  default public java.lang.Boolean setVarB_Bar2(java.lang.Boolean value)
  {
    return this.setVarB_Bar2__generic_boolean(((boolean) value));
  }
  default public A bar_Bar2(A a, java.lang.Boolean b)
  {
    return this.bar_Bar2__generic_boolean(((A) a), ((boolean) b));
  }
}
