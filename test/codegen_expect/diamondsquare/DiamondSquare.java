package diamondsquare;

public class DiamondSquare extends diamondsquare.BenchmarkBase implements diamondsquare.DiamondSquare_interface
{
    public static dart._runtime.types.simple.InterfaceTypeInfo dart2java$typeInfo = new dart._runtime.types.simple.InterfaceTypeInfo(diamondsquare.DiamondSquare.class, diamondsquare.DiamondSquare_interface.class);
    private static dart._runtime.types.simple.InterfaceTypeExpr dart2java$typeExpr_World = new dart._runtime.types.simple.InterfaceTypeExpr(diamondsquare.World.dart2java$typeInfo);
    private static dart._runtime.types.simple.InterfaceTypeExpr dart2java$typeExpr_BenchmarkBase = new dart._runtime.types.simple.InterfaceTypeExpr(diamondsquare.BenchmarkBase.dart2java$typeInfo);
    static {
      diamondsquare.DiamondSquare.dart2java$typeInfo.superclass = dart2java$typeExpr_BenchmarkBase;
    }
  
    public DiamondSquare(dart._runtime.helpers.ConstructorHelper.EmptyConstructorMarker arg, dart._runtime.types.simple.Type type)
    {
      super(arg, type);
    }
  
    public void run()
    {
      final dart._runtime.types.simple.TypeEnvironment dart2java$localTypeEnv = this.dart2java$type.env;
      diamondsquare.World_interface w = diamondsquare.World._new(dart2java$localTypeEnv.evaluate(dart2java$typeExpr_World));
      w.Base();
      w.Generate();
      w.Smooth();
    }
    public void _constructor()
    {
      final dart._runtime.types.simple.TypeEnvironment dart2java$localTypeEnv = this.dart2java$type.env;
      super._constructor("DiamondSquare");
    }
    public static diamondsquare.DiamondSquare_interface _new(dart._runtime.types.simple.Type type)
    {
      diamondsquare.DiamondSquare_interface result;
      result = new diamondsquare.DiamondSquare(((dart._runtime.helpers.ConstructorHelper.EmptyConstructorMarker) null), type);
      result._constructor();
      return result;
    }
}
