package generics;

public class Bar2__int_double extends dart._runtime.base.DartObject implements generics.Bar2_interface__int_double
{
    public int varA;
    public double varB;
  
    public Bar2__int_double(dart._runtime.helpers.ConstructorHelper.EmptyConstructorMarker arg, dart._runtime.types.simple.Type type)
    {
      super(arg, type);
    }
  
    public int bar_Bar2__int_double(int a, double b)
    {
      final dart._runtime.types.simple.TypeEnvironment dart2java$localTypeEnv = this.dart2java$type.env;
      return this.getVarA_Bar2__int_double();
    }
    public void _constructor()
    {
      final dart._runtime.types.simple.TypeEnvironment dart2java$localTypeEnv = this.dart2java$type.env;
      super._constructor();
    }
    public int getVarA_Bar2__int_double()
    {
      return this.varA;
    }
    public double getVarB_Bar2__int_double()
    {
      return this.varB;
    }
    public int setVarA_Bar2__int_double(int value)
    {
      this.varA = value;
      return value;
    }
    public double setVarB_Bar2__int_double(double value)
    {
      this.varB = value;
      return value;
    }
    public java.lang.Integer getVarA()
    {
      return this.getVarA_Bar2__int_double();
    }
    public java.lang.Double getVarB()
    {
      return this.getVarB_Bar2__int_double();
    }
    public java.lang.Integer setVarA(java.lang.Integer value)
    {
      return this.setVarA_Bar2__int_double(((int) value));
    }
    public java.lang.Double setVarB(java.lang.Double value)
    {
      return this.setVarB_Bar2__int_double(((double) value));
    }
    public java.lang.Integer bar(java.lang.Integer a, java.lang.Double b)
    {
      return this.bar_Bar2__int_double(((int) a), ((double) b));
    }
}
