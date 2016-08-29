package deltablue;

public interface Variable_interface extends dart.core.Object_interface
{
  void addConstraint(deltablue.Constraint_interface c);
  void removeConstraint(deltablue.Constraint_interface c);
  dart._runtime.base.DartList<deltablue.Constraint_interface> getConstraints();
  deltablue.Constraint_interface getDeterminedBy();
  int getMark();
  deltablue.Strength_interface getWalkStrength();
  java.lang.Boolean getStay();
  int getValue();
  java.lang.String getName();
  dart._runtime.base.DartList<deltablue.Constraint_interface> setConstraints(dart._runtime.base.DartList<deltablue.Constraint_interface> value);
  deltablue.Constraint_interface setDeterminedBy(deltablue.Constraint_interface value);
  int setMark(int value);
  deltablue.Strength_interface setWalkStrength(deltablue.Strength_interface value);
  java.lang.Boolean setStay(java.lang.Boolean value);
  int setValue(int value);
}
