package deltablue;

public class Variable extends dart._runtime.base.DartObject
{
    public dart._runtime.base.DartList<deltablue.Constraint> constraints = null;
    public deltablue.Constraint determinedBy = null;
    public java.lang.Integer mark = null;
    public deltablue.Strength walkStrength = null;
    public java.lang.Boolean stay = null;
    public java.lang.Integer value = null;
    public java.lang.String name = null;
  
    public Variable(java.lang.String name, java.lang.Integer value)
    {
      super((dart._runtime.helpers.ConstructorHelper.EmptyConstructorMarker) null);
      this._constructor(name, value);
    }
    public Variable(dart._runtime.helpers.ConstructorHelper.EmptyConstructorMarker arg)
    {
      super(arg);
    }
  
    protected void _constructor(java.lang.String name, java.lang.Integer value)
    {
      this.constraints = (dart._runtime.base.DartList) dart._runtime.base.DartList._fromArguments(deltablue.Constraint.class);
      this.mark = 0;
      this.walkStrength = deltablue.__TopLevel.WEAKEST;
      this.stay = true;
      this.name = name;
      this.value = value;
      super._constructor();
    }
    public void addConstraint(deltablue.Constraint c)
    {
      this.getConstraints().add(c);
    }
    public void removeConstraint(deltablue.Constraint c)
    {
      this.getConstraints().remove(c);
      if (dart._runtime.helpers.ObjectHelper.operatorEqual(this.getDeterminedBy(), c))
      {
        this.setDeterminedBy(null);
      }
    }
    public dart._runtime.base.DartList<deltablue.Constraint> getConstraints()
    {
      return this.constraints;
    }
    public deltablue.Constraint getDeterminedBy()
    {
      return this.determinedBy;
    }
    public java.lang.Integer getMark()
    {
      return this.mark;
    }
    public deltablue.Strength getWalkStrength()
    {
      return this.walkStrength;
    }
    public java.lang.Boolean getStay()
    {
      return this.stay;
    }
    public java.lang.Integer getValue()
    {
      return this.value;
    }
    public java.lang.String getName()
    {
      return this.name;
    }
    public dart._runtime.base.DartList<deltablue.Constraint> setConstraints(dart._runtime.base.DartList<deltablue.Constraint> value)
    {
      this.constraints = value;
      return value;
    }
    public deltablue.Constraint setDeterminedBy(deltablue.Constraint value)
    {
      this.determinedBy = value;
      return value;
    }
    public java.lang.Integer setMark(java.lang.Integer value)
    {
      this.mark = value;
      return value;
    }
    public deltablue.Strength setWalkStrength(deltablue.Strength value)
    {
      this.walkStrength = value;
      return value;
    }
    public java.lang.Boolean setStay(java.lang.Boolean value)
    {
      this.stay = value;
      return value;
    }
    public java.lang.Integer setValue(java.lang.Integer value)
    {
      this.value = value;
      return value;
    }
}
