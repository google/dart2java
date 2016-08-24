package richards;

public class TaskControlBlock extends dart._runtime.base.DartObject
{
    public static dart._runtime.types.simple.InterfaceTypeInfo dart2java$typeInfo = new dart._runtime.types.simple.InterfaceTypeInfo("file:///usr/local/google/home/stanm/f/d/ddc-java/gen/codegen_tests/richards.dart", "TaskControlBlock");
    static {
      richards.TaskControlBlock.dart2java$typeInfo.superclass = new dart._runtime.types.simple.InterfaceTypeExpr(dart._runtime.helpers.ObjectHelper.dart2java$typeInfo);
    }
    public richards.TaskControlBlock link;
    public int id;
    public int priority;
    public richards.Packet queue;
    public richards.Task task;
    public int state;
    public static int STATE_RUNNING = 0;
    public static int STATE_RUNNABLE = 1;
    public static int STATE_SUSPENDED = 2;
    public static int STATE_HELD = 4;
    public static int STATE_SUSPENDED_RUNNABLE = (richards.TaskControlBlock.STATE_SUSPENDED | richards.TaskControlBlock.STATE_RUNNABLE);
    public static int STATE_NOT_HELD = (~richards.TaskControlBlock.STATE_HELD);
  
    public TaskControlBlock(richards.TaskControlBlock link, int id, int priority, richards.Packet queue, richards.Task task)
    {
      super((dart._runtime.helpers.ConstructorHelper.EmptyConstructorMarker) null);
      this._constructor(link, id, priority, queue, task);
    }
    public TaskControlBlock(dart._runtime.helpers.ConstructorHelper.EmptyConstructorMarker arg)
    {
      super(arg);
    }
  
    protected void _constructor(richards.TaskControlBlock link, int id, int priority, richards.Packet queue, richards.Task task)
    {
      this.id = 0;
      this.priority = 0;
      this.state = 0;
      this.STATE_RUNNING = 0;
      this.STATE_RUNNABLE = 1;
      this.STATE_SUSPENDED = 2;
      this.STATE_HELD = 4;
      this.STATE_SUSPENDED_RUNNABLE = (richards.TaskControlBlock.STATE_SUSPENDED | richards.TaskControlBlock.STATE_RUNNABLE);
      this.STATE_NOT_HELD = (~richards.TaskControlBlock.STATE_HELD);
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
      this.setState((this.getState() & richards.TaskControlBlock.STATE_NOT_HELD));
    }
    public void markAsHeld()
    {
      this.setState((this.getState() | richards.TaskControlBlock.STATE_HELD));
    }
    public java.lang.Boolean isHeldOrSuspended()
    {
      return ((!((this.getState() & richards.TaskControlBlock.STATE_HELD) == 0)) || (this.getState() == richards.TaskControlBlock.STATE_SUSPENDED));
    }
    public void markAsSuspended()
    {
      this.setState((this.getState() | richards.TaskControlBlock.STATE_SUSPENDED));
    }
    public void markAsRunnable()
    {
      this.setState((this.getState() | richards.TaskControlBlock.STATE_RUNNABLE));
    }
    public richards.TaskControlBlock run()
    {
      richards.Packet packet = null;
      if ((this.getState() == richards.TaskControlBlock.STATE_SUSPENDED_RUNNABLE))
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
        if ((this.getPriority() > task.getPriority()))
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
      return (((("tcb { " + this.getTask().toString()) + "@") + this.getState()) + " }");
    }
    public richards.TaskControlBlock getLink()
    {
      return this.link;
    }
    public int getId()
    {
      return this.id;
    }
    public int getPriority()
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
    public int getState()
    {
      return this.state;
    }
    public static int getSTATE_RUNNING()
    {
      return richards.TaskControlBlock.STATE_RUNNING;
    }
    public static int getSTATE_RUNNABLE()
    {
      return richards.TaskControlBlock.STATE_RUNNABLE;
    }
    public static int getSTATE_SUSPENDED()
    {
      return richards.TaskControlBlock.STATE_SUSPENDED;
    }
    public static int getSTATE_HELD()
    {
      return richards.TaskControlBlock.STATE_HELD;
    }
    public static int getSTATE_SUSPENDED_RUNNABLE()
    {
      return richards.TaskControlBlock.STATE_SUSPENDED_RUNNABLE;
    }
    public static int getSTATE_NOT_HELD()
    {
      return richards.TaskControlBlock.STATE_NOT_HELD;
    }
    public richards.TaskControlBlock setLink(richards.TaskControlBlock value)
    {
      this.link = value;
      return value;
    }
    public int setId(int value)
    {
      this.id = value;
      return value;
    }
    public int setPriority(int value)
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
    public int setState(int value)
    {
      this.state = value;
      return value;
    }
    public static int setSTATE_RUNNING(int value)
    {
      richards.TaskControlBlock.STATE_RUNNING = value;
      return value;
    }
    public static int setSTATE_RUNNABLE(int value)
    {
      richards.TaskControlBlock.STATE_RUNNABLE = value;
      return value;
    }
    public static int setSTATE_SUSPENDED(int value)
    {
      richards.TaskControlBlock.STATE_SUSPENDED = value;
      return value;
    }
    public static int setSTATE_HELD(int value)
    {
      richards.TaskControlBlock.STATE_HELD = value;
      return value;
    }
    public static int setSTATE_SUSPENDED_RUNNABLE(int value)
    {
      richards.TaskControlBlock.STATE_SUSPENDED_RUNNABLE = value;
      return value;
    }
    public static int setSTATE_NOT_HELD(int value)
    {
      richards.TaskControlBlock.STATE_NOT_HELD = value;
      return value;
    }
}
