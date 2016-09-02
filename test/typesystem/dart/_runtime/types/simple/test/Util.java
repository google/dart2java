package dart._runtime.types.simple.test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import dart._runtime.types.simple.Type;
import dart._runtime.types.simple.TypeEnvironment;
import dart._runtime.types.simple.TypeExpr;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Testing utilities; these are designed to be statically imported into the type system unit tests.
 */
class Util {

  /**
   * Test-internal used to build and test a sub-hierarchy of types.
   * <p>
   * A {@code Node} represents one point in the sub-hierarchy. When creating a {@code Node}, the
   * test code declares a list of direct supertypes (for the purpose of testing). This doesn't have
   * to include all the supertypes (that's why it's a sub-hierarchy!).
   *
   * @see #testHierarchy
   */
  static class Node {
    final String name;
    final Type type;
    final List<Node> directSupertypes;
    final List<Node> directSubtypes;

    /**
     * Create a {@code Node} with no direct supertypes (at least, no supertypes in its
     * sub-hierarchy).
     *
     * @param name describes this type in failure messages
     * @param type the type to test
     */
    Node(String name, Type type) {
      this.name = name;
      this.type = type;
      this.directSupertypes = Collections.emptyList();
      this.directSubtypes = new ArrayList<>();
    }

    /**
     * Create a {@code Node} with no direct supertypes (at least, no supertypes in its
     * sub-hierarchy).
     * <p>
     * The {@code typeExpr} is evaluated in the root type environment. This constructor forwards to
     * {@link #Node(String, Type)}; it is provided as a convenience.
     *
     * @param name describes this type in failure messages
     * @param typeExpr the type to test (expression evaluated in root type environment)
     */
    Node(String name, TypeExpr typeExpr) {
      this(name, TypeEnvironment.ROOT.evaluate(typeExpr));
    }

    /**
     * Create a {@code Node} with the given direct supertypes (at least, these are the supertype
     * edges in this {@code Node}'s sub-hierarchy).
     *
     * @param name describes this type in failure messages
     * @param type the type to test
     * @param directSupertypes direct supertypes of this type in the sub-hierarchy
     */
    Node(String name, Type type, Collection<Node> directSupertypes) {
      this.name = name;
      this.type = type;
      this.directSupertypes = Collections.unmodifiableList(new ArrayList<Node>(directSupertypes));
      this.directSubtypes = new ArrayList<>();

      for (Node sup : this.directSupertypes) {
        sup.directSubtypes.add(this);
      }
    }

    /**
     * Create a {@code Node} with the given direct supertypes (at least, these are the supertype
     * edges in this {@code Node}'s sub-hierarchy).
     * <p>
     * The {@code typeExpr} is evaluated in the root type environment. This constructor forwards to
     * {@link #Node(String, Type, Collection)}; it is provided as a convenience.
     *
     * @param name describes this type in failure messages
     * @param typeExpr the type to test (expression evaluated in root type environment)
     * @param directSupertypes direct supertypes of this type in the sub-hierarchy
     */
    Node(String name, TypeExpr typeExpr, Collection<Node> directSupertypes) {
      this(name, TypeEnvironment.ROOT.evaluate(typeExpr), directSupertypes);
    }

    /**
     * The proper subtypes of this type in the sub-hierarchy (does not include {@code this}).
     */
    Stream<Node> properSubtypes() {
      return directSubtypes.stream().flatMap(Node::thisAndSubtypes);
    }

    /**
     * The full set of subtypes of this type in the sub-hierarchy (includes {@code this}).
     */
    Stream<Node> thisAndSubtypes() {
      return Stream.concat(Stream.of(this), properSubtypes());
    }

    /**
     * The proper supertypes of this type in the sub-hierarchy (does not include {@code this}).
     */
    Stream<Node> properSupertypes() {
      return directSupertypes.stream().flatMap(Node::thisAndSupertypes);
    }

    /**
     * The full set of supertypes of this type in the sub-hierarchy (includes {@code this}).
     */
    Stream<Node> thisAndSupertypes() {
      return Stream.concat(Stream.of(this), properSupertypes());
    }
  }

  /**
   * Test the sub-hierarchy with the given roots.
   * <p>
   * The sub-hierarchy under test might have multiple roots (i.e. maximal elements), even though the
   * actual Dart type-system has a single {@code top} node. This method will enumerate all subtypes
   * in the sub-hierarchy, then test all relations that ought to hold in the sub-hierarchy.
   *
   * @param roots roots of the sub-hierarchy
   */
  static void testHierarchy(Node... roots) {
    Set<Node> universe =
        Arrays.asList(roots).stream().flatMap(Node::thisAndSubtypes).collect(Collectors.toSet());
    for (Node n : universe) {
      Set<Node> unrelated = new HashSet<Node>(universe);
      assertTrue(n.name + "   ==   " + n.name, n.type.isSubtypeOf(n.type));
      unrelated.remove(n);

      for (Node sub : n.properSubtypes().collect(Collectors.toSet())) {
        assertTrue(n.name + "   :>   " + sub.name,
            sub.type.isSubtypeOf(n.type) && !n.type.isSubtypeOf(sub.type));
        unrelated.remove(sub);
      }

      for (Node sup : n.properSupertypes().collect(Collectors.toSet())) {
        assertTrue(n.name + "   <:   " + sup.name,
            n.type.isSubtypeOf(sup.type) && !sup.type.isSubtypeOf(n.type));
        unrelated.remove(sup);
      }

      for (Node u : unrelated) {
        assertFalse(n.name + "   <>   " + u.name,
            u.type.isSubtypeOf(n.type) || n.type.isSubtypeOf(u.type));
      }
    }
  }
}
