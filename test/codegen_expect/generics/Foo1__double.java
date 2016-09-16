package generics;

public class Foo1__double extends dart._runtime.base.DartObject implements generics.Foo1_interface__double
{
    public double variable;
  
    public Foo1__double(dart._runtime.helpers.ConstructorHelper.EmptyConstructorMarker arg, dart._runtime.types.simple.Type type)
    {
      super(arg, type);
    }
  
    public double foo__double(double t)
    {
      final dart._runtime.types.simple.TypeEnvironment dart2java$localTypeEnv = this.dart2java$type.env;
      return t;
    }
    public void _constructor__double()
    {
      final dart._runtime.types.simple.TypeEnvironment dart2java$localTypeEnv = this.dart2java$type.env;
      super._constructor();
    }
    public double getVariable__double()
    {
      return this.variable;
    }
    public double setVariable__double(double value)
    {
      this.variable = value;
      return value;
    }
    public java.lang.Double getVariable()
    {
      return this.getVariable__double();
    }
    public java.lang.Double setVariable(java.lang.Double value)
    {
      return this.setVariable__double(((double) value));
    }
    public java.lang.Double foo(java.lang.Double t)
    {
      return this.foo__double(((double) t));
    }
    public void _constructor()
    {
      this._constructor__double();
    }
}
