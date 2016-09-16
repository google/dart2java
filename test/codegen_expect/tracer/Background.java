package tracer;

public class Background extends dart._runtime.base.DartObject implements tracer.Background_interface
{
    public static dart._runtime.types.simple.InterfaceTypeInfo dart2java$typeInfo = new dart._runtime.types.simple.InterfaceTypeInfo(tracer.Background.class, tracer.Background_interface.class);
    private static dart._runtime.types.simple.InterfaceTypeExpr dart2java$typeExpr_Object = new dart._runtime.types.simple.InterfaceTypeExpr(dart._runtime.helpers.ObjectHelper.dart2java$typeInfo);
    static {
      tracer.Background.dart2java$typeInfo.superclass = dart2java$typeExpr_Object;
    }
    public tracer.Color_interface color;
    public double ambience;
  
    public Background(dart._runtime.helpers.ConstructorHelper.EmptyConstructorMarker arg, dart._runtime.types.simple.Type type)
    {
      super(arg, type);
    }
  
    public void _constructor(tracer.Color_interface color, double ambience)
    {
      final dart._runtime.types.simple.TypeEnvironment dart2java$localTypeEnv = this.dart2java$type.env;
      this.color = color;
      this.ambience = ambience;
      super._constructor();
    }
    public tracer.Color_interface getColor()
    {
      return this.color;
    }
    public double getAmbience()
    {
      return this.ambience;
    }
    public static tracer.Background_interface _new(dart._runtime.types.simple.Type type, tracer.Color_interface color, double ambience)
    {
      tracer.Background_interface result;
      result = new tracer.Background(((dart._runtime.helpers.ConstructorHelper.EmptyConstructorMarker) null), type);
      result._constructor(color, ambience);
      return result;
    }
}
