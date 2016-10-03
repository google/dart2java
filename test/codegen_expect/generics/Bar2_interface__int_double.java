package generics;

public interface Bar2_interface__int_double extends dart._runtime.base.DartObject_interface, generics.Bar2_interface<java.lang.Integer, java.lang.Double>, generics.Bar2_interface__int_generic<java.lang.Double>, generics.Bar2_interface__generic_double<java.lang.Integer>
{
  int bar_Bar2__int_double(int a, double b);
  int getVarA_Bar2__int_double();
  double getVarB_Bar2__int_double();
  int setVarA_Bar2__int_double(int value);
  double setVarB_Bar2__int_double(double value);
  default public java.lang.Integer getVarA_Bar2()
  {
    return this.getVarA_Bar2__int_double();
  }
  default public java.lang.Double getVarB_Bar2()
  {
    return this.getVarB_Bar2__int_double();
  }
  default public java.lang.Integer setVarA_Bar2(java.lang.Integer value)
  {
    return this.setVarA_Bar2__int_double(((int) value));
  }
  default public java.lang.Double setVarB_Bar2(java.lang.Double value)
  {
    return this.setVarB_Bar2__int_double(((double) value));
  }
  default public java.lang.Integer bar_Bar2(java.lang.Integer a, java.lang.Double b)
  {
    return this.bar_Bar2__int_double(((int) a), ((double) b));
  }
  default public int getVarA_Bar2__int_generic()
  {
    return this.getVarA_Bar2__int_double();
  }
  default public java.lang.Double getVarB_Bar2__int_generic()
  {
    return this.getVarB_Bar2__int_double();
  }
  default public int setVarA_Bar2__int_generic(int value)
  {
    return this.setVarA_Bar2__int_double(((int) value));
  }
  default public java.lang.Double setVarB_Bar2__int_generic(java.lang.Double value)
  {
    return this.setVarB_Bar2__int_double(((double) value));
  }
  default public int bar_Bar2__int_generic(int a, java.lang.Double b)
  {
    return this.bar_Bar2__int_double(((int) a), ((double) b));
  }
  default public java.lang.Integer getVarA_Bar2__generic_double()
  {
    return this.getVarA_Bar2__int_double();
  }
  default public double getVarB_Bar2__generic_double()
  {
    return this.getVarB_Bar2__int_double();
  }
  default public java.lang.Integer setVarA_Bar2__generic_double(java.lang.Integer value)
  {
    return this.setVarA_Bar2__int_double(((int) value));
  }
  default public double setVarB_Bar2__generic_double(double value)
  {
    return this.setVarB_Bar2__int_double(((double) value));
  }
  default public java.lang.Integer bar_Bar2__generic_double(java.lang.Integer a, double b)
  {
    return this.bar_Bar2__int_double(((int) a), ((double) b));
  }
}
