package deltablue;

public class Variable extends dart._runtime.base.DartObject implements deltablue.Variable_interface
{
    public static dart._runtime.types.simple.InterfaceTypeInfo dart2java$typeInfo = new dart._runtime.types.simple.InterfaceTypeInfo(deltablue.Variable.class, deltablue.Variable_interface.class);
    private static dart._runtime.types.simple.InterfaceTypeExpr dart2java$typeExpr_Object = new dart._runtime.types.simple.InterfaceTypeExpr(dart._runtime.helpers.ObjectHelper.dart2java$typeInfo);
    static {
      deltablue.Variable.dart2java$typeInfo.superclass = dart2java$typeExpr_Object;
    }
    public dart.core.List_interface<deltablue.Constraint_interface> constraints;
    public deltablue.Constraint_interface determinedBy;
    public int mark;
    public deltablue.Strength_interface walkStrength;
    public boolean stay;
    public int value;
    public java.lang.String name;
  
    public Variable(dart._runtime.types.simple.Type type, java.lang.String name, int value)
    {
      super(((dart._runtime.helpers.ConstructorHelper.EmptyConstructorMarker) null), type);
      this._constructor(name, value);
    }
    public Variable(dart._runtime.helpers.ConstructorHelper.EmptyConstructorMarker arg, dart._runtime.types.simple.Type type)
    {
      super(arg, type);
    }
  
    protected void _constructor(java.lang.String name, int value)
    {
      final dart._runtime.types.simple.TypeEnvironment dart2java$localTypeEnv = this.dart2java$type.env;
      this.constraints = ((dart.core.List_interface) dart._runtime.base.DartList.Generic._fromArguments(deltablue.Constraint_interface.class));
      this.mark = 0;
      this.walkStrength = deltablue.__TopLevel.WEAKEST;
      this.stay = true;
      this.value = 0;
      this.name = name;
      this.value = value;
      super._constructor();
    }
    public void addConstraint(deltablue.Constraint_interface c)
    {
      final dart._runtime.types.simple.TypeEnvironment dart2java$localTypeEnv = this.dart2java$type.env;
      this.getConstraints().add(c);
    }
    public void removeConstraint(deltablue.Constraint_interface c)
    {
      final dart._runtime.types.simple.TypeEnvironment dart2java$localTypeEnv = this.dart2java$type.env;
      this.getConstraints().remove(c);
      if (dart._runtime.helpers.ObjectHelper.operatorEqual(this.getDeterminedBy(), c))
      {
        this.setDeterminedBy(null);
      }
    }
    public dart.core.List_interface<deltablue.Constraint_interface> getConstraints()
    {
      return this.constraints;
    }
    public deltablue.Constraint_interface getDeterminedBy()
    {
      return this.determinedBy;
    }
    public int getMark()
    {
      return this.mark;
    }
    public deltablue.Strength_interface getWalkStrength()
    {
      return this.walkStrength;
    }
    public boolean getStay()
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
    public dart.core.List_interface<deltablue.Constraint_interface> setConstraints(dart.core.List_interface<deltablue.Constraint_interface> value)
    {
      this.constraints = value;
      return value;
    }
    public deltablue.Constraint_interface setDeterminedBy(deltablue.Constraint_interface value)
    {
      this.determinedBy = value;
      return value;
    }
    public int setMark(int value)
    {
      this.mark = value;
      return value;
    }
    public deltablue.Strength_interface setWalkStrength(deltablue.Strength_interface value)
    {
      this.walkStrength = value;
      return value;
    }
    public boolean setStay(boolean value)
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
