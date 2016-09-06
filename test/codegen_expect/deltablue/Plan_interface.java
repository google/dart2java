package deltablue;

public interface Plan_interface extends dart._runtime.base.DartObject_interface
{
  void addConstraint(deltablue.Constraint_interface c);
  int size();
  void execute();
  dart.core.List_interface<deltablue.Constraint_interface> getList();
  dart.core.List_interface<deltablue.Constraint_interface> setList(dart.core.List_interface<deltablue.Constraint_interface> value);
}
