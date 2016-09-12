package generics;

public class Foo<T> extends dart._runtime.base.DartObject implements generics.Foo_interface<T>
{
    public static dart._runtime.types.simple.InterfaceTypeInfo dart2java$typeInfo = new dart._runtime.types.simple.InterfaceTypeInfo(new java.lang.String[] {"T"}, generics.Foo.class, generics.Foo_interface.class);
    private static dart._runtime.types.simple.InterfaceTypeExpr dart2java$typeExpr_Object = new dart._runtime.types.simple.InterfaceTypeExpr(dart._runtime.helpers.ObjectHelper.dart2java$typeInfo);
    static {
      generics.Foo.dart2java$typeInfo.superclass = dart2java$typeExpr_Object;
    }
  
    public Foo(dart._runtime.types.simple.Type type)
    {
      super(((dart._runtime.helpers.ConstructorHelper.EmptyConstructorMarker) null), type);
      this._constructor();
    }
    public Foo(dart._runtime.helpers.ConstructorHelper.EmptyConstructorMarker arg, dart._runtime.types.simple.Type type)
    {
      super(arg, type);
    }
  
    protected void _constructor()
    {
      final dart._runtime.types.simple.TypeEnvironment dart2java$localTypeEnv = this.dart2java$type.env;
      super._constructor();
    }
    public int test1()
    {
      final dart._runtime.types.simple.TypeEnvironment dart2java$localTypeEnv = this.dart2java$type.env;
      return 1;
    }
}
