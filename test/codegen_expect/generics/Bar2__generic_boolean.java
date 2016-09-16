package generics;

public class Bar2__generic_boolean<A> extends dart._runtime.base.DartObject implements generics.Bar2_interface__generic_boolean<A>
{
  
  
    public Bar2__generic_boolean(dart._runtime.types.simple.Type type)
    {
      super(((dart._runtime.helpers.ConstructorHelper.EmptyConstructorMarker) null), type);
      this._constructor();
    }
    public Bar2__generic_boolean(dart._runtime.helpers.ConstructorHelper.EmptyConstructorMarker arg, dart._runtime.types.simple.Type type)
    {
      super(arg, type);
    }
  
    protected void _constructor()
    {
      final dart._runtime.types.simple.TypeEnvironment dart2java$localTypeEnv = this.dart2java$type.env;
      super._constructor();
    }
    public A bar__generic_boolean(A a, boolean b)
    {
      final dart._runtime.types.simple.TypeEnvironment dart2java$localTypeEnv = this.dart2java$type.env;
      return a;
    }
    public A bar(A a, java.lang.Boolean b)
    {
      return this.bar__generic_boolean(((A) a), ((boolean) b));
    }
}
