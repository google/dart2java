package tracer;

public class Plane extends tracer.BaseShape implements tracer.Plane_interface
{
    public static dart._runtime.types.simple.InterfaceTypeInfo dart2java$typeInfo = new dart._runtime.types.simple.InterfaceTypeInfo(tracer.Plane.class, tracer.Plane_interface.class);
    private static dart._runtime.types.simple.InterfaceTypeExpr dart2java$typeExpr_IntersectionInfo = new dart._runtime.types.simple.InterfaceTypeExpr(tracer.IntersectionInfo.dart2java$typeInfo);
    private static dart._runtime.types.simple.InterfaceTypeExpr dart2java$typeExpr_bool = new dart._runtime.types.simple.InterfaceTypeExpr(dart._runtime.helpers.BoolHelper.dart2java$typeInfo);
    private static dart._runtime.types.simple.InterfaceTypeExpr dart2java$typeExpr_double$0 = new dart._runtime.types.simple.InterfaceTypeExpr(dart._runtime.helpers.DoubleHelper.dart2java$typeInfo);
    private static dart._runtime.types.simple.InterfaceTypeExpr dart2java$typeExpr_Vector = new dart._runtime.types.simple.InterfaceTypeExpr(tracer.Vector.dart2java$typeInfo);
    private static dart._runtime.types.simple.InterfaceTypeExpr dart2java$typeExpr_BaseShape = new dart._runtime.types.simple.InterfaceTypeExpr(tracer.BaseShape.dart2java$typeInfo);
    static {
      tracer.Plane.dart2java$typeInfo.superclass = dart2java$typeExpr_BaseShape;
    }
    public java.lang.Object d;
  
    public Plane(dart._runtime.helpers.ConstructorHelper.EmptyConstructorMarker arg, dart._runtime.types.simple.Type type)
    {
      super(arg, type);
    }
  
    public tracer.IntersectionInfo_interface intersect(tracer.Ray_interface ray)
    {
      final dart._runtime.types.simple.TypeEnvironment dart2java$localTypeEnv = this.dart2java$type.env;
      tracer.IntersectionInfo_interface info = tracer.IntersectionInfo._new(dart2java$localTypeEnv.evaluate(dart2java$typeExpr_IntersectionInfo));
      java.lang.Object Vd = dart._runtime.helpers.DynamicHelper.invoke("dot", this.getPosition(), ray.getDirection());
      if (dart._runtime.helpers.ObjectHelper.operatorEqual(Vd, 0))
      {
        return info;
      }
      java.lang.Object t = dart._runtime.helpers.DynamicHelper.invoke("operatorDivide", dart._runtime.helpers.DynamicHelper.invoke("operatorUnaryMinus", dart._runtime.helpers.DynamicHelper.invoke("operatorPlus", dart._runtime.helpers.DynamicHelper.invoke("dot", this.getPosition(), ray.getPosition()), this.getD())), Vd);
      if (((boolean) dart2java$localTypeEnv.evaluate(dart2java$typeExpr_bool).cast(dart._runtime.helpers.DynamicHelper.invoke("operatorLessEqual", t, 0))))
      {
        return info;
      }
      info.setShape(this);
      info.setIsHit(true);
      info.setPosition(dart._runtime.helpers.DynamicHelper.invoke("operatorPlus", ray.getPosition(), dart._runtime.helpers.DynamicHelper.invoke("multiplyScalar", ray.getDirection(), t)));
      info.setNormal(this.getPosition());
      info.setDistance(t);
      if (((boolean) dart2java$localTypeEnv.evaluate(dart2java$typeExpr_bool).cast(dart._runtime.helpers.DynamicHelper.invoke("getHasTexture", this.getMaterial()))))
      {
        tracer.Vector_interface vU = tracer.Vector._new(dart2java$localTypeEnv.evaluate(dart2java$typeExpr_Vector), ((double) dart2java$localTypeEnv.evaluate(dart2java$typeExpr_double$0).check(dart._runtime.helpers.DynamicHelper.invoke("getY", this.getPosition()))), ((double) dart2java$localTypeEnv.evaluate(dart2java$typeExpr_double$0).check(dart._runtime.helpers.DynamicHelper.invoke("getZ", this.getPosition()))), ((double) dart2java$localTypeEnv.evaluate(dart2java$typeExpr_double$0).check(dart._runtime.helpers.DynamicHelper.invoke("operatorUnaryMinus", dart._runtime.helpers.DynamicHelper.invoke("getX", this.getPosition())))));
        tracer.Vector_interface vV = vU.cross(((tracer.Vector_interface) dart2java$localTypeEnv.evaluate(dart2java$typeExpr_Vector).check(this.getPosition())));
        java.lang.Object u = dart._runtime.helpers.DynamicHelper.invoke("dot", info.getPosition(), vU);
        java.lang.Object v = dart._runtime.helpers.DynamicHelper.invoke("dot", info.getPosition(), vV);
        info.setColor(dart._runtime.helpers.DynamicHelper.invoke("getColor_", this.getMaterial(), u, v));
      }
      else
      {
        info.setColor(dart._runtime.helpers.DynamicHelper.invoke("getColor_", this.getMaterial(), 0, 0));
      }
      return info;
    }
    public java.lang.String toString()
    {
      final dart._runtime.types.simple.TypeEnvironment dart2java$localTypeEnv = this.dart2java$type.env;
      return (((("Plane [" + this.getPosition().toString()) + ", d=") + this.getD().toString()) + "]");
    }
    public void _constructor(java.lang.Object pos, java.lang.Object d, java.lang.Object material)
    {
      final dart._runtime.types.simple.TypeEnvironment dart2java$localTypeEnv = this.dart2java$type.env;
      this.d = d;
      super._constructor(pos, material);
    }
    public java.lang.Object getD()
    {
      return this.d;
    }
    public static tracer.Plane_interface _new(dart._runtime.types.simple.Type type, java.lang.Object pos, java.lang.Object d, java.lang.Object material)
    {
      tracer.Plane result;
      result = new tracer.Plane(((dart._runtime.helpers.ConstructorHelper.EmptyConstructorMarker) null), type);
      result._constructor(pos, d, material);
      return result;
    }
}
