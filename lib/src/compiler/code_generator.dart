import 'package:analyzer/dart/ast/ast.dart';

import 'compiler.dart' show BuildUnit, JavaFile;

// TODO(stanm):
class CodeGenerator {
  // TODO(stanm): change type (1 file per class, right?).
  JavaFile compile(BuildUnit unit, List<CompilationUnit> compilationUnits,
      List<String> errors) {
    // TODO(stanm): really compile :)
    return new JavaFile(errors, "");
  }
}
