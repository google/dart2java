package havlak;

public interface SimpleLoop_interface extends dart.core.Object_interface
{
  boolean addNode(havlak.BasicBlock_interface bb);
  boolean addChildLoop(havlak.SimpleLoop_interface loop);
  void setParent_(havlak.SimpleLoop_interface p);
  void setHeader_(havlak.BasicBlock_interface bb);
  void setNestingLevel_(int level);
  int checksum();
  dart._runtime.base.DartList<havlak.BasicBlock_interface> getBasicBlocks();
  dart._runtime.base.DartList<havlak.SimpleLoop_interface> getChildren();
  int getCounter();
  havlak.SimpleLoop_interface getParent();
  havlak.BasicBlock_interface getHeader();
  boolean getIsRoot();
  boolean getIsReducible();
  int getNestingLevel();
  int getDepthLevel();
  havlak.SimpleLoop_interface setParent(havlak.SimpleLoop_interface value);
  havlak.BasicBlock_interface setHeader(havlak.BasicBlock_interface value);
  boolean setIsRoot(boolean value);
  boolean setIsReducible(boolean value);
  int setNestingLevel(int value);
  int setDepthLevel(int value);
}
