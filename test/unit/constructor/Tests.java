import static org.junit.Assert.assertEquals;

import org.junit.Test;
import scenario.__TopLevel;

public class Tests {
  @Test
  public void testBasicConstructors() {
    assertEquals(12, (int) __TopLevel.createClass1());
    assertEquals(5, (int) __TopLevel.createClass2());
  }

  @Test
  public void testInitializers() {
    assertEquals(5, (int) __TopLevel.createClass3());
    assertEquals(4, (int) __TopLevel.createClass4());
  }
}