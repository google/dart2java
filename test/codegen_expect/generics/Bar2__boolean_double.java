package generics;

public class Bar2__boolean_double extends dart._runtime.base.DartObject implements generics.Bar2_interface__boolean_double
{
    public boolean varA;
    public double varB;
  
    public Bar2__boolean_double(dart._runtime.helpers.ConstructorHelper.EmptyConstructorMarker arg, dart._runtime.types.simple.Type type)
    {
      super(arg, type);
    }
  
    public boolean bar_Bar2__boolean_double(boolean a, double b)
    {
      final dart._runtime.types.simple.TypeEnvironment dart2java$localTypeEnv = this.dart2java$type.env;
      return this.getVarA_Bar2__boolean_double();
    }
    public void _constructor()
    {
      final dart._runtime.types.simple.TypeEnvironment dart2java$localTypeEnv = this.dart2java$type.env;
      super._constructor();
    }
    public boolean getVarA_Bar2__boolean_double()
    {
      return this.varA;
    }
    public double getVarB_Bar2__boolean_double()
    {
      return this.varB;
    }
    public boolean setVarA_Bar2__boolean_double(boolean value)
    {
      this.varA = value;
      return value;
    }
    public double setVarB_Bar2__boolean_double(double value)
    {
      this.varB = value;
      return value;
    }
    public java.lang.Boolean getVarA()
    {
      return this.getVarA_Bar2__boolean_double();
    }
    public java.lang.Double getVarB()
    {
      return this.getVarB_Bar2__boolean_double();
    }
    public java.lang.Boolean setVarA(java.lang.Boolean value)
    {
      return this.setVarA_Bar2__boolean_double(((boolean) value));
    }
    public java.lang.Double setVarB(java.lang.Double value)
    {
      return this.setVarB_Bar2__boolean_double(((double) value));
    }
    public java.lang.Boolean bar(java.lang.Boolean a, java.lang.Double b)
    {
      return this.bar_Bar2__boolean_double(((boolean) a), ((double) b));
    }
}
