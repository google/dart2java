package tracer;

public class Sphere extends tracer.BaseShape
{
    public static dart._runtime.types.simple.InterfaceTypeInfo dart2java$typeInfo = new dart._runtime.types.simple.InterfaceTypeInfo("file:///usr/local/google/home/springerm/ddc-java/gen/codegen_tests/tracer.dart", "Sphere");
    static {
      tracer.Sphere.dart2java$typeInfo.superclass = new dart._runtime.types.simple.InterfaceTypeExpr(tracer.BaseShape.dart2java$typeInfo);
    }
    public java.lang.Double radius;
  
    public Sphere(java.lang.Object pos, java.lang.Double radius, java.lang.Object material)
    {
      super((dart._runtime.helpers.ConstructorHelper.EmptyConstructorMarker) null);
      this._constructor(pos, radius, material);
    }
    public Sphere(dart._runtime.helpers.ConstructorHelper.EmptyConstructorMarker arg)
    {
      super(arg);
    }
  
    protected void _constructor(java.lang.Object pos, java.lang.Double radius, java.lang.Object material)
    {
      this.radius = radius;
      super._constructor(pos, material);
    }
    public tracer.IntersectionInfo intersect(tracer.Ray ray)
    {
      tracer.IntersectionInfo info = new tracer.IntersectionInfo();
      info.setShape(this);
      java.lang.Object dst = dart._runtime.helpers.DynamicHelper.invoke("operatorMinus", ray.getPosition(), this.getPosition());
      java.lang.Object B = dart._runtime.helpers.DynamicHelper.invoke("dot", dst, ray.getDirection());
      java.lang.Object C = dart._runtime.helpers.DynamicHelper.invoke("operatorMinus", dart._runtime.helpers.DynamicHelper.invoke("dot", dst, dst), dart._runtime.helpers.DoubleHelper.operatorStar(this.getRadius(), this.getRadius()));
      java.lang.Object D = dart._runtime.helpers.DynamicHelper.invoke("operatorMinus", dart._runtime.helpers.DynamicHelper.invoke("operatorStar", B, B), C);
      if ((java.lang.Boolean) dart._runtime.helpers.DynamicHelper.invoke("operatorGreater", D, 0))
      {
        info.setIsHit(true);
        info.setDistance(dart._runtime.helpers.DynamicHelper.invoke("operatorMinus", dart._runtime.helpers.DynamicHelper.invoke("operatorUnaryMinus", B), dart.math.__TopLevel.sqrt((java.lang.Number) D)));
        info.setPosition(dart._runtime.helpers.DynamicHelper.invoke("operatorPlus", ray.getPosition(), dart._runtime.helpers.DynamicHelper.invoke("multiplyScalar", ray.getDirection(), info.getDistance())));
        info.setNormal(dart._runtime.helpers.DynamicHelper.invoke("normalize", dart._runtime.helpers.DynamicHelper.invoke("operatorMinus", info.getPosition(), this.getPosition())));
        info.setColor(dart._runtime.helpers.DynamicHelper.invoke("getColor_", this.getMaterial(), 0, 0));
      }
      else
      {
        info.setIsHit(false);
      }
      return info;
    }
    public java.lang.String toString()
    {
      return (((("Sphere [position=" + this.getPosition().toString()) + ", radius=") + this.getRadius().toString()) + "]");
    }
    public java.lang.Double getRadius()
    {
      return this.radius;
    }
}
