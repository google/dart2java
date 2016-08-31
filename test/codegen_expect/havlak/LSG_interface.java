package havlak;

public interface LSG_interface extends dart.core.Object_interface
{
  havlak.SimpleLoop_interface createNewLoop();
  boolean addLoop(havlak.SimpleLoop_interface loop);
  int checksum();
  int getNumLoops();
  int getLoopCounter();
  dart._runtime.base.DartList<havlak.SimpleLoop_interface> getLoops();
  havlak.SimpleLoop_interface getRoot();
  int setLoopCounter(int value);
}
