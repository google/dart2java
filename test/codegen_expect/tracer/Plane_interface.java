package tracer;

public interface Plane_interface extends tracer.BaseShape_interface
{
  tracer.IntersectionInfo intersect(tracer.Ray ray);
  java.lang.String toString();
}
