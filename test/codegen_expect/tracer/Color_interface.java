package tracer;

public interface Color_interface extends dart._runtime.base.DartObject_interface
{
  tracer.Color_interface limit();
  tracer.Color_interface operatorPlus(tracer.Color_interface c2);
  tracer.Color_interface addScalar(double s);
  tracer.Color_interface operatorStar(tracer.Color_interface c2);
  tracer.Color_interface multiplyScalar(double f);
  tracer.Color_interface blend(tracer.Color_interface c2, double w);
  int brightness();
  java.lang.String toString();
  double getRed();
  double getGreen();
  double getBlue();

}
