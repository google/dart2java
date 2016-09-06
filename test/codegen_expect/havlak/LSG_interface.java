package havlak;

public interface LSG_interface extends dart._runtime.base.DartObject_interface
{
  havlak.SimpleLoop_interface createNewLoop();
  boolean addLoop(havlak.SimpleLoop_interface loop);
  int checksum();
  int getNumLoops();
  int getLoopCounter();
  dart.core.List_interface<havlak.SimpleLoop_interface> getLoops();
  havlak.SimpleLoop_interface getRoot();
  int setLoopCounter(int value);
}
