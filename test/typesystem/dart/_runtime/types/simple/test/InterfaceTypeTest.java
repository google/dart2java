package dart._runtime.types.simple.test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import dart._runtime.types.simple.InterfaceType;
import dart._runtime.types.simple.InterfaceTypeExpr;
import dart._runtime.types.simple.InterfaceTypeInfo;
import dart._runtime.types.simple.Type;
import dart._runtime.types.simple.TypeEnvironment;
import dart._runtime.types.simple.TypeExpr;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

@RunWith(JUnit4.class)
public class InterfaceTypeTest {
  InterfaceTypeInfo objectTypeInfo;
  InterfaceTypeInfo comparableTypeInfo;
  InterfaceTypeInfo numTypeInfo;
  InterfaceTypeInfo intTypeInfo;
  InterfaceTypeInfo doubleTypeInfo;
  InterfaceTypeInfo stringTypeInfo;
  InterfaceTypeInfo iterableTypeInfo;
  InterfaceTypeInfo listTypeInfo;
  InterfaceTypeInfo listMixinTypeInfo;
  InterfaceTypeInfo listBaseTypeInfo;
  InterfaceTypeInfo growableListTypeInfo;

  @Before
  public void initialize() {
    // In real code, these would be static final fields on generated Java classes (or maybe on a
    // helper class, or on the library's __TopLevel class). For now, we declare them as
    // "test-global" variables.
    objectTypeInfo = new InterfaceTypeInfo(java.lang.Object.class, null);

    comparableTypeInfo = new InterfaceTypeInfo(new String[] {"E"}, dart.core.Comparable.class,
        dart.core.Comparable_interface.class);
    comparableTypeInfo.superclass = new InterfaceTypeExpr(objectTypeInfo);

    numTypeInfo = new InterfaceTypeInfo(java.lang.Number.class, null);
    numTypeInfo.superclass = new InterfaceTypeExpr(objectTypeInfo);
    numTypeInfo.interfaces = new InterfaceTypeExpr[] {new InterfaceTypeExpr(comparableTypeInfo,
        new TypeExpr[] {new InterfaceTypeExpr(numTypeInfo)})};

    intTypeInfo = new InterfaceTypeInfo(int.class, null);
    intTypeInfo.superclass = new InterfaceTypeExpr(numTypeInfo);

    doubleTypeInfo = new InterfaceTypeInfo(double.class, null);
    doubleTypeInfo.superclass = new InterfaceTypeExpr(numTypeInfo);

    stringTypeInfo = new InterfaceTypeInfo(java.lang.String.class, null);
    stringTypeInfo.superclass = new InterfaceTypeExpr(objectTypeInfo);
    stringTypeInfo.interfaces = new InterfaceTypeExpr[] {new InterfaceTypeExpr(comparableTypeInfo,
        new TypeExpr[] {new InterfaceTypeExpr(stringTypeInfo)})};

    iterableTypeInfo = new InterfaceTypeInfo(new String[] {"T"}, dart.core.Iterable.class,
        dart.core.Iterable_interface.class);
    iterableTypeInfo.superclass = new InterfaceTypeExpr(objectTypeInfo);

    listTypeInfo = new InterfaceTypeInfo(new String[] {"T"}, null, dart.core.List_interface.class);
    listTypeInfo.superclass = new InterfaceTypeExpr(objectTypeInfo);
    listTypeInfo.interfaces = new InterfaceTypeExpr[] {
        new InterfaceTypeExpr(iterableTypeInfo, new TypeExpr[] {listTypeInfo.typeVariables[0]})};

    class ListMixin {
    }
    listMixinTypeInfo = new InterfaceTypeInfo(new String[] {"T"}, ListMixin.class, null);
    listMixinTypeInfo.superclass = new InterfaceTypeExpr(objectTypeInfo);
    listMixinTypeInfo.interfaces = new InterfaceTypeExpr[] {
        new InterfaceTypeExpr(listTypeInfo, new TypeExpr[] {listMixinTypeInfo.typeVariables[0]})};

    class ListBase {
    }
    listBaseTypeInfo = new InterfaceTypeInfo(new String[] {"T"}, ListBase.class, null);
    listBaseTypeInfo.superclass = new InterfaceTypeExpr(objectTypeInfo);
    listBaseTypeInfo.mixin = new InterfaceTypeExpr(listMixinTypeInfo,
        new TypeExpr[] {listBaseTypeInfo.typeVariables[0]});

    class GrowableList {
    }
    growableListTypeInfo = new InterfaceTypeInfo(new String[] {"T"}, GrowableList.class, null);
    growableListTypeInfo.superclass = new InterfaceTypeExpr(listBaseTypeInfo,
        new TypeExpr[] {growableListTypeInfo.typeVariables[0]});

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

    assertTrue("Object <: Object", objectType.isSubtypeOf(objectType));
    assertFalse("Object <: num", objectType.isSubtypeOf(numType));
    assertFalse("Object <: int", objectType.isSubtypeOf(intType));
    assertFalse("Object <: double", objectType.isSubtypeOf(doubleType));

    assertTrue("num <: Object", numType.isSubtypeOf(objectType));
    assertTrue("num <: num", numType.isSubtypeOf(numType));
    assertFalse("num <: int", numType.isSubtypeOf(intType));
    assertFalse("num <: double", numType.isSubtypeOf(doubleType));

    assertTrue("int <: Object", intType.isSubtypeOf(objectType));
    assertTrue("int <: num", intType.isSubtypeOf(numType));
    assertTrue("int <: int", intType.isSubtypeOf(intType));
    assertFalse("int <: double", intType.isSubtypeOf(doubleType));

    assertTrue("double <: Object", doubleType.isSubtypeOf(objectType));
    assertTrue("double <: num", doubleType.isSubtypeOf(numType));
    assertFalse("double <: int", doubleType.isSubtypeOf(intType));
    assertTrue("double <: double", doubleType.isSubtypeOf(doubleType));
  }

  @Test
  public void correctGenericInheritance() {
    Type numType = TypeEnvironment.ROOT.evaluate(new InterfaceTypeExpr(numTypeInfo));
    Type stringType = TypeEnvironment.ROOT.evaluate(new InterfaceTypeExpr(stringTypeInfo));
    Type comparableOfObjectType =
        TypeEnvironment.ROOT.evaluate(new InterfaceTypeExpr(comparableTypeInfo,
            new TypeExpr[] {new InterfaceTypeExpr(objectTypeInfo)}));
    Type comparableOfNumType =
        TypeEnvironment.ROOT.evaluate(new InterfaceTypeExpr(comparableTypeInfo,
            new TypeExpr[] {new InterfaceTypeExpr(numTypeInfo)}));
    Type comparableOfIntType =
        TypeEnvironment.ROOT.evaluate(new InterfaceTypeExpr(comparableTypeInfo,
            new TypeExpr[] {new InterfaceTypeExpr(intTypeInfo)}));
    Type comparableOfStringType =
        TypeEnvironment.ROOT.evaluate(new InterfaceTypeExpr(comparableTypeInfo,
            new TypeExpr[] {new InterfaceTypeExpr(stringTypeInfo)}));
    Type comparableOfComparableOfStringType =
        TypeEnvironment.ROOT.evaluate(new InterfaceTypeExpr(comparableTypeInfo,
            new TypeExpr[] {new InterfaceTypeExpr(comparableTypeInfo,
                new TypeExpr[] {new InterfaceTypeExpr(stringTypeInfo)})}));

    assertTrue("num <: Comparable<Object>", numType.isSubtypeOf(comparableOfObjectType));
    assertTrue("num <: Comparable<num>", numType.isSubtypeOf(comparableOfNumType));
    assertFalse("num <: Comparable<int>", numType.isSubtypeOf(comparableOfIntType));
    assertFalse("num <: Comparable<String>", numType.isSubtypeOf(comparableOfStringType));
    assertFalse("num <: Comparable<Comparable<String>>",
        numType.isSubtypeOf(comparableOfComparableOfStringType));

    assertTrue("String <: Comparable<String>", stringType.isSubtypeOf(comparableOfStringType));
    assertFalse("String <: Comparable<num>", stringType.isSubtypeOf(comparableOfNumType));
    assertTrue("String <: Comparable<Comparable<String>>",
        stringType.isSubtypeOf(comparableOfComparableOfStringType));
  }

  @Test
  public void collections() {
    Type stringType = TypeEnvironment.ROOT.evaluate(new InterfaceTypeExpr(stringTypeInfo));
    InterfaceType iterableOfStringType =
        TypeEnvironment.ROOT.evaluate(new InterfaceTypeExpr(iterableTypeInfo,
            new TypeExpr[] {new InterfaceTypeExpr(stringTypeInfo)}));
    InterfaceType listOfStringType =
        TypeEnvironment.ROOT.evaluate(new InterfaceTypeExpr(listTypeInfo,
            new TypeExpr[] {new InterfaceTypeExpr(stringTypeInfo)}));
    InterfaceType listMixinOfStringType =
        TypeEnvironment.ROOT.evaluate(new InterfaceTypeExpr(listMixinTypeInfo,
            new TypeExpr[] {new InterfaceTypeExpr(stringTypeInfo)}));
    InterfaceType listBaseOfStringType =
        TypeEnvironment.ROOT.evaluate(new InterfaceTypeExpr(listBaseTypeInfo,
            new TypeExpr[] {new InterfaceTypeExpr(stringTypeInfo)}));
    InterfaceType growableListOfStringType =
        TypeEnvironment.ROOT.evaluate(new InterfaceTypeExpr(growableListTypeInfo,
            new TypeExpr[] {new InterfaceTypeExpr(stringTypeInfo)}));



    assertTrue("List<String> <: Iterable<String>",
        listOfStringType.isSubtypeOf(iterableOfStringType));
    assertTrue("Iterable<String>::T == String",
        iterableOfStringType.env.evaluate(iterableTypeInfo.typeVariables[0]) == stringType);
    assertTrue("List<String>::T == String",
        listOfStringType.env.evaluate(listTypeInfo.typeVariables[0]) == stringType);

    assertTrue("GrowableList<String> <: Iterable<String>",
        growableListOfStringType.isSubtypeOf(iterableOfStringType));
    assertTrue("GrowableList<String> <: List<String>",
        growableListOfStringType.isSubtypeOf(listOfStringType));
    assertTrue("GrowableList<String> <: ListMixin<String>",
        growableListOfStringType.isSubtypeOf(listMixinOfStringType));
    assertTrue("GrowableList<String> <: ListBase<String>",
        growableListOfStringType.isSubtypeOf(listBaseOfStringType));

    assertTrue("(ListMixin<String>::T in GrowableList<String>) == String",
        growableListOfStringType.env.evaluate(listMixinTypeInfo.typeVariables[0]) == stringType);
    assertTrue("(ListBase<String>::T in GrowableList<String>) == String",
        growableListOfStringType.env.evaluate(listBaseTypeInfo.typeVariables[0]) == stringType);
    assertTrue("(GrowableList<String>::T in GrowableList<String>) == String",
        growableListOfStringType.env.evaluate(growableListTypeInfo.typeVariables[0]) == stringType);

  }
}
