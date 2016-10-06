package havlak;

public class UnionFindNode extends dart._runtime.base.DartObject implements havlak.UnionFindNode_interface
{
    public static dart._runtime.types.simple.InterfaceTypeInfo dart2java$typeInfo = new dart._runtime.types.simple.InterfaceTypeInfo(havlak.UnionFindNode.class, havlak.UnionFindNode_interface.class);
    private static dart._runtime.types.simple.InterfaceTypeExpr dart2java$typeExpr_List$ltUnionFindNode$gt = new dart._runtime.types.simple.InterfaceTypeExpr(dart.core.List.dart2java$typeInfo, new dart._runtime.types.simple.TypeExpr[] {new dart._runtime.types.simple.InterfaceTypeExpr(havlak.UnionFindNode.dart2java$typeInfo)});
    private static dart._runtime.types.simple.InterfaceTypeExpr dart2java$typeExpr_Object = new dart._runtime.types.simple.InterfaceTypeExpr(dart._runtime.helpers.ObjectHelper.dart2java$typeInfo);
    static {
      havlak.UnionFindNode.dart2java$typeInfo.superclass = dart2java$typeExpr_Object;
    }
    public int dfsNumber;
    public havlak.UnionFindNode_interface parent;
    public havlak.BasicBlock_interface bb;
    public havlak.SimpleLoop_interface loop;
  
    public UnionFindNode(dart._runtime.helpers.ConstructorHelper.EmptyConstructorMarker arg, dart._runtime.types.simple.Type type)
    {
      super(arg, type);
    }
  
    public void initNode(havlak.BasicBlock_interface bb, int dfsNumber)
    {
      final dart._runtime.types.simple.TypeEnvironment dart2java$localTypeEnv = this.dart2java$type.env;
      this.setParent(this);
      this.setBb(bb);
      this.setDfsNumber(dfsNumber);
    }
    public havlak.UnionFindNode_interface findSet()
    {
      final dart._runtime.types.simple.TypeEnvironment dart2java$localTypeEnv = this.dart2java$type.env;
      dart.core.List_interface<havlak.UnionFindNode_interface> nodeList = ((dart.core.List_interface) dart._runtime.base.DartList.<havlak.UnionFindNode_interface>specialfactory$fromArguments(dart2java$localTypeEnv.evaluate(dart2java$typeExpr_List$ltUnionFindNode$gt)));
      havlak.UnionFindNode_interface node = this;
      while ((!dart._runtime.helpers.ObjectHelper.operatorEqual(node, node.getParent())))
      {
        if ((!dart._runtime.helpers.ObjectHelper.operatorEqual(node.getParent(), node.getParent().getParent())))
        {
          nodeList.add_List(node);
        }
        node = node.getParent();
      }
      for (int iter = 0; (iter < nodeList.getLength_List()); iter = (iter + 1))
      {
        nodeList.operatorAt_List(iter).setParent(node.getParent());
      }
      return node;
    }
    public void union(havlak.UnionFindNode_interface unionFindNode)
    {
      final dart._runtime.types.simple.TypeEnvironment dart2java$localTypeEnv = this.dart2java$type.env;
      this.setParent(unionFindNode);
    }
    public havlak.SimpleLoop_interface setLoop_(havlak.SimpleLoop_interface l)
    {
      final dart._runtime.types.simple.TypeEnvironment dart2java$localTypeEnv = this.dart2java$type.env;
      return this.setLoop(l);
    }
    public void _constructor()
    {
      final dart._runtime.types.simple.TypeEnvironment dart2java$localTypeEnv = this.dart2java$type.env;
      this.dfsNumber = 0;
      super._constructor();
    }
    public int getDfsNumber()
    {
      return this.dfsNumber;
    }
    public havlak.UnionFindNode_interface getParent()
    {
      return this.parent;
    }
    public havlak.BasicBlock_interface getBb()
    {
      return this.bb;
    }
    public havlak.SimpleLoop_interface getLoop()
    {
      return this.loop;
    }
    public int setDfsNumber(int value)
    {
      this.dfsNumber = value;
      return value;
    }
    public havlak.UnionFindNode_interface setParent(havlak.UnionFindNode_interface value)
    {
      this.parent = value;
      return value;
    }
    public havlak.BasicBlock_interface setBb(havlak.BasicBlock_interface value)
    {
      this.bb = value;
      return value;
    }
    public havlak.SimpleLoop_interface setLoop(havlak.SimpleLoop_interface value)
    {
      this.loop = value;
      return value;
    }
    public static havlak.UnionFindNode_interface _new_UnionFindNode$(dart._runtime.types.simple.Type type)
    {
      havlak.UnionFindNode result;
      result = new havlak.UnionFindNode(((dart._runtime.helpers.ConstructorHelper.EmptyConstructorMarker) null), type);
      result._constructor();
      return result;
    }
}
