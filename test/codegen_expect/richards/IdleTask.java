package richards;

public class IdleTask extends richards.Task
{
    public java.lang.Integer v1 = null;
    public java.lang.Integer count = null;
  
    public IdleTask(richards.Scheduler scheduler, java.lang.Integer v1, java.lang.Integer count)
    {
      super((dart._runtime.helpers.ConstructorHelper.EmptyConstructorMarker) null);
      this._constructor(scheduler, v1, count);
    }
    public IdleTask(dart._runtime.helpers.ConstructorHelper.EmptyConstructorMarker arg)
    {
      super(arg);
    }
  
    protected void _constructor(richards.Scheduler scheduler, java.lang.Integer v1, java.lang.Integer count)
    {
      this.v1 = v1;
      this.count = count;
      super._constructor(scheduler);
    }
    public richards.TaskControlBlock run(richards.Packet packet)
    {
      richards.IdleTask __tempVar_1;
      dart._runtime.helpers.LetExpressionHelper.comma(__tempVar_1 = this, __tempVar_1.setCount(dart._runtime.helpers.IntegerHelper.operatorMinus(__tempVar_1.getCount(), 1)));
      if (dart._runtime.helpers.ObjectHelper.operatorEqual(this.getCount(), 0))
      {
        return this.getScheduler().holdCurrent();
      }
      if (dart._runtime.helpers.ObjectHelper.operatorEqual(dart._runtime.helpers.IntegerHelper.operatorBitAnd(this.getV1(), 1), 0))
      {
        this.setV1(dart._runtime.helpers.IntegerHelper.operatorShiftRight(this.getV1(), 1));
        return this.getScheduler().release(richards.Richards.ID_DEVICE_A);
      }
      this.setV1(dart._runtime.helpers.IntegerHelper.operatorBitXor(dart._runtime.helpers.IntegerHelper.operatorShiftRight(this.getV1(), 1), 53256));
      return this.getScheduler().release(richards.Richards.ID_DEVICE_B);
    }
    public java.lang.String toString()
    {
      return "IdleTask";
    }
    public java.lang.Integer getV1()
    {
      return this.v1;
    }
    public java.lang.Integer getCount()
    {
      return this.count;
    }
    public java.lang.Integer setV1(java.lang.Integer value)
    {
      this.v1 = value;
      return value;
    }
    public java.lang.Integer setCount(java.lang.Integer value)
    {
      this.count = value;
      return value;
    }
}
