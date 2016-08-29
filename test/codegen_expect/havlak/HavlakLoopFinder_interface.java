package havlak;

public interface HavlakLoopFinder_interface extends dart.core.Object_interface
{
  boolean isAncestor(int w, int v, dart._runtime.base.DartList._int last);
  int DFS(havlak.BasicBlock_interface currentNode, dart._runtime.base.DartList<havlak.UnionFindNode_interface> nodes, dart._runtime.base.DartList._int number, dart._runtime.base.DartList._int last, int current);
  int findLoops();
  havlak.CFG_interface getCfg();
  havlak.LSG_interface getLsg();
}
