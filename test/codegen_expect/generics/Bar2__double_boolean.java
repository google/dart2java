package generics;

public class Bar2__double_boolean extends dart._runtime.base.DartObject implements generics.Bar2_interface__double_boolean
{
  
  
    public Bar2__double_boolean(dart._runtime.types.simple.Type type)
    {
      super(((dart._runtime.helpers.ConstructorHelper.EmptyConstructorMarker) null), type);
      this._constructor();
    }
    public Bar2__double_boolean(dart._runtime.helpers.ConstructorHelper.EmptyConstructorMarker arg, dart._runtime.types.simple.Type type)
    {
      super(arg, type);
    }
  
    protected void _constructor()
    {
      final dart._runtime.types.simple.TypeEnvironment dart2java$localTypeEnv = this.dart2java$type.env;
      super._constructor();
    }
    public double bar__double_boolean(double a, boolean b)
    {
      final dart._runtime.types.simple.TypeEnvironment dart2java$localTypeEnv = this.dart2java$type.env;
      return a;
    }
    public java.lang.Double bar(java.lang.Double a, java.lang.Boolean b)
    {
      return this.bar__double_boolean(((double) a), ((boolean) b));
    }
    public double bar__double_generic(double a, java.lang.Boolean b)
    {
      return this.bar__double_boolean(((double) a), ((boolean) b));
    }
    public java.lang.Double bar__generic_boolean(java.lang.Double a, boolean b)
    {
      return this.bar__double_boolean(((double) a), ((boolean) b));
    }
}
