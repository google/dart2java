package havlak;

public class SimpleLoop extends dart._runtime.base.DartObject implements havlak.SimpleLoop_interface
{
    public static dart._runtime.types.simple.InterfaceTypeInfo dart2java$typeInfo = new dart._runtime.types.simple.InterfaceTypeInfo(havlak.SimpleLoop.class, havlak.SimpleLoop_interface.class);
    private static dart._runtime.types.simple.InterfaceTypeExpr dart2java$typeExpr_List$ltBasicBlock$gt = new dart._runtime.types.simple.InterfaceTypeExpr(dart.core.List.dart2java$typeInfo, new dart._runtime.types.simple.TypeExpr[] {new dart._runtime.types.simple.InterfaceTypeExpr(havlak.BasicBlock.dart2java$typeInfo)});
    private static dart._runtime.types.simple.InterfaceTypeExpr dart2java$typeExpr_List$ltSimpleLoop$gt = new dart._runtime.types.simple.InterfaceTypeExpr(dart.core.List.dart2java$typeInfo, new dart._runtime.types.simple.TypeExpr[] {new dart._runtime.types.simple.InterfaceTypeExpr(havlak.SimpleLoop.dart2java$typeInfo)});
    private static dart._runtime.types.simple.InterfaceTypeExpr dart2java$typeExpr_Object = new dart._runtime.types.simple.InterfaceTypeExpr(dart._runtime.helpers.ObjectHelper.dart2java$typeInfo);
    static {
      havlak.SimpleLoop.dart2java$typeInfo.superclass = dart2java$typeExpr_Object;
    }
    public dart.core.List_interface<havlak.BasicBlock_interface> basicBlocks;
    public dart.core.List_interface<havlak.SimpleLoop_interface> children;
    public int counter;
    public havlak.SimpleLoop_interface parent;
    public havlak.BasicBlock_interface header;
    public boolean isRoot;
    public boolean isReducible;
    public int nestingLevel;
    public int depthLevel;
  
    public SimpleLoop(dart._runtime.helpers.ConstructorHelper.EmptyConstructorMarker arg, dart._runtime.types.simple.Type type)
    {
      super(arg, type);
    }
  
    public boolean addNode(havlak.BasicBlock_interface bb)
    {
      final dart._runtime.types.simple.TypeEnvironment dart2java$localTypeEnv = this.dart2java$type.env;
      return this.getBasicBlocks().add_List(bb);
    }
    public boolean addChildLoop(havlak.SimpleLoop_interface loop)
    {
      final dart._runtime.types.simple.TypeEnvironment dart2java$localTypeEnv = this.dart2java$type.env;
      return this.getChildren().add_List(loop);
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
      this.getBasicBlocks().add_List(bb);
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
      result = havlak.__TopLevel.mix(result, ((this.getIsRoot()) ? (1) : (0)));
      result = havlak.__TopLevel.mix(result, ((this.getIsReducible()) ? (1) : (0)));
      result = havlak.__TopLevel.mix(result, this.getNestingLevel());
      result = havlak.__TopLevel.mix(result, this.getDepthLevel());
      if ((!dart._runtime.helpers.ObjectHelper.operatorEqual(this.getHeader(), null)))
      {
        result = havlak.__TopLevel.mix(result, this.getHeader().getName());
      }
      for (dart.core.Iterator_interface<havlak.BasicBlock_interface> __tempVar_0 = ((dart.core.Iterator_interface<havlak.BasicBlock_interface>) this.getBasicBlocks().getIterator_Iterable()); __tempVar_0.moveNext_Iterator(); )
      {
        havlak.BasicBlock_interface e = __tempVar_0.getCurrent_Iterator();
        result = havlak.__TopLevel.mix(result, e.getName());
      }
      for (dart.core.Iterator_interface<havlak.SimpleLoop_interface> __tempVar_1 = ((dart.core.Iterator_interface<havlak.SimpleLoop_interface>) this.getChildren().getIterator_Iterable()); __tempVar_1.moveNext_Iterator(); )
      {
        havlak.SimpleLoop_interface e = __tempVar_1.getCurrent_Iterator();
        result = havlak.__TopLevel.mix(result, e.checksum());
      }
      return result;
    }
    public void _constructor(int counter)
    {
      final dart._runtime.types.simple.TypeEnvironment dart2java$localTypeEnv = this.dart2java$type.env;
      this.basicBlocks = ((dart.core.List_interface) dart._runtime.base.DartList.<havlak.BasicBlock_interface>specialfactory$fromArguments(dart2java$localTypeEnv.evaluate(dart2java$typeExpr_List$ltBasicBlock$gt)));
      this.children = ((dart.core.List_interface) dart._runtime.base.DartList.<havlak.SimpleLoop_interface>specialfactory$fromArguments(dart2java$localTypeEnv.evaluate(dart2java$typeExpr_List$ltSimpleLoop$gt)));
      this.isRoot = false;
      this.isReducible = true;
      this.nestingLevel = 0;
      this.depthLevel = 0;
      this.counter = counter;
      super._constructor();
    }
    public dart.core.List_interface<havlak.BasicBlock_interface> getBasicBlocks()
    {
      return this.basicBlocks;
    }
    public dart.core.List_interface<havlak.SimpleLoop_interface> getChildren()
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
    public static havlak.SimpleLoop_interface _new_SimpleLoop$(dart._runtime.types.simple.Type type, int counter)
    {
      havlak.SimpleLoop result;
      result = new havlak.SimpleLoop(((dart._runtime.helpers.ConstructorHelper.EmptyConstructorMarker) null), type);
      result._constructor(counter);
      return result;
    }
}
