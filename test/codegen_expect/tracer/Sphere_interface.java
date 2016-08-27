package tracer;

public interface Sphere_interface extends tracer.BaseShape_interface
{
  tracer.IntersectionInfo intersect(tracer.Ray ray);
  java.lang.String toString();
}
