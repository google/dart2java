package generics;

public class Foo1__int extends dart._runtime.base.DartObject implements generics.Foo1_interface__int
{
    private static dart._runtime.types.simple.TypeExpr dart2java$typeExpr_Foo1$T = generics.Foo1.dart2java$typeInfo.typeVariables[0];
    public int variable;
    public generics.Foo1_interface__int anotherFoo1;
  
    public Foo1__int(dart._runtime.helpers.ConstructorHelper.EmptyConstructorMarker arg, dart._runtime.types.simple.Type type)
    {
      super(arg, type);
    }
  
    public void createInnerFoo__int()
    {
      final dart._runtime.types.simple.TypeEnvironment dart2java$localTypeEnv = this.dart2java$type.env;
      this.setAnotherFoo1__int(((generics.Foo1_interface__int) ((generics.Foo1_interface__int) generics.Foo1.<java.lang.Integer>factory$(dart2java$localTypeEnv.extend(generics.Foo1.factory$$typeInfo.typeVariables, new dart._runtime.types.simple.Type[] {dart2java$localTypeEnv.evaluate(dart2java$typeExpr_Foo1$T)})))));
    }
    public int foo__int(int t)
    {
      final dart._runtime.types.simple.TypeEnvironment dart2java$localTypeEnv = this.dart2java$type.env;
      return t;
    }
    public void writeVariable__int(int value)
    {
      final dart._runtime.types.simple.TypeEnvironment dart2java$localTypeEnv = this.dart2java$type.env;
      this.setVariable__int(value);
    }
    public void _constructornewMe__int()
    {
      final dart._runtime.types.simple.TypeEnvironment dart2java$localTypeEnv = this.dart2java$type.env;
      super._constructor();
    }
    public int getVariable__int()
    {
      return this.variable;
    }
    public generics.Foo1_interface__int getAnotherFoo1__int()
    {
      return this.anotherFoo1;
    }
    public int setVariable__int(int value)
    {
      this.variable = value;
      return value;
    }
    public generics.Foo1_interface__int setAnotherFoo1__int(generics.Foo1_interface__int value)
    {
      this.anotherFoo1 = value;
      return value;
    }
    public java.lang.Integer getVariable()
    {
      return this.getVariable__int();
    }
    public generics.Foo1_interface<java.lang.Integer> getAnotherFoo1()
    {
      return this.getAnotherFoo1__int();
    }
    public java.lang.Integer setVariable(java.lang.Integer value)
    {
      return this.setVariable__int(((int) value));
    }
    public generics.Foo1_interface<java.lang.Integer> setAnotherFoo1(generics.Foo1_interface<java.lang.Integer> value)
    {
      return this.setAnotherFoo1__int(((generics.Foo1_interface__int) value));
    }
    public void createInnerFoo()
    {
      this.createInnerFoo__int();
    }
    public java.lang.Integer foo(java.lang.Integer t)
    {
      return this.foo__int(((int) t));
    }
    public void writeVariable(java.lang.Integer value)
    {
      this.writeVariable__int(((int) value));
    }
    public void _constructornewMe()
    {
      this._constructornewMe__int();
    }
}
