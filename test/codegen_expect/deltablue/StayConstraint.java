package deltablue;

public class StayConstraint extends deltablue.UnaryConstraint implements deltablue.StayConstraint_interface
{
    public static dart._runtime.types.simple.InterfaceTypeInfo dart2java$typeInfo = new dart._runtime.types.simple.InterfaceTypeInfo(deltablue.StayConstraint.class, deltablue.StayConstraint_interface.class);
    private static dart._runtime.types.simple.InterfaceTypeExpr dart2java$typeExpr_UnaryConstraint = new dart._runtime.types.simple.InterfaceTypeExpr(deltablue.UnaryConstraint.dart2java$typeInfo);
    static {
      deltablue.StayConstraint.dart2java$typeInfo.superclass = dart2java$typeExpr_UnaryConstraint;
    }
  
    public StayConstraint(dart._runtime.helpers.ConstructorHelper.EmptyConstructorMarker arg, dart._runtime.types.simple.Type type)
    {
      super(arg, type);
    }
  
    public void execute()
    {
      final dart._runtime.types.simple.TypeEnvironment dart2java$localTypeEnv = this.dart2java$type.env;
    }
    public void _constructor(deltablue.Variable_interface v, deltablue.Strength_interface str)
    {
      final dart._runtime.types.simple.TypeEnvironment dart2java$localTypeEnv = this.dart2java$type.env;
      super._constructor(v, str);
    }
    public static deltablue.StayConstraint_interface _new(dart._runtime.types.simple.Type type, deltablue.Variable_interface v, deltablue.Strength_interface str)
    {
      deltablue.StayConstraint result;
      result = new deltablue.StayConstraint(((dart._runtime.helpers.ConstructorHelper.EmptyConstructorMarker) null), type);
      result._constructor(v, str);
      return result;
    }
}
