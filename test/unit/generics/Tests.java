import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;

import org.junit.Test;
import scenario.__TopLevel;

public class Tests {
  @Test
  public void testCovariance() {
    assertEquals(72, (int) __TopLevel.testSubclassSubtype());
  }

  @Test
  public void testSubtype() {
    assertEquals(36, (int) __TopLevel.testTypeSubtypeInList());
  }
}
