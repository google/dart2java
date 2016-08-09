import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;

import org.junit.Test;
import scenario.__TopLevel;

public class Tests {
  @Test
  public void testLet() {
    assertEquals(90, (int) __TopLevel.countForMultiUpdate());
    assertEquals(51, (int) __TopLevel.ternarySideEffectTest());
  }
}