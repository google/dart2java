package deltablue;

public class EqualityConstraint extends deltablue.BinaryConstraint implements deltablue.EqualityConstraint_interface
{
    public static dart._runtime.types.simple.InterfaceTypeInfo dart2java$typeInfo = new dart._runtime.types.simple.InterfaceTypeInfo(deltablue.EqualityConstraint.class, deltablue.EqualityConstraint_interface.class);
    private static dart._runtime.types.simple.InterfaceTypeExpr dart2java$typeExpr_BinaryConstraint = new dart._runtime.types.simple.InterfaceTypeExpr(deltablue.BinaryConstraint.dart2java$typeInfo);
    static {
      deltablue.EqualityConstraint.dart2java$typeInfo.superclass = dart2java$typeExpr_BinaryConstraint;
    }
  
    public EqualityConstraint(dart._runtime.types.simple.Type type, deltablue.Variable_interface v1, deltablue.Variable_interface v2, deltablue.Strength_interface strength)
    {
      super(((dart._runtime.helpers.ConstructorHelper.EmptyConstructorMarker) null), type);
      this._constructor(v1, v2, strength);
    }
    public EqualityConstraint(dart._runtime.helpers.ConstructorHelper.EmptyConstructorMarker arg, dart._runtime.types.simple.Type type)
    {
      super(arg, type);
    }
  
    protected void _constructor(deltablue.Variable_interface v1, deltablue.Variable_interface v2, deltablue.Strength_interface strength)
    {
      final dart._runtime.types.simple.TypeEnvironment dart2java$localTypeEnv = this.dart2java$type.env;
      super._constructor(v1, v2, strength);
    }
    public void execute()
    {
      final dart._runtime.types.simple.TypeEnvironment dart2java$localTypeEnv = this.dart2java$type.env;
      this.output().setValue(this.input().getValue());
    }
}
