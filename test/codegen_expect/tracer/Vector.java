package tracer;

public class Vector extends dart._runtime.base.DartObject
{
    public static dart._runtime.types.simple.InterfaceTypeInfo dart2java$typeInfo = new dart._runtime.types.simple.InterfaceTypeInfo("tracer.Vector");
    static {
      tracer.Vector.dart2java$typeInfo.superclass = new dart._runtime.types.simple.InterfaceTypeExpr(dart._runtime.helpers.ObjectHelper.dart2java$typeInfo);
    }
    public java.lang.Double x;
    public java.lang.Double y;
    public java.lang.Double z;
  
    public Vector(dart._runtime.types.simple.Type type, java.lang.Double x, java.lang.Double y, java.lang.Double z)
    {
      super((dart._runtime.helpers.ConstructorHelper.EmptyConstructorMarker) null, type);
      this._constructor(x, y, z);
    }
    public Vector(dart._runtime.helpers.ConstructorHelper.EmptyConstructorMarker arg, dart._runtime.types.simple.Type type)
    {
      super(arg, type);
    }
  
    protected void _constructor(java.lang.Double x, java.lang.Double y, java.lang.Double z)
    {
      final dart._runtime.types.simple.TypeEnvironment dart2java$localTypeEnv = this.dart2java$type.env;
      this.x = x;
      this.y = y;
      this.z = z;
      super._constructor();
    }
    public tracer.Vector normalize()
    {
      final dart._runtime.types.simple.TypeEnvironment dart2java$localTypeEnv = this.dart2java$type.env;
      java.lang.Double m = this.magnitude();
      return new tracer.Vector(dart2java$localTypeEnv.evaluate(new dart._runtime.types.simple.InterfaceTypeExpr(tracer.Vector.dart2java$typeInfo)), dart._runtime.helpers.DoubleHelper.operatorDivide(this.getX(), m), dart._runtime.helpers.DoubleHelper.operatorDivide(this.getY(), m), dart._runtime.helpers.DoubleHelper.operatorDivide(this.getZ(), m));
    }
    public tracer.Vector negateY()
    {
      final dart._runtime.types.simple.TypeEnvironment dart2java$localTypeEnv = this.dart2java$type.env;
      return new tracer.Vector(dart2java$localTypeEnv.evaluate(new dart._runtime.types.simple.InterfaceTypeExpr(tracer.Vector.dart2java$typeInfo)), this.getX(), dart._runtime.helpers.DoubleHelper.operatorUnaryMinus(this.getY()), this.getZ());
    }
    public java.lang.Double magnitude()
    {
      final dart._runtime.types.simple.TypeEnvironment dart2java$localTypeEnv = this.dart2java$type.env;
      return dart.math.__TopLevel.sqrt(dart._runtime.helpers.DoubleHelper.operatorPlus(dart._runtime.helpers.DoubleHelper.operatorPlus(dart._runtime.helpers.DoubleHelper.operatorStar(this.getX(), this.getX()), dart._runtime.helpers.DoubleHelper.operatorStar(this.getY(), this.getY())), dart._runtime.helpers.DoubleHelper.operatorStar(this.getZ(), this.getZ())));
    }
    public tracer.Vector cross(tracer.Vector w)
    {
      final dart._runtime.types.simple.TypeEnvironment dart2java$localTypeEnv = this.dart2java$type.env;
      return new tracer.Vector(dart2java$localTypeEnv.evaluate(new dart._runtime.types.simple.InterfaceTypeExpr(tracer.Vector.dart2java$typeInfo)), dart._runtime.helpers.DoubleHelper.operatorPlus(dart._runtime.helpers.DoubleHelper.operatorStar(dart._runtime.helpers.DoubleHelper.operatorUnaryMinus(this.getZ()), w.getY()), dart._runtime.helpers.DoubleHelper.operatorStar(this.getY(), w.getZ())), dart._runtime.helpers.DoubleHelper.operatorMinus(dart._runtime.helpers.DoubleHelper.operatorStar(this.getZ(), w.getX()), dart._runtime.helpers.DoubleHelper.operatorStar(this.getX(), w.getZ())), dart._runtime.helpers.DoubleHelper.operatorPlus(dart._runtime.helpers.DoubleHelper.operatorStar(dart._runtime.helpers.DoubleHelper.operatorUnaryMinus(this.getY()), w.getX()), dart._runtime.helpers.DoubleHelper.operatorStar(this.getX(), w.getY())));
    }
    public java.lang.Double dot(tracer.Vector w)
    {
      final dart._runtime.types.simple.TypeEnvironment dart2java$localTypeEnv = this.dart2java$type.env;
      return dart._runtime.helpers.DoubleHelper.operatorPlus(dart._runtime.helpers.DoubleHelper.operatorPlus(dart._runtime.helpers.DoubleHelper.operatorStar(this.getX(), w.getX()), dart._runtime.helpers.DoubleHelper.operatorStar(this.getY(), w.getY())), dart._runtime.helpers.DoubleHelper.operatorStar(this.getZ(), w.getZ()));
    }
    public tracer.Vector operatorPlus(tracer.Vector w)
    {
      final dart._runtime.types.simple.TypeEnvironment dart2java$localTypeEnv = this.dart2java$type.env;
      return new tracer.Vector(dart2java$localTypeEnv.evaluate(new dart._runtime.types.simple.InterfaceTypeExpr(tracer.Vector.dart2java$typeInfo)), dart._runtime.helpers.DoubleHelper.operatorPlus(w.getX(), this.getX()), dart._runtime.helpers.DoubleHelper.operatorPlus(w.getY(), this.getY()), dart._runtime.helpers.DoubleHelper.operatorPlus(w.getZ(), this.getZ()));
    }
    public tracer.Vector operatorMinus(tracer.Vector w)
    {
      final dart._runtime.types.simple.TypeEnvironment dart2java$localTypeEnv = this.dart2java$type.env;
      return new tracer.Vector(dart2java$localTypeEnv.evaluate(new dart._runtime.types.simple.InterfaceTypeExpr(tracer.Vector.dart2java$typeInfo)), dart._runtime.helpers.DoubleHelper.operatorMinus(this.getX(), w.getX()), dart._runtime.helpers.DoubleHelper.operatorMinus(this.getY(), w.getY()), dart._runtime.helpers.DoubleHelper.operatorMinus(this.getZ(), w.getZ()));
    }
    public tracer.Vector operatorStar(tracer.Vector w)
    {
      final dart._runtime.types.simple.TypeEnvironment dart2java$localTypeEnv = this.dart2java$type.env;
      return new tracer.Vector(dart2java$localTypeEnv.evaluate(new dart._runtime.types.simple.InterfaceTypeExpr(tracer.Vector.dart2java$typeInfo)), dart._runtime.helpers.DoubleHelper.operatorStar(this.getX(), w.getX()), dart._runtime.helpers.DoubleHelper.operatorStar(this.getY(), w.getY()), dart._runtime.helpers.DoubleHelper.operatorStar(this.getZ(), w.getZ()));
    }
    public tracer.Vector multiplyScalar(java.lang.Double w)
    {
      final dart._runtime.types.simple.TypeEnvironment dart2java$localTypeEnv = this.dart2java$type.env;
      return new tracer.Vector(dart2java$localTypeEnv.evaluate(new dart._runtime.types.simple.InterfaceTypeExpr(tracer.Vector.dart2java$typeInfo)), dart._runtime.helpers.DoubleHelper.operatorStar(this.getX(), w), dart._runtime.helpers.DoubleHelper.operatorStar(this.getY(), w), dart._runtime.helpers.DoubleHelper.operatorStar(this.getZ(), w));
    }
    public java.lang.String toString()
    {
      final dart._runtime.types.simple.TypeEnvironment dart2java$localTypeEnv = this.dart2java$type.env;
      return (((((("Vector [" + this.getX().toString()) + ", ") + this.getY().toString()) + " ,") + this.getZ().toString()) + " ]");
    }
    public java.lang.Double getX()
    {
      return this.x;
    }
    public java.lang.Double getY()
    {
      return this.y;
    }
    public java.lang.Double getZ()
    {
      return this.z;
    }
}
