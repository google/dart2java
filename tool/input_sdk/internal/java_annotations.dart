part of dart._internal;

/// Annonated methods should be declared as "external" and be implemented in
/// Java. This is useful for functionality that cannot be implemented in Dart
/// or should be implemented in Java for performance reasons.
class JavaCall {
  /// The full name of the static Java method.
  final String name;

  const JavaCall(this.name);
}
