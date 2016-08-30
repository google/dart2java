package havlak;

public class SimpleLoop extends dart._runtime.base.DartObject implements havlak.SimpleLoop_interface
{
    public static dart._runtime.types.simple.InterfaceTypeInfo dart2java$typeInfo = new dart._runtime.types.simple.InterfaceTypeInfo(havlak.SimpleLoop.class, havlak.SimpleLoop_interface.class);
    static {
      havlak.SimpleLoop.dart2java$typeInfo.superclass = new dart._runtime.types.simple.InterfaceTypeExpr(dart._runtime.helpers.ObjectHelper.dart2java$typeInfo);
    }
    public dart._runtime.base.DartList<havlak.BasicBlock_interface> basicBlocks;
    public dart._runtime.base.DartList<havlak.SimpleLoop_interface> children;
    public int counter;
    public havlak.SimpleLoop_interface parent;
    public havlak.BasicBlock_interface header;
    public boolean isRoot;
    public boolean isReducible;
    public int nestingLevel;
    public int depthLevel;
  
    public SimpleLoop(dart._runtime.types.simple.Type type, int counter)
    {
      super((dart._runtime.helpers.ConstructorHelper.EmptyConstructorMarker) null, type);
      this._constructor(counter);
    }
    public SimpleLoop(dart._runtime.helpers.ConstructorHelper.EmptyConstructorMarker arg, dart._runtime.types.simple.Type type)
    {
      super(arg, type);
    }
  
    protected void _constructor(int counter)
    {
      final dart._runtime.types.simple.TypeEnvironment dart2java$localTypeEnv = this.dart2java$type.env;
      this.basicBlocks = (dart._runtime.base.DartList) dart._runtime.base.DartList.Generic._fromArguments(havlak.BasicBlock_interface.class);
      this.children = (dart._runtime.base.DartList) dart._runtime.base.DartList.Generic._fromArguments(havlak.SimpleLoop_interface.class);
      this.isRoot = false;
      this.isReducible = true;
      this.nestingLevel = 0;
      this.depthLevel = 0;
      this.counter = counter;
      super._constructor();
    }
    public void addNode(havlak.BasicBlock_interface bb)
    {
      final dart._runtime.types.simple.TypeEnvironment dart2java$localTypeEnv = this.dart2java$type.env;
      this.getBasicBlocks().add(bb);
      return;
    }
    public void addChildLoop(havlak.SimpleLoop_interface loop)
    {
      final dart._runtime.types.simple.TypeEnvironment dart2java$localTypeEnv = this.dart2java$type.env;
      this.getChildren().add(loop);
      return;
    }
    public void setParent_(havlak.SimpleLoop_interface p)
    {
      final dart._runtime.types.simple.TypeEnvironment dart2java$localTypeEnv = this.dart2java$type.env;
      this.setParent(p);
      p.addChildLoop(this);
    }
    public void setHeader_(havlak.BasicBlock_interface bb)
    {
      final dart._runtime.types.simple.TypeEnvironment dart2java$localTypeEnv = this.dart2java$type.env;
      this.getBasicBlocks().add(bb);
      this.setHeader(bb);
    }
    public void setNestingLevel_(int level)
    {
      final dart._runtime.types.simple.TypeEnvironment dart2java$localTypeEnv = this.dart2java$type.env;
      this.setNestingLevel(level);
      if ((level == 0))
      {
        this.setIsRoot(true);
      }
    }
    public int checksum()
    {
      final dart._runtime.types.simple.TypeEnvironment dart2java$localTypeEnv = this.dart2java$type.env;
      int result = this.getCounter();
      result = havlak.__TopLevel.mix(result, (this.getIsRoot()) ? (1) : (0));
      result = havlak.__TopLevel.mix(result, (this.getIsReducible()) ? (1) : (0));
      result = havlak.__TopLevel.mix(result, this.getNestingLevel());
      result = havlak.__TopLevel.mix(result, this.getDepthLevel());
      if ((!dart._runtime.helpers.ObjectHelper.operatorEqual(this.getHeader(), null)))
      {
        result = havlak.__TopLevel.mix(result, this.getHeader().getName());
      }
      for (havlak.BasicBlock_interface e : this.getBasicBlocks())
      {
        result = havlak.__TopLevel.mix(result, e.getName());
      }
      for (havlak.SimpleLoop_interface e : this.getChildren())
      {
        result = havlak.__TopLevel.mix(result, e.checksum());
      }
      return result;
    }
    public dart._runtime.base.DartList<havlak.BasicBlock_interface> getBasicBlocks()
    {
      return this.basicBlocks;
    }
    public dart._runtime.base.DartList<havlak.SimpleLoop_interface> getChildren()
    {
      return this.children;
    }
    public int getCounter()
    {
      return this.counter;
    }
    public havlak.SimpleLoop_interface getParent()
    {
      return this.parent;
    }
    public havlak.BasicBlock_interface getHeader()
    {
      return this.header;
    }
    public boolean getIsRoot()
    {
      return this.isRoot;
    }
    public boolean getIsReducible()
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
    public havlak.SimpleLoop_interface setParent(havlak.SimpleLoop_interface value)
    {
      this.parent = value;
      return value;
    }
    public havlak.BasicBlock_interface setHeader(havlak.BasicBlock_interface value)
    {
      this.header = value;
      return value;
    }
    public boolean setIsRoot(boolean value)
    {
      this.isRoot = value;
      return value;
    }
    public boolean setIsReducible(boolean value)
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
