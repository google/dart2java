package havlak;

public class CFG extends dart._runtime.base.DartObject implements havlak.CFG_interface
{
    public static dart._runtime.types.simple.InterfaceTypeInfo dart2java$typeInfo = new dart._runtime.types.simple.InterfaceTypeInfo("havlak.CFG");
    static {
      havlak.CFG.dart2java$typeInfo.superclass = new dart._runtime.types.simple.InterfaceTypeExpr(dart._runtime.helpers.ObjectHelper.dart2java$typeInfo);
    }
    public dart._runtime.base.DartMap<java.lang.Integer, havlak.BasicBlock_interface> basicBlockMap;
    public dart._runtime.base.DartList<havlak.BasicBlockEdge_interface> edgeList;
    public havlak.BasicBlock_interface startNode;
  
    public CFG(dart._runtime.types.simple.Type type)
    {
      super((dart._runtime.helpers.ConstructorHelper.EmptyConstructorMarker) null, type);
      this._constructor();
    }
    public CFG(dart._runtime.helpers.ConstructorHelper.EmptyConstructorMarker arg, dart._runtime.types.simple.Type type)
    {
      super(arg, type);
    }
  
    protected void _constructor()
    {
      final dart._runtime.types.simple.TypeEnvironment dart2java$localTypeEnv = this.dart2java$type.env;
      this.basicBlockMap = (dart._runtime.base.DartMap) dart._runtime.base.DartMap.Generic.newInstance(int.class, havlak.BasicBlock_interface.class);
      this.edgeList = (dart._runtime.base.DartList) dart._runtime.base.DartList.Generic._fromArguments(havlak.BasicBlockEdge_interface.class);
      super._constructor();
    }
    public havlak.BasicBlock_interface createNode(int name)
    {
      final dart._runtime.types.simple.TypeEnvironment dart2java$localTypeEnv = this.dart2java$type.env;
      havlak.BasicBlock_interface node = this.getBasicBlockMap().operatorAt(name);
      if (dart._runtime.helpers.ObjectHelper.operatorEqual(node, null))
      {
        node = new havlak.BasicBlock(dart2java$localTypeEnv.evaluate(new dart._runtime.types.simple.InterfaceTypeExpr(havlak.BasicBlock.dart2java$typeInfo)), name);
        this.getBasicBlockMap().operatorAtPut(name, node);
      }
      if ((this.getNumNodes() == 1))
      {
        this.setStartNode(node);
      }
      return node;
    }
    public void addEdge(havlak.BasicBlockEdge_interface edge)
    {
      final dart._runtime.types.simple.TypeEnvironment dart2java$localTypeEnv = this.dart2java$type.env;
      this.getEdgeList().add(edge);
      return;
    }
    public int getNumNodes()
    {
      final dart._runtime.types.simple.TypeEnvironment dart2java$localTypeEnv = this.dart2java$type.env;
      return this.getBasicBlockMap().getLength();
    }
    public havlak.BasicBlock_interface getDst(havlak.BasicBlockEdge_interface edge)
    {
      final dart._runtime.types.simple.TypeEnvironment dart2java$localTypeEnv = this.dart2java$type.env;
      return edge.getTo();
    }
    public havlak.BasicBlock_interface getSrc(havlak.BasicBlockEdge_interface edge)
    {
      final dart._runtime.types.simple.TypeEnvironment dart2java$localTypeEnv = this.dart2java$type.env;
      return edge.getFrom();
    }
    public dart._runtime.base.DartMap<java.lang.Integer, havlak.BasicBlock_interface> getBasicBlockMap()
    {
      return this.basicBlockMap;
    }
    public dart._runtime.base.DartList<havlak.BasicBlockEdge_interface> getEdgeList()
    {
      return this.edgeList;
    }
    public havlak.BasicBlock_interface getStartNode()
    {
      return this.startNode;
    }
    public havlak.BasicBlock_interface setStartNode(havlak.BasicBlock_interface value)
    {
      this.startNode = value;
      return value;
    }
}
