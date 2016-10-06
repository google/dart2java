package havlak;

public class BasicBlock extends dart._runtime.base.DartObject implements havlak.BasicBlock_interface
{
    public static dart._runtime.types.simple.InterfaceTypeInfo dart2java$typeInfo = new dart._runtime.types.simple.InterfaceTypeInfo(havlak.BasicBlock.class, havlak.BasicBlock_interface.class);
    private static dart._runtime.types.simple.InterfaceTypeExpr dart2java$typeExpr_List$ltBasicBlock$gt = new dart._runtime.types.simple.InterfaceTypeExpr(dart.core.List.dart2java$typeInfo, new dart._runtime.types.simple.TypeExpr[] {new dart._runtime.types.simple.InterfaceTypeExpr(havlak.BasicBlock.dart2java$typeInfo)});
    private static dart._runtime.types.simple.InterfaceTypeExpr dart2java$typeExpr_Object = new dart._runtime.types.simple.InterfaceTypeExpr(dart._runtime.helpers.ObjectHelper.dart2java$typeInfo);
    static {
      havlak.BasicBlock.dart2java$typeInfo.superclass = dart2java$typeExpr_Object;
    }
    public int name;
    public dart.core.List_interface<havlak.BasicBlock_interface> inEdges;
    public dart.core.List_interface<havlak.BasicBlock_interface> outEdges;
  
    public BasicBlock(dart._runtime.helpers.ConstructorHelper.EmptyConstructorMarker arg, dart._runtime.types.simple.Type type)
    {
      super(arg, type);
    }
  
    public java.lang.String toString()
    {
      final dart._runtime.types.simple.TypeEnvironment dart2java$localTypeEnv = this.dart2java$type.env;
      return (("BB" + this.getName()) + "");
    }
    public int getNumPred()
    {
      final dart._runtime.types.simple.TypeEnvironment dart2java$localTypeEnv = this.dart2java$type.env;
      return this.getInEdges().getLength_List();
    }
    public int getNumSucc()
    {
      final dart._runtime.types.simple.TypeEnvironment dart2java$localTypeEnv = this.dart2java$type.env;
      return this.getOutEdges().getLength_List();
    }
    public boolean addInEdge(havlak.BasicBlock_interface bb)
    {
      final dart._runtime.types.simple.TypeEnvironment dart2java$localTypeEnv = this.dart2java$type.env;
      return this.getInEdges().add_List(bb);
    }
    public boolean addOutEdge(havlak.BasicBlock_interface bb)
    {
      final dart._runtime.types.simple.TypeEnvironment dart2java$localTypeEnv = this.dart2java$type.env;
      return this.getOutEdges().add_List(bb);
    }
    public void _constructor(int name)
    {
      final dart._runtime.types.simple.TypeEnvironment dart2java$localTypeEnv = this.dart2java$type.env;
      this.inEdges = ((dart.core.List_interface) dart._runtime.base.DartList.<havlak.BasicBlock_interface>specialfactory$fromArguments(dart2java$localTypeEnv.evaluate(dart2java$typeExpr_List$ltBasicBlock$gt)));
      this.outEdges = ((dart.core.List_interface) dart._runtime.base.DartList.<havlak.BasicBlock_interface>specialfactory$fromArguments(dart2java$localTypeEnv.evaluate(dart2java$typeExpr_List$ltBasicBlock$gt)));
      this.name = name;
      super._constructor();
      havlak.__TopLevel.numBasicBlocks = (havlak.__TopLevel.numBasicBlocks + 1);
    }
    public int getName()
    {
      return this.name;
    }
    public dart.core.List_interface<havlak.BasicBlock_interface> getInEdges()
    {
      return this.inEdges;
    }
    public dart.core.List_interface<havlak.BasicBlock_interface> getOutEdges()
    {
      return this.outEdges;
    }
    public dart.core.List_interface<havlak.BasicBlock_interface> setInEdges(dart.core.List_interface<havlak.BasicBlock_interface> value)
    {
      this.inEdges = value;
      return value;
    }
    public dart.core.List_interface<havlak.BasicBlock_interface> setOutEdges(dart.core.List_interface<havlak.BasicBlock_interface> value)
    {
      this.outEdges = value;
      return value;
    }
    public static havlak.BasicBlock_interface _new_BasicBlock$(dart._runtime.types.simple.Type type, int name)
    {
      havlak.BasicBlock result;
      result = new havlak.BasicBlock(((dart._runtime.helpers.ConstructorHelper.EmptyConstructorMarker) null), type);
      result._constructor(name);
      return result;
    }
}
