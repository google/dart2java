package deltablue;

public class ScaleConstraint extends deltablue.BinaryConstraint implements deltablue.ScaleConstraint_interface
{
    public static dart._runtime.types.simple.InterfaceTypeInfo dart2java$typeInfo = new dart._runtime.types.simple.InterfaceTypeInfo(deltablue.ScaleConstraint.class, deltablue.ScaleConstraint_interface.class);
    private static dart._runtime.types.simple.InterfaceTypeExpr dart2java$typeExpr_BinaryConstraint = new dart._runtime.types.simple.InterfaceTypeExpr(deltablue.BinaryConstraint.dart2java$typeInfo);
    static {
      deltablue.ScaleConstraint.dart2java$typeInfo.superclass = dart2java$typeExpr_BinaryConstraint;
    }
    public deltablue.Variable_interface scale;
    public deltablue.Variable_interface offset;
  
    public ScaleConstraint(dart._runtime.helpers.ConstructorHelper.EmptyConstructorMarker arg, dart._runtime.types.simple.Type type)
    {
      super(arg, type);
    }
  
    public void addToGraph()
    {
      final dart._runtime.types.simple.TypeEnvironment dart2java$localTypeEnv = this.dart2java$type.env;
      super.addToGraph();
      this.getScale().addConstraint(this);
      this.getOffset().addConstraint(this);
    }
    public void removeFromGraph()
    {
      final dart._runtime.types.simple.TypeEnvironment dart2java$localTypeEnv = this.dart2java$type.env;
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
    public void markInputs(int mark)
    {
      final dart._runtime.types.simple.TypeEnvironment dart2java$localTypeEnv = this.dart2java$type.env;
      super.markInputs(mark);
      this.getScale().setMark(this.getOffset().setMark(mark));
    }
    public void execute()
    {
      final dart._runtime.types.simple.TypeEnvironment dart2java$localTypeEnv = this.dart2java$type.env;
      if ((this.getDirection() == deltablue.__TopLevel.FORWARD))
      {
        this.getV2().setValue(((this.getV1().getValue() * this.getScale().getValue()) + this.getOffset().getValue()));
      }
      else
      {
        this.getV1().setValue(dart._runtime.helpers.IntegerHelper.operatorTruncatedDivide((this.getV2().getValue() - this.getOffset().getValue()), this.getScale().getValue()));
      }
    }
    public void recalculate()
    {
      final dart._runtime.types.simple.TypeEnvironment dart2java$localTypeEnv = this.dart2java$type.env;
      deltablue.Variable_interface ihn = this.input();
      deltablue.Variable_interface out = this.output();
      out.setWalkStrength(deltablue.Strength.weakest(this.getStrength(), ihn.getWalkStrength()));
      out.setStay(((ihn.getStay() && this.getScale().getStay()) && this.getOffset().getStay()));
      if (out.getStay())
      {
        this.execute();
      }
    }
    public void _constructor(deltablue.Variable_interface src, deltablue.Variable_interface scale, deltablue.Variable_interface offset, deltablue.Variable_interface dest, deltablue.Strength_interface strength)
    {
      final dart._runtime.types.simple.TypeEnvironment dart2java$localTypeEnv = this.dart2java$type.env;
      this.scale = scale;
      this.offset = offset;
      super._constructor(src, dest, strength);
    }
    public deltablue.Variable_interface getScale()
    {
      return this.scale;
    }
    public deltablue.Variable_interface getOffset()
    {
      return this.offset;
    }
    public static deltablue.ScaleConstraint_interface _new_ScaleConstraint$(dart._runtime.types.simple.Type type, deltablue.Variable_interface src, deltablue.Variable_interface scale, deltablue.Variable_interface offset, deltablue.Variable_interface dest, deltablue.Strength_interface strength)
    {
      deltablue.ScaleConstraint result;
      result = new deltablue.ScaleConstraint(((dart._runtime.helpers.ConstructorHelper.EmptyConstructorMarker) null), type);
      result._constructor(src, scale, offset, dest, strength);
      return result;
    }
}
