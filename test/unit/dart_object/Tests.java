import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;

import org.junit.Test;
import scenario.__TopLevel;

public class Tests {
  @Test
  public void testOperatorEqual() {
    assertFalse(__TopLevel.equalObjectObject());
    assertFalse(__TopLevel.equalObjectInt());
    assertFalse(__TopLevel.equalIntObject());
    assertTrue(__TopLevel.equalIntInt());
  }
  
  @Test
  public void testHashCode() {
    // Just want to make sure that it compiles...
    __TopLevel.hashCodeObject();
    __TopLevel.hashCodeClass1();
    __TopLevel.hashCodeInteger();
  }

  @Test
  public void testToString() {
    assertEquals("Instance of 'Object'", __TopLevel.toStringObject());
    assertEquals("12", __TopLevel.toStringInteger());
  }

  @Test
  public void testSubclassOfDartClass() {
    assertFalse(__TopLevel.equalObjectClass1());
    assertFalse(__TopLevel.equalClass1Object());
    assertFalse(__TopLevel.equalClass1Class1());
  }
}