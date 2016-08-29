package havlak;

public interface BasicBlock_interface extends dart.core.Object_interface
{
  java.lang.String toString();
  int getNumPred();
  int getNumSucc();
  void addInEdge(havlak.BasicBlock_interface bb);
  void addOutEdge(havlak.BasicBlock_interface bb);
  int getName();
  dart._runtime.base.DartList<havlak.BasicBlock_interface> getInEdges();
  dart._runtime.base.DartList<havlak.BasicBlock_interface> getOutEdges();
  dart._runtime.base.DartList<havlak.BasicBlock_interface> setInEdges(dart._runtime.base.DartList<havlak.BasicBlock_interface> value);
  dart._runtime.base.DartList<havlak.BasicBlock_interface> setOutEdges(dart._runtime.base.DartList<havlak.BasicBlock_interface> value);
}
