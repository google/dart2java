package generics;

public class Bar2__double_double extends dart._runtime.base.DartObject implements generics.Bar2_interface__double_double
{
  
  
    public Bar2__double_double(dart._runtime.types.simple.Type type)
    {
      super(((dart._runtime.helpers.ConstructorHelper.EmptyConstructorMarker) null), type);
      this._constructor();
    }
    public Bar2__double_double(dart._runtime.helpers.ConstructorHelper.EmptyConstructorMarker arg, dart._runtime.types.simple.Type type)
    {
      super(arg, type);
    }
  
    protected void _constructor()
    {
      final dart._runtime.types.simple.TypeEnvironment dart2java$localTypeEnv = this.dart2java$type.env;
      super._constructor();
    }
    public double bar__double_double(double a, double b)
    {
      final dart._runtime.types.simple.TypeEnvironment dart2java$localTypeEnv = this.dart2java$type.env;
      return a;
    }
    public java.lang.Double bar(java.lang.Double a, java.lang.Double b)
    {
      return this.bar__double_double(((double) a), ((double) b));
    }
    public double bar__double_generic(double a, java.lang.Double b)
    {
      return this.bar__double_double(((double) a), ((double) b));
    }
    public java.lang.Double bar__generic_double(java.lang.Double a, double b)
    {
      return this.bar__double_double(((double) a), ((double) b));
    }
}
