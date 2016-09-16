package richards;

public interface Task_interface extends dart._runtime.base.DartObject_interface
{
  richards.TaskControlBlock_interface run(richards.Packet_interface packet);
  richards.Scheduler_interface getScheduler();
  richards.Scheduler_interface setScheduler(richards.Scheduler_interface value);
  void _constructor(richards.Scheduler_interface scheduler);
}
