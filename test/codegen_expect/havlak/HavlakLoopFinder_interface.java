package havlak;

public interface HavlakLoopFinder_interface extends dart.core.Object_interface
{
  java.lang.Boolean isAncestor(int w, int v, dart._runtime.base.DartList._int last);
  int DFS(havlak.BasicBlock currentNode, dart._runtime.base.DartList<havlak.UnionFindNode> nodes, dart._runtime.base.DartList._int number, dart._runtime.base.DartList._int last, int current);
  int findLoops();
}
