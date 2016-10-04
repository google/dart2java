package richards;

public class WorkerTask extends richards.Task implements richards.WorkerTask_interface
{
    public static dart._runtime.types.simple.InterfaceTypeInfo dart2java$typeInfo = new dart._runtime.types.simple.InterfaceTypeInfo(richards.WorkerTask.class, richards.WorkerTask_interface.class);
    private static dart._runtime.types.simple.InterfaceTypeExpr dart2java$typeExpr_Task = new dart._runtime.types.simple.InterfaceTypeExpr(richards.Task.dart2java$typeInfo);
    static {
      richards.WorkerTask.dart2java$typeInfo.superclass = dart2java$typeExpr_Task;
    }
    public int v1;
    public int v2;
  
    public WorkerTask(dart._runtime.helpers.ConstructorHelper.EmptyConstructorMarker arg, dart._runtime.types.simple.Type type)
    {
      super(arg, type);
    }
  
    public richards.TaskControlBlock_interface run(richards.Packet_interface packet)
    {
      final dart._runtime.types.simple.TypeEnvironment dart2java$localTypeEnv = this.dart2java$type.env;
      richards.WorkerTask_interface __tempVar_1;
      if (dart._runtime.helpers.ObjectHelper.operatorEqual(packet, null))
      {
        return this.getScheduler().suspendCurrent();
      }
      if ((this.getV1() == richards.Richards.ID_HANDLER_A))
      {
        this.setV1(richards.Richards.ID_HANDLER_B);
      }
      else
      {
        this.setV1(richards.Richards.ID_HANDLER_A);
      }
      packet.setId(this.getV1());
      packet.setA1(0);
      for (int i = 0; (i < richards.Richards.DATA_SIZE); i = (i + 1))
      {
        dart._runtime.helpers.LetExpressionHelper.comma(__tempVar_1 = this, __tempVar_1.setV2((__tempVar_1.getV2() + 1)));
        if ((this.getV2() > 26))
        {
          this.setV2(1);
        }
        packet.getA2().operatorAtPut_List__int(i, this.getV2());
      }
      return this.getScheduler().queue(packet);
    }
    public java.lang.String toString()
    {
      final dart._runtime.types.simple.TypeEnvironment dart2java$localTypeEnv = this.dart2java$type.env;
      return "WorkerTask";
    }
    public void _constructor(richards.Scheduler_interface scheduler, int v1, int v2)
    {
      final dart._runtime.types.simple.TypeEnvironment dart2java$localTypeEnv = this.dart2java$type.env;
      this.v1 = 0;
      this.v2 = 0;
      this.v1 = v1;
      this.v2 = v2;
      super._constructor(scheduler);
    }
    public int getV1()
    {
      return this.v1;
    }
    public int getV2()
    {
      return this.v2;
    }
    public int setV1(int value)
    {
      this.v1 = value;
      return value;
    }
    public int setV2(int value)
    {
      this.v2 = value;
      return value;
    }
    public static richards.WorkerTask_interface _new_WorkerTask$(dart._runtime.types.simple.Type type, richards.Scheduler_interface scheduler, int v1, int v2)
    {
      richards.WorkerTask result;
      result = new richards.WorkerTask(((dart._runtime.helpers.ConstructorHelper.EmptyConstructorMarker) null), type);
      result._constructor(scheduler, v1, v2);
      return result;
    }
}
