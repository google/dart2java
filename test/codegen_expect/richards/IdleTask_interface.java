package richards;

public interface IdleTask_interface extends richards.Task_interface
{
  richards.TaskControlBlock_interface run(richards.Packet_interface packet);
  java.lang.String toString();
  int getV1();
  int getCount();
  int setV1(int value);
  int setCount(int value);
}
