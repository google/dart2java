package richards;

public interface Scheduler_interface extends dart.core.Object_interface
{
  void addIdleTask(int id, int priority, richards.Packet queue, int count);
  void addWorkerTask(int id, int priority, richards.Packet queue);
  void addHandlerTask(int id, int priority, richards.Packet queue);
  void addDeviceTask(int id, int priority, richards.Packet queue);
  void addRunningTask(int id, int priority, richards.Packet queue, richards.Task task);
  void addTask(int id, int priority, richards.Packet queue, richards.Task task);
  void schedule();
  richards.TaskControlBlock release(int id);
  richards.TaskControlBlock holdCurrent();
  richards.TaskControlBlock suspendCurrent();
  richards.TaskControlBlock queue(richards.Packet packet);
}
