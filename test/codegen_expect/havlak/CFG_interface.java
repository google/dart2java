package havlak;

public interface CFG_interface extends dart._runtime.base.DartObject_interface
{
  havlak.BasicBlock_interface createNode(int name);
  boolean addEdge(havlak.BasicBlockEdge_interface edge);
  int getNumNodes();
  havlak.BasicBlock_interface getDst(havlak.BasicBlockEdge_interface edge);
  havlak.BasicBlock_interface getSrc(havlak.BasicBlockEdge_interface edge);
  dart.core.Map_interface__int_generic<havlak.BasicBlock_interface> getBasicBlockMap();
  dart.core.List_interface<havlak.BasicBlockEdge_interface> getEdgeList();
  havlak.BasicBlock_interface getStartNode();
  havlak.BasicBlock_interface setStartNode(havlak.BasicBlock_interface value);
  void _constructor();
}
