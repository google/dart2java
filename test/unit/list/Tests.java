import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;

import java.util.List;
import org.junit.Test;
import scenario.__TopLevel;

public class Tests {
  @Test
  public void testListMethods() {
    assertEquals(12, (int) __TopLevel.createAndOperatorAt());
    assertEquals(170, (int) __TopLevel.simpleOperations());
    assertEquals(18, (int) __TopLevel.triggerArrayGrowth());
  }

  @Test
  public void testListLiteral() {
    assertEquals(30, (int) __TopLevel.listLiteral());
  }

  @Test
  public void testJavaListInterface() {
    int result = 0;
    List<Integer> list = __TopLevel.getIntList();
    
    for (int value : list) {
      result = result + value;
    }

    assertEquals(5 + 8 + 10, result);
  }
}
