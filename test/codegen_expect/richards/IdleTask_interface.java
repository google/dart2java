package richards;

public interface IdleTask_interface extends richards.Task_interface
{
  richards.TaskControlBlock_interface run(richards.Packet_interface packet);
  java.lang.String toString();
  int getV1();
  int getCount();
  int setV1(int value);
  int setCount(int value);
  void _constructor(richards.Scheduler_interface scheduler, int v1, int count);
}
