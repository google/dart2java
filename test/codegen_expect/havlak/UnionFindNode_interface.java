package havlak;

public interface UnionFindNode_interface extends dart._runtime.base.DartObject_interface
{
  void initNode(havlak.BasicBlock_interface bb, int dfsNumber);
  havlak.UnionFindNode_interface findSet();
  void union(havlak.UnionFindNode_interface unionFindNode);
  havlak.SimpleLoop_interface setLoop_(havlak.SimpleLoop_interface l);
  int getDfsNumber();
  havlak.UnionFindNode_interface getParent();
  havlak.BasicBlock_interface getBb();
  havlak.SimpleLoop_interface getLoop();
  int setDfsNumber(int value);
  havlak.UnionFindNode_interface setParent(havlak.UnionFindNode_interface value);
  havlak.BasicBlock_interface setBb(havlak.BasicBlock_interface value);
  havlak.SimpleLoop_interface setLoop(havlak.SimpleLoop_interface value);
  void _constructor();
}
