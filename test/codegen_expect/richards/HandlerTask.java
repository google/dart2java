package richards;

public class HandlerTask extends richards.Task implements richards.HandlerTask_interface
{
    public static dart._runtime.types.simple.InterfaceTypeInfo dart2java$typeInfo = new dart._runtime.types.simple.InterfaceTypeInfo(richards.HandlerTask.class, richards.HandlerTask_interface.class);
    private static dart._runtime.types.simple.InterfaceTypeExpr dart2java$typeExpr_Task = new dart._runtime.types.simple.InterfaceTypeExpr(richards.Task.dart2java$typeInfo);
    static {
      richards.HandlerTask.dart2java$typeInfo.superclass = dart2java$typeExpr_Task;
    }
    public richards.Packet_interface v1;
    public richards.Packet_interface v2;
  
    public HandlerTask(dart._runtime.helpers.ConstructorHelper.EmptyConstructorMarker arg, dart._runtime.types.simple.Type type)
    {
      super(arg, type);
    }
  
    public richards.TaskControlBlock_interface run(richards.Packet_interface packet)
    {
      final dart._runtime.types.simple.TypeEnvironment dart2java$localTypeEnv = this.dart2java$type.env;
      if ((!dart._runtime.helpers.ObjectHelper.operatorEqual(packet, null)))
      {
        if ((packet.getKind() == richards.Richards.KIND_WORK))
        {
          this.setV1(packet.addTo(this.getV1()));
        }
        else
        {
          this.setV2(packet.addTo(this.getV2()));
        }
      }
      if ((!dart._runtime.helpers.ObjectHelper.operatorEqual(this.getV1(), null)))
      {
        int count = this.getV1().getA1();
        richards.Packet_interface v = null;
        if ((count < richards.Richards.DATA_SIZE))
        {
          if ((!dart._runtime.helpers.ObjectHelper.operatorEqual(this.getV2(), null)))
          {
            v = this.getV2();
            this.setV2(this.getV2().getLink());
            v.setA1(this.getV1().getA2().operatorAt__int(count));
            this.getV1().setA1((count + 1));
            return this.getScheduler().queue(v);
          }
        }
        else
        {
          v = this.getV1();
          this.setV1(this.getV1().getLink());
          return this.getScheduler().queue(v);
        }
      }
      return this.getScheduler().suspendCurrent();
    }
    public java.lang.String toString()
    {
      final dart._runtime.types.simple.TypeEnvironment dart2java$localTypeEnv = this.dart2java$type.env;
      return "HandlerTask";
    }
    public void _constructor(richards.Scheduler_interface scheduler)
    {
      final dart._runtime.types.simple.TypeEnvironment dart2java$localTypeEnv = this.dart2java$type.env;
      super._constructor(scheduler);
    }
    public richards.Packet_interface getV1()
    {
      return this.v1;
    }
    public richards.Packet_interface getV2()
    {
      return this.v2;
    }
    public richards.Packet_interface setV1(richards.Packet_interface value)
    {
      this.v1 = value;
      return value;
    }
    public richards.Packet_interface setV2(richards.Packet_interface value)
    {
      this.v2 = value;
      return value;
    }
    public static richards.HandlerTask_interface _new(dart._runtime.types.simple.Type type, richards.Scheduler_interface scheduler)
    {
      richards.HandlerTask_interface result;
      result = new richards.HandlerTask(((dart._runtime.helpers.ConstructorHelper.EmptyConstructorMarker) null), type);
      result._constructor(scheduler);
      return result;
    }
}
