package richards;

public class IdleTask extends richards.Task implements richards.IdleTask_interface
{
    public static dart._runtime.types.simple.InterfaceTypeInfo dart2java$typeInfo = new dart._runtime.types.simple.InterfaceTypeInfo(richards.IdleTask.class, richards.IdleTask_interface.class);
    private static dart._runtime.types.simple.InterfaceTypeExpr dart2java$typeExpr_Task = new dart._runtime.types.simple.InterfaceTypeExpr(richards.Task.dart2java$typeInfo);
    static {
      richards.IdleTask.dart2java$typeInfo.superclass = dart2java$typeExpr_Task;
    }
    public int v1;
    public int count;
  
    public IdleTask(dart._runtime.helpers.ConstructorHelper.EmptyConstructorMarker arg, dart._runtime.types.simple.Type type)
    {
      super(arg, type);
    }
  
    public richards.TaskControlBlock_interface run(richards.Packet_interface packet)
    {
      final dart._runtime.types.simple.TypeEnvironment dart2java$localTypeEnv = this.dart2java$type.env;
      richards.IdleTask_interface __tempVar_1;
      dart._runtime.helpers.LetExpressionHelper.comma(__tempVar_1 = this, __tempVar_1.setCount((__tempVar_1.getCount() - 1)));
      if ((this.getCount() == 0))
      {
        return this.getScheduler().holdCurrent();
      }
      if (((this.getV1() & 1) == 0))
      {
        this.setV1((this.getV1() >> 1));
        return this.getScheduler().release(richards.Richards.ID_DEVICE_A);
      }
      this.setV1(((this.getV1() >> 1) ^ 53256));
      return this.getScheduler().release(richards.Richards.ID_DEVICE_B);
    }
    public java.lang.String toString()
    {
      final dart._runtime.types.simple.TypeEnvironment dart2java$localTypeEnv = this.dart2java$type.env;
      return "IdleTask";
    }
    public void _constructor(richards.Scheduler_interface scheduler, int v1, int count)
    {
      final dart._runtime.types.simple.TypeEnvironment dart2java$localTypeEnv = this.dart2java$type.env;
      this.v1 = 0;
      this.count = 0;
      this.v1 = v1;
      this.count = count;
      super._constructor(scheduler);
    }
    public int getV1()
    {
      return this.v1;
    }
    public int getCount()
    {
      return this.count;
    }
    public int setV1(int value)
    {
      this.v1 = value;
      return value;
    }
    public int setCount(int value)
    {
      this.count = value;
      return value;
    }
    public static richards.IdleTask_interface _new(dart._runtime.types.simple.Type type, richards.Scheduler_interface scheduler, int v1, int count)
    {
      richards.IdleTask result;
      result = new richards.IdleTask(((dart._runtime.helpers.ConstructorHelper.EmptyConstructorMarker) null), type);
      result._constructor(scheduler, v1, count);
      return result;
    }
}
