package deltablue;

public class Variable extends dart._runtime.base.DartObject
{
    public static dart._runtime.types.simple.InterfaceTypeInfo dart2java$typeInfo = new dart._runtime.types.simple.InterfaceTypeInfo("file:///usr/local/google/home/andrewkrieger/ddc-java/gen/codegen_tests/deltablue.dart", "Variable");
    static {
      deltablue.Variable.dart2java$typeInfo.superclass = new dart._runtime.types.simple.InterfaceTypeExpr(dart._runtime.helpers.ObjectHelper.dart2java$typeInfo);
    }
    public dart._runtime.base.DartList<deltablue.Constraint> constraints;
    public deltablue.Constraint determinedBy;
    public int mark;
    public deltablue.Strength walkStrength;
    public java.lang.Boolean stay;
    public int value;
    public java.lang.String name;
  
    public Variable(dart._runtime.types.simple.Type type, java.lang.String name, int value)
    {
      super((dart._runtime.helpers.ConstructorHelper.EmptyConstructorMarker) null, type);
      this._constructor(name, value);
    }
    public Variable(dart._runtime.helpers.ConstructorHelper.EmptyConstructorMarker arg, dart._runtime.types.simple.Type type)
    {
      super(arg, type);
    }
  
    protected void _constructor(java.lang.String name, int value)
    {
      final dart._runtime.types.simple.TypeEnvironment dart2java$localTypeEnv = this.dart2java$type.env;
      this.constraints = (dart._runtime.base.DartList) dart._runtime.base.DartList.Generic._fromArguments(deltablue.Constraint.class);
      this.mark = 0;
      this.walkStrength = deltablue.__TopLevel.WEAKEST;
      this.stay = true;
      this.value = 0;
      this.name = name;
      this.value = value;
      super._constructor();
    }
    public void addConstraint(deltablue.Constraint c)
    {
      final dart._runtime.types.simple.TypeEnvironment dart2java$localTypeEnv = this.dart2java$type.env;
      this.getConstraints().add(c);
    }
    public void removeConstraint(deltablue.Constraint c)
    {
      final dart._runtime.types.simple.TypeEnvironment dart2java$localTypeEnv = this.dart2java$type.env;
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
    public int getMark()
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
    public int getValue()
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
    public int setMark(int value)
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
    public int setValue(int value)
    {
      this.value = value;
      return value;
    }
}
