package richards;

public interface BenchmarkBase_interface extends dart.core.Object_interface
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
}
