package havlak;

public interface UnionFindNode_interface extends dart.core.Object_interface
{
  void initNode(havlak.BasicBlock bb, int dfsNumber);
  havlak.UnionFindNode findSet();
  void union(havlak.UnionFindNode unionFindNode);
  havlak.SimpleLoop setLoop_(havlak.SimpleLoop l);
}
