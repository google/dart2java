// Copyright 2016, the Dart project authors.
//
// Licensed under the Apache License, Version 2.0 (the "License");
// you may not use this file except in compliance with the License.
// You may obtain a copy of the License at
//
//     http://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing, software
// distributed under the License is distributed on an "AS IS" BASIS,
// WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// See the License for the specific language governing permissions and
// limitations under the License.

package dart._runtime.types.simple.test;

import static dart._runtime.types.simple.test.Util.testHierarchy;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import dart._runtime.types.simple.FunctionTypeExpr;
import dart._runtime.types.simple.FunctionTypeInfo;
import dart._runtime.types.simple.InterfaceTypeExpr;
import dart._runtime.types.simple.InterfaceTypeInfo;
import dart._runtime.types.simple.Type;
import dart._runtime.types.simple.TypeEnvironment;
import dart._runtime.types.simple.TypeExpr;
import dart._runtime.types.simple.VoidType;
import dart._runtime.types.simple.test.Util.Node;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

@RunWith(JUnit4.class)
public class FunctionTypeTest {
  InterfaceTypeInfo objectTypeInfo;
  InterfaceTypeInfo numTypeInfo;
  InterfaceTypeInfo intTypeInfo;

  // int -> void
  FunctionTypeInfo I2V;
  // num -> void
  FunctionTypeInfo N2V;
  // Object -> void
  FunctionTypeInfo O2V;
  // int -> int
  FunctionTypeInfo I2I;
  // int -> num
  FunctionTypeInfo I2N;
  // int -> Object
  FunctionTypeInfo I2O;
  // int -> int (duplicate; to test equality).
  FunctionTypeInfo I2I_2;

  @Before
  public void initialize() {
    // In real code, these would be static final fields on generated Java classes (or maybe on a
    // helper class, or on the library's __TopLevel class). For now, we declare them as
    // "test-global" variables. The behavior of these interface types is tested by
    // InterfaceTypeTest.
    objectTypeInfo = new InterfaceTypeInfo(Object.class, null);
    numTypeInfo = new InterfaceTypeInfo(Number.class, null);
    numTypeInfo.superclass = new InterfaceTypeExpr(objectTypeInfo);
    intTypeInfo = new InterfaceTypeInfo(int.class, null);
    intTypeInfo.superclass = new InterfaceTypeExpr(numTypeInfo);

    // In real code, these would be static variables in the Java class that contains the function
    // declarations. For now, we declare them as "test-global" variables.
    I2V =
        new FunctionTypeInfo(VoidType.EXPR, 1, new TypeExpr[] {new InterfaceTypeExpr(intTypeInfo)});
    N2V =
        new FunctionTypeInfo(VoidType.EXPR, 1, new TypeExpr[] {new InterfaceTypeExpr(numTypeInfo)});
    O2V = new FunctionTypeInfo(VoidType.EXPR, 1,
        new TypeExpr[] {new InterfaceTypeExpr(objectTypeInfo)});
    I2I = new FunctionTypeInfo(new InterfaceTypeExpr(intTypeInfo), 1,
        new TypeExpr[] {new InterfaceTypeExpr(intTypeInfo)});
    I2N = new FunctionTypeInfo(new InterfaceTypeExpr(numTypeInfo), 1,
        new TypeExpr[] {new InterfaceTypeExpr(intTypeInfo)});
    I2O = new FunctionTypeInfo(new InterfaceTypeExpr(objectTypeInfo), 1,
        new TypeExpr[] {new InterfaceTypeExpr(intTypeInfo)});
    I2I_2 = new FunctionTypeInfo(new InterfaceTypeExpr(intTypeInfo), 1,
        new TypeExpr[] {new InterfaceTypeExpr(intTypeInfo)});
  }

  @Test
  public void typeEvaluation() {
    Type I2IType = TypeEnvironment.ROOT.evaluate(new FunctionTypeExpr(I2I));
    Type I2NType = TypeEnvironment.ROOT.evaluate(new FunctionTypeExpr(I2N));
    Type I2OType = TypeEnvironment.ROOT.evaluate(new FunctionTypeExpr(I2O));
    Type I2VType = TypeEnvironment.ROOT.evaluate(new FunctionTypeExpr(I2V));
    Type N2VType = TypeEnvironment.ROOT.evaluate(new FunctionTypeExpr(N2V));
    Type O2VType = TypeEnvironment.ROOT.evaluate(new FunctionTypeExpr(O2V));
    Type I2I_2Type = TypeEnvironment.ROOT.evaluate(new FunctionTypeExpr(I2I_2));

    assertNotNull("int -> int should evaluate to a Type.", I2IType);
    assertNotNull("int -> num should evaluate to a Type.", I2NType);
    assertNotNull("int -> Object should evaluate to a Type.", I2OType);
    assertNotNull("int -> void should evaluate to a Type.", I2VType);
    assertNotNull("num -> void should evaluate to a Type.", N2VType);
    assertNotNull("Object -> void should evaluate to a Type.", O2VType);
    assertNotNull("int -> int should evaluate to a Type from a duplicate type info.", I2I_2Type);
    assertTrue("int -> int should be identical, even with different type info instances.",
        I2IType == I2I_2Type);
  }

  @Test
  public void returnTypeVariance() {
    Node I2VType = new Node("(int) -> void", new FunctionTypeExpr(I2V));
    Node I2OType = new Node("(int) -> Object", new FunctionTypeExpr(I2O), Arrays.asList(I2VType));
    Node I2NType = new Node("(int) -> num", new FunctionTypeExpr(I2N), Arrays.asList(I2OType));
    @SuppressWarnings("unused")
    Node I2IType = new Node("(int) -> int", new FunctionTypeExpr(I2I), Arrays.asList(I2NType));

    testHierarchy(I2VType);
  }

  @Test
  public void parameterTypeVariance() {
    Node I2VType = new Node("(int) -> void", new FunctionTypeExpr(I2V));
    Node N2VType = new Node("(num) -> void", new FunctionTypeExpr(N2V), Arrays.asList(I2VType));
    @SuppressWarnings("unused")
    Node O2VType = new Node("(Object) -> void", new FunctionTypeExpr(O2V), Arrays.asList(N2VType));

    testHierarchy(I2VType);
  }

  @Test
  public void optionalParamVariance2() {
    // The type "req${i}" requires i positional parameters, and accepts no optional parameters.
    // These are all root types in this sub-hierarchy.
    Node req0 = new Node("() -> Object", makeWithPositional(0, 0));
    Node req1 = new Node("(Object) -> Object", makeWithPositional(1, 0));
    Node req2 = new Node("(Object,Object) -> Object", makeWithPositional(2, 0));

    // The type "req${i}pos${j}" has i required parameters and an additional j optional positional
    // parameters; all parameters are of type Object.
    Node req0pos1 =
        new Node("([Object]) -> Object", makeWithPositional(0, 1), Arrays.asList(req0, req1));
    Node req1pos1 = new Node("(Object,[Object]) -> Object", makeWithPositional(1, 1),
        Arrays.asList(req1, req2));
    @SuppressWarnings("unused")
    Node req0pos2 = new Node("([Object,Object]) -> Object", makeWithPositional(0, 2),
        Arrays.asList(req0pos1, req1pos1));

    // The type "req${i}nam${bits}" has i required parameters; bits is a bitfield indicating which
    // of the optional named parameters are present.
    Node req0nam01 =
        new Node("({named0:Object}) -> Object", makeWithNamed(0, 0b01), Arrays.asList(req0));
    Node req0nam10 =
        new Node("({named1:Object}) -> Object", makeWithNamed(0, 0b10), Arrays.asList(req0));
    @SuppressWarnings("unused")
    Node req0nam11 = new Node("({named0:Object,named1:Object}) -> Object", makeWithNamed(0, 0b11),
        Arrays.asList(req0nam01, req0nam10));
    @SuppressWarnings("unused")
    Node req1nam01 = new Node("(Object,{named0:Object}) -> Object", makeWithNamed(1, 0b01),
        Arrays.asList(req1));
    @SuppressWarnings("unused")
    Node req1nam10 = new Node("(Object,{named1:Object}) -> Object", makeWithNamed(1, 0b10),
        Arrays.asList(req1));

    testHierarchy(req0, req1, req2);
  }

  private Type makeWithPositional(int required, int extraOptional) {
    TypeExpr[] positionalParams = new TypeExpr[required + extraOptional];
    for (int i = 0; i < positionalParams.length; i++) {
      positionalParams[i] = new InterfaceTypeExpr(objectTypeInfo);
    }
    return TypeEnvironment.ROOT.evaluate(
        new FunctionTypeExpr(new FunctionTypeInfo(VoidType.EXPR, required, positionalParams)));
  }

  private Type makeWithNamed(int required, int namedBits) {
    TypeExpr[] positionalParams = new TypeExpr[required];
    for (int i = 0; i < positionalParams.length; i++) {
      positionalParams[i] = new InterfaceTypeExpr(objectTypeInfo);
    }
    List<String> namedNames = new ArrayList<>();
    List<TypeExpr> namedTypes = new ArrayList<>();
    for (int i = 0; i < 32; i++) {
      if ((namedBits & (1 << i)) != 0) {
        namedNames.add("named" + i);
        namedTypes.add(new InterfaceTypeExpr(objectTypeInfo));
      }
    }
    return TypeEnvironment.ROOT.evaluate(
        new FunctionTypeExpr(new FunctionTypeInfo(VoidType.EXPR, required, positionalParams,
            namedNames.toArray(new String[0]), namedTypes.toArray(new TypeExpr[0]))));
  }

  // TODO(andrewkrieger): Throw on attempts to create type reps for illegal types, such as function
  // types with more required parameters than positional parameters, or function types with both
  // optional positional and optional named parameters.
  @Test
  public void illegalTypes() {}
}
