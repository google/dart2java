package tracer;

public interface Plane_interface extends tracer.BaseShape_interface
{
  tracer.IntersectionInfo_interface intersect(tracer.Ray_interface ray);
  java.lang.String toString();
  java.lang.Object getD();
}
