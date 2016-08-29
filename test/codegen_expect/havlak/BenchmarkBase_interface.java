package havlak;

public interface BenchmarkBase_interface extends dart.core.Object_interface
{
  void run();
  void warmup();
  void exercise();
  void setup();
  void teardown();
  java.lang.Double measureForWarumup(int timeMinimum);
  java.lang.Double measureForExercise(int timeMinimum);
  java.lang.Double measure();
  void report();
  java.lang.String getName();
}
