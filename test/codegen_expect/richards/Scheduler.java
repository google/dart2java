package richards;

public class Scheduler extends dart._runtime.base.DartObject
{
    public java.lang.Integer queueCount = null;
    public java.lang.Integer holdCount = null;
    public richards.TaskControlBlock currentTcb = null;
    public java.lang.Integer currentId = null;
    public richards.TaskControlBlock list = null;
    public dart._runtime.base.DartList<richards.TaskControlBlock> blocks = null;
  
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
      this.blocks = (dart._runtime.base.DartList) dart._runtime.base.DartList.Generic.newInstance(richards.TaskControlBlock.class, richards.Richards.NUMBER_OF_IDS);
      super._constructor();
    }
    public void addIdleTask(java.lang.Integer id, java.lang.Integer priority, richards.Packet queue, java.lang.Integer count)
    {
      this.addRunningTask(id, priority, queue, new richards.IdleTask(this, 1, count));
    }
    public void addWorkerTask(java.lang.Integer id, java.lang.Integer priority, richards.Packet queue)
    {
      this.addTask(id, priority, queue, new richards.WorkerTask(this, richards.Richards.ID_HANDLER_A, 0));
    }
    public void addHandlerTask(java.lang.Integer id, java.lang.Integer priority, richards.Packet queue)
    {
      this.addTask(id, priority, queue, new richards.HandlerTask(this));
    }
    public void addDeviceTask(java.lang.Integer id, java.lang.Integer priority, richards.Packet queue)
    {
      this.addTask(id, priority, queue, new richards.DeviceTask(this));
    }
    public void addRunningTask(java.lang.Integer id, java.lang.Integer priority, richards.Packet queue, richards.Task task)
    {
      this.addTask(id, priority, queue, task);
      this.getCurrentTcb().setRunning();
    }
    public void addTask(java.lang.Integer id, java.lang.Integer priority, richards.Packet queue, richards.Task task)
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
    public richards.TaskControlBlock release(java.lang.Integer id)
    {
      richards.TaskControlBlock tcb = this.getBlocks().operatorAt(id);
      if (dart._runtime.helpers.ObjectHelper.operatorEqual(tcb, null))
      {
        return tcb;
      }
      tcb.markAsNotHeld();
      if (dart._runtime.helpers.IntegerHelper.operatorGreater(tcb.getPriority(), this.getCurrentTcb().getPriority()))
      {
        return tcb;
      }
      return this.getCurrentTcb();
    }
    public richards.TaskControlBlock holdCurrent()
    {
      richards.Scheduler __tempVar_1;
      dart._runtime.helpers.LetExpressionHelper.comma(__tempVar_1 = this, __tempVar_1.setHoldCount(dart._runtime.helpers.IntegerHelper.operatorPlus(__tempVar_1.getHoldCount(), 1)));
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
      dart._runtime.helpers.LetExpressionHelper.comma(__tempVar_3 = this, __tempVar_3.setQueueCount(dart._runtime.helpers.IntegerHelper.operatorPlus(__tempVar_3.getQueueCount(), 1)));
      packet.setLink(null);
      packet.setId(this.getCurrentId());
      return t.checkPriorityAdd(this.getCurrentTcb(), packet);
    }
    public java.lang.Integer getQueueCount()
    {
      return this.queueCount;
    }
    public java.lang.Integer getHoldCount()
    {
      return this.holdCount;
    }
    public richards.TaskControlBlock getCurrentTcb()
    {
      return this.currentTcb;
    }
    public java.lang.Integer getCurrentId()
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
    public java.lang.Integer setQueueCount(java.lang.Integer value)
    {
      this.queueCount = value;
      return value;
    }
    public java.lang.Integer setHoldCount(java.lang.Integer value)
    {
      this.holdCount = value;
      return value;
    }
    public richards.TaskControlBlock setCurrentTcb(richards.TaskControlBlock value)
    {
      this.currentTcb = value;
      return value;
    }
    public java.lang.Integer setCurrentId(java.lang.Integer value)
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
