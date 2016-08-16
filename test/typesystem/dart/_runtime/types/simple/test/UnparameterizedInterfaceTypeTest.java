package dart._runtime.types.simple.test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import dart._runtime.types.simple.InterfaceTypeExpr;
import dart._runtime.types.simple.InterfaceTypeInfo;
import dart._runtime.types.simple.Type;
import dart._runtime.types.simple.TypeEnvironment;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

@RunWith(JUnit4.class)
public class UnparameterizedInterfaceTypeTest {
  InterfaceTypeInfo objectTypeInfo;
  InterfaceTypeInfo comparableTypeInfo;
  InterfaceTypeInfo numTypeInfo;
  InterfaceTypeInfo intTypeInfo;
  InterfaceTypeInfo doubleTypeInfo;

  @Before
  public void initialize() {
    // In real code, these would be static final fields on generated Java classes (or maybe on a
    // helper class, or on the library's __TopLevel class). For now, we declare them as
    // "test-global" variables.
    objectTypeInfo = new InterfaceTypeInfo(null, "dart:core", "Object");

    comparableTypeInfo = new InterfaceTypeInfo(new String[] {"E"}, "dart:core", "Comparable");
    comparableTypeInfo.superclass = new InterfaceTypeExpr(objectTypeInfo);

    numTypeInfo = new InterfaceTypeInfo(null, "dart:core", "num");
    numTypeInfo.superclass = new InterfaceTypeExpr(objectTypeInfo);
    numTypeInfo.interfaces = new InterfaceTypeExpr[] {new InterfaceTypeExpr(comparableTypeInfo,
        new InterfaceTypeExpr[] {new InterfaceTypeExpr(numTypeInfo)})};

    intTypeInfo = new InterfaceTypeInfo(null, "dart:core", "int");
    intTypeInfo.superclass = new InterfaceTypeExpr(numTypeInfo);

    doubleTypeInfo = new InterfaceTypeInfo(null, "dart:core", "double");
    doubleTypeInfo.superclass = new InterfaceTypeExpr(numTypeInfo);
  }

  @Test
  public void createsObjectType() {
    Type objectType = TypeEnvironment.ROOT.evaluate(new InterfaceTypeExpr(objectTypeInfo));

    assertNotNull("dart:core#Object should be evaluated to a Type.", objectType);
    assertTrue("dart:core#Object should be a subtype of itself.",
        objectType.isSubtypeOf(objectType));
    assertTrue("dart:core#Object should be canonicalized.",
        objectType == TypeEnvironment.ROOT.evaluate(new InterfaceTypeExpr(objectTypeInfo)));
  }

  @Test
  public void correctNumericHierarchy() {
    Type objectType = TypeEnvironment.ROOT.evaluate(new InterfaceTypeExpr(objectTypeInfo));
    Type numType = TypeEnvironment.ROOT.evaluate(new InterfaceTypeExpr(numTypeInfo));
    Type intType = TypeEnvironment.ROOT.evaluate(new InterfaceTypeExpr(intTypeInfo));
    Type doubleType = TypeEnvironment.ROOT.evaluate(new InterfaceTypeExpr(doubleTypeInfo));

    assertNotNull("dart:core#Object should be evaluated to a Type.", objectType);
    assertNotNull("dart:core#num should be evaluated to a Type.", numType);
    assertNotNull("dart:core#int should be evaluated to a Type.", intType);
    assertNotNull("dart:core#double should be evaluated to a Type.", doubleType);

    assertTrue("Object <: Object should be true", objectType.isSubtypeOf(objectType));
    assertFalse("Object <: num should be false", objectType.isSubtypeOf(numType));
    assertFalse("Object <: int should be false", objectType.isSubtypeOf(intType));
    assertFalse("Object <: double should be false", objectType.isSubtypeOf(doubleType));

    assertTrue("num <: Object should be true", numType.isSubtypeOf(objectType));
    assertTrue("num <: num should be true", numType.isSubtypeOf(numType));
    assertFalse("num <: int should be false", numType.isSubtypeOf(intType));
    assertFalse("num <: double should be false", numType.isSubtypeOf(doubleType));

    assertTrue("int <: Object should be true", intType.isSubtypeOf(objectType));
    assertTrue("int <: num should be true", intType.isSubtypeOf(numType));
    assertTrue("int <: int should be true", intType.isSubtypeOf(intType));
    assertFalse("int <: double should be false", intType.isSubtypeOf(doubleType));

    assertTrue("double <: Object should be true", doubleType.isSubtypeOf(objectType));
    assertTrue("double <: num should be true", doubleType.isSubtypeOf(numType));
    assertFalse("double <: int should be false", doubleType.isSubtypeOf(intType));
    assertTrue("double <: double should be true", doubleType.isSubtypeOf(doubleType));
  }

}
