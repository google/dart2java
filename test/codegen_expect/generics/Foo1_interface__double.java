package generics;

public interface Foo1_interface__double extends dart._runtime.base.DartObject_interface, generics.Foo1_interface<java.lang.Double>
{
  void createInnerFoo_Foo1__double();
  double foo_Foo1__double(double t);
  void writeVariable_Foo1__double(double value);
  double getVariable_Foo1__double();
  generics.Foo1_interface__double getAnotherFoo1_Foo1__double();
  double setVariable_Foo1__double(double value);
  generics.Foo1_interface__double setAnotherFoo1_Foo1__double(generics.Foo1_interface__double value);
  default public java.lang.Double getVariable_Foo1()
  {
    return this.getVariable_Foo1__double();
  }
  default public generics.Foo1_interface<java.lang.Double> getAnotherFoo1_Foo1()
  {
    return this.getAnotherFoo1_Foo1__double();
  }
  default public java.lang.Double setVariable_Foo1(java.lang.Double value)
  {
    return this.setVariable_Foo1__double(((double) value));
  }
  default public generics.Foo1_interface<java.lang.Double> setAnotherFoo1_Foo1(generics.Foo1_interface<java.lang.Double> value)
  {
    return this.setAnotherFoo1_Foo1__double(((generics.Foo1_interface__double) value));
  }
  default public void createInnerFoo_Foo1()
  {
    this.createInnerFoo_Foo1__double();
  }
  default public java.lang.Double foo_Foo1(java.lang.Double t)
  {
    return this.foo_Foo1__double(((double) t));
  }
  default public void writeVariable_Foo1(java.lang.Double value)
  {
    this.writeVariable_Foo1__double(((double) value));
  }
}
