package deltablue;

public class EqualityConstraint extends deltablue.BinaryConstraint
{
    public static dart._runtime.types.simple.InterfaceTypeInfo dart2java$typeInfo = new dart._runtime.types.simple.InterfaceTypeInfo("file:///usr/local/google/home/andrewkrieger/ddc-java/gen/codegen_tests/deltablue.dart", "EqualityConstraint");
    static {
      deltablue.EqualityConstraint.dart2java$typeInfo.superclass = new dart._runtime.types.simple.InterfaceTypeExpr(deltablue.BinaryConstraint.dart2java$typeInfo);
    }
  
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
