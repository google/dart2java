import '../java/ast.dart' as java;

class CompilerState {
  /// Maps Dart classes to Java classes which will be reused in generated code.
  ///
  /// E.g., dart.core.int uses java.lang.Integer. This is required to get
  /// the types right in generated Java code.
  /// TODO(springerm): Try to use mapping from dart.DartType here later.
  final javaClasses = new Map<String, String>();

  /// Maps Dart SDK classes and interfaces to their runtime implementations,
  /// i.e., "interceptor classes".
  ///
  /// E.g., dart.core.int is implemented by dart class JavaInteger. This
  /// required to find method implementations.
  /// TODO(springerm): Try to use mapping from dart.DartType here later.
  final interceptorClasses = new Map<String, String>();

  CompilerState() {
    // Set up primitive types
    registerPrimitiveCoreClass(
        "dart.core::bool", "java.lang.Boolean", "dart.core::bool");
    registerPrimitiveCoreClass(
        "dart.core::int", "java.lang.Integer", "dart._runtime::JavaInteger");
    registerPrimitiveCoreClass(
        "dart.core::double", "java.lang.Double", "dart._runtime::JavaDouble");
    registerPrimitiveCoreClass(
        "dart.core::String", "java.lang.String", "dart._runtime::JavaString");
  }

  void registerPrimitiveCoreClass(
      String dartName, String javaName, String interceptorClass) {
    javaClasses[dartName] = javaName;
    javaClasses[interceptorClass] = javaName;
    interceptorClasses[dartName] = interceptorClass;
    interceptorClasses[interceptorClass] = interceptorClass;
  } 

  /// Check if a certain class is an interceptor class.
  /// 
  /// TODO(springerm): Remove once we got annotations working.
  /// TODO(springerm): This should be the fully qualified class name, but
  /// package naming is not fully implemented yet.
  bool isInterceptorClass(String dartClassName) {
    return interceptorClasses.values.map((interceptor) =>
      interceptor.split("::").last).contains(dartClassName);
  }
}
