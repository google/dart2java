package havlak;

public class Havlak extends havlak.BenchmarkBase implements havlak.Havlak_interface
{
    public static dart._runtime.types.simple.InterfaceTypeInfo dart2java$typeInfo = new dart._runtime.types.simple.InterfaceTypeInfo(havlak.Havlak.class, havlak.Havlak_interface.class);
    private static dart._runtime.types.simple.InterfaceTypeExpr dart2java$typeExpr_LSG = new dart._runtime.types.simple.InterfaceTypeExpr(havlak.LSG.dart2java$typeInfo);
    private static dart._runtime.types.simple.InterfaceTypeExpr dart2java$typeExpr_HavlakLoopFinder = new dart._runtime.types.simple.InterfaceTypeExpr(havlak.HavlakLoopFinder.dart2java$typeInfo);
    private static dart._runtime.types.simple.InterfaceTypeExpr dart2java$typeExpr_CFG = new dart._runtime.types.simple.InterfaceTypeExpr(havlak.CFG.dart2java$typeInfo);
    private static dart._runtime.types.simple.InterfaceTypeExpr dart2java$typeExpr_BenchmarkBase = new dart._runtime.types.simple.InterfaceTypeExpr(havlak.BenchmarkBase.dart2java$typeInfo);
    static {
      havlak.Havlak.dart2java$typeInfo.superclass = dart2java$typeExpr_BenchmarkBase;
    }
    public havlak.CFG_interface cfg;
  
    public Havlak(dart._runtime.helpers.ConstructorHelper.EmptyConstructorMarker arg, dart._runtime.types.simple.Type type)
    {
      super(arg, type);
    }
  
    public void exercise()
    {
      final dart._runtime.types.simple.TypeEnvironment dart2java$localTypeEnv = this.dart2java$type.env;
      havlak.LSG_interface lsg = havlak.LSG._new(dart2java$localTypeEnv.evaluate(dart2java$typeExpr_LSG));
      havlak.HavlakLoopFinder_interface finder = havlak.HavlakLoopFinder._new(dart2java$localTypeEnv.evaluate(dart2java$typeExpr_HavlakLoopFinder), this.getCfg(), lsg);
      int numLoops = finder.findLoops();
      if ((!(numLoops == 1522)))
      {
        throw new RuntimeException((("Wrong result - expected <1522>, but was <" + numLoops) + ">"));
      }
    }
    public void warmup()
    {
      final dart._runtime.types.simple.TypeEnvironment dart2java$localTypeEnv = this.dart2java$type.env;
      for (int dummyloop = 0; (dummyloop < 20); dummyloop = (dummyloop + 1))
      {
        havlak.LSG_interface lsg = havlak.LSG._new(dart2java$localTypeEnv.evaluate(dart2java$typeExpr_LSG));
        havlak.HavlakLoopFinder_interface finder = havlak.HavlakLoopFinder._new(dart2java$localTypeEnv.evaluate(dart2java$typeExpr_HavlakLoopFinder), this.getCfg(), lsg);
        finder.findLoops();
        int checksum = lsg.checksum();
        if ((!(checksum == 435630002)))
        {
          throw new RuntimeException((("Wrong checksum - expected <435630002>, but was <" + checksum) + ">"));
        }
      }
    }
    public void _constructor()
    {
      final dart._runtime.types.simple.TypeEnvironment dart2java$localTypeEnv = this.dart2java$type.env;
      this.cfg = havlak.CFG._new(dart2java$localTypeEnv.evaluate(dart2java$typeExpr_CFG));
      super._constructor("Havlak");
      this.getCfg().createNode(0);
      havlak.__TopLevel.buildBaseLoop(this.getCfg(), 0);
      this.getCfg().createNode(1);
      havlak.__TopLevel.buildConnect(this.getCfg(), 0, 2);
      int n = 2;
      for (int parlooptrees = 0; (parlooptrees < 10); parlooptrees = (parlooptrees + 1))
      {
        this.getCfg().createNode((n + 1));
        havlak.__TopLevel.buildConnect(this.getCfg(), n, (n + 1));
        n = (n + 1);
        for (int i = 0; (i < 2); i = (i + 1))
        {
          int top = n;
          n = havlak.__TopLevel.buildStraight(this.getCfg(), n, 1);
          for (int j = 0; (j < 25); j = (j + 1))
          {
            n = havlak.__TopLevel.buildBaseLoop(this.getCfg(), n);
          }
          int bottom = havlak.__TopLevel.buildStraight(this.getCfg(), n, 1);
          havlak.__TopLevel.buildConnect(this.getCfg(), n, top);
          n = bottom;
        }
      }
    }
    public havlak.CFG_interface getCfg()
    {
      return this.cfg;
    }
    public static havlak.Havlak_interface _new(dart._runtime.types.simple.Type type)
    {
      havlak.Havlak_interface result;
      result = new havlak.Havlak(((dart._runtime.helpers.ConstructorHelper.EmptyConstructorMarker) null), type);
      result._constructor();
      return result;
    }
}
