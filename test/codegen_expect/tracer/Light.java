package tracer;

public class Light extends dart._runtime.base.DartObject
{
    public static dart._runtime.types.simple.InterfaceTypeInfo dart2java$typeInfo = new dart._runtime.types.simple.InterfaceTypeInfo("file:///usr/local/google/home/springerm/ddc-java/gen/codegen_tests/tracer.dart", "Light");
    static {
      tracer.Light.dart2java$typeInfo.superclass = new dart._runtime.types.simple.InterfaceTypeExpr(dart._runtime.helpers.ObjectHelper.dart2java$typeInfo);
    }
    public java.lang.Object position;
    public java.lang.Object color;
    public java.lang.Object intensity;
  
    public Light(java.lang.Object position, java.lang.Object color, java.lang.Object intensity)
    {
      super((dart._runtime.helpers.ConstructorHelper.EmptyConstructorMarker) null);
      this._constructor(position, color, intensity);
    }
    public Light(dart._runtime.helpers.ConstructorHelper.EmptyConstructorMarker arg)
    {
      super(arg);
    }
  
    protected void _constructor(java.lang.Object position, java.lang.Object color, java.lang.Object intensity)
    {
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
