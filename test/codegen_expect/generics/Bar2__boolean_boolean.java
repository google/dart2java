package generics;

public class Bar2__boolean_boolean extends dart._runtime.base.DartObject implements generics.Bar2_interface__boolean_boolean
{
  
  
    public Bar2__boolean_boolean(dart._runtime.types.simple.Type type)
    {
      super(((dart._runtime.helpers.ConstructorHelper.EmptyConstructorMarker) null), type);
      this._constructor();
    }
    public Bar2__boolean_boolean(dart._runtime.helpers.ConstructorHelper.EmptyConstructorMarker arg, dart._runtime.types.simple.Type type)
    {
      super(arg, type);
    }
  
    protected void _constructor()
    {
      final dart._runtime.types.simple.TypeEnvironment dart2java$localTypeEnv = this.dart2java$type.env;
      super._constructor();
    }
    public boolean bar__boolean_boolean(boolean a, boolean b)
    {
      final dart._runtime.types.simple.TypeEnvironment dart2java$localTypeEnv = this.dart2java$type.env;
      return a;
    }
    public java.lang.Boolean bar(java.lang.Boolean a, java.lang.Boolean b)
    {
      return this.bar__boolean_boolean(((boolean) a), ((boolean) b));
    }
    public boolean bar__boolean_generic(boolean a, java.lang.Boolean b)
    {
      return this.bar__boolean_boolean(((boolean) a), ((boolean) b));
    }
    public java.lang.Boolean bar__generic_boolean(java.lang.Boolean a, boolean b)
    {
      return this.bar__boolean_boolean(((boolean) a), ((boolean) b));
    }
}
