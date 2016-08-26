package is_operator_test;

public class IsOperatorTest extends dart._runtime.base.DartObject
{
    public static dart._runtime.types.simple.InterfaceTypeInfo dart2java$typeInfo = new dart._runtime.types.simple.InterfaceTypeInfo("file:///usr/local/google/home/andrewkrieger/ddc-java/gen/codegen_tests/is_operator_test.dart", "IsOperatorTest");
    static {
      is_operator_test.IsOperatorTest.dart2java$typeInfo.superclass = new dart._runtime.types.simple.InterfaceTypeExpr(dart._runtime.helpers.ObjectHelper.dart2java$typeInfo);
    }
  
    public IsOperatorTest(dart._runtime.types.simple.Type type)
    {
      super((dart._runtime.helpers.ConstructorHelper.EmptyConstructorMarker) null, type);
      this._constructor();
    }
    public IsOperatorTest(dart._runtime.helpers.ConstructorHelper.EmptyConstructorMarker arg, dart._runtime.types.simple.Type type)
    {
      super(arg, type);
    }
  
    protected void _constructor()
    {
      final dart._runtime.types.simple.TypeEnvironment dart2java$localTypeEnv = this.dart2java$type.env;
      super._constructor();
    }
    public static void testMain()
    {
      final dart._runtime.types.simple.TypeEnvironment dart2java$localTypeEnv = dart._runtime.types.simple.TypeEnvironment.ROOT;
      is_operator_test.A a = new is_operator_test.A(dart2java$localTypeEnv.evaluate(new dart._runtime.types.simple.InterfaceTypeExpr(is_operator_test.A.dart2java$typeInfo)));
      is_operator_test.B b = new is_operator_test.B(dart2java$localTypeEnv.evaluate(new dart._runtime.types.simple.InterfaceTypeExpr(is_operator_test.B.dart2java$typeInfo)));
      is_operator_test.C c = new is_operator_test.C(dart2java$localTypeEnv.evaluate(new dart._runtime.types.simple.InterfaceTypeExpr(is_operator_test.C.dart2java$typeInfo)));
      java.lang.Object n = null;
      is_operator_test.Expect.equals(true, dart._runtime.helpers.TypeSystemHelper.getTrueType(a).isSubtypeOf(dart2java$localTypeEnv.evaluate(new dart._runtime.types.simple.InterfaceTypeExpr(is_operator_test.A.dart2java$typeInfo))));
      is_operator_test.Expect.equals(false, (!dart._runtime.helpers.TypeSystemHelper.getTrueType(a).isSubtypeOf(dart2java$localTypeEnv.evaluate(new dart._runtime.types.simple.InterfaceTypeExpr(is_operator_test.A.dart2java$typeInfo)))));
      is_operator_test.Expect.equals(true, dart._runtime.helpers.TypeSystemHelper.getTrueType(b).isSubtypeOf(dart2java$localTypeEnv.evaluate(new dart._runtime.types.simple.InterfaceTypeExpr(is_operator_test.B.dart2java$typeInfo))));
      is_operator_test.Expect.equals(false, (!dart._runtime.helpers.TypeSystemHelper.getTrueType(b).isSubtypeOf(dart2java$localTypeEnv.evaluate(new dart._runtime.types.simple.InterfaceTypeExpr(is_operator_test.B.dart2java$typeInfo)))));
      is_operator_test.Expect.equals(true, dart._runtime.helpers.TypeSystemHelper.getTrueType(c).isSubtypeOf(dart2java$localTypeEnv.evaluate(new dart._runtime.types.simple.InterfaceTypeExpr(is_operator_test.C.dart2java$typeInfo))));
      is_operator_test.Expect.equals(false, (!dart._runtime.helpers.TypeSystemHelper.getTrueType(c).isSubtypeOf(dart2java$localTypeEnv.evaluate(new dart._runtime.types.simple.InterfaceTypeExpr(is_operator_test.C.dart2java$typeInfo)))));
      is_operator_test.Expect.equals(true, dart._runtime.helpers.TypeSystemHelper.getTrueType(c).isSubtypeOf(dart2java$localTypeEnv.evaluate(new dart._runtime.types.simple.InterfaceTypeExpr(is_operator_test.A.dart2java$typeInfo))));
      is_operator_test.Expect.equals(false, (!dart._runtime.helpers.TypeSystemHelper.getTrueType(c).isSubtypeOf(dart2java$localTypeEnv.evaluate(new dart._runtime.types.simple.InterfaceTypeExpr(is_operator_test.A.dart2java$typeInfo)))));
      is_operator_test.Expect.equals(true, dart._runtime.helpers.TypeSystemHelper.getTrueType(a).isSubtypeOf(dart2java$localTypeEnv.evaluate(new dart._runtime.types.simple.InterfaceTypeExpr(is_operator_test.AI.dart2java$typeInfo))));
      is_operator_test.Expect.equals(false, (!dart._runtime.helpers.TypeSystemHelper.getTrueType(a).isSubtypeOf(dart2java$localTypeEnv.evaluate(new dart._runtime.types.simple.InterfaceTypeExpr(is_operator_test.AI.dart2java$typeInfo)))));
      is_operator_test.Expect.equals(true, dart._runtime.helpers.TypeSystemHelper.getTrueType(a).isSubtypeOf(dart2java$localTypeEnv.evaluate(new dart._runtime.types.simple.InterfaceTypeExpr(is_operator_test.I.dart2java$typeInfo))));
      is_operator_test.Expect.equals(false, (!dart._runtime.helpers.TypeSystemHelper.getTrueType(a).isSubtypeOf(dart2java$localTypeEnv.evaluate(new dart._runtime.types.simple.InterfaceTypeExpr(is_operator_test.I.dart2java$typeInfo)))));
      is_operator_test.Expect.equals(false, dart._runtime.helpers.TypeSystemHelper.getTrueType(b).isSubtypeOf(dart2java$localTypeEnv.evaluate(new dart._runtime.types.simple.InterfaceTypeExpr(is_operator_test.AI.dart2java$typeInfo))));
      is_operator_test.Expect.equals(true, (!dart._runtime.helpers.TypeSystemHelper.getTrueType(b).isSubtypeOf(dart2java$localTypeEnv.evaluate(new dart._runtime.types.simple.InterfaceTypeExpr(is_operator_test.AI.dart2java$typeInfo)))));
      is_operator_test.Expect.equals(true, dart._runtime.helpers.TypeSystemHelper.getTrueType(b).isSubtypeOf(dart2java$localTypeEnv.evaluate(new dart._runtime.types.simple.InterfaceTypeExpr(is_operator_test.I.dart2java$typeInfo))));
      is_operator_test.Expect.equals(false, (!dart._runtime.helpers.TypeSystemHelper.getTrueType(b).isSubtypeOf(dart2java$localTypeEnv.evaluate(new dart._runtime.types.simple.InterfaceTypeExpr(is_operator_test.I.dart2java$typeInfo)))));
      is_operator_test.Expect.equals(true, dart._runtime.helpers.TypeSystemHelper.getTrueType(c).isSubtypeOf(dart2java$localTypeEnv.evaluate(new dart._runtime.types.simple.InterfaceTypeExpr(is_operator_test.AI.dart2java$typeInfo))));
      is_operator_test.Expect.equals(false, (!dart._runtime.helpers.TypeSystemHelper.getTrueType(c).isSubtypeOf(dart2java$localTypeEnv.evaluate(new dart._runtime.types.simple.InterfaceTypeExpr(is_operator_test.AI.dart2java$typeInfo)))));
      is_operator_test.Expect.equals(true, dart._runtime.helpers.TypeSystemHelper.getTrueType(c).isSubtypeOf(dart2java$localTypeEnv.evaluate(new dart._runtime.types.simple.InterfaceTypeExpr(is_operator_test.I.dart2java$typeInfo))));
      is_operator_test.Expect.equals(false, (!dart._runtime.helpers.TypeSystemHelper.getTrueType(c).isSubtypeOf(dart2java$localTypeEnv.evaluate(new dart._runtime.types.simple.InterfaceTypeExpr(is_operator_test.I.dart2java$typeInfo)))));
      is_operator_test.Expect.equals(false, dart._runtime.helpers.TypeSystemHelper.getTrueType(n).isSubtypeOf(dart2java$localTypeEnv.evaluate(new dart._runtime.types.simple.InterfaceTypeExpr(is_operator_test.AI.dart2java$typeInfo))));
      is_operator_test.Expect.equals(true, (!dart._runtime.helpers.TypeSystemHelper.getTrueType(n).isSubtypeOf(dart2java$localTypeEnv.evaluate(new dart._runtime.types.simple.InterfaceTypeExpr(is_operator_test.AI.dart2java$typeInfo)))));
      is_operator_test.Expect.equals(false, dart._runtime.helpers.TypeSystemHelper.getTrueType(n).isSubtypeOf(dart2java$localTypeEnv.evaluate(new dart._runtime.types.simple.InterfaceTypeExpr(is_operator_test.I.dart2java$typeInfo))));
      is_operator_test.Expect.equals(true, (!dart._runtime.helpers.TypeSystemHelper.getTrueType(n).isSubtypeOf(dart2java$localTypeEnv.evaluate(new dart._runtime.types.simple.InterfaceTypeExpr(is_operator_test.I.dart2java$typeInfo)))));
      is_operator_test.Expect.equals(false, dart._runtime.helpers.TypeSystemHelper.getTrueType(a).isSubtypeOf(dart2java$localTypeEnv.evaluate(new dart._runtime.types.simple.InterfaceTypeExpr(is_operator_test.B.dart2java$typeInfo))));
      is_operator_test.Expect.equals(true, (!dart._runtime.helpers.TypeSystemHelper.getTrueType(a).isSubtypeOf(dart2java$localTypeEnv.evaluate(new dart._runtime.types.simple.InterfaceTypeExpr(is_operator_test.B.dart2java$typeInfo)))));
      is_operator_test.Expect.equals(false, dart._runtime.helpers.TypeSystemHelper.getTrueType(a).isSubtypeOf(dart2java$localTypeEnv.evaluate(new dart._runtime.types.simple.InterfaceTypeExpr(is_operator_test.C.dart2java$typeInfo))));
      is_operator_test.Expect.equals(true, (!dart._runtime.helpers.TypeSystemHelper.getTrueType(a).isSubtypeOf(dart2java$localTypeEnv.evaluate(new dart._runtime.types.simple.InterfaceTypeExpr(is_operator_test.C.dart2java$typeInfo)))));
      is_operator_test.Expect.equals(false, dart._runtime.helpers.TypeSystemHelper.getTrueType(b).isSubtypeOf(dart2java$localTypeEnv.evaluate(new dart._runtime.types.simple.InterfaceTypeExpr(is_operator_test.A.dart2java$typeInfo))));
      is_operator_test.Expect.equals(true, (!dart._runtime.helpers.TypeSystemHelper.getTrueType(b).isSubtypeOf(dart2java$localTypeEnv.evaluate(new dart._runtime.types.simple.InterfaceTypeExpr(is_operator_test.A.dart2java$typeInfo)))));
      is_operator_test.Expect.equals(false, dart._runtime.helpers.TypeSystemHelper.getTrueType(b).isSubtypeOf(dart2java$localTypeEnv.evaluate(new dart._runtime.types.simple.InterfaceTypeExpr(is_operator_test.C.dart2java$typeInfo))));
      is_operator_test.Expect.equals(true, (!dart._runtime.helpers.TypeSystemHelper.getTrueType(b).isSubtypeOf(dart2java$localTypeEnv.evaluate(new dart._runtime.types.simple.InterfaceTypeExpr(is_operator_test.C.dart2java$typeInfo)))));
      is_operator_test.Expect.equals(false, dart._runtime.helpers.TypeSystemHelper.getTrueType(c).isSubtypeOf(dart2java$localTypeEnv.evaluate(new dart._runtime.types.simple.InterfaceTypeExpr(is_operator_test.B.dart2java$typeInfo))));
      is_operator_test.Expect.equals(true, (!dart._runtime.helpers.TypeSystemHelper.getTrueType(c).isSubtypeOf(dart2java$localTypeEnv.evaluate(new dart._runtime.types.simple.InterfaceTypeExpr(is_operator_test.B.dart2java$typeInfo)))));
      is_operator_test.Expect.equals(false, dart._runtime.helpers.TypeSystemHelper.getTrueType(n).isSubtypeOf(dart2java$localTypeEnv.evaluate(new dart._runtime.types.simple.InterfaceTypeExpr(is_operator_test.A.dart2java$typeInfo))));
      is_operator_test.Expect.equals(true, (!dart._runtime.helpers.TypeSystemHelper.getTrueType(n).isSubtypeOf(dart2java$localTypeEnv.evaluate(new dart._runtime.types.simple.InterfaceTypeExpr(is_operator_test.A.dart2java$typeInfo)))));
      is_operator_test.Expect.equals(false, dart._runtime.helpers.TypeSystemHelper.getTrueType(null).isSubtypeOf(dart2java$localTypeEnv.evaluate(new dart._runtime.types.simple.InterfaceTypeExpr(is_operator_test.A.dart2java$typeInfo))));
      is_operator_test.Expect.equals(false, dart._runtime.helpers.TypeSystemHelper.getTrueType(null).isSubtypeOf(dart2java$localTypeEnv.evaluate(new dart._runtime.types.simple.InterfaceTypeExpr(is_operator_test.B.dart2java$typeInfo))));
      is_operator_test.Expect.equals(false, dart._runtime.helpers.TypeSystemHelper.getTrueType(null).isSubtypeOf(dart2java$localTypeEnv.evaluate(new dart._runtime.types.simple.InterfaceTypeExpr(is_operator_test.C.dart2java$typeInfo))));
      is_operator_test.Expect.equals(false, dart._runtime.helpers.TypeSystemHelper.getTrueType(null).isSubtypeOf(dart2java$localTypeEnv.evaluate(new dart._runtime.types.simple.InterfaceTypeExpr(is_operator_test.AI.dart2java$typeInfo))));
      is_operator_test.Expect.equals(false, dart._runtime.helpers.TypeSystemHelper.getTrueType(null).isSubtypeOf(dart2java$localTypeEnv.evaluate(new dart._runtime.types.simple.InterfaceTypeExpr(is_operator_test.I.dart2java$typeInfo))));
      is_operator_test.Expect.equals(true, (!dart._runtime.helpers.TypeSystemHelper.getTrueType(null).isSubtypeOf(dart2java$localTypeEnv.evaluate(new dart._runtime.types.simple.InterfaceTypeExpr(is_operator_test.A.dart2java$typeInfo)))));
      is_operator_test.Expect.equals(true, (!dart._runtime.helpers.TypeSystemHelper.getTrueType(null).isSubtypeOf(dart2java$localTypeEnv.evaluate(new dart._runtime.types.simple.InterfaceTypeExpr(is_operator_test.B.dart2java$typeInfo)))));
      is_operator_test.Expect.equals(true, (!dart._runtime.helpers.TypeSystemHelper.getTrueType(null).isSubtypeOf(dart2java$localTypeEnv.evaluate(new dart._runtime.types.simple.InterfaceTypeExpr(is_operator_test.C.dart2java$typeInfo)))));
      is_operator_test.Expect.equals(true, (!dart._runtime.helpers.TypeSystemHelper.getTrueType(null).isSubtypeOf(dart2java$localTypeEnv.evaluate(new dart._runtime.types.simple.InterfaceTypeExpr(is_operator_test.AI.dart2java$typeInfo)))));
      is_operator_test.Expect.equals(true, (!dart._runtime.helpers.TypeSystemHelper.getTrueType(null).isSubtypeOf(dart2java$localTypeEnv.evaluate(new dart._runtime.types.simple.InterfaceTypeExpr(is_operator_test.I.dart2java$typeInfo)))));
    }
}
