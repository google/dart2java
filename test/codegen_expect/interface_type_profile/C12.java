package interface_type_profile;

public class C12 extends dart._runtime.base.DartObject implements interface_type_profile.C12_interface, interface_type_profile.B12_interface, interface_type_profile.B13_interface
{
    public static dart._runtime.types.simple.InterfaceTypeInfo dart2java$typeInfo = new dart._runtime.types.simple.InterfaceTypeInfo(interface_type_profile.C12.class, interface_type_profile.C12_interface.class);
    private static dart._runtime.types.simple.InterfaceTypeExpr dart2java$typeExpr_Object = new dart._runtime.types.simple.InterfaceTypeExpr(dart._runtime.helpers.ObjectHelper.dart2java$typeInfo);
    private static dart._runtime.types.simple.InterfaceTypeExpr dart2java$typeExpr_B12 = new dart._runtime.types.simple.InterfaceTypeExpr(interface_type_profile.B12.dart2java$typeInfo);
    private static dart._runtime.types.simple.InterfaceTypeExpr dart2java$typeExpr_B13 = new dart._runtime.types.simple.InterfaceTypeExpr(interface_type_profile.B13.dart2java$typeInfo);
    static {
      interface_type_profile.C12.dart2java$typeInfo.superclass = dart2java$typeExpr_Object;
      interface_type_profile.C12.dart2java$typeInfo.interfaces = new dart._runtime.types.simple.InterfaceTypeExpr[] {dart2java$typeExpr_B12, dart2java$typeExpr_B13};
    }
  
    public C12(dart._runtime.helpers.ConstructorHelper.EmptyConstructorMarker arg, dart._runtime.types.simple.Type type)
    {
      super(arg, type);
    }
  
    public void _constructor()
    {
      final dart._runtime.types.simple.TypeEnvironment dart2java$localTypeEnv = this.dart2java$type.env;
      super._constructor();
    }
    public static interface_type_profile.C12_interface _new(dart._runtime.types.simple.Type type)
    {
      interface_type_profile.C12 result;
      result = new interface_type_profile.C12(((dart._runtime.helpers.ConstructorHelper.EmptyConstructorMarker) null), type);
      result._constructor();
      return result;
    }
}
