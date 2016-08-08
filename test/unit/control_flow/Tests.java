import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;

import org.junit.Test;
import scenario.__TopLevel;

public class Tests {

  @Test
  public void testIfStatement() {
    assertTrue(__TopLevel.isLessIfThen(1, 2));
    assertFalse(__TopLevel.isLessIfThen(2, 1));
    assertTrue(__TopLevel.isLessIfThenElse(1, 2));
    assertFalse(__TopLevel.isLessIfThenElse(2, 1));
  }

  @Test
  public void testTernary() {
    assertTrue(__TopLevel.isLessTernary(1, 2));
    assertFalse(__TopLevel.isLessTernary(2, 1));
  }

  @Test
  public void testLoops() {
    assertEquals(10, (int) __TopLevel.countWhile());
    assertEquals(10, (int) __TopLevel.countFor());
    assertEquals(90, (int) __TopLevel.countForMultiUpdate());
  }
}