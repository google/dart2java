package deltablue;

public interface ScaleConstraint_interface extends deltablue.BinaryConstraint_interface
{
  void addToGraph();
  void removeFromGraph();
  void markInputs(int mark);
  void execute();
  void recalculate();
  deltablue.Variable_interface getScale();
  deltablue.Variable_interface getOffset();
  void _constructor(deltablue.Variable_interface src, deltablue.Variable_interface scale, deltablue.Variable_interface offset, deltablue.Variable_interface dest, deltablue.Strength_interface strength);
}
