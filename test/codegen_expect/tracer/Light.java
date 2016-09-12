package tracer;

public class Light extends dart._runtime.base.DartObject implements tracer.Light_interface
{
    public static dart._runtime.types.simple.InterfaceTypeInfo dart2java$typeInfo = new dart._runtime.types.simple.InterfaceTypeInfo(tracer.Light.class, tracer.Light_interface.class);
    private static dart._runtime.types.simple.InterfaceTypeExpr dart2java$typeExpr_Object = new dart._runtime.types.simple.InterfaceTypeExpr(dart._runtime.helpers.ObjectHelper.dart2java$typeInfo);
    static {
      tracer.Light.dart2java$typeInfo.superclass = dart2java$typeExpr_Object;
    }
    public java.lang.Object position;
    public java.lang.Object color;
    public java.lang.Object intensity;
  
    public Light(dart._runtime.types.simple.Type type, java.lang.Object position, java.lang.Object color, java.lang.Object intensity)
    {
      super(((dart._runtime.helpers.ConstructorHelper.EmptyConstructorMarker) null), type);
      this._constructor(position, color, intensity);
    }
    public Light(dart._runtime.helpers.ConstructorHelper.EmptyConstructorMarker arg, dart._runtime.types.simple.Type type)
    {
      super(arg, type);
    }
  
    protected void _constructor(java.lang.Object position, java.lang.Object color, java.lang.Object intensity)
    {
      final dart._runtime.types.simple.TypeEnvironment dart2java$localTypeEnv = this.dart2java$type.env;
      this.position = position;
      this.color = color;
      this.intensity = intensity;
      super._constructor();
    }
    public java.lang.Object getPosition()
    {
      return this.position;
    }
    public java.lang.Object getColor()
    {
      return this.color;
    }
    public java.lang.Object getIntensity()
    {
      return this.intensity;
    }
}
