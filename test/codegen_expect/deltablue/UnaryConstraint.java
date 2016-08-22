package deltablue;

public abstract class UnaryConstraint extends deltablue.Constraint
{
    public static dart._runtime.types.simple.InterfaceTypeInfo dart2java$typeInfo = new dart._runtime.types.simple.InterfaceTypeInfo("file:///usr/local/google/home/andrewkrieger/ddc-java/gen/codegen_tests/deltablue.dart", "UnaryConstraint");
    static {
      deltablue.UnaryConstraint.dart2java$typeInfo.superclass = new dart._runtime.types.simple.InterfaceTypeExpr(deltablue.Constraint.dart2java$typeInfo);
    }
    public deltablue.Variable myOutput = null;
    public java.lang.Boolean satisfied = null;
  
    public UnaryConstraint(deltablue.Variable myOutput, deltablue.Strength strength)
    {
      super((dart._runtime.helpers.ConstructorHelper.EmptyConstructorMarker) null);
      this._constructor(myOutput, strength);
    }
    public UnaryConstraint(dart._runtime.helpers.ConstructorHelper.EmptyConstructorMarker arg)
    {
      super(arg);
    }
  
    protected void _constructor(deltablue.Variable myOutput, deltablue.Strength strength)
    {
      this.satisfied = false;
      this.myOutput = myOutput;
      super._constructor(strength);
      this.addConstraint();
    }
    public void addToGraph()
    {
      this.getMyOutput().addConstraint(this);
      this.setSatisfied(false);
    }
    public void chooseMethod(java.lang.Integer mark)
    {
      this.setSatisfied(((!dart._runtime.helpers.ObjectHelper.operatorEqual(this.getMyOutput().getMark(), mark)) && deltablue.Strength.stronger(this.getStrength(), this.getMyOutput().getWalkStrength())));
    }
    public java.lang.Boolean isSatisfied()
    {
      return this.getSatisfied();
    }
    public void markInputs(java.lang.Integer mark)
    {
      
    }
    public deltablue.Variable output()
    {
      return this.getMyOutput();
    }
    public void recalculate()
    {
      this.getMyOutput().setWalkStrength(this.getStrength());
      this.getMyOutput().setStay((!this.isInput()));
      if (this.getMyOutput().getStay())
      {
        this.execute();
      }
    }
    public void markUnsatisfied()
    {
      this.setSatisfied(false);
    }
    public java.lang.Boolean inputsKnown(java.lang.Integer mark)
    {
      return true;
    }
    public void removeFromGraph()
    {
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
