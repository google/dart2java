package deltablue;

public class StayConstraint extends deltablue.UnaryConstraint implements deltablue.StayConstraint_interface
{
    public static dart._runtime.types.simple.InterfaceTypeInfo dart2java$typeInfo = new dart._runtime.types.simple.InterfaceTypeInfo(deltablue.StayConstraint.class, deltablue.StayConstraint_interface.class);
    static {
      deltablue.StayConstraint.dart2java$typeInfo.superclass = new dart._runtime.types.simple.InterfaceTypeExpr(deltablue.UnaryConstraint.dart2java$typeInfo);
    }
  
    public StayConstraint(dart._runtime.types.simple.Type type, deltablue.Variable_interface v, deltablue.Strength_interface str)
    {
      super(((dart._runtime.helpers.ConstructorHelper.EmptyConstructorMarker) null), type);
      this._constructor(v, str);
    }
    public StayConstraint(dart._runtime.helpers.ConstructorHelper.EmptyConstructorMarker arg, dart._runtime.types.simple.Type type)
    {
      super(arg, type);
    }
  
    protected void _constructor(deltablue.Variable_interface v, deltablue.Strength_interface str)
    {
      final dart._runtime.types.simple.TypeEnvironment dart2java$localTypeEnv = this.dart2java$type.env;
      super._constructor(v, str);
    }
    public void execute()
    {
      final dart._runtime.types.simple.TypeEnvironment dart2java$localTypeEnv = this.dart2java$type.env;
    }
}
