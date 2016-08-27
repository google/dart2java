package deltablue;

public interface BinaryConstraint_interface extends deltablue.Constraint_interface
{
  void chooseMethod(int mark);
  void addToGraph();
  java.lang.Boolean isSatisfied();
  void markInputs(int mark);
  deltablue.Variable input();
  deltablue.Variable output();
  void recalculate();
  void markUnsatisfied();
  java.lang.Boolean inputsKnown(int mark);
  void removeFromGraph();
}
