package deltablue;

public interface StayConstraint_interface extends deltablue.UnaryConstraint_interface
{
  void execute();
  void _constructor(deltablue.Variable_interface v, deltablue.Strength_interface str);
}
