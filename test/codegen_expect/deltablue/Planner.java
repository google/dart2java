package deltablue;

public class Planner extends dart._runtime.base.DartObject
{
    public static dart._runtime.types.simple.InterfaceTypeInfo dart2java$typeInfo = new dart._runtime.types.simple.InterfaceTypeInfo("file:///usr/local/google/home/stanm/f/d/ddc-java/gen/codegen_tests/deltablue.dart", "Planner");
    static {
      deltablue.Planner.dart2java$typeInfo.superclass = new dart._runtime.types.simple.InterfaceTypeExpr(dart._runtime.helpers.ObjectHelper.dart2java$typeInfo);
    }
    public int currentMark;
  
    public Planner()
    {
      super((dart._runtime.helpers.ConstructorHelper.EmptyConstructorMarker) null);
      this._constructor();
    }
    public Planner(dart._runtime.helpers.ConstructorHelper.EmptyConstructorMarker arg)
    {
      super(arg);
    }
  
    protected void _constructor()
    {
      this.currentMark = 0;
      super._constructor();
    }
    public void incrementalAdd(deltablue.Constraint c)
    {
      int mark = this.newMark();
      for (deltablue.Constraint overridden = c.satisfy(mark); (!dart._runtime.helpers.ObjectHelper.operatorEqual(overridden, null)); overridden = overridden.satisfy(mark))
      {
        
      }
    }
    public void incrementalRemove(deltablue.Constraint c)
    {
      deltablue.Variable out = c.output();
      c.markUnsatisfied();
      c.removeFromGraph();
      dart._runtime.base.DartList<deltablue.Constraint> unsatisfied = (dart._runtime.base.DartList) this.removePropagateFrom(out);
      deltablue.Strength strength = deltablue.__TopLevel.REQUIRED;
      do
      {
        for (int i = 0; dart._runtime.helpers.IntegerHelper.operatorLess(i, unsatisfied.getLength()); i = dart._runtime.helpers.IntegerHelper.operatorPlus(i, 1))
        {
          deltablue.Constraint u = unsatisfied.operatorAt(i);
          if (dart._runtime.helpers.ObjectHelper.operatorEqual(u.getStrength(), strength))
          {
            this.incrementalAdd(u);
          }
        }
        strength = strength.nextWeaker();
      }
      while ((!dart._runtime.helpers.ObjectHelper.operatorEqual(strength, deltablue.__TopLevel.WEAKEST)));
    }
    public int newMark()
    {
      deltablue.Planner __tempVar_0;
      return dart._runtime.helpers.LetExpressionHelper.comma(__tempVar_0 = this, __tempVar_0.setCurrentMark(dart._runtime.helpers.IntegerHelper.operatorPlus(__tempVar_0.getCurrentMark(), 1)));
    }
    public deltablue.Plan makePlan(dart._runtime.base.DartList<deltablue.Constraint> sources)
    {
      int mark = this.newMark();
      deltablue.Plan plan = new deltablue.Plan();
      dart._runtime.base.DartList<deltablue.Constraint> todo = (dart._runtime.base.DartList) sources;
      while (dart._runtime.helpers.IntegerHelper.operatorGreater(todo.getLength(), 0))
      {
        deltablue.Constraint c = todo.removeLast();
        if (((!dart._runtime.helpers.ObjectHelper.operatorEqual(c.output().getMark(), mark)) && c.inputsKnown(mark)))
        {
          plan.addConstraint(c);
          c.output().setMark(mark);
          this.addConstraintsConsumingTo(c.output(), (dart._runtime.base.DartList) todo);
        }
      }
      return plan;
    }
    public deltablue.Plan extractPlanFromConstraints(dart._runtime.base.DartList<deltablue.Constraint> constraints)
    {
      dart._runtime.base.DartList<deltablue.Constraint> sources = (dart._runtime.base.DartList) dart._runtime.base.DartList.Generic._fromArguments(deltablue.Constraint.class);
      for (int i = 0; dart._runtime.helpers.IntegerHelper.operatorLess(i, constraints.getLength()); i = dart._runtime.helpers.IntegerHelper.operatorPlus(i, 1))
      {
        deltablue.Constraint c = constraints.operatorAt(i);
        if ((c.isInput() && c.isSatisfied()))
        {
          sources.add(c);
        }
      }
      return this.makePlan((dart._runtime.base.DartList) sources);
    }
    public java.lang.Boolean addPropagate(deltablue.Constraint c, int mark)
    {
      dart._runtime.base.DartList<deltablue.Constraint> todo = (dart._runtime.base.DartList) dart._runtime.base.DartList.Generic._fromArguments(deltablue.Constraint.class, c);
      while (dart._runtime.helpers.IntegerHelper.operatorGreater(todo.getLength(), 0))
      {
        deltablue.Constraint d = todo.removeLast();
        if (dart._runtime.helpers.ObjectHelper.operatorEqual(d.output().getMark(), mark))
        {
          this.incrementalRemove(c);
          return false;
        }
        d.recalculate();
        this.addConstraintsConsumingTo(d.output(), (dart._runtime.base.DartList) todo);
      }
      return true;
    }
    public dart._runtime.base.DartList<deltablue.Constraint> removePropagateFrom(deltablue.Variable out)
    {
      out.setDeterminedBy(null);
      out.setWalkStrength(deltablue.__TopLevel.WEAKEST);
      out.setStay(true);
      dart._runtime.base.DartList<deltablue.Constraint> unsatisfied = (dart._runtime.base.DartList) dart._runtime.base.DartList.Generic._fromArguments(deltablue.Constraint.class);
      dart._runtime.base.DartList<deltablue.Variable> todo = (dart._runtime.base.DartList) dart._runtime.base.DartList.Generic._fromArguments(deltablue.Variable.class, out);
      while (dart._runtime.helpers.IntegerHelper.operatorGreater(todo.getLength(), 0))
      {
        deltablue.Variable v = todo.removeLast();
        for (int i = 0; dart._runtime.helpers.IntegerHelper.operatorLess(i, v.getConstraints().getLength()); i = dart._runtime.helpers.IntegerHelper.operatorPlus(i, 1))
        {
          deltablue.Constraint c = v.getConstraints().operatorAt(i);
          if ((!c.isSatisfied()))
          {
            unsatisfied.add(c);
          }
        }
        deltablue.Constraint determining = v.getDeterminedBy();
        for (int i = 0; dart._runtime.helpers.IntegerHelper.operatorLess(i, v.getConstraints().getLength()); i = dart._runtime.helpers.IntegerHelper.operatorPlus(i, 1))
        {
          deltablue.Constraint next = v.getConstraints().operatorAt(i);
          if (((!dart._runtime.helpers.ObjectHelper.operatorEqual(next, determining)) && next.isSatisfied()))
          {
            next.recalculate();
            todo.add(next.output());
          }
        }
      }
      return (dart._runtime.base.DartList) unsatisfied;
    }
    public void addConstraintsConsumingTo(deltablue.Variable v, dart._runtime.base.DartList<deltablue.Constraint> coll)
    {
      deltablue.Constraint determining = v.getDeterminedBy();
      for (int i = 0; dart._runtime.helpers.IntegerHelper.operatorLess(i, v.getConstraints().getLength()); i = dart._runtime.helpers.IntegerHelper.operatorPlus(i, 1))
      {
        deltablue.Constraint c = v.getConstraints().operatorAt(i);
        if (((!dart._runtime.helpers.ObjectHelper.operatorEqual(c, determining)) && c.isSatisfied()))
        {
          coll.add(c);
        }
      }
    }
    public int getCurrentMark()
    {
      return this.currentMark;
    }
    public int setCurrentMark(int value)
    {
      this.currentMark = value;
      return value;
    }
}
