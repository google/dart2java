import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;

import org.junit.Test;
import scenario.__TopLevel;

public class Tests {
  @Test
  public void testNullEquals() {
    assertTrue(__TopLevel.nullEqualsNull1());
    assertTrue(__TopLevel.nullEqualsNull2());
    assertTrue(__TopLevel.nullEqualsNull3());
    assertTrue(__TopLevel.nullEqualsNull4());
    assertTrue(__TopLevel.nullEqualsNull5());
  }

  @Test
  public void testNullToString() {
    assertEquals("null", __TopLevel.nullToString1());
    assertEquals("null", __TopLevel.nullToString2());
    assertEquals("null", __TopLevel.nullToString3());
  }
}
