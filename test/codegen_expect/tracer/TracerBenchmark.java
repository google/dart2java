package tracer;

public class TracerBenchmark extends tracer.BenchmarkBase
{
    public static dart._runtime.types.simple.InterfaceTypeInfo dart2java$typeInfo = new dart._runtime.types.simple.InterfaceTypeInfo("file:///usr/local/google/home/springerm/ddc-java/gen/codegen_tests/tracer.dart", "TracerBenchmark");
    static {
      tracer.TracerBenchmark.dart2java$typeInfo.superclass = new dart._runtime.types.simple.InterfaceTypeExpr(tracer.BenchmarkBase.dart2java$typeInfo);
    }
  
    public TracerBenchmark()
    {
      super((dart._runtime.helpers.ConstructorHelper.EmptyConstructorMarker) null);
      this._constructor();
    }
    public TracerBenchmark(dart._runtime.helpers.ConstructorHelper.EmptyConstructorMarker arg)
    {
      super(arg);
    }
  
    protected void _constructor()
    {
      super._constructor("Tracer");
    }
    public void warmup()
    {
      tracer.__TopLevel.renderScene(null);
    }
    public void exercise()
    {
      tracer.__TopLevel.renderScene(null);
    }
}
