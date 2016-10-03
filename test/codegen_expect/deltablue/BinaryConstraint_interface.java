package deltablue;

public interface BinaryConstraint_interface extends deltablue.Constraint_interface
{
  void chooseMethod(int mark);
  void addToGraph();
  boolean isSatisfied();
  void markInputs(int mark);
  deltablue.Variable_interface input();
  deltablue.Variable_interface output();
  void recalculate();
  void markUnsatisfied();
  boolean inputsKnown(int mark);
  void removeFromGraph();
  deltablue.Variable_interface getV1();
  deltablue.Variable_interface getV2();
  int getDirection();
  deltablue.Variable_interface setV1(deltablue.Variable_interface value);
  deltablue.Variable_interface setV2(deltablue.Variable_interface value);
  int setDirection(int value);

}
