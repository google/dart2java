package havlak;

public class CFG extends dart._runtime.base.DartObject implements havlak.CFG_interface
{
    public static dart._runtime.types.simple.InterfaceTypeInfo dart2java$typeInfo = new dart._runtime.types.simple.InterfaceTypeInfo(havlak.CFG.class, havlak.CFG_interface.class);
    private static dart._runtime.types.simple.InterfaceTypeExpr dart2java$typeExpr_BasicBlock = new dart._runtime.types.simple.InterfaceTypeExpr(havlak.BasicBlock.dart2java$typeInfo);
    private static dart._runtime.types.simple.InterfaceTypeExpr dart2java$typeExpr_Object = new dart._runtime.types.simple.InterfaceTypeExpr(dart._runtime.helpers.ObjectHelper.dart2java$typeInfo);
    static {
      havlak.CFG.dart2java$typeInfo.superclass = dart2java$typeExpr_Object;
    }
    public dart.core.Map_interface<java.lang.Integer, havlak.BasicBlock_interface> basicBlockMap;
    public dart.core.List_interface<havlak.BasicBlockEdge_interface> edgeList;
    public havlak.BasicBlock_interface startNode;
  
    public CFG(dart._runtime.helpers.ConstructorHelper.EmptyConstructorMarker arg, dart._runtime.types.simple.Type type)
    {
      super(arg, type);
    }
  
    public havlak.BasicBlock_interface createNode(int name)
    {
      final dart._runtime.types.simple.TypeEnvironment dart2java$localTypeEnv = this.dart2java$type.env;
      havlak.BasicBlock_interface node = this.getBasicBlockMap().operatorAt(name);
      if (dart._runtime.helpers.ObjectHelper.operatorEqual(node, null))
      {
        node = ((havlak.BasicBlock_interface) havlak.BasicBlock._new(dart2java$localTypeEnv.evaluate(dart2java$typeExpr_BasicBlock), name));
        this.getBasicBlockMap().operatorAtPut(name, node);
      }
      if ((this.getNumNodes() == 1))
      {
        this.setStartNode(node);
      }
      return node;
    }
    public boolean addEdge(havlak.BasicBlockEdge_interface edge)
    {
      final dart._runtime.types.simple.TypeEnvironment dart2java$localTypeEnv = this.dart2java$type.env;
      return this.getEdgeList().add(edge);
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
    public void _constructor()
    {
      final dart._runtime.types.simple.TypeEnvironment dart2java$localTypeEnv = this.dart2java$type.env;
      this.basicBlockMap = ((dart.core.Map_interface) dart.core.Map.factory$(java.lang.Integer.class, havlak.BasicBlock_interface.class));
      this.edgeList = ((dart.core.List_interface) dart._runtime.base.DartList.Generic._fromArguments(havlak.BasicBlockEdge_interface.class));
      super._constructor();
    }
    public dart.core.Map_interface<java.lang.Integer, havlak.BasicBlock_interface> getBasicBlockMap()
    {
      return this.basicBlockMap;
    }
    public dart.core.List_interface<havlak.BasicBlockEdge_interface> getEdgeList()
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
    public static havlak.CFG_interface _new(dart._runtime.types.simple.Type type)
    {
      havlak.CFG_interface result;
      result = new havlak.CFG(((dart._runtime.helpers.ConstructorHelper.EmptyConstructorMarker) null), type);
      result._constructor();
      return result;
    }
}
