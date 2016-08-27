package richards;

public interface TaskControlBlock_interface extends dart.core.Object_interface
{
  void setRunning();
  void markAsNotHeld();
  void markAsHeld();
  java.lang.Boolean isHeldOrSuspended();
  void markAsSuspended();
  void markAsRunnable();
  richards.TaskControlBlock run();
  richards.TaskControlBlock checkPriorityAdd(richards.TaskControlBlock task, richards.Packet packet);
  java.lang.String toString();
}
