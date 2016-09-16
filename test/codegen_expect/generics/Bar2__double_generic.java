package generics;

public class Bar2__double_generic<B> extends dart._runtime.base.DartObject implements generics.Bar2_interface__double_generic<B>
{
  
  
    public Bar2__double_generic(dart._runtime.types.simple.Type type)
    {
      super(((dart._runtime.helpers.ConstructorHelper.EmptyConstructorMarker) null), type);
      this._constructor();
    }
    public Bar2__double_generic(dart._runtime.helpers.ConstructorHelper.EmptyConstructorMarker arg, dart._runtime.types.simple.Type type)
    {
      super(arg, type);
    }
  
    protected void _constructor()
    {
      final dart._runtime.types.simple.TypeEnvironment dart2java$localTypeEnv = this.dart2java$type.env;
      super._constructor();
    }
    public double bar__double_generic(double a, B b)
    {
      final dart._runtime.types.simple.TypeEnvironment dart2java$localTypeEnv = this.dart2java$type.env;
      return a;
    }
    public java.lang.Double bar(java.lang.Double a, B b)
    {
      return this.bar__double_generic(((double) a), ((B) b));
    }
}
