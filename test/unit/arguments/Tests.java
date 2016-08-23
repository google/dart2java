import static org.junit.Assert.assertEquals;

import org.junit.Test;
import scenario.__TopLevel;

public class Tests {
  @Test
  public void testOptionalArgs() {
    assertEquals(321, (int) __TopLevel.callFuncWithoutOptionalArg());
    assertEquals(421, (int) __TopLevel.callFuncWithOptionalArg());
  }
}