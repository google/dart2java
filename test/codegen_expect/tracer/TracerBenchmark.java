package tracer;

public class TracerBenchmark extends tracer.BenchmarkBase
{
    public static dart._runtime.types.simple.InterfaceTypeInfo dart2java$typeInfo = new dart._runtime.types.simple.InterfaceTypeInfo("file:///usr/local/google/home/andrewkrieger/ddc-java/gen/codegen_tests/tracer.dart", "TracerBenchmark");
    static {
      tracer.TracerBenchmark.dart2java$typeInfo.superclass = new dart._runtime.types.simple.InterfaceTypeExpr(tracer.BenchmarkBase.dart2java$typeInfo);
    }
  
    public TracerBenchmark(dart._runtime.types.simple.Type type)
    {
      super((dart._runtime.helpers.ConstructorHelper.EmptyConstructorMarker) null, type);
      this._constructor();
    }
    public TracerBenchmark(dart._runtime.helpers.ConstructorHelper.EmptyConstructorMarker arg, dart._runtime.types.simple.Type type)
    {
      super(arg, type);
    }
  
    protected void _constructor()
    {
      final dart._runtime.types.simple.TypeEnvironment dart2java$localTypeEnv = this.dart2java$type.env;
      super._constructor("Tracer");
    }
    public void warmup()
    {
      final dart._runtime.types.simple.TypeEnvironment dart2java$localTypeEnv = this.dart2java$type.env;
      tracer.__TopLevel.renderScene(null);
    }
    public void exercise()
    {
      final dart._runtime.types.simple.TypeEnvironment dart2java$localTypeEnv = this.dart2java$type.env;
      tracer.__TopLevel.renderScene(null);
    }
}
