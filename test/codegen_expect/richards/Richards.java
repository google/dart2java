package richards;

public class Richards extends richards.BenchmarkBase
{
    public static dart._runtime.types.simple.InterfaceTypeInfo dart2java$typeInfo = new dart._runtime.types.simple.InterfaceTypeInfo("file:///usr/local/google/home/springerm/ddc-java/gen/codegen_tests/richards.dart", "Richards");
    static {
      richards.Richards.dart2java$typeInfo.superclass = new dart._runtime.types.simple.InterfaceTypeExpr(richards.BenchmarkBase.dart2java$typeInfo);
    }
    public static int DATA_SIZE = 4;
    public static int COUNT = 1000;
    public static int EXPECTED_QUEUE_COUNT = 2322;
    public static int EXPECTED_HOLD_COUNT = 928;
    public static int ID_IDLE = 0;
    public static int ID_WORKER = 1;
    public static int ID_HANDLER_A = 2;
    public static int ID_HANDLER_B = 3;
    public static int ID_DEVICE_A = 4;
    public static int ID_DEVICE_B = 5;
    public static int NUMBER_OF_IDS = 6;
    public static int KIND_DEVICE = 0;
    public static int KIND_WORK = 1;
  
    public Richards()
    {
      super((dart._runtime.helpers.ConstructorHelper.EmptyConstructorMarker) null);
      this._constructor();
    }
    public Richards(dart._runtime.helpers.ConstructorHelper.EmptyConstructorMarker arg)
    {
      super(arg);
    }
  
    protected void _constructor()
    {
      this.DATA_SIZE = 4;
      this.COUNT = 1000;
      this.EXPECTED_QUEUE_COUNT = 2322;
      this.EXPECTED_HOLD_COUNT = 928;
      this.ID_IDLE = 0;
      this.ID_WORKER = 1;
      this.ID_HANDLER_A = 2;
      this.ID_HANDLER_B = 3;
      this.ID_DEVICE_A = 4;
      this.ID_DEVICE_B = 5;
      this.NUMBER_OF_IDS = 6;
      this.KIND_DEVICE = 0;
      this.KIND_WORK = 1;
      super._constructor("Richards");
    }
    public void run()
    {
      richards.Scheduler scheduler = new richards.Scheduler();
      scheduler.addIdleTask(richards.Richards.ID_IDLE, 0, null, richards.Richards.COUNT);
      richards.Packet queue = new richards.Packet(null, richards.Richards.ID_WORKER, richards.Richards.KIND_WORK);
      queue = new richards.Packet(queue, richards.Richards.ID_WORKER, richards.Richards.KIND_WORK);
      scheduler.addWorkerTask(richards.Richards.ID_WORKER, 1000, queue);
      queue = new richards.Packet(null, richards.Richards.ID_DEVICE_A, richards.Richards.KIND_DEVICE);
      queue = new richards.Packet(queue, richards.Richards.ID_DEVICE_A, richards.Richards.KIND_DEVICE);
      queue = new richards.Packet(queue, richards.Richards.ID_DEVICE_A, richards.Richards.KIND_DEVICE);
      scheduler.addHandlerTask(richards.Richards.ID_HANDLER_A, 2000, queue);
      queue = new richards.Packet(null, richards.Richards.ID_DEVICE_B, richards.Richards.KIND_DEVICE);
      queue = new richards.Packet(queue, richards.Richards.ID_DEVICE_B, richards.Richards.KIND_DEVICE);
      queue = new richards.Packet(queue, richards.Richards.ID_DEVICE_B, richards.Richards.KIND_DEVICE);
      scheduler.addHandlerTask(richards.Richards.ID_HANDLER_B, 3000, queue);
      scheduler.addDeviceTask(richards.Richards.ID_DEVICE_A, 4000, null);
      scheduler.addDeviceTask(richards.Richards.ID_DEVICE_B, 5000, null);
      scheduler.schedule();
      if (((!dart._runtime.helpers.ObjectHelper.operatorEqual(scheduler.getQueueCount(), richards.Richards.EXPECTED_QUEUE_COUNT)) || (!dart._runtime.helpers.ObjectHelper.operatorEqual(scheduler.getHoldCount(), richards.Richards.EXPECTED_HOLD_COUNT))))
      {
        dart.core.__TopLevel.print(((((("Error during execution: queueCount = " + scheduler.getQueueCount()) + "") + ", holdCount = ") + scheduler.getHoldCount()) + "."));
      }
      if ((!dart._runtime.helpers.ObjectHelper.operatorEqual(richards.Richards.EXPECTED_QUEUE_COUNT, scheduler.getQueueCount())))
      {
        throw new RuntimeException("bad scheduler queue-count");
      }
      if ((!dart._runtime.helpers.ObjectHelper.operatorEqual(richards.Richards.EXPECTED_HOLD_COUNT, scheduler.getHoldCount())))
      {
        throw new RuntimeException("bad scheduler hold-count");
      }
    }
    public static int getDATA_SIZE()
    {
      return richards.Richards.DATA_SIZE;
    }
    public static int getCOUNT()
    {
      return richards.Richards.COUNT;
    }
    public static int getEXPECTED_QUEUE_COUNT()
    {
      return richards.Richards.EXPECTED_QUEUE_COUNT;
    }
    public static int getEXPECTED_HOLD_COUNT()
    {
      return richards.Richards.EXPECTED_HOLD_COUNT;
    }
    public static int getID_IDLE()
    {
      return richards.Richards.ID_IDLE;
    }
    public static int getID_WORKER()
    {
      return richards.Richards.ID_WORKER;
    }
    public static int getID_HANDLER_A()
    {
      return richards.Richards.ID_HANDLER_A;
    }
    public static int getID_HANDLER_B()
    {
      return richards.Richards.ID_HANDLER_B;
    }
    public static int getID_DEVICE_A()
    {
      return richards.Richards.ID_DEVICE_A;
    }
    public static int getID_DEVICE_B()
    {
      return richards.Richards.ID_DEVICE_B;
    }
    public static int getNUMBER_OF_IDS()
    {
      return richards.Richards.NUMBER_OF_IDS;
    }
    public static int getKIND_DEVICE()
    {
      return richards.Richards.KIND_DEVICE;
    }
    public static int getKIND_WORK()
    {
      return richards.Richards.KIND_WORK;
    }
    public static int setDATA_SIZE(int value)
    {
      richards.Richards.DATA_SIZE = value;
      return value;
    }
    public static int setCOUNT(int value)
    {
      richards.Richards.COUNT = value;
      return value;
    }
    public static int setEXPECTED_QUEUE_COUNT(int value)
    {
      richards.Richards.EXPECTED_QUEUE_COUNT = value;
      return value;
    }
    public static int setEXPECTED_HOLD_COUNT(int value)
    {
      richards.Richards.EXPECTED_HOLD_COUNT = value;
      return value;
    }
    public static int setID_IDLE(int value)
    {
      richards.Richards.ID_IDLE = value;
      return value;
    }
    public static int setID_WORKER(int value)
    {
      richards.Richards.ID_WORKER = value;
      return value;
    }
    public static int setID_HANDLER_A(int value)
    {
      richards.Richards.ID_HANDLER_A = value;
      return value;
    }
    public static int setID_HANDLER_B(int value)
    {
      richards.Richards.ID_HANDLER_B = value;
      return value;
    }
    public static int setID_DEVICE_A(int value)
    {
      richards.Richards.ID_DEVICE_A = value;
      return value;
    }
    public static int setID_DEVICE_B(int value)
    {
      richards.Richards.ID_DEVICE_B = value;
      return value;
    }
    public static int setNUMBER_OF_IDS(int value)
    {
      richards.Richards.NUMBER_OF_IDS = value;
      return value;
    }
    public static int setKIND_DEVICE(int value)
    {
      richards.Richards.KIND_DEVICE = value;
      return value;
    }
    public static int setKIND_WORK(int value)
    {
      richards.Richards.KIND_WORK = value;
      return value;
    }
}
