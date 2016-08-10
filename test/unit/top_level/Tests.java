import static org.junit.Assert.assertEquals;

import org.junit.Test;
import scenario.__TopLevel;

public class Tests {
  @Test
  public void testTopLevelMembers() {
    assertEquals(5, (int) __TopLevel.method1());
    assertEquals(10, (int) __TopLevel.getVar1());
    assertEquals(10, (int) __TopLevel.callVar1Getter());

    __TopLevel.setVar2(12);
    assertEquals(12, (int) __TopLevel.getVar2());
  }
}