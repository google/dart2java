import 'compiler.dart' show JavaFile, CompilerOptions;


import 'package:kernel/kernel.dart' show Repository;
import 'package:kernel/ast.dart' as dart_ast;

// TODO(stanm):
class CodeGenerator {
  final CompilerOptions options;

  CodeGenerator(this.options);

  Iterable<JavaFile> compile(Repository repository, List<String> errors) {
    for (var library in repository.libraries) {
      print('Got library "${library.name}"');
    }

    return <JavaFile>[];
  }
}
