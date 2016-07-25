import 'dart:io';

import 'package:kernel/ast.dart' as dart;

import '../java/ast.dart' as java;
import '../java/java_builder.dart' show buildWrapperClass, buildClass;
import '../java/java_emitter.dart' show emitClassDecl;
import 'compiler_state.dart' show CompilerState;
import 'writer.dart' show FileWriter;

class CodeGenerator {
  FileWriter writer;
  CompilerState compilerState;

  CodeGenerator(this.writer, this.compilerState);

  /// Compile [library] to a set of Java files.
  ///
  /// Returns the set of the files that have been written.
  Set<File> compile(dart.Library library) {
    var filesWritten = new Set<File>();

    if (library.procedures.isNotEmpty || library.fields.isNotEmpty) {
      java.ClassDecl cls = buildWrapperClass(library, compilerState);
      filesWritten.add(writer.writeJavaFile(
          cls.type.package, cls.type.name, emitClassDecl(cls)));
    }

    for (var dartCls in library.classes) {
      List<java.ClassDecl> classes = buildClass(dartCls, compilerState);
      for (var cls in classes) {
        filesWritten.add(writer.writeJavaFile(
            cls.type.package, cls.type.name, emitClassDecl(cls)));
      }
    }

    return filesWritten;
  }
}
