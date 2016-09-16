package generics;

public class Bar2__int_generic<B> extends dart._runtime.base.DartObject implements generics.Bar2_interface__int_generic<B>
{
  
  
    public Bar2__int_generic(dart._runtime.types.simple.Type type)
    {
      super(((dart._runtime.helpers.ConstructorHelper.EmptyConstructorMarker) null), type);
      this._constructor();
    }
    public Bar2__int_generic(dart._runtime.helpers.ConstructorHelper.EmptyConstructorMarker arg, dart._runtime.types.simple.Type type)
    {
      super(arg, type);
    }
  
    protected void _constructor()
    {
      final dart._runtime.types.simple.TypeEnvironment dart2java$localTypeEnv = this.dart2java$type.env;
      super._constructor();
    }
    public int bar__int_generic(int a, B b)
    {
      final dart._runtime.types.simple.TypeEnvironment dart2java$localTypeEnv = this.dart2java$type.env;
      return a;
    }
    public java.lang.Integer bar(java.lang.Integer a, B b)
    {
      return this.bar__int_generic(((int) a), ((B) b));
    }
}
