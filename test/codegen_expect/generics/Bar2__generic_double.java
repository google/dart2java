package generics;

public class Bar2__generic_double<A> extends dart._runtime.base.DartObject implements generics.Bar2_interface__generic_double<A>
{
    public A varA;
    public double varB;
  
    public Bar2__generic_double(dart._runtime.helpers.ConstructorHelper.EmptyConstructorMarker arg, dart._runtime.types.simple.Type type)
    {
      super(arg, type);
    }
  
    public A bar__generic_double(A a, double b)
    {
      final dart._runtime.types.simple.TypeEnvironment dart2java$localTypeEnv = this.dart2java$type.env;
      return this.getVarA__generic_double();
    }
    public void _constructor__generic_double()
    {
      final dart._runtime.types.simple.TypeEnvironment dart2java$localTypeEnv = this.dart2java$type.env;
      super._constructor();
    }
    public A getVarA__generic_double()
    {
      return this.varA;
    }
    public double getVarB__generic_double()
    {
      return this.varB;
    }
    public A setVarA__generic_double(A value)
    {
      this.varA = value;
      return value;
    }
    public double setVarB__generic_double(double value)
    {
      this.varB = value;
      return value;
    }
    public A getVarA()
    {
      return this.getVarA__generic_double();
    }
    public java.lang.Double getVarB()
    {
      return this.getVarB__generic_double();
    }
    public A setVarA(A value)
    {
      return this.setVarA__generic_double(((A) value));
    }
    public java.lang.Double setVarB(java.lang.Double value)
    {
      return this.setVarB__generic_double(((double) value));
    }
    public A bar(A a, java.lang.Double b)
    {
      return this.bar__generic_double(((A) a), ((double) b));
    }
    public void _constructor()
    {
      this._constructor__generic_double();
    }
}
