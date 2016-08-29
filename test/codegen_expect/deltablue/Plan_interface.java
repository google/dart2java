package deltablue;

public interface Plan_interface extends dart.core.Object_interface
{
  void addConstraint(deltablue.Constraint_interface c);
  int size();
  void execute();
  dart._runtime.base.DartList<deltablue.Constraint_interface> getList();
  dart._runtime.base.DartList<deltablue.Constraint_interface> setList(dart._runtime.base.DartList<deltablue.Constraint_interface> value);
}
