# Generic Specializations
This documents describes an optimization to generate efficient Java code when
using generic Dart classes with primitive (`int`, `double`, `bool`) type
arguments.

In generated Java code, type variable types are represented as `Object`,
regardless of whether Java generics are used or not. For example, Dart code is
translated as follows.

```dart
// Dart
class Foo<T> {
  T field;

  T getTheField() {
    return field;
  }
}
```

```java
// Java
public class Foo {
  Object field;

  public Object getTheField() {
    return field;
  }
}
```

When using `Foo<int>` in Dart, the generated Java code cannot take advantage of
unboxed Java `int`s, because values are stored in variables of type `Object`.
This can be a bottleneck particularly when using `List<int>`, where we can
expect frequent reads/writes (maybe inside loops). Microbenchmarks show that
that the overhead of writing integers to an array of boxed `Integer` vs. writing
integers to an array of unboxed `int` in Java is around 300% (unboxed is 4x
faster when writing 100000 integers in a loop with 100 iterations).

To provide a fast implementation of Dart generics for primitive types, we
propose generating *type specializations* for `int`, `double`, and `bool`. A
specialization uses unboxed versions of these types internally and provides
additional methods whose signatures use unboxed types.

The following paragraphs describe the code that will be generated for the
example code shown above.

## Generic Interface
We generate an interface for every generic class.

```java
public interface Foo {
  Object getTheField;
}
```

This interface is used for all expressions of type `Foo<T>` where `T` is *not*
one of the three special types for which we have specializations. For example,
see how the following Dart code is translated.

```dart
// Dart
Foo<Object> o = ...;
Foo<String> s = o;
```

```java
// Java
Foo o = ...;
Foo s = o;
```

Type checks will be inserted automatically to enforce soundness. Specifically,
there are methods `cast` and `check` provided by the type system which will be
utilized.

## Generic Implementation
We generate a generic implementation of the generic interface which is used
for instantiation and static method calls whenever the generic parameter `T` is
not one of the three special types.

```java
public interface Foo {
  Object getTheField;

  public static Foo _new(Class<?> genericType) {
    return new Generic(genericType);
  }

  public static class Generic implements Foo {
    Class<?> genericType;

    Object field;

    public Generic(Class<?> genericType) {
      this.genericType = genericType;
    }

    public Object getTheField() {
      return field;
    }
  }
}
```

```dart
// Dart
Foo<Object> o = new Foo<Object>();
Foo<String> s = new Foo<String>();
```

Generic classes are always instantiated through the `_new` factory method of
their interfaces.

```java
// Java
Foo o = Foo._new(Object.class);
Foo s = Foo._new(String.class);
```

Type arguments are passed as the first argument. The actual implementation will
use classes from the new type system instead of `java.lang.Class`.


## Implementation of Specializations
We generate one type specialization per `int`, `double`, and `bool`, up front
(regardless of whether these are used or not; this is hard to detect and requires
knowledge of the entire program).

```java
public interface Foo {
  Object getTheField;

  public static Foo _new(Class<?> genericType) {
    if (genericType == int.class) {
      return new _int();
    } else {
      return new Generic(genericType);
    }
  }

  public class Generic implements Foo {
    /* ... */
  }

  public class _int implements Foo {
    int field;

    public _int() {

    }

    public int getTheField$primitive() {
      return field;
    }

    public Object getTheField() {
      return getTheField$primitive();
    }
  }
}
```

Since these specializations implement the interface `Foo`, it is possible to
assign them variables of generic (unspecialized) types.

```dart
// Dart
Foo<int> i = new Foo<int>();
Foo<Object> o = new Foo<Object>();
o = i;
```
Assignments of expressions where a specialized type is expected result in an 
automatic cast.

```java
// Java
Foo._int i = (Foo._int) Foo._new(int.class);
Foo o = Foo._new(Object.class);
o = i;
```

Method calls on specialized objects are handled in a different way: the
`$primitive` suffix is added to all method names.

```dart
// Dart
Foo<int> i = new Foo<int>();
Foo<Object> o = new Foo<int>();
i.getTheField();
o.getTheField();
```

```java
// Java
Foo._int i = (Foo._int) Foo._new(int.class);
Foo o = Foo._new(int.class);
i.getTheField$primitive();  // returns int
o.getTheField();            // returns Object (boxed Integer at runtime)
```

## Dart Type Checks
When calling a method of `Foo` that consumes a value of type `T`, we have to
insert runtime type checks at the beginning of that method to ensure that the
argument is actually assignable to `T`.

```dart
List<String> stringList = new List<String>();
List<Object> objectList = stringList;
objectList.add(123);    // should fail at runtime
```

No type checks are required when passing arguments using a primitive
specialization method.

## Specializations for Multiple Type Arguments
Our implementation will generate specializations for classes with one or two
type arguments, resulting in 4 or 16 classes, respectively. This way, we can
have efficient implementations for the most common data structures `List`,
`Map`, `Set`, etc. Classes with more than two specializations have only a single
implementation `Generic`.

## Casts for Producer Methods
The Dart class `Foo<String>` corresponds to the Java class `Foo.Generic` or its
interface `Foo`. Calling `getTheField` returns an `Object` object instead of a
`String` object. Therefore, we have to insert a cast whenever a generic
implementation is used.

```dart
// Dart
Foo<String> s = new Foo<String>();
Foo<int> i = new Foo<int>();
String fs = s.getTheField();
int fi = i.getTheField();
```

```java
// Java
Foo s = Foo._new(String.class);
Foo._int i = Foo._new(int.class);
String fs = (String) s.getTheField();
int fi = i.getTheField$primitive();
```

## Subclassing and Inheritance
In case a generic class `Bar` with zero or one or two type arguments subclasses
another generic class `Foo` of specialized type `int`, all nested specializations
subclass that specialization.

```dart
// Dart
class Bar<T> extends Foo<int> { 

}
```

```java
// Java
public interface Bar {
  public class Generic extends Foo._int { }
  public class _int extends Foo._int { }
}
```

In case a generic class `Bar` with one or two type arguments subclasses another
generic class `Foo` with one or two type arguments, where the same type
variables are used in the definition of `Bar` and the instantiation of `Foo`,
every nested specialization subclasses its respective specialization in `Foo`.

```dart
// Dart
class Bar<T> extends Foo<T> {

}
```

```java
// Java
public interface Bar {
  public class Generic extends Foo.Generic { }
  public class _int extends Foo._int { }
}
```

In case a generic class `Bar` with three or more type arguments subclasses
another generic class `Foo`, all specializations subclass `Foo.Generic`.

```dart
// Dart
class Bar<A, B, C> extends Foo<A> {

}
```

```java
// Java
public interface Bar {
  public class Generic extends Foo.Generic { }
}
```

