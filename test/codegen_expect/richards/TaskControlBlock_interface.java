package richards;

public interface TaskControlBlock_interface extends dart._runtime.base.DartObject_interface
{
  void setRunning();
  void markAsNotHeld();
  void markAsHeld();
  boolean isHeldOrSuspended();
  void markAsSuspended();
  void markAsRunnable();
  richards.TaskControlBlock_interface run();
  richards.TaskControlBlock_interface checkPriorityAdd(richards.TaskControlBlock_interface task, richards.Packet_interface packet);
  java.lang.String toString();
  richards.TaskControlBlock_interface getLink();
  int getId();
  int getPriority();
  richards.Packet_interface getQueue();
  richards.Task_interface getTask();
  int getState();
  richards.TaskControlBlock_interface setLink(richards.TaskControlBlock_interface value);
  int setId(int value);
  int setPriority(int value);
  richards.Packet_interface setQueue(richards.Packet_interface value);
  richards.Task_interface setTask(richards.Task_interface value);
  int setState(int value);

}
