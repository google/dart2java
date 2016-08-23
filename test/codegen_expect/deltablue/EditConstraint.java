package deltablue;

public class EditConstraint extends deltablue.UnaryConstraint
{
    public static dart._runtime.types.simple.InterfaceTypeInfo dart2java$typeInfo = new dart._runtime.types.simple.InterfaceTypeInfo("file:///usr/local/google/home/stanm/f/d/ddc-java/gen/codegen_tests/deltablue.dart", "EditConstraint");
    static {
      deltablue.EditConstraint.dart2java$typeInfo.superclass = new dart._runtime.types.simple.InterfaceTypeExpr(deltablue.UnaryConstraint.dart2java$typeInfo);
    }
  
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
