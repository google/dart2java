package generics;

public class Bar2__generic_int<A> extends dart._runtime.base.DartObject implements generics.Bar2_interface__generic_int<A>
{
    public A varA;
    public int varB;
  
    public Bar2__generic_int(dart._runtime.helpers.ConstructorHelper.EmptyConstructorMarker arg, dart._runtime.types.simple.Type type)
    {
      super(arg, type);
    }
  
    public A bar__generic_int(A a, int b)
    {
      final dart._runtime.types.simple.TypeEnvironment dart2java$localTypeEnv = this.dart2java$type.env;
      return this.getVarA__generic_int();
    }
    public void _constructor__generic_int()
    {
      final dart._runtime.types.simple.TypeEnvironment dart2java$localTypeEnv = this.dart2java$type.env;
      super._constructor();
    }
    public A getVarA__generic_int()
    {
      return this.varA;
    }
    public int getVarB__generic_int()
    {
      return this.varB;
    }
    public A setVarA__generic_int(A value)
    {
      this.varA = value;
      return value;
    }
    public int setVarB__generic_int(int value)
    {
      this.varB = value;
      return value;
    }
    public A getVarA()
    {
      return this.getVarA__generic_int();
    }
    public java.lang.Integer getVarB()
    {
      return this.getVarB__generic_int();
    }
    public A setVarA(A value)
    {
      return this.setVarA__generic_int(((A) value));
    }
    public java.lang.Integer setVarB(java.lang.Integer value)
    {
      return this.setVarB__generic_int(((int) value));
    }
    public A bar(A a, java.lang.Integer b)
    {
      return this.bar__generic_int(((A) a), ((int) b));
    }
    public void _constructor()
    {
      this._constructor__generic_int();
    }
}
