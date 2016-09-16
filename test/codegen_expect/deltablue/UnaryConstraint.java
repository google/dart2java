package deltablue;

public abstract class UnaryConstraint extends deltablue.Constraint implements deltablue.UnaryConstraint_interface
{
    public static dart._runtime.types.simple.InterfaceTypeInfo dart2java$typeInfo = new dart._runtime.types.simple.InterfaceTypeInfo(deltablue.UnaryConstraint.class, deltablue.UnaryConstraint_interface.class);
    private static dart._runtime.types.simple.InterfaceTypeExpr dart2java$typeExpr_Constraint = new dart._runtime.types.simple.InterfaceTypeExpr(deltablue.Constraint.dart2java$typeInfo);
    static {
      deltablue.UnaryConstraint.dart2java$typeInfo.superclass = dart2java$typeExpr_Constraint;
    }
    public deltablue.Variable_interface myOutput;
    public boolean satisfied;
  
    public UnaryConstraint(dart._runtime.helpers.ConstructorHelper.EmptyConstructorMarker arg, dart._runtime.types.simple.Type type)
    {
      super(arg, type);
    }
  
    public void addToGraph()
    {
      final dart._runtime.types.simple.TypeEnvironment dart2java$localTypeEnv = this.dart2java$type.env;
      this.getMyOutput().addConstraint(this);
      this.setSatisfied(false);
    }
    public void chooseMethod(int mark)
    {
      final dart._runtime.types.simple.TypeEnvironment dart2java$localTypeEnv = this.dart2java$type.env;
      this.setSatisfied(((!(this.getMyOutput().getMark() == mark)) && deltablue.Strength.stronger(this.getStrength(), this.getMyOutput().getWalkStrength())));
    }
    public boolean isSatisfied()
    {
      final dart._runtime.types.simple.TypeEnvironment dart2java$localTypeEnv = this.dart2java$type.env;
      return this.getSatisfied();
    }
    public void markInputs(int mark)
    {
      final dart._runtime.types.simple.TypeEnvironment dart2java$localTypeEnv = this.dart2java$type.env;
    }
    public deltablue.Variable_interface output()
    {
      final dart._runtime.types.simple.TypeEnvironment dart2java$localTypeEnv = this.dart2java$type.env;
      return this.getMyOutput();
    }
    public void recalculate()
    {
      final dart._runtime.types.simple.TypeEnvironment dart2java$localTypeEnv = this.dart2java$type.env;
      this.getMyOutput().setWalkStrength(this.getStrength());
      this.getMyOutput().setStay((!this.isInput()));
      if (this.getMyOutput().getStay())
      {
        this.execute();
      }
    }
    public void markUnsatisfied()
    {
      final dart._runtime.types.simple.TypeEnvironment dart2java$localTypeEnv = this.dart2java$type.env;
      this.setSatisfied(false);
    }
    public boolean inputsKnown(int mark)
    {
      final dart._runtime.types.simple.TypeEnvironment dart2java$localTypeEnv = this.dart2java$type.env;
      return true;
    }
    public void removeFromGraph()
    {
      final dart._runtime.types.simple.TypeEnvironment dart2java$localTypeEnv = this.dart2java$type.env;
      if ((!dart._runtime.helpers.ObjectHelper.operatorEqual(this.getMyOutput(), null)))
      {
        this.getMyOutput().removeConstraint(this);
      }
      this.setSatisfied(false);
    }
    public void _constructor(deltablue.Variable_interface myOutput, deltablue.Strength_interface strength)
    {
      final dart._runtime.types.simple.TypeEnvironment dart2java$localTypeEnv = this.dart2java$type.env;
      this.satisfied = false;
      this.myOutput = myOutput;
      super._constructor(strength);
      this.addConstraint();
    }
    public deltablue.Variable_interface getMyOutput()
    {
      return this.myOutput;
    }
    public boolean getSatisfied()
    {
      return this.satisfied;
    }
    public boolean setSatisfied(boolean value)
    {
      this.satisfied = value;
      return value;
    }
}
