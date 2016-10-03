package generics;

public interface Bar2_interface__boolean_double extends dart._runtime.base.DartObject_interface, generics.Bar2_interface<java.lang.Boolean, java.lang.Double>, generics.Bar2_interface__boolean_generic<java.lang.Double>, generics.Bar2_interface__generic_double<java.lang.Boolean>
{
  boolean bar_Bar2__boolean_double(boolean a, double b);
  boolean getVarA_Bar2__boolean_double();
  double getVarB_Bar2__boolean_double();
  boolean setVarA_Bar2__boolean_double(boolean value);
  double setVarB_Bar2__boolean_double(double value);
  default public java.lang.Boolean getVarA_Bar2()
  {
    return this.getVarA_Bar2__boolean_double();
  }
  default public java.lang.Double getVarB_Bar2()
  {
    return this.getVarB_Bar2__boolean_double();
  }
  default public java.lang.Boolean setVarA_Bar2(java.lang.Boolean value)
  {
    return this.setVarA_Bar2__boolean_double(((boolean) value));
  }
  default public java.lang.Double setVarB_Bar2(java.lang.Double value)
  {
    return this.setVarB_Bar2__boolean_double(((double) value));
  }
  default public java.lang.Boolean bar_Bar2(java.lang.Boolean a, java.lang.Double b)
  {
    return this.bar_Bar2__boolean_double(((boolean) a), ((double) b));
  }
  default public boolean getVarA_Bar2__boolean_generic()
  {
    return this.getVarA_Bar2__boolean_double();
  }
  default public java.lang.Double getVarB_Bar2__boolean_generic()
  {
    return this.getVarB_Bar2__boolean_double();
  }
  default public boolean setVarA_Bar2__boolean_generic(boolean value)
  {
    return this.setVarA_Bar2__boolean_double(((boolean) value));
  }
  default public java.lang.Double setVarB_Bar2__boolean_generic(java.lang.Double value)
  {
    return this.setVarB_Bar2__boolean_double(((double) value));
  }
  default public boolean bar_Bar2__boolean_generic(boolean a, java.lang.Double b)
  {
    return this.bar_Bar2__boolean_double(((boolean) a), ((double) b));
  }
  default public java.lang.Boolean getVarA_Bar2__generic_double()
  {
    return this.getVarA_Bar2__boolean_double();
  }
  default public double getVarB_Bar2__generic_double()
  {
    return this.getVarB_Bar2__boolean_double();
  }
  default public java.lang.Boolean setVarA_Bar2__generic_double(java.lang.Boolean value)
  {
    return this.setVarA_Bar2__boolean_double(((boolean) value));
  }
  default public double setVarB_Bar2__generic_double(double value)
  {
    return this.setVarB_Bar2__boolean_double(((double) value));
  }
  default public java.lang.Boolean bar_Bar2__generic_double(java.lang.Boolean a, double b)
  {
    return this.bar_Bar2__boolean_double(((boolean) a), ((double) b));
  }
}
