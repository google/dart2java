package generics;

public interface Foo1_interface__int extends dart._runtime.base.DartObject_interface, generics.Foo1_interface<java.lang.Integer>
{
  void createInnerFoo_Foo1__int();
  int foo_Foo1__int(int t);
  void writeVariable_Foo1__int(int value);
  int getVariable_Foo1__int();
  generics.Foo1_interface__int getAnotherFoo1_Foo1__int();
  int setVariable_Foo1__int(int value);
  generics.Foo1_interface__int setAnotherFoo1_Foo1__int(generics.Foo1_interface__int value);
  default public java.lang.Integer getVariable_Foo1()
  {
    return this.getVariable_Foo1__int();
  }
  default public generics.Foo1_interface<java.lang.Integer> getAnotherFoo1_Foo1()
  {
    return this.getAnotherFoo1_Foo1__int();
  }
  default public java.lang.Integer setVariable_Foo1(java.lang.Integer value)
  {
    return this.setVariable_Foo1__int(((int) value));
  }
  default public generics.Foo1_interface<java.lang.Integer> setAnotherFoo1_Foo1(generics.Foo1_interface<java.lang.Integer> value)
  {
    return this.setAnotherFoo1_Foo1__int(((generics.Foo1_interface__int) value));
  }
  default public void createInnerFoo_Foo1()
  {
    this.createInnerFoo_Foo1__int();
  }
  default public java.lang.Integer foo_Foo1(java.lang.Integer t)
  {
    return this.foo_Foo1__int(((int) t));
  }
  default public void writeVariable_Foo1(java.lang.Integer value)
  {
    this.writeVariable_Foo1__int(((int) value));
  }
}
