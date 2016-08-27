package richards;

public interface IdleTask_interface extends richards.Task_interface
{
  richards.TaskControlBlock run(richards.Packet packet);
  java.lang.String toString();
}
