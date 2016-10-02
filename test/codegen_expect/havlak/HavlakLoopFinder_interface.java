package havlak;

public interface HavlakLoopFinder_interface extends dart._runtime.base.DartObject_interface
{
  boolean isAncestor(int w, int v, dart.core.List_interface<java.lang.Integer> last);
  int DFS(havlak.BasicBlock_interface currentNode, dart.core.List_interface<havlak.UnionFindNode_interface> nodes, dart.core.List_interface<java.lang.Integer> number, dart.core.List_interface<java.lang.Integer> last, int current);
  int findLoops();
  havlak.CFG_interface getCfg();
  havlak.LSG_interface getLsg();

}
