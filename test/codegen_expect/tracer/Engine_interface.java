package tracer;

public interface Engine_interface extends dart.core.Object_interface
{
  void setPixel(int x, int y, tracer.Color color);
  void renderScene(tracer.Scene scene, java.lang.Object canvas);
  tracer.Color getPixelColor(tracer.Ray ray, tracer.Scene scene);
  tracer.IntersectionInfo testIntersection(tracer.Ray ray, tracer.Scene scene, tracer.BaseShape exclude);
  tracer.Ray getReflectionRay(tracer.Vector P, tracer.Vector N, tracer.Vector V);
  tracer.Color rayTrace(tracer.IntersectionInfo info, tracer.Ray ray, tracer.Scene scene, int depth);
  java.lang.String toString();
}
