package richards;

public class DeviceTask extends richards.Task implements richards.DeviceTask_interface
{
    public static dart._runtime.types.simple.InterfaceTypeInfo dart2java$typeInfo = new dart._runtime.types.simple.InterfaceTypeInfo(richards.DeviceTask.class, richards.DeviceTask_interface.class);
    private static dart._runtime.types.simple.InterfaceTypeExpr dart2java$typeExpr_Task = new dart._runtime.types.simple.InterfaceTypeExpr(richards.Task.dart2java$typeInfo);
    static {
      richards.DeviceTask.dart2java$typeInfo.superclass = dart2java$typeExpr_Task;
    }
    public richards.Packet_interface v1;
  
    public DeviceTask(dart._runtime.types.simple.Type type, richards.Scheduler_interface scheduler)
    {
      super(((dart._runtime.helpers.ConstructorHelper.EmptyConstructorMarker) null), type);
      this._constructor(scheduler);
    }
    public DeviceTask(dart._runtime.helpers.ConstructorHelper.EmptyConstructorMarker arg, dart._runtime.types.simple.Type type)
    {
      super(arg, type);
    }
  
    protected void _constructor(richards.Scheduler_interface scheduler)
    {
      final dart._runtime.types.simple.TypeEnvironment dart2java$localTypeEnv = this.dart2java$type.env;
      super._constructor(scheduler);
    }
    public richards.TaskControlBlock_interface run(richards.Packet_interface packet)
    {
      final dart._runtime.types.simple.TypeEnvironment dart2java$localTypeEnv = this.dart2java$type.env;
      if (dart._runtime.helpers.ObjectHelper.operatorEqual(packet, null))
      {
        if (dart._runtime.helpers.ObjectHelper.operatorEqual(this.getV1(), null))
        {
          return this.getScheduler().suspendCurrent();
        }
        richards.Packet_interface v = this.getV1();
        this.setV1(null);
        return this.getScheduler().queue(v);
      }
      this.setV1(packet);
      return this.getScheduler().holdCurrent();
    }
    public java.lang.String toString()
    {
      final dart._runtime.types.simple.TypeEnvironment dart2java$localTypeEnv = this.dart2java$type.env;
      return "DeviceTask";
    }
    public richards.Packet_interface getV1()
    {
      return this.v1;
    }
    public richards.Packet_interface setV1(richards.Packet_interface value)
    {
      this.v1 = value;
      return value;
    }
}
