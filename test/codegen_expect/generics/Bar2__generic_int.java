package generics;

public class Bar2__generic_int<A> extends dart._runtime.base.DartObject implements generics.Bar2_interface__generic_int<A>
{
    private static dart._runtime.types.simple.TypeExpr dart2java$typeExpr_Bar2$A = generics.Bar2.dart2java$typeInfo.typeVariables[0];
    public A varA;
    public int varB;
  
    public Bar2__generic_int(dart._runtime.helpers.ConstructorHelper.EmptyConstructorMarker arg, dart._runtime.types.simple.Type type)
    {
      super(arg, type);
    }
  
    public A bar_Bar2__generic_int(A a, int b)
    {
      final dart._runtime.types.simple.TypeEnvironment dart2java$localTypeEnv = this.dart2java$type.env;
      dart2java$localTypeEnv.evaluate(dart2java$typeExpr_Bar2$A).check(a);
      return this.getVarA_Bar2__generic_int();
    }
    public void _constructor()
    {
      final dart._runtime.types.simple.TypeEnvironment dart2java$localTypeEnv = this.dart2java$type.env;
      super._constructor();
    }
    public A getVarA_Bar2__generic_int()
    {
      return this.varA;
    }
    public int getVarB_Bar2__generic_int()
    {
      return this.varB;
    }
    public A setVarA_Bar2__generic_int(A value)
    {
      this.varA = value;
      return value;
    }
    public int setVarB_Bar2__generic_int(int value)
    {
      this.varB = value;
      return value;
    }
    public A getVarA()
    {
      return this.getVarA_Bar2__generic_int();
    }
    public java.lang.Integer getVarB()
    {
      return this.getVarB_Bar2__generic_int();
    }
    public A setVarA(A value)
    {
      return this.setVarA_Bar2__generic_int(((A) value));
    }
    public java.lang.Integer setVarB(java.lang.Integer value)
    {
      return this.setVarB_Bar2__generic_int(((int) value));
    }
    public A bar(A a, java.lang.Integer b)
    {
      return this.bar_Bar2__generic_int(((A) a), ((int) b));
    }
}
