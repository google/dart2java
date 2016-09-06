package havlak;

public class BenchmarkBase extends dart._runtime.base.DartObject implements havlak.BenchmarkBase_interface
{
    public static dart._runtime.types.simple.InterfaceTypeInfo dart2java$typeInfo = new dart._runtime.types.simple.InterfaceTypeInfo(havlak.BenchmarkBase.class, havlak.BenchmarkBase_interface.class);
    static {
      havlak.BenchmarkBase.dart2java$typeInfo.superclass = new dart._runtime.types.simple.InterfaceTypeExpr(dart._runtime.helpers.ObjectHelper.dart2java$typeInfo);
    }
    static {
      final dart._runtime.types.simple.TypeEnvironment dart2java$localTypeEnv = dart._runtime.types.simple.TypeEnvironment.ROOT;
      havlak.BenchmarkBase.iters = 1000;
    }
    public java.lang.String name;
    public static int iters;
  
    public BenchmarkBase(dart._runtime.types.simple.Type type, java.lang.String name)
    {
      super(((dart._runtime.helpers.ConstructorHelper.EmptyConstructorMarker) null), type);
      this._constructor(name);
    }
    public BenchmarkBase(dart._runtime.helpers.ConstructorHelper.EmptyConstructorMarker arg, dart._runtime.types.simple.Type type)
    {
      super(arg, type);
    }
  
    protected void _constructor(java.lang.String name)
    {
      final dart._runtime.types.simple.TypeEnvironment dart2java$localTypeEnv = this.dart2java$type.env;
      this.name = name;
      super._constructor();
    }
    public void run()
    {
      final dart._runtime.types.simple.TypeEnvironment dart2java$localTypeEnv = this.dart2java$type.env;
    }
    public void warmup()
    {
      final dart._runtime.types.simple.TypeEnvironment dart2java$localTypeEnv = this.dart2java$type.env;
      this.run();
    }
    public void exercise()
    {
      final dart._runtime.types.simple.TypeEnvironment dart2java$localTypeEnv = this.dart2java$type.env;
      for (int i = 0; (i < havlak.BenchmarkBase.iters); i = (i + 1))
      {
        this.run();
      }
    }
    public void setup()
    {
      final dart._runtime.types.simple.TypeEnvironment dart2java$localTypeEnv = this.dart2java$type.env;
    }
    public void teardown()
    {
      final dart._runtime.types.simple.TypeEnvironment dart2java$localTypeEnv = this.dart2java$type.env;
    }
    public double measureForWarumup(int timeMinimum)
    {
      final dart._runtime.types.simple.TypeEnvironment dart2java$localTypeEnv = this.dart2java$type.env;
      int time = 0;
      int iter = 0;
      dart.core.Stopwatch_interface watch = new dart.core.Stopwatch(dart2java$localTypeEnv.evaluate(new dart._runtime.types.simple.InterfaceTypeExpr(dart.core.Stopwatch.dart2java$typeInfo)));
      watch.start();
      int elapsed = 0;
      while ((elapsed < timeMinimum))
      {
        this.warmup();
        elapsed = watch.getElapsedMilliseconds();
        iter = (iter + 1);
      }
      return dart._runtime.helpers.DoubleHelper.operatorDivide(dart._runtime.helpers.DoubleHelper.operatorDivide(dart._runtime.helpers.DoubleHelper.operatorStar(1000.0, elapsed), iter), havlak.BenchmarkBase.iters);
    }
    public double measureForExercise(int timeMinimum)
    {
      final dart._runtime.types.simple.TypeEnvironment dart2java$localTypeEnv = this.dart2java$type.env;
      int time = 0;
      int iter = 0;
      dart.core.Stopwatch_interface watch = new dart.core.Stopwatch(dart2java$localTypeEnv.evaluate(new dart._runtime.types.simple.InterfaceTypeExpr(dart.core.Stopwatch.dart2java$typeInfo)));
      watch.start();
      int elapsed = 0;
      while ((elapsed < timeMinimum))
      {
        this.exercise();
        elapsed = watch.getElapsedMilliseconds();
        iter = (iter + 1);
      }
      return dart._runtime.helpers.DoubleHelper.operatorDivide(dart._runtime.helpers.DoubleHelper.operatorDivide(dart._runtime.helpers.DoubleHelper.operatorStar(1000.0, elapsed), iter), havlak.BenchmarkBase.iters);
    }
    public double measure()
    {
      final dart._runtime.types.simple.TypeEnvironment dart2java$localTypeEnv = this.dart2java$type.env;
      this.setup();
      this.measureForWarumup(100);
      double result = this.measureForExercise((2 * 1000));
      this.teardown();
      return result;
    }
    public void report()
    {
      final dart._runtime.types.simple.TypeEnvironment dart2java$localTypeEnv = this.dart2java$type.env;
      double score = this.measure();
      dart.core.__TopLevel.print((((("" + this.getName().toString()) + "(RunTime): ") + score) + " us."));
    }
    public java.lang.String getName()
    {
      return this.name;
    }
    public static int getIters()
    {
      return havlak.BenchmarkBase.iters;
    }
    public static int setIters(int value)
    {
      havlak.BenchmarkBase.iters = value;
      return value;
    }
}
