package deltablue;

public class DeltaBlue extends deltablue.BenchmarkBase
{
    public static dart._runtime.types.simple.InterfaceTypeInfo dart2java$typeInfo = new dart._runtime.types.simple.InterfaceTypeInfo("file:///usr/local/google/home/andrewkrieger/ddc-java/gen/codegen_tests/deltablue.dart", "DeltaBlue");
    static {
      deltablue.DeltaBlue.dart2java$typeInfo.superclass = new dart._runtime.types.simple.InterfaceTypeExpr(deltablue.BenchmarkBase.dart2java$typeInfo);
    }
  
    public DeltaBlue()
    {
      super((dart._runtime.helpers.ConstructorHelper.EmptyConstructorMarker) null);
      this._constructor();
    }
    public DeltaBlue(dart._runtime.helpers.ConstructorHelper.EmptyConstructorMarker arg)
    {
      super(arg);
    }
  
    protected void _constructor()
    {
      super._constructor("DeltaBlue");
    }
    public void run()
    {
      deltablue.__TopLevel.chainTest(100);
      deltablue.__TopLevel.projectionTest(100);
    }
}
