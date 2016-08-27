package deltablue;

public interface Planner_interface extends dart.core.Object_interface
{
  void incrementalAdd(deltablue.Constraint c);
  void incrementalRemove(deltablue.Constraint c);
  int newMark();
  deltablue.Plan makePlan(dart._runtime.base.DartList<deltablue.Constraint> sources);
  deltablue.Plan extractPlanFromConstraints(dart._runtime.base.DartList<deltablue.Constraint> constraints);
  java.lang.Boolean addPropagate(deltablue.Constraint c, int mark);
  dart._runtime.base.DartList<deltablue.Constraint> removePropagateFrom(deltablue.Variable out);
  void addConstraintsConsumingTo(deltablue.Variable v, dart._runtime.base.DartList<deltablue.Constraint> coll);
}
