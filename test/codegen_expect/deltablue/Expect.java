package deltablue;

public class Expect extends dart._runtime.base.DartObject
{
    public static dart._runtime.types.simple.InterfaceTypeInfo dart2java$typeInfo = new dart._runtime.types.simple.InterfaceTypeInfo("file:///usr/local/google/home/andrewkrieger/ddc-java/gen/codegen_tests/deltablue.dart", "Expect");
    static {
      deltablue.Expect.dart2java$typeInfo.superclass = new dart._runtime.types.simple.InterfaceTypeExpr(dart._runtime.helpers.ObjectHelper.dart2java$typeInfo);
    }
  
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
      super._constructor();
    }
    public static void equals(java.lang.Object expected, java.lang.Object actual)
    {
      final dart._runtime.types.simple.TypeEnvironment dart2java$localTypeEnv = dart._runtime.types.simple.TypeEnvironment.ROOT;
      if ((!dart._runtime.helpers.ObjectHelper.operatorEqual(expected, actual)))
      {
        dart.core.__TopLevel.print("Values not equal: ");
        dart.core.__TopLevel.print((java.lang.Object) expected);
        dart.core.__TopLevel.print("vs ");
        dart.core.__TopLevel.print((java.lang.Object) actual);
      }
    }
    public static void listEquals(dart._runtime.base.DartList<java.lang.Object> expected, dart._runtime.base.DartList<java.lang.Object> actual)
    {
      final dart._runtime.types.simple.TypeEnvironment dart2java$localTypeEnv = dart._runtime.types.simple.TypeEnvironment.ROOT;
      if ((!(expected.getLength() == actual.getLength())))
      {
        dart.core.__TopLevel.print("Lists have different lengths: ");
        dart.core.__TopLevel.print(expected.getLength());
        dart.core.__TopLevel.print("vs ");
        dart.core.__TopLevel.print(actual.getLength());
      }
      for (int i = 0; (i < actual.getLength()); i = (i + 1))
      {
        deltablue.Expect.equals(expected.operatorAt(i), actual.operatorAt(i));
      }
    }
    public void fail(java.lang.String message)
    {
      final dart._runtime.types.simple.TypeEnvironment dart2java$localTypeEnv = this.dart2java$type.env;
      dart.core.__TopLevel.print(message);
    }
}
