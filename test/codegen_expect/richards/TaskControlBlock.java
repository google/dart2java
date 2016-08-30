package richards;

public class TaskControlBlock extends dart._runtime.base.DartObject implements richards.TaskControlBlock_interface
{
    public static dart._runtime.types.simple.InterfaceTypeInfo dart2java$typeInfo = new dart._runtime.types.simple.InterfaceTypeInfo(richards.TaskControlBlock.class, richards.TaskControlBlock_interface.class);
    static {
      richards.TaskControlBlock.dart2java$typeInfo.superclass = new dart._runtime.types.simple.InterfaceTypeExpr(dart._runtime.helpers.ObjectHelper.dart2java$typeInfo);
    }
    static {
      final dart._runtime.types.simple.TypeEnvironment dart2java$localTypeEnv = dart._runtime.types.simple.TypeEnvironment.ROOT;
      richards.TaskControlBlock.STATE_RUNNING = 0;
      richards.TaskControlBlock.STATE_RUNNABLE = 1;
      richards.TaskControlBlock.STATE_SUSPENDED = 2;
      richards.TaskControlBlock.STATE_HELD = 4;
      richards.TaskControlBlock.STATE_SUSPENDED_RUNNABLE = (richards.TaskControlBlock.STATE_SUSPENDED | richards.TaskControlBlock.STATE_RUNNABLE);
      richards.TaskControlBlock.STATE_NOT_HELD = (~richards.TaskControlBlock.STATE_HELD);
    }
    public richards.TaskControlBlock_interface link;
    public int id;
    public int priority;
    public richards.Packet_interface queue;
    public richards.Task_interface task;
    public int state;
    public static int STATE_RUNNING;
    public static int STATE_RUNNABLE;
    public static int STATE_SUSPENDED;
    public static int STATE_HELD;
    public static int STATE_SUSPENDED_RUNNABLE;
    public static int STATE_NOT_HELD;
  
    public TaskControlBlock(dart._runtime.types.simple.Type type, richards.TaskControlBlock_interface link, int id, int priority, richards.Packet_interface queue, richards.Task_interface task)
    {
      super((dart._runtime.helpers.ConstructorHelper.EmptyConstructorMarker) null, type);
      this._constructor(link, id, priority, queue, task);
    }
    public TaskControlBlock(dart._runtime.helpers.ConstructorHelper.EmptyConstructorMarker arg, dart._runtime.types.simple.Type type)
    {
      super(arg, type);
    }
  
    protected void _constructor(richards.TaskControlBlock_interface link, int id, int priority, richards.Packet_interface queue, richards.Task_interface task)
    {
      final dart._runtime.types.simple.TypeEnvironment dart2java$localTypeEnv = this.dart2java$type.env;
      this.id = 0;
      this.priority = 0;
      this.state = 0;
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
      final dart._runtime.types.simple.TypeEnvironment dart2java$localTypeEnv = this.dart2java$type.env;
      this.setState(richards.TaskControlBlock.STATE_RUNNING);
    }
    public void markAsNotHeld()
    {
      final dart._runtime.types.simple.TypeEnvironment dart2java$localTypeEnv = this.dart2java$type.env;
      this.setState((this.getState() & richards.TaskControlBlock.STATE_NOT_HELD));
    }
    public void markAsHeld()
    {
      final dart._runtime.types.simple.TypeEnvironment dart2java$localTypeEnv = this.dart2java$type.env;
      this.setState((this.getState() | richards.TaskControlBlock.STATE_HELD));
    }
    public boolean isHeldOrSuspended()
    {
      final dart._runtime.types.simple.TypeEnvironment dart2java$localTypeEnv = this.dart2java$type.env;
      return ((!((this.getState() & richards.TaskControlBlock.STATE_HELD) == 0)) || (this.getState() == richards.TaskControlBlock.STATE_SUSPENDED));
    }
    public void markAsSuspended()
    {
      final dart._runtime.types.simple.TypeEnvironment dart2java$localTypeEnv = this.dart2java$type.env;
      this.setState((this.getState() | richards.TaskControlBlock.STATE_SUSPENDED));
    }
    public void markAsRunnable()
    {
      final dart._runtime.types.simple.TypeEnvironment dart2java$localTypeEnv = this.dart2java$type.env;
      this.setState((this.getState() | richards.TaskControlBlock.STATE_RUNNABLE));
    }
    public richards.TaskControlBlock_interface run()
    {
      final dart._runtime.types.simple.TypeEnvironment dart2java$localTypeEnv = this.dart2java$type.env;
      richards.Packet_interface packet = null;
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
    public richards.TaskControlBlock_interface checkPriorityAdd(richards.TaskControlBlock_interface task, richards.Packet_interface packet)
    {
      final dart._runtime.types.simple.TypeEnvironment dart2java$localTypeEnv = this.dart2java$type.env;
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
      final dart._runtime.types.simple.TypeEnvironment dart2java$localTypeEnv = this.dart2java$type.env;
      return (((("tcb { " + this.getTask().toString()) + "@") + this.getState()) + " }");
    }
    public richards.TaskControlBlock_interface getLink()
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
    public richards.Packet_interface getQueue()
    {
      return this.queue;
    }
    public richards.Task_interface getTask()
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
    public richards.TaskControlBlock_interface setLink(richards.TaskControlBlock_interface value)
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
    public richards.Packet_interface setQueue(richards.Packet_interface value)
    {
      this.queue = value;
      return value;
    }
    public richards.Task_interface setTask(richards.Task_interface value)
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
