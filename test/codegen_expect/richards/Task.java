package richards;

public abstract class Task extends dart._runtime.base.DartObject
{
    public richards.Scheduler scheduler = null;
  
    public Task(richards.Scheduler scheduler)
    {
      super((dart._runtime.helpers.ConstructorHelper.EmptyConstructorMarker) null);
      this._constructor(scheduler);
    }
    public Task(dart._runtime.helpers.ConstructorHelper.EmptyConstructorMarker arg)
    {
      super(arg);
    }
  
    protected void _constructor(richards.Scheduler scheduler)
    {
      this.scheduler = scheduler;
      super._constructor();
    }
    public abstract richards.TaskControlBlock run(richards.Packet packet);
    public richards.Scheduler getScheduler()
    {
      return this.scheduler;
    }
    public richards.Scheduler setScheduler(richards.Scheduler value)
    {
      this.scheduler = value;
      return value;
    }
}
