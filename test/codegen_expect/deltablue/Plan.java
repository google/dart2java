package deltablue;

public class Plan extends dart._runtime.base.DartObject implements deltablue.Plan_interface
{
    public static dart._runtime.types.simple.InterfaceTypeInfo dart2java$typeInfo = new dart._runtime.types.simple.InterfaceTypeInfo("deltablue.Plan");
    static {
      deltablue.Plan.dart2java$typeInfo.superclass = new dart._runtime.types.simple.InterfaceTypeExpr(dart._runtime.helpers.ObjectHelper.dart2java$typeInfo);
    }
    public dart._runtime.base.DartList<deltablue.Constraint> list;
  
    public Plan(dart._runtime.types.simple.Type type)
    {
      super((dart._runtime.helpers.ConstructorHelper.EmptyConstructorMarker) null, type);
      this._constructor();
    }
    public Plan(dart._runtime.helpers.ConstructorHelper.EmptyConstructorMarker arg, dart._runtime.types.simple.Type type)
    {
      super(arg, type);
    }
  
    protected void _constructor()
    {
      final dart._runtime.types.simple.TypeEnvironment dart2java$localTypeEnv = this.dart2java$type.env;
      this.list = (dart._runtime.base.DartList) dart._runtime.base.DartList.Generic._fromArguments(deltablue.Constraint.class);
      super._constructor();
    }
    public void addConstraint(deltablue.Constraint c)
    {
      final dart._runtime.types.simple.TypeEnvironment dart2java$localTypeEnv = this.dart2java$type.env;
      this.getList().add(c);
    }
    public int size()
    {
      final dart._runtime.types.simple.TypeEnvironment dart2java$localTypeEnv = this.dart2java$type.env;
      return this.getList().getLength();
    }
    public void execute()
    {
      final dart._runtime.types.simple.TypeEnvironment dart2java$localTypeEnv = this.dart2java$type.env;
      for (int i = 0; (i < this.getList().getLength()); i = (i + 1))
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
