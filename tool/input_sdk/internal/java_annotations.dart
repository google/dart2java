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
