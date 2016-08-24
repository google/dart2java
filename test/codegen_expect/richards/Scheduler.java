package richards;

public class Scheduler extends dart._runtime.base.DartObject
{
    public static dart._runtime.types.simple.InterfaceTypeInfo dart2java$typeInfo = new dart._runtime.types.simple.InterfaceTypeInfo("file:///usr/local/google/home/stanm/f/d/ddc-java/gen/codegen_tests/richards.dart", "Scheduler");
    static {
      richards.Scheduler.dart2java$typeInfo.superclass = new dart._runtime.types.simple.InterfaceTypeExpr(dart._runtime.helpers.ObjectHelper.dart2java$typeInfo);
    }
    public int queueCount;
    public int holdCount;
    public richards.TaskControlBlock currentTcb;
    public int currentId;
    public richards.TaskControlBlock list;
    public dart._runtime.base.DartList<richards.TaskControlBlock> blocks;
  
    public Scheduler()
    {
      super((dart._runtime.helpers.ConstructorHelper.EmptyConstructorMarker) null);
      this._constructor();
    }
    public Scheduler(dart._runtime.helpers.ConstructorHelper.EmptyConstructorMarker arg)
    {
      super(arg);
    }
  
    protected void _constructor()
    {
      this.queueCount = 0;
      this.holdCount = 0;
      this.currentId = 0;
      this.blocks = (dart._runtime.base.DartList) dart._runtime.base.DartList.Generic.newInstance(richards.TaskControlBlock.class, richards.Richards.NUMBER_OF_IDS);
      super._constructor();
    }
    public void addIdleTask(int id, int priority, richards.Packet queue, int count)
    {
      this.addRunningTask(id, priority, queue, new richards.IdleTask(this, 1, count));
    }
    public void addWorkerTask(int id, int priority, richards.Packet queue)
    {
      this.addTask(id, priority, queue, new richards.WorkerTask(this, richards.Richards.ID_HANDLER_A, 0));
    }
    public void addHandlerTask(int id, int priority, richards.Packet queue)
    {
      this.addTask(id, priority, queue, new richards.HandlerTask(this));
    }
    public void addDeviceTask(int id, int priority, richards.Packet queue)
    {
      this.addTask(id, priority, queue, new richards.DeviceTask(this));
    }
    public void addRunningTask(int id, int priority, richards.Packet queue, richards.Task task)
    {
      this.addTask(id, priority, queue, task);
      this.getCurrentTcb().setRunning();
    }
    public void addTask(int id, int priority, richards.Packet queue, richards.Task task)
    {
      this.setCurrentTcb(new richards.TaskControlBlock(this.getList(), id, priority, queue, task));
      this.setList(this.getCurrentTcb());
      this.getBlocks().operatorAtPut(id, this.getCurrentTcb());
    }
    public void schedule()
    {
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
    public richards.TaskControlBlock release(int id)
    {
      richards.TaskControlBlock tcb = this.getBlocks().operatorAt(id);
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
    public richards.TaskControlBlock holdCurrent()
    {
      richards.Scheduler __tempVar_1;
      dart._runtime.helpers.LetExpressionHelper.comma(__tempVar_1 = this, __tempVar_1.setHoldCount((__tempVar_1.getHoldCount() + 1)));
      this.getCurrentTcb().markAsHeld();
      return this.getCurrentTcb().getLink();
    }
    public richards.TaskControlBlock suspendCurrent()
    {
      this.getCurrentTcb().markAsSuspended();
      return this.getCurrentTcb();
    }
    public richards.TaskControlBlock queue(richards.Packet packet)
    {
      richards.Scheduler __tempVar_3;
      richards.TaskControlBlock t = this.getBlocks().operatorAt(packet.getId());
      if (dart._runtime.helpers.ObjectHelper.operatorEqual(t, null))
      {
        return t;
      }
      dart._runtime.helpers.LetExpressionHelper.comma(__tempVar_3 = this, __tempVar_3.setQueueCount((__tempVar_3.getQueueCount() + 1)));
      packet.setLink(null);
      packet.setId(this.getCurrentId());
      return t.checkPriorityAdd(this.getCurrentTcb(), packet);
    }
    public int getQueueCount()
    {
      return this.queueCount;
    }
    public int getHoldCount()
    {
      return this.holdCount;
    }
    public richards.TaskControlBlock getCurrentTcb()
    {
      return this.currentTcb;
    }
    public int getCurrentId()
    {
      return this.currentId;
    }
    public richards.TaskControlBlock getList()
    {
      return this.list;
    }
    public dart._runtime.base.DartList<richards.TaskControlBlock> getBlocks()
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
    public richards.TaskControlBlock setCurrentTcb(richards.TaskControlBlock value)
    {
      this.currentTcb = value;
      return value;
    }
    public int setCurrentId(int value)
    {
      this.currentId = value;
      return value;
    }
    public richards.TaskControlBlock setList(richards.TaskControlBlock value)
    {
      this.list = value;
      return value;
    }
    public dart._runtime.base.DartList<richards.TaskControlBlock> setBlocks(dart._runtime.base.DartList<richards.TaskControlBlock> value)
    {
      this.blocks = value;
      return value;
    }
}
