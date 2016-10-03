package fluidmotion;

public interface FluidField_interface extends dart._runtime.base.DartObject_interface
{
  void validate(java.lang.Object expectedDens, java.lang.Object expectedU, java.lang.Object expectedV);
  void reset();
  void addFields(dart.core.List_interface__double x, dart.core.List_interface__double s, double dt);
  void set_bnd(int b, dart.core.List_interface__double x);
  void lin_solve(int b, dart.core.List_interface__double x, dart.core.List_interface__double x0, int a, int c);
  void diffuse(int b, dart.core.List_interface__double x, dart.core.List_interface__double x0, double dt);
  void lin_solve2(dart.core.List_interface__double x, dart.core.List_interface__double x0, dart.core.List_interface__double y, dart.core.List_interface__double y0, int a, int c);
  void diffuse2(dart.core.List_interface__double x, dart.core.List_interface__double x0, java.lang.Object y, dart.core.List_interface__double y0, double dt);
  void advect(int b, dart.core.List_interface__double d, dart.core.List_interface__double d0, dart.core.List_interface__double u, dart.core.List_interface__double v, double dt);
  void project(dart.core.List_interface__double u, dart.core.List_interface__double v, dart.core.List_interface__double p, dart.core.List_interface__double div);
  void dens_step(dart.core.List_interface__double x, dart.core.List_interface__double x0, dart.core.List_interface__double u, dart.core.List_interface__double v, double dt);
  void vel_step(dart.core.List_interface__double u, dart.core.List_interface__double v, dart.core.List_interface__double u0, dart.core.List_interface__double v0, double dt);
  void queryUI(dart.core.List_interface__double d, dart.core.List_interface__double u, dart.core.List_interface__double v);
  void update();
  java.lang.Object getCanvas();
  int getIterations();
  double getDt();
  int getSize();
  dart.core.List_interface__double getDens();
  dart.core.List_interface__double getDens_prev();
  dart.core.List_interface__double getU();
  dart.core.List_interface__double getU_prev();
  dart.core.List_interface__double getV();
  dart.core.List_interface__double getV_prev();
  int getWidth();
  int getHeight();
  int getRowSize();
  dart.core.List_interface__double setDens(dart.core.List_interface__double value);
  dart.core.List_interface__double setDens_prev(dart.core.List_interface__double value);
  dart.core.List_interface__double setU(dart.core.List_interface__double value);
  dart.core.List_interface__double setU_prev(dart.core.List_interface__double value);
  dart.core.List_interface__double setV(dart.core.List_interface__double value);
  dart.core.List_interface__double setV_prev(dart.core.List_interface__double value);

}
