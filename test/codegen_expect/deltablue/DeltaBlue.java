package deltablue;

public class DeltaBlue extends deltablue.BenchmarkBase implements deltablue.DeltaBlue_interface
{
    public static dart._runtime.types.simple.InterfaceTypeInfo dart2java$typeInfo = new dart._runtime.types.simple.InterfaceTypeInfo(deltablue.DeltaBlue.class, deltablue.DeltaBlue_interface.class);
    private static dart._runtime.types.simple.InterfaceTypeExpr dart2java$typeExpr_BenchmarkBase = new dart._runtime.types.simple.InterfaceTypeExpr(deltablue.BenchmarkBase.dart2java$typeInfo);
    static {
      deltablue.DeltaBlue.dart2java$typeInfo.superclass = dart2java$typeExpr_BenchmarkBase;
    }
  
    public DeltaBlue(dart._runtime.helpers.ConstructorHelper.EmptyConstructorMarker arg, dart._runtime.types.simple.Type type)
    {
      super(arg, type);
    }
  
    public void run()
    {
      final dart._runtime.types.simple.TypeEnvironment dart2java$localTypeEnv = this.dart2java$type.env;
      deltablue.__TopLevel.chainTest(100);
      deltablue.__TopLevel.projectionTest(100);
    }
    public void _constructor()
    {
      final dart._runtime.types.simple.TypeEnvironment dart2java$localTypeEnv = this.dart2java$type.env;
      super._constructor("DeltaBlue");
    }
    public static deltablue.DeltaBlue_interface _new_DeltaBlue$(dart._runtime.types.simple.Type type)
    {
      deltablue.DeltaBlue result;
      result = new deltablue.DeltaBlue(((dart._runtime.helpers.ConstructorHelper.EmptyConstructorMarker) null), type);
      result._constructor();
      return result;
    }
}
