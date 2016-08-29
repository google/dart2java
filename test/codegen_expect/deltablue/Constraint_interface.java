package deltablue;

public interface Constraint_interface extends dart.core.Object_interface
{
  java.lang.Boolean isSatisfied();
  void markUnsatisfied();
  void addToGraph();
  void removeFromGraph();
  void chooseMethod(int mark);
  void markInputs(int mark);
  java.lang.Boolean inputsKnown(int mark);
  deltablue.Variable_interface output();
  void execute();
  void recalculate();
  void addConstraint();
  deltablue.Constraint_interface satisfy(int mark);
  void destroyConstraint();
  java.lang.Boolean isInput();
  deltablue.Strength_interface getStrength();
}
