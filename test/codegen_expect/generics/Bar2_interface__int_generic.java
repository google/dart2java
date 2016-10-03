package generics;

public interface Bar2_interface__int_generic<B> extends dart._runtime.base.DartObject_interface, generics.Bar2_interface<java.lang.Integer, B>
{
  int bar_Bar2__int_generic(int a, B b);
  int getVarA_Bar2__int_generic();
  B getVarB_Bar2__int_generic();
  int setVarA_Bar2__int_generic(int value);
  B setVarB_Bar2__int_generic(B value);
  default public java.lang.Integer getVarA_Bar2()
  {
    return this.getVarA_Bar2__int_generic();
  }
  default public B getVarB_Bar2()
  {
    return this.getVarB_Bar2__int_generic();
  }
  default public java.lang.Integer setVarA_Bar2(java.lang.Integer value)
  {
    return this.setVarA_Bar2__int_generic(((int) value));
  }
  default public B setVarB_Bar2(B value)
  {
    return this.setVarB_Bar2__int_generic(((B) value));
  }
  default public java.lang.Integer bar_Bar2(java.lang.Integer a, B b)
  {
    return this.bar_Bar2__int_generic(((int) a), ((B) b));
  }
}
