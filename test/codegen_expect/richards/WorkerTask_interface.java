package richards;

public interface WorkerTask_interface extends richards.Task_interface
{
  richards.TaskControlBlock_interface run(richards.Packet_interface packet);
  java.lang.String toString();
  int getV1();
  int getV2();
  int setV1(int value);
  int setV2(int value);
  void _constructor(richards.Scheduler_interface scheduler, int v1, int v2);
}
