package havlak;

public interface CFG_interface extends dart.core.Object_interface
{
  havlak.BasicBlock createNode(int name);
  void addEdge(havlak.BasicBlockEdge edge);
  int getNumNodes();
  havlak.BasicBlock getDst(havlak.BasicBlockEdge edge);
  havlak.BasicBlock getSrc(havlak.BasicBlockEdge edge);
}
