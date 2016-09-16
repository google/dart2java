package tracer;

public interface Vector_interface extends dart._runtime.base.DartObject_interface
{
  tracer.Vector_interface normalize();
  tracer.Vector_interface negateY();
  double magnitude();
  tracer.Vector_interface cross(tracer.Vector_interface w);
  double dot(tracer.Vector_interface w);
  tracer.Vector_interface operatorPlus(tracer.Vector_interface w);
  tracer.Vector_interface operatorMinus(tracer.Vector_interface w);
  tracer.Vector_interface operatorStar(tracer.Vector_interface w);
  tracer.Vector_interface multiplyScalar(double w);
  java.lang.String toString();
  double getX();
  double getY();
  double getZ();
  void _constructor(double x, double y, double z);
}
