import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;

import org.junit.Test;
import scenario.__TopLevel;

public class Tests {
  @Test
  public void testSuperMethodInvocation() {
    assertEquals(114, (int) __TopLevel.callClass3Bar());
  }
}