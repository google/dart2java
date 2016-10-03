package generics;

public class Bar2__double_int extends dart._runtime.base.DartObject implements generics.Bar2_interface__double_int
{
    public double varA;
    public int varB;
  
    public Bar2__double_int(dart._runtime.helpers.ConstructorHelper.EmptyConstructorMarker arg, dart._runtime.types.simple.Type type)
    {
      super(arg, type);
    }
  
    public double bar_Bar2__double_int(double a, int b)
    {
      final dart._runtime.types.simple.TypeEnvironment dart2java$localTypeEnv = this.dart2java$type.env;
      return this.getVarA_Bar2__double_int();
    }
    public void _constructor()
    {
      final dart._runtime.types.simple.TypeEnvironment dart2java$localTypeEnv = this.dart2java$type.env;
      super._constructor();
    }
    public double getVarA_Bar2__double_int()
    {
      return this.varA;
    }
    public int getVarB_Bar2__double_int()
    {
      return this.varB;
    }
    public double setVarA_Bar2__double_int(double value)
    {
      this.varA = value;
      return value;
    }
    public int setVarB_Bar2__double_int(int value)
    {
      this.varB = value;
      return value;
    }
    public java.lang.Double getVarA()
    {
      return this.getVarA_Bar2__double_int();
    }
    public java.lang.Integer getVarB()
    {
      return this.getVarB_Bar2__double_int();
    }
    public java.lang.Double setVarA(java.lang.Double value)
    {
      return this.setVarA_Bar2__double_int(((double) value));
    }
    public java.lang.Integer setVarB(java.lang.Integer value)
    {
      return this.setVarB_Bar2__double_int(((int) value));
    }
    public java.lang.Double bar(java.lang.Double a, java.lang.Integer b)
    {
      return this.bar_Bar2__double_int(((double) a), ((int) b));
    }
}
