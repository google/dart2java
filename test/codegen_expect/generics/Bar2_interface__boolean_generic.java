package generics;

public interface Bar2_interface__boolean_generic<B> extends dart._runtime.base.DartObject_interface, generics.Bar2_interface<java.lang.Boolean, B>
{
  boolean bar_Bar2__boolean_generic(boolean a, B b);
  boolean getVarA_Bar2__boolean_generic();
  B getVarB_Bar2__boolean_generic();
  boolean setVarA_Bar2__boolean_generic(boolean value);
  B setVarB_Bar2__boolean_generic(B value);
  default public java.lang.Boolean getVarA_Bar2()
  {
    return this.getVarA_Bar2__boolean_generic();
  }
  default public B getVarB_Bar2()
  {
    return this.getVarB_Bar2__boolean_generic();
  }
  default public java.lang.Boolean setVarA_Bar2(java.lang.Boolean value)
  {
    return this.setVarA_Bar2__boolean_generic(((boolean) value));
  }
  default public B setVarB_Bar2(B value)
  {
    return this.setVarB_Bar2__boolean_generic(((B) value));
  }
  default public java.lang.Boolean bar_Bar2(java.lang.Boolean a, B b)
  {
    return this.bar_Bar2__boolean_generic(((boolean) a), ((B) b));
  }
}
