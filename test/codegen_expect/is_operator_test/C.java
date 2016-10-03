package is_operator_test;

public class C extends is_operator_test.A implements is_operator_test.C_interface
{
    public static dart._runtime.types.simple.InterfaceTypeInfo dart2java$typeInfo = new dart._runtime.types.simple.InterfaceTypeInfo(is_operator_test.C.class, is_operator_test.C_interface.class);
    private static dart._runtime.types.simple.InterfaceTypeExpr dart2java$typeExpr_A = new dart._runtime.types.simple.InterfaceTypeExpr(is_operator_test.A.dart2java$typeInfo);
    static {
      is_operator_test.C.dart2java$typeInfo.superclass = dart2java$typeExpr_A;
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
    public static is_operator_test.C_interface _new(dart._runtime.types.simple.Type type)
    {
      is_operator_test.C result;
      result = new is_operator_test.C(((dart._runtime.helpers.ConstructorHelper.EmptyConstructorMarker) null), type);
      result._constructor();
      return result;
    }
}
