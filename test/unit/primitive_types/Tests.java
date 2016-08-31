import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;

import org.junit.Test;
import scenario.__TopLevel;

public class Tests {
  private static final double DELTA = 0.01;

  @Test
  public void testReturnSimpleValues() {
    assertEquals("foo", __TopLevel.getString());
    assertEquals(42, (int) __TopLevel.getInt());
    assertEquals(4.2, (double) __TopLevel.getDouble(), DELTA);
    assertEquals(true, (boolean) __TopLevel.getBool());
    assertEquals(43, (int) __TopLevel.getNumberInt());
    assertEquals(4.3, (double) __TopLevel.getNumberDouble(), DELTA);
  }

  @Test
  public void testProperties() {
    assertEquals(1, (int) __TopLevel.getIntSign(34));
    assertEquals(-1, (int) __TopLevel.getIntSign(-5));
    assertEquals(1.0, (double) __TopLevel.getDoubleSign(34.4), DELTA);
    assertEquals(-1.0, (double) __TopLevel.getDoubleSign(-5.5), DELTA);
    assertEquals(1, (int) __TopLevel.getNumberIntSign(34));
    assertEquals(-1, (int) __TopLevel.getNumberIntSign(-5));
    assertEquals(1.0, (double) __TopLevel.getNumberDoubleSign(34.4), DELTA);
    assertEquals(-1.0, (double) __TopLevel.getNumberDoubleSign(-5.5), DELTA);
    // TODO(springerm): Implement property set
  }

  @Test
  public void testMethodCall() {
    assertEquals(35, (int) __TopLevel.getIntAbs(35));
    assertEquals(36, (int) __TopLevel.getIntAbs(-36));
    assertEquals(35.3, (double) __TopLevel.getDoubleAbs(35.3), DELTA);
    assertEquals(36.4, (double) __TopLevel.getDoubleAbs(-36.4), DELTA);
    assertEquals(35, (int) __TopLevel.getNumberIntAbs(35));
    assertEquals(36, (int) __TopLevel.getNumberIntAbs(-36));
    assertEquals(35.3, (double) __TopLevel.getNumberDoubleAbs(35.3), DELTA);
    assertEquals(36.4, (double) __TopLevel.getNumberDoubleAbs(-36.4), DELTA);
    // TODO(springerm): Implement methods with multiple parameters
  }

  @Test
  public void testOperators() {
    assertEquals(4, (int) __TopLevel.addInts(1, 3));
    assertEquals(4.3, (double) __TopLevel.addDoubles(1.1, 3.2), DELTA);
    assertEquals(4.2, (double) __TopLevel.addIntDouble(1, 3.2), DELTA);
    assertEquals(4.2, (double) __TopLevel.addDoubleInt(3.2, 1), DELTA);
    assertEquals(4.3, (double) __TopLevel.addNumbers(1.1, 3.2), DELTA);
    assertEquals(4.1, (double) __TopLevel.addNumbers(1.1, 3), DELTA);
    assertEquals(4.1, (double) __TopLevel.addNumbers(1, 3.1), DELTA);
    assertEquals(4, (int) __TopLevel.addNumbers(1, 3), DELTA);
    assertEquals(4.1, (double) __TopLevel.addNumberInt(1.1, 3), DELTA);
    assertEquals(4, (int) __TopLevel.addNumberInt(1, 3), DELTA);
    assertEquals(4.2, (double) __TopLevel.addNumberDouble(1.1, 3.1), DELTA);
    assertEquals(4.1, (double) __TopLevel.addNumberDouble(1, 3.1), DELTA);
    assertEquals(4.1, (double) __TopLevel.addIntNumber(3, 1.1), DELTA);
    assertEquals(4, (int) __TopLevel.addIntNumber(3, 1), DELTA);
    assertEquals(4.2, (double) __TopLevel.addDoubleNumber(3.1, 1.1), DELTA);
    assertEquals(4.1, (double) __TopLevel.addDoubleNumber(3.1, 1), DELTA);

    assertEquals(true, (boolean) __TopLevel.lessEqualInts(1, 3));
    assertEquals(true, (boolean) __TopLevel.lessEqualDoubles(1.2, 1.3));
    assertEquals(true, (boolean) __TopLevel.lessEqualIntDouble(1, 3.1));
    assertEquals(true, (boolean) __TopLevel.lessEqualDoubleInt(1.9, 2));
    assertEquals(true, (boolean) __TopLevel.lessEqualNumbers(1, 3));
    assertEquals(true, (boolean) __TopLevel.lessEqualNumbers(1.1, 3));
    assertEquals(true, (boolean) __TopLevel.lessEqualNumbers(1, 3.1));
    assertEquals(true, (boolean) __TopLevel.lessEqualNumbers(1.1, 3.1));
    assertEquals(true, (boolean) __TopLevel.lessEqualNumberInt(1.1, 3));
    assertEquals(true, (boolean) __TopLevel.lessEqualNumberInt(1, 3));
    assertEquals(true, (boolean) __TopLevel.lessEqualNumberDouble(1.1, 3.1));
    assertEquals(true, (boolean) __TopLevel.lessEqualNumberDouble(1, 3.1));
    assertEquals(true, (boolean) __TopLevel.lessEqualIntNumber(1, 3.1));
    assertEquals(true, (boolean) __TopLevel.lessEqualIntNumber(1, 3));
    assertEquals(true, (boolean) __TopLevel.lessEqualDoubleNumber(1.1, 3.1));
    assertEquals(true, (boolean) __TopLevel.lessEqualDoubleNumber(1.1, 3));

    assertEquals(false, (boolean) __TopLevel.lessEqualInts(4, 3));

    assertEquals(-3, (int) __TopLevel.negateInt(3));
    assertEquals(-4, (int) __TopLevel.unaryTilde(3));

    assertEquals(8, (int) __TopLevel.testNumberInteraction());
  }

  @Test
  public void testLogicalOperators() {
    assertTrue(__TopLevel.getLogicalAnd(true, true));
    assertFalse(__TopLevel.getLogicalAnd(true, false));
    assertTrue(__TopLevel.getLogicalOr(false, true));
    assertFalse(__TopLevel.getLogicalOr(false, false));
    assertTrue(__TopLevel.getLogicalNot(false));
    assertFalse(__TopLevel.getLogicalNot(true));
  }
  
  @Test
  public void testStaticFields() {
    assertTrue(__TopLevel.getDoubleMin() < __TopLevel.getDoubleInfinity());
  }

  @Test
  public void testStaticMethods() {
    assertEquals("ぁ", __TopLevel.getFromCharCode(12353));
  }

  @Test
  public void testToString() {
    assertEquals("HelloWorld", __TopLevel.getStringToString());
    assertEquals("42", __TopLevel.getIntToString());
    assertEquals("42.12", __TopLevel.getDoubleToString());
    assertEquals("42", __TopLevel.getNumToString(42));
    assertEquals("42.12", __TopLevel.getNumToString(42.12));
    assertEquals("false", __TopLevel.getFalseToString());
    assertEquals("true", __TopLevel.getTrueToString());
  }

  @Test
  public void testStringInterpolation() {
    assertEquals("HelloWorld", __TopLevel.getStringStringInterpolation1());
    assertEquals("HelloWorld", __TopLevel.getStringStringInterpolation2());
    assertEquals("42", __TopLevel.getIntStringInterpolation1());
    assertEquals("42", __TopLevel.getIntStringInterpolation2());
    assertEquals("42", __TopLevel.getIntStringInterpolation3());
    assertEquals("42", __TopLevel.getIntStringInterpolation4());
    assertEquals("42", __TopLevel.getIntStringInterpolation5());
    assertEquals("42", __TopLevel.getIntStringInterpolation6());
    assertEquals("42", __TopLevel.getIntStringInterpolation7());
    assertEquals("42.12", __TopLevel.getDoubleStringInterpolation());
    assertEquals("42", __TopLevel.getNumStringInterpolation(42));
    assertEquals("42.12", __TopLevel.getNumStringInterpolation(42.12));
    assertEquals("false", __TopLevel.getFalseStringInterpolation());
    assertEquals("true", __TopLevel.getTrueStringInterpolation());
  }

  @Test
  public void testEquals() {
    assertTrue(__TopLevel.getEqualsBools(true, true));
    assertFalse(__TopLevel.getEqualsBools(false, true));
    assertTrue(__TopLevel.getEqualsInts(12, 12));
    assertTrue(__TopLevel.getEqualsDoubles(45.2, 45.2));
    
    // TODO(springerm): Fix test
    // assertTrue(__TopLevel.getEqualsIntDouble(42, 42.0));
    
    assertTrue(__TopLevel.getEqualsIntNum(42, 42));
    assertTrue(__TopLevel.getEqualsNumInt(42, 42));
    assertTrue(__TopLevel.getEqualsNums(42, 42));
    assertTrue(__TopLevel.getEqualsNums(45.2, 45.2));
  }

  @Test
  public void testUnaryArithmeticOperators() {
    assertEquals(22, (int) __TopLevel.testUnaryArithmetics());
  }
}
