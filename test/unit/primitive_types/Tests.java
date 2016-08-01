import static org.junit.Assert.assertEquals;

import org.junit.Test;
import scenario.__TopLevel;

public class Tests {
  @Test
  public void testReturnSimpleValues() {
    assertEquals("foo", __TopLevel.getString());
    assertEquals(42, (int) __TopLevel.getInt());
//    assertEquals(4.2, (double) __TopLevel.getDouble());
    assertEquals(true, (boolean) __TopLevel.getBool());
  }

  @Test
  public void testProperties() {
    assertEquals(1, (int) __TopLevel.getIntSign(34));
    assertEquals(-1, (int) __TopLevel.getIntSign(-5));
    // TODO(springerm): Implement property set
  }

  @Test
  public void testMethodCall() {
    assertEquals(35, (int) __TopLevel.getIntAbs(35));
    assertEquals(36, (int) __TopLevel.getIntAbs(-36));
    // TODO(springerm): Implement methods with multiple parameters
  }

  @Test
  public void testOperators() {
    assertEquals(4, (int) __TopLevel.addInts(1, 3));
    assertEquals(true, (boolean) __TopLevel.lessEqualInts(1, 3));
    assertEquals(false, (boolean) __TopLevel.lessEqualInts(4, 3));
    assertEquals(-3, (int) __TopLevel.negateInt(3));
    assertEquals(-4, (int) __TopLevel.unaryTilde(3));
  }
}