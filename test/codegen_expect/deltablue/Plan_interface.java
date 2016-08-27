package deltablue;

public interface Plan_interface extends dart.core.Object_interface
{
  void addConstraint(deltablue.Constraint c);
  int size();
  void execute();
}
