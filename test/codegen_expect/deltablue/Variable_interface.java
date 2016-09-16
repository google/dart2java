package deltablue;

public interface Variable_interface extends dart._runtime.base.DartObject_interface
{
  void addConstraint(deltablue.Constraint_interface c);
  void removeConstraint(deltablue.Constraint_interface c);
  dart.core.List_interface<deltablue.Constraint_interface> getConstraints();
  deltablue.Constraint_interface getDeterminedBy();
  int getMark();
  deltablue.Strength_interface getWalkStrength();
  boolean getStay();
  int getValue();
  java.lang.String getName();
  dart.core.List_interface<deltablue.Constraint_interface> setConstraints(dart.core.List_interface<deltablue.Constraint_interface> value);
  deltablue.Constraint_interface setDeterminedBy(deltablue.Constraint_interface value);
  int setMark(int value);
  deltablue.Strength_interface setWalkStrength(deltablue.Strength_interface value);
  boolean setStay(boolean value);
  int setValue(int value);
  void _constructor(java.lang.String name, int value);
}
