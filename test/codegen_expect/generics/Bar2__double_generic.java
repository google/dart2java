package generics;

public class Bar2__double_generic<B> extends dart._runtime.base.DartObject implements generics.Bar2_interface__double_generic<B>
{
    public double varA;
    public B varB;
  
    public Bar2__double_generic(dart._runtime.helpers.ConstructorHelper.EmptyConstructorMarker arg, dart._runtime.types.simple.Type type)
    {
      super(arg, type);
    }
  
    public double bar__double_generic(double a, B b)
    {
      final dart._runtime.types.simple.TypeEnvironment dart2java$localTypeEnv = this.dart2java$type.env;
      return this.getVarA__double_generic();
    }
    public void _constructor__double_generic()
    {
      final dart._runtime.types.simple.TypeEnvironment dart2java$localTypeEnv = this.dart2java$type.env;
      super._constructor();
    }
    public double getVarA__double_generic()
    {
      return this.varA;
    }
    public B getVarB__double_generic()
    {
      return this.varB;
    }
    public double setVarA__double_generic(double value)
    {
      this.varA = value;
      return value;
    }
    public B setVarB__double_generic(B value)
    {
      this.varB = value;
      return value;
    }
    public java.lang.Double getVarA()
    {
      return this.getVarA__double_generic();
    }
    public B getVarB()
    {
      return this.getVarB__double_generic();
    }
    public java.lang.Double setVarA(java.lang.Double value)
    {
      return this.setVarA__double_generic(((double) value));
    }
    public B setVarB(B value)
    {
      return this.setVarB__double_generic(((B) value));
    }
    public java.lang.Double bar(java.lang.Double a, B b)
    {
      return this.bar__double_generic(((double) a), ((B) b));
    }
    public void _constructor()
    {
      this._constructor__double_generic();
    }
}
