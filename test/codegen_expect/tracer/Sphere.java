package tracer;

public class Sphere extends tracer.BaseShape implements tracer.Sphere_interface
{
    public static dart._runtime.types.simple.InterfaceTypeInfo dart2java$typeInfo = new dart._runtime.types.simple.InterfaceTypeInfo(tracer.Sphere.class, tracer.Sphere_interface.class);
    private static dart._runtime.types.simple.InterfaceTypeExpr dart2java$typeExpr_IntersectionInfo = new dart._runtime.types.simple.InterfaceTypeExpr(tracer.IntersectionInfo.dart2java$typeInfo);
    private static dart._runtime.types.simple.InterfaceTypeExpr dart2java$typeExpr_bool = new dart._runtime.types.simple.InterfaceTypeExpr(dart._runtime.helpers.BoolHelper.dart2java$typeInfo);
    private static dart._runtime.types.simple.InterfaceTypeExpr dart2java$typeExpr_num = new dart._runtime.types.simple.InterfaceTypeExpr(dart._runtime.helpers.NumberHelper.dart2java$typeInfo);
    private static dart._runtime.types.simple.InterfaceTypeExpr dart2java$typeExpr_BaseShape = new dart._runtime.types.simple.InterfaceTypeExpr(tracer.BaseShape.dart2java$typeInfo);
    static {
      tracer.Sphere.dart2java$typeInfo.superclass = dart2java$typeExpr_BaseShape;
    }
    public double radius;
  
    public Sphere(dart._runtime.helpers.ConstructorHelper.EmptyConstructorMarker arg, dart._runtime.types.simple.Type type)
    {
      super(arg, type);
    }
  
    public tracer.IntersectionInfo_interface intersect(tracer.Ray_interface ray)
    {
      final dart._runtime.types.simple.TypeEnvironment dart2java$localTypeEnv = this.dart2java$type.env;
      tracer.IntersectionInfo_interface info = tracer.IntersectionInfo._new(dart2java$localTypeEnv.evaluate(dart2java$typeExpr_IntersectionInfo));
      info.setShape(this);
      java.lang.Object dst = dart._runtime.helpers.DynamicHelper.invoke("operatorMinus", ray.getPosition(), this.getPosition());
      java.lang.Object B = dart._runtime.helpers.DynamicHelper.invoke("dot", dst, ray.getDirection());
      java.lang.Object C = dart._runtime.helpers.DynamicHelper.invoke("operatorMinus", dart._runtime.helpers.DynamicHelper.invoke("dot", dst, dst), (this.getRadius() * this.getRadius()));
      java.lang.Object D = dart._runtime.helpers.DynamicHelper.invoke("operatorMinus", dart._runtime.helpers.DynamicHelper.invoke("operatorStar", B, B), C);
      if (((boolean) dart2java$localTypeEnv.evaluate(dart2java$typeExpr_bool).cast(dart._runtime.helpers.DynamicHelper.invoke("operatorGreater", D, 0))))
      {
        info.setIsHit(true);
        info.setDistance(dart._runtime.helpers.DynamicHelper.invoke("operatorMinus", dart._runtime.helpers.DynamicHelper.invoke("operatorUnaryMinus", B), dart.math.__TopLevel.sqrt(((java.lang.Number) dart2java$localTypeEnv.evaluate(dart2java$typeExpr_num).check(D)))));
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
      final dart._runtime.types.simple.TypeEnvironment dart2java$localTypeEnv = this.dart2java$type.env;
      return (((("Sphere [position=" + this.getPosition().toString()) + ", radius=") + this.getRadius()) + "]");
    }
    public void _constructor(java.lang.Object pos, double radius, java.lang.Object material)
    {
      final dart._runtime.types.simple.TypeEnvironment dart2java$localTypeEnv = this.dart2java$type.env;
      this.radius = radius;
      super._constructor(pos, material);
    }
    public double getRadius()
    {
      return this.radius;
    }
    public static tracer.Sphere_interface _new(dart._runtime.types.simple.Type type, java.lang.Object pos, double radius, java.lang.Object material)
    {
      tracer.Sphere_interface result;
      result = new tracer.Sphere(((dart._runtime.helpers.ConstructorHelper.EmptyConstructorMarker) null), type);
      result._constructor(pos, radius, material);
      return result;
    }
}
