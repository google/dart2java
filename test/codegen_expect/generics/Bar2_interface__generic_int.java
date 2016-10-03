package generics;

public interface Bar2_interface__generic_int<A> extends dart._runtime.base.DartObject_interface, generics.Bar2_interface<A, java.lang.Integer>
{
  A bar_Bar2__generic_int(A a, int b);
  A getVarA_Bar2__generic_int();
  int getVarB_Bar2__generic_int();
  A setVarA_Bar2__generic_int(A value);
  int setVarB_Bar2__generic_int(int value);
  default public A getVarA_Bar2()
  {
    return this.getVarA_Bar2__generic_int();
  }
  default public java.lang.Integer getVarB_Bar2()
  {
    return this.getVarB_Bar2__generic_int();
  }
  default public A setVarA_Bar2(A value)
  {
    return this.setVarA_Bar2__generic_int(((A) value));
  }
  default public java.lang.Integer setVarB_Bar2(java.lang.Integer value)
  {
    return this.setVarB_Bar2__generic_int(((int) value));
  }
  default public A bar_Bar2(A a, java.lang.Integer b)
  {
    return this.bar_Bar2__generic_int(((A) a), ((int) b));
  }
}
