package havlak;

public interface BasicBlock_interface extends dart._runtime.base.DartObject_interface
{
  java.lang.String toString();
  int getNumPred();
  int getNumSucc();
  boolean addInEdge(havlak.BasicBlock_interface bb);
  boolean addOutEdge(havlak.BasicBlock_interface bb);
  int getName();
  dart.core.List_interface<havlak.BasicBlock_interface> getInEdges();
  dart.core.List_interface<havlak.BasicBlock_interface> getOutEdges();
  dart.core.List_interface<havlak.BasicBlock_interface> setInEdges(dart.core.List_interface<havlak.BasicBlock_interface> value);
  dart.core.List_interface<havlak.BasicBlock_interface> setOutEdges(dart.core.List_interface<havlak.BasicBlock_interface> value);
}
