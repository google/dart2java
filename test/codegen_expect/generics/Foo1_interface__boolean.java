package generics;

public interface Foo1_interface__boolean extends dart._runtime.base.DartObject_interface, generics.Foo1_interface<java.lang.Boolean>
{
  void createInnerFoo_Foo1__boolean();
  boolean foo_Foo1__boolean(boolean t);
  void writeVariable_Foo1__boolean(boolean value);
  boolean getVariable_Foo1__boolean();
  generics.Foo1_interface__boolean getAnotherFoo1_Foo1__boolean();
  boolean setVariable_Foo1__boolean(boolean value);
  generics.Foo1_interface__boolean setAnotherFoo1_Foo1__boolean(generics.Foo1_interface__boolean value);
  default public java.lang.Boolean getVariable_Foo1()
  {
    return this.getVariable_Foo1__boolean();
  }
  default public generics.Foo1_interface<java.lang.Boolean> getAnotherFoo1_Foo1()
  {
    return this.getAnotherFoo1_Foo1__boolean();
  }
  default public java.lang.Boolean setVariable_Foo1(java.lang.Boolean value)
  {
    return this.setVariable_Foo1__boolean(((boolean) value));
  }
  default public generics.Foo1_interface<java.lang.Boolean> setAnotherFoo1_Foo1(generics.Foo1_interface<java.lang.Boolean> value)
  {
    return this.setAnotherFoo1_Foo1__boolean(((generics.Foo1_interface__boolean) value));
  }
  default public void createInnerFoo_Foo1()
  {
    this.createInnerFoo_Foo1__boolean();
  }
  default public java.lang.Boolean foo_Foo1(java.lang.Boolean t)
  {
    return this.foo_Foo1__boolean(((boolean) t));
  }
  default public void writeVariable_Foo1(java.lang.Boolean value)
  {
    this.writeVariable_Foo1__boolean(((boolean) value));
  }
}
