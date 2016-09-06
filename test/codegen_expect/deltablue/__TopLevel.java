package deltablue;

public class __TopLevel
{
    static {
      final dart._runtime.types.simple.TypeEnvironment dart2java$localTypeEnv = dart._runtime.types.simple.TypeEnvironment.ROOT;
      deltablue.__TopLevel.REQUIRED = new deltablue.Strength(dart2java$localTypeEnv.evaluate(new dart._runtime.types.simple.InterfaceTypeExpr(deltablue.Strength.dart2java$typeInfo)), 0, "required");
      deltablue.__TopLevel.STRONG_PREFERRED = new deltablue.Strength(dart2java$localTypeEnv.evaluate(new dart._runtime.types.simple.InterfaceTypeExpr(deltablue.Strength.dart2java$typeInfo)), 1, "strongPreferred");
      deltablue.__TopLevel.PREFERRED = new deltablue.Strength(dart2java$localTypeEnv.evaluate(new dart._runtime.types.simple.InterfaceTypeExpr(deltablue.Strength.dart2java$typeInfo)), 2, "preferred");
      deltablue.__TopLevel.STRONG_DEFAULT = new deltablue.Strength(dart2java$localTypeEnv.evaluate(new dart._runtime.types.simple.InterfaceTypeExpr(deltablue.Strength.dart2java$typeInfo)), 3, "strongDefault");
      deltablue.__TopLevel.NORMAL = new deltablue.Strength(dart2java$localTypeEnv.evaluate(new dart._runtime.types.simple.InterfaceTypeExpr(deltablue.Strength.dart2java$typeInfo)), 4, "normal");
      deltablue.__TopLevel.WEAK_DEFAULT = new deltablue.Strength(dart2java$localTypeEnv.evaluate(new dart._runtime.types.simple.InterfaceTypeExpr(deltablue.Strength.dart2java$typeInfo)), 5, "weakDefault");
      deltablue.__TopLevel.WEAKEST = new deltablue.Strength(dart2java$localTypeEnv.evaluate(new dart._runtime.types.simple.InterfaceTypeExpr(deltablue.Strength.dart2java$typeInfo)), 6, "weakest");
      deltablue.__TopLevel.NONE = 1;
      deltablue.__TopLevel.FORWARD = 2;
      deltablue.__TopLevel.BACKWARD = 0;
    }
    public static deltablue.Strength_interface REQUIRED;
    public static deltablue.Strength_interface STRONG_PREFERRED;
    public static deltablue.Strength_interface PREFERRED;
    public static deltablue.Strength_interface STRONG_DEFAULT;
    public static deltablue.Strength_interface NORMAL;
    public static deltablue.Strength_interface WEAK_DEFAULT;
    public static deltablue.Strength_interface WEAKEST;
    public static int NONE;
    public static int FORWARD;
    public static int BACKWARD;
    public static deltablue.Planner_interface planner;
  
  
  
    public static void main(String[] args)
    {
      final dart._runtime.types.simple.TypeEnvironment dart2java$localTypeEnv = dart._runtime.types.simple.TypeEnvironment.ROOT;
      dart.core.__TopLevel.print("Running benchmark...");
      new deltablue.DeltaBlue(dart2java$localTypeEnv.evaluate(new dart._runtime.types.simple.InterfaceTypeExpr(deltablue.DeltaBlue.dart2java$typeInfo))).report();
      dart.core.__TopLevel.print("Done.");
    }
    public static void chainTest(int n)
    {
      final dart._runtime.types.simple.TypeEnvironment dart2java$localTypeEnv = dart._runtime.types.simple.TypeEnvironment.ROOT;
      deltablue.__TopLevel.planner = new deltablue.Planner(dart2java$localTypeEnv.evaluate(new dart._runtime.types.simple.InterfaceTypeExpr(deltablue.Planner.dart2java$typeInfo)));
      deltablue.Variable_interface prev = null;
      deltablue.Variable_interface first = null;
      deltablue.Variable_interface last = null;
      for (int i = 0; (i <= n); i = (i + 1))
      {
        deltablue.Variable_interface v = new deltablue.Variable(dart2java$localTypeEnv.evaluate(new dart._runtime.types.simple.InterfaceTypeExpr(deltablue.Variable.dart2java$typeInfo)), "v", 0);
        if ((!dart._runtime.helpers.ObjectHelper.operatorEqual(prev, null)))
        {
          new deltablue.EqualityConstraint(dart2java$localTypeEnv.evaluate(new dart._runtime.types.simple.InterfaceTypeExpr(deltablue.EqualityConstraint.dart2java$typeInfo)), prev, v, deltablue.__TopLevel.REQUIRED);
        }
        if ((i == 0))
        {
          first = v;
        }
        if ((i == n))
        {
          last = v;
        }
        prev = v;
      }
      new deltablue.StayConstraint(dart2java$localTypeEnv.evaluate(new dart._runtime.types.simple.InterfaceTypeExpr(deltablue.StayConstraint.dart2java$typeInfo)), last, deltablue.__TopLevel.STRONG_DEFAULT);
      deltablue.EditConstraint_interface edit = new deltablue.EditConstraint(dart2java$localTypeEnv.evaluate(new dart._runtime.types.simple.InterfaceTypeExpr(deltablue.EditConstraint.dart2java$typeInfo)), first, deltablue.__TopLevel.PREFERRED);
      deltablue.Plan_interface plan = deltablue.__TopLevel.planner.extractPlanFromConstraints(((dart.core.List_interface) dart._runtime.base.DartList.Generic._fromArguments(deltablue.Constraint_interface.class, edit)));
      for (int i = 0; (i < 100); i = (i + 1))
      {
        first.setValue(i);
        plan.execute();
        if ((!(last.getValue() == i)))
        {
          dart.core.__TopLevel.print("Chain test failed:");
          dart.core.__TopLevel.print((((("Expected last value to be " + i) + " but it was ") + last.getValue()) + "."));
        }
      }
    }
    public static void projectionTest(int n)
    {
      final dart._runtime.types.simple.TypeEnvironment dart2java$localTypeEnv = dart._runtime.types.simple.TypeEnvironment.ROOT;
      deltablue.__TopLevel.planner = new deltablue.Planner(dart2java$localTypeEnv.evaluate(new dart._runtime.types.simple.InterfaceTypeExpr(deltablue.Planner.dart2java$typeInfo)));
      deltablue.Variable_interface scale = new deltablue.Variable(dart2java$localTypeEnv.evaluate(new dart._runtime.types.simple.InterfaceTypeExpr(deltablue.Variable.dart2java$typeInfo)), "scale", 10);
      deltablue.Variable_interface offset = new deltablue.Variable(dart2java$localTypeEnv.evaluate(new dart._runtime.types.simple.InterfaceTypeExpr(deltablue.Variable.dart2java$typeInfo)), "offset", 1000);
      deltablue.Variable_interface src = null;
      deltablue.Variable_interface dst = null;
      dart.core.List_interface<deltablue.Variable_interface> dests = ((dart.core.List_interface) dart._runtime.base.DartList.Generic._fromArguments(deltablue.Variable_interface.class));
      for (int i = 0; (i < n); i = (i + 1))
      {
        src = new deltablue.Variable(dart2java$localTypeEnv.evaluate(new dart._runtime.types.simple.InterfaceTypeExpr(deltablue.Variable.dart2java$typeInfo)), "src", i);
        dst = new deltablue.Variable(dart2java$localTypeEnv.evaluate(new dart._runtime.types.simple.InterfaceTypeExpr(deltablue.Variable.dart2java$typeInfo)), "dst", i);
        dests.add(dst);
        new deltablue.StayConstraint(dart2java$localTypeEnv.evaluate(new dart._runtime.types.simple.InterfaceTypeExpr(deltablue.StayConstraint.dart2java$typeInfo)), src, deltablue.__TopLevel.NORMAL);
        new deltablue.ScaleConstraint(dart2java$localTypeEnv.evaluate(new dart._runtime.types.simple.InterfaceTypeExpr(deltablue.ScaleConstraint.dart2java$typeInfo)), src, scale, offset, dst, deltablue.__TopLevel.REQUIRED);
      }
      deltablue.__TopLevel.change(src, 17);
      if ((!(dst.getValue() == 1170)))
      {
        dart.core.__TopLevel.print("Projection 1 failed");
      }
      deltablue.__TopLevel.change(dst, 1050);
      if ((!(src.getValue() == 5)))
      {
        dart.core.__TopLevel.print("Projection 2 failed");
      }
      deltablue.__TopLevel.change(scale, 5);
      for (int i = 0; (i < (n - 1)); i = (i + 1))
      {
        if ((!(dests.operatorAt(i).getValue() == ((i * 5) + 1000))))
        {
          dart.core.__TopLevel.print("Projection 3 failed");
        }
      }
      deltablue.__TopLevel.change(offset, 2000);
      for (int i = 0; (i < (n - 1)); i = (i + 1))
      {
        if ((!(dests.operatorAt(i).getValue() == ((i * 5) + 2000))))
        {
          dart.core.__TopLevel.print("Projection 4 failed");
        }
      }
    }
    public static void change(deltablue.Variable_interface v, int newValue)
    {
      final dart._runtime.types.simple.TypeEnvironment dart2java$localTypeEnv = dart._runtime.types.simple.TypeEnvironment.ROOT;
      deltablue.EditConstraint_interface edit = new deltablue.EditConstraint(dart2java$localTypeEnv.evaluate(new dart._runtime.types.simple.InterfaceTypeExpr(deltablue.EditConstraint.dart2java$typeInfo)), v, deltablue.__TopLevel.PREFERRED);
      deltablue.Plan_interface plan = deltablue.__TopLevel.planner.extractPlanFromConstraints(((dart.core.List_interface) dart._runtime.base.DartList.Generic._fromArguments(deltablue.EditConstraint_interface.class, edit)));
      for (int i = 0; (i < 10); i = (i + 1))
      {
        v.setValue(newValue);
        plan.execute();
      }
      edit.destroyConstraint();
    }
}
