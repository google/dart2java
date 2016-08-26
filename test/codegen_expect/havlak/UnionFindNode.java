package havlak;

public class UnionFindNode extends dart._runtime.base.DartObject
{
    public static dart._runtime.types.simple.InterfaceTypeInfo dart2java$typeInfo = new dart._runtime.types.simple.InterfaceTypeInfo("file:///usr/local/google/home/andrewkrieger/ddc-java/gen/codegen_tests/havlak.dart", "UnionFindNode");
    static {
      havlak.UnionFindNode.dart2java$typeInfo.superclass = new dart._runtime.types.simple.InterfaceTypeExpr(dart._runtime.helpers.ObjectHelper.dart2java$typeInfo);
    }
    public int dfsNumber;
    public havlak.UnionFindNode parent;
    public havlak.BasicBlock bb;
    public havlak.SimpleLoop loop;
  
    public UnionFindNode(dart._runtime.types.simple.Type type)
    {
      super((dart._runtime.helpers.ConstructorHelper.EmptyConstructorMarker) null, type);
      this._constructor();
    }
    public UnionFindNode(dart._runtime.helpers.ConstructorHelper.EmptyConstructorMarker arg, dart._runtime.types.simple.Type type)
    {
      super(arg, type);
    }
  
    protected void _constructor()
    {
      final dart._runtime.types.simple.TypeEnvironment dart2java$localTypeEnv = this.dart2java$type.env;
      this.dfsNumber = 0;
      super._constructor();
    }
    public void initNode(havlak.BasicBlock bb, int dfsNumber)
    {
      final dart._runtime.types.simple.TypeEnvironment dart2java$localTypeEnv = this.dart2java$type.env;
      this.setParent(this);
      this.setBb(bb);
      this.setDfsNumber(dfsNumber);
    }
    public havlak.UnionFindNode findSet()
    {
      final dart._runtime.types.simple.TypeEnvironment dart2java$localTypeEnv = this.dart2java$type.env;
      dart._runtime.base.DartList<havlak.UnionFindNode> nodeList = (dart._runtime.base.DartList) dart._runtime.base.DartList.Generic._fromArguments(havlak.UnionFindNode.class);
      havlak.UnionFindNode node = this;
      while ((!dart._runtime.helpers.ObjectHelper.operatorEqual(node, node.getParent())))
      {
        if ((!dart._runtime.helpers.ObjectHelper.operatorEqual(node.getParent(), node.getParent().getParent())))
        {
          nodeList.add(node);
        }
        node = node.getParent();
      }
      for (int iter = 0; (iter < nodeList.getLength()); iter = (iter + 1))
      {
        nodeList.operatorAt(iter).setParent(node.getParent());
      }
      return node;
    }
    public void union(havlak.UnionFindNode unionFindNode)
    {
      final dart._runtime.types.simple.TypeEnvironment dart2java$localTypeEnv = this.dart2java$type.env;
      this.setParent(unionFindNode);
    }
    public havlak.SimpleLoop setLoop_(havlak.SimpleLoop l)
    {
      final dart._runtime.types.simple.TypeEnvironment dart2java$localTypeEnv = this.dart2java$type.env;
      return this.setLoop(l);
    }
    public int getDfsNumber()
    {
      return this.dfsNumber;
    }
    public havlak.UnionFindNode getParent()
    {
      return this.parent;
    }
    public havlak.BasicBlock getBb()
    {
      return this.bb;
    }
    public havlak.SimpleLoop getLoop()
    {
      return this.loop;
    }
    public int setDfsNumber(int value)
    {
      this.dfsNumber = value;
      return value;
    }
    public havlak.UnionFindNode setParent(havlak.UnionFindNode value)
    {
      this.parent = value;
      return value;
    }
    public havlak.BasicBlock setBb(havlak.BasicBlock value)
    {
      this.bb = value;
      return value;
    }
    public havlak.SimpleLoop setLoop(havlak.SimpleLoop value)
    {
      this.loop = value;
      return value;
    }
}