import static org.junit.Assert.assertEquals;

import org.junit.Test;
import scenario.__TopLevel;

public class Tests {
  @Test
  public void testHelloWorld() {
    assertEquals("Hello World!", __TopLevel.helloWorld());
  }

  @Test
  public void testMultiFieldAssignment() {
    assertEquals(12, (int) __TopLevel.setReturnMultipleFields(12));
  }
}