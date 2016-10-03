package generics;

public interface Bar2_interface__boolean_int extends dart._runtime.base.DartObject_interface, generics.Bar2_interface<java.lang.Boolean, java.lang.Integer>, generics.Bar2_interface__boolean_generic<java.lang.Integer>, generics.Bar2_interface__generic_int<java.lang.Boolean>
{
  boolean bar_Bar2__boolean_int(boolean a, int b);
  boolean getVarA_Bar2__boolean_int();
  int getVarB_Bar2__boolean_int();
  boolean setVarA_Bar2__boolean_int(boolean value);
  int setVarB_Bar2__boolean_int(int value);
  default public java.lang.Boolean getVarA_Bar2()
  {
    return this.getVarA_Bar2__boolean_int();
  }
  default public java.lang.Integer getVarB_Bar2()
  {
    return this.getVarB_Bar2__boolean_int();
  }
  default public java.lang.Boolean setVarA_Bar2(java.lang.Boolean value)
  {
    return this.setVarA_Bar2__boolean_int(((boolean) value));
  }
  default public java.lang.Integer setVarB_Bar2(java.lang.Integer value)
  {
    return this.setVarB_Bar2__boolean_int(((int) value));
  }
  default public java.lang.Boolean bar_Bar2(java.lang.Boolean a, java.lang.Integer b)
  {
    return this.bar_Bar2__boolean_int(((boolean) a), ((int) b));
  }
  default public boolean getVarA_Bar2__boolean_generic()
  {
    return this.getVarA_Bar2__boolean_int();
  }
  default public java.lang.Integer getVarB_Bar2__boolean_generic()
  {
    return this.getVarB_Bar2__boolean_int();
  }
  default public boolean setVarA_Bar2__boolean_generic(boolean value)
  {
    return this.setVarA_Bar2__boolean_int(((boolean) value));
  }
  default public java.lang.Integer setVarB_Bar2__boolean_generic(java.lang.Integer value)
  {
    return this.setVarB_Bar2__boolean_int(((int) value));
  }
  default public boolean bar_Bar2__boolean_generic(boolean a, java.lang.Integer b)
  {
    return this.bar_Bar2__boolean_int(((boolean) a), ((int) b));
  }
  default public java.lang.Boolean getVarA_Bar2__generic_int()
  {
    return this.getVarA_Bar2__boolean_int();
  }
  default public int getVarB_Bar2__generic_int()
  {
    return this.getVarB_Bar2__boolean_int();
  }
  default public java.lang.Boolean setVarA_Bar2__generic_int(java.lang.Boolean value)
  {
    return this.setVarA_Bar2__boolean_int(((boolean) value));
  }
  default public int setVarB_Bar2__generic_int(int value)
  {
    return this.setVarB_Bar2__boolean_int(((int) value));
  }
  default public java.lang.Boolean bar_Bar2__generic_int(java.lang.Boolean a, int b)
  {
    return this.bar_Bar2__boolean_int(((boolean) a), ((int) b));
  }
}
