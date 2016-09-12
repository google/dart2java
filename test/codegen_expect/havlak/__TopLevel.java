package havlak;

public class __TopLevel
{
    private static dart._runtime.types.simple.InterfaceTypeExpr dart2java$typeExpr_Havlak = new dart._runtime.types.simple.InterfaceTypeExpr(havlak.Havlak.dart2java$typeInfo);
    private static dart._runtime.types.simple.InterfaceTypeExpr dart2java$typeExpr_BasicBlockEdge = new dart._runtime.types.simple.InterfaceTypeExpr(havlak.BasicBlockEdge.dart2java$typeInfo);
    static {
      final dart._runtime.types.simple.TypeEnvironment dart2java$localTypeEnv = dart._runtime.types.simple.TypeEnvironment.ROOT;
      havlak.__TopLevel.numBasicBlocks = 0;
    }
    public static int numBasicBlocks;
  
  
  
    public static void main(String[] args)
    {
      final dart._runtime.types.simple.TypeEnvironment dart2java$localTypeEnv = dart._runtime.types.simple.TypeEnvironment.ROOT;
      new havlak.Havlak(dart2java$localTypeEnv.evaluate(dart2java$typeExpr_Havlak)).report();
    }
    public static int mix(int existing, int value)
    {
      final dart._runtime.types.simple.TypeEnvironment dart2java$localTypeEnv = dart._runtime.types.simple.TypeEnvironment.ROOT;
      return (((existing & 268435455) << 1) + value);
    }
    public static int getNumBasicBlocks()
    {
      final dart._runtime.types.simple.TypeEnvironment dart2java$localTypeEnv = dart._runtime.types.simple.TypeEnvironment.ROOT;
      return havlak.__TopLevel.numBasicBlocks;
    }
    public static int buildDiamond(havlak.CFG_interface cfg, int start)
    {
      final dart._runtime.types.simple.TypeEnvironment dart2java$localTypeEnv = dart._runtime.types.simple.TypeEnvironment.ROOT;
      int bb0 = start;
      new havlak.BasicBlockEdge(dart2java$localTypeEnv.evaluate(dart2java$typeExpr_BasicBlockEdge), cfg, bb0, (bb0 + 1));
      new havlak.BasicBlockEdge(dart2java$localTypeEnv.evaluate(dart2java$typeExpr_BasicBlockEdge), cfg, bb0, (bb0 + 2));
      new havlak.BasicBlockEdge(dart2java$localTypeEnv.evaluate(dart2java$typeExpr_BasicBlockEdge), cfg, (bb0 + 1), (bb0 + 3));
      new havlak.BasicBlockEdge(dart2java$localTypeEnv.evaluate(dart2java$typeExpr_BasicBlockEdge), cfg, (bb0 + 2), (bb0 + 3));
      return (bb0 + 3);
    }
    public static void buildConnect(havlak.CFG_interface cfg, int start, int end)
    {
      final dart._runtime.types.simple.TypeEnvironment dart2java$localTypeEnv = dart._runtime.types.simple.TypeEnvironment.ROOT;
      new havlak.BasicBlockEdge(dart2java$localTypeEnv.evaluate(dart2java$typeExpr_BasicBlockEdge), cfg, start, end);
    }
    public static int buildStraight(havlak.CFG_interface cfg, int start, int n)
    {
      final dart._runtime.types.simple.TypeEnvironment dart2java$localTypeEnv = dart._runtime.types.simple.TypeEnvironment.ROOT;
      for (int i = 0; (i < n); i = (i + 1))
      {
        havlak.__TopLevel.buildConnect(cfg, (start + i), ((start + i) + 1));
      }
      return (start + n);
    }
    public static int buildBaseLoop(havlak.CFG_interface cfg, int from)
    {
      final dart._runtime.types.simple.TypeEnvironment dart2java$localTypeEnv = dart._runtime.types.simple.TypeEnvironment.ROOT;
      int header = havlak.__TopLevel.buildStraight(cfg, from, 1);
      int diamond1 = havlak.__TopLevel.buildDiamond(cfg, header);
      int d11 = havlak.__TopLevel.buildStraight(cfg, diamond1, 1);
      int diamond2 = havlak.__TopLevel.buildDiamond(cfg, d11);
      int footer = havlak.__TopLevel.buildStraight(cfg, diamond2, 1);
      havlak.__TopLevel.buildConnect(cfg, diamond2, d11);
      havlak.__TopLevel.buildConnect(cfg, diamond1, header);
      havlak.__TopLevel.buildConnect(cfg, footer, from);
      footer = havlak.__TopLevel.buildStraight(cfg, footer, 1);
      return footer;
    }
}
