package gameoflife;

public class Culture extends dart._runtime.base.DartObject implements gameoflife.Culture_interface
{
    public static dart._runtime.types.simple.InterfaceTypeInfo dart2java$typeInfo = new dart._runtime.types.simple.InterfaceTypeInfo(gameoflife.Culture.class, gameoflife.Culture_interface.class);
    private static dart._runtime.types.simple.InterfaceTypeExpr dart2java$typeExpr_Cell = new dart._runtime.types.simple.InterfaceTypeExpr(gameoflife.Cell.dart2java$typeInfo);
    private static dart._runtime.types.simple.InterfaceTypeExpr dart2java$typeExpr_String = new dart._runtime.types.simple.InterfaceTypeExpr(dart._runtime.helpers.StringHelper.dart2java$typeInfo);
    private static dart._runtime.types.simple.InterfaceTypeExpr dart2java$typeExpr_Object = new dart._runtime.types.simple.InterfaceTypeExpr(dart._runtime.helpers.ObjectHelper.dart2java$typeInfo);
    static {
      gameoflife.Culture.dart2java$typeInfo.superclass = dart2java$typeExpr_Object;
    }
    public dart.core.Map_interface<java.lang.String, gameoflife.Cell_interface> cellDish;
    public int width;
    public dart.math.Random_interface rng;
  
    public Culture(dart._runtime.helpers.ConstructorHelper.EmptyConstructorMarker arg, dart._runtime.types.simple.Type type)
    {
      super(arg, type);
    }
  
    public void initPopulation(int Pop)
    {
      final dart._runtime.types.simple.TypeEnvironment dart2java$localTypeEnv = this.dart2java$type.env;
      for (int i = 0; (i < Pop); i = (i + 1))
      {
        this.add(this.getRng().nextInt(this.getWidth()), this.getRng().nextInt(this.getWidth()));
      }
    }
    public void add(java.lang.Object x, java.lang.Object y)
    {
      final dart._runtime.types.simple.TypeEnvironment dart2java$localTypeEnv = this.dart2java$type.env;
      gameoflife.Cell_interface c = this.getCellDish().operatorAt_Map((((("" + x.toString()) + "-") + y.toString()) + ""));
      c.setState(1);
    }
    public void update()
    {
      final dart._runtime.types.simple.TypeEnvironment dart2java$localTypeEnv = this.dart2java$type.env;
      for (dart.core.Iterator_interface<java.lang.String> __tempVar_0 = ((dart.core.Iterator_interface<java.lang.String>) this.getCellDish().getKeys_Map().getIterator_Iterable()); __tempVar_0.moveNext_Iterator(); )
      {
        java.lang.String k = __tempVar_0.getCurrent_Iterator();
        this.getCellDish().operatorAt_Map(k).update();
      }
      for (dart.core.Iterator_interface<java.lang.String> __tempVar_1 = ((dart.core.Iterator_interface<java.lang.String>) this.getCellDish().getKeys_Map().getIterator_Iterable()); __tempVar_1.moveNext_Iterator(); )
      {
        java.lang.String k = __tempVar_1.getCurrent_Iterator();
        this.getCellDish().operatorAt_Map(k).commit();
      }
    }
    public void _constructor()
    {
      final dart._runtime.types.simple.TypeEnvironment dart2java$localTypeEnv = this.dart2java$type.env;
      this.cellDish = ((dart.core.Map_interface) ((dart.core.Map_interface<java.lang.String, gameoflife.Cell_interface>) dart.core.Map.<java.lang.String, gameoflife.Cell_interface>factory$(dart2java$localTypeEnv.extend(dart.core.Map.factory$$typeInfo.typeVariables, new dart._runtime.types.simple.Type[] {dart2java$localTypeEnv.evaluate(dart2java$typeExpr_String), dart2java$localTypeEnv.evaluate(dart2java$typeExpr_Cell)}))));
      this.width = 10;
      this.rng = dart.math.Random.factory$(17);
      super._constructor();
      for (int x = 0; (x < this.getWidth()); x = (x + 1))
      {
        for (int y = 0; (y < this.getWidth()); y = (y + 1))
        {
          this.getCellDish().operatorAtPut_Map((((("" + x) + "-") + y) + ""), gameoflife.Cell._new_Cell$(dart2java$localTypeEnv.evaluate(dart2java$typeExpr_Cell), x, y, ((dart.core.Map_interface) this.getCellDish())));
        }
      }
    }
    public dart.core.Map_interface<java.lang.String, gameoflife.Cell_interface> getCellDish()
    {
      return this.cellDish;
    }
    public int getWidth()
    {
      return this.width;
    }
    public dart.math.Random_interface getRng()
    {
      return this.rng;
    }
    public dart.core.Map_interface<java.lang.String, gameoflife.Cell_interface> setCellDish(dart.core.Map_interface<java.lang.String, gameoflife.Cell_interface> value)
    {
      this.cellDish = value;
      return value;
    }
    public int setWidth(int value)
    {
      this.width = value;
      return value;
    }
    public dart.math.Random_interface setRng(dart.math.Random_interface value)
    {
      this.rng = value;
      return value;
    }
    public static gameoflife.Culture_interface _new_Culture$(dart._runtime.types.simple.Type type)
    {
      gameoflife.Culture result;
      result = new gameoflife.Culture(((dart._runtime.helpers.ConstructorHelper.EmptyConstructorMarker) null), type);
      result._constructor();
      return result;
    }
}
