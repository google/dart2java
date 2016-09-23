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
  
    public void createInnerFoo__double()
    {
      final dart._runtime.types.simple.TypeEnvironment dart2java$localTypeEnv = this.dart2java$type.env;
      this.setAnotherFoo1__double(((generics.Foo1_interface__double) ((generics.Foo1_interface__double) generics.Foo1.<java.lang.Double>factory$(dart2java$localTypeEnv.extend(generics.Foo1.factory$$typeInfo.typeVariables, new dart._runtime.types.simple.Type[] {dart2java$localTypeEnv.evaluate(dart2java$typeExpr_Foo1$T)})))));
    }
    public double foo__double(double t)
    {
      final dart._runtime.types.simple.TypeEnvironment dart2java$localTypeEnv = this.dart2java$type.env;
      return t;
    }
    public void writeVariable__double(double value)
    {
      final dart._runtime.types.simple.TypeEnvironment dart2java$localTypeEnv = this.dart2java$type.env;
      this.setVariable__double(value);
    }
    public void _constructornewMe__double()
    {
      final dart._runtime.types.simple.TypeEnvironment dart2java$localTypeEnv = this.dart2java$type.env;
      super._constructor();
    }
    public double getVariable__double()
    {
      return this.variable;
    }
    public generics.Foo1_interface__double getAnotherFoo1__double()
    {
      return this.anotherFoo1;
    }
    public double setVariable__double(double value)
    {
      this.variable = value;
      return value;
    }
    public generics.Foo1_interface__double setAnotherFoo1__double(generics.Foo1_interface__double value)
    {
      this.anotherFoo1 = value;
      return value;
    }
    public java.lang.Double getVariable()
    {
      return this.getVariable__double();
    }
    public generics.Foo1_interface<java.lang.Double> getAnotherFoo1()
    {
      return this.getAnotherFoo1__double();
    }
    public java.lang.Double setVariable(java.lang.Double value)
    {
      return this.setVariable__double(((double) value));
    }
    public generics.Foo1_interface<java.lang.Double> setAnotherFoo1(generics.Foo1_interface<java.lang.Double> value)
    {
      return this.setAnotherFoo1__double(((generics.Foo1_interface__double) value));
    }
    public void createInnerFoo()
    {
      this.createInnerFoo__double();
    }
    public java.lang.Double foo(java.lang.Double t)
    {
      return this.foo__double(((double) t));
    }
    public void writeVariable(java.lang.Double value)
    {
      this.writeVariable__double(((double) value));
    }
    public void _constructornewMe()
    {
      this._constructornewMe__double();
    }
}
