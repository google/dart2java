package havlak;

public interface LSG_interface extends dart.core.Object_interface
{
  havlak.SimpleLoop createNewLoop();
  void addLoop(havlak.SimpleLoop loop);
  int checksum();
  int getNumLoops();
}
