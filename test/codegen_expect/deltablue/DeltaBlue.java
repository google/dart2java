package deltablue;

public class DeltaBlue extends deltablue.BenchmarkBase
{
  
  
    public DeltaBlue()
    {
      super((dart._runtime.helpers.ConstructorHelper.EmptyConstructorMarker) null);
      this._constructor();
    }
    public DeltaBlue(dart._runtime.helpers.ConstructorHelper.EmptyConstructorMarker arg)
    {
      super(arg);
    }
  
    protected void _constructor()
    {
      super._constructor("DeltaBlue");
    }
    public void run()
    {
      deltablue.__TopLevel.chainTest(100);
      deltablue.__TopLevel.projectionTest(100);
    }
}
