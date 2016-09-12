package deltablue;

public abstract class Constraint extends dart._runtime.base.DartObject implements deltablue.Constraint_interface
{
    public static dart._runtime.types.simple.InterfaceTypeInfo dart2java$typeInfo = new dart._runtime.types.simple.InterfaceTypeInfo(deltablue.Constraint.class, deltablue.Constraint_interface.class);
    private static dart._runtime.types.simple.InterfaceTypeExpr dart2java$typeExpr_Object = new dart._runtime.types.simple.InterfaceTypeExpr(dart._runtime.helpers.ObjectHelper.dart2java$typeInfo);
    static {
      deltablue.Constraint.dart2java$typeInfo.superclass = dart2java$typeExpr_Object;
    }
    public deltablue.Strength_interface strength;
  
    public Constraint(dart._runtime.types.simple.Type type, deltablue.Strength_interface strength)
    {
      super(((dart._runtime.helpers.ConstructorHelper.EmptyConstructorMarker) null), type);
      this._constructor(strength);
    }
    public Constraint(dart._runtime.helpers.ConstructorHelper.EmptyConstructorMarker arg, dart._runtime.types.simple.Type type)
    {
      super(arg, type);
    }
  
    protected void _constructor(deltablue.Strength_interface strength)
    {
      final dart._runtime.types.simple.TypeEnvironment dart2java$localTypeEnv = this.dart2java$type.env;
      this.strength = strength;
      super._constructor();
    }
    public abstract boolean isSatisfied();
    public abstract void markUnsatisfied();
    public abstract void addToGraph();
    public abstract void removeFromGraph();
    public abstract void chooseMethod(int mark);
    public abstract void markInputs(int mark);
    public abstract boolean inputsKnown(int mark);
    public abstract deltablue.Variable_interface output();
    public abstract void execute();
    public abstract void recalculate();
    public void addConstraint()
    {
      final dart._runtime.types.simple.TypeEnvironment dart2java$localTypeEnv = this.dart2java$type.env;
      this.addToGraph();
      deltablue.__TopLevel.planner.incrementalAdd(this);
    }
    public deltablue.Constraint_interface satisfy(int mark)
    {
      final dart._runtime.types.simple.TypeEnvironment dart2java$localTypeEnv = this.dart2java$type.env;
      this.chooseMethod(mark);
      if ((!this.isSatisfied()))
      {
        if (dart._runtime.helpers.ObjectHelper.operatorEqual(this.getStrength(), deltablue.__TopLevel.REQUIRED))
        {
          dart.core.__TopLevel.print("Could not satisfy a required constraint!");
        }
        return null;
      }
      this.markInputs(mark);
      deltablue.Variable_interface out = this.output();
      deltablue.Constraint_interface overridden = out.getDeterminedBy();
      if ((!dart._runtime.helpers.ObjectHelper.operatorEqual(overridden, null)))
      {
        overridden.markUnsatisfied();
      }
      out.setDeterminedBy(this);
      if ((!deltablue.__TopLevel.planner.addPropagate(this, mark)))
      {
        dart.core.__TopLevel.print("Cycle encountered");
      }
      out.setMark(mark);
      return overridden;
    }
    public void destroyConstraint()
    {
      final dart._runtime.types.simple.TypeEnvironment dart2java$localTypeEnv = this.dart2java$type.env;
      if (this.isSatisfied())
      {
        deltablue.__TopLevel.planner.incrementalRemove(this);
      }
      this.removeFromGraph();
    }
    public boolean isInput()
    {
      final dart._runtime.types.simple.TypeEnvironment dart2java$localTypeEnv = this.dart2java$type.env;
      return false;
    }
    public deltablue.Strength_interface getStrength()
    {
      return this.strength;
    }
}
