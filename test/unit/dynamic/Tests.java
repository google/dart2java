import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;

import org.junit.Test;
import scenario.__TopLevel;

public class Tests {
  @Test
  public void testDynamicDispatch() {
    assertEquals(530, (int) __TopLevel.testDynamicDispatch());
    assertEquals(181, (int) __TopLevel.testDynamicDispatchPrimitive());
  }
}