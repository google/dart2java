package tracer;

public interface Vector_interface extends dart.core.Object_interface
{
  tracer.Vector normalize();
  tracer.Vector negateY();
  java.lang.Double magnitude();
  tracer.Vector cross(tracer.Vector w);
  java.lang.Double dot(tracer.Vector w);
  tracer.Vector operatorPlus(tracer.Vector w);
  tracer.Vector operatorMinus(tracer.Vector w);
  tracer.Vector operatorStar(tracer.Vector w);
  tracer.Vector multiplyScalar(java.lang.Double w);
  java.lang.String toString();
}
