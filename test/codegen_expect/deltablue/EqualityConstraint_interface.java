package deltablue;

public interface EqualityConstraint_interface extends deltablue.BinaryConstraint_interface
{
  void execute();
  void _constructor(deltablue.Variable_interface v1, deltablue.Variable_interface v2, deltablue.Strength_interface strength);
}
