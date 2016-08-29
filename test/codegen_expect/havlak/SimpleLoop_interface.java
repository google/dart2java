package havlak;

public interface SimpleLoop_interface extends dart.core.Object_interface
{
  void addNode(havlak.BasicBlock_interface bb);
  void addChildLoop(havlak.SimpleLoop_interface loop);
  void setParent_(havlak.SimpleLoop_interface p);
  void setHeader_(havlak.BasicBlock_interface bb);
  void setNestingLevel_(int level);
  int checksum();
  dart._runtime.base.DartList<havlak.BasicBlock_interface> getBasicBlocks();
  dart._runtime.base.DartList<havlak.SimpleLoop_interface> getChildren();
  int getCounter();
  havlak.SimpleLoop_interface getParent();
  havlak.BasicBlock_interface getHeader();
  java.lang.Boolean getIsRoot();
  java.lang.Boolean getIsReducible();
  int getNestingLevel();
  int getDepthLevel();
  havlak.SimpleLoop_interface setParent(havlak.SimpleLoop_interface value);
  havlak.BasicBlock_interface setHeader(havlak.BasicBlock_interface value);
  java.lang.Boolean setIsRoot(java.lang.Boolean value);
  java.lang.Boolean setIsReducible(java.lang.Boolean value);
  int setNestingLevel(int value);
  int setDepthLevel(int value);
}
