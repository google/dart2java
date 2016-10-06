package deltablue;

public class __TopLevel
{
    private static dart._runtime.types.simple.InterfaceTypeExpr dart2java$typeExpr_Strength = new dart._runtime.types.simple.InterfaceTypeExpr(deltablue.Strength.dart2java$typeInfo);
    private static dart._runtime.types.simple.InterfaceTypeExpr dart2java$typeExpr_DeltaBlue = new dart._runtime.types.simple.InterfaceTypeExpr(deltablue.DeltaBlue.dart2java$typeInfo);
    private static dart._runtime.types.simple.InterfaceTypeExpr dart2java$typeExpr_Planner = new dart._runtime.types.simple.InterfaceTypeExpr(deltablue.Planner.dart2java$typeInfo);
    private static dart._runtime.types.simple.InterfaceTypeExpr dart2java$typeExpr_Variable = new dart._runtime.types.simple.InterfaceTypeExpr(deltablue.Variable.dart2java$typeInfo);
    private static dart._runtime.types.simple.InterfaceTypeExpr dart2java$typeExpr_EqualityConstraint = new dart._runtime.types.simple.InterfaceTypeExpr(deltablue.EqualityConstraint.dart2java$typeInfo);
    private static dart._runtime.types.simple.InterfaceTypeExpr dart2java$typeExpr_StayConstraint = new dart._runtime.types.simple.InterfaceTypeExpr(deltablue.StayConstraint.dart2java$typeInfo);
    private static dart._runtime.types.simple.InterfaceTypeExpr dart2java$typeExpr_EditConstraint = new dart._runtime.types.simple.InterfaceTypeExpr(deltablue.EditConstraint.dart2java$typeInfo);
    private static dart._runtime.types.simple.InterfaceTypeExpr dart2java$typeExpr_List$ltConstraint$gt = new dart._runtime.types.simple.InterfaceTypeExpr(dart.core.List.dart2java$typeInfo, new dart._runtime.types.simple.TypeExpr[] {new dart._runtime.types.simple.InterfaceTypeExpr(deltablue.Constraint.dart2java$typeInfo)});
    private static dart._runtime.types.simple.InterfaceTypeExpr dart2java$typeExpr_List$ltVariable$gt = new dart._runtime.types.simple.InterfaceTypeExpr(dart.core.List.dart2java$typeInfo, new dart._runtime.types.simple.TypeExpr[] {new dart._runtime.types.simple.InterfaceTypeExpr(deltablue.Variable.dart2java$typeInfo)});
    private static dart._runtime.types.simple.InterfaceTypeExpr dart2java$typeExpr_ScaleConstraint = new dart._runtime.types.simple.InterfaceTypeExpr(deltablue.ScaleConstraint.dart2java$typeInfo);
    private static dart._runtime.types.simple.InterfaceTypeExpr dart2java$typeExpr_List$ltEditConstraint$gt = new dart._runtime.types.simple.InterfaceTypeExpr(dart.core.List.dart2java$typeInfo, new dart._runtime.types.simple.TypeExpr[] {new dart._runtime.types.simple.InterfaceTypeExpr(deltablue.EditConstraint.dart2java$typeInfo)});
    static {
      final dart._runtime.types.simple.TypeEnvironment dart2java$localTypeEnv = dart._runtime.types.simple.TypeEnvironment.ROOT;
      deltablue.__TopLevel.REQUIRED = deltablue.Strength._new_Strength$(dart2java$localTypeEnv.evaluate(dart2java$typeExpr_Strength), 0, "required");
      deltablue.__TopLevel.STRONG_PREFERRED = deltablue.Strength._new_Strength$(dart2java$localTypeEnv.evaluate(dart2java$typeExpr_Strength), 1, "strongPreferred");
      deltablue.__TopLevel.PREFERRED = deltablue.Strength._new_Strength$(dart2java$localTypeEnv.evaluate(dart2java$typeExpr_Strength), 2, "preferred");
      deltablue.__TopLevel.STRONG_DEFAULT = deltablue.Strength._new_Strength$(dart2java$localTypeEnv.evaluate(dart2java$typeExpr_Strength), 3, "strongDefault");
      deltablue.__TopLevel.NORMAL = deltablue.Strength._new_Strength$(dart2java$localTypeEnv.evaluate(dart2java$typeExpr_Strength), 4, "normal");
      deltablue.__TopLevel.WEAK_DEFAULT = deltablue.Strength._new_Strength$(dart2java$localTypeEnv.evaluate(dart2java$typeExpr_Strength), 5, "weakDefault");
      deltablue.__TopLevel.WEAKEST = deltablue.Strength._new_Strength$(dart2java$localTypeEnv.evaluate(dart2java$typeExpr_Strength), 6, "weakest");
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
      deltablue.DeltaBlue._new_DeltaBlue$(dart2java$localTypeEnv.evaluate(dart2java$typeExpr_DeltaBlue)).report();
      dart.core.__TopLevel.print("Done.");
    }
    public static void chainTest(int n)
    {
      final dart._runtime.types.simple.TypeEnvironment dart2java$localTypeEnv = dart._runtime.types.simple.TypeEnvironment.ROOT;
      deltablue.__TopLevel.planner = deltablue.Planner._new_Planner$(dart2java$localTypeEnv.evaluate(dart2java$typeExpr_Planner));
      deltablue.Variable_interface prev = null;
      deltablue.Variable_interface first = null;
      deltablue.Variable_interface last = null;
      for (int i = 0; (i <= n); i = (i + 1))
      {
        deltablue.Variable_interface v = deltablue.Variable._new_Variable$(dart2java$localTypeEnv.evaluate(dart2java$typeExpr_Variable), "v", 0);
        if ((!dart._runtime.helpers.ObjectHelper.operatorEqual(prev, null)))
        {
          deltablue.EqualityConstraint._new_EqualityConstraint$(dart2java$localTypeEnv.evaluate(dart2java$typeExpr_EqualityConstraint), prev, v, deltablue.__TopLevel.REQUIRED);
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
      deltablue.StayConstraint._new_StayConstraint$(dart2java$localTypeEnv.evaluate(dart2java$typeExpr_StayConstraint), last, deltablue.__TopLevel.STRONG_DEFAULT);
      deltablue.EditConstraint_interface edit = deltablue.EditConstraint._new_EditConstraint$(dart2java$localTypeEnv.evaluate(dart2java$typeExpr_EditConstraint), first, deltablue.__TopLevel.PREFERRED);
      deltablue.Plan_interface plan = deltablue.__TopLevel.planner.extractPlanFromConstraints(((dart.core.List_interface) dart._runtime.base.DartList.<deltablue.Constraint_interface>specialfactory$fromArguments(dart2java$localTypeEnv.evaluate(dart2java$typeExpr_List$ltConstraint$gt), edit)));
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
      deltablue.__TopLevel.planner = deltablue.Planner._new_Planner$(dart2java$localTypeEnv.evaluate(dart2java$typeExpr_Planner));
      deltablue.Variable_interface scale = deltablue.Variable._new_Variable$(dart2java$localTypeEnv.evaluate(dart2java$typeExpr_Variable), "scale", 10);
      deltablue.Variable_interface offset = deltablue.Variable._new_Variable$(dart2java$localTypeEnv.evaluate(dart2java$typeExpr_Variable), "offset", 1000);
      deltablue.Variable_interface src = null;
      deltablue.Variable_interface dst = null;
      dart.core.List_interface<deltablue.Variable_interface> dests = ((dart.core.List_interface) dart._runtime.base.DartList.<deltablue.Variable_interface>specialfactory$fromArguments(dart2java$localTypeEnv.evaluate(dart2java$typeExpr_List$ltVariable$gt)));
      for (int i = 0; (i < n); i = (i + 1))
      {
        src = deltablue.Variable._new_Variable$(dart2java$localTypeEnv.evaluate(dart2java$typeExpr_Variable), "src", i);
        dst = deltablue.Variable._new_Variable$(dart2java$localTypeEnv.evaluate(dart2java$typeExpr_Variable), "dst", i);
        dests.add_List(dst);
        deltablue.StayConstraint._new_StayConstraint$(dart2java$localTypeEnv.evaluate(dart2java$typeExpr_StayConstraint), src, deltablue.__TopLevel.NORMAL);
        deltablue.ScaleConstraint._new_ScaleConstraint$(dart2java$localTypeEnv.evaluate(dart2java$typeExpr_ScaleConstraint), src, scale, offset, dst, deltablue.__TopLevel.REQUIRED);
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
        if ((!(dests.operatorAt_List(i).getValue() == ((i * 5) + 1000))))
        {
          dart.core.__TopLevel.print("Projection 3 failed");
        }
      }
      deltablue.__TopLevel.change(offset, 2000);
      for (int i = 0; (i < (n - 1)); i = (i + 1))
      {
        if ((!(dests.operatorAt_List(i).getValue() == ((i * 5) + 2000))))
        {
          dart.core.__TopLevel.print("Projection 4 failed");
        }
      }
    }
    public static void change(deltablue.Variable_interface v, int newValue)
    {
      final dart._runtime.types.simple.TypeEnvironment dart2java$localTypeEnv = dart._runtime.types.simple.TypeEnvironment.ROOT;
      deltablue.EditConstraint_interface edit = deltablue.EditConstraint._new_EditConstraint$(dart2java$localTypeEnv.evaluate(dart2java$typeExpr_EditConstraint), v, deltablue.__TopLevel.PREFERRED);
      deltablue.Plan_interface plan = deltablue.__TopLevel.planner.extractPlanFromConstraints(((dart.core.List_interface) dart._runtime.base.DartList.<deltablue.EditConstraint_interface>specialfactory$fromArguments(dart2java$localTypeEnv.evaluate(dart2java$typeExpr_List$ltEditConstraint$gt), edit)));
      for (int i = 0; (i < 10); i = (i + 1))
      {
        v.setValue(newValue);
        plan.execute();
      }
      edit.destroyConstraint();
    }
}
