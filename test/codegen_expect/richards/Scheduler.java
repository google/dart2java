package richards;

public class Scheduler extends dart._runtime.base.DartObject implements richards.Scheduler_interface
{
    public static dart._runtime.types.simple.InterfaceTypeInfo dart2java$typeInfo = new dart._runtime.types.simple.InterfaceTypeInfo(richards.Scheduler.class, richards.Scheduler_interface.class);
    private static dart._runtime.types.simple.InterfaceTypeExpr dart2java$typeExpr_IdleTask = new dart._runtime.types.simple.InterfaceTypeExpr(richards.IdleTask.dart2java$typeInfo);
    private static dart._runtime.types.simple.InterfaceTypeExpr dart2java$typeExpr_WorkerTask = new dart._runtime.types.simple.InterfaceTypeExpr(richards.WorkerTask.dart2java$typeInfo);
    private static dart._runtime.types.simple.InterfaceTypeExpr dart2java$typeExpr_HandlerTask = new dart._runtime.types.simple.InterfaceTypeExpr(richards.HandlerTask.dart2java$typeInfo);
    private static dart._runtime.types.simple.InterfaceTypeExpr dart2java$typeExpr_DeviceTask = new dart._runtime.types.simple.InterfaceTypeExpr(richards.DeviceTask.dart2java$typeInfo);
    private static dart._runtime.types.simple.InterfaceTypeExpr dart2java$typeExpr_TaskControlBlock = new dart._runtime.types.simple.InterfaceTypeExpr(richards.TaskControlBlock.dart2java$typeInfo);
    private static dart._runtime.types.simple.InterfaceTypeExpr dart2java$typeExpr_Object = new dart._runtime.types.simple.InterfaceTypeExpr(dart._runtime.helpers.ObjectHelper.dart2java$typeInfo);
    static {
      richards.Scheduler.dart2java$typeInfo.superclass = dart2java$typeExpr_Object;
    }
    public int queueCount;
    public int holdCount;
    public richards.TaskControlBlock_interface currentTcb;
    public int currentId;
    public richards.TaskControlBlock_interface list;
    public dart.core.List_interface<richards.TaskControlBlock_interface> blocks;
  
    public Scheduler(dart._runtime.helpers.ConstructorHelper.EmptyConstructorMarker arg, dart._runtime.types.simple.Type type)
    {
      super(arg, type);
    }
  
    public void addIdleTask(int id, int priority, richards.Packet_interface queue, int count)
    {
      final dart._runtime.types.simple.TypeEnvironment dart2java$localTypeEnv = this.dart2java$type.env;
      this.addRunningTask(id, priority, queue, richards.IdleTask._new_IdleTask$(dart2java$localTypeEnv.evaluate(dart2java$typeExpr_IdleTask), this, 1, count));
    }
    public void addWorkerTask(int id, int priority, richards.Packet_interface queue)
    {
      final dart._runtime.types.simple.TypeEnvironment dart2java$localTypeEnv = this.dart2java$type.env;
      this.addTask(id, priority, queue, richards.WorkerTask._new_WorkerTask$(dart2java$localTypeEnv.evaluate(dart2java$typeExpr_WorkerTask), this, richards.Richards.ID_HANDLER_A, 0));
    }
    public void addHandlerTask(int id, int priority, richards.Packet_interface queue)
    {
      final dart._runtime.types.simple.TypeEnvironment dart2java$localTypeEnv = this.dart2java$type.env;
      this.addTask(id, priority, queue, richards.HandlerTask._new_HandlerTask$(dart2java$localTypeEnv.evaluate(dart2java$typeExpr_HandlerTask), this));
    }
    public void addDeviceTask(int id, int priority, richards.Packet_interface queue)
    {
      final dart._runtime.types.simple.TypeEnvironment dart2java$localTypeEnv = this.dart2java$type.env;
      this.addTask(id, priority, queue, richards.DeviceTask._new_DeviceTask$(dart2java$localTypeEnv.evaluate(dart2java$typeExpr_DeviceTask), this));
    }
    public void addRunningTask(int id, int priority, richards.Packet_interface queue, richards.Task_interface task)
    {
      final dart._runtime.types.simple.TypeEnvironment dart2java$localTypeEnv = this.dart2java$type.env;
      this.addTask(id, priority, queue, task);
      this.getCurrentTcb().setRunning();
    }
    public void addTask(int id, int priority, richards.Packet_interface queue, richards.Task_interface task)
    {
      final dart._runtime.types.simple.TypeEnvironment dart2java$localTypeEnv = this.dart2java$type.env;
      this.setCurrentTcb(richards.TaskControlBlock._new_TaskControlBlock$(dart2java$localTypeEnv.evaluate(dart2java$typeExpr_TaskControlBlock), this.getList(), id, priority, queue, task));
      this.setList(this.getCurrentTcb());
      this.getBlocks().operatorAtPut_List(id, this.getCurrentTcb());
    }
    public void schedule()
    {
      final dart._runtime.types.simple.TypeEnvironment dart2java$localTypeEnv = this.dart2java$type.env;
      this.setCurrentTcb(this.getList());
      while ((!dart._runtime.helpers.ObjectHelper.operatorEqual(this.getCurrentTcb(), null)))
      {
        if (this.getCurrentTcb().isHeldOrSuspended())
        {
          this.setCurrentTcb(this.getCurrentTcb().getLink());
        }
        else
        {
          this.setCurrentId(this.getCurrentTcb().getId());
          this.setCurrentTcb(this.getCurrentTcb().run());
        }
      }
    }
    public richards.TaskControlBlock_interface release(int id)
    {
      final dart._runtime.types.simple.TypeEnvironment dart2java$localTypeEnv = this.dart2java$type.env;
      richards.TaskControlBlock_interface tcb = this.getBlocks().operatorAt_List(id);
      if (dart._runtime.helpers.ObjectHelper.operatorEqual(tcb, null))
      {
        return tcb;
      }
      tcb.markAsNotHeld();
      if ((tcb.getPriority() > this.getCurrentTcb().getPriority()))
      {
        return tcb;
      }
      return this.getCurrentTcb();
    }
    public richards.TaskControlBlock_interface holdCurrent()
    {
      final dart._runtime.types.simple.TypeEnvironment dart2java$localTypeEnv = this.dart2java$type.env;
      richards.Scheduler_interface __tempVar_1;
      dart._runtime.helpers.LetExpressionHelper.comma(__tempVar_1 = this, __tempVar_1.setHoldCount((__tempVar_1.getHoldCount() + 1)));
      this.getCurrentTcb().markAsHeld();
      return this.getCurrentTcb().getLink();
    }
    public richards.TaskControlBlock_interface suspendCurrent()
    {
      final dart._runtime.types.simple.TypeEnvironment dart2java$localTypeEnv = this.dart2java$type.env;
      this.getCurrentTcb().markAsSuspended();
      return this.getCurrentTcb();
    }
    public richards.TaskControlBlock_interface queue(richards.Packet_interface packet)
    {
      final dart._runtime.types.simple.TypeEnvironment dart2java$localTypeEnv = this.dart2java$type.env;
      richards.Scheduler_interface __tempVar_3;
      richards.TaskControlBlock_interface t = this.getBlocks().operatorAt_List(packet.getId());
      if (dart._runtime.helpers.ObjectHelper.operatorEqual(t, null))
      {
        return t;
      }
      dart._runtime.helpers.LetExpressionHelper.comma(__tempVar_3 = this, __tempVar_3.setQueueCount((__tempVar_3.getQueueCount() + 1)));
      packet.setLink(null);
      packet.setId(this.getCurrentId());
      return t.checkPriorityAdd(this.getCurrentTcb(), packet);
    }
    public void _constructor()
    {
      final dart._runtime.types.simple.TypeEnvironment dart2java$localTypeEnv = this.dart2java$type.env;
      this.queueCount = 0;
      this.holdCount = 0;
      this.currentId = 0;
      this.blocks = ((dart.core.List_interface) ((dart.core.List_interface<richards.TaskControlBlock_interface>) dart.core.List.<richards.TaskControlBlock_interface>factory$(dart2java$localTypeEnv.extend(dart.core.List.factory$$typeInfo.typeVariables, new dart._runtime.types.simple.Type[] {dart2java$localTypeEnv.evaluate(dart2java$typeExpr_TaskControlBlock)}), richards.Richards.NUMBER_OF_IDS)));
      super._constructor();
    }
    public int getQueueCount()
    {
      return this.queueCount;
    }
    public int getHoldCount()
    {
      return this.holdCount;
    }
    public richards.TaskControlBlock_interface getCurrentTcb()
    {
      return this.currentTcb;
    }
    public int getCurrentId()
    {
      return this.currentId;
    }
    public richards.TaskControlBlock_interface getList()
    {
      return this.list;
    }
    public dart.core.List_interface<richards.TaskControlBlock_interface> getBlocks()
    {
      return this.blocks;
    }
    public int setQueueCount(int value)
    {
      this.queueCount = value;
      return value;
    }
    public int setHoldCount(int value)
    {
      this.holdCount = value;
      return value;
    }
    public richards.TaskControlBlock_interface setCurrentTcb(richards.TaskControlBlock_interface value)
    {
      this.currentTcb = value;
      return value;
    }
    public int setCurrentId(int value)
    {
      this.currentId = value;
      return value;
    }
    public richards.TaskControlBlock_interface setList(richards.TaskControlBlock_interface value)
    {
      this.list = value;
      return value;
    }
    public dart.core.List_interface<richards.TaskControlBlock_interface> setBlocks(dart.core.List_interface<richards.TaskControlBlock_interface> value)
    {
      this.blocks = value;
      return value;
    }
    public static richards.Scheduler_interface _new_Scheduler$(dart._runtime.types.simple.Type type)
    {
      richards.Scheduler result;
      result = new richards.Scheduler(((dart._runtime.helpers.ConstructorHelper.EmptyConstructorMarker) null), type);
      result._constructor();
      return result;
    }
}
