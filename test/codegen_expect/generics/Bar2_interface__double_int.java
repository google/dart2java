package generics;

public interface Bar2_interface__double_int extends dart._runtime.base.DartObject_interface, generics.Bar2_interface<java.lang.Double, java.lang.Integer>, generics.Bar2_interface__double_generic<java.lang.Integer>, generics.Bar2_interface__generic_int<java.lang.Double>
{
  double bar_Bar2__double_int(double a, int b);
  double getVarA_Bar2__double_int();
  int getVarB_Bar2__double_int();
  double setVarA_Bar2__double_int(double value);
  int setVarB_Bar2__double_int(int value);
  default public java.lang.Double getVarA_Bar2()
  {
    return this.getVarA_Bar2__double_int();
  }
  default public java.lang.Integer getVarB_Bar2()
  {
    return this.getVarB_Bar2__double_int();
  }
  default public java.lang.Double setVarA_Bar2(java.lang.Double value)
  {
    return this.setVarA_Bar2__double_int(((double) value));
  }
  default public java.lang.Integer setVarB_Bar2(java.lang.Integer value)
  {
    return this.setVarB_Bar2__double_int(((int) value));
  }
  default public java.lang.Double bar_Bar2(java.lang.Double a, java.lang.Integer b)
  {
    return this.bar_Bar2__double_int(((double) a), ((int) b));
  }
  default public double getVarA_Bar2__double_generic()
  {
    return this.getVarA_Bar2__double_int();
  }
  default public java.lang.Integer getVarB_Bar2__double_generic()
  {
    return this.getVarB_Bar2__double_int();
  }
  default public double setVarA_Bar2__double_generic(double value)
  {
    return this.setVarA_Bar2__double_int(((double) value));
  }
  default public java.lang.Integer setVarB_Bar2__double_generic(java.lang.Integer value)
  {
    return this.setVarB_Bar2__double_int(((int) value));
  }
  default public double bar_Bar2__double_generic(double a, java.lang.Integer b)
  {
    return this.bar_Bar2__double_int(((double) a), ((int) b));
  }
  default public java.lang.Double getVarA_Bar2__generic_int()
  {
    return this.getVarA_Bar2__double_int();
  }
  default public int getVarB_Bar2__generic_int()
  {
    return this.getVarB_Bar2__double_int();
  }
  default public java.lang.Double setVarA_Bar2__generic_int(java.lang.Double value)
  {
    return this.setVarA_Bar2__double_int(((double) value));
  }
  default public int setVarB_Bar2__generic_int(int value)
  {
    return this.setVarB_Bar2__double_int(((int) value));
  }
  default public java.lang.Double bar_Bar2__generic_int(java.lang.Double a, int b)
  {
    return this.bar_Bar2__double_int(((double) a), ((int) b));
  }
}
