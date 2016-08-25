package tracer;

public class Plane extends tracer.BaseShape
{
    public static dart._runtime.types.simple.InterfaceTypeInfo dart2java$typeInfo = new dart._runtime.types.simple.InterfaceTypeInfo("file:///usr/local/google/home/andrewkrieger/ddc-java/gen/codegen_tests/tracer.dart", "Plane");
    static {
      tracer.Plane.dart2java$typeInfo.superclass = new dart._runtime.types.simple.InterfaceTypeExpr(tracer.BaseShape.dart2java$typeInfo);
    }
    public java.lang.Object d;
  
    public Plane(dart._runtime.types.simple.Type type, java.lang.Object pos, java.lang.Object d, java.lang.Object material)
    {
      super((dart._runtime.helpers.ConstructorHelper.EmptyConstructorMarker) null, type);
      this._constructor(pos, d, material);
    }
    public Plane(dart._runtime.helpers.ConstructorHelper.EmptyConstructorMarker arg, dart._runtime.types.simple.Type type)
    {
      super(arg, type);
    }
  
    protected void _constructor(java.lang.Object pos, java.lang.Object d, java.lang.Object material)
    {
      final dart._runtime.types.simple.TypeEnvironment dart2java$localTypeEnv = this.dart2java$type.env;
      this.d = d;
      super._constructor(pos, material);
    }
    public tracer.IntersectionInfo intersect(tracer.Ray ray)
    {
      final dart._runtime.types.simple.TypeEnvironment dart2java$localTypeEnv = this.dart2java$type.env;
      tracer.IntersectionInfo info = new tracer.IntersectionInfo(dart2java$localTypeEnv.evaluate(new dart._runtime.types.simple.InterfaceTypeExpr(tracer.IntersectionInfo.dart2java$typeInfo)));
      java.lang.Object Vd = dart._runtime.helpers.DynamicHelper.invoke("dot", this.getPosition(), ray.getDirection());
      if (dart._runtime.helpers.ObjectHelper.operatorEqual(Vd, 0))
      {
        return info;
      }
      java.lang.Object t = dart._runtime.helpers.DynamicHelper.invoke("operatorDivide", dart._runtime.helpers.DynamicHelper.invoke("operatorUnaryMinus", dart._runtime.helpers.DynamicHelper.invoke("operatorPlus", dart._runtime.helpers.DynamicHelper.invoke("dot", this.getPosition(), ray.getPosition()), this.getD())), Vd);
      if ((java.lang.Boolean) dart._runtime.helpers.DynamicHelper.invoke("operatorLessEqual", t, 0))
      {
        return info;
      }
      info.setShape(this);
      info.setIsHit(true);
      info.setPosition(dart._runtime.helpers.DynamicHelper.invoke("operatorPlus", ray.getPosition(), dart._runtime.helpers.DynamicHelper.invoke("multiplyScalar", ray.getDirection(), t)));
      info.setNormal(this.getPosition());
      info.setDistance(t);
      if ((java.lang.Boolean) dart._runtime.helpers.DynamicHelper.invoke("getHasTexture", this.getMaterial()))
      {
        tracer.Vector vU = new tracer.Vector(dart2java$localTypeEnv.evaluate(new dart._runtime.types.simple.InterfaceTypeExpr(tracer.Vector.dart2java$typeInfo)), (java.lang.Double) dart._runtime.helpers.DynamicHelper.invoke("getY", this.getPosition()), (java.lang.Double) dart._runtime.helpers.DynamicHelper.invoke("getZ", this.getPosition()), (java.lang.Double) dart._runtime.helpers.DynamicHelper.invoke("operatorUnaryMinus", dart._runtime.helpers.DynamicHelper.invoke("getX", this.getPosition())));
        tracer.Vector vV = vU.cross((tracer.Vector) this.getPosition());
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
    public java.lang.Object getD()
    {
      return this.d;
    }
}
