package richards;

public abstract class Task extends dart._runtime.base.DartObject
{
    public static dart._runtime.types.simple.InterfaceTypeInfo dart2java$typeInfo = new dart._runtime.types.simple.InterfaceTypeInfo("file:///usr/local/google/home/stanm/f/d/ddc-java/gen/codegen_tests/richards.dart", "Task");
    static {
      richards.Task.dart2java$typeInfo.superclass = new dart._runtime.types.simple.InterfaceTypeExpr(dart._runtime.helpers.ObjectHelper.dart2java$typeInfo);
    }
    public richards.Scheduler scheduler;
  
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
