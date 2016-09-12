package generics;

public class Qux3<C, D, E> extends dart._runtime.base.DartObject implements generics.Qux3_interface<C, D, E>
{
    public static dart._runtime.types.simple.InterfaceTypeInfo dart2java$typeInfo = new dart._runtime.types.simple.InterfaceTypeInfo(new java.lang.String[] {"C", "D", "E"}, generics.Qux3.class, generics.Qux3_interface.class);
    private static dart._runtime.types.simple.InterfaceTypeExpr dart2java$typeExpr_Object = new dart._runtime.types.simple.InterfaceTypeExpr(dart._runtime.helpers.ObjectHelper.dart2java$typeInfo);
    static {
      generics.Qux3.dart2java$typeInfo.superclass = dart2java$typeExpr_Object;
    }
  
    public Qux3(dart._runtime.types.simple.Type type)
    {
      super(((dart._runtime.helpers.ConstructorHelper.EmptyConstructorMarker) null), type);
      this._constructor();
    }
    public Qux3(dart._runtime.helpers.ConstructorHelper.EmptyConstructorMarker arg, dart._runtime.types.simple.Type type)
    {
      super(arg, type);
    }
  
    protected void _constructor()
    {
      final dart._runtime.types.simple.TypeEnvironment dart2java$localTypeEnv = this.dart2java$type.env;
      super._constructor();
    }
    public int test()
    {
      final dart._runtime.types.simple.TypeEnvironment dart2java$localTypeEnv = this.dart2java$type.env;
      return 3;
    }
}
