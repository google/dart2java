package tracer;

public class Background extends dart._runtime.base.DartObject
{
    public static dart._runtime.types.simple.InterfaceTypeInfo dart2java$typeInfo = new dart._runtime.types.simple.InterfaceTypeInfo("file:///usr/local/google/home/springerm/ddc-java/gen/codegen_tests/tracer.dart", "Background");
    static {
      tracer.Background.dart2java$typeInfo.superclass = new dart._runtime.types.simple.InterfaceTypeExpr(dart._runtime.helpers.ObjectHelper.dart2java$typeInfo);
    }
    public tracer.Color color;
    public java.lang.Double ambience;
  
    public Background(tracer.Color color, java.lang.Double ambience)
    {
      super((dart._runtime.helpers.ConstructorHelper.EmptyConstructorMarker) null);
      this._constructor(color, ambience);
    }
    public Background(dart._runtime.helpers.ConstructorHelper.EmptyConstructorMarker arg)
    {
      super(arg);
    }
  
    protected void _constructor(tracer.Color color, java.lang.Double ambience)
    {
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
