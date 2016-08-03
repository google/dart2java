import static org.junit.Assert.assertEquals;

import org.junit.Test;
import scenario.__TopLevel;

public class Tests {
  @Test
  public void testStaticFieldAccess() {
    assertEquals(12, (int) __TopLevel.getVarViaStaticGet());
    assertEquals(12, (int) __TopLevel.getVarViaStaticGetFromInst());
    assertEquals(12, (int) __TopLevel.getVarViaStaticGetDirectly());
    assertEquals(12, (int) __TopLevel.getVarViaStaticMethod());
    assertEquals(12, (int) __TopLevel.getVarViaStaticMethodFromInst());
  }
}