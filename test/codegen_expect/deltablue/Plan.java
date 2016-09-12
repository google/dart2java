package deltablue;

public class Plan extends dart._runtime.base.DartObject implements deltablue.Plan_interface
{
    public static dart._runtime.types.simple.InterfaceTypeInfo dart2java$typeInfo = new dart._runtime.types.simple.InterfaceTypeInfo(deltablue.Plan.class, deltablue.Plan_interface.class);
    private static dart._runtime.types.simple.InterfaceTypeExpr dart2java$typeExpr_Object = new dart._runtime.types.simple.InterfaceTypeExpr(dart._runtime.helpers.ObjectHelper.dart2java$typeInfo);
    static {
      deltablue.Plan.dart2java$typeInfo.superclass = dart2java$typeExpr_Object;
    }
    public dart.core.List_interface<deltablue.Constraint_interface> list;
  
    public Plan(dart._runtime.types.simple.Type type)
    {
      super(((dart._runtime.helpers.ConstructorHelper.EmptyConstructorMarker) null), type);
      this._constructor();
    }
    public Plan(dart._runtime.helpers.ConstructorHelper.EmptyConstructorMarker arg, dart._runtime.types.simple.Type type)
    {
      super(arg, type);
    }
  
    protected void _constructor()
    {
      final dart._runtime.types.simple.TypeEnvironment dart2java$localTypeEnv = this.dart2java$type.env;
      this.list = ((dart.core.List_interface) dart._runtime.base.DartList.Generic._fromArguments(deltablue.Constraint_interface.class));
      super._constructor();
    }
    public void addConstraint(deltablue.Constraint_interface c)
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
    public dart.core.List_interface<deltablue.Constraint_interface> getList()
    {
      return this.list;
    }
    public dart.core.List_interface<deltablue.Constraint_interface> setList(dart.core.List_interface<deltablue.Constraint_interface> value)
    {
      this.list = value;
      return value;
    }
}
