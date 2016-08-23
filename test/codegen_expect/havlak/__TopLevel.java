package havlak;

public class __TopLevel
{
    public static int numBasicBlocks = 0;
  
  
  
    public static void main(String[] args)
    {
      new havlak.Havlak().report();
    }
    public static int mix(int existing, int value)
    {
      return dart._runtime.helpers.IntegerHelper.operatorPlus(dart._runtime.helpers.IntegerHelper.operatorShiftLeft(dart._runtime.helpers.IntegerHelper.operatorBitAnd(existing, 268435455), 1), value);
    }
    public static int getNumBasicBlocks()
    {
      return havlak.__TopLevel.numBasicBlocks;
    }
    public static int buildDiamond(havlak.CFG cfg, int start)
    {
      int bb0 = start;
      new havlak.BasicBlockEdge(cfg, bb0, dart._runtime.helpers.IntegerHelper.operatorPlus(bb0, 1));
      new havlak.BasicBlockEdge(cfg, bb0, dart._runtime.helpers.IntegerHelper.operatorPlus(bb0, 2));
      new havlak.BasicBlockEdge(cfg, dart._runtime.helpers.IntegerHelper.operatorPlus(bb0, 1), dart._runtime.helpers.IntegerHelper.operatorPlus(bb0, 3));
      new havlak.BasicBlockEdge(cfg, dart._runtime.helpers.IntegerHelper.operatorPlus(bb0, 2), dart._runtime.helpers.IntegerHelper.operatorPlus(bb0, 3));
      return dart._runtime.helpers.IntegerHelper.operatorPlus(bb0, 3);
    }
    public static void buildConnect(havlak.CFG cfg, int start, int end)
    {
      new havlak.BasicBlockEdge(cfg, start, end);
    }
    public static int buildStraight(havlak.CFG cfg, int start, int n)
    {
      for (int i = 0; dart._runtime.helpers.IntegerHelper.operatorLess(i, n); i = dart._runtime.helpers.IntegerHelper.operatorPlus(i, 1))
      {
        havlak.__TopLevel.buildConnect(cfg, dart._runtime.helpers.IntegerHelper.operatorPlus(start, i), dart._runtime.helpers.IntegerHelper.operatorPlus(dart._runtime.helpers.IntegerHelper.operatorPlus(start, i), 1));
      }
      return dart._runtime.helpers.IntegerHelper.operatorPlus(start, n);
    }
    public static int buildBaseLoop(havlak.CFG cfg, int from)
    {
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
