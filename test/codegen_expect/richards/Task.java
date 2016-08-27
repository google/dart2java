package richards;

public abstract class Task extends dart._runtime.base.DartObject implements richards.Task_interface
{
    public static dart._runtime.types.simple.InterfaceTypeInfo dart2java$typeInfo = new dart._runtime.types.simple.InterfaceTypeInfo("richards.Task");
    static {
      richards.Task.dart2java$typeInfo.superclass = new dart._runtime.types.simple.InterfaceTypeExpr(dart._runtime.helpers.ObjectHelper.dart2java$typeInfo);
    }
    public richards.Scheduler scheduler;
  
    public Task(dart._runtime.types.simple.Type type, richards.Scheduler scheduler)
    {
      super((dart._runtime.helpers.ConstructorHelper.EmptyConstructorMarker) null, type);
      this._constructor(scheduler);
    }
    public Task(dart._runtime.helpers.ConstructorHelper.EmptyConstructorMarker arg, dart._runtime.types.simple.Type type)
    {
      super(arg, type);
    }
  
    protected void _constructor(richards.Scheduler scheduler)
    {
      final dart._runtime.types.simple.TypeEnvironment dart2java$localTypeEnv = this.dart2java$type.env;
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
