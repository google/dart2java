package tracer;

public interface Sphere_interface extends tracer.BaseShape_interface
{
  tracer.IntersectionInfo_interface intersect(tracer.Ray_interface ray);
  java.lang.String toString();
  double getRadius();
}
