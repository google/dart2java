package deltablue;

public abstract class UnaryConstraint extends deltablue.Constraint
{
    public static dart._runtime.types.simple.InterfaceTypeInfo dart2java$typeInfo = new dart._runtime.types.simple.InterfaceTypeInfo("file:///usr/local/google/home/andrewkrieger/ddc-java/gen/codegen_tests/deltablue.dart", "UnaryConstraint");
    static {
      deltablue.UnaryConstraint.dart2java$typeInfo.superclass = new dart._runtime.types.simple.InterfaceTypeExpr(deltablue.Constraint.dart2java$typeInfo);
    }
    public deltablue.Variable myOutput;
    public java.lang.Boolean satisfied;
  
    public UnaryConstraint(dart._runtime.types.simple.Type type, deltablue.Variable myOutput, deltablue.Strength strength)
    {
      super((dart._runtime.helpers.ConstructorHelper.EmptyConstructorMarker) null, type);
      this._constructor(myOutput, strength);
    }
    public UnaryConstraint(dart._runtime.helpers.ConstructorHelper.EmptyConstructorMarker arg, dart._runtime.types.simple.Type type)
    {
      super(arg, type);
    }
  
    protected void _constructor(deltablue.Variable myOutput, deltablue.Strength strength)
    {
      final dart._runtime.types.simple.TypeEnvironment dart2java$localTypeEnv = this.dart2java$type.env;
      this.satisfied = false;
      this.myOutput = myOutput;
      super._constructor(strength);
      this.addConstraint();
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
    public java.lang.Boolean isSatisfied()
    {
      final dart._runtime.types.simple.TypeEnvironment dart2java$localTypeEnv = this.dart2java$type.env;
      return this.getSatisfied();
    }
    public void markInputs(int mark)
    {
      final dart._runtime.types.simple.TypeEnvironment dart2java$localTypeEnv = this.dart2java$type.env;
    }
    public deltablue.Variable output()
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
    public java.lang.Boolean inputsKnown(int mark)
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
    public deltablue.Variable getMyOutput()
    {
      return this.myOutput;
    }
    public java.lang.Boolean getSatisfied()
    {
      return this.satisfied;
    }
    public java.lang.Boolean setSatisfied(java.lang.Boolean value)
    {
      this.satisfied = value;
      return value;
    }
}
