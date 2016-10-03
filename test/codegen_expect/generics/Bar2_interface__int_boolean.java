package generics;

public interface Bar2_interface__int_boolean extends dart._runtime.base.DartObject_interface, generics.Bar2_interface<java.lang.Integer, java.lang.Boolean>, generics.Bar2_interface__int_generic<java.lang.Boolean>, generics.Bar2_interface__generic_boolean<java.lang.Integer>
{
  int bar_Bar2__int_boolean(int a, boolean b);
  int getVarA_Bar2__int_boolean();
  boolean getVarB_Bar2__int_boolean();
  int setVarA_Bar2__int_boolean(int value);
  boolean setVarB_Bar2__int_boolean(boolean value);
  default public java.lang.Integer getVarA_Bar2()
  {
    return this.getVarA_Bar2__int_boolean();
  }
  default public java.lang.Boolean getVarB_Bar2()
  {
    return this.getVarB_Bar2__int_boolean();
  }
  default public java.lang.Integer setVarA_Bar2(java.lang.Integer value)
  {
    return this.setVarA_Bar2__int_boolean(((int) value));
  }
  default public java.lang.Boolean setVarB_Bar2(java.lang.Boolean value)
  {
    return this.setVarB_Bar2__int_boolean(((boolean) value));
  }
  default public java.lang.Integer bar_Bar2(java.lang.Integer a, java.lang.Boolean b)
  {
    return this.bar_Bar2__int_boolean(((int) a), ((boolean) b));
  }
  default public int getVarA_Bar2__int_generic()
  {
    return this.getVarA_Bar2__int_boolean();
  }
  default public java.lang.Boolean getVarB_Bar2__int_generic()
  {
    return this.getVarB_Bar2__int_boolean();
  }
  default public int setVarA_Bar2__int_generic(int value)
  {
    return this.setVarA_Bar2__int_boolean(((int) value));
  }
  default public java.lang.Boolean setVarB_Bar2__int_generic(java.lang.Boolean value)
  {
    return this.setVarB_Bar2__int_boolean(((boolean) value));
  }
  default public int bar_Bar2__int_generic(int a, java.lang.Boolean b)
  {
    return this.bar_Bar2__int_boolean(((int) a), ((boolean) b));
  }
  default public java.lang.Integer getVarA_Bar2__generic_boolean()
  {
    return this.getVarA_Bar2__int_boolean();
  }
  default public boolean getVarB_Bar2__generic_boolean()
  {
    return this.getVarB_Bar2__int_boolean();
  }
  default public java.lang.Integer setVarA_Bar2__generic_boolean(java.lang.Integer value)
  {
    return this.setVarA_Bar2__int_boolean(((int) value));
  }
  default public boolean setVarB_Bar2__generic_boolean(boolean value)
  {
    return this.setVarB_Bar2__int_boolean(((boolean) value));
  }
  default public java.lang.Integer bar_Bar2__generic_boolean(java.lang.Integer a, boolean b)
  {
    return this.bar_Bar2__int_boolean(((int) a), ((boolean) b));
  }
}
