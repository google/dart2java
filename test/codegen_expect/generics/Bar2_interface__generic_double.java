package generics;

public interface Bar2_interface__generic_double<A> extends dart._runtime.base.DartObject_interface, generics.Bar2_interface<A, java.lang.Double>
{
  A bar_Bar2__generic_double(A a, double b);
  A getVarA_Bar2__generic_double();
  double getVarB_Bar2__generic_double();
  A setVarA_Bar2__generic_double(A value);
  double setVarB_Bar2__generic_double(double value);
  default public A getVarA_Bar2()
  {
    return this.getVarA_Bar2__generic_double();
  }
  default public java.lang.Double getVarB_Bar2()
  {
    return this.getVarB_Bar2__generic_double();
  }
  default public A setVarA_Bar2(A value)
  {
    return this.setVarA_Bar2__generic_double(((A) value));
  }
  default public java.lang.Double setVarB_Bar2(java.lang.Double value)
  {
    return this.setVarB_Bar2__generic_double(((double) value));
  }
  default public A bar_Bar2(A a, java.lang.Double b)
  {
    return this.bar_Bar2__generic_double(((A) a), ((double) b));
  }
}
