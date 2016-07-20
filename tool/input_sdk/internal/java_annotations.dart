part of dart._internal;

// TODO(springerm): Add back final keyword once constructors are implemented
/// Annotated classes contain Dart instance methods for the corresponding
/// Java class. There must be only one class annonated with a certain name.
class JavaClass {
  /// The fully-qualified name of the corresponding Java class.
  final String name;

  const JavaClass(this.name);
}

/// Annonated methods should be declared as "external" and be implemented in
/// Java. This is useful for functionality that cannot be implemented in Dart
/// or should be implemented in Java for performance reasons.
class JavaCall {
  /// The full name of the static Java method.
  final String name;

  const JavaCall(this.name);
}

/// A class annotated with this metadata is an interceptor class.
///
/// For details on how interceptors world, see the doc-comment on
/// ClassImpl.interceptor in package:dart2java/src/compiler/compiler_state.dart.
///
/// Example usage:
///     // Declare `Object` as a @JavaClass and interceptor. The compiler will
///     // use `java.lang.Object` for instances of Object. It will generate
///     // static methods for each of the instance methods declared in the
///     // class body, and calls to these methods will be dispatched to the
///     // static implementations.
///     @JavaClass("java.lang.Object")
///     @InterceptorFor("dart:core", "Object"
///     class Object {
///       Type get runtimeType => return Object;
///     }
class InterceptorFor {
  /// The library containing the intercepted class.
  final String library;

  /// The name of the intercepted class.
  final String cls;

  const InterceptorFor(this.library, this.cls);
}
