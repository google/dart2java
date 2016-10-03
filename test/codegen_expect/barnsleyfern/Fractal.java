package barnsleyfern;

public class Fractal extends barnsleyfern.BenchmarkBase implements barnsleyfern.Fractal_interface
{
    public static dart._runtime.types.simple.InterfaceTypeInfo dart2java$typeInfo = new dart._runtime.types.simple.InterfaceTypeInfo(barnsleyfern.Fractal.class, barnsleyfern.Fractal_interface.class);
    private static dart._runtime.types.simple.InterfaceTypeExpr dart2java$typeExpr_BenchmarkBase = new dart._runtime.types.simple.InterfaceTypeExpr(barnsleyfern.BenchmarkBase.dart2java$typeInfo);
    static {
      barnsleyfern.Fractal.dart2java$typeInfo.superclass = dart2java$typeExpr_BenchmarkBase;
    }
  
    public Fractal(dart._runtime.helpers.ConstructorHelper.EmptyConstructorMarker arg, dart._runtime.types.simple.Type type)
    {
      super(arg, type);
    }
  
    public void run()
    {
      final dart._runtime.types.simple.TypeEnvironment dart2java$localTypeEnv = this.dart2java$type.env;
      this.drawBarnsleyFern();
    }
    public int drawBarnsleyFern()
    {
      final dart._runtime.types.simple.TypeEnvironment dart2java$localTypeEnv = this.dart2java$type.env;
      int checksum = 0;
      double x = 0.0;
      double y = 0.0;
      double nextx = 0.0;
      double nexty = 0.0;
      double plotDecider = 0.0;
      dart.math.Random_interface rng = dart.math.Random.factory$(1337);
      x = rng.nextDouble();
      y = rng.nextDouble();
      for (int i = 0; (i < 50000); i = (i + 1))
      {
        plotDecider = rng.nextDouble();
        if ((plotDecider < 0.01))
        {
          x = 0.0;
          y = (0.16 * y);
        }
        else
        {
          if ((plotDecider < 0.86))
          {
            nextx = ((0.85 * x) + (0.04 * y));
            nexty = (((0.04 * x) + (0.85 * y)) + 1.6);
            x = nextx;
            y = nexty;
          }
          else
          {
            if ((plotDecider < 0.92))
            {
              nextx = ((0.2 * x) - (0.26 * y));
              nexty = (((0.23 * x) + (0.22 * y)) + 1.6);
              x = nextx;
              y = nexty;
            }
            else
            {
              nextx = (((-0.15) * x) + (0.28 * y));
              nexty = (((0.26 * x) + (0.24 * y)) + 0.44);
              x = nextx;
              y = nexty;
            }
          }
        }
        int col = (100 + rng.nextInt(143));
        checksum = (checksum + ((((100 + dart._runtime.helpers.DoubleHelper.toInt(dart._runtime.helpers.DoubleHelper.operatorStar(x, 50))) + 500) - dart._runtime.helpers.DoubleHelper.toInt(dart._runtime.helpers.DoubleHelper.operatorStar(y, 40))) % 9971));
      }
      return checksum;
    }
    public void _constructor()
    {
      final dart._runtime.types.simple.TypeEnvironment dart2java$localTypeEnv = this.dart2java$type.env;
      super._constructor("Barnsley Fern Fractal");
    }
    public static barnsleyfern.Fractal_interface _new(dart._runtime.types.simple.Type type)
    {
      barnsleyfern.Fractal_interface result;
      result = new barnsleyfern.Fractal(((dart._runtime.helpers.ConstructorHelper.EmptyConstructorMarker) null), type);
      result._constructor();
      return result;
    }
}
