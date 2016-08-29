package tracer;

public interface Camera_interface extends dart.core.Object_interface
{
  tracer.Ray_interface getRay(double vx, double vy);
  java.lang.String toString();
  java.lang.Object getPosition();
  java.lang.Object getLookAt();
  java.lang.Object getUp();
  java.lang.Object getEquator();
  java.lang.Object getScreen();
  java.lang.Object setEquator(java.lang.Object value);
  java.lang.Object setScreen(java.lang.Object value);
}
