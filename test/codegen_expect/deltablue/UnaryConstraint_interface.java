package deltablue;

public interface UnaryConstraint_interface extends deltablue.Constraint_interface
{
  void addToGraph();
  void chooseMethod(int mark);
  boolean isSatisfied();
  void markInputs(int mark);
  deltablue.Variable_interface output();
  void recalculate();
  void markUnsatisfied();
  boolean inputsKnown(int mark);
  void removeFromGraph();
  deltablue.Variable_interface getMyOutput();
  boolean getSatisfied();
  boolean setSatisfied(boolean value);

}
