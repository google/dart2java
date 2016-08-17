package deltablue;

public class __TopLevel
{
    public static deltablue.Strength REQUIRED = new deltablue.Strength(0, "required");
    public static deltablue.Strength STRONG_PREFERRED = new deltablue.Strength(1, "strongPreferred");
    public static deltablue.Strength PREFERRED = new deltablue.Strength(2, "preferred");
    public static deltablue.Strength STRONG_DEFAULT = new deltablue.Strength(3, "strongDefault");
    public static deltablue.Strength NORMAL = new deltablue.Strength(4, "normal");
    public static deltablue.Strength WEAK_DEFAULT = new deltablue.Strength(5, "weakDefault");
    public static deltablue.Strength WEAKEST = new deltablue.Strength(6, "weakest");
    public static java.lang.Integer NONE = 1;
    public static java.lang.Integer FORWARD = 2;
    public static java.lang.Integer BACKWARD = 0;
    public static deltablue.Planner planner;
  
  
  
    public static void main(String[] args)
    {
      dart.core.__TopLevel.print("Running benchmark...");
      new deltablue.DeltaBlue().report();
      dart.core.__TopLevel.print("Done.");
    }
    public static void chainTest(java.lang.Integer n)
    {
      deltablue.__TopLevel.planner = new deltablue.Planner();
      deltablue.Variable prev = null;
      deltablue.Variable first = null;
      deltablue.Variable last = null;
      for (java.lang.Integer i = 0; dart._runtime.helpers.IntegerHelper.operatorLessEqual(i, n); i = dart._runtime.helpers.IntegerHelper.operatorPlus(i, 1))
      {
        deltablue.Variable v = new deltablue.Variable("v", 0);
        if ((!dart._runtime.helpers.ObjectHelper.operatorEqual(prev, null)))
        {
          new deltablue.EqualityConstraint(prev, v, deltablue.__TopLevel.REQUIRED);
        }
        if (dart._runtime.helpers.ObjectHelper.operatorEqual(i, 0))
        {
          first = v;
        }
        if (dart._runtime.helpers.ObjectHelper.operatorEqual(i, n))
        {
          last = v;
        }
        prev = v;
      }
      new deltablue.StayConstraint(last, deltablue.__TopLevel.STRONG_DEFAULT);
      deltablue.EditConstraint edit = new deltablue.EditConstraint(first, deltablue.__TopLevel.PREFERRED);
      deltablue.Plan plan = deltablue.__TopLevel.planner.extractPlanFromConstraints((dart._runtime.base.DartList) dart._runtime.base.DartList.Generic._fromArguments(deltablue.Constraint.class, edit));
      for (java.lang.Integer i = 0; dart._runtime.helpers.IntegerHelper.operatorLess(i, 100); i = dart._runtime.helpers.IntegerHelper.operatorPlus(i, 1))
      {
        first.setValue(i);
        plan.execute();
        if ((!dart._runtime.helpers.ObjectHelper.operatorEqual(last.getValue(), i)))
        {
          dart.core.__TopLevel.print("Chain test failed:");
          dart.core.__TopLevel.print((((("Expected last value to be ".toString() + i.toString()) + " but it was ".toString()) + last.getValue().toString()) + ".".toString()));
        }
      }
    }
    public static void projectionTest(java.lang.Integer n)
    {
      deltablue.__TopLevel.planner = new deltablue.Planner();
      deltablue.Variable scale = new deltablue.Variable("scale", 10);
      deltablue.Variable offset = new deltablue.Variable("offset", 1000);
      deltablue.Variable src = null;
      deltablue.Variable dst = null;
      dart._runtime.base.DartList<deltablue.Variable> dests = (dart._runtime.base.DartList) dart._runtime.base.DartList.Generic._fromArguments(deltablue.Variable.class);
      for (java.lang.Integer i = 0; dart._runtime.helpers.IntegerHelper.operatorLess(i, n); i = dart._runtime.helpers.IntegerHelper.operatorPlus(i, 1))
      {
        src = new deltablue.Variable("src", i);
        dst = new deltablue.Variable("dst", i);
        dests.add(dst);
        new deltablue.StayConstraint(src, deltablue.__TopLevel.NORMAL);
        new deltablue.ScaleConstraint(src, scale, offset, dst, deltablue.__TopLevel.REQUIRED);
      }
      deltablue.__TopLevel.change(src, 17);
      if ((!dart._runtime.helpers.ObjectHelper.operatorEqual(dst.getValue(), 1170)))
      {
        dart.core.__TopLevel.print("Projection 1 failed");
      }
      deltablue.__TopLevel.change(dst, 1050);
      if ((!dart._runtime.helpers.ObjectHelper.operatorEqual(src.getValue(), 5)))
      {
        dart.core.__TopLevel.print("Projection 2 failed");
      }
      deltablue.__TopLevel.change(scale, 5);
      for (java.lang.Integer i = 0; dart._runtime.helpers.IntegerHelper.operatorLess(i, dart._runtime.helpers.IntegerHelper.operatorMinus(n, 1)); i = dart._runtime.helpers.IntegerHelper.operatorPlus(i, 1))
      {
        if ((!dart._runtime.helpers.ObjectHelper.operatorEqual(dests.operatorAt(i).getValue(), dart._runtime.helpers.IntegerHelper.operatorPlus(dart._runtime.helpers.IntegerHelper.operatorStar(i, 5), 1000))))
        {
          dart.core.__TopLevel.print("Projection 3 failed");
        }
      }
      deltablue.__TopLevel.change(offset, 2000);
      for (java.lang.Integer i = 0; dart._runtime.helpers.IntegerHelper.operatorLess(i, dart._runtime.helpers.IntegerHelper.operatorMinus(n, 1)); i = dart._runtime.helpers.IntegerHelper.operatorPlus(i, 1))
      {
        if ((!dart._runtime.helpers.ObjectHelper.operatorEqual(dests.operatorAt(i).getValue(), dart._runtime.helpers.IntegerHelper.operatorPlus(dart._runtime.helpers.IntegerHelper.operatorStar(i, 5), 2000))))
        {
          dart.core.__TopLevel.print("Projection 4 failed");
        }
      }
    }
    public static void change(deltablue.Variable v, java.lang.Integer newValue)
    {
      deltablue.EditConstraint edit = new deltablue.EditConstraint(v, deltablue.__TopLevel.PREFERRED);
      deltablue.Plan plan = deltablue.__TopLevel.planner.extractPlanFromConstraints((dart._runtime.base.DartList) dart._runtime.base.DartList.Generic._fromArguments(deltablue.EditConstraint.class, edit));
      for (java.lang.Integer i = 0; dart._runtime.helpers.IntegerHelper.operatorLess(i, 10); i = dart._runtime.helpers.IntegerHelper.operatorPlus(i, 1))
      {
        v.setValue(newValue);
        plan.execute();
      }
      edit.destroyConstraint();
    }
}
