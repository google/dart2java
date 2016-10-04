package gameoflife;

public class GameOfLife extends gameoflife.BenchmarkBase implements gameoflife.GameOfLife_interface
{
    public static dart._runtime.types.simple.InterfaceTypeInfo dart2java$typeInfo = new dart._runtime.types.simple.InterfaceTypeInfo(gameoflife.GameOfLife.class, gameoflife.GameOfLife_interface.class);
    private static dart._runtime.types.simple.InterfaceTypeExpr dart2java$typeExpr_BenchmarkBase = new dart._runtime.types.simple.InterfaceTypeExpr(gameoflife.BenchmarkBase.dart2java$typeInfo);
    static {
      gameoflife.GameOfLife.dart2java$typeInfo.superclass = dart2java$typeExpr_BenchmarkBase;
    }
  
    public GameOfLife(dart._runtime.helpers.ConstructorHelper.EmptyConstructorMarker arg, dart._runtime.types.simple.Type type)
    {
      super(arg, type);
    }
  
    public void run()
    {
      final dart._runtime.types.simple.TypeEnvironment dart2java$localTypeEnv = this.dart2java$type.env;
      gameoflife.__TopLevel.gameOfLife();
    }
    public void _constructor()
    {
      final dart._runtime.types.simple.TypeEnvironment dart2java$localTypeEnv = this.dart2java$type.env;
      super._constructor("GameOfLife");
    }
    public static gameoflife.GameOfLife_interface _new_GameOfLife$(dart._runtime.types.simple.Type type)
    {
      gameoflife.GameOfLife result;
      result = new gameoflife.GameOfLife(((dart._runtime.helpers.ConstructorHelper.EmptyConstructorMarker) null), type);
      result._constructor();
      return result;
    }
}
