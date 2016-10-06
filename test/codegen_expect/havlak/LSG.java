package havlak;

public class LSG extends dart._runtime.base.DartObject implements havlak.LSG_interface
{
    public static dart._runtime.types.simple.InterfaceTypeInfo dart2java$typeInfo = new dart._runtime.types.simple.InterfaceTypeInfo(havlak.LSG.class, havlak.LSG_interface.class);
    private static dart._runtime.types.simple.InterfaceTypeExpr dart2java$typeExpr_SimpleLoop = new dart._runtime.types.simple.InterfaceTypeExpr(havlak.SimpleLoop.dart2java$typeInfo);
    private static dart._runtime.types.simple.InterfaceTypeExpr dart2java$typeExpr_List$ltSimpleLoop$gt = new dart._runtime.types.simple.InterfaceTypeExpr(dart.core.List.dart2java$typeInfo, new dart._runtime.types.simple.TypeExpr[] {new dart._runtime.types.simple.InterfaceTypeExpr(havlak.SimpleLoop.dart2java$typeInfo)});
    private static dart._runtime.types.simple.InterfaceTypeExpr dart2java$typeExpr_Object = new dart._runtime.types.simple.InterfaceTypeExpr(dart._runtime.helpers.ObjectHelper.dart2java$typeInfo);
    static {
      havlak.LSG.dart2java$typeInfo.superclass = dart2java$typeExpr_Object;
    }
    public int loopCounter;
    public dart.core.List_interface<havlak.SimpleLoop_interface> loops;
    public havlak.SimpleLoop_interface root;
  
    public LSG(dart._runtime.helpers.ConstructorHelper.EmptyConstructorMarker arg, dart._runtime.types.simple.Type type)
    {
      super(arg, type);
    }
  
    public havlak.SimpleLoop_interface createNewLoop()
    {
      final dart._runtime.types.simple.TypeEnvironment dart2java$localTypeEnv = this.dart2java$type.env;
      havlak.LSG_interface __tempVar_0;
      int __tempVar_1;
      int __tempVar_2;
      havlak.SimpleLoop_interface loop = havlak.SimpleLoop._new_SimpleLoop$(dart2java$localTypeEnv.evaluate(dart2java$typeExpr_SimpleLoop), dart._runtime.helpers.LetExpressionHelper.comma(__tempVar_0 = this, dart._runtime.helpers.LetExpressionHelper.comma(__tempVar_1 = __tempVar_0.getLoopCounter(), dart._runtime.helpers.LetExpressionHelper.comma(__tempVar_2 = __tempVar_0.setLoopCounter((__tempVar_1 + 1)), __tempVar_1))));
      return loop;
    }
    public boolean addLoop(havlak.SimpleLoop_interface loop)
    {
      final dart._runtime.types.simple.TypeEnvironment dart2java$localTypeEnv = this.dart2java$type.env;
      return this.getLoops().add_List(loop);
    }
    public int checksum()
    {
      final dart._runtime.types.simple.TypeEnvironment dart2java$localTypeEnv = this.dart2java$type.env;
      int result = this.getLoops().getLength_List();
      java.lang.Object ebefore = null;
      for (dart.core.Iterator_interface<havlak.SimpleLoop_interface> __tempVar_3 = ((dart.core.Iterator_interface<havlak.SimpleLoop_interface>) this.getLoops().getIterator_Iterable()); __tempVar_3.moveNext_Iterator(); )
      {
        havlak.SimpleLoop_interface e = __tempVar_3.getCurrent_Iterator();
        result = havlak.__TopLevel.mix(result, e.checksum());
      }
      return havlak.__TopLevel.mix(result, this.getRoot().checksum());
    }
    public int getNumLoops()
    {
      final dart._runtime.types.simple.TypeEnvironment dart2java$localTypeEnv = this.dart2java$type.env;
      return this.getLoops().getLength_List();
    }
    public void _constructor()
    {
      final dart._runtime.types.simple.TypeEnvironment dart2java$localTypeEnv = this.dart2java$type.env;
      this.loopCounter = 1;
      this.loops = ((dart.core.List_interface) dart._runtime.base.DartList.<havlak.SimpleLoop_interface>specialfactory$fromArguments(dart2java$localTypeEnv.evaluate(dart2java$typeExpr_List$ltSimpleLoop$gt)));
      this.root = havlak.SimpleLoop._new_SimpleLoop$(dart2java$localTypeEnv.evaluate(dart2java$typeExpr_SimpleLoop), 0);
      super._constructor();
      this.getRoot().setNestingLevel_(0);
      this.getLoops().add_List(this.getRoot());
    }
    public int getLoopCounter()
    {
      return this.loopCounter;
    }
    public dart.core.List_interface<havlak.SimpleLoop_interface> getLoops()
    {
      return this.loops;
    }
    public havlak.SimpleLoop_interface getRoot()
    {
      return this.root;
    }
    public int setLoopCounter(int value)
    {
      this.loopCounter = value;
      return value;
    }
    public static havlak.LSG_interface _new_LSG$(dart._runtime.types.simple.Type type)
    {
      havlak.LSG result;
      result = new havlak.LSG(((dart._runtime.helpers.ConstructorHelper.EmptyConstructorMarker) null), type);
      result._constructor();
      return result;
    }
}
