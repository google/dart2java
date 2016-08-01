_(This replaces what we previously called "interceptors, and vastly simplifies
the "delegates" mechanism that we temporarily talked about. This document is
referenced by the dart2java source code.)_

[TOC]

# Implementing methods on Java types

There are two cases in which we delegate instance-method calls in Dart to a
static helper function: `Object` methods and core types.

# Object methods

The Dart `Object` class has five methods, which we must be able to call on any
type (including primitive types and `null`):

```dart
class Object {
  bool operator ==(other);
  int get hashCode;
  String toString();
  dynamic noSuchMethod(Invocation invocation);
  Type get runtimeType;
}
```

Invoking these methods on a variable of static type `Object` results in the
compiler emitting a call to a static function in
`dart._runtime.helpers.ObjectHelper`. This call does need to check the run-time
type of its `self` argument, since a user-defined class might provide an
implementation for one of these methods as an instance method.

Ordinary user-defined classes implement from a synthetic interface `DartObject`,
which defines these 5 methods. If any of these is invoked on a user-defined
class, the compiler emits an ordinary instance method invocation.

User-defined `@JavaClass` classes do not extend from `DartObject`. For these,
the compiler emits a call to `ObjectHelper`, just like for `Object`. Currently,
a `@JavaClass` cannot extend from a user-defined class or implement a
user-defined interface, so there is no need to worry about a `@JavaClass`
method being invoked differently based on the static type of the reference.

If variable has run-time type extending a `@JavaClass` and an
`Object`-method is called through a statically typed `@JavaClass` reference,
the method will be dispatched through `ObjectHelper` (just as if the static type
of the reference were the less-specific type `Object`).

# Core type methods

## String and bool

Both `String` and `bool` are `@JavaClass` classes, implemented by
`java.lang.String` and `java.lang.Boolean` respectively. (`bool` might sometimes
use the primitive `boolean` type instead, but it is wrapped in a `Boolean`
instance when needed.)

The instance methods on these classes are implemented via dispatch to static
methods on `StringHelper` and `BoolHelper`. The helper classes also implement
the `Object` methods, so that a call to an `Object` method on a receiver of
static type `bool` is dispatched just like a `bool` method on the same receiver.

## num, int, and double

The numeric types `num`, `int`, and `double` are slightly more complex because
of their type hierarchy. Still, their instance methods are implemented via
static methods in helper classes. `NumHelper` will frequently type-test the
receiver and forward to `IntHelper` or `DoubleHelper` appropriately. Again, the
various helper classes implement the `Object` methods, and `IntHelper` and
`DoubleHelper` explicitly implement all the `num` methods.

## Long-term considerations

If we eventually implement multiple subtypes of `int`, `String`, or `double`
(which are `abstract` in the SDK), the helper methods have to be able to accept
any valid type for `self`. Because these subtypes are never user-visible, the
dispatch should still work as expected (by calling `IntHelper`, `DoubleHelper`,
or `StringHelper`).

# Dispatching a method invocation

Assuming that the static type of the receiver is not `dynamic`:

1. If the static receiver type is a `@JavaClass` (including `Object`, `String`,
   `bool`, `num`, `int`, or `double`) and if the method uses a helper function,
   then dispatch to the helper method.
    * `String`, `bool`, `num`, `int`, and `double` have their own helper
      classes, with helper functions for all the methods on these types. There
      are no `@JavaMethod` methods on these types.
    * `ObjectHelper` has helper functions for the five object methods. These
      helper functions should forward to the `StringHelper` implementation if
      the run-time receiver type is `String`, etc.
    * User-defined Java classes use `ObjectHelper` for the five object methods.
      All other methods on a user-defined `@JavaClass` must be instance methods
      on the underlying Java type (probably with some metadata; still TBD).
2. Otherwise, the method is an actual instance method on the receiver Java
   object. There are a few possibilities here, but codegen is the same for all
   of them:
    * The method is an `Object` method, and the receiver implements the
      compiler's synthetic `DartObject` interface (because the receiver is an
      ordinary user-defined class). The `DartObject` interface defines the five
      `Object` methods, so an instance method call is valid (except for the
      usual caveat about `null`).
    * The method is a `@JavaMethod` and the receiver is a `@JavaClass`.
    * The method is an ordinary user-defined Dart method, and the receiver is
      an ordinary user-defined class.
    * The method is an override, and the overridden method belongs to one of the
      bullet points above.

Note that if we reach rule 2, it is impossible for the run-time receiver type to
be any of `String`, `bool`, `num`, `int`, or `double`. Suppose that the run-time
type is one of these types. Then:

* If we got past rule 1, the static receiver type is not any of `String`,
  `bool`, `num`, `int`, or `double`.
* By assumption, the run-time type is one of `String`, `bool`, `num`, `int`, or
  `double`. Therefore, by examining the Dart type hierarchy, the static receiver
  type must be `Object`. Only the `Object` methods can be invoked on static
  receiver type `Object`, but all of those are dispatched in step 1.
* QED!

This proof is important, since if we represent `int` with a primitive `int`, we
cannot actually call instance methods on it.

On the other hand, it's entirely possible under these rules to (attempt to) call
an instance method on `null`. We don't yet know how to handle null effectively.
 
> Under these rules, a `@JavaClass` can't override `Object` methods. It doesn't
> make sense for a `@JavaClass` to override `runtimeType` or `noSuchMethod`,
> but it could make sense to use its implementation of `toString`, `hashCode`,
> or `equals` for the analogous Dart methods. If we decide to use these three
> Java instance methods, we could either:
> * Always generate instance method invocations for these three `Object`
>   methods. Step one stays the same (core types are still special), but step 2
>   now only applies if the method is `runtimeType` or `noSuchMethod`. We still
>   can't reach step 3 with a primitive receiver type and a method like
>   `toString`, since either it would have been dispatched in step 1 (if the
>   static type is known to be `int` etc.) or else the receiver is statically
>   typed as `Object`, and hence the run-time type is `java.lang.Integer` etc.
> * Have `ObjectHelper.toString`, `ObjectHelper.getHashCode`, and
>   `ObjectHelper.operatorEquals` call the appropriate instance method on the
>   receiver.
>
> We'd want to check the semantics before making this change, to make sure there
> are no surprises hidden here.


# Case studies

## Object methods

### Core types
```dart
new Object().runtimeType;
3.runtimeType;
(3 as Object).runtimeType;
```

```java
ObjectHelper.getRuntimeType(new Object());
IntHelper.getRuntimeType(3);
ObjectHelper.getRuntimeType(3);
```

### User-defined classes

```dart
class Foo { String toString() => "Foo!"; }

new Foo().toString();
(new Foo() as Object).toString();
```

```java
class Foo implements DartObject {
  String toString() { return "Foo!"; }
}

new Foo().toString();
ObjectHelper.toString((Object)new Foo());  // Invokes
                                           // ((DartObject)foo).toString().
```

### User-defined @JavaClass classes
```dart
@JavaClass("java.util.ArrayList") class ArrayList {
  // A method on the underlying Java class.
  external Object get(int index);
}
class Bar extends ArrayList {
  String toString() => "Bar";

  Object get(int index) => null;
}

new ArrayList().toString();
(new ArrayList() as Object).toString();
new ArrayList().get(3);
// (new ArrayList() as Object).get(3);  // static type error

new Bar().toString();
(new Bar() as Object).toString();
new Bar().get(3);
(new Bar() as ArrayList).get(3);
// (new Bar() as Object).get(3);        // static type error
```

```java
class Bar extends java.util.ArrayList implements DartObject {
  // Overrides DartObject.toString()
  String toString() { return "Bar"; }

  // Overrides ArrayList.get
  Object get(int index) {
    return null;
  }
}

ObjectHelper.toString(new java.util.ArrayList());
ObjectHelper.toString((Object)new java.util.ArrayList());
new ArrayList().get(3);                    // throws IndexOutOfboundsException,
                                           // but that's a programmer error
                                           // unrelated to method dispatch.

new Bar().toString();
ObjectHelper.toString((Object)new Bar());  // Invokes
                                           // ((DartObject)bar).toString().
new Bar().get(3);
((java.util.ArrayList)new Bar()).get(3);   // Calls the overload, due to Java
                                           // virtual call semantics.
```

### Generics
```dart
void f<T>(T t) {
  t.runtimeType;
}

void g<T extends ArrayList>(T t) {
  t.runtimeType;
}

void h<T extends Bar>(T t) {
  t.runtimeType;
}
```

```java
void f(Object t) {
  ObjectHelper.getRuntimeType(t);  // Might call DartObject.getRuntimeType(), if
                                   // (f instanceof DartObject) == true
}

void g(java.util.ArrayList t) {
  ObjectHelper.getRuntimeType(t);  // Static type is `T extends ArrayList`, and
                                   // ArrayList is a @JavaClass.
}

void h(some.user.package.Bar t) {
  t.getRuntimeType();              // Bar implements DartObject, so this is a
                                   // valid interface method.
}
```

## Numeric types

### Basic operations
```dart
2 + 2;
2.0 + 2;
2 + 2.0;
2.0 + 2.0
```

```java
IntHelper.operatorPlus(2, 2);
DoubleHelper.operatorPlus(2.0, 2);
IntHelper.operatorPlus(2, 2.0);
DoubleHelper.operatorPlus(2.0, 2.0);
```

> Remark: As a future optimization, we could dispatch to a faster method is the
> static type of the argument is known. For example, the base implementation of
> `IntHelper.operatorPlus probably looks like:
>
> ```java
> Number operatorPlus(int self, Number other) {
>   if (other instanceof Integer) {
>     return Integer.valueOf(self + other.intValue());
>   } else if (other instanceof Double) {
>     return Double.valueOf(self + other.doubleValue());
>   } else {
>     throw;
>   }
> }
> ```
>
> If we detect that `other` is an `int`, we could replace this by a direct `int`
> addition in Java, or at least by a helper function that skips the type check.

### Static type num

```dart
(3 as num) + 4;

3 + (4 as num);

double average<T extends num>(List<T> l) {
  assert(l.isNotEmpty);
  T sum = l.first;
  for (int i = 1; i < l.length; i++) {
    sum += l[i];
  }
  return sum / l.length;
}
```

```java
NumHelper.operatorPlus((Number)3, 4);

IntHelper.operatorPlus(3, (Number)4);

// Type parameters aren't used in Java.
double average(List /*<Number>*/ l) {
  Number sum = l.getFirst();
  for (int i = 1; i < l.getLength(); i++) {
    sum = NumHelpers.operatorPlus(sum, l.operatorIndexRead(i));
  }
  return NumHelpers.operatorDivide(sum, l.getLength());
}
```

> A possible optimization is to replace generics with `T extends num` by two
> template-instantiations (one for `int` and one for `double`).

# Open questions

## null

Perhaps the biggest question is how to handle `null`. There are, as usual, a few
possibilities:
* Whenever dispatching an instance method (step 3 in the algorithm above), check
  that the receiver is not `null`. This could be a static function or a ternary
  operator.
* Always dispatch via a static function which does `null` checks.
* Accept different semantics: throw `java.lang.NullPointerException` instead of
  `dart.core.NoSuchMethodError`.
* Always wrap `null` values in some type that implements `DartObject`. The
  difficulty here is that the wrapped `null` has to be assignable to any type.

## Interoperation with Java

Once we have more complete support for declaring `@JavaClass` classes, perhaps
with something like `@JavaMethod` or `@JavaField` members, we may need to
revisit this.
