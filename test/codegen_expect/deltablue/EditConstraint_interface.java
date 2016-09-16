package deltablue;

public interface EditConstraint_interface extends deltablue.UnaryConstraint_interface
{
  boolean isInput();
  void execute();
  void _constructor(deltablue.Variable_interface v, deltablue.Strength_interface str);
}
