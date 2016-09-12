package havlak;

public class BasicBlockEdge extends dart._runtime.base.DartObject implements havlak.BasicBlockEdge_interface
{
    public static dart._runtime.types.simple.InterfaceTypeInfo dart2java$typeInfo = new dart._runtime.types.simple.InterfaceTypeInfo(havlak.BasicBlockEdge.class, havlak.BasicBlockEdge_interface.class);
    private static dart._runtime.types.simple.InterfaceTypeExpr dart2java$typeExpr_Object = new dart._runtime.types.simple.InterfaceTypeExpr(dart._runtime.helpers.ObjectHelper.dart2java$typeInfo);
    static {
      havlak.BasicBlockEdge.dart2java$typeInfo.superclass = dart2java$typeExpr_Object;
    }
    public havlak.BasicBlock_interface from;
    public havlak.BasicBlock_interface to;
  
    public BasicBlockEdge(dart._runtime.types.simple.Type type, havlak.CFG_interface cfg, int fromName, int toName)
    {
      super(((dart._runtime.helpers.ConstructorHelper.EmptyConstructorMarker) null), type);
      this._constructor(cfg, fromName, toName);
    }
    public BasicBlockEdge(dart._runtime.helpers.ConstructorHelper.EmptyConstructorMarker arg, dart._runtime.types.simple.Type type)
    {
      super(arg, type);
    }
  
    protected void _constructor(havlak.CFG_interface cfg, int fromName, int toName)
    {
      final dart._runtime.types.simple.TypeEnvironment dart2java$localTypeEnv = this.dart2java$type.env;
      super._constructor();
      this.setFrom(cfg.createNode(fromName));
      this.setTo(cfg.createNode(toName));
      this.getFrom().addOutEdge(this.getTo());
      this.getTo().addInEdge(this.getFrom());
      cfg.addEdge(this);
    }
    public havlak.BasicBlock_interface getFrom()
    {
      return this.from;
    }
    public havlak.BasicBlock_interface getTo()
    {
      return this.to;
    }
    public havlak.BasicBlock_interface setFrom(havlak.BasicBlock_interface value)
    {
      this.from = value;
      return value;
    }
    public havlak.BasicBlock_interface setTo(havlak.BasicBlock_interface value)
    {
      this.to = value;
      return value;
    }
}
