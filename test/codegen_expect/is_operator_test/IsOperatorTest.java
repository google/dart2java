package is_operator_test;

public class IsOperatorTest extends dart._runtime.base.DartObject implements is_operator_test.IsOperatorTest_interface
{
    public static dart._runtime.types.simple.InterfaceTypeInfo dart2java$typeInfo = new dart._runtime.types.simple.InterfaceTypeInfo(is_operator_test.IsOperatorTest.class, is_operator_test.IsOperatorTest_interface.class);
    private static dart._runtime.types.simple.InterfaceTypeExpr dart2java$typeExpr_A = new dart._runtime.types.simple.InterfaceTypeExpr(is_operator_test.A.dart2java$typeInfo);
    private static dart._runtime.types.simple.InterfaceTypeExpr dart2java$typeExpr_B = new dart._runtime.types.simple.InterfaceTypeExpr(is_operator_test.B.dart2java$typeInfo);
    private static dart._runtime.types.simple.InterfaceTypeExpr dart2java$typeExpr_C = new dart._runtime.types.simple.InterfaceTypeExpr(is_operator_test.C.dart2java$typeInfo);
    private static dart._runtime.types.simple.InterfaceTypeExpr dart2java$typeExpr_AI = new dart._runtime.types.simple.InterfaceTypeExpr(is_operator_test.AI.dart2java$typeInfo);
    private static dart._runtime.types.simple.InterfaceTypeExpr dart2java$typeExpr_I = new dart._runtime.types.simple.InterfaceTypeExpr(is_operator_test.I.dart2java$typeInfo);
    private static dart._runtime.types.simple.InterfaceTypeExpr dart2java$typeExpr_Object = new dart._runtime.types.simple.InterfaceTypeExpr(dart._runtime.helpers.ObjectHelper.dart2java$typeInfo);
    static {
      is_operator_test.IsOperatorTest.dart2java$typeInfo.superclass = dart2java$typeExpr_Object;
    }
  
    public IsOperatorTest(dart._runtime.helpers.ConstructorHelper.EmptyConstructorMarker arg, dart._runtime.types.simple.Type type)
    {
      super(arg, type);
    }
  
    public static void testMain()
    {
      final dart._runtime.types.simple.TypeEnvironment dart2java$localTypeEnv = dart._runtime.types.simple.TypeEnvironment.ROOT;
      is_operator_test.A_interface a = is_operator_test.A._new(dart2java$localTypeEnv.evaluate(dart2java$typeExpr_A));
      is_operator_test.B_interface b = is_operator_test.B._new(dart2java$localTypeEnv.evaluate(dart2java$typeExpr_B));
      is_operator_test.C_interface c = is_operator_test.C._new(dart2java$localTypeEnv.evaluate(dart2java$typeExpr_C));
      java.lang.Object n = null;
      is_operator_test.Expect.equals(true, dart._runtime.helpers.TypeSystemHelper.getTrueType(a).isSubtypeOf(dart2java$localTypeEnv.evaluate(dart2java$typeExpr_A)));
      is_operator_test.Expect.equals(false, (!dart._runtime.helpers.TypeSystemHelper.getTrueType(a).isSubtypeOf(dart2java$localTypeEnv.evaluate(dart2java$typeExpr_A))));
      is_operator_test.Expect.equals(true, dart._runtime.helpers.TypeSystemHelper.getTrueType(b).isSubtypeOf(dart2java$localTypeEnv.evaluate(dart2java$typeExpr_B)));
      is_operator_test.Expect.equals(false, (!dart._runtime.helpers.TypeSystemHelper.getTrueType(b).isSubtypeOf(dart2java$localTypeEnv.evaluate(dart2java$typeExpr_B))));
      is_operator_test.Expect.equals(true, dart._runtime.helpers.TypeSystemHelper.getTrueType(c).isSubtypeOf(dart2java$localTypeEnv.evaluate(dart2java$typeExpr_C)));
      is_operator_test.Expect.equals(false, (!dart._runtime.helpers.TypeSystemHelper.getTrueType(c).isSubtypeOf(dart2java$localTypeEnv.evaluate(dart2java$typeExpr_C))));
      is_operator_test.Expect.equals(true, dart._runtime.helpers.TypeSystemHelper.getTrueType(c).isSubtypeOf(dart2java$localTypeEnv.evaluate(dart2java$typeExpr_A)));
      is_operator_test.Expect.equals(false, (!dart._runtime.helpers.TypeSystemHelper.getTrueType(c).isSubtypeOf(dart2java$localTypeEnv.evaluate(dart2java$typeExpr_A))));
      is_operator_test.Expect.equals(true, dart._runtime.helpers.TypeSystemHelper.getTrueType(a).isSubtypeOf(dart2java$localTypeEnv.evaluate(dart2java$typeExpr_AI)));
      is_operator_test.Expect.equals(false, (!dart._runtime.helpers.TypeSystemHelper.getTrueType(a).isSubtypeOf(dart2java$localTypeEnv.evaluate(dart2java$typeExpr_AI))));
      is_operator_test.Expect.equals(true, dart._runtime.helpers.TypeSystemHelper.getTrueType(a).isSubtypeOf(dart2java$localTypeEnv.evaluate(dart2java$typeExpr_I)));
      is_operator_test.Expect.equals(false, (!dart._runtime.helpers.TypeSystemHelper.getTrueType(a).isSubtypeOf(dart2java$localTypeEnv.evaluate(dart2java$typeExpr_I))));
      is_operator_test.Expect.equals(false, dart._runtime.helpers.TypeSystemHelper.getTrueType(b).isSubtypeOf(dart2java$localTypeEnv.evaluate(dart2java$typeExpr_AI)));
      is_operator_test.Expect.equals(true, (!dart._runtime.helpers.TypeSystemHelper.getTrueType(b).isSubtypeOf(dart2java$localTypeEnv.evaluate(dart2java$typeExpr_AI))));
      is_operator_test.Expect.equals(true, dart._runtime.helpers.TypeSystemHelper.getTrueType(b).isSubtypeOf(dart2java$localTypeEnv.evaluate(dart2java$typeExpr_I)));
      is_operator_test.Expect.equals(false, (!dart._runtime.helpers.TypeSystemHelper.getTrueType(b).isSubtypeOf(dart2java$localTypeEnv.evaluate(dart2java$typeExpr_I))));
      is_operator_test.Expect.equals(true, dart._runtime.helpers.TypeSystemHelper.getTrueType(c).isSubtypeOf(dart2java$localTypeEnv.evaluate(dart2java$typeExpr_AI)));
      is_operator_test.Expect.equals(false, (!dart._runtime.helpers.TypeSystemHelper.getTrueType(c).isSubtypeOf(dart2java$localTypeEnv.evaluate(dart2java$typeExpr_AI))));
      is_operator_test.Expect.equals(true, dart._runtime.helpers.TypeSystemHelper.getTrueType(c).isSubtypeOf(dart2java$localTypeEnv.evaluate(dart2java$typeExpr_I)));
      is_operator_test.Expect.equals(false, (!dart._runtime.helpers.TypeSystemHelper.getTrueType(c).isSubtypeOf(dart2java$localTypeEnv.evaluate(dart2java$typeExpr_I))));
      is_operator_test.Expect.equals(false, dart._runtime.helpers.TypeSystemHelper.getTrueType(n).isSubtypeOf(dart2java$localTypeEnv.evaluate(dart2java$typeExpr_AI)));
      is_operator_test.Expect.equals(true, (!dart._runtime.helpers.TypeSystemHelper.getTrueType(n).isSubtypeOf(dart2java$localTypeEnv.evaluate(dart2java$typeExpr_AI))));
      is_operator_test.Expect.equals(false, dart._runtime.helpers.TypeSystemHelper.getTrueType(n).isSubtypeOf(dart2java$localTypeEnv.evaluate(dart2java$typeExpr_I)));
      is_operator_test.Expect.equals(true, (!dart._runtime.helpers.TypeSystemHelper.getTrueType(n).isSubtypeOf(dart2java$localTypeEnv.evaluate(dart2java$typeExpr_I))));
      is_operator_test.Expect.equals(false, dart._runtime.helpers.TypeSystemHelper.getTrueType(a).isSubtypeOf(dart2java$localTypeEnv.evaluate(dart2java$typeExpr_B)));
      is_operator_test.Expect.equals(true, (!dart._runtime.helpers.TypeSystemHelper.getTrueType(a).isSubtypeOf(dart2java$localTypeEnv.evaluate(dart2java$typeExpr_B))));
      is_operator_test.Expect.equals(false, dart._runtime.helpers.TypeSystemHelper.getTrueType(a).isSubtypeOf(dart2java$localTypeEnv.evaluate(dart2java$typeExpr_C)));
      is_operator_test.Expect.equals(true, (!dart._runtime.helpers.TypeSystemHelper.getTrueType(a).isSubtypeOf(dart2java$localTypeEnv.evaluate(dart2java$typeExpr_C))));
      is_operator_test.Expect.equals(false, dart._runtime.helpers.TypeSystemHelper.getTrueType(b).isSubtypeOf(dart2java$localTypeEnv.evaluate(dart2java$typeExpr_A)));
      is_operator_test.Expect.equals(true, (!dart._runtime.helpers.TypeSystemHelper.getTrueType(b).isSubtypeOf(dart2java$localTypeEnv.evaluate(dart2java$typeExpr_A))));
      is_operator_test.Expect.equals(false, dart._runtime.helpers.TypeSystemHelper.getTrueType(b).isSubtypeOf(dart2java$localTypeEnv.evaluate(dart2java$typeExpr_C)));
      is_operator_test.Expect.equals(true, (!dart._runtime.helpers.TypeSystemHelper.getTrueType(b).isSubtypeOf(dart2java$localTypeEnv.evaluate(dart2java$typeExpr_C))));
      is_operator_test.Expect.equals(false, dart._runtime.helpers.TypeSystemHelper.getTrueType(c).isSubtypeOf(dart2java$localTypeEnv.evaluate(dart2java$typeExpr_B)));
      is_operator_test.Expect.equals(true, (!dart._runtime.helpers.TypeSystemHelper.getTrueType(c).isSubtypeOf(dart2java$localTypeEnv.evaluate(dart2java$typeExpr_B))));
      is_operator_test.Expect.equals(false, dart._runtime.helpers.TypeSystemHelper.getTrueType(n).isSubtypeOf(dart2java$localTypeEnv.evaluate(dart2java$typeExpr_A)));
      is_operator_test.Expect.equals(true, (!dart._runtime.helpers.TypeSystemHelper.getTrueType(n).isSubtypeOf(dart2java$localTypeEnv.evaluate(dart2java$typeExpr_A))));
      is_operator_test.Expect.equals(false, dart._runtime.helpers.TypeSystemHelper.getTrueType(null).isSubtypeOf(dart2java$localTypeEnv.evaluate(dart2java$typeExpr_A)));
      is_operator_test.Expect.equals(false, dart._runtime.helpers.TypeSystemHelper.getTrueType(null).isSubtypeOf(dart2java$localTypeEnv.evaluate(dart2java$typeExpr_B)));
      is_operator_test.Expect.equals(false, dart._runtime.helpers.TypeSystemHelper.getTrueType(null).isSubtypeOf(dart2java$localTypeEnv.evaluate(dart2java$typeExpr_C)));
      is_operator_test.Expect.equals(false, dart._runtime.helpers.TypeSystemHelper.getTrueType(null).isSubtypeOf(dart2java$localTypeEnv.evaluate(dart2java$typeExpr_AI)));
      is_operator_test.Expect.equals(false, dart._runtime.helpers.TypeSystemHelper.getTrueType(null).isSubtypeOf(dart2java$localTypeEnv.evaluate(dart2java$typeExpr_I)));
      is_operator_test.Expect.equals(true, (!dart._runtime.helpers.TypeSystemHelper.getTrueType(null).isSubtypeOf(dart2java$localTypeEnv.evaluate(dart2java$typeExpr_A))));
      is_operator_test.Expect.equals(true, (!dart._runtime.helpers.TypeSystemHelper.getTrueType(null).isSubtypeOf(dart2java$localTypeEnv.evaluate(dart2java$typeExpr_B))));
      is_operator_test.Expect.equals(true, (!dart._runtime.helpers.TypeSystemHelper.getTrueType(null).isSubtypeOf(dart2java$localTypeEnv.evaluate(dart2java$typeExpr_C))));
      is_operator_test.Expect.equals(true, (!dart._runtime.helpers.TypeSystemHelper.getTrueType(null).isSubtypeOf(dart2java$localTypeEnv.evaluate(dart2java$typeExpr_AI))));
      is_operator_test.Expect.equals(true, (!dart._runtime.helpers.TypeSystemHelper.getTrueType(null).isSubtypeOf(dart2java$localTypeEnv.evaluate(dart2java$typeExpr_I))));
    }
    public void _constructor()
    {
      final dart._runtime.types.simple.TypeEnvironment dart2java$localTypeEnv = this.dart2java$type.env;
      super._constructor();
    }
    public static is_operator_test.IsOperatorTest_interface _new(dart._runtime.types.simple.Type type)
    {
      is_operator_test.IsOperatorTest_interface result;
      result = new is_operator_test.IsOperatorTest(((dart._runtime.helpers.ConstructorHelper.EmptyConstructorMarker) null), type);
      result._constructor();
      return result;
    }
}
