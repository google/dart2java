package tracer;

public interface Color_interface extends dart.core.Object_interface
{
  tracer.Color_interface limit();
  tracer.Color_interface operatorPlus(tracer.Color_interface c2);
  tracer.Color_interface addScalar(java.lang.Double s);
  tracer.Color_interface operatorStar(tracer.Color_interface c2);
  tracer.Color_interface multiplyScalar(java.lang.Double f);
  tracer.Color_interface blend(tracer.Color_interface c2, java.lang.Double w);
  int brightness();
  java.lang.String toString();
  java.lang.Double getRed();
  java.lang.Double getGreen();
  java.lang.Double getBlue();
}
