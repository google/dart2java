package generics;

public class Bar2__generic_double<A> extends dart._runtime.base.DartObject implements generics.Bar2_interface__generic_double<A>
{
    private static dart._runtime.types.simple.TypeExpr dart2java$typeExpr_Bar2$A = generics.Bar2.dart2java$typeInfo.typeVariables[0];
    public A varA;
    public double varB;
  
    public Bar2__generic_double(dart._runtime.helpers.ConstructorHelper.EmptyConstructorMarker arg, dart._runtime.types.simple.Type type)
    {
      super(arg, type);
    }
  
    public A bar_Bar2__generic_double(A a, double b)
    {
      final dart._runtime.types.simple.TypeEnvironment dart2java$localTypeEnv = this.dart2java$type.env;
      dart2java$localTypeEnv.evaluate(dart2java$typeExpr_Bar2$A).check(a);
      return this.getVarA_Bar2__generic_double();
    }
    public void _constructor()
    {
      final dart._runtime.types.simple.TypeEnvironment dart2java$localTypeEnv = this.dart2java$type.env;
      super._constructor();
    }
    public A getVarA_Bar2__generic_double()
    {
      return this.varA;
    }
    public double getVarB_Bar2__generic_double()
    {
      return this.varB;
    }
    public A setVarA_Bar2__generic_double(A value)
    {
      this.varA = value;
      return value;
    }
    public double setVarB_Bar2__generic_double(double value)
    {
      this.varB = value;
      return value;
    }
    public A getVarA()
    {
      return this.getVarA_Bar2__generic_double();
    }
    public java.lang.Double getVarB()
    {
      return this.getVarB_Bar2__generic_double();
    }
    public A setVarA(A value)
    {
      return this.setVarA_Bar2__generic_double(((A) value));
    }
    public java.lang.Double setVarB(java.lang.Double value)
    {
      return this.setVarB_Bar2__generic_double(((double) value));
    }
    public A bar(A a, java.lang.Double b)
    {
      return this.bar_Bar2__generic_double(((A) a), ((double) b));
    }
}
