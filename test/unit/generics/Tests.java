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

  @Test
  public void testSpecialization() {
    // Cannot test if the correct specialization was chosen,
    // have to look at the generated code!
    assertEquals(60, (int) __TopLevel.testSingleTypeArgumentSpecialization());
    assertEquals("102030",  __TopLevel.testAssignmentOfSpecialization());
  }
}
