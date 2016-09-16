package generics;

public class Foo1__int extends dart._runtime.base.DartObject implements generics.Foo1_interface__int
{
    public int variable;
  
    public Foo1__int(dart._runtime.types.simple.Type type)
    {
      super(((dart._runtime.helpers.ConstructorHelper.EmptyConstructorMarker) null), type);
      this._constructor();
    }
    public Foo1__int(dart._runtime.helpers.ConstructorHelper.EmptyConstructorMarker arg, dart._runtime.types.simple.Type type)
    {
      super(arg, type);
    }
  
    protected void _constructor()
    {
      final dart._runtime.types.simple.TypeEnvironment dart2java$localTypeEnv = this.dart2java$type.env;
      super._constructor();
    }
    public int foo__int(int t)
    {
      final dart._runtime.types.simple.TypeEnvironment dart2java$localTypeEnv = this.dart2java$type.env;
      return t;
    }
    public int getVariable__int()
    {
      return this.variable;
    }
    public int setVariable__int(int value)
    {
      this.variable = value;
      return value;
    }
    public java.lang.Integer getVariable()
    {
      return this.getVariable__int();
    }
    public java.lang.Integer setVariable(java.lang.Integer value)
    {
      return this.setVariable__int(((int) value));
    }
    public java.lang.Integer foo(java.lang.Integer t)
    {
      return this.foo__int(((int) t));
    }
}
