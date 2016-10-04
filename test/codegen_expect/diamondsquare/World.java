package diamondsquare;

public class World extends dart._runtime.base.DartObject implements diamondsquare.World_interface
{
    public static dart._runtime.types.simple.InterfaceTypeInfo dart2java$typeInfo = new dart._runtime.types.simple.InterfaceTypeInfo(diamondsquare.World.class, diamondsquare.World_interface.class);
    private static dart._runtime.types.simple.InterfaceTypeExpr dart2java$typeExpr_List$ltint$0$gt = new dart._runtime.types.simple.InterfaceTypeExpr(dart.core.List.dart2java$typeInfo, new dart._runtime.types.simple.TypeExpr[] {new dart._runtime.types.simple.InterfaceTypeExpr(dart._runtime.helpers.IntegerHelper.dart2java$typeInfo)});
    private static dart._runtime.types.simple.InterfaceTypeExpr dart2java$typeExpr_List$ltList$ltint$0$gt$gt = new dart._runtime.types.simple.InterfaceTypeExpr(dart.core.List.dart2java$typeInfo, new dart._runtime.types.simple.TypeExpr[] {new dart._runtime.types.simple.InterfaceTypeExpr(dart.core.List.dart2java$typeInfo, new dart._runtime.types.simple.TypeExpr[] {new dart._runtime.types.simple.InterfaceTypeExpr(dart._runtime.helpers.IntegerHelper.dart2java$typeInfo)})});
    private static dart._runtime.types.simple.InterfaceTypeExpr dart2java$typeExpr_Object = new dart._runtime.types.simple.InterfaceTypeExpr(dart._runtime.helpers.ObjectHelper.dart2java$typeInfo);
    static {
      diamondsquare.World.dart2java$typeInfo.superclass = dart2java$typeExpr_Object;
    }
    public int Width;
    public int Length;
    public dart.math.Random_interface rng;
    public int Iterations;
    public dart.core.List_interface<dart.core.List_interface__int> map_data;
  
    public World(dart._runtime.helpers.ConstructorHelper.EmptyConstructorMarker arg, dart._runtime.types.simple.Type type)
    {
      super(arg, type);
    }
  
    public void Reset()
    {
      final dart._runtime.types.simple.TypeEnvironment dart2java$localTypeEnv = this.dart2java$type.env;
      dart.core.List_interface__int row = ((dart.core.List_interface__int) ((dart.core.List_interface__int) dart._runtime.base.DartList.factory$fromArguments(dart2java$localTypeEnv.evaluate(dart2java$typeExpr_List$ltint$0$gt), java.lang.Integer.class)));
      for (int y = 0; (y < this.getLength()); y = (y + 1))
      {
        row = ((dart.core.List_interface__int) ((dart.core.List_interface__int) dart._runtime.base.DartList.factory$fromArguments(dart2java$localTypeEnv.evaluate(dart2java$typeExpr_List$ltint$0$gt), java.lang.Integer.class)));
        for (int x = 0; (x < this.getWidth()); x = (x + 1))
        {
          row.add_List__int(0);
        }
        this.getMap_data().add_List(((dart.core.List_interface__int) row));
      }
    }
    public void Base()
    {
      final dart._runtime.types.simple.TypeEnvironment dart2java$localTypeEnv = this.dart2java$type.env;
      for (int y = 0; (y < this.getLength()); y = (y + 1))
      {
        for (int x = 0; (x < this.getWidth()); x = (x + 1))
        {
          this.getMap_data().operatorAt_List(x).operatorAtPut_List__int(y, (this.getRng().nextInt(55) + 200));
        }
      }
    }
    public void SetCorners(int x, int y, int w, int h, dart.core.List_interface__int v)
    {
      final dart._runtime.types.simple.TypeEnvironment dart2java$localTypeEnv = this.dart2java$type.env;
      if ((w < 1))
      {
        return;
      }
      if ((!dart._runtime.helpers.ObjectHelper.operatorEqual(v, null)))
      {
        this.getMap_data().operatorAt_List(x).operatorAtPut_List__int(y, v.operatorAt_List__int(0));
        this.getMap_data().operatorAt_List((x + w)).operatorAtPut_List__int(y, v.operatorAt_List__int(1));
        this.getMap_data().operatorAt_List(x).operatorAtPut_List__int((y + h), v.operatorAt_List__int(2));
        this.getMap_data().operatorAt_List((x + w)).operatorAtPut_List__int((y + h), v.operatorAt_List__int(3));
      }
      int hw = dart._runtime.helpers.DoubleHelper.floor(dart._runtime.helpers.IntegerHelper.operatorDivide(w, 2));
      int hh = dart._runtime.helpers.DoubleHelper.floor(dart._runtime.helpers.IntegerHelper.operatorDivide(h, 2));
      int total = (((((this.getMap_data().operatorAt_List(x).operatorAt_List__int(y) + this.getMap_data().operatorAt_List((x + w)).operatorAt_List__int(y)) + this.getMap_data().operatorAt_List(x).operatorAt_List__int((y + h))) + this.getMap_data().operatorAt_List((x + w)).operatorAt_List__int((y + h))) + this.getRng().nextInt((4 + h))) + 4);
      this.getMap_data().operatorAt_List((x + hw)).operatorAtPut_List__int((y + hh), dart._runtime.helpers.DoubleHelper.floor(dart._runtime.helpers.IntegerHelper.operatorDivide(total, 4.0)));
      this.SetCorners(x, y, hw, hh, null);
      this.SetCorners((x + hw), y, hw, hh, null);
      this.SetCorners(x, (y + hh), hw, hh, null);
      this.SetCorners((x + hw), (y + hh), hw, hh, null);
    }
    public void Generate()
    {
      final dart._runtime.types.simple.TypeEnvironment dart2java$localTypeEnv = this.dart2java$type.env;
      int w = (this.getWidth() - 1);
      int h = (this.getLength() - 1);
      this.SetCorners(0, 0, w, h, ((dart.core.List_interface__int) ((dart.core.List_interface__int) dart._runtime.base.DartList.factory$fromArguments(dart2java$localTypeEnv.evaluate(dart2java$typeExpr_List$ltint$0$gt), java.lang.Integer.class, 155, 155, 155, 155))));
      this.SetCorners((this.getRng().nextInt(5) + 14), 0, 15, 15, null);
    }
    public void Smooth()
    {
      final dart._runtime.types.simple.TypeEnvironment dart2java$localTypeEnv = this.dart2java$type.env;
      int average = 0;
      for (int yl = 1; (yl < (this.getLength() - 1)); yl = (yl + 1))
      {
        for (int xl = 1; (xl < (this.getWidth() - 1)); xl = (xl + 1))
        {
          average = dart._runtime.helpers.DoubleHelper.floor((1.1 * dart._runtime.helpers.IntegerHelper.operatorDivide((((this.getMap_data().operatorAt_List(xl).operatorAt_List__int(yl) + this.getMap_data().operatorAt_List((xl + 1)).operatorAt_List__int(yl)) + this.getMap_data().operatorAt_List(xl).operatorAt_List__int((yl + 1))) + this.getMap_data().operatorAt_List((xl + 1)).operatorAt_List__int((yl + 1))), 4)));
          this.getMap_data().operatorAt_List(xl).operatorAtPut_List__int(yl, average);
          this.getMap_data().operatorAt_List((xl + 1)).operatorAtPut_List__int(yl, average);
          this.getMap_data().operatorAt_List((xl + 1)).operatorAtPut_List__int((yl + 1), average);
          this.getMap_data().operatorAt_List(xl).operatorAtPut_List__int((yl + 1), average);
          this.getMap_data().operatorAt_List((xl - 1)).operatorAtPut_List__int(yl, average);
          this.getMap_data().operatorAt_List((xl - 1)).operatorAtPut_List__int((yl - 1), average);
          this.getMap_data().operatorAt_List(xl).operatorAtPut_List__int((yl - 1), average);
        }
      }
    }
    public void _constructor()
    {
      final dart._runtime.types.simple.TypeEnvironment dart2java$localTypeEnv = this.dart2java$type.env;
      this.Width = 64;
      this.Length = 64;
      this.rng = dart.math.Random.factory$(17);
      this.Iterations = 6;
      this.map_data = ((dart.core.List_interface) ((dart.core.List_interface<dart.core.List_interface__int>) dart._runtime.base.DartList.factory$fromArguments(dart2java$localTypeEnv.evaluate(dart2java$typeExpr_List$ltList$ltint$0$gt$gt), dart.core.List_interface__int.class)));
      super._constructor();
      this.Reset();
    }
    public int getWidth()
    {
      return this.Width;
    }
    public int getLength()
    {
      return this.Length;
    }
    public dart.math.Random_interface getRng()
    {
      return this.rng;
    }
    public int getIterations()
    {
      return this.Iterations;
    }
    public dart.core.List_interface<dart.core.List_interface__int> getMap_data()
    {
      return this.map_data;
    }
    public int setWidth(int value)
    {
      this.Width = value;
      return value;
    }
    public int setLength(int value)
    {
      this.Length = value;
      return value;
    }
    public dart.math.Random_interface setRng(dart.math.Random_interface value)
    {
      this.rng = value;
      return value;
    }
    public int setIterations(int value)
    {
      this.Iterations = value;
      return value;
    }
    public dart.core.List_interface<dart.core.List_interface__int> setMap_data(dart.core.List_interface<dart.core.List_interface__int> value)
    {
      this.map_data = value;
      return value;
    }
    public static diamondsquare.World_interface _new_World$(dart._runtime.types.simple.Type type)
    {
      diamondsquare.World result;
      result = new diamondsquare.World(((dart._runtime.helpers.ConstructorHelper.EmptyConstructorMarker) null), type);
      result._constructor();
      return result;
    }
}
