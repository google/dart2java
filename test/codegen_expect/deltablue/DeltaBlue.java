package deltablue;

public class DeltaBlue extends deltablue.BenchmarkBase
{
    public static dart._runtime.types.simple.InterfaceTypeInfo dart2java$typeInfo = new dart._runtime.types.simple.InterfaceTypeInfo("deltablue.DeltaBlue");
    static {
      deltablue.DeltaBlue.dart2java$typeInfo.superclass = new dart._runtime.types.simple.InterfaceTypeExpr(deltablue.BenchmarkBase.dart2java$typeInfo);
    }
  
    public DeltaBlue(dart._runtime.types.simple.Type type)
    {
      super((dart._runtime.helpers.ConstructorHelper.EmptyConstructorMarker) null, type);
      this._constructor();
    }
    public DeltaBlue(dart._runtime.helpers.ConstructorHelper.EmptyConstructorMarker arg, dart._runtime.types.simple.Type type)
    {
      super(arg, type);
    }
  
    protected void _constructor()
    {
      final dart._runtime.types.simple.TypeEnvironment dart2java$localTypeEnv = this.dart2java$type.env;
      super._constructor("DeltaBlue");
    }
    public void run()
    {
      final dart._runtime.types.simple.TypeEnvironment dart2java$localTypeEnv = this.dart2java$type.env;
      deltablue.__TopLevel.chainTest(100);
      deltablue.__TopLevel.projectionTest(100);
    }
}
