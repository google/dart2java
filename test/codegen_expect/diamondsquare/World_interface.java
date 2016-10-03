package diamondsquare;

public interface World_interface extends dart._runtime.base.DartObject_interface
{
  void Reset();
  void Base();
  void SetCorners(int x, int y, int w, int h, dart.core.List_interface__int v);
  void Generate();
  void Smooth();
  int getWidth();
  int getLength();
  dart.math.Random_interface getRng();
  int getIterations();
  dart.core.List_interface<dart.core.List_interface__int> getMap_data();
  int setWidth(int value);
  int setLength(int value);
  dart.math.Random_interface setRng(dart.math.Random_interface value);
  int setIterations(int value);
  dart.core.List_interface<dart.core.List_interface__int> setMap_data(dart.core.List_interface<dart.core.List_interface__int> value);
  void _constructor();
}
