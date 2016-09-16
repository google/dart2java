package generics;

public class Bar2__generic_boolean<A> extends dart._runtime.base.DartObject implements generics.Bar2_interface__generic_boolean<A>
{
    public A varA;
    public boolean varB;
  
    public Bar2__generic_boolean(dart._runtime.helpers.ConstructorHelper.EmptyConstructorMarker arg, dart._runtime.types.simple.Type type)
    {
      super(arg, type);
    }
  
    public A bar__generic_boolean(A a, boolean b)
    {
      final dart._runtime.types.simple.TypeEnvironment dart2java$localTypeEnv = this.dart2java$type.env;
      return this.getVarA__generic_boolean();
    }
    public void _constructor__generic_boolean()
    {
      final dart._runtime.types.simple.TypeEnvironment dart2java$localTypeEnv = this.dart2java$type.env;
      super._constructor();
    }
    public A getVarA__generic_boolean()
    {
      return this.varA;
    }
    public boolean getVarB__generic_boolean()
    {
      return this.varB;
    }
    public A setVarA__generic_boolean(A value)
    {
      this.varA = value;
      return value;
    }
    public boolean setVarB__generic_boolean(boolean value)
    {
      this.varB = value;
      return value;
    }
    public A getVarA()
    {
      return this.getVarA__generic_boolean();
    }
    public java.lang.Boolean getVarB()
    {
      return this.getVarB__generic_boolean();
    }
    public A setVarA(A value)
    {
      return this.setVarA__generic_boolean(((A) value));
    }
    public java.lang.Boolean setVarB(java.lang.Boolean value)
    {
      return this.setVarB__generic_boolean(((boolean) value));
    }
    public A bar(A a, java.lang.Boolean b)
    {
      return this.bar__generic_boolean(((A) a), ((boolean) b));
    }
    public void _constructor()
    {
      this._constructor__generic_boolean();
    }
}
