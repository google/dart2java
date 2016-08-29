package tracer;

public interface Vector_interface extends dart.core.Object_interface
{
  tracer.Vector_interface normalize();
  tracer.Vector_interface negateY();
  java.lang.Double magnitude();
  tracer.Vector_interface cross(tracer.Vector_interface w);
  java.lang.Double dot(tracer.Vector_interface w);
  tracer.Vector_interface operatorPlus(tracer.Vector_interface w);
  tracer.Vector_interface operatorMinus(tracer.Vector_interface w);
  tracer.Vector_interface operatorStar(tracer.Vector_interface w);
  tracer.Vector_interface multiplyScalar(java.lang.Double w);
  java.lang.String toString();
  java.lang.Double getX();
  java.lang.Double getY();
  java.lang.Double getZ();
}
