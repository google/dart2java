package is_operator_test;

public class Expect extends dart._runtime.base.DartObject implements is_operator_test.Expect_interface
{
    public static dart._runtime.types.simple.InterfaceTypeInfo dart2java$typeInfo = new dart._runtime.types.simple.InterfaceTypeInfo("is_operator_test.Expect");
    static {
      is_operator_test.Expect.dart2java$typeInfo.superclass = new dart._runtime.types.simple.InterfaceTypeExpr(dart._runtime.helpers.ObjectHelper.dart2java$typeInfo);
    }
    public int x;
  
    public Expect(dart._runtime.types.simple.Type type)
    {
      super((dart._runtime.helpers.ConstructorHelper.EmptyConstructorMarker) null, type);
      this._constructor();
    }
    public Expect(dart._runtime.helpers.ConstructorHelper.EmptyConstructorMarker arg, dart._runtime.types.simple.Type type)
    {
      super(arg, type);
    }
  
    protected void _constructor()
    {
      final dart._runtime.types.simple.TypeEnvironment dart2java$localTypeEnv = this.dart2java$type.env;
      this.x = 0;
      super._constructor();
    }
    public static void equals(java.lang.Object a, java.lang.Object b)
    {
      final dart._runtime.types.simple.TypeEnvironment dart2java$localTypeEnv = dart._runtime.types.simple.TypeEnvironment.ROOT;
      if ((!dart._runtime.helpers.ObjectHelper.operatorEqual(a, b)))
      {
        dart.core.__TopLevel.print((((("Expected " + a.toString()) + ", got ") + b.toString()) + ""));
        dart.core.__TopLevel.print(dart._runtime.helpers.StringHelper.operatorStar("*", 80));
        dart.core.__TopLevel.print("(Ignore the NPE; it's just used to cause test failure)");
        is_operator_test.Expect_interface e = null;
        dart.core.__TopLevel.print(e.getX());
      }
    }
    public int getX()
    {
      return this.x;
    }
    public int setX(int value)
    {
      this.x = value;
      return value;
    }
}
