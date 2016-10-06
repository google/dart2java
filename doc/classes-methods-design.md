# Classes
For every user-defined Dart class, our compiler generates a Java class and a
Java interface. In the presence of generics, there might be additional classes
or interfaces due to specialization. There are a number of special classes that
have to be handled differently as described in the following sections.

Note on nullability: `int`, `bool`, and `double` are not nullable anymore.
Assigning `null` to either one of these types will result in a compile-time
error (enforced by an Analyzer flag) or a runtime error (`NullPointerException`)
if not detectable at compile time

## Types of Classes
We distinguish two types of classes: Ordinary user-defined Dart classes and
"special" classes. `CompilerState` defines which Dart classes are special.
These classes are handled differently throughout code generation. Our intuition
is that special classes are classes, for which existing Java types should be
reused, for both performance and interoperability reasons. We store the
following information for special classes (as part of `ClassImpl`).

* Dart Kernel AST class object
* `javaClass`: The Java class that implements the Dart class.
* `javaInterface`: The Java interface for the Dart class.
* `javaLValueType`: The type that is used when generating variable declarations.
* `javaBoxedLValueType`: The type that is used when the Dart class has to appear
  in a Java generics clause. This is required for specialization.
* `helperClass`: A Java class containing instance methods of the Dart class.
  This is required because we cannot add new instance methods to existing Java
  classes.
* `mappedMethodNames`: Maps Dart methods to different Java method names. This is
  required to make certain Java methods available in Dart. For example, a Java
  instance method with the name `get` cannot be called from Dart.

Before compilation starts, basic Dart classes are set up as follows. Programmers
can use the `@JavaClass` annotation to make their own Java classes available in
Dart code.

* `dart:core.Object`
    * `javaClass = dart._runtime.base.DartObject`
    * `javaInterface = dart._runtime.base.Object_interface`
    * `javaLValueType = java.lang.Object`
    * `javaBoxedLValueType = java.lang.Object`
    * `helperClass = dart._runtime.helpers.ObjectHelper`
* `dart:core.bool`
    * `javaLValueType = boolean`
    * `javaBoxedLValueType = java.lang.Boolean`
    * `helperClass = dart._runtime.helpers.BoolHelper`
* `dart:core.int`
    * `javaLValueType = int`
    * `javaBoxedLValueType = java.lang.Integer`
    * `helperClass = dart._runtime.helpers.IntegerHelper`
* `dart:core.String`
    * `javaClass = java.lang.String`
    * `helperClass = dart._runtime.helpers.StringHelper`
* `dart:core.num`
    * `javaLValueType = java.lang.Number`
    * `javaBoxedLValueType = java.lang.Number`
    * `helperClass = dart._runtime.helpers.NumberHelper`

In generated Java code, `javaLValueType` is used for variable declarations etc.
If no `javaLValueType` is given, we use `javaInterface`. If no `javaInterface`
is given, we use `javaClass`. One of the three must be defined.

Notice that `dart:core.List` and `dart:core.Map` are *not* special.

Methods defined on `dart:core.Object` are handled in a different way. Such
methods are *always* forwarded to `ObjectHelper`.

### Examples
The following examples illustrate how special classes influence code
generation (simplified code, that does not take into account special handling of
constructors etc.).

```dart
// Dart code
Object o = new Object();
int i = 10;
bool b = true;
double d = 1.2;
String s = "Hello World!";
num n = 10;

o.toString();
i.toString();
n.toString();
null.toString();
i.gcd(10);
n.abs();
```

```java
// Java code
Object o = new dart._runtime.base.DartObject();
int i = 10;
bool b = true;
double d = 1.2;
String s = "Hello World";
java.lang.Number n = 10;

dart._runtime.helpers.ObjectHelper.toString(o);
dart._runtime.helpers.ObjectHelper.toString(i);
dart._runtime.helpers.ObjectHelper.toString(n);
dart._runtime.helpers.ObjectHelper.toString(null);
dart._runtime.helpers.IntegerHelper.gcd(i, 10);
dart._runtime.helpers.NumberHelper.abs(n);
```

## Implementation of `dart:core.Object`
`dart:core.Object` is implemented by `dart._runtime.base.Object`, which is a
hand-written Java class. All user-defined Dart classes have this class as a
superclass (maybe not directly). This class contains implementations of all
methods defined in `Object.dart`.

It also has a final field `dart2java$type` containing the runtime type of a Dart
object. For generic classes, this object also contains reified type information.

## Implementation of Helper Classes
Helper classes are hand-written Java classes containing only static methods that
take a self parameter and optionally other parameters. Most helpers have a
static `dart2java$typeInfo` field that contains the `InterfaceTypeInfo` field
describing the class (this field is also placed in all ordinary Dart classes).
It contains information about the superclass and implemented interfaces (for
type checks), as well as a list of all generic parameters, such that these can
be used in type expressions.

The helpers for all numeric classes must provide several overloadings of their
methods. For example, `NumberHelper` must provide three overloadings of
`operatorPlus`.

```java
public static Number operatorPlus(Number self, int other) {
  if (self instanceof Integer) {
    return IntegerHelper.operatorPlus((int) self, other);
  } else {
    // Must be double
    return DoubleHelper.operatorPlus((double) self, other);
  }
}

 public static Number operatorPlus(Number self, double other) {
  if (self instanceof Integer) {
    return IntegerHelper.operatorPlus((int) self, other);
  } else {
    // Must be double
    return DoubleHelper.operatorPlus((double) self, other);
  }
}

public static Number operatorPlus(Number self, Number other) {
  if (self instanceof Integer) {
    return IntegerHelper.operatorPlus((int) self, other);
  } else {
    // Must be double
    return DoubleHelper.operatorPlus((double) self, other);
  }
}
```

Note that most operators are translated to Java operators whenever possible,
e.g., the expression `1 + 1` is translated to the same Java expression `1 + 1`.
Addition of two `dart:core.num` object, however, uses `NumberHelper`, because
the operator `+` is not defined for `Number, Number` in Java. Method invocations
on dynamic objects also dispatch to helper classes using the Java Reflection API.

# Methods
This section describes how procedures definitions and invocations are
translated. It focuses on dynamic method invocations.

## Methods in Kernel AST
Dart has instance methods, static methods, instance/static getters/setter,
factory constructors, and ordinary constructors. All of them except for
ordinary constructors are represented as `Procedure` objects. The latter
one is represented as `Constructor` objects. `Procedure` objects have a `kind`
field indicating the type of the method.

## Translating Procedures
Dart procedures are translated by the visitor method `visitProcedure`. In the
absence of generic specialization, that process proceeds as follows.

1. Determine Java method name.
2. Build positional parameter list. Parameter AST nodes are basically name-type
   pairs. There is currently no support for named parameters.
3. If the method is generic (factory constructor are potentially generic): Build
   a type environment mapping type variables defined in the method to actual
   runtime types.
4. Check if the method is external. External method must be annotated with an
   `@JavaCall("...")` annotation or be patched (replaced) with an actual Dart
   implementation. An `@JavaCall("...")` implementation indicates that a certain
   (static) Java method should be called. In that case, the generated method
   calls that Java method and returns its return value (with the same
   arguments). For the remaining steps, we assume that the method is not
   external.
5. Check if the method is abstract. If not, translate the body of the method.
   That step might require adding some temporary variables (e.g., for `Let`
   expressions, which are used for `++` operators etc. in Kernel AST). These
   variables are collected in a map during translation and appended to the
   resulting body as variable declaration statements (at the beginning of the
   body). We also insert a final variable referencing the current type
   environment at the beginning of every method. That type environment variable
   has the name `dart2java$localTypeEnv` and is stored as an instance variable
   of the object (inside the `type` object). In case the method is generic, that
   environment is duplicated and extended with the environment that is passed
   into the method in Step 3.
6. Check if the method is a top-level method with the name `main`. In that case,
   this method is a program entry point and should have an additional
   `String[]` parameter.
7. Return a `MethodDef` Java AST node.


## Translating Procedures of Generic Classes
Classes with one or two generic type parameters are specialized. The compiler
generates separate classes with all possible bindings of type variables to one
of the types `int`, `double`, `bool`, or *generic* (i.e., not specialized).
Depending on the specialization of the class under translation, procedures get
different method names.

1. Take the original name of the method (or field in case of a getter).
2. Prepend the method name with `get` or `set`, if it is a getter or setter.
3. Append the name of the class in which the method is defined.
4. Append a suffix indicating the binding of type variables; e.g., `int_int` for
   `<int, int>` or `int_generic` for `<int, A>` where A is not a type that will
   be specialized for.

Inside the method body, types are specialized according to the binding of type
variables. For example, if a type variable `A` is bound to `int`, then declaring
a variable of type `A` will result in declaring a variable of type `int`.

Generic specialization ensures that primitive unboxed types can be used whenever
possible. This is a performance optimization.


## Calling Methods on Generic Classes
The compiler uses the same rules as described above for generating the name of
the method to be called. However, for `super` calls, the compiler has to infer
the binding of the generic type variables of the superclass (or whichever class
contains the *next* definition of the method) and use the name of the superclass
during method name generation.

Similarly, for normal method calls, the compiler has to infer which class or
interface defines the procedure (starting with the static type of the method
invocation receiver), along with the binding of type variables. That class name
and the class's specialization are used for method name generation.

```dart
// Example: Dart code
class Foo<A, B> {
  void method() { /* ... */ }
}

class Bar<A, B> extends Foo<B, A> { }

Foo<int, bool> objFoo = new Bar<bool, int>();
Bar<bool, int> objBar = new Bar<bool, int>();
objFoo.method();
objBar.method();
```

```java
// Generated Java code (only selected parts):
class Foo__int_bool extends DartObject implements Foo_interface__int_bool {
  /* Method implementations omitted here, see "Delegator methods" */
}

class Bar__bool_int extends Foo__int_bool implements Bar_interface__bool_int {
  /* Method implementations omitted here, see "Delegator methods" */
}

Foo_interface__int_bool objFoo = Bar._new("<type object for Bar<bool, int>");
Bar_interface__bool_int objBar = Bar._new("<type object for Bar<bool, int>");
objFoo.method__Foo_int_bool();
objBar.method__Foo_int_bool();    // Method defined on Foo but not on Bar!
```

### Delegator Methods
Delegator methods are methods that do not contain an actual implementation of a
procedure but call a certain specialized method. They are required, so that an
object of a certain specialized type can act as an object of a supertype.

For example, delegator methods are created for supertypes of specializations. As
an example, the type of a class with two generic parameters and specialization
`int_bool` has three such supertypes: The same class with specializations
`int_generic`, `generic_bool` and `generic_generic`.

```dart
// Dart code
class List<T> {
  void add (T object) { /* ... */ }
}

List<Object> list = new List<int>();
list.add(10);
```

```java
// Java code
class List<T> extends DartObject implements List_interface<T> {
  void add (T object) { /* ... */ }
}

interface List_interface__int extends List_interface<java.lang.Integer> {
  /* ... */
}

class List__int extends DartObject implements List_interface__int {
  void add__List_int(int object) { /* ... */ }

  // Delegator method
  void add__List_generic(java.lang.Integer object) {
    add__List_int(object);
  }
}

List_interface<Object> list = List._new("<type of List<int>>");
list.add__List_generic(10);
```

Delegator methods must also be generated if a method is overridden. Consider a
modification of one of the previous examples.

```dart
// Dart code
class Foo<A, B> {
  void method() { /* ... */ }
}

class Bar<A, B> extends Foo<B, A> {
  void method() { /* overridden */ }
}

Foo<int, bool> objFoo = new Bar<bool, int>();
Bar<bool, int> objBar = new Bar<bool, int>();
objFoo.method();
objBar.method();
```

In this case, we have to ensure that there is a delegator method
`method__Foo_int_bool` defined on `Bar__bool_int`, because `method` is invoked
through the interface of `Foo_interface__int_bool`. It is not known statically 
if `objFoo` is actually of type `Foo` or one of its subclasses.

```java
// Java code (only method invocations):
objFoo.method__Foo_int_bool();    // Want to call the overridden method!
objBar.method__Bar_bool_int();    // Method defined on Bar
```

This new delegator method defined on `Bar` will dispatch to the correct
overridden method defined on `Bar`.

```java
class Bar__bool_int extends Foo__int_bool implements Bar_interface__bool_int {
  void method__Bar_bool_int() { /* actual implementation */ }

  // Delegator methods for invocation through interface with different
  // specialization but same class (Bar)
  void method__Bar_generic_int() { method__Bar_bool_int(); }
  void method__Bar_int_generic() { method__Bar_bool_int(); }
  void method__Bar_generic_generic() { method__Bar_bool_int(); }

  // Delegator methods for invocation through interface with same specialization
  // but superclass of Bar.
  // Note, that type parameters are inversed!
  void method__Foo_int_bool() { method__Bar_bool_int(); }

  // Delegator methods for invocation through interface with different
  // specialization and superclass of Bar.
  // Not necessary. E.g., method__Foo_generic_bool --> method__Foo_int_bool
  // --> method__Bar_bool_int
}
```

## Future Work: Different Mangling Schemes
The current name mangling schemes results in an excessive number of delegator
methods. One different approach would be to mangle according to method parameter
types instead of receiver static type. In fact, this would be done automatically
by the Java compiler if using method overloading (apart from return types). We
have not looked at all details of this approach, but it seems promising.

## Future Work: Named Parameters
One approach would be to store named arguments in a map that is automatically
generated at the call site and passed implicitly (the Ruby approach). Another
approach is using call-target-specific method arguments, i.e., there is an PIC
at the call site that does not only store call targets but also parameters to
be passed.

## Types of Method Calls
Kernel AST provides abstractions for different types of method calls. Most of
these are handled uniformly in the compiler.

* Dynamic Method Invocation: A virtual or dynamic method call.
* Property Get: Used to read a field or call a property getter. Handled similar
  to dynamic method invocations.
* Property Set: Used to write a field or call a property setter. Handled similar
  to dynamic method invocations.
* Static Invocation: A method call to a static method, i.e., the receiver is
  known at compile time (including top-level functions, factory constructors).
* Static Property Get: Used to read a field or call a property getter on a
  statically-known receiver. Handled similar to static invocations.
* Static Property Set: Used to write a field or call a property setter on a
  statically-known receiver. Handled similar to static invocations.
* Constructor Invocation: A constructor invocation, handled similar to static
  invocations (see below).
* Super Method Invocation: Calls a super instance method. Translated directly
  to Java `super` calls.
* Super Initializer: Used to call the super constructor. Handled similar to
  super method invocations.


