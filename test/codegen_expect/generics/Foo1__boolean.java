package generics;

public class Foo1__boolean extends dart._runtime.base.DartObject implements generics.Foo1_interface__boolean
{
    public boolean variable;
  
    public Foo1__boolean(dart._runtime.helpers.ConstructorHelper.EmptyConstructorMarker arg, dart._runtime.types.simple.Type type)
    {
      super(arg, type);
    }
  
    public boolean foo__boolean(boolean t)
    {
      final dart._runtime.types.simple.TypeEnvironment dart2java$localTypeEnv = this.dart2java$type.env;
      return t;
    }
    public void writeVariable__boolean(boolean value)
    {
      final dart._runtime.types.simple.TypeEnvironment dart2java$localTypeEnv = this.dart2java$type.env;
      this.setVariable__boolean(value);
    }
    public void _constructornewMe__boolean()
    {
      final dart._runtime.types.simple.TypeEnvironment dart2java$localTypeEnv = this.dart2java$type.env;
      super._constructor();
    }
    public boolean getVariable__boolean()
    {
      return this.variable;
    }
    public boolean setVariable__boolean(boolean value)
    {
      this.variable = value;
      return value;
    }
    public java.lang.Boolean getVariable()
    {
      return this.getVariable__boolean();
    }
    public java.lang.Boolean setVariable(java.lang.Boolean value)
    {
      return this.setVariable__boolean(((boolean) value));
    }
    public java.lang.Boolean foo(java.lang.Boolean t)
    {
      return this.foo__boolean(((boolean) t));
    }
    public void writeVariable(java.lang.Boolean value)
    {
      this.writeVariable__boolean(((boolean) value));
    }
    public void _constructornewMe()
    {
      this._constructornewMe__boolean();
    }
}
