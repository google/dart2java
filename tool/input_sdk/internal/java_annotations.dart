part of dart._internal;

/// Annonated methods should be declared as "external" and be implemented in
/// Java. This is useful for functionality that cannot be implemented in Dart
/// or should be implemented in Java for performance reasons.
class JavaCall {
  /// The full name of the static Java method.
  final String name;

  const JavaCall(this.name);
}

/// Annotated (external) methods have a different Java name. E.g., a Dart
/// method cannot have the name `get`, but a Java method can. In that case,
/// an `@JavaMethod` annotation should be added to a Dart method with a
/// different name.
class JavaMethod {
  /// The name of the Java method.
  final String name;

  const JavaMethod(this.name);
}

/// Annotated classes serve as Dart interfaces only and are implemented by
/// Java classes.
class JavaClass {
  /// The full name of the Java class.
  final String name;

  const JavaClass(this.name);
}