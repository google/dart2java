package deltablue;

public interface Planner_interface extends dart.core.Object_interface
{
  void incrementalAdd(deltablue.Constraint_interface c);
  void incrementalRemove(deltablue.Constraint_interface c);
  int newMark();
  deltablue.Plan_interface makePlan(dart._runtime.base.DartList<deltablue.Constraint_interface> sources);
  deltablue.Plan_interface extractPlanFromConstraints(dart._runtime.base.DartList<deltablue.Constraint_interface> constraints);
  boolean addPropagate(deltablue.Constraint_interface c, int mark);
  dart._runtime.base.DartList<deltablue.Constraint_interface> removePropagateFrom(deltablue.Variable_interface out);
  void addConstraintsConsumingTo(deltablue.Variable_interface v, dart._runtime.base.DartList<deltablue.Constraint_interface> coll);
  int getCurrentMark();
  int setCurrentMark(int value);
}
