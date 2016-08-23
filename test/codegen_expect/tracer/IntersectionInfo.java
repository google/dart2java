package tracer;

public class IntersectionInfo extends dart._runtime.base.DartObject
{
    public static dart._runtime.types.simple.InterfaceTypeInfo dart2java$typeInfo = new dart._runtime.types.simple.InterfaceTypeInfo("file:///usr/local/google/home/springerm/ddc-java/gen/codegen_tests/tracer.dart", "IntersectionInfo");
    static {
      tracer.IntersectionInfo.dart2java$typeInfo.superclass = new dart._runtime.types.simple.InterfaceTypeExpr(dart._runtime.helpers.ObjectHelper.dart2java$typeInfo);
    }
    public java.lang.Boolean isHit;
    public int hitCount;
    public java.lang.Object shape;
    public java.lang.Object position;
    public java.lang.Object normal;
    public java.lang.Object color;
    public java.lang.Object distance;
  
    public IntersectionInfo()
    {
      super((dart._runtime.helpers.ConstructorHelper.EmptyConstructorMarker) null);
      this._constructor();
    }
    public IntersectionInfo(dart._runtime.helpers.ConstructorHelper.EmptyConstructorMarker arg)
    {
      super(arg);
    }
  
    protected void _constructor()
    {
      this.isHit = false;
      this.hitCount = 0;
      super._constructor();
      this.setColor(new tracer.Color(0.0, 0.0, 0.0));
    }
    public java.lang.String toString()
    {
      return (("Intersection [" + this.getPosition().toString()) + "]");
    }
    public java.lang.Boolean getIsHit()
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
    public java.lang.Boolean setIsHit(java.lang.Boolean value)
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
}
