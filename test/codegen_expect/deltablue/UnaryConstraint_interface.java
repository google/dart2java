package deltablue;

public interface UnaryConstraint_interface extends deltablue.Constraint_interface
{
  void addToGraph();
  void chooseMethod(int mark);
  java.lang.Boolean isSatisfied();
  void markInputs(int mark);
  deltablue.Variable output();
  void recalculate();
  void markUnsatisfied();
  java.lang.Boolean inputsKnown(int mark);
  void removeFromGraph();
}
