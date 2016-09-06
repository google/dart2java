package richards;

public class Richards extends richards.BenchmarkBase implements richards.Richards_interface
{
    public static dart._runtime.types.simple.InterfaceTypeInfo dart2java$typeInfo = new dart._runtime.types.simple.InterfaceTypeInfo(richards.Richards.class, richards.Richards_interface.class);
    static {
      richards.Richards.dart2java$typeInfo.superclass = new dart._runtime.types.simple.InterfaceTypeExpr(richards.BenchmarkBase.dart2java$typeInfo);
    }
    static {
      final dart._runtime.types.simple.TypeEnvironment dart2java$localTypeEnv = dart._runtime.types.simple.TypeEnvironment.ROOT;
      richards.Richards.DATA_SIZE = 4;
      richards.Richards.COUNT = 1000;
      richards.Richards.EXPECTED_QUEUE_COUNT = 2322;
      richards.Richards.EXPECTED_HOLD_COUNT = 928;
      richards.Richards.ID_IDLE = 0;
      richards.Richards.ID_WORKER = 1;
      richards.Richards.ID_HANDLER_A = 2;
      richards.Richards.ID_HANDLER_B = 3;
      richards.Richards.ID_DEVICE_A = 4;
      richards.Richards.ID_DEVICE_B = 5;
      richards.Richards.NUMBER_OF_IDS = 6;
      richards.Richards.KIND_DEVICE = 0;
      richards.Richards.KIND_WORK = 1;
    }
    public static int DATA_SIZE;
    public static int COUNT;
    public static int EXPECTED_QUEUE_COUNT;
    public static int EXPECTED_HOLD_COUNT;
    public static int ID_IDLE;
    public static int ID_WORKER;
    public static int ID_HANDLER_A;
    public static int ID_HANDLER_B;
    public static int ID_DEVICE_A;
    public static int ID_DEVICE_B;
    public static int NUMBER_OF_IDS;
    public static int KIND_DEVICE;
    public static int KIND_WORK;
  
    public Richards(dart._runtime.types.simple.Type type)
    {
      super(((dart._runtime.helpers.ConstructorHelper.EmptyConstructorMarker) null), type);
      this._constructor();
    }
    public Richards(dart._runtime.helpers.ConstructorHelper.EmptyConstructorMarker arg, dart._runtime.types.simple.Type type)
    {
      super(arg, type);
    }
  
    protected void _constructor()
    {
      final dart._runtime.types.simple.TypeEnvironment dart2java$localTypeEnv = this.dart2java$type.env;
      super._constructor("Richards");
    }
    public void run()
    {
      final dart._runtime.types.simple.TypeEnvironment dart2java$localTypeEnv = this.dart2java$type.env;
      richards.Scheduler_interface scheduler = new richards.Scheduler(dart2java$localTypeEnv.evaluate(new dart._runtime.types.simple.InterfaceTypeExpr(richards.Scheduler.dart2java$typeInfo)));
      scheduler.addIdleTask(richards.Richards.ID_IDLE, 0, null, richards.Richards.COUNT);
      richards.Packet_interface queue = new richards.Packet(dart2java$localTypeEnv.evaluate(new dart._runtime.types.simple.InterfaceTypeExpr(richards.Packet.dart2java$typeInfo)), null, richards.Richards.ID_WORKER, richards.Richards.KIND_WORK);
      queue = new richards.Packet(dart2java$localTypeEnv.evaluate(new dart._runtime.types.simple.InterfaceTypeExpr(richards.Packet.dart2java$typeInfo)), queue, richards.Richards.ID_WORKER, richards.Richards.KIND_WORK);
      scheduler.addWorkerTask(richards.Richards.ID_WORKER, 1000, queue);
      queue = new richards.Packet(dart2java$localTypeEnv.evaluate(new dart._runtime.types.simple.InterfaceTypeExpr(richards.Packet.dart2java$typeInfo)), null, richards.Richards.ID_DEVICE_A, richards.Richards.KIND_DEVICE);
      queue = new richards.Packet(dart2java$localTypeEnv.evaluate(new dart._runtime.types.simple.InterfaceTypeExpr(richards.Packet.dart2java$typeInfo)), queue, richards.Richards.ID_DEVICE_A, richards.Richards.KIND_DEVICE);
      queue = new richards.Packet(dart2java$localTypeEnv.evaluate(new dart._runtime.types.simple.InterfaceTypeExpr(richards.Packet.dart2java$typeInfo)), queue, richards.Richards.ID_DEVICE_A, richards.Richards.KIND_DEVICE);
      scheduler.addHandlerTask(richards.Richards.ID_HANDLER_A, 2000, queue);
      queue = new richards.Packet(dart2java$localTypeEnv.evaluate(new dart._runtime.types.simple.InterfaceTypeExpr(richards.Packet.dart2java$typeInfo)), null, richards.Richards.ID_DEVICE_B, richards.Richards.KIND_DEVICE);
      queue = new richards.Packet(dart2java$localTypeEnv.evaluate(new dart._runtime.types.simple.InterfaceTypeExpr(richards.Packet.dart2java$typeInfo)), queue, richards.Richards.ID_DEVICE_B, richards.Richards.KIND_DEVICE);
      queue = new richards.Packet(dart2java$localTypeEnv.evaluate(new dart._runtime.types.simple.InterfaceTypeExpr(richards.Packet.dart2java$typeInfo)), queue, richards.Richards.ID_DEVICE_B, richards.Richards.KIND_DEVICE);
      scheduler.addHandlerTask(richards.Richards.ID_HANDLER_B, 3000, queue);
      scheduler.addDeviceTask(richards.Richards.ID_DEVICE_A, 4000, null);
      scheduler.addDeviceTask(richards.Richards.ID_DEVICE_B, 5000, null);
      scheduler.schedule();
      if (((!(scheduler.getQueueCount() == richards.Richards.EXPECTED_QUEUE_COUNT)) || (!(scheduler.getHoldCount() == richards.Richards.EXPECTED_HOLD_COUNT))))
      {
        dart.core.__TopLevel.print(((((("Error during execution: queueCount = " + scheduler.getQueueCount()) + "") + ", holdCount = ") + scheduler.getHoldCount()) + "."));
      }
      if ((!(richards.Richards.EXPECTED_QUEUE_COUNT == scheduler.getQueueCount())))
      {
        throw new RuntimeException("bad scheduler queue-count");
      }
      if ((!(richards.Richards.EXPECTED_HOLD_COUNT == scheduler.getHoldCount())))
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
