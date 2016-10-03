package richards;

public interface HandlerTask_interface extends richards.Task_interface
{
  richards.TaskControlBlock_interface run(richards.Packet_interface packet);
  java.lang.String toString();
  richards.Packet_interface getV1();
  richards.Packet_interface getV2();
  richards.Packet_interface setV1(richards.Packet_interface value);
  richards.Packet_interface setV2(richards.Packet_interface value);

}
