package richards;

public class TaskControlBlock extends dart._runtime.base.DartObject
{
    public richards.TaskControlBlock link = null;
    public java.lang.Integer id = null;
    public java.lang.Integer priority = null;
    public richards.Packet queue = null;
    public richards.Task task = null;
    public java.lang.Integer state = null;
    public static java.lang.Integer STATE_RUNNING = 0;
    public static java.lang.Integer STATE_RUNNABLE = 1;
    public static java.lang.Integer STATE_SUSPENDED = 2;
    public static java.lang.Integer STATE_HELD = 4;
    public static java.lang.Integer STATE_SUSPENDED_RUNNABLE = dart._runtime.helpers.IntegerHelper.operatorBitOr(richards.TaskControlBlock.STATE_SUSPENDED, richards.TaskControlBlock.STATE_RUNNABLE);
    public static java.lang.Integer STATE_NOT_HELD = dart._runtime.helpers.IntegerHelper.operatorUnaryBitNegate(richards.TaskControlBlock.STATE_HELD);
  
    public TaskControlBlock(richards.TaskControlBlock link, java.lang.Integer id, java.lang.Integer priority, richards.Packet queue, richards.Task task)
    {
      super((dart._runtime.helpers.ConstructorHelper.EmptyConstructorMarker) null);
      this._constructor(link, id, priority, queue, task);
    }
    public TaskControlBlock(dart._runtime.helpers.ConstructorHelper.EmptyConstructorMarker arg)
    {
      super(arg);
    }
  
    protected void _constructor(richards.TaskControlBlock link, java.lang.Integer id, java.lang.Integer priority, richards.Packet queue, richards.Task task)
    {
      this.STATE_RUNNING = 0;
      this.STATE_RUNNABLE = 1;
      this.STATE_SUSPENDED = 2;
      this.STATE_HELD = 4;
      this.STATE_SUSPENDED_RUNNABLE = dart._runtime.helpers.IntegerHelper.operatorBitOr(richards.TaskControlBlock.STATE_SUSPENDED, richards.TaskControlBlock.STATE_RUNNABLE);
      this.STATE_NOT_HELD = dart._runtime.helpers.IntegerHelper.operatorUnaryBitNegate(richards.TaskControlBlock.STATE_HELD);
      this.link = link;
      this.id = id;
      this.priority = priority;
      this.queue = queue;
      this.task = task;
      super._constructor();
      this.setState((dart._runtime.helpers.ObjectHelper.operatorEqual(this.getQueue(), null)) ? (richards.TaskControlBlock.STATE_SUSPENDED) : (richards.TaskControlBlock.STATE_SUSPENDED_RUNNABLE));
    }
    public void setRunning()
    {
      this.setState(richards.TaskControlBlock.STATE_RUNNING);
    }
    public void markAsNotHeld()
    {
      this.setState(dart._runtime.helpers.IntegerHelper.operatorBitAnd(this.getState(), richards.TaskControlBlock.STATE_NOT_HELD));
    }
    public void markAsHeld()
    {
      this.setState(dart._runtime.helpers.IntegerHelper.operatorBitOr(this.getState(), richards.TaskControlBlock.STATE_HELD));
    }
    public java.lang.Boolean isHeldOrSuspended()
    {
      return ((!dart._runtime.helpers.ObjectHelper.operatorEqual(dart._runtime.helpers.IntegerHelper.operatorBitAnd(this.getState(), richards.TaskControlBlock.STATE_HELD), 0)) || dart._runtime.helpers.ObjectHelper.operatorEqual(this.getState(), richards.TaskControlBlock.STATE_SUSPENDED));
    }
    public void markAsSuspended()
    {
      this.setState(dart._runtime.helpers.IntegerHelper.operatorBitOr(this.getState(), richards.TaskControlBlock.STATE_SUSPENDED));
    }
    public void markAsRunnable()
    {
      this.setState(dart._runtime.helpers.IntegerHelper.operatorBitOr(this.getState(), richards.TaskControlBlock.STATE_RUNNABLE));
    }
    public richards.TaskControlBlock run()
    {
      richards.Packet packet = null;
      if (dart._runtime.helpers.ObjectHelper.operatorEqual(this.getState(), richards.TaskControlBlock.STATE_SUSPENDED_RUNNABLE))
      {
        packet = this.getQueue();
        this.setQueue(packet.getLink());
        this.setState((dart._runtime.helpers.ObjectHelper.operatorEqual(this.getQueue(), null)) ? (richards.TaskControlBlock.STATE_RUNNING) : (richards.TaskControlBlock.STATE_RUNNABLE));
      }
      else
      {
        packet = null;
      }
      return this.getTask().run(packet);
    }
    public richards.TaskControlBlock checkPriorityAdd(richards.TaskControlBlock task, richards.Packet packet)
    {
      if (dart._runtime.helpers.ObjectHelper.operatorEqual(this.getQueue(), null))
      {
        this.setQueue(packet);
        this.markAsRunnable();
        if (dart._runtime.helpers.IntegerHelper.operatorGreater(this.getPriority(), task.getPriority()))
        {
          return this;
        }
      }
      else
      {
        this.setQueue(packet.addTo(this.getQueue()));
      }
      return task;
    }
    public java.lang.String toString()
    {
      return (((("tcb { ".toString() + this.getTask().toString()) + "@".toString()) + this.getState().toString()) + " }".toString());
    }
    public richards.TaskControlBlock getLink()
    {
      return this.link;
    }
    public java.lang.Integer getId()
    {
      return this.id;
    }
    public java.lang.Integer getPriority()
    {
      return this.priority;
    }
    public richards.Packet getQueue()
    {
      return this.queue;
    }
    public richards.Task getTask()
    {
      return this.task;
    }
    public java.lang.Integer getState()
    {
      return this.state;
    }
    public static java.lang.Integer getSTATE_RUNNING()
    {
      return richards.TaskControlBlock.STATE_RUNNING;
    }
    public static java.lang.Integer getSTATE_RUNNABLE()
    {
      return richards.TaskControlBlock.STATE_RUNNABLE;
    }
    public static java.lang.Integer getSTATE_SUSPENDED()
    {
      return richards.TaskControlBlock.STATE_SUSPENDED;
    }
    public static java.lang.Integer getSTATE_HELD()
    {
      return richards.TaskControlBlock.STATE_HELD;
    }
    public static java.lang.Integer getSTATE_SUSPENDED_RUNNABLE()
    {
      return richards.TaskControlBlock.STATE_SUSPENDED_RUNNABLE;
    }
    public static java.lang.Integer getSTATE_NOT_HELD()
    {
      return richards.TaskControlBlock.STATE_NOT_HELD;
    }
    public richards.TaskControlBlock setLink(richards.TaskControlBlock value)
    {
      this.link = value;
      return value;
    }
    public java.lang.Integer setId(java.lang.Integer value)
    {
      this.id = value;
      return value;
    }
    public java.lang.Integer setPriority(java.lang.Integer value)
    {
      this.priority = value;
      return value;
    }
    public richards.Packet setQueue(richards.Packet value)
    {
      this.queue = value;
      return value;
    }
    public richards.Task setTask(richards.Task value)
    {
      this.task = value;
      return value;
    }
    public java.lang.Integer setState(java.lang.Integer value)
    {
      this.state = value;
      return value;
    }
    public static java.lang.Integer setSTATE_RUNNING(java.lang.Integer value)
    {
      richards.TaskControlBlock.STATE_RUNNING = value;
      return value;
    }
    public static java.lang.Integer setSTATE_RUNNABLE(java.lang.Integer value)
    {
      richards.TaskControlBlock.STATE_RUNNABLE = value;
      return value;
    }
    public static java.lang.Integer setSTATE_SUSPENDED(java.lang.Integer value)
    {
      richards.TaskControlBlock.STATE_SUSPENDED = value;
      return value;
    }
    public static java.lang.Integer setSTATE_HELD(java.lang.Integer value)
    {
      richards.TaskControlBlock.STATE_HELD = value;
      return value;
    }
    public static java.lang.Integer setSTATE_SUSPENDED_RUNNABLE(java.lang.Integer value)
    {
      richards.TaskControlBlock.STATE_SUSPENDED_RUNNABLE = value;
      return value;
    }
    public static java.lang.Integer setSTATE_NOT_HELD(java.lang.Integer value)
    {
      richards.TaskControlBlock.STATE_NOT_HELD = value;
      return value;
    }
}
