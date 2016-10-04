package generics;

public class Qux3<C, D, E> extends dart._runtime.base.DartObject implements generics.Qux3_interface<C, D, E>
{
    public static dart._runtime.types.simple.InterfaceTypeInfo dart2java$typeInfo = new dart._runtime.types.simple.InterfaceTypeInfo(new java.lang.String[] {"C", "D", "E"}, generics.Qux3.class, generics.Qux3_interface.class);
    private static dart._runtime.types.simple.InterfaceTypeExpr dart2java$typeExpr_Object = new dart._runtime.types.simple.InterfaceTypeExpr(dart._runtime.helpers.ObjectHelper.dart2java$typeInfo);
    static {
      generics.Qux3.dart2java$typeInfo.superclass = dart2java$typeExpr_Object;
    }
  
    public Qux3(dart._runtime.helpers.ConstructorHelper.EmptyConstructorMarker arg, dart._runtime.types.simple.Type type)
    {
      super(arg, type);
    }
  
    public C qux_Qux3(C c, D d, E e)
    {
      final dart._runtime.types.simple.TypeEnvironment dart2java$localTypeEnv = this.dart2java$type.env;
      return c;
    }
    public void _constructor()
    {
      final dart._runtime.types.simple.TypeEnvironment dart2java$localTypeEnv = this.dart2java$type.env;
      super._constructor();
    }
    public C qux(C c, D d, E e)
    {
      return this.qux_Qux3(((C) c), ((D) d), ((E) e));
    }
    public static <C, D, E> generics.Qux3_interface _new_Qux3$(dart._runtime.types.simple.Type type)
    {
      dart._runtime.types.simple.Type cached_0_int = null;
      dart._runtime.types.simple.Type cached_0_boolean = null;
      dart._runtime.types.simple.Type cached_0_double = null;
      dart._runtime.types.simple.Type cached_1_int = null;
      dart._runtime.types.simple.Type cached_1_boolean = null;
      dart._runtime.types.simple.Type cached_1_double = null;
      dart._runtime.types.simple.Type cached_2_int = null;
      dart._runtime.types.simple.Type cached_2_boolean = null;
      dart._runtime.types.simple.Type cached_2_double = null;
      generics.Qux3 result;
      result = new generics.Qux3(((dart._runtime.helpers.ConstructorHelper.EmptyConstructorMarker) null), type);
      result._constructor();
      return result;
    }
}
