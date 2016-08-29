package tracer;

public class Vector extends dart._runtime.base.DartObject implements tracer.Vector_interface
{
    public static dart._runtime.types.simple.InterfaceTypeInfo dart2java$typeInfo = new dart._runtime.types.simple.InterfaceTypeInfo("tracer.Vector");
    static {
      tracer.Vector.dart2java$typeInfo.superclass = new dart._runtime.types.simple.InterfaceTypeExpr(dart._runtime.helpers.ObjectHelper.dart2java$typeInfo);
    }
    public double x;
    public double y;
    public double z;
  
    public Vector(dart._runtime.types.simple.Type type, double x, double y, double z)
    {
      super((dart._runtime.helpers.ConstructorHelper.EmptyConstructorMarker) null, type);
      this._constructor(x, y, z);
    }
    public Vector(dart._runtime.helpers.ConstructorHelper.EmptyConstructorMarker arg, dart._runtime.types.simple.Type type)
    {
      super(arg, type);
    }
  
    protected void _constructor(double x, double y, double z)
    {
      final dart._runtime.types.simple.TypeEnvironment dart2java$localTypeEnv = this.dart2java$type.env;
      this.x = x;
      this.y = y;
      this.z = z;
      super._constructor();
    }
    public tracer.Vector_interface normalize()
    {
      final dart._runtime.types.simple.TypeEnvironment dart2java$localTypeEnv = this.dart2java$type.env;
      double m = this.magnitude();
      return new tracer.Vector(dart2java$localTypeEnv.evaluate(new dart._runtime.types.simple.InterfaceTypeExpr(tracer.Vector.dart2java$typeInfo)), (this.getX() / m), (this.getY() / m), (this.getZ() / m));
    }
    public tracer.Vector_interface negateY()
    {
      final dart._runtime.types.simple.TypeEnvironment dart2java$localTypeEnv = this.dart2java$type.env;
      return new tracer.Vector(dart2java$localTypeEnv.evaluate(new dart._runtime.types.simple.InterfaceTypeExpr(tracer.Vector.dart2java$typeInfo)), this.getX(), (-this.getY()), this.getZ());
    }
    public double magnitude()
    {
      final dart._runtime.types.simple.TypeEnvironment dart2java$localTypeEnv = this.dart2java$type.env;
      return dart.math.__TopLevel.sqrt((((this.getX() * this.getX()) + (this.getY() * this.getY())) + (this.getZ() * this.getZ())));
    }
    public tracer.Vector_interface cross(tracer.Vector_interface w)
    {
      final dart._runtime.types.simple.TypeEnvironment dart2java$localTypeEnv = this.dart2java$type.env;
      return new tracer.Vector(dart2java$localTypeEnv.evaluate(new dart._runtime.types.simple.InterfaceTypeExpr(tracer.Vector.dart2java$typeInfo)), (((-this.getZ()) * w.getY()) + (this.getY() * w.getZ())), ((this.getZ() * w.getX()) - (this.getX() * w.getZ())), (((-this.getY()) * w.getX()) + (this.getX() * w.getY())));
    }
    public double dot(tracer.Vector_interface w)
    {
      final dart._runtime.types.simple.TypeEnvironment dart2java$localTypeEnv = this.dart2java$type.env;
      return (((this.getX() * w.getX()) + (this.getY() * w.getY())) + (this.getZ() * w.getZ()));
    }
    public tracer.Vector_interface operatorPlus(tracer.Vector_interface w)
    {
      final dart._runtime.types.simple.TypeEnvironment dart2java$localTypeEnv = this.dart2java$type.env;
      return new tracer.Vector(dart2java$localTypeEnv.evaluate(new dart._runtime.types.simple.InterfaceTypeExpr(tracer.Vector.dart2java$typeInfo)), (w.getX() + this.getX()), (w.getY() + this.getY()), (w.getZ() + this.getZ()));
    }
    public tracer.Vector_interface operatorMinus(tracer.Vector_interface w)
    {
      final dart._runtime.types.simple.TypeEnvironment dart2java$localTypeEnv = this.dart2java$type.env;
      return new tracer.Vector(dart2java$localTypeEnv.evaluate(new dart._runtime.types.simple.InterfaceTypeExpr(tracer.Vector.dart2java$typeInfo)), (this.getX() - w.getX()), (this.getY() - w.getY()), (this.getZ() - w.getZ()));
    }
    public tracer.Vector_interface operatorStar(tracer.Vector_interface w)
    {
      final dart._runtime.types.simple.TypeEnvironment dart2java$localTypeEnv = this.dart2java$type.env;
      return new tracer.Vector(dart2java$localTypeEnv.evaluate(new dart._runtime.types.simple.InterfaceTypeExpr(tracer.Vector.dart2java$typeInfo)), (this.getX() * w.getX()), (this.getY() * w.getY()), (this.getZ() * w.getZ()));
    }
    public tracer.Vector_interface multiplyScalar(double w)
    {
      final dart._runtime.types.simple.TypeEnvironment dart2java$localTypeEnv = this.dart2java$type.env;
      return new tracer.Vector(dart2java$localTypeEnv.evaluate(new dart._runtime.types.simple.InterfaceTypeExpr(tracer.Vector.dart2java$typeInfo)), (this.getX() * w), (this.getY() * w), (this.getZ() * w));
    }
    public java.lang.String toString()
    {
      final dart._runtime.types.simple.TypeEnvironment dart2java$localTypeEnv = this.dart2java$type.env;
      return (((((("Vector [" + this.getX()) + ", ") + this.getY()) + " ,") + this.getZ()) + " ]");
    }
    public double getX()
    {
      return this.x;
    }
    public double getY()
    {
      return this.y;
    }
    public double getZ()
    {
      return this.z;
    }
}
