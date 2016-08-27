package tracer;

public interface Color_interface extends dart.core.Object_interface
{
  tracer.Color limit();
  tracer.Color operatorPlus(tracer.Color c2);
  tracer.Color addScalar(java.lang.Double s);
  tracer.Color operatorStar(tracer.Color c2);
  tracer.Color multiplyScalar(java.lang.Double f);
  tracer.Color blend(tracer.Color c2, java.lang.Double w);
  int brightness();
  java.lang.String toString();
}
