package havlak;

public class CFG extends dart._runtime.base.DartObject
{
    public static dart._runtime.types.simple.InterfaceTypeInfo dart2java$typeInfo = new dart._runtime.types.simple.InterfaceTypeInfo("file:///usr/local/google/home/springerm/ddc-java/gen/codegen_tests/havlak.dart", "CFG");
    static {
      havlak.CFG.dart2java$typeInfo.superclass = new dart._runtime.types.simple.InterfaceTypeExpr(dart._runtime.helpers.ObjectHelper.dart2java$typeInfo);
    }
    public dart._runtime.base.DartMap<java.lang.Integer, havlak.BasicBlock> basicBlockMap;
    public dart._runtime.base.DartList<havlak.BasicBlockEdge> edgeList;
    public havlak.BasicBlock startNode;
  
    public CFG()
    {
      super((dart._runtime.helpers.ConstructorHelper.EmptyConstructorMarker) null);
      this._constructor();
    }
    public CFG(dart._runtime.helpers.ConstructorHelper.EmptyConstructorMarker arg)
    {
      super(arg);
    }
  
    protected void _constructor()
    {
      this.basicBlockMap = (dart._runtime.base.DartMap) dart._runtime.base.DartMap.Generic.newInstance(int.class, havlak.BasicBlock.class);
      this.edgeList = (dart._runtime.base.DartList) dart._runtime.base.DartList.Generic._fromArguments(havlak.BasicBlockEdge.class);
      super._constructor();
    }
    public havlak.BasicBlock createNode(int name)
    {
      havlak.BasicBlock node = this.getBasicBlockMap().operatorAt(name);
      if (dart._runtime.helpers.ObjectHelper.operatorEqual(node, null))
      {
        node = new havlak.BasicBlock(name);
        this.getBasicBlockMap().operatorAtPut(name, node);
      }
      if (dart._runtime.helpers.ObjectHelper.operatorEqual(this.getNumNodes(), 1))
      {
        this.setStartNode(node);
      }
      return node;
    }
    public void addEdge(havlak.BasicBlockEdge edge)
    {
      this.getEdgeList().add(edge);
      return;
    }
    public int getNumNodes()
    {
      return this.getBasicBlockMap().getLength();
    }
    public havlak.BasicBlock getDst(havlak.BasicBlockEdge edge)
    {
      return edge.getTo();
    }
    public havlak.BasicBlock getSrc(havlak.BasicBlockEdge edge)
    {
      return edge.getFrom();
    }
    public dart._runtime.base.DartMap<java.lang.Integer, havlak.BasicBlock> getBasicBlockMap()
    {
      return this.basicBlockMap;
    }
    public dart._runtime.base.DartList<havlak.BasicBlockEdge> getEdgeList()
    {
      return this.edgeList;
    }
    public havlak.BasicBlock getStartNode()
    {
      return this.startNode;
    }
    public havlak.BasicBlock setStartNode(havlak.BasicBlock value)
    {
      this.startNode = value;
      return value;
    }
}
