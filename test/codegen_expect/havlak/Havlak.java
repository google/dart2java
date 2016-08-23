package havlak;

public class Havlak extends havlak.BenchmarkBase
{
    public static dart._runtime.types.simple.InterfaceTypeInfo dart2java$typeInfo = new dart._runtime.types.simple.InterfaceTypeInfo("file:///usr/local/google/home/springerm/ddc-java/gen/codegen_tests/havlak.dart", "Havlak");
    static {
      havlak.Havlak.dart2java$typeInfo.superclass = new dart._runtime.types.simple.InterfaceTypeExpr(havlak.BenchmarkBase.dart2java$typeInfo);
    }
    public havlak.CFG cfg;
  
    public Havlak()
    {
      super((dart._runtime.helpers.ConstructorHelper.EmptyConstructorMarker) null);
      this._constructor();
    }
    public Havlak(dart._runtime.helpers.ConstructorHelper.EmptyConstructorMarker arg)
    {
      super(arg);
    }
  
    protected void _constructor()
    {
      this.cfg = new havlak.CFG();
      super._constructor("Havlak");
      this.getCfg().createNode(0);
      havlak.__TopLevel.buildBaseLoop(this.getCfg(), 0);
      this.getCfg().createNode(1);
      havlak.__TopLevel.buildConnect(this.getCfg(), 0, 2);
      int n = 2;
      for (int parlooptrees = 0; dart._runtime.helpers.IntegerHelper.operatorLess(parlooptrees, 10); parlooptrees = dart._runtime.helpers.IntegerHelper.operatorPlus(parlooptrees, 1))
      {
        this.getCfg().createNode(dart._runtime.helpers.IntegerHelper.operatorPlus(n, 1));
        havlak.__TopLevel.buildConnect(this.getCfg(), n, dart._runtime.helpers.IntegerHelper.operatorPlus(n, 1));
        n = dart._runtime.helpers.IntegerHelper.operatorPlus(n, 1);
        for (int i = 0; dart._runtime.helpers.IntegerHelper.operatorLess(i, 2); i = dart._runtime.helpers.IntegerHelper.operatorPlus(i, 1))
        {
          int top = n;
          n = havlak.__TopLevel.buildStraight(this.getCfg(), n, 1);
          for (int j = 0; dart._runtime.helpers.IntegerHelper.operatorLess(j, 25); j = dart._runtime.helpers.IntegerHelper.operatorPlus(j, 1))
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
      havlak.LSG lsg = new havlak.LSG();
      havlak.HavlakLoopFinder finder = new havlak.HavlakLoopFinder(this.getCfg(), lsg);
      int numLoops = finder.findLoops();
      if ((!dart._runtime.helpers.ObjectHelper.operatorEqual(numLoops, 1522)))
      {
        throw new RuntimeException((("Wrong result - expected <1522>, but was <" + numLoops) + ">"));
      }
    }
    public void warmup()
    {
      for (int dummyloop = 0; dart._runtime.helpers.IntegerHelper.operatorLess(dummyloop, 20); dummyloop = dart._runtime.helpers.IntegerHelper.operatorPlus(dummyloop, 1))
      {
        havlak.LSG lsg = new havlak.LSG();
        havlak.HavlakLoopFinder finder = new havlak.HavlakLoopFinder(this.getCfg(), lsg);
        finder.findLoops();
        int checksum = lsg.checksum();
        if ((!dart._runtime.helpers.ObjectHelper.operatorEqual(checksum, 435630002)))
        {
          throw new RuntimeException((("Wrong checksum - expected <435630002>, but was <" + checksum) + ">"));
        }
      }
    }
    public havlak.CFG getCfg()
    {
      return this.cfg;
    }
}
