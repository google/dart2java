package generics;

public class Bar2__boolean_generic<B> extends dart._runtime.base.DartObject implements generics.Bar2_interface__boolean_generic<B>
{
    public boolean varA;
    public B varB;
  
    public Bar2__boolean_generic(dart._runtime.helpers.ConstructorHelper.EmptyConstructorMarker arg, dart._runtime.types.simple.Type type)
    {
      super(arg, type);
    }
  
    public boolean bar_Bar2__boolean_generic(boolean a, B b)
    {
      final dart._runtime.types.simple.TypeEnvironment dart2java$localTypeEnv = this.dart2java$type.env;
      return this.getVarA_Bar2__boolean_generic();
    }
    public void _constructor()
    {
      final dart._runtime.types.simple.TypeEnvironment dart2java$localTypeEnv = this.dart2java$type.env;
      super._constructor();
    }
    public boolean getVarA_Bar2__boolean_generic()
    {
      return this.varA;
    }
    public B getVarB_Bar2__boolean_generic()
    {
      return this.varB;
    }
    public boolean setVarA_Bar2__boolean_generic(boolean value)
    {
      this.varA = value;
      return value;
    }
    public B setVarB_Bar2__boolean_generic(B value)
    {
      this.varB = value;
      return value;
    }
    public java.lang.Boolean getVarA()
    {
      return this.getVarA_Bar2__boolean_generic();
    }
    public B getVarB()
    {
      return this.getVarB_Bar2__boolean_generic();
    }
    public java.lang.Boolean setVarA(java.lang.Boolean value)
    {
      return this.setVarA_Bar2__boolean_generic(((boolean) value));
    }
    public B setVarB(B value)
    {
      return this.setVarB_Bar2__boolean_generic(((B) value));
    }
    public java.lang.Boolean bar(java.lang.Boolean a, B b)
    {
      return this.bar_Bar2__boolean_generic(((boolean) a), ((B) b));
    }
}
