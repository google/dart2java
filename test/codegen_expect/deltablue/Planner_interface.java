package deltablue;

public interface Planner_interface extends dart._runtime.base.DartObject_interface
{
  void incrementalAdd(deltablue.Constraint_interface c);
  void incrementalRemove(deltablue.Constraint_interface c);
  int newMark();
  deltablue.Plan_interface makePlan(dart.core.List_interface<deltablue.Constraint_interface> sources);
  deltablue.Plan_interface extractPlanFromConstraints(dart.core.List_interface<deltablue.Constraint_interface> constraints);
  boolean addPropagate(deltablue.Constraint_interface c, int mark);
  dart.core.List_interface<deltablue.Constraint_interface> removePropagateFrom(deltablue.Variable_interface out);
  void addConstraintsConsumingTo(deltablue.Variable_interface v, dart.core.List_interface<deltablue.Constraint_interface> coll);
  int getCurrentMark();
  int setCurrentMark(int value);

}
