# Type system public API

## Storing type information

All ordinary classes will declare a synthetic `Type` field (even if users
override `runtimeType`, we should probably access the known-good value). Type
information for `@JavaClass`es needs some thought, but we can hopefully make a
single Java class (i.e., a value of type `java.lang.Class`) correspond to a
single Dart type, and use a lookup table. For the purposes of this document, we
assume that there is a global function called `getTrueRuntimeType` that takes an
object (i.e., an instance of some subclass of `java.lang.Object`) and returns
its runtime type.

## Type info

During compilation, we generate `InterfaceTypeInfo` instances for each class,
which essentially capture the information from the Dart `class` declaration
(namely, the superclass, mixin, interfaces, and type variables, as well as the
class name for debugging purposes). We will also generate `FunctionTypeInfo` in
the places where we will need to generate Function types. The `*TypeInfo`
classes are not directly converted to types; instead, they are referenced by
`TypeExpr`s. The `*Info` classes are implementation details. As far as the type
system is concerned, their purpose is to support `isSubtypeOf` computations.
The `*TypeInfo` classes might also be exposed by reflection, but that's outside
the scope of this document.

Type variables are saved in the `InterfaceTypeInfo` and `FunctionTypeInfo`
objects. Type variables are essentially opaque Java objects of class
`TypeVariableExpr`; they have a name (for debugging purposes), but two
`TypeVariableExpr`s are equal only if they are identical. Each
`InterfaceTypeInfo` has its own list of distinct `TypeVariableExpr`s. The `Expr`
suffix is used to indicate that a type variable is a `TypeExpr` as opposed to a
reified `Type` (see below).

## Type expressions

A `TypeExpr` is used to create types. A `TypeExpr` may be any of:

* A special type, like `Top` or `Void`.
* An interface type, comprised of an `InterfaceTypeInfo` and zero or more
  type arguments (which are themselves `TypeExpr`s)
* A function type, comprised of a `FunctionTypeInfo`, and various parameter
  types and return type (again, all of these are `TypeExpr`s)
* A `TypeVariableExpr` from an enclosing `InterfaceTypeInfo` or
  `FunctionTypeInfo`.

## Type environments

A `TypeEnvironment` maps `TypeVariableExpr`s to values. The root
`TypeEnvironment` contains no `TypeVariableExpr`s. Every instantiation of a
generic class or function type induces a new `TypeEnvironment` that binds its
formal type variables to their actual reified values.

`TypeEnvironment`s are used to evaluate `TypeExpr`s into reified `Type` values.
When a `Type` is needed, use the "innermost" `TypeEnvironment` (which might be
the environment of the enclosing generic class, the enclosing generic function,
or the root environment) and call `evaluate` on a `TypeExpr`.

There is also a singleton `TypeEnvironment.ROOT` that has no type variables in
scope. This should be used in top-level functions. (Even in classes with no
generic parameters, use `getTrueRuntimeType(this).env`. If the class has no type
parameters, the `env` property will alias to `TypeEnvironment.ROOT`.

### Type environments for interface types

The `TypeEnvironment` for a generic class includes bindings for the
`TypeVariable`s defined in that class, as well as those declared in the
(transitive) superclasses or mixins of the class. The reason is that the class
might inherit method implementations from a superclass or mixin, and the
inherited method might refer to a superclass/mixin type variable. For example,
in the following code, when a `Multimap<int, String>` instance is passed to
`Map.forEach`, the instance's `TypeEnvironment` must be able to resolve `Map.V`
to `List<String>`:

```dart
class Map<K,V> {
  void forEach(void action(K k, V v)) {}
}

class Multimap<K,V> extends Map<K, List<V> {}
```

### Type environments for functions

The `TypeEnvironment` for a function must be able to resolve the
`TypeVariableExpr`s from enclosing functions and classes as well, as in this
example:

```dart
class Cls<A> {
  dynamic outer<B>() {
    dynamic inner<C>() {
      return () {
        print('A = $A');
        print('B = $B');
        print('C = $C');
      };
    }
    return inner<List<B>>;
  }
}

new Cls<int>().outer<String>()()();

// Prints something like:
//     A = int
//     B = String
//     C = List<String>
```

### Temporary type environments

There is one additional source of `TypeEnvironment`s. As an internal detail,
when the type system is instantiating an interface type, it creates a temporary
`TypeEnvironment` that binds the type variables of the class under
instantiation (but not any of its superclasses). This environment is used to
resolve any type variables in the supertype declarations (such as, say
`List<T> implements Iterable<T>`), and is then discarded. Later access to the
newly-instantiated class's `TypeEnvironment` includes the supertypes, as
described above.

## Reified types 

Reified types are instances of `Type`, and are created by calling
`TypeEnvironment.evaluate`. Reified types support type testing via `isSubtypeOf`
as well as convenience methods like `cast` and `check` (which take in an object,
call `isSubtypeOf` against the `Object`'s runtime type, and throw `CastError` or
`TypeError` if the object does not belong to the given `Type`).

## Sample usage

User code (in library `dart:core`):

```dart
class Object {}

class String extends Object {}

class Iterable<E> extends Object {}

class List<E> implements Iterable<E> {}

class Map<K,V> extends Object {
  Iterable<K> keys() {
    return new Iterable<K>(/* list of keys to iterate over */);
  }

  void forEach(void action(K k, V v)) {
    // Implementation...
  }
}

void foo() {
  // Assume that there is a constructor `List<E>(int _)`.
  var l = new List<List<String>>(10);
  var m = new Map<String, Object>();
}
```

Here is the generated code:

```java
package dart.core;

// Because `Object` is a `@JavaClass`, this goes in the helper class.
class ObjectHelper {
  public static final InterfaceTypeInfo typeInfo = new InterfaceTypeInfo(
      /* library */ "dart:core",
      /* class name */ "Object");
}

// Because `String` is a `@JavaClass`, this goes in the helper class.
class StringHelper {
  public static final InterfaceTypeInfo typeInfo = new InterfaceTypeInfo(
      /* library */ "dart:core",
      /* class name */ "String");

  static {
    typeInfo.superclass = new InterfaceTypeExpr(ObjectHelper.typeInfo);
  }
}

class Iterable {
  public static final InterfaceTypeInfo typeInfo = new InterfaceTypeInfo(
      /* type variable names */ new String[] { "E" },
      /* library */ "dart:core",
      /* class name */ "Iterable");

  static {
    typeInfo.superclass = new InterfaceTypeExpr(ObjectHelper.typeInfo);
  }

class List {
  public static final InterfaceTypeInfo typeInfo = new InterfaceTypeInfo(
      /* type variable names */ new String[] { "E" },
      /* library */ "dart:core",
      /* class name */ "List");

  static {
    typeInfo.superclass = new InterfaceTypeExpr(ObjectHelper.typeInfo);
    typeInfo.interfaces = new InterfaceTypeExpr[] {
      new InterfaceTypeExpr(Iterable.typeInfo, new TypeExpr[] {
        typeInfo.typeVariables[0]
      })
    };
  }
}

class Map {
  public static final InterfaceTypeInfo typeInfo = new InterfaceTypeInfo(
      /* type variable names */ new String[] { "K", "V" },
      /* library */ "dart:core",
      /* class name */ "Map");

  static {
    typeInfo.superclass = new InterfaceTypeExpr(ObjectHelper.typeInfo);
  }

  Iterable keys() {
    return new Iterable(
      getTrueRuntimeType(this).env.evaluate(
        // An InterfaceTypeExpr representing `Iterable<K>`
        new InterfaceTypeExpr(Iterable.typeInfo, new TypeExpr[] {
          Map.typeInfo.typeVariables[0]
        })
      ), /* list of keys to iterate over */);
  }

  void forEach(Closure action) {
    getTrueRuntimeType(this).env.evaluate(
      // a FunctionTypeExpr representing `(K,V) -> void`
      new FunctionTypeExpr(
        /* parameters */ new TypeExpr[] {
          Map.typeInfo.typeVariables[0],
          Map.typeInfo.typeVariables[1]
        },
        /* return type */ dart._runtime.types.Special.VoidExpr
      )
    ).check(action);
    // Implementation...
  }
}

void foo() {
  List l = new List(
    TypeEnvironment.ROOT.evaluate(
      // An InterfaceTypeExpr representing `List<List<String>>`
      new InterfaceTypeExpr(List.typeInfo), new TypeExpr[] {
        new InterfaceTypeExpr(List.typeInfo, new TypeExpr[] {
          new InterfaceTypeExpr(StringHelper.typeInfo)
        })
      }
    ), 10);

  Map m = new Map(
    TypeEnvironment.ROOT.evaluate
      // An InterfaceTypeExpr representing `Map<String, Object>`
      new InterfaceTypeExpr(Map.typeInfo), new TypeExpr[] {
        new InterfaceTypeExpr(StringHelper.typeInfo),
        new InterfaceTypeExpr(ObjectHelper.typeInfo)
      }
    )
  );
}
```
