package dart._runtime.helpers;

public class StopwatchHelper {
  public static void initTicker() {
    dart.core.Stopwatch._frequency = 1000 * 1000;
  }

  public static int now() {
    return (int)((System.nanoTime() + 500L) / 1000L);
  }
}
