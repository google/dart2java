package richards;

public class WorkerTask extends richards.Task
{
    public java.lang.Integer v1 = null;
    public java.lang.Integer v2 = null;
  
    public WorkerTask(richards.Scheduler scheduler, java.lang.Integer v1, java.lang.Integer v2)
    {
      super((dart._runtime.helpers.ConstructorHelper.EmptyConstructorMarker) null);
      this._constructor(scheduler, v1, v2);
    }
    public WorkerTask(dart._runtime.helpers.ConstructorHelper.EmptyConstructorMarker arg)
    {
      super(arg);
    }
  
    protected void _constructor(richards.Scheduler scheduler, java.lang.Integer v1, java.lang.Integer v2)
    {
      this.v1 = v1;
      this.v2 = v2;
      super._constructor(scheduler);
    }
    public richards.TaskControlBlock run(richards.Packet packet)
    {
      richards.WorkerTask __tempVar_1;
      if (dart._runtime.helpers.ObjectHelper.operatorEqual(packet, null))
      {
        return this.getScheduler().suspendCurrent();
      }
      if (dart._runtime.helpers.ObjectHelper.operatorEqual(this.getV1(), richards.Richards.ID_HANDLER_A))
      {
        this.setV1(richards.Richards.ID_HANDLER_B);
      }
      else
      {
        this.setV1(richards.Richards.ID_HANDLER_A);
      }
      packet.setId(this.getV1());
      packet.setA1(0);
      for (java.lang.Integer i = 0; dart._runtime.helpers.IntegerHelper.operatorLess(i, richards.Richards.DATA_SIZE); i = dart._runtime.helpers.IntegerHelper.operatorPlus(i, 1))
      {
        dart._runtime.helpers.LetExpressionHelper.comma(__tempVar_1 = this, __tempVar_1.setV2(dart._runtime.helpers.IntegerHelper.operatorPlus(__tempVar_1.getV2(), 1)));
        if (dart._runtime.helpers.IntegerHelper.operatorGreater(this.getV2(), 26))
        {
          this.setV2(1);
        }
        packet.getA2().operatorAtPut_primitive(i, this.getV2());
      }
      return this.getScheduler().queue(packet);
    }
    public java.lang.String toString()
    {
      return "WorkerTask";
    }
    public java.lang.Integer getV1()
    {
      return this.v1;
    }
    public java.lang.Integer getV2()
    {
      return this.v2;
    }
    public java.lang.Integer setV1(java.lang.Integer value)
    {
      this.v1 = value;
      return value;
    }
    public java.lang.Integer setV2(java.lang.Integer value)
    {
      this.v2 = value;
      return value;
    }
}
