package is_interfaces_test;

public class C extends dart._runtime.base.DartObject implements is_interfaces_test.C_interface, is_interfaces_test.B_interface
{
    public static dart._runtime.types.simple.InterfaceTypeInfo dart2java$typeInfo = new dart._runtime.types.simple.InterfaceTypeInfo(is_interfaces_test.C.class, is_interfaces_test.C_interface.class);
    private static dart._runtime.types.simple.InterfaceTypeExpr dart2java$typeExpr_Object = new dart._runtime.types.simple.InterfaceTypeExpr(dart._runtime.helpers.ObjectHelper.dart2java$typeInfo);
    private static dart._runtime.types.simple.InterfaceTypeExpr dart2java$typeExpr_B = new dart._runtime.types.simple.InterfaceTypeExpr(is_interfaces_test.B.dart2java$typeInfo);
    static {
      is_interfaces_test.C.dart2java$typeInfo.superclass = dart2java$typeExpr_Object;
      is_interfaces_test.C.dart2java$typeInfo.interfaces = new dart._runtime.types.simple.InterfaceTypeExpr[] {dart2java$typeExpr_B};
    }
  
    public C(dart._runtime.helpers.ConstructorHelper.EmptyConstructorMarker arg, dart._runtime.types.simple.Type type)
    {
      super(arg, type);
    }
  
    public void _constructor()
    {
      final dart._runtime.types.simple.TypeEnvironment dart2java$localTypeEnv = this.dart2java$type.env;
      super._constructor();
    }
    public static is_interfaces_test.C_interface _new(dart._runtime.types.simple.Type type)
    {
      is_interfaces_test.C result;
      result = new is_interfaces_test.C(((dart._runtime.helpers.ConstructorHelper.EmptyConstructorMarker) null), type);
      result._constructor();
      return result;
    }
}
