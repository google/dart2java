package tracer;

public class Ray extends dart._runtime.base.DartObject implements tracer.Ray_interface
{
    public static dart._runtime.types.simple.InterfaceTypeInfo dart2java$typeInfo = new dart._runtime.types.simple.InterfaceTypeInfo(tracer.Ray.class, tracer.Ray_interface.class);
    private static dart._runtime.types.simple.InterfaceTypeExpr dart2java$typeExpr_Object = new dart._runtime.types.simple.InterfaceTypeExpr(dart._runtime.helpers.ObjectHelper.dart2java$typeInfo);
    static {
      tracer.Ray.dart2java$typeInfo.superclass = dart2java$typeExpr_Object;
    }
    public java.lang.Object position;
    public java.lang.Object direction;
  
    public Ray(dart._runtime.helpers.ConstructorHelper.EmptyConstructorMarker arg, dart._runtime.types.simple.Type type)
    {
      super(arg, type);
    }
  
    public java.lang.String toString()
    {
      final dart._runtime.types.simple.TypeEnvironment dart2java$localTypeEnv = this.dart2java$type.env;
      return (((("Ray [" + this.getPosition().toString()) + ", ") + this.getDirection().toString()) + "]");
    }
    public void _constructor(java.lang.Object position, java.lang.Object direction)
    {
      final dart._runtime.types.simple.TypeEnvironment dart2java$localTypeEnv = this.dart2java$type.env;
      this.position = position;
      this.direction = direction;
      super._constructor();
    }
    public java.lang.Object getPosition()
    {
      return this.position;
    }
    public java.lang.Object getDirection()
    {
      return this.direction;
    }
    public static tracer.Ray_interface _new(dart._runtime.types.simple.Type type, java.lang.Object position, java.lang.Object direction)
    {
      tracer.Ray result;
      result = new tracer.Ray(((dart._runtime.helpers.ConstructorHelper.EmptyConstructorMarker) null), type);
      result._constructor(position, direction);
      return result;
    }
}
