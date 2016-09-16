package generics;

public class Bar2__boolean_generic<B> extends dart._runtime.base.DartObject implements generics.Bar2_interface__boolean_generic<B>
{
    public boolean varA;
    public B varB;
  
    public Bar2__boolean_generic(dart._runtime.helpers.ConstructorHelper.EmptyConstructorMarker arg, dart._runtime.types.simple.Type type)
    {
      super(arg, type);
    }
  
    public boolean bar__boolean_generic(boolean a, B b)
    {
      final dart._runtime.types.simple.TypeEnvironment dart2java$localTypeEnv = this.dart2java$type.env;
      return this.getVarA__boolean_generic();
    }
    public void _constructor__boolean_generic()
    {
      final dart._runtime.types.simple.TypeEnvironment dart2java$localTypeEnv = this.dart2java$type.env;
      super._constructor();
    }
    public boolean getVarA__boolean_generic()
    {
      return this.varA;
    }
    public B getVarB__boolean_generic()
    {
      return this.varB;
    }
    public boolean setVarA__boolean_generic(boolean value)
    {
      this.varA = value;
      return value;
    }
    public B setVarB__boolean_generic(B value)
    {
      this.varB = value;
      return value;
    }
    public java.lang.Boolean getVarA()
    {
      return this.getVarA__boolean_generic();
    }
    public B getVarB()
    {
      return this.getVarB__boolean_generic();
    }
    public java.lang.Boolean setVarA(java.lang.Boolean value)
    {
      return this.setVarA__boolean_generic(((boolean) value));
    }
    public B setVarB(B value)
    {
      return this.setVarB__boolean_generic(((B) value));
    }
    public java.lang.Boolean bar(java.lang.Boolean a, B b)
    {
      return this.bar__boolean_generic(((boolean) a), ((B) b));
    }
    public void _constructor()
    {
      this._constructor__boolean_generic();
    }
}
