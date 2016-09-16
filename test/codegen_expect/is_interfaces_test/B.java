package is_interfaces_test;

public class B extends is_interfaces_test.A implements is_interfaces_test.B_interface
{
    public static dart._runtime.types.simple.InterfaceTypeInfo dart2java$typeInfo = new dart._runtime.types.simple.InterfaceTypeInfo(is_interfaces_test.B.class, is_interfaces_test.B_interface.class);
    private static dart._runtime.types.simple.InterfaceTypeExpr dart2java$typeExpr_A = new dart._runtime.types.simple.InterfaceTypeExpr(is_interfaces_test.A.dart2java$typeInfo);
    static {
      is_interfaces_test.B.dart2java$typeInfo.superclass = dart2java$typeExpr_A;
    }
  
    public B(dart._runtime.helpers.ConstructorHelper.EmptyConstructorMarker arg, dart._runtime.types.simple.Type type)
    {
      super(arg, type);
    }
  
    public void _constructor()
    {
      final dart._runtime.types.simple.TypeEnvironment dart2java$localTypeEnv = this.dart2java$type.env;
      super._constructor();
    }
    public static is_interfaces_test.B_interface _new(dart._runtime.types.simple.Type type)
    {
      is_interfaces_test.B_interface result;
      result = new is_interfaces_test.B(((dart._runtime.helpers.ConstructorHelper.EmptyConstructorMarker) null), type);
      result._constructor();
      return result;
    }
}
