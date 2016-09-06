package tracer;

public class Color extends dart._runtime.base.DartObject implements tracer.Color_interface
{
    public static dart._runtime.types.simple.InterfaceTypeInfo dart2java$typeInfo = new dart._runtime.types.simple.InterfaceTypeInfo(tracer.Color.class, tracer.Color_interface.class);
    static {
      tracer.Color.dart2java$typeInfo.superclass = new dart._runtime.types.simple.InterfaceTypeExpr(dart._runtime.helpers.ObjectHelper.dart2java$typeInfo);
    }
    public double red;
    public double green;
    public double blue;
  
    public Color(dart._runtime.types.simple.Type type, double red, double green, double blue)
    {
      super(((dart._runtime.helpers.ConstructorHelper.EmptyConstructorMarker) null), type);
      this._constructor(red, green, blue);
    }
    public Color(dart._runtime.helpers.ConstructorHelper.EmptyConstructorMarker arg, dart._runtime.types.simple.Type type)
    {
      super(arg, type);
    }
  
    protected void _constructor(double red, double green, double blue)
    {
      final dart._runtime.types.simple.TypeEnvironment dart2java$localTypeEnv = this.dart2java$type.env;
      this.red = red;
      this.green = green;
      this.blue = blue;
      super._constructor();
    }
    public tracer.Color_interface limit()
    {
      final dart._runtime.types.simple.TypeEnvironment dart2java$localTypeEnv = this.dart2java$type.env;
      double r = ((this.getRed() > 0.0)) ? (((this.getRed() > 1.0)) ? (1.0) : (this.getRed())) : (0.0);
      double g = ((this.getGreen() > 0.0)) ? (((this.getGreen() > 1.0)) ? (1.0) : (this.getGreen())) : (0.0);
      double b = ((this.getBlue() > 0.0)) ? (((this.getBlue() > 1.0)) ? (1.0) : (this.getBlue())) : (0.0);
      return new tracer.Color(dart2java$localTypeEnv.evaluate(new dart._runtime.types.simple.InterfaceTypeExpr(tracer.Color.dart2java$typeInfo)), r, g, b);
    }
    public tracer.Color_interface operatorPlus(tracer.Color_interface c2)
    {
      final dart._runtime.types.simple.TypeEnvironment dart2java$localTypeEnv = this.dart2java$type.env;
      return new tracer.Color(dart2java$localTypeEnv.evaluate(new dart._runtime.types.simple.InterfaceTypeExpr(tracer.Color.dart2java$typeInfo)), (this.getRed() + c2.getRed()), (this.getGreen() + c2.getGreen()), (this.getBlue() + c2.getBlue()));
    }
    public tracer.Color_interface addScalar(double s)
    {
      final dart._runtime.types.simple.TypeEnvironment dart2java$localTypeEnv = this.dart2java$type.env;
      tracer.Color_interface result = new tracer.Color(dart2java$localTypeEnv.evaluate(new dart._runtime.types.simple.InterfaceTypeExpr(tracer.Color.dart2java$typeInfo)), (this.getRed() + s), (this.getGreen() + s), (this.getBlue() + s));
      result.limit();
      return result;
    }
    public tracer.Color_interface operatorStar(tracer.Color_interface c2)
    {
      final dart._runtime.types.simple.TypeEnvironment dart2java$localTypeEnv = this.dart2java$type.env;
      tracer.Color_interface result = new tracer.Color(dart2java$localTypeEnv.evaluate(new dart._runtime.types.simple.InterfaceTypeExpr(tracer.Color.dart2java$typeInfo)), (this.getRed() * c2.getRed()), (this.getGreen() * c2.getGreen()), (this.getBlue() * c2.getBlue()));
      return result;
    }
    public tracer.Color_interface multiplyScalar(double f)
    {
      final dart._runtime.types.simple.TypeEnvironment dart2java$localTypeEnv = this.dart2java$type.env;
      tracer.Color_interface result = new tracer.Color(dart2java$localTypeEnv.evaluate(new dart._runtime.types.simple.InterfaceTypeExpr(tracer.Color.dart2java$typeInfo)), (this.getRed() * f), (this.getGreen() * f), (this.getBlue() * f));
      return result;
    }
    public tracer.Color_interface blend(tracer.Color_interface c2, double w)
    {
      final dart._runtime.types.simple.TypeEnvironment dart2java$localTypeEnv = this.dart2java$type.env;
      tracer.Color_interface result = this.multiplyScalar((1.0 - w)).operatorPlus(c2.multiplyScalar(w));
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
    public double getRed()
    {
      return this.red;
    }
    public double getGreen()
    {
      return this.green;
    }
    public double getBlue()
    {
      return this.blue;
    }
}
