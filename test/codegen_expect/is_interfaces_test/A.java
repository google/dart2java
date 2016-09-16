package is_interfaces_test;

public class A extends dart._runtime.base.DartObject implements is_interfaces_test.A_interface
{
    public static dart._runtime.types.simple.InterfaceTypeInfo dart2java$typeInfo = new dart._runtime.types.simple.InterfaceTypeInfo(is_interfaces_test.A.class, is_interfaces_test.A_interface.class);
    private static dart._runtime.types.simple.InterfaceTypeExpr dart2java$typeExpr_Object = new dart._runtime.types.simple.InterfaceTypeExpr(dart._runtime.helpers.ObjectHelper.dart2java$typeInfo);
    static {
      is_interfaces_test.A.dart2java$typeInfo.superclass = dart2java$typeExpr_Object;
    }
  
    public A(dart._runtime.helpers.ConstructorHelper.EmptyConstructorMarker arg, dart._runtime.types.simple.Type type)
    {
      super(arg, type);
    }
  
    public void _constructor()
    {
      final dart._runtime.types.simple.TypeEnvironment dart2java$localTypeEnv = this.dart2java$type.env;
      super._constructor();
    }
    public static is_interfaces_test.A_interface _new(dart._runtime.types.simple.Type type)
    {
      is_interfaces_test.A_interface result;
      result = new is_interfaces_test.A(((dart._runtime.helpers.ConstructorHelper.EmptyConstructorMarker) null), type);
      result._constructor();
      return result;
    }
}
