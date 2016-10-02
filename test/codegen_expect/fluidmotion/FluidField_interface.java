package fluidmotion;

public interface FluidField_interface extends dart._runtime.base.DartObject_interface
{
  void validate(java.lang.Object expectedDens, java.lang.Object expectedU, java.lang.Object expectedV);
  void reset();
  void addFields(dart.core.List_interface<java.lang.Double> x, dart.core.List_interface<java.lang.Double> s, double dt);
  void set_bnd(int b, dart.core.List_interface<java.lang.Double> x);
  void lin_solve(int b, dart.core.List_interface<java.lang.Double> x, dart.core.List_interface<java.lang.Double> x0, int a, int c);
  void diffuse(int b, dart.core.List_interface<java.lang.Double> x, dart.core.List_interface<java.lang.Double> x0, double dt);
  void lin_solve2(dart.core.List_interface<java.lang.Double> x, dart.core.List_interface<java.lang.Double> x0, dart.core.List_interface<java.lang.Double> y, dart.core.List_interface<java.lang.Double> y0, int a, int c);
  void diffuse2(dart.core.List_interface<java.lang.Double> x, dart.core.List_interface<java.lang.Double> x0, java.lang.Object y, dart.core.List_interface<java.lang.Double> y0, double dt);
  void advect(int b, dart.core.List_interface<java.lang.Double> d, dart.core.List_interface<java.lang.Double> d0, dart.core.List_interface<java.lang.Double> u, dart.core.List_interface<java.lang.Double> v, double dt);
  void project(dart.core.List_interface<java.lang.Double> u, dart.core.List_interface<java.lang.Double> v, dart.core.List_interface<java.lang.Double> p, dart.core.List_interface<java.lang.Double> div);
  void dens_step(dart.core.List_interface<java.lang.Double> x, dart.core.List_interface<java.lang.Double> x0, dart.core.List_interface<java.lang.Double> u, dart.core.List_interface<java.lang.Double> v, double dt);
  void vel_step(dart.core.List_interface<java.lang.Double> u, dart.core.List_interface<java.lang.Double> v, dart.core.List_interface<java.lang.Double> u0, dart.core.List_interface<java.lang.Double> v0, double dt);
  void queryUI(dart.core.List_interface<java.lang.Double> d, dart.core.List_interface<java.lang.Double> u, dart.core.List_interface<java.lang.Double> v);
  void update();
  java.lang.Object getCanvas();
  int getIterations();
  double getDt();
  int getSize();
  dart.core.List_interface<java.lang.Double> getDens();
  dart.core.List_interface<java.lang.Double> getDens_prev();
  dart.core.List_interface<java.lang.Double> getU();
  dart.core.List_interface<java.lang.Double> getU_prev();
  dart.core.List_interface<java.lang.Double> getV();
  dart.core.List_interface<java.lang.Double> getV_prev();
  int getWidth();
  int getHeight();
  int getRowSize();
  dart.core.List_interface<java.lang.Double> setDens(dart.core.List_interface<java.lang.Double> value);
  dart.core.List_interface<java.lang.Double> setDens_prev(dart.core.List_interface<java.lang.Double> value);
  dart.core.List_interface<java.lang.Double> setU(dart.core.List_interface<java.lang.Double> value);
  dart.core.List_interface<java.lang.Double> setU_prev(dart.core.List_interface<java.lang.Double> value);
  dart.core.List_interface<java.lang.Double> setV(dart.core.List_interface<java.lang.Double> value);
  dart.core.List_interface<java.lang.Double> setV_prev(dart.core.List_interface<java.lang.Double> value);

}
