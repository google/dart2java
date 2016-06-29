import 'compiler.dart' show CompilerOptions;

import 'package:kernel/ast.dart' as dart_ast;

import 'writer.dart';

class CodeGenerator {
  final CompilerOptions options;
  final FileWriter writer;

  CodeGenerator(this.options, this.writer);

  void compile(dart_ast.Library library) {
    String package = 'com.google.ddc_java';
    String className = 'HelloWorld';
    writer.writeJavaFile(
        package,
        className,
        'package $package;\n'
        '\n'
        'public class $className {\n'
        '  public static void main(String[] args) {\n'
        '    System.out.println("Hello, world!");\n'
        '  }\n'
        '}\n');
  }
}
