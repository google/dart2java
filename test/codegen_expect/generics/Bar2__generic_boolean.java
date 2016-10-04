package generics;

public class Bar2__generic_boolean<A> extends dart._runtime.base.DartObject implements generics.Bar2_interface__generic_boolean<A>
{
    private static dart._runtime.types.simple.TypeExpr dart2java$typeExpr_Bar2$A = generics.Bar2.dart2java$typeInfo.typeVariables[0];
    public A varA;
    public boolean varB;
  
    public Bar2__generic_boolean(dart._runtime.helpers.ConstructorHelper.EmptyConstructorMarker arg, dart._runtime.types.simple.Type type)
    {
      super(arg, type);
    }
  
    public A bar_Bar2__generic_boolean(A a, boolean b)
    {
      final dart._runtime.types.simple.TypeEnvironment dart2java$localTypeEnv = this.dart2java$type.env;
      dart2java$localTypeEnv.evaluate(dart2java$typeExpr_Bar2$A).check(a);
      return this.getVarA_Bar2__generic_boolean();
    }
    public void _constructor()
    {
      final dart._runtime.types.simple.TypeEnvironment dart2java$localTypeEnv = this.dart2java$type.env;
      super._constructor();
    }
    public A getVarA_Bar2__generic_boolean()
    {
      return this.varA;
    }
    public boolean getVarB_Bar2__generic_boolean()
    {
      return this.varB;
    }
    public A setVarA_Bar2__generic_boolean(A value)
    {
      this.varA = value;
      return value;
    }
    public boolean setVarB_Bar2__generic_boolean(boolean value)
    {
      this.varB = value;
      return value;
    }
    public A getVarA()
    {
      return this.getVarA_Bar2__generic_boolean();
    }
    public java.lang.Boolean getVarB()
    {
      return this.getVarB_Bar2__generic_boolean();
    }
    public A setVarA(A value)
    {
      return this.setVarA_Bar2__generic_boolean(((A) value));
    }
    public java.lang.Boolean setVarB(java.lang.Boolean value)
    {
      return this.setVarB_Bar2__generic_boolean(((boolean) value));
    }
    public A bar(A a, java.lang.Boolean b)
    {
      return this.bar_Bar2__generic_boolean(((A) a), ((boolean) b));
    }
}
