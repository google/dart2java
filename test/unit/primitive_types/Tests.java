import static org.junit.Assert.assertEquals;

import org.junit.Test;
import scenario.__TopLevel;

public class Tests {
  @Test
  public void testReturnSimpleValues() {
    assertEquals("foo", __TopLevel.returnString());
    assertEquals(42, __TopLevel.returnInt());
    assertEquals(4.2, __TopLevel.returnDouble());
    assertEquals(true, __TopLevel.returnBool());
  }
}