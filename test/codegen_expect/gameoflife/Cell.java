package gameoflife;

public class Cell extends dart._runtime.base.DartObject implements gameoflife.Cell_interface
{
    public static dart._runtime.types.simple.InterfaceTypeInfo dart2java$typeInfo = new dart._runtime.types.simple.InterfaceTypeInfo(gameoflife.Cell.class, gameoflife.Cell_interface.class);
    private static dart._runtime.types.simple.InterfaceTypeExpr dart2java$typeExpr_Object = new dart._runtime.types.simple.InterfaceTypeExpr(dart._runtime.helpers.ObjectHelper.dart2java$typeInfo);
    static {
      gameoflife.Cell.dart2java$typeInfo.superclass = dart2java$typeExpr_Object;
    }
    public int x;
    public int y;
    public dart.core.Map_interface<java.lang.String, gameoflife.Cell_interface> Environment;
    public int state;
    public int nextState;
    public int age;
    public int updates;
  
    public Cell(dart._runtime.helpers.ConstructorHelper.EmptyConstructorMarker arg, dart._runtime.types.simple.Type type)
    {
      super(arg, type);
    }
  
    public void update()
    {
      final dart._runtime.types.simple.TypeEnvironment dart2java$localTypeEnv = this.dart2java$type.env;
      gameoflife.Cell_interface __tempVar_1;
      gameoflife.Cell_interface __tempVar_3;
      int n = this.getNeighbours();
      dart._runtime.helpers.LetExpressionHelper.comma(__tempVar_1 = this, __tempVar_1.setUpdates((__tempVar_1.getUpdates() + 1)));
      if ((((this.getState() == 1) && (n == 2)) || (n == 3)))
      {
        this.setNextState(1);
        dart._runtime.helpers.LetExpressionHelper.comma(__tempVar_3 = this, __tempVar_3.setAge((__tempVar_3.getAge() + 1)));
      }
      else
      {
        this.setNextState(0);
        this.setAge(0);
      }
    }
    public void commit()
    {
      final dart._runtime.types.simple.TypeEnvironment dart2java$localTypeEnv = this.dart2java$type.env;
      this.setState(this.getNextState());
    }
    public int getNeighbours()
    {
      final dart._runtime.types.simple.TypeEnvironment dart2java$localTypeEnv = this.dart2java$type.env;
      int n = 0;
      if (this.isNeighbourPopulated((this.getX() - 1), (this.getY() - 1)))
      {
        n = (n + 1);
      }
      if (this.isNeighbourPopulated(this.getX(), (this.getY() - 1)))
      {
        n = (n + 1);
      }
      if (this.isNeighbourPopulated((this.getX() + 1), (this.getY() - 1)))
      {
        n = (n + 1);
      }
      if (this.isNeighbourPopulated((this.getX() - 1), this.getY()))
      {
        n = (n + 1);
      }
      if (this.isNeighbourPopulated((this.getX() + 1), this.getY()))
      {
        n = (n + 1);
      }
      if (this.isNeighbourPopulated((this.getX() - 1), (this.getY() + 1)))
      {
        n = (n + 1);
      }
      if (this.isNeighbourPopulated(this.getX(), (this.getY() + 1)))
      {
        n = (n + 1);
      }
      if (this.isNeighbourPopulated((this.getX() + 1), (this.getY() + 1)))
      {
        n = (n + 1);
      }
      return n;
    }
    public boolean isNeighbourPopulated(int nx, int ny)
    {
      final dart._runtime.types.simple.TypeEnvironment dart2java$localTypeEnv = this.dart2java$type.env;
      if ((!this.getEnvironment().containsKey_Map((((("" + nx) + "-") + ny) + ""))))
      {
        return false;
      }
      gameoflife.Cell_interface t = this.getEnvironment().operatorAt_Map((((("" + nx) + "-") + ny) + ""));
      if ((t.getState() == 1))
      {
        return true;
      }
      else
      {
        return false;
      }
    }
    public void _constructor(int x, int y, dart.core.Map_interface<java.lang.String, gameoflife.Cell_interface> Environment)
    {
      final dart._runtime.types.simple.TypeEnvironment dart2java$localTypeEnv = this.dart2java$type.env;
      this.x = 0;
      this.y = 0;
      this.state = 0;
      this.nextState = (-1);
      this.age = 0;
      this.updates = 0;
      this.x = x;
      this.y = y;
      this.Environment = ((dart.core.Map_interface) Environment);
      super._constructor();
    }
    public int getX()
    {
      return this.x;
    }
    public int getY()
    {
      return this.y;
    }
    public dart.core.Map_interface<java.lang.String, gameoflife.Cell_interface> getEnvironment()
    {
      return this.Environment;
    }
    public int getState()
    {
      return this.state;
    }
    public int getNextState()
    {
      return this.nextState;
    }
    public int getAge()
    {
      return this.age;
    }
    public int getUpdates()
    {
      return this.updates;
    }
    public int setX(int value)
    {
      this.x = value;
      return value;
    }
    public int setY(int value)
    {
      this.y = value;
      return value;
    }
    public dart.core.Map_interface<java.lang.String, gameoflife.Cell_interface> setEnvironment(dart.core.Map_interface<java.lang.String, gameoflife.Cell_interface> value)
    {
      this.Environment = value;
      return value;
    }
    public int setState(int value)
    {
      this.state = value;
      return value;
    }
    public int setNextState(int value)
    {
      this.nextState = value;
      return value;
    }
    public int setAge(int value)
    {
      this.age = value;
      return value;
    }
    public int setUpdates(int value)
    {
      this.updates = value;
      return value;
    }
    public static gameoflife.Cell_interface _new_Cell$(dart._runtime.types.simple.Type type, int x, int y, dart.core.Map_interface<java.lang.String, gameoflife.Cell_interface> Environment)
    {
      gameoflife.Cell result;
      result = new gameoflife.Cell(((dart._runtime.helpers.ConstructorHelper.EmptyConstructorMarker) null), type);
      result._constructor(x, y, Environment);
      return result;
    }
}
