package richards;

public class HandlerTask extends richards.Task
{
    public static dart._runtime.types.simple.InterfaceTypeInfo dart2java$typeInfo = new dart._runtime.types.simple.InterfaceTypeInfo("file:///usr/local/google/home/stanm/f/d/ddc-java/gen/codegen_tests/richards.dart", "HandlerTask");
    static {
      richards.HandlerTask.dart2java$typeInfo.superclass = new dart._runtime.types.simple.InterfaceTypeExpr(richards.Task.dart2java$typeInfo);
    }
    public richards.Packet v1;
    public richards.Packet v2;
  
    public HandlerTask(richards.Scheduler scheduler)
    {
      super((dart._runtime.helpers.ConstructorHelper.EmptyConstructorMarker) null);
      this._constructor(scheduler);
    }
    public HandlerTask(dart._runtime.helpers.ConstructorHelper.EmptyConstructorMarker arg)
    {
      super(arg);
    }
  
    protected void _constructor(richards.Scheduler scheduler)
    {
      super._constructor(scheduler);
    }
    public richards.TaskControlBlock run(richards.Packet packet)
    {
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
        richards.Packet v = null;
        if ((count < richards.Richards.DATA_SIZE))
        {
          if ((!dart._runtime.helpers.ObjectHelper.operatorEqual(this.getV2(), null)))
          {
            v = this.getV2();
            this.setV2(this.getV2().getLink());
            v.setA1(this.getV1().getA2().operatorAt_primitive(count));
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
      return "HandlerTask";
    }
    public richards.Packet getV1()
    {
      return this.v1;
    }
    public richards.Packet getV2()
    {
      return this.v2;
    }
    public richards.Packet setV1(richards.Packet value)
    {
      this.v1 = value;
      return value;
    }
    public richards.Packet setV2(richards.Packet value)
    {
      this.v2 = value;
      return value;
    }
}
