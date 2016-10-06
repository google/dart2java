package gameoflife;

public class __TopLevel
{
    private static dart._runtime.types.simple.InterfaceTypeExpr dart2java$typeExpr_GameOfLife = new dart._runtime.types.simple.InterfaceTypeExpr(gameoflife.GameOfLife.dart2java$typeInfo);
    private static dart._runtime.types.simple.InterfaceTypeExpr dart2java$typeExpr_Culture = new dart._runtime.types.simple.InterfaceTypeExpr(gameoflife.Culture.dart2java$typeInfo);
    private static dart._runtime.types.simple.InterfaceTypeExpr dart2java$typeExpr_List$ltCulture$gt = new dart._runtime.types.simple.InterfaceTypeExpr(dart.core.List.dart2java$typeInfo, new dart._runtime.types.simple.TypeExpr[] {new dart._runtime.types.simple.InterfaceTypeExpr(gameoflife.Culture.dart2java$typeInfo)});
  
  
  
    public static void main(String[] args)
    {
      final dart._runtime.types.simple.TypeEnvironment dart2java$localTypeEnv = dart._runtime.types.simple.TypeEnvironment.ROOT;
      dart.core.__TopLevel.print("Running benchmark...");
      gameoflife.GameOfLife._new_GameOfLife$(dart2java$localTypeEnv.evaluate(dart2java$typeExpr_GameOfLife)).report();
      dart.core.__TopLevel.print("Done.");
    }
    public static void gameOfLife()
    {
      final dart._runtime.types.simple.TypeEnvironment dart2java$localTypeEnv = dart._runtime.types.simple.TypeEnvironment.ROOT;
      int iterations = 10;
      java.lang.Object ce = null;
      java.lang.Object c2d = null;
      dart.math.Random_interface rng = dart.math.Random.factory$(1337);
      gameoflife.Culture_interface redCells = gameoflife.Culture._new_Culture$(dart2java$localTypeEnv.evaluate(dart2java$typeExpr_Culture));
      gameoflife.Culture_interface greenCells = gameoflife.Culture._new_Culture$(dart2java$localTypeEnv.evaluate(dart2java$typeExpr_Culture));
      gameoflife.Culture_interface blueCells = gameoflife.Culture._new_Culture$(dart2java$localTypeEnv.evaluate(dart2java$typeExpr_Culture));
      gameoflife.Culture_interface yellowCells = gameoflife.Culture._new_Culture$(dart2java$localTypeEnv.evaluate(dart2java$typeExpr_Culture));
      dart.core.List_interface<gameoflife.Culture_interface> cultures = ((dart.core.List_interface) dart._runtime.base.DartList.<gameoflife.Culture_interface>specialfactory$fromArguments(dart2java$localTypeEnv.evaluate(dart2java$typeExpr_List$ltCulture$gt), redCells, greenCells, blueCells, yellowCells));
      int cells = (699 + rng.nextInt(99));
      redCells.initPopulation(cells);
      greenCells.initPopulation(dart._runtime.helpers.IntegerHelper.operatorTruncatedDivide(cells, 1.1));
      blueCells.initPopulation(dart._runtime.helpers.IntegerHelper.operatorTruncatedDivide(cells, 1.2));
      yellowCells.initPopulation(dart._runtime.helpers.IntegerHelper.operatorTruncatedDivide(cells, 1.3));
      for (int i = 0; (i < iterations); i = (i + 1))
      {
        for (dart.core.Iterator_interface<gameoflife.Culture_interface> __tempVar_0 = ((dart.core.Iterator_interface<gameoflife.Culture_interface>) cultures.getIterator_Iterable()); __tempVar_0.moveNext_Iterator(); )
        {
          gameoflife.Culture_interface culture = __tempVar_0.getCurrent_Iterator();
          culture.update();
        }
      }
    }
}
