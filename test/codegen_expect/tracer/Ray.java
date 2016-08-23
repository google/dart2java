package tracer;

public class Ray extends dart._runtime.base.DartObject
{
    public static dart._runtime.types.simple.InterfaceTypeInfo dart2java$typeInfo = new dart._runtime.types.simple.InterfaceTypeInfo("file:///usr/local/google/home/springerm/ddc-java/gen/codegen_tests/tracer.dart", "Ray");
    static {
      tracer.Ray.dart2java$typeInfo.superclass = new dart._runtime.types.simple.InterfaceTypeExpr(dart._runtime.helpers.ObjectHelper.dart2java$typeInfo);
    }
    public java.lang.Object position;
    public java.lang.Object direction;
  
    public Ray(java.lang.Object position, java.lang.Object direction)
    {
      super((dart._runtime.helpers.ConstructorHelper.EmptyConstructorMarker) null);
      this._constructor(position, direction);
    }
    public Ray(dart._runtime.helpers.ConstructorHelper.EmptyConstructorMarker arg)
    {
      super(arg);
    }
  
    protected void _constructor(java.lang.Object position, java.lang.Object direction)
    {
      this.position = position;
      this.direction = direction;
      super._constructor();
    }
    public java.lang.String toString()
    {
      return (((("Ray [" + this.getPosition().toString()) + ", ") + this.getDirection().toString()) + "]");
    }
    public java.lang.Object getPosition()
    {
      return this.position;
    }
    public java.lang.Object getDirection()
    {
      return this.direction;
    }
}
