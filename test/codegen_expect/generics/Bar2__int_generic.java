package generics;

public class Bar2__int_generic<B> extends dart._runtime.base.DartObject implements generics.Bar2_interface__int_generic<B>
{
    public int varA;
    public B varB;
  
    public Bar2__int_generic(dart._runtime.helpers.ConstructorHelper.EmptyConstructorMarker arg, dart._runtime.types.simple.Type type)
    {
      super(arg, type);
    }
  
    public int bar__int_generic(int a, B b)
    {
      final dart._runtime.types.simple.TypeEnvironment dart2java$localTypeEnv = this.dart2java$type.env;
      return this.getVarA__int_generic();
    }
    public void _constructor__int_generic()
    {
      final dart._runtime.types.simple.TypeEnvironment dart2java$localTypeEnv = this.dart2java$type.env;
      super._constructor();
    }
    public int getVarA__int_generic()
    {
      return this.varA;
    }
    public B getVarB__int_generic()
    {
      return this.varB;
    }
    public int setVarA__int_generic(int value)
    {
      this.varA = value;
      return value;
    }
    public B setVarB__int_generic(B value)
    {
      this.varB = value;
      return value;
    }
    public java.lang.Integer getVarA()
    {
      return this.getVarA__int_generic();
    }
    public B getVarB()
    {
      return this.getVarB__int_generic();
    }
    public java.lang.Integer setVarA(java.lang.Integer value)
    {
      return this.setVarA__int_generic(((int) value));
    }
    public B setVarB(B value)
    {
      return this.setVarB__int_generic(((B) value));
    }
    public java.lang.Integer bar(java.lang.Integer a, B b)
    {
      return this.bar__int_generic(((int) a), ((B) b));
    }
    public void _constructor()
    {
      this._constructor__int_generic();
    }
}
