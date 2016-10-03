package gameoflife;

public interface Cell_interface extends dart._runtime.base.DartObject_interface
{
  void update();
  void commit();
  int getNeighbours();
  boolean isNeighbourPopulated(int nx, int ny);
  int getX();
  int getY();
  dart.core.Map_interface<java.lang.String, gameoflife.Cell_interface> getEnvironment();
  int getState();
  int getNextState();
  int getAge();
  int getUpdates();
  int setX(int value);
  int setY(int value);
  dart.core.Map_interface<java.lang.String, gameoflife.Cell_interface> setEnvironment(dart.core.Map_interface<java.lang.String, gameoflife.Cell_interface> value);
  int setState(int value);
  int setNextState(int value);
  int setAge(int value);
  int setUpdates(int value);
  void _constructor(int x, int y, dart.core.Map_interface<java.lang.String, gameoflife.Cell_interface> Environment);
}
