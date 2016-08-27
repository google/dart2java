import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;

import org.junit.Test;
import scenario.__TopLevel;

public class Tests {
  @Test
  public void testInterfaces() {
    assertEquals(31, (int) __TopLevel.testA());
    assertEquals(1213, (int) __TopLevel.testB());
    assertEquals(1312, (int) __TopLevel.testC());
  }
}
