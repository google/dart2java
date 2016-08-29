package richards;

public interface WorkerTask_interface extends richards.Task_interface
{
  richards.TaskControlBlock_interface run(richards.Packet_interface packet);
  java.lang.String toString();
  int getV1();
  int getV2();
  int setV1(int value);
  int setV2(int value);
}
