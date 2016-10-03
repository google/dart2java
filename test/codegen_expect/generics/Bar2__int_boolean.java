package generics;

public class Bar2__int_boolean extends dart._runtime.base.DartObject implements generics.Bar2_interface__int_boolean
{
    public int varA;
    public boolean varB;
  
    public Bar2__int_boolean(dart._runtime.helpers.ConstructorHelper.EmptyConstructorMarker arg, dart._runtime.types.simple.Type type)
    {
      super(arg, type);
    }
  
    public int bar_Bar2__int_boolean(int a, boolean b)
    {
      final dart._runtime.types.simple.TypeEnvironment dart2java$localTypeEnv = this.dart2java$type.env;
      return this.getVarA_Bar2__int_boolean();
    }
    public void _constructor()
    {
      final dart._runtime.types.simple.TypeEnvironment dart2java$localTypeEnv = this.dart2java$type.env;
      super._constructor();
    }
    public int getVarA_Bar2__int_boolean()
    {
      return this.varA;
    }
    public boolean getVarB_Bar2__int_boolean()
    {
      return this.varB;
    }
    public int setVarA_Bar2__int_boolean(int value)
    {
      this.varA = value;
      return value;
    }
    public boolean setVarB_Bar2__int_boolean(boolean value)
    {
      this.varB = value;
      return value;
    }
    public java.lang.Integer getVarA()
    {
      return this.getVarA_Bar2__int_boolean();
    }
    public java.lang.Boolean getVarB()
    {
      return this.getVarB_Bar2__int_boolean();
    }
    public java.lang.Integer setVarA(java.lang.Integer value)
    {
      return this.setVarA_Bar2__int_boolean(((int) value));
    }
    public java.lang.Boolean setVarB(java.lang.Boolean value)
    {
      return this.setVarB_Bar2__int_boolean(((boolean) value));
    }
    public java.lang.Integer bar(java.lang.Integer a, java.lang.Boolean b)
    {
      return this.bar_Bar2__int_boolean(((int) a), ((boolean) b));
    }
}
