package deltablue;

public class Plan extends dart._runtime.base.DartObject implements deltablue.Plan_interface
{
    public static dart._runtime.types.simple.InterfaceTypeInfo dart2java$typeInfo = new dart._runtime.types.simple.InterfaceTypeInfo(deltablue.Plan.class, deltablue.Plan_interface.class);
    private static dart._runtime.types.simple.InterfaceTypeExpr dart2java$typeExpr_List$ltConstraint$gt = new dart._runtime.types.simple.InterfaceTypeExpr(dart.core.List.dart2java$typeInfo, new dart._runtime.types.simple.TypeExpr[] {new dart._runtime.types.simple.InterfaceTypeExpr(deltablue.Constraint.dart2java$typeInfo)});
    private static dart._runtime.types.simple.InterfaceTypeExpr dart2java$typeExpr_Object = new dart._runtime.types.simple.InterfaceTypeExpr(dart._runtime.helpers.ObjectHelper.dart2java$typeInfo);
    static {
      deltablue.Plan.dart2java$typeInfo.superclass = dart2java$typeExpr_Object;
    }
    public dart.core.List_interface<deltablue.Constraint_interface> list;
  
    public Plan(dart._runtime.helpers.ConstructorHelper.EmptyConstructorMarker arg, dart._runtime.types.simple.Type type)
    {
      super(arg, type);
    }
  
    public void addConstraint(deltablue.Constraint_interface c)
    {
      final dart._runtime.types.simple.TypeEnvironment dart2java$localTypeEnv = this.dart2java$type.env;
      this.getList().add_List(c);
    }
    public int size()
    {
      final dart._runtime.types.simple.TypeEnvironment dart2java$localTypeEnv = this.dart2java$type.env;
      return this.getList().getLength_List();
    }
    public void execute()
    {
      final dart._runtime.types.simple.TypeEnvironment dart2java$localTypeEnv = this.dart2java$type.env;
      for (int i = 0; (i < this.getList().getLength_List()); i = (i + 1))
      {
        this.getList().operatorAt_List(i).execute();
      }
    }
    public void _constructor()
    {
      final dart._runtime.types.simple.TypeEnvironment dart2java$localTypeEnv = this.dart2java$type.env;
      this.list = ((dart.core.List_interface) ((dart.core.List_interface<deltablue.Constraint_interface>) dart._runtime.base.DartList.factory$fromArguments(dart2java$localTypeEnv.evaluate(dart2java$typeExpr_List$ltConstraint$gt), deltablue.Constraint_interface.class)));
      super._constructor();
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
    public static deltablue.Plan_interface _new(dart._runtime.types.simple.Type type)
    {
      deltablue.Plan result;
      result = new deltablue.Plan(((dart._runtime.helpers.ConstructorHelper.EmptyConstructorMarker) null), type);
      result._constructor();
      return result;
    }
}
