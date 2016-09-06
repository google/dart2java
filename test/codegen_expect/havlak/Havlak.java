package havlak;

public class Havlak extends havlak.BenchmarkBase implements havlak.Havlak_interface
{
    public static dart._runtime.types.simple.InterfaceTypeInfo dart2java$typeInfo = new dart._runtime.types.simple.InterfaceTypeInfo(havlak.Havlak.class, havlak.Havlak_interface.class);
    static {
      havlak.Havlak.dart2java$typeInfo.superclass = new dart._runtime.types.simple.InterfaceTypeExpr(havlak.BenchmarkBase.dart2java$typeInfo);
    }
    public havlak.CFG_interface cfg;
  
    public Havlak(dart._runtime.types.simple.Type type)
    {
      super(((dart._runtime.helpers.ConstructorHelper.EmptyConstructorMarker) null), type);
      this._constructor();
    }
    public Havlak(dart._runtime.helpers.ConstructorHelper.EmptyConstructorMarker arg, dart._runtime.types.simple.Type type)
    {
      super(arg, type);
    }
  
    protected void _constructor()
    {
      final dart._runtime.types.simple.TypeEnvironment dart2java$localTypeEnv = this.dart2java$type.env;
      this.cfg = new havlak.CFG(dart2java$localTypeEnv.evaluate(new dart._runtime.types.simple.InterfaceTypeExpr(havlak.CFG.dart2java$typeInfo)));
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
    public void exercise()
    {
      final dart._runtime.types.simple.TypeEnvironment dart2java$localTypeEnv = this.dart2java$type.env;
      havlak.LSG_interface lsg = new havlak.LSG(dart2java$localTypeEnv.evaluate(new dart._runtime.types.simple.InterfaceTypeExpr(havlak.LSG.dart2java$typeInfo)));
      havlak.HavlakLoopFinder_interface finder = new havlak.HavlakLoopFinder(dart2java$localTypeEnv.evaluate(new dart._runtime.types.simple.InterfaceTypeExpr(havlak.HavlakLoopFinder.dart2java$typeInfo)), this.getCfg(), lsg);
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
        havlak.LSG_interface lsg = new havlak.LSG(dart2java$localTypeEnv.evaluate(new dart._runtime.types.simple.InterfaceTypeExpr(havlak.LSG.dart2java$typeInfo)));
        havlak.HavlakLoopFinder_interface finder = new havlak.HavlakLoopFinder(dart2java$localTypeEnv.evaluate(new dart._runtime.types.simple.InterfaceTypeExpr(havlak.HavlakLoopFinder.dart2java$typeInfo)), this.getCfg(), lsg);
        finder.findLoops();
        int checksum = lsg.checksum();
        if ((!(checksum == 435630002)))
        {
          throw new RuntimeException((("Wrong checksum - expected <435630002>, but was <" + checksum) + ">"));
        }
      }
    }
    public havlak.CFG_interface getCfg()
    {
      return this.cfg;
    }
}
