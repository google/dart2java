package tracer;

public class Color extends dart._runtime.base.DartObject implements tracer.Color_interface
{
    public static dart._runtime.types.simple.InterfaceTypeInfo dart2java$typeInfo = new dart._runtime.types.simple.InterfaceTypeInfo("tracer.Color");
    static {
      tracer.Color.dart2java$typeInfo.superclass = new dart._runtime.types.simple.InterfaceTypeExpr(dart._runtime.helpers.ObjectHelper.dart2java$typeInfo);
    }
    public java.lang.Double red;
    public java.lang.Double green;
    public java.lang.Double blue;
  
    public Color(dart._runtime.types.simple.Type type, java.lang.Double red, java.lang.Double green, java.lang.Double blue)
    {
      super((dart._runtime.helpers.ConstructorHelper.EmptyConstructorMarker) null, type);
      this._constructor(red, green, blue);
    }
    public Color(dart._runtime.helpers.ConstructorHelper.EmptyConstructorMarker arg, dart._runtime.types.simple.Type type)
    {
      super(arg, type);
    }
  
    protected void _constructor(java.lang.Double red, java.lang.Double green, java.lang.Double blue)
    {
      final dart._runtime.types.simple.TypeEnvironment dart2java$localTypeEnv = this.dart2java$type.env;
      this.red = red;
      this.green = green;
      this.blue = blue;
      super._constructor();
    }
    public tracer.Color limit()
    {
      final dart._runtime.types.simple.TypeEnvironment dart2java$localTypeEnv = this.dart2java$type.env;
      java.lang.Double r = (dart._runtime.helpers.DoubleHelper.operatorGreater(this.getRed(), 0.0)) ? ((dart._runtime.helpers.DoubleHelper.operatorGreater(this.getRed(), 1.0)) ? (1.0) : (this.getRed())) : (0.0);
      java.lang.Double g = (dart._runtime.helpers.DoubleHelper.operatorGreater(this.getGreen(), 0.0)) ? ((dart._runtime.helpers.DoubleHelper.operatorGreater(this.getGreen(), 1.0)) ? (1.0) : (this.getGreen())) : (0.0);
      java.lang.Double b = (dart._runtime.helpers.DoubleHelper.operatorGreater(this.getBlue(), 0.0)) ? ((dart._runtime.helpers.DoubleHelper.operatorGreater(this.getBlue(), 1.0)) ? (1.0) : (this.getBlue())) : (0.0);
      return new tracer.Color(dart2java$localTypeEnv.evaluate(new dart._runtime.types.simple.InterfaceTypeExpr(tracer.Color.dart2java$typeInfo)), r, g, b);
    }
    public tracer.Color operatorPlus(tracer.Color c2)
    {
      final dart._runtime.types.simple.TypeEnvironment dart2java$localTypeEnv = this.dart2java$type.env;
      return new tracer.Color(dart2java$localTypeEnv.evaluate(new dart._runtime.types.simple.InterfaceTypeExpr(tracer.Color.dart2java$typeInfo)), dart._runtime.helpers.DoubleHelper.operatorPlus(this.getRed(), c2.getRed()), dart._runtime.helpers.DoubleHelper.operatorPlus(this.getGreen(), c2.getGreen()), dart._runtime.helpers.DoubleHelper.operatorPlus(this.getBlue(), c2.getBlue()));
    }
    public tracer.Color addScalar(java.lang.Double s)
    {
      final dart._runtime.types.simple.TypeEnvironment dart2java$localTypeEnv = this.dart2java$type.env;
      tracer.Color result = new tracer.Color(dart2java$localTypeEnv.evaluate(new dart._runtime.types.simple.InterfaceTypeExpr(tracer.Color.dart2java$typeInfo)), dart._runtime.helpers.DoubleHelper.operatorPlus(this.getRed(), s), dart._runtime.helpers.DoubleHelper.operatorPlus(this.getGreen(), s), dart._runtime.helpers.DoubleHelper.operatorPlus(this.getBlue(), s));
      result.limit();
      return result;
    }
    public tracer.Color operatorStar(tracer.Color c2)
    {
      final dart._runtime.types.simple.TypeEnvironment dart2java$localTypeEnv = this.dart2java$type.env;
      tracer.Color result = new tracer.Color(dart2java$localTypeEnv.evaluate(new dart._runtime.types.simple.InterfaceTypeExpr(tracer.Color.dart2java$typeInfo)), dart._runtime.helpers.DoubleHelper.operatorStar(this.getRed(), c2.getRed()), dart._runtime.helpers.DoubleHelper.operatorStar(this.getGreen(), c2.getGreen()), dart._runtime.helpers.DoubleHelper.operatorStar(this.getBlue(), c2.getBlue()));
      return result;
    }
    public tracer.Color multiplyScalar(java.lang.Double f)
    {
      final dart._runtime.types.simple.TypeEnvironment dart2java$localTypeEnv = this.dart2java$type.env;
      tracer.Color result = new tracer.Color(dart2java$localTypeEnv.evaluate(new dart._runtime.types.simple.InterfaceTypeExpr(tracer.Color.dart2java$typeInfo)), dart._runtime.helpers.DoubleHelper.operatorStar(this.getRed(), f), dart._runtime.helpers.DoubleHelper.operatorStar(this.getGreen(), f), dart._runtime.helpers.DoubleHelper.operatorStar(this.getBlue(), f));
      return result;
    }
    public tracer.Color blend(tracer.Color c2, java.lang.Double w)
    {
      final dart._runtime.types.simple.TypeEnvironment dart2java$localTypeEnv = this.dart2java$type.env;
      tracer.Color result = this.multiplyScalar(dart._runtime.helpers.DoubleHelper.operatorMinus(1.0, w)).operatorPlus(c2.multiplyScalar(w));
      return result;
    }
    public int brightness()
    {
      final dart._runtime.types.simple.TypeEnvironment dart2java$localTypeEnv = this.dart2java$type.env;
      int r = dart._runtime.helpers.DoubleHelper.toInt(dart._runtime.helpers.DoubleHelper.operatorStar(this.getRed(), 255));
      int g = dart._runtime.helpers.DoubleHelper.toInt(dart._runtime.helpers.DoubleHelper.operatorStar(this.getGreen(), 255));
      int b = dart._runtime.helpers.DoubleHelper.toInt(dart._runtime.helpers.DoubleHelper.operatorStar(this.getBlue(), 255));
      return ((((r * 77) + (g * 150)) + (b * 29)) >> 8);
    }
    public java.lang.String toString()
    {
      final dart._runtime.types.simple.TypeEnvironment dart2java$localTypeEnv = this.dart2java$type.env;
      int r = dart._runtime.helpers.DoubleHelper.toInt(dart._runtime.helpers.DoubleHelper.operatorStar(this.getRed(), 255));
      int g = dart._runtime.helpers.DoubleHelper.toInt(dart._runtime.helpers.DoubleHelper.operatorStar(this.getGreen(), 255));
      int b = dart._runtime.helpers.DoubleHelper.toInt(dart._runtime.helpers.DoubleHelper.operatorStar(this.getBlue(), 255));
      return (((((("rgb(" + r) + ",") + g) + ",") + b) + ")");
    }
    public java.lang.Double getRed()
    {
      return this.red;
    }
    public java.lang.Double getGreen()
    {
      return this.green;
    }
    public java.lang.Double getBlue()
    {
      return this.blue;
    }
}
