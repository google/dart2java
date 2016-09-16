package deltablue;

public interface BenchmarkBase_interface extends dart._runtime.base.DartObject_interface
{
  void run();
  void warmup();
  void exercise();
  void setup();
  void teardown();
  double measureForWarumup(int timeMinimum);
  double measureForExercise(int timeMinimum);
  double measure();
  void report();
  java.lang.String getName();
  void _constructor(java.lang.String name);
}
