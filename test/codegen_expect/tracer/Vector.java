package tracer;

public class Vector extends dart._runtime.base.DartObject
{
    public static dart._runtime.types.simple.InterfaceTypeInfo dart2java$typeInfo = new dart._runtime.types.simple.InterfaceTypeInfo("file:///usr/local/google/home/springerm/ddc-java/gen/codegen_tests/tracer.dart", "Vector");
    static {
      tracer.Vector.dart2java$typeInfo.superclass = new dart._runtime.types.simple.InterfaceTypeExpr(dart._runtime.helpers.ObjectHelper.dart2java$typeInfo);
    }
    public java.lang.Double x;
    public java.lang.Double y;
    public java.lang.Double z;
  
    public Vector(java.lang.Double x, java.lang.Double y, java.lang.Double z)
    {
      super((dart._runtime.helpers.ConstructorHelper.EmptyConstructorMarker) null);
      this._constructor(x, y, z);
    }
    public Vector(dart._runtime.helpers.ConstructorHelper.EmptyConstructorMarker arg)
    {
      super(arg);
    }
  
    protected void _constructor(java.lang.Double x, java.lang.Double y, java.lang.Double z)
    {
      this.x = x;
      this.y = y;
      this.z = z;
      super._constructor();
    }
    public tracer.Vector normalize()
    {
      java.lang.Double m = this.magnitude();
      return new tracer.Vector(dart._runtime.helpers.DoubleHelper.operatorDivide(this.getX(), m), dart._runtime.helpers.DoubleHelper.operatorDivide(this.getY(), m), dart._runtime.helpers.DoubleHelper.operatorDivide(this.getZ(), m));
    }
    public tracer.Vector negateY()
    {
      return new tracer.Vector(this.getX(), dart._runtime.helpers.DoubleHelper.operatorUnaryMinus(this.getY()), this.getZ());
    }
    public java.lang.Double magnitude()
    {
      return dart.math.__TopLevel.sqrt(dart._runtime.helpers.DoubleHelper.operatorPlus(dart._runtime.helpers.DoubleHelper.operatorPlus(dart._runtime.helpers.DoubleHelper.operatorStar(this.getX(), this.getX()), dart._runtime.helpers.DoubleHelper.operatorStar(this.getY(), this.getY())), dart._runtime.helpers.DoubleHelper.operatorStar(this.getZ(), this.getZ())));
    }
    public tracer.Vector cross(tracer.Vector w)
    {
      return new tracer.Vector(dart._runtime.helpers.DoubleHelper.operatorPlus(dart._runtime.helpers.DoubleHelper.operatorStar(dart._runtime.helpers.DoubleHelper.operatorUnaryMinus(this.getZ()), w.getY()), dart._runtime.helpers.DoubleHelper.operatorStar(this.getY(), w.getZ())), dart._runtime.helpers.DoubleHelper.operatorMinus(dart._runtime.helpers.DoubleHelper.operatorStar(this.getZ(), w.getX()), dart._runtime.helpers.DoubleHelper.operatorStar(this.getX(), w.getZ())), dart._runtime.helpers.DoubleHelper.operatorPlus(dart._runtime.helpers.DoubleHelper.operatorStar(dart._runtime.helpers.DoubleHelper.operatorUnaryMinus(this.getY()), w.getX()), dart._runtime.helpers.DoubleHelper.operatorStar(this.getX(), w.getY())));
    }
    public java.lang.Double dot(tracer.Vector w)
    {
      return dart._runtime.helpers.DoubleHelper.operatorPlus(dart._runtime.helpers.DoubleHelper.operatorPlus(dart._runtime.helpers.DoubleHelper.operatorStar(this.getX(), w.getX()), dart._runtime.helpers.DoubleHelper.operatorStar(this.getY(), w.getY())), dart._runtime.helpers.DoubleHelper.operatorStar(this.getZ(), w.getZ()));
    }
    public tracer.Vector operatorPlus(tracer.Vector w)
    {
      return new tracer.Vector(dart._runtime.helpers.DoubleHelper.operatorPlus(w.getX(), this.getX()), dart._runtime.helpers.DoubleHelper.operatorPlus(w.getY(), this.getY()), dart._runtime.helpers.DoubleHelper.operatorPlus(w.getZ(), this.getZ()));
    }
    public tracer.Vector operatorMinus(tracer.Vector w)
    {
      return new tracer.Vector(dart._runtime.helpers.DoubleHelper.operatorMinus(this.getX(), w.getX()), dart._runtime.helpers.DoubleHelper.operatorMinus(this.getY(), w.getY()), dart._runtime.helpers.DoubleHelper.operatorMinus(this.getZ(), w.getZ()));
    }
    public tracer.Vector operatorStar(tracer.Vector w)
    {
      return new tracer.Vector(dart._runtime.helpers.DoubleHelper.operatorStar(this.getX(), w.getX()), dart._runtime.helpers.DoubleHelper.operatorStar(this.getY(), w.getY()), dart._runtime.helpers.DoubleHelper.operatorStar(this.getZ(), w.getZ()));
    }
    public tracer.Vector multiplyScalar(java.lang.Double w)
    {
      return new tracer.Vector(dart._runtime.helpers.DoubleHelper.operatorStar(this.getX(), w), dart._runtime.helpers.DoubleHelper.operatorStar(this.getY(), w), dart._runtime.helpers.DoubleHelper.operatorStar(this.getZ(), w));
    }
    public java.lang.String toString()
    {
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
