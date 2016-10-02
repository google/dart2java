package fluidmotion;

public interface Field_interface extends dart._runtime.base.DartObject_interface
{
  void setDensity(int x, int y, double d);
  double getDensity(int x, int y);
  void setVelocity(int x, int y, double xv, double yv);
  double getXVelocity(int x, int y);
  double getYVelocity(int x, int y);
  dart.core.List_interface<java.lang.Double> getDens();
  dart.core.List_interface<java.lang.Double> getU();
  dart.core.List_interface<java.lang.Double> getV();
  int getRowSize();

}
