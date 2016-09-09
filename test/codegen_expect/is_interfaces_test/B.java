package is_interfaces_test;

public class B extends is_interfaces_test.A implements is_interfaces_test.B_interface
{
    public static dart._runtime.types.simple.InterfaceTypeInfo dart2java$typeInfo = new dart._runtime.types.simple.InterfaceTypeInfo(is_interfaces_test.B.class, is_interfaces_test.B_interface.class);
    static {
      is_interfaces_test.B.dart2java$typeInfo.superclass = new dart._runtime.types.simple.InterfaceTypeExpr(is_interfaces_test.A.dart2java$typeInfo);
    }
  
    public B(dart._runtime.types.simple.Type type)
    {
      super(((dart._runtime.helpers.ConstructorHelper.EmptyConstructorMarker) null), type);
      this._constructor();
    }
    public B(dart._runtime.helpers.ConstructorHelper.EmptyConstructorMarker arg, dart._runtime.types.simple.Type type)
    {
      super(arg, type);
    }
  
    protected void _constructor()
    {
      final dart._runtime.types.simple.TypeEnvironment dart2java$localTypeEnv = this.dart2java$type.env;
      super._constructor();
    }
}
