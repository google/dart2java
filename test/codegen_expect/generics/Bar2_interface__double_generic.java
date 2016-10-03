package generics;

public interface Bar2_interface__double_generic<B> extends dart._runtime.base.DartObject_interface, generics.Bar2_interface<java.lang.Double, B>
{
  double bar_Bar2__double_generic(double a, B b);
  double getVarA_Bar2__double_generic();
  B getVarB_Bar2__double_generic();
  double setVarA_Bar2__double_generic(double value);
  B setVarB_Bar2__double_generic(B value);
  default public java.lang.Double getVarA_Bar2()
  {
    return this.getVarA_Bar2__double_generic();
  }
  default public B getVarB_Bar2()
  {
    return this.getVarB_Bar2__double_generic();
  }
  default public java.lang.Double setVarA_Bar2(java.lang.Double value)
  {
    return this.setVarA_Bar2__double_generic(((double) value));
  }
  default public B setVarB_Bar2(B value)
  {
    return this.setVarB_Bar2__double_generic(((B) value));
  }
  default public java.lang.Double bar_Bar2(java.lang.Double a, B b)
  {
    return this.bar_Bar2__double_generic(((double) a), ((B) b));
  }
}
