package generics;

public class Foo1__double extends dart._runtime.base.DartObject implements generics.Foo1_interface__double
{
    private static dart._runtime.types.simple.TypeExpr dart2java$typeExpr_Foo1$T = generics.Foo1.dart2java$typeInfo.typeVariables[0];
    public double variable;
    public generics.Foo1_interface__double anotherFoo1;
  
    public Foo1__double(dart._runtime.helpers.ConstructorHelper.EmptyConstructorMarker arg, dart._runtime.types.simple.Type type)
    {
      super(arg, type);
    }
  
    public void createInnerFoo_Foo1__double()
    {
      final dart._runtime.types.simple.TypeEnvironment dart2java$localTypeEnv = this.dart2java$type.env;
      this.setAnotherFoo1_Foo1__double(((generics.Foo1_interface__double) ((generics.Foo1_interface__double) generics.Foo1.<java.lang.Double>factory$(dart2java$localTypeEnv.extend(generics.Foo1.factory$$typeInfo.typeVariables, new dart._runtime.types.simple.Type[] {dart2java$localTypeEnv.evaluate(dart2java$typeExpr_Foo1$T)})))));
    }
    public double foo_Foo1__double(double t)
    {
      final dart._runtime.types.simple.TypeEnvironment dart2java$localTypeEnv = this.dart2java$type.env;
      return t;
    }
    public void writeVariable_Foo1__double(double value)
    {
      final dart._runtime.types.simple.TypeEnvironment dart2java$localTypeEnv = this.dart2java$type.env;
      this.setVariable_Foo1__double(value);
    }
    public void _constructornewMe()
    {
      final dart._runtime.types.simple.TypeEnvironment dart2java$localTypeEnv = this.dart2java$type.env;
      super._constructor();
    }
    public double getVariable_Foo1__double()
    {
      return this.variable;
    }
    public generics.Foo1_interface__double getAnotherFoo1_Foo1__double()
    {
      return this.anotherFoo1;
    }
    public double setVariable_Foo1__double(double value)
    {
      this.variable = value;
      return value;
    }
    public generics.Foo1_interface__double setAnotherFoo1_Foo1__double(generics.Foo1_interface__double value)
    {
      this.anotherFoo1 = value;
      return value;
    }
    public java.lang.Double getVariable()
    {
      return this.getVariable_Foo1__double();
    }
    public generics.Foo1_interface__double getAnotherFoo1()
    {
      return this.getAnotherFoo1_Foo1__double();
    }
    public java.lang.Double setVariable(java.lang.Double value)
    {
      return this.setVariable_Foo1__double(((double) value));
    }
    public generics.Foo1_interface__double setAnotherFoo1(generics.Foo1_interface__double value)
    {
      return this.setAnotherFoo1_Foo1__double(((generics.Foo1_interface__double) value));
    }
    public void createInnerFoo()
    {
      this.createInnerFoo_Foo1__double();
    }
    public java.lang.Double foo(java.lang.Double t)
    {
      return this.foo_Foo1__double(((double) t));
    }
    public void writeVariable(java.lang.Double value)
    {
      this.writeVariable_Foo1__double(((double) value));
    }
}
