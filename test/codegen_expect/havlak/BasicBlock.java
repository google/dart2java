package havlak;

public class BasicBlock extends dart._runtime.base.DartObject
{
    public static dart._runtime.types.simple.InterfaceTypeInfo dart2java$typeInfo = new dart._runtime.types.simple.InterfaceTypeInfo("file:///usr/local/google/home/stanm/f/d/ddc-java/gen/codegen_tests/havlak.dart", "BasicBlock");
    static {
      havlak.BasicBlock.dart2java$typeInfo.superclass = new dart._runtime.types.simple.InterfaceTypeExpr(dart._runtime.helpers.ObjectHelper.dart2java$typeInfo);
    }
    public int name;
    public dart._runtime.base.DartList<havlak.BasicBlock> inEdges;
    public dart._runtime.base.DartList<havlak.BasicBlock> outEdges;
  
    public BasicBlock(int name)
    {
      super((dart._runtime.helpers.ConstructorHelper.EmptyConstructorMarker) null);
      this._constructor(name);
    }
    public BasicBlock(dart._runtime.helpers.ConstructorHelper.EmptyConstructorMarker arg)
    {
      super(arg);
    }
  
    protected void _constructor(int name)
    {
      this.inEdges = (dart._runtime.base.DartList) dart._runtime.base.DartList.Generic._fromArguments(havlak.BasicBlock.class);
      this.outEdges = (dart._runtime.base.DartList) dart._runtime.base.DartList.Generic._fromArguments(havlak.BasicBlock.class);
      this.name = name;
      super._constructor();
      havlak.__TopLevel.numBasicBlocks = (havlak.__TopLevel.numBasicBlocks + 1);
    }
    public java.lang.String toString()
    {
      return (("BB" + this.getName()) + "");
    }
    public int getNumPred()
    {
      return this.getInEdges().getLength();
    }
    public int getNumSucc()
    {
      return this.getOutEdges().getLength();
    }
    public void addInEdge(havlak.BasicBlock bb)
    {
      this.getInEdges().add(bb);
      return;
    }
    public void addOutEdge(havlak.BasicBlock bb)
    {
      this.getOutEdges().add(bb);
      return;
    }
    public int getName()
    {
      return this.name;
    }
    public dart._runtime.base.DartList<havlak.BasicBlock> getInEdges()
    {
      return this.inEdges;
    }
    public dart._runtime.base.DartList<havlak.BasicBlock> getOutEdges()
    {
      return this.outEdges;
    }
    public dart._runtime.base.DartList<havlak.BasicBlock> setInEdges(dart._runtime.base.DartList<havlak.BasicBlock> value)
    {
      this.inEdges = value;
      return value;
    }
    public dart._runtime.base.DartList<havlak.BasicBlock> setOutEdges(dart._runtime.base.DartList<havlak.BasicBlock> value)
    {
      this.outEdges = value;
      return value;
    }
}
