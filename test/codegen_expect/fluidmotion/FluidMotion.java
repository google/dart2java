package fluidmotion;

public class FluidMotion extends fluidmotion.BenchmarkBase implements fluidmotion.FluidMotion_interface
{
    public static dart._runtime.types.simple.InterfaceTypeInfo dart2java$typeInfo = new dart._runtime.types.simple.InterfaceTypeInfo(fluidmotion.FluidMotion.class, fluidmotion.FluidMotion_interface.class);
    private static dart._runtime.types.simple.InterfaceTypeExpr dart2java$typeExpr_BenchmarkBase = new dart._runtime.types.simple.InterfaceTypeExpr(fluidmotion.BenchmarkBase.dart2java$typeInfo);
    static {
      fluidmotion.FluidMotion.dart2java$typeInfo.superclass = dart2java$typeExpr_BenchmarkBase;
    }
    static {
      final dart._runtime.types.simple.TypeEnvironment dart2java$localTypeEnv = dart._runtime.types.simple.TypeEnvironment.ROOT;
      fluidmotion.FluidMotion.framesTillAddingPoints = 0;
      fluidmotion.FluidMotion.framesBetweenAddingPoints = 5;
    }
    public static fluidmotion.FluidField_interface solver;
    public static int framesTillAddingPoints;
    public static int framesBetweenAddingPoints;
  
    public FluidMotion(dart._runtime.helpers.ConstructorHelper.EmptyConstructorMarker arg, dart._runtime.types.simple.Type type)
    {
      super(arg, type);
    }
  
    public static void setupFluidMotion()
    {
      final dart._runtime.types.simple.TypeEnvironment dart2java$localTypeEnv = dart._runtime.types.simple.TypeEnvironment.ROOT;
      fluidmotion.FluidMotion.framesTillAddingPoints = 0;
      fluidmotion.FluidMotion.framesBetweenAddingPoints = 5;
      fluidmotion.FluidMotion.solver = fluidmotion.FluidField.factory$create(null, 128, 128, 20);
    }
    public static void runFluidMotion()
    {
      final dart._runtime.types.simple.TypeEnvironment dart2java$localTypeEnv = dart._runtime.types.simple.TypeEnvironment.ROOT;
      fluidmotion.FluidMotion.setupFluidMotion();
      for (int i = 0; (i < 10); i = (i + 1))
      {
        fluidmotion.FluidMotion.solver.update();
      }
      fluidmotion.FluidMotion.solver.validate(758.9012130174812, (-352.56376676179076), (-357.3690235879736));
    }
    public static void main()
    {
      final dart._runtime.types.simple.TypeEnvironment dart2java$localTypeEnv = dart._runtime.types.simple.TypeEnvironment.ROOT;
      fluidmotion.FluidMotion.runFluidMotion();
    }
    public static void addPoints(fluidmotion.Field_interface field)
    {
      final dart._runtime.types.simple.TypeEnvironment dart2java$localTypeEnv = dart._runtime.types.simple.TypeEnvironment.ROOT;
      int n = 64;
      for (int i = 1; (i <= n); i = (i + 1))
      {
        field.setVelocity(i, i, dart._runtime.helpers.IntegerHelper.toDouble(n), dart._runtime.helpers.IntegerHelper.toDouble(n));
        field.setDensity(i, i, 5.0);
        field.setVelocity(i, (n - i), (-dart._runtime.helpers.IntegerHelper.toDouble(n)), (-dart._runtime.helpers.IntegerHelper.toDouble(n)));
        field.setDensity(i, (n - i), 20.0);
        field.setVelocity((128 - i), (n + i), (-dart._runtime.helpers.IntegerHelper.toDouble(n)), (-dart._runtime.helpers.IntegerHelper.toDouble(n)));
        field.setDensity((128 - i), (n + i), 30.0);
      }
    }
    public static void prepareFrame(fluidmotion.Field_interface field)
    {
      final dart._runtime.types.simple.TypeEnvironment dart2java$localTypeEnv = dart._runtime.types.simple.TypeEnvironment.ROOT;
      if ((fluidmotion.FluidMotion.framesTillAddingPoints == 0))
      {
        fluidmotion.FluidMotion.addPoints(field);
        fluidmotion.FluidMotion.framesTillAddingPoints = fluidmotion.FluidMotion.framesBetweenAddingPoints;
        fluidmotion.FluidMotion.framesBetweenAddingPoints = (fluidmotion.FluidMotion.framesBetweenAddingPoints + 1);
      }
      else
      {
        fluidmotion.FluidMotion.framesTillAddingPoints = (fluidmotion.FluidMotion.framesTillAddingPoints - 1);
      }
    }
    public void warmup()
    {
      final dart._runtime.types.simple.TypeEnvironment dart2java$localTypeEnv = this.dart2java$type.env;
      fluidmotion.FluidMotion.runFluidMotion();
    }
    public void exercise()
    {
      final dart._runtime.types.simple.TypeEnvironment dart2java$localTypeEnv = this.dart2java$type.env;
      fluidmotion.FluidMotion.runFluidMotion();
    }
    public void _constructor()
    {
      final dart._runtime.types.simple.TypeEnvironment dart2java$localTypeEnv = this.dart2java$type.env;
      super._constructor("FluidMotion");
    }
    public static fluidmotion.FluidField_interface getSolver()
    {
      return fluidmotion.FluidMotion.solver;
    }
    public static int getFramesTillAddingPoints()
    {
      return fluidmotion.FluidMotion.framesTillAddingPoints;
    }
    public static int getFramesBetweenAddingPoints()
    {
      return fluidmotion.FluidMotion.framesBetweenAddingPoints;
    }
    public static fluidmotion.FluidField_interface setSolver(fluidmotion.FluidField_interface value)
    {
      fluidmotion.FluidMotion.solver = value;
      return value;
    }
    public static int setFramesTillAddingPoints(int value)
    {
      fluidmotion.FluidMotion.framesTillAddingPoints = value;
      return value;
    }
    public static int setFramesBetweenAddingPoints(int value)
    {
      fluidmotion.FluidMotion.framesBetweenAddingPoints = value;
      return value;
    }
    public static fluidmotion.FluidMotion_interface _new(dart._runtime.types.simple.Type type)
    {
      fluidmotion.FluidMotion result;
      result = new fluidmotion.FluidMotion(((dart._runtime.helpers.ConstructorHelper.EmptyConstructorMarker) null), type);
      result._constructor();
      return result;
    }
}
