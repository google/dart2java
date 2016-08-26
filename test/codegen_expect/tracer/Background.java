package tracer;

public class Background extends dart._runtime.base.DartObject
{
    public static dart._runtime.types.simple.InterfaceTypeInfo dart2java$typeInfo = new dart._runtime.types.simple.InterfaceTypeInfo("tracer.Background");
    static {
      tracer.Background.dart2java$typeInfo.superclass = new dart._runtime.types.simple.InterfaceTypeExpr(dart._runtime.helpers.ObjectHelper.dart2java$typeInfo);
    }
    public tracer.Color color;
    public java.lang.Double ambience;
  
    public Background(dart._runtime.types.simple.Type type, tracer.Color color, java.lang.Double ambience)
    {
      super((dart._runtime.helpers.ConstructorHelper.EmptyConstructorMarker) null, type);
      this._constructor(color, ambience);
    }
    public Background(dart._runtime.helpers.ConstructorHelper.EmptyConstructorMarker arg, dart._runtime.types.simple.Type type)
    {
      super(arg, type);
    }
  
    protected void _constructor(tracer.Color color, java.lang.Double ambience)
    {
      final dart._runtime.types.simple.TypeEnvironment dart2java$localTypeEnv = this.dart2java$type.env;
      this.color = color;
      this.ambience = ambience;
      super._constructor();
    }
    public tracer.Color getColor()
    {
      return this.color;
    }
    public java.lang.Double getAmbience()
    {
      return this.ambience;
    }
}
