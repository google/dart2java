package richards;

public class HandlerTask extends richards.Task
{
    public richards.Packet v1 = null;
    public richards.Packet v2 = null;
  
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
        if (dart._runtime.helpers.ObjectHelper.operatorEqual(packet.getKind(), richards.Richards.KIND_WORK))
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
        java.lang.Integer count = this.getV1().getA1();
        richards.Packet v = null;
        if (dart._runtime.helpers.IntegerHelper.operatorLess(count, richards.Richards.DATA_SIZE))
        {
          if ((!dart._runtime.helpers.ObjectHelper.operatorEqual(this.getV2(), null)))
          {
            v = this.getV2();
            this.setV2(this.getV2().getLink());
            v.setA1(this.getV1().getA2().operatorAt_primitive(count));
            this.getV1().setA1(dart._runtime.helpers.IntegerHelper.operatorPlus(count, 1));
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
