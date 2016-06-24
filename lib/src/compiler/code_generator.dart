import 'compiler.dart' show JavaFile, CompilerOptions;

import 'package:kernel/ast.dart' as dart_ast;

import 'debug_printer.dart';

// TODO(stanm):
class CodeGenerator {
  final CompilerOptions options;

  CodeGenerator(this.options);

  Iterable<JavaFile> compile(dart_ast.Library library, List<String> errors) {
    library.accept(new DebugPrinter());

    return <JavaFile>[];
  }
}
