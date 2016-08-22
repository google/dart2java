package deltablue;

public class Plan extends dart._runtime.base.DartObject
{
    public static dart._runtime.types.simple.InterfaceTypeInfo dart2java$typeInfo = new dart._runtime.types.simple.InterfaceTypeInfo("file:///usr/local/google/home/andrewkrieger/ddc-java/gen/codegen_tests/deltablue.dart", "Plan");
    static {
      deltablue.Plan.dart2java$typeInfo.superclass = new dart._runtime.types.simple.InterfaceTypeExpr(dart._runtime.helpers.ObjectHelper.dart2java$typeInfo);
    }
    public dart._runtime.base.DartList<deltablue.Constraint> list = null;
  
    public Plan()
    {
      super((dart._runtime.helpers.ConstructorHelper.EmptyConstructorMarker) null);
      this._constructor();
    }
    public Plan(dart._runtime.helpers.ConstructorHelper.EmptyConstructorMarker arg)
    {
      super(arg);
    }
  
    protected void _constructor()
    {
      this.list = (dart._runtime.base.DartList) dart._runtime.base.DartList.Generic._fromArguments(deltablue.Constraint.class);
      super._constructor();
    }
    public void addConstraint(deltablue.Constraint c)
    {
      this.getList().add(c);
    }
    public java.lang.Integer size()
    {
      return this.getList().getLength();
    }
    public void execute()
    {
      for (java.lang.Integer i = 0; dart._runtime.helpers.IntegerHelper.operatorLess(i, this.getList().getLength()); i = dart._runtime.helpers.IntegerHelper.operatorPlus(i, 1))
      {
        this.getList().operatorAt(i).execute();
      }
    }
    public dart._runtime.base.DartList<deltablue.Constraint> getList()
    {
      return this.list;
    }
    public dart._runtime.base.DartList<deltablue.Constraint> setList(dart._runtime.base.DartList<deltablue.Constraint> value)
    {
      this.list = value;
      return value;
    }
}
