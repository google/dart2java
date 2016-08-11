package deltablue;

public class EqualityConstraint extends deltablue.BinaryConstraint
{
  
  
    public EqualityConstraint(deltablue.Variable v1, deltablue.Variable v2, deltablue.Strength strength)
    {
      super((dart._runtime.helpers.ConstructorHelper.EmptyConstructorMarker) null);
      this._constructor(v1, v2, strength);
    }
    public EqualityConstraint(dart._runtime.helpers.ConstructorHelper.EmptyConstructorMarker arg)
    {
      super(arg);
    }
  
    protected void _constructor(deltablue.Variable v1, deltablue.Variable v2, deltablue.Strength strength)
    {
      super._constructor(v1, v2, strength);
    }
    public void execute()
    {
      this.output().setValue(this.input().getValue());
    }
}
