package havlak;

public class BasicBlock extends dart._runtime.base.DartObject implements havlak.BasicBlock_interface
{
    public static dart._runtime.types.simple.InterfaceTypeInfo dart2java$typeInfo = new dart._runtime.types.simple.InterfaceTypeInfo(havlak.BasicBlock.class, havlak.BasicBlock_interface.class);
    static {
      havlak.BasicBlock.dart2java$typeInfo.superclass = new dart._runtime.types.simple.InterfaceTypeExpr(dart._runtime.helpers.ObjectHelper.dart2java$typeInfo);
    }
    public int name;
    public dart._runtime.base.DartList<havlak.BasicBlock_interface> inEdges;
    public dart._runtime.base.DartList<havlak.BasicBlock_interface> outEdges;
  
    public BasicBlock(dart._runtime.types.simple.Type type, int name)
    {
      super((dart._runtime.helpers.ConstructorHelper.EmptyConstructorMarker) null, type);
      this._constructor(name);
    }
    public BasicBlock(dart._runtime.helpers.ConstructorHelper.EmptyConstructorMarker arg, dart._runtime.types.simple.Type type)
    {
      super(arg, type);
    }
  
    protected void _constructor(int name)
    {
      final dart._runtime.types.simple.TypeEnvironment dart2java$localTypeEnv = this.dart2java$type.env;
      this.inEdges = (dart._runtime.base.DartList) dart._runtime.base.DartList.Generic._fromArguments(havlak.BasicBlock_interface.class);
      this.outEdges = (dart._runtime.base.DartList) dart._runtime.base.DartList.Generic._fromArguments(havlak.BasicBlock_interface.class);
      this.name = name;
      super._constructor();
      havlak.__TopLevel.numBasicBlocks = (havlak.__TopLevel.numBasicBlocks + 1);
    }
    public java.lang.String toString()
    {
      final dart._runtime.types.simple.TypeEnvironment dart2java$localTypeEnv = this.dart2java$type.env;
      return (("BB" + this.getName()) + "");
    }
    public int getNumPred()
    {
      final dart._runtime.types.simple.TypeEnvironment dart2java$localTypeEnv = this.dart2java$type.env;
      return this.getInEdges().getLength();
    }
    public int getNumSucc()
    {
      final dart._runtime.types.simple.TypeEnvironment dart2java$localTypeEnv = this.dart2java$type.env;
      return this.getOutEdges().getLength();
    }
    public boolean addInEdge(havlak.BasicBlock_interface bb)
    {
      final dart._runtime.types.simple.TypeEnvironment dart2java$localTypeEnv = this.dart2java$type.env;
      return this.getInEdges().add(bb);
    }
    public boolean addOutEdge(havlak.BasicBlock_interface bb)
    {
      final dart._runtime.types.simple.TypeEnvironment dart2java$localTypeEnv = this.dart2java$type.env;
      return this.getOutEdges().add(bb);
    }
    public int getName()
    {
      return this.name;
    }
    public dart._runtime.base.DartList<havlak.BasicBlock_interface> getInEdges()
    {
      return this.inEdges;
    }
    public dart._runtime.base.DartList<havlak.BasicBlock_interface> getOutEdges()
    {
      return this.outEdges;
    }
    public dart._runtime.base.DartList<havlak.BasicBlock_interface> setInEdges(dart._runtime.base.DartList<havlak.BasicBlock_interface> value)
    {
      this.inEdges = value;
      return value;
    }
    public dart._runtime.base.DartList<havlak.BasicBlock_interface> setOutEdges(dart._runtime.base.DartList<havlak.BasicBlock_interface> value)
    {
      this.outEdges = value;
      return value;
    }
}
