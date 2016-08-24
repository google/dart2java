package richards;

public class IdleTask extends richards.Task
{
    public static dart._runtime.types.simple.InterfaceTypeInfo dart2java$typeInfo = new dart._runtime.types.simple.InterfaceTypeInfo("file:///usr/local/google/home/stanm/f/d/ddc-java/gen/codegen_tests/richards.dart", "IdleTask");
    static {
      richards.IdleTask.dart2java$typeInfo.superclass = new dart._runtime.types.simple.InterfaceTypeExpr(richards.Task.dart2java$typeInfo);
    }
    public int v1;
    public int count;
  
    public IdleTask(richards.Scheduler scheduler, int v1, int count)
    {
      super((dart._runtime.helpers.ConstructorHelper.EmptyConstructorMarker) null);
      this._constructor(scheduler, v1, count);
    }
    public IdleTask(dart._runtime.helpers.ConstructorHelper.EmptyConstructorMarker arg)
    {
      super(arg);
    }
  
    protected void _constructor(richards.Scheduler scheduler, int v1, int count)
    {
      this.v1 = 0;
      this.count = 0;
      this.v1 = v1;
      this.count = count;
      super._constructor(scheduler);
    }
    public richards.TaskControlBlock run(richards.Packet packet)
    {
      richards.IdleTask __tempVar_1;
      dart._runtime.helpers.LetExpressionHelper.comma(__tempVar_1 = this, __tempVar_1.setCount((__tempVar_1.getCount() - 1)));
      if ((this.getCount() == 0))
      {
        return this.getScheduler().holdCurrent();
      }
      if (((this.getV1() & 1) == 0))
      {
        this.setV1((this.getV1() >> 1));
        return this.getScheduler().release(richards.Richards.ID_DEVICE_A);
      }
      this.setV1(((this.getV1() >> 1) ^ 53256));
      return this.getScheduler().release(richards.Richards.ID_DEVICE_B);
    }
    public java.lang.String toString()
    {
      return "IdleTask";
    }
    public int getV1()
    {
      return this.v1;
    }
    public int getCount()
    {
      return this.count;
    }
    public int setV1(int value)
    {
      this.v1 = value;
      return value;
    }
    public int setCount(int value)
    {
      this.count = value;
      return value;
    }
}
