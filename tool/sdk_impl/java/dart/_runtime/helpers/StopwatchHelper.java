package dart._runtime.helpers;

import java.util.Calendar;

public class StopwatchHelper {
  public static void initTicker() {
    dart.core.Stopwatch._frequency = 1;
  }

  public static int now() {
    return Calendar.getInstance().get(Calendar.MILLISECOND);
  }
}