# Type expressions and type keys

There are two concepts that are closely related, but which it might be useful to
separate: _type expressions_ and _type keys_.

## Type Expressions

A type expression is essentially a
[type constructor](https://en.wikipedia.org/wiki/Type_constructor): it describes
how to build types. For example, we might use the following Java data types as
type expressions:

```java

public abstract class TypeExpr {
  // I am deliberately not specifying what "context" is here. It includes
  // enough information to map type names and type variables to their values.
  public Type evaluate(Context context);
}

public class InterfaceTypeExpr extends TypeExpr {
  private final DartClassDeclaration clazz;
  private final List<TypeExpr> actualTypeParams;

  public InterfaceTypeExpr(DartClassDeclaration clazz,
      List<Type> actualTypeParams) {
    this.clazz = clazz;
    this.actualTypeParams = actualTypeParams;
  }

  @Override
  public Type evaluate(Context context) {
    // Evaluate the `TypeExpr`s in `actualTypeParams`, then either create a new
    // `InterfaceType` instance based on the given `clazz` and the evaluated
    // type parameters or return a cached instance.
  }
}

public class FunctionTypeExpr extends TypeExpr {
  // I'm ignoring Dart's optional parameters, just to make this simpler.
  private final List<TypeExpr> paramTypes
  private final TypeExpr returnType;

  public FunctionTypeExpr(List<TypeExpr> paramTypes, TypeExpr returnType) {
    this.paramTypes = paramTypes;
    this.returnType = returnType;
  }

  @Override
  public Type evaluate(Context context) {
    // Evaluate the paramater and return types, then create a new `FunctionType`
    // instance or return a cached instance.
  }
}

public class TypeVariable extends TypeExpr {
  @Override
  public Type evaluate(Context context) {
    // Resolve this variable in `context`; by assumption, `context` contains
    // enough information to resolve this variable to a `Type`.
  }
}
```

Logically, the difference between a `TypeExpr` and a `Type` is that `TypeExpr`s
can contain `TypeVariable`s, whereas `Type`s must be fully resolved to reified
types. For example, `List<T>` is a `TypeExpr`, whereas `List<int>` could be a
`Type` (it could also be a `TypeExpr` that happens not to include any type
variables). Evaluating a `TypeExpr` in the form above is straightforward:
recursively evaluate any dependent expressions (like `actualTypeParams` or
`paramTypes`), then construct a new `Type` instance or retrieve one from cache.
During this recursion, any `TypeVariable`s are evaluated by resolving them in
the given context. Of course, an actual implementation will have to specify
exactly what this context is, but we'll defer that question for now.

### Requirements for type expressions

* A type expression, together with contextual information like the values of
  type variables, must evaluate to a reified type.
    * In particular, either the type expression or the context must store enough
      information to resolve a class name to the underlying class, even across
      libraries and in the presence of imports from other libraries.
    * If two type expressions (possibly in two different contexts) resolve to
      the same (logical) type, then either (a) they should evaluate to the same
      `Type` instance, or (b) they should evaluate to semantically equal `Type`
      instances.
* Evaluating type expressions should be efficient: fast, and (if possible) low
  memory overhead. Experience shows that "simple" is probably even more
  important than "fast".

### Possible implementations of type expressions

* The above example uses a tree-based implementation. Expressions are rooted at
  a reference to a Dart class, a function signature, a type variable, or one of
  the special types (`top`, `bottom`, or `void`). Creating expressions in the
  generated code is clumsy, especially in the case of self-referential
  expressions (such as `class num implements Comparable<num>`).
* Type expressions can be implemented via strings. To evaluate an expression
  entails parsing it into an explicit or implicit tree. This implementation
  probably has less memory overhead, but is likely slower to parse, and
  certainly requires more complexity in order to support parsing.

Given the simplicity, we plan to use the tree-based implementation, at least to
start with. If we keep things properly separated, we should be able to
experiment with alternate implementations later on.

## Type keys

A type key is a simple data type that uniquely refers to a single type; it is
useful for caching the results of evaluating type expressions. For example,
suppose that we generate the following code for `Map.forEach`:

```java
class Map {
  public static final TypeVariable[] formalTypeParams = new TypeVariable[] {
    new TypeVariable("K"), new TypeVariable("V") };

  private static final FunctionTypeExpr KVToVoidTypeExpr = new FunctionTypeExpr(
      Arrays.asList(formalTypeParams[0], formalTypeParams[1]),
      dart._runtime.types.VoidType.INSTANCE);

  // Assume that `Closure` is what we use to represent closures.
  void forEach(Closure closure) {
    KVToVoidTypeExpr.evaluate(this.context).check(closure);
    // Implementation
  }
}
```

We would like to avoid the full call to `evaluate` every time the client
programmer calls `forEach` on a `Map<String, int>` (say). However, the
tree-based `TypeExpr`s that are easy to evaluate are not particularly suited for
use as keys in a cache (they aren't easily hash-able or equality-comparable).
However, we can convert a `TypeExpr` to a unique representation, called its type
key. These keys might be simple strings, which are easy to use as keys in a
cache. For example, each `Context` might store a `Map<String, Type>`, and
whenever a `TypeExpr` is evaluated against that `Context`, the first step is to
check the `Map` for the key of the `TypeExpr`. If the result is already saved in
the `Map`, return it immediately. Otherwise, compute the `TypeExpr`, store the
result in the map, and return.

### Requirements for type keys

* Type keys must be simple data structures that are easily used as keys in a
  `Map`. In particular, they should use minimal memory overhead, and
  hashing/comparing them should be fast. Fixed-width integers would be ideal,
  although flat-text strings are a good second choice.
* Type keys must uniquely identify a type expression. In the extreme case, we
  might use the expression itself as a key (although this fails to meet the
  first requirement).
* Ideally, if two expressions are semantically equivalent (in the sense that,
  for any given context, both expressions evaluate to the same type), then their
  keys are identical or equal.

### Possible implementations of type keys

* Fixed-width integers are possible, but it's difficult to simultaneously
  enforce "each key maps to a unique type expression" and "semantically
  equivalent expressions have the same key". There are a couple of solutions:
    * Give up "semantically equivalent expressions have the same key". Each
      instance of a type expression has a unique key. Individual libraries can
      and should reuse instances (so that all references to `List<int>` in the
      same library use the same expression, and hence the same key), but across
      libraries we use different keys. This reduces cache performance (more
      cache misses and more cache entries).
    * Do more work at link-time/run-time to canonicalize type expressions. Every
      time as type expression is created, check to see if a semantically
      equivalent expression already exists; if so, re-use it (or at least re-use
      its key). This might be somewhat difficult because the implementation of
      type expressions is not optimized for hashing or comparison, but is still
      possible. Canonicalizing would lead to extra overhead, probably at startup
      time, which may outweight the benefits.
    * For completeness, giving up "each key maps to a unique expression" is not
      viable. At best, it turns type keys into (imperfect) hashes, so we still
      need to support direct comparison of type expressions.
    * We could use a complex strategy that perfectly hashes "most" expressions
      to integers, with a special sentinel value that means "hash failed - use
      the string key instead". Or, we could use variable-width integers (a.k.a.
      strings) directly. This smells like premature optimization.
* Variable-length strings are a solid choice. They are still fairly easy to hash
  and compare, but (allowing for variable length) they can easily encode any
  type expression, in a way that they keys match if and only if two expressions
  are semantically equivalent.
* If the type expressions are themselves strings (or some other data type that
  is easily hashed or compared), we can use the expressions themselves as keys.
