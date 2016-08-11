package deltablue;

public class EditConstraint extends deltablue.UnaryConstraint
{
  
  
    public EditConstraint(deltablue.Variable v, deltablue.Strength str)
    {
      super((dart._runtime.helpers.ConstructorHelper.EmptyConstructorMarker) null);
      this._constructor(v, str);
    }
    public EditConstraint(dart._runtime.helpers.ConstructorHelper.EmptyConstructorMarker arg)
    {
      super(arg);
    }
  
    protected void _constructor(deltablue.Variable v, deltablue.Strength str)
    {
      super._constructor(v, str);
    }
    public java.lang.Boolean isInput()
    {
      return true;
    }
    public void execute()
    {
      
    }
}
