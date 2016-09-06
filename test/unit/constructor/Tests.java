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

  @Test
  public void testIntializerOrder() {
    assertEquals(101, (int) __TopLevel.createClass6());

    __TopLevel.createB();
    String[] initOrderExpected = new String[] {"fieldB2", "fieldB1", 
      "fieldA3", "fieldA2", "fieldA1", "bodyA", "bodyB"};
    String[] initOrder = ((java.util.List<String>)__TopLevel.initOrder).toArray(new String[] {});
    assertEquals(initOrderExpected, initOrder);
  }
}
