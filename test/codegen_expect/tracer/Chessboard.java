package tracer;

public class Chessboard extends tracer.Materials implements tracer.Chessboard_interface
{
    public static dart._runtime.types.simple.InterfaceTypeInfo dart2java$typeInfo = new dart._runtime.types.simple.InterfaceTypeInfo("tracer.Chessboard");
    static {
      tracer.Chessboard.dart2java$typeInfo.superclass = new dart._runtime.types.simple.InterfaceTypeExpr(tracer.Materials.dart2java$typeInfo);
    }
    public tracer.Color_interface colorEven;
    public tracer.Color_interface colorOdd;
    public double density;
  
    public Chessboard(dart._runtime.types.simple.Type type, tracer.Color_interface colorEven, tracer.Color_interface colorOdd, java.lang.Object reflection, java.lang.Object transparency, java.lang.Object gloss, double density)
    {
      super((dart._runtime.helpers.ConstructorHelper.EmptyConstructorMarker) null, type);
      this._constructor(colorEven, colorOdd, reflection, transparency, gloss, density);
    }
    public Chessboard(dart._runtime.helpers.ConstructorHelper.EmptyConstructorMarker arg, dart._runtime.types.simple.Type type)
    {
      super(arg, type);
    }
  
    protected void _constructor(tracer.Color_interface colorEven, tracer.Color_interface colorOdd, java.lang.Object reflection, java.lang.Object transparency, java.lang.Object gloss, double density)
    {
      final dart._runtime.types.simple.TypeEnvironment dart2java$localTypeEnv = this.dart2java$type.env;
      this.colorEven = colorEven;
      this.colorOdd = colorOdd;
      this.density = density;
      super._constructor((double) reflection, (double) transparency, (double) gloss, 0.5, true);
    }
    public tracer.Color_interface getColor_(java.lang.Number u, java.lang.Number v)
    {
      final dart._runtime.types.simple.TypeEnvironment dart2java$localTypeEnv = this.dart2java$type.env;
      java.lang.Object t = dart._runtime.helpers.DynamicHelper.invoke("operatorStar", this.wrapUp(dart._runtime.helpers.NumberHelper.operatorStar(u, this.getDensity())), this.wrapUp(dart._runtime.helpers.NumberHelper.operatorStar(v, this.getDensity())));
      if ((boolean) dart._runtime.helpers.DynamicHelper.invoke("operatorLess", t, 0.0))
      {
        return this.getColorEven();
      }
      else
      {
        return this.getColorOdd();
      }
    }
    public tracer.Color_interface getColorEven()
    {
      return this.colorEven;
    }
    public tracer.Color_interface getColorOdd()
    {
      return this.colorOdd;
    }
    public double getDensity()
    {
      return this.density;
    }
}
