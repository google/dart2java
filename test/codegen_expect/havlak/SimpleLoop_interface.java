package havlak;

public interface SimpleLoop_interface extends dart.core.Object_interface
{
  void addNode(havlak.BasicBlock bb);
  void addChildLoop(havlak.SimpleLoop loop);
  void setParent_(havlak.SimpleLoop p);
  void setHeader_(havlak.BasicBlock bb);
  void setNestingLevel_(int level);
  int checksum();
}
