package gameoflife;

public interface Culture_interface extends dart._runtime.base.DartObject_interface
{
  void initPopulation(int Pop);
  void add(java.lang.Object x, java.lang.Object y);
  void update();
  dart.core.Map_interface<java.lang.String, gameoflife.Cell_interface> getCellDish();
  int getWidth();
  dart.math.Random_interface getRng();
  dart.core.Map_interface<java.lang.String, gameoflife.Cell_interface> setCellDish(dart.core.Map_interface<java.lang.String, gameoflife.Cell_interface> value);
  int setWidth(int value);
  dart.math.Random_interface setRng(dart.math.Random_interface value);

}
