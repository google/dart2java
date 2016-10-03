package generics;

public class Bar2__boolean_int extends dart._runtime.base.DartObject implements generics.Bar2_interface__boolean_int
{
    public boolean varA;
    public int varB;
  
    public Bar2__boolean_int(dart._runtime.helpers.ConstructorHelper.EmptyConstructorMarker arg, dart._runtime.types.simple.Type type)
    {
      super(arg, type);
    }
  
    public boolean bar_Bar2__boolean_int(boolean a, int b)
    {
      final dart._runtime.types.simple.TypeEnvironment dart2java$localTypeEnv = this.dart2java$type.env;
      return this.getVarA_Bar2__boolean_int();
    }
    public void _constructor()
    {
      final dart._runtime.types.simple.TypeEnvironment dart2java$localTypeEnv = this.dart2java$type.env;
      super._constructor();
    }
    public boolean getVarA_Bar2__boolean_int()
    {
      return this.varA;
    }
    public int getVarB_Bar2__boolean_int()
    {
      return this.varB;
    }
    public boolean setVarA_Bar2__boolean_int(boolean value)
    {
      this.varA = value;
      return value;
    }
    public int setVarB_Bar2__boolean_int(int value)
    {
      this.varB = value;
      return value;
    }
    public java.lang.Boolean getVarA()
    {
      return this.getVarA_Bar2__boolean_int();
    }
    public java.lang.Integer getVarB()
    {
      return this.getVarB_Bar2__boolean_int();
    }
    public java.lang.Boolean setVarA(java.lang.Boolean value)
    {
      return this.setVarA_Bar2__boolean_int(((boolean) value));
    }
    public java.lang.Integer setVarB(java.lang.Integer value)
    {
      return this.setVarB_Bar2__boolean_int(((int) value));
    }
    public java.lang.Boolean bar(java.lang.Boolean a, java.lang.Integer b)
    {
      return this.bar_Bar2__boolean_int(((boolean) a), ((int) b));
    }
}
