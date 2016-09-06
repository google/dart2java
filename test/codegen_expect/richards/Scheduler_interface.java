package richards;

public interface Scheduler_interface extends dart._runtime.base.DartObject_interface
{
  void addIdleTask(int id, int priority, richards.Packet_interface queue, int count);
  void addWorkerTask(int id, int priority, richards.Packet_interface queue);
  void addHandlerTask(int id, int priority, richards.Packet_interface queue);
  void addDeviceTask(int id, int priority, richards.Packet_interface queue);
  void addRunningTask(int id, int priority, richards.Packet_interface queue, richards.Task_interface task);
  void addTask(int id, int priority, richards.Packet_interface queue, richards.Task_interface task);
  void schedule();
  richards.TaskControlBlock_interface release(int id);
  richards.TaskControlBlock_interface holdCurrent();
  richards.TaskControlBlock_interface suspendCurrent();
  richards.TaskControlBlock_interface queue(richards.Packet_interface packet);
  int getQueueCount();
  int getHoldCount();
  richards.TaskControlBlock_interface getCurrentTcb();
  int getCurrentId();
  richards.TaskControlBlock_interface getList();
  dart.core.List_interface<richards.TaskControlBlock_interface> getBlocks();
  int setQueueCount(int value);
  int setHoldCount(int value);
  richards.TaskControlBlock_interface setCurrentTcb(richards.TaskControlBlock_interface value);
  int setCurrentId(int value);
  richards.TaskControlBlock_interface setList(richards.TaskControlBlock_interface value);
  dart.core.List_interface<richards.TaskControlBlock_interface> setBlocks(dart.core.List_interface<richards.TaskControlBlock_interface> value);
}
