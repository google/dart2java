package deltablue;

public interface Constraint_interface extends dart._runtime.base.DartObject_interface
{
  boolean isSatisfied();
  void markUnsatisfied();
  void addToGraph();
  void removeFromGraph();
  void chooseMethod(int mark);
  void markInputs(int mark);
  boolean inputsKnown(int mark);
  deltablue.Variable_interface output();
  void execute();
  void recalculate();
  void addConstraint();
  deltablue.Constraint_interface satisfy(int mark);
  void destroyConstraint();
  boolean isInput();
  deltablue.Strength_interface getStrength();
  void _constructor(deltablue.Strength_interface strength);
}
