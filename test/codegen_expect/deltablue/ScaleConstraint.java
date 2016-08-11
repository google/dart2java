package deltablue;

public class ScaleConstraint extends deltablue.BinaryConstraint
{
    public deltablue.Variable scale = null;
    public deltablue.Variable offset = null;
  
    public ScaleConstraint(deltablue.Variable src, deltablue.Variable scale, deltablue.Variable offset, deltablue.Variable dest, deltablue.Strength strength)
    {
      super((dart._runtime.helpers.ConstructorHelper.EmptyConstructorMarker) null);
      this._constructor(src, scale, offset, dest, strength);
    }
    public ScaleConstraint(dart._runtime.helpers.ConstructorHelper.EmptyConstructorMarker arg)
    {
      super(arg);
    }
  
    protected void _constructor(deltablue.Variable src, deltablue.Variable scale, deltablue.Variable offset, deltablue.Variable dest, deltablue.Strength strength)
    {
      this.scale = scale;
      this.offset = offset;
      super._constructor(src, dest, strength);
    }
    public void addToGraph()
    {
      super.addToGraph();
      this.getScale().addConstraint(this);
      this.getOffset().addConstraint(this);
    }
    public void removeFromGraph()
    {
      super.removeFromGraph();
      if ((!dart._runtime.helpers.ObjectHelper.operatorEqual(this.getScale(), null)))
      {
        this.getScale().removeConstraint(this);
      }
      if ((!dart._runtime.helpers.ObjectHelper.operatorEqual(this.getOffset(), null)))
      {
        this.getOffset().removeConstraint(this);
      }
    }
    public void markInputs(java.lang.Integer mark)
    {
      super.markInputs(mark);
      this.getScale().setMark(this.getOffset().setMark(mark));
    }
    public void execute()
    {
      if (dart._runtime.helpers.ObjectHelper.operatorEqual(this.getDirection(), deltablue.__TopLevel.FORWARD))
      {
        this.getV2().setValue(dart._runtime.helpers.IntegerHelper.operatorPlus(dart._runtime.helpers.IntegerHelper.operatorStar(this.getV1().getValue(), this.getScale().getValue()), this.getOffset().getValue()));
      }
      else
      {
        this.getV1().setValue(dart._runtime.helpers.IntegerHelper.operatorTruncatedDivide(dart._runtime.helpers.IntegerHelper.operatorMinus(this.getV2().getValue(), this.getOffset().getValue()), this.getScale().getValue()));
      }
    }
    public void recalculate()
    {
      deltablue.Variable ihn = this.input();
      deltablue.Variable out = this.output();
      out.setWalkStrength(deltablue.Strength.weakest(this.getStrength(), ihn.getWalkStrength()));
      out.setStay(((ihn.getStay() && this.getScale().getStay()) && this.getOffset().getStay()));
      if (out.getStay())
      {
        this.execute();
      }
    }
    public deltablue.Variable getScale()
    {
      return this.scale;
    }
    public deltablue.Variable getOffset()
    {
      return this.offset;
    }
}
