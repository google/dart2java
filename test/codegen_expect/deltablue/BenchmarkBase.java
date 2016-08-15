package deltablue;

public class BenchmarkBase extends dart._runtime.base.DartObject
{
    public java.lang.String name = null;
    public static java.lang.Integer iters = 1000;
  
    public BenchmarkBase(java.lang.String name)
    {
      super((dart._runtime.helpers.ConstructorHelper.EmptyConstructorMarker) null);
      this._constructor(name);
    }
    public BenchmarkBase(dart._runtime.helpers.ConstructorHelper.EmptyConstructorMarker arg)
    {
      super(arg);
    }
  
    protected void _constructor(java.lang.String name)
    {
      this.iters = 1000;
      this.name = name;
      super._constructor();
    }
    public void run()
    {
      
    }
    public void warmup()
    {
      this.run();
    }
    public void exercise()
    {
      for (java.lang.Integer i = 0; dart._runtime.helpers.IntegerHelper.operatorLess(i, deltablue.BenchmarkBase.iters); i = dart._runtime.helpers.IntegerHelper.operatorPlus(i, 1))
      {
        this.run();
      }
    }
    public void setup()
    {
      
    }
    public void teardown()
    {
      
    }
    public java.lang.Double measureForWarumup(java.lang.Integer timeMinimum)
    {
      java.lang.Integer time = 0;
      java.lang.Integer iter = 0;
      dart.core.Stopwatch watch = new dart.core.Stopwatch();
      watch.start();
      java.lang.Integer elapsed = 0;
      while (dart._runtime.helpers.IntegerHelper.operatorLess(elapsed, timeMinimum))
      {
        this.warmup();
        elapsed = watch.getElapsedMilliseconds();
        iter = dart._runtime.helpers.IntegerHelper.operatorPlus(iter, 1);
      }
      return dart._runtime.helpers.DoubleHelper.operatorDivide(dart._runtime.helpers.DoubleHelper.operatorDivide(dart._runtime.helpers.DoubleHelper.operatorStar(1000.0, elapsed), iter), deltablue.BenchmarkBase.iters);
    }
    public java.lang.Double measureForExercise(java.lang.Integer timeMinimum)
    {
      java.lang.Integer time = 0;
      java.lang.Integer iter = 0;
      dart.core.Stopwatch watch = new dart.core.Stopwatch();
      watch.start();
      java.lang.Integer elapsed = 0;
      while (dart._runtime.helpers.IntegerHelper.operatorLess(elapsed, timeMinimum))
      {
        this.exercise();
        elapsed = watch.getElapsedMilliseconds();
        iter = dart._runtime.helpers.IntegerHelper.operatorPlus(iter, 1);
      }
      return dart._runtime.helpers.DoubleHelper.operatorDivide(dart._runtime.helpers.DoubleHelper.operatorDivide(dart._runtime.helpers.DoubleHelper.operatorStar(1000.0, elapsed), iter), deltablue.BenchmarkBase.iters);
    }
    public java.lang.Double measure()
    {
      this.setup();
      this.measureForWarumup(100);
      java.lang.Double result = this.measureForExercise(dart._runtime.helpers.IntegerHelper.operatorStar(10, 1000));
      this.teardown();
      return result;
    }
    public void report()
    {
      java.lang.Double score = this.measure();
      dart.core.__TopLevel.print((((("".toString() + this.getName().toString()) + "(RunTime): ".toString()) + score.toString()) + " us.".toString()));
    }
    public java.lang.String getName()
    {
      return this.name;
    }
    public static java.lang.Integer getIters()
    {
      return deltablue.BenchmarkBase.iters;
    }
    public static java.lang.Integer setIters(java.lang.Integer value)
    {
      deltablue.BenchmarkBase.iters = value;
      return value;
    }
}
