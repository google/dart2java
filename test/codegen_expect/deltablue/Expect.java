package deltablue;

public class Expect extends dart._runtime.base.DartObject
{
    public static dart._runtime.types.simple.InterfaceTypeInfo dart2java$typeInfo = new dart._runtime.types.simple.InterfaceTypeInfo("file:///usr/local/google/home/andrewkrieger/ddc-java/gen/codegen_tests/deltablue.dart", "Expect");
    static {
      deltablue.Expect.dart2java$typeInfo.superclass = new dart._runtime.types.simple.InterfaceTypeExpr(dart._runtime.helpers.ObjectHelper.dart2java$typeInfo);
    }
  
    public Expect()
    {
      super((dart._runtime.helpers.ConstructorHelper.EmptyConstructorMarker) null);
      this._constructor();
    }
    public Expect(dart._runtime.helpers.ConstructorHelper.EmptyConstructorMarker arg)
    {
      super(arg);
    }
  
    protected void _constructor()
    {
      super._constructor();
    }
    public static void equals(java.lang.Object expected, java.lang.Object actual)
    {
      if ((!dart._runtime.helpers.ObjectHelper.operatorEqual(expected, actual)))
      {
        dart.core.__TopLevel.print("Values not equal: ");
        dart.core.__TopLevel.print(expected);
        dart.core.__TopLevel.print("vs ");
        dart.core.__TopLevel.print(actual);
      }
    }
    public static void listEquals(dart._runtime.base.DartList<java.lang.Object> expected, dart._runtime.base.DartList<java.lang.Object> actual)
    {
      if ((!dart._runtime.helpers.ObjectHelper.operatorEqual(expected.getLength(), actual.getLength())))
      {
        dart.core.__TopLevel.print("Lists have different lengths: ");
        dart.core.__TopLevel.print(expected.getLength());
        dart.core.__TopLevel.print("vs ");
        dart.core.__TopLevel.print(actual.getLength());
      }
      for (java.lang.Integer i = 0; dart._runtime.helpers.IntegerHelper.operatorLess(i, actual.getLength()); i = dart._runtime.helpers.IntegerHelper.operatorPlus(i, 1))
      {
        deltablue.Expect.equals(expected.operatorAt(i), actual.operatorAt(i));
      }
    }
    public void fail(java.lang.String message)
    {
      dart.core.__TopLevel.print(message);
    }
}
