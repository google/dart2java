import 'package:kernel/ast.dart' as dart;
import '../java/ast.dart' as java;

class CompilerState {
  /// Maps Dart classes to Java classes which will be reused in generated code.
  ///
  /// E.g., dart.core.int uses java.lang.Integer. This is required to get
  /// the types right in generated Java code.
  /// TODO(springerm): Try to use mapping from dart.DartType here later.
  final javaClasses = new Map<String, String>();

  /// Maps Dart SDK classes and interfaces to their runtime implementations.
  ///
  /// E.g., dart.core.int is implemented by dart class JavaInteger. This
  /// required to find method implementations.
  /// TODO(springerm): Try to use mapping from dart.DartType here later.
  final implementationClasses = new Map<String, java.ClassRefExpr>();

  CompilerState() {
    // Set up primitive types
    registerPrimitiveCoreClass(
        "dart.core::bool", "java.lang.Boolean", "dart.core.bool");
    registerPrimitiveCoreClass(
        "dart.core::int", "java.lang.Integer", "dart._runtime.JavaInteger");
    registerPrimitiveCoreClass(
        "dart.core::double", "java.lang.Double", "dart._runtime.JavaDouble");
    registerPrimitiveCoreClass(
        "dart.core::String", "java.lang.String", "dart._runtime.JavaString");
  }

  void registerPrimitiveCoreClass(
      String dartName, String javaName, String implClass) {
    javaClasses[dartName] = javaName;
    implementationClasses[dartName] = new java.ClassRefExpr(implClass);
  }
}
