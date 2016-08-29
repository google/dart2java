package havlak;

public interface CFG_interface extends dart.core.Object_interface
{
  havlak.BasicBlock_interface createNode(int name);
  void addEdge(havlak.BasicBlockEdge_interface edge);
  int getNumNodes();
  havlak.BasicBlock_interface getDst(havlak.BasicBlockEdge_interface edge);
  havlak.BasicBlock_interface getSrc(havlak.BasicBlockEdge_interface edge);
  dart._runtime.base.DartMap<java.lang.Integer, havlak.BasicBlock_interface> getBasicBlockMap();
  dart._runtime.base.DartList<havlak.BasicBlockEdge_interface> getEdgeList();
  havlak.BasicBlock_interface getStartNode();
  havlak.BasicBlock_interface setStartNode(havlak.BasicBlock_interface value);
}
