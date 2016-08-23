package havlak;

public class SimpleLoop extends dart._runtime.base.DartObject
{
    public static dart._runtime.types.simple.InterfaceTypeInfo dart2java$typeInfo = new dart._runtime.types.simple.InterfaceTypeInfo("file:///usr/local/google/home/springerm/ddc-java/gen/codegen_tests/havlak.dart", "SimpleLoop");
    static {
      havlak.SimpleLoop.dart2java$typeInfo.superclass = new dart._runtime.types.simple.InterfaceTypeExpr(dart._runtime.helpers.ObjectHelper.dart2java$typeInfo);
    }
    public dart._runtime.base.DartList<havlak.BasicBlock> basicBlocks;
    public dart._runtime.base.DartList<havlak.SimpleLoop> children;
    public int counter;
    public havlak.SimpleLoop parent;
    public havlak.BasicBlock header;
    public java.lang.Boolean isRoot;
    public java.lang.Boolean isReducible;
    public int nestingLevel;
    public int depthLevel;
  
    public SimpleLoop(int counter)
    {
      super((dart._runtime.helpers.ConstructorHelper.EmptyConstructorMarker) null);
      this._constructor(counter);
    }
    public SimpleLoop(dart._runtime.helpers.ConstructorHelper.EmptyConstructorMarker arg)
    {
      super(arg);
    }
  
    protected void _constructor(int counter)
    {
      this.basicBlocks = (dart._runtime.base.DartList) dart._runtime.base.DartList.Generic._fromArguments(havlak.BasicBlock.class);
      this.children = (dart._runtime.base.DartList) dart._runtime.base.DartList.Generic._fromArguments(havlak.SimpleLoop.class);
      this.isRoot = false;
      this.isReducible = true;
      this.nestingLevel = 0;
      this.depthLevel = 0;
      this.counter = counter;
      super._constructor();
    }
    public void addNode(havlak.BasicBlock bb)
    {
      this.getBasicBlocks().add(bb);
      return;
    }
    public void addChildLoop(havlak.SimpleLoop loop)
    {
      this.getChildren().add(loop);
      return;
    }
    public void setParent_(havlak.SimpleLoop p)
    {
      this.setParent(p);
      p.addChildLoop(this);
    }
    public void setHeader_(havlak.BasicBlock bb)
    {
      this.getBasicBlocks().add(bb);
      this.setHeader(bb);
    }
    public void setNestingLevel_(int level)
    {
      this.setNestingLevel(level);
      if (dart._runtime.helpers.ObjectHelper.operatorEqual(level, 0))
      {
        this.setIsRoot(true);
      }
    }
    public int checksum()
    {
      int result = this.getCounter();
      result = havlak.__TopLevel.mix(result, (this.getIsRoot()) ? (1) : (0));
      result = havlak.__TopLevel.mix(result, (this.getIsReducible()) ? (1) : (0));
      result = havlak.__TopLevel.mix(result, this.getNestingLevel());
      result = havlak.__TopLevel.mix(result, this.getDepthLevel());
      if ((!dart._runtime.helpers.ObjectHelper.operatorEqual(this.getHeader(), null)))
      {
        result = havlak.__TopLevel.mix(result, this.getHeader().getName());
      }
      for (havlak.BasicBlock e : this.getBasicBlocks())
      {
        result = havlak.__TopLevel.mix(result, e.getName());
      }
      for (havlak.SimpleLoop e : this.getChildren())
      {
        result = havlak.__TopLevel.mix(result, e.checksum());
      }
      return result;
    }
    public dart._runtime.base.DartList<havlak.BasicBlock> getBasicBlocks()
    {
      return this.basicBlocks;
    }
    public dart._runtime.base.DartList<havlak.SimpleLoop> getChildren()
    {
      return this.children;
    }
    public int getCounter()
    {
      return this.counter;
    }
    public havlak.SimpleLoop getParent()
    {
      return this.parent;
    }
    public havlak.BasicBlock getHeader()
    {
      return this.header;
    }
    public java.lang.Boolean getIsRoot()
    {
      return this.isRoot;
    }
    public java.lang.Boolean getIsReducible()
    {
      return this.isReducible;
    }
    public int getNestingLevel()
    {
      return this.nestingLevel;
    }
    public int getDepthLevel()
    {
      return this.depthLevel;
    }
    public havlak.SimpleLoop setParent(havlak.SimpleLoop value)
    {
      this.parent = value;
      return value;
    }
    public havlak.BasicBlock setHeader(havlak.BasicBlock value)
    {
      this.header = value;
      return value;
    }
    public java.lang.Boolean setIsRoot(java.lang.Boolean value)
    {
      this.isRoot = value;
      return value;
    }
    public java.lang.Boolean setIsReducible(java.lang.Boolean value)
    {
      this.isReducible = value;
      return value;
    }
    public int setNestingLevel(int value)
    {
      this.nestingLevel = value;
      return value;
    }
    public int setDepthLevel(int value)
    {
      this.depthLevel = value;
      return value;
    }
}
