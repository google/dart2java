package deltablue;

public interface UnaryConstraint_interface extends deltablue.Constraint_interface
{
  void addToGraph();
  void chooseMethod(int mark);
  java.lang.Boolean isSatisfied();
  void markInputs(int mark);
  deltablue.Variable_interface output();
  void recalculate();
  void markUnsatisfied();
  java.lang.Boolean inputsKnown(int mark);
  void removeFromGraph();
  deltablue.Variable_interface getMyOutput();
  java.lang.Boolean getSatisfied();
  java.lang.Boolean setSatisfied(java.lang.Boolean value);
}
