package tracer;

public class IntersectionInfo extends dart._runtime.base.DartObject implements tracer.IntersectionInfo_interface
{
    public static dart._runtime.types.simple.InterfaceTypeInfo dart2java$typeInfo = new dart._runtime.types.simple.InterfaceTypeInfo(tracer.IntersectionInfo.class, tracer.IntersectionInfo_interface.class);
    private static dart._runtime.types.simple.InterfaceTypeExpr dart2java$typeExpr_Color = new dart._runtime.types.simple.InterfaceTypeExpr(tracer.Color.dart2java$typeInfo);
    private static dart._runtime.types.simple.InterfaceTypeExpr dart2java$typeExpr_Object = new dart._runtime.types.simple.InterfaceTypeExpr(dart._runtime.helpers.ObjectHelper.dart2java$typeInfo);
    static {
      tracer.IntersectionInfo.dart2java$typeInfo.superclass = dart2java$typeExpr_Object;
    }
    public boolean isHit;
    public int hitCount;
    public java.lang.Object shape;
    public java.lang.Object position;
    public java.lang.Object normal;
    public java.lang.Object color;
    public java.lang.Object distance;
  
    public IntersectionInfo(dart._runtime.helpers.ConstructorHelper.EmptyConstructorMarker arg, dart._runtime.types.simple.Type type)
    {
      super(arg, type);
    }
  
    public java.lang.String toString()
    {
      final dart._runtime.types.simple.TypeEnvironment dart2java$localTypeEnv = this.dart2java$type.env;
      return (("Intersection [" + this.getPosition().toString()) + "]");
    }
    public void _constructor()
    {
      final dart._runtime.types.simple.TypeEnvironment dart2java$localTypeEnv = this.dart2java$type.env;
      this.isHit = false;
      this.hitCount = 0;
      super._constructor();
      this.setColor(tracer.Color._new(dart2java$localTypeEnv.evaluate(dart2java$typeExpr_Color), 0.0, 0.0, 0.0));
    }
    public boolean getIsHit()
    {
      return this.isHit;
    }
    public int getHitCount()
    {
      return this.hitCount;
    }
    public java.lang.Object getShape()
    {
      return this.shape;
    }
    public java.lang.Object getPosition()
    {
      return this.position;
    }
    public java.lang.Object getNormal()
    {
      return this.normal;
    }
    public java.lang.Object getColor()
    {
      return this.color;
    }
    public java.lang.Object getDistance()
    {
      return this.distance;
    }
    public boolean setIsHit(boolean value)
    {
      this.isHit = value;
      return value;
    }
    public int setHitCount(int value)
    {
      this.hitCount = value;
      return value;
    }
    public java.lang.Object setShape(java.lang.Object value)
    {
      this.shape = value;
      return value;
    }
    public java.lang.Object setPosition(java.lang.Object value)
    {
      this.position = value;
      return value;
    }
    public java.lang.Object setNormal(java.lang.Object value)
    {
      this.normal = value;
      return value;
    }
    public java.lang.Object setColor(java.lang.Object value)
    {
      this.color = value;
      return value;
    }
    public java.lang.Object setDistance(java.lang.Object value)
    {
      this.distance = value;
      return value;
    }
    public static tracer.IntersectionInfo_interface _new(dart._runtime.types.simple.Type type)
    {
      tracer.IntersectionInfo_interface result;
      result = new tracer.IntersectionInfo(((dart._runtime.helpers.ConstructorHelper.EmptyConstructorMarker) null), type);
      result._constructor();
      return result;
    }
}
