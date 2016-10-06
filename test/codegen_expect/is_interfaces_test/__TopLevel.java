package is_interfaces_test;

public class __TopLevel
{
    private static dart._runtime.types.simple.InterfaceTypeExpr dart2java$typeExpr_A = new dart._runtime.types.simple.InterfaceTypeExpr(is_interfaces_test.A.dart2java$typeInfo);
    private static dart._runtime.types.simple.InterfaceTypeExpr dart2java$typeExpr_B = new dart._runtime.types.simple.InterfaceTypeExpr(is_interfaces_test.B.dart2java$typeInfo);
    private static dart._runtime.types.simple.InterfaceTypeExpr dart2java$typeExpr_C = new dart._runtime.types.simple.InterfaceTypeExpr(is_interfaces_test.C.dart2java$typeInfo);
    private static dart._runtime.types.simple.InterfaceTypeExpr dart2java$typeExpr_List$ltA$gt = new dart._runtime.types.simple.InterfaceTypeExpr(dart.core.List.dart2java$typeInfo, new dart._runtime.types.simple.TypeExpr[] {new dart._runtime.types.simple.InterfaceTypeExpr(is_interfaces_test.A.dart2java$typeInfo)});
  
  
  
    public static int inscrutable(int x)
    {
      final dart._runtime.types.simple.TypeEnvironment dart2java$localTypeEnv = dart._runtime.types.simple.TypeEnvironment.ROOT;
      return (((x == 0)) ? (0) : ((x | is_interfaces_test.__TopLevel.inscrutable((x & (x - 1))))));
    }
    public static void main(String[] args)
    {
      final dart._runtime.types.simple.TypeEnvironment dart2java$localTypeEnv = dart._runtime.types.simple.TypeEnvironment.ROOT;
      dart.core.List_interface<is_interfaces_test.A_interface> things = ((dart.core.List_interface) dart._runtime.base.DartList.<is_interfaces_test.A_interface>specialfactory$fromArguments(dart2java$localTypeEnv.evaluate(dart2java$typeExpr_List$ltA$gt), is_interfaces_test.A._new_A$(dart2java$localTypeEnv.evaluate(dart2java$typeExpr_A)), is_interfaces_test.B._new_B$(dart2java$localTypeEnv.evaluate(dart2java$typeExpr_B)), is_interfaces_test.C._new_C$(dart2java$localTypeEnv.evaluate(dart2java$typeExpr_C))));
      is_interfaces_test.A_interface a = things.operatorAt_List(is_interfaces_test.__TopLevel.inscrutable(0));
      is_interfaces_test.Expect.isTrue(dart._runtime.helpers.TypeSystemHelper.getTrueType(a).isSubtypeOf(dart2java$localTypeEnv.evaluate(dart2java$typeExpr_A)));
      is_interfaces_test.Expect.isFalse(dart._runtime.helpers.TypeSystemHelper.getTrueType(a).isSubtypeOf(dart2java$localTypeEnv.evaluate(dart2java$typeExpr_B)));
      is_interfaces_test.Expect.isFalse(dart._runtime.helpers.TypeSystemHelper.getTrueType(a).isSubtypeOf(dart2java$localTypeEnv.evaluate(dart2java$typeExpr_C)));
      is_interfaces_test.A_interface b = things.operatorAt_List(is_interfaces_test.__TopLevel.inscrutable(1));
      is_interfaces_test.Expect.isTrue(dart._runtime.helpers.TypeSystemHelper.getTrueType(b).isSubtypeOf(dart2java$localTypeEnv.evaluate(dart2java$typeExpr_A)));
      is_interfaces_test.Expect.isTrue(dart._runtime.helpers.TypeSystemHelper.getTrueType(b).isSubtypeOf(dart2java$localTypeEnv.evaluate(dart2java$typeExpr_B)));
      is_interfaces_test.Expect.isFalse(dart._runtime.helpers.TypeSystemHelper.getTrueType(b).isSubtypeOf(dart2java$localTypeEnv.evaluate(dart2java$typeExpr_C)));
      is_interfaces_test.A_interface c = things.operatorAt_List(is_interfaces_test.__TopLevel.inscrutable(2));
      is_interfaces_test.Expect.isTrue(dart._runtime.helpers.TypeSystemHelper.getTrueType(c).isSubtypeOf(dart2java$localTypeEnv.evaluate(dart2java$typeExpr_A)));
      is_interfaces_test.Expect.isTrue(dart._runtime.helpers.TypeSystemHelper.getTrueType(c).isSubtypeOf(dart2java$localTypeEnv.evaluate(dart2java$typeExpr_B)));
      is_interfaces_test.Expect.isTrue(dart._runtime.helpers.TypeSystemHelper.getTrueType(c).isSubtypeOf(dart2java$localTypeEnv.evaluate(dart2java$typeExpr_C)));
    }
}
