package deltablue;

public interface ScaleConstraint_interface extends deltablue.BinaryConstraint_interface
{
  void addToGraph();
  void removeFromGraph();
  void markInputs(int mark);
  void execute();
  void recalculate();
}
