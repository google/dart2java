package havlak;

public interface BasicBlock_interface extends dart.core.Object_interface
{
  java.lang.String toString();
  int getNumPred();
  int getNumSucc();
  void addInEdge(havlak.BasicBlock bb);
  void addOutEdge(havlak.BasicBlock bb);
}
