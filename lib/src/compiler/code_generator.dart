import 'compiler.dart' show CompilerOptions;

import 'package:kernel/ast.dart' as dart;

import '../java/ast.dart' as java;
import '../java/java_builder.dart' show JavaAstBuilder;
import '../java/java_emitter.dart' show JavaAstEmitter;
import 'writer.dart' show FileWriter;
import 'compiler_state.dart';

class CodeGenerator {
  FileWriter writer;
  CompilerState compilerState;

  CodeGenerator(CompilerOptions options, FileWriter writer) {
    this.writer = writer;
    compilerState = new CompilerState(options);
  }

  /// Compile [library] to a set of Java files.
  ///
  /// Returns the set of the files that have been written.
  Set<File> compile(dart.Library library) {
    String package = compilerState.getJavaPackageName(library);

    var filesWritten = new Set<File>();

    if (library.procedures.isNotEmpty || library.fields.isNotEmpty) {
      // TODO(andrewkrieger): Check for name collisions.
      String className = CompilerState.getClassNameForPackageTopLevel(package);
      java.ClassDecl cls =
          JavaAstBuilder.buildWrapperClass(package, className, library, compilerState);
      filesWritten.add(writer.writeJavaFile(
          package, className, JavaAstEmitter.emitClassDecl(cls)));
    }

    for (var dartCls in library.classes) {
      java.ClassDecl cls =
          JavaAstBuilder.buildClass(package, dartCls, compilerState);
      filesWritten.add(writer.writeJavaFile(
          package, cls.name, JavaAstEmitter.emitClassDecl(cls)));
    }

    return filesWritten;
  }
}

