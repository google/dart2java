package diamondsquare;

public class BenchmarkBase extends dart._runtime.base.DartObject implements diamondsquare.BenchmarkBase_interface
{
    public static dart._runtime.types.simple.InterfaceTypeInfo dart2java$typeInfo = new dart._runtime.types.simple.InterfaceTypeInfo(diamondsquare.BenchmarkBase.class, diamondsquare.BenchmarkBase_interface.class);
    private static dart._runtime.types.simple.InterfaceTypeExpr dart2java$typeExpr_Stopwatch = new dart._runtime.types.simple.InterfaceTypeExpr(dart.core.Stopwatch.dart2java$typeInfo);
    private static dart._runtime.types.simple.InterfaceTypeExpr dart2java$typeExpr_Object = new dart._runtime.types.simple.InterfaceTypeExpr(dart._runtime.helpers.ObjectHelper.dart2java$typeInfo);
    static {
      diamondsquare.BenchmarkBase.dart2java$typeInfo.superclass = dart2java$typeExpr_Object;
    }
    static {
      final dart._runtime.types.simple.TypeEnvironment dart2java$localTypeEnv = dart._runtime.types.simple.TypeEnvironment.ROOT;
      diamondsquare.BenchmarkBase.iters = 1000;
    }
    public java.lang.String name;
    public static int iters;
  
    public BenchmarkBase(dart._runtime.helpers.ConstructorHelper.EmptyConstructorMarker arg, dart._runtime.types.simple.Type type)
    {
      super(arg, type);
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
      for (int i = 0; (i < diamondsquare.BenchmarkBase.iters); i = (i + 1))
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
      dart.core.Stopwatch_interface watch = dart.core.Stopwatch._new_Stopwatch$(dart2java$localTypeEnv.evaluate(dart2java$typeExpr_Stopwatch));
      watch.start();
      int elapsed = 0;
      while ((elapsed < timeMinimum))
      {
        this.warmup();
        elapsed = watch.getElapsedMilliseconds();
        iter = (iter + 1);
      }
      return dart._runtime.helpers.DoubleHelper.operatorDivide(dart._runtime.helpers.DoubleHelper.operatorDivide(dart._runtime.helpers.DoubleHelper.operatorStar(1000.0, elapsed), iter), diamondsquare.BenchmarkBase.iters);
    }
    public double measureForExercise(int timeMinimum)
    {
      final dart._runtime.types.simple.TypeEnvironment dart2java$localTypeEnv = this.dart2java$type.env;
      int time = 0;
      int iter = 0;
      dart.core.Stopwatch_interface watch = dart.core.Stopwatch._new_Stopwatch$(dart2java$localTypeEnv.evaluate(dart2java$typeExpr_Stopwatch));
      watch.start();
      int elapsed = 0;
      while ((elapsed < timeMinimum))
      {
        this.exercise();
        elapsed = watch.getElapsedMilliseconds();
        iter = (iter + 1);
      }
      return dart._runtime.helpers.DoubleHelper.operatorDivide(dart._runtime.helpers.DoubleHelper.operatorDivide(dart._runtime.helpers.DoubleHelper.operatorStar(1000.0, elapsed), iter), diamondsquare.BenchmarkBase.iters);
    }
    public double measure()
    {
      final dart._runtime.types.simple.TypeEnvironment dart2java$localTypeEnv = this.dart2java$type.env;
      this.setup();
      this.measureForWarumup(1000);
      double result = this.measureForExercise(10000);
      this.teardown();
      return result;
    }
    public void report()
    {
      final dart._runtime.types.simple.TypeEnvironment dart2java$localTypeEnv = this.dart2java$type.env;
      double score = this.measure();
      dart.core.__TopLevel.print((((("" + this.getName().toString()) + "(RunTime): ") + score) + " us."));
    }
    public void _constructor(java.lang.String name)
    {
      final dart._runtime.types.simple.TypeEnvironment dart2java$localTypeEnv = this.dart2java$type.env;
      this.name = name;
      super._constructor();
    }
    public java.lang.String getName()
    {
      return this.name;
    }
    public static int getIters()
    {
      return diamondsquare.BenchmarkBase.iters;
    }
    public static int setIters(int value)
    {
      diamondsquare.BenchmarkBase.iters = value;
      return value;
    }
    public static diamondsquare.BenchmarkBase_interface _new_BenchmarkBase$(dart._runtime.types.simple.Type type, java.lang.String name)
    {
      diamondsquare.BenchmarkBase result;
      result = new diamondsquare.BenchmarkBase(((dart._runtime.helpers.ConstructorHelper.EmptyConstructorMarker) null), type);
      result._constructor(name);
      return result;
    }
}
