package havlak;

public class BasicBlockEdge extends dart._runtime.base.DartObject
{
    public static dart._runtime.types.simple.InterfaceTypeInfo dart2java$typeInfo = new dart._runtime.types.simple.InterfaceTypeInfo("havlak.BasicBlockEdge");
    static {
      havlak.BasicBlockEdge.dart2java$typeInfo.superclass = new dart._runtime.types.simple.InterfaceTypeExpr(dart._runtime.helpers.ObjectHelper.dart2java$typeInfo);
    }
    public havlak.BasicBlock from;
    public havlak.BasicBlock to;
  
    public BasicBlockEdge(dart._runtime.types.simple.Type type, havlak.CFG cfg, int fromName, int toName)
    {
      super((dart._runtime.helpers.ConstructorHelper.EmptyConstructorMarker) null, type);
      this._constructor(cfg, fromName, toName);
    }
    public BasicBlockEdge(dart._runtime.helpers.ConstructorHelper.EmptyConstructorMarker arg, dart._runtime.types.simple.Type type)
    {
      super(arg, type);
    }
  
    protected void _constructor(havlak.CFG cfg, int fromName, int toName)
    {
      final dart._runtime.types.simple.TypeEnvironment dart2java$localTypeEnv = this.dart2java$type.env;
      super._constructor();
      this.setFrom(cfg.createNode(fromName));
      this.setTo(cfg.createNode(toName));
      this.getFrom().addOutEdge(this.getTo());
      this.getTo().addInEdge(this.getFrom());
      cfg.addEdge(this);
    }
    public havlak.BasicBlock getFrom()
    {
      return this.from;
    }
    public havlak.BasicBlock getTo()
    {
      return this.to;
    }
    public havlak.BasicBlock setFrom(havlak.BasicBlock value)
    {
      this.from = value;
      return value;
    }
    public havlak.BasicBlock setTo(havlak.BasicBlock value)
    {
      this.to = value;
      return value;
    }
}
