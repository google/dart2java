// Copyright (c) 2016, the Dart project authors.  Please see the AUTHORS file
// for details. All rights reserved. Use of this source code is governed by a
// BSD-style license that can be found in the LICENSE file.

import 'package:analyzer/analyzer.dart' show AnalysisError, ErrorSeverity;
import 'package:analyzer/src/generated/engine.dart' show AnalysisContext;
import 'package:args/args.dart' show ArgParser, ArgResults;
import 'package:cli_util/cli_util.dart' show getSdkDir;
import 'package:kernel/analyzer/analyzer_repository.dart'
    show AnalyzerRepository;
import 'package:kernel/analyzer/loader.dart' show AnalyzerLoader;
import 'package:kernel/ast.dart' show Library;
import 'package:path/path.dart' as path;

import 'code_generator.dart' show CodeGenerator;
import 'error_helpers.dart' show errorSeverity, formatError, sortErrors;

/// Compiles a set of Dart files into Java source files.
///
/// For a single [BuildUnit] definition, this will produce multiple [JavaFile]s
/// (one [JavaFile] per top-level Java class, as per conventions).
///
/// This class loads the Dart sources from the [BuildUnit], builds kernel IR,
/// and passes each [Library] in the [BuildUnit] to a [CodeGenerator]. Then,
/// this class gathers the results of code generation and writes the resulting
/// [JavaFile]s to the proper directories.
///
/// This class is tightly coupled to the kernel analyzer frontend. Until kernel
/// implements strong mode in its IR, it will be helpful to have access to the
/// Analyzer AST through an [AnalyzerLoader].
class ModuleCompiler {
  final AnalyzerRepository repository;
  final AnalyzerLoader loader;
  final CompilerOptions options;

  factory ModuleCompiler(CompilerOptions options) {
    var repository =
        new AnalyzerRepository(sdk: options.dartSdkPath, strongMode: true);
    return new ModuleCompiler.withRepository(repository, options);
  }

  ModuleCompiler.withRepository(
      AnalyzerRepository repository, CompilerOptions options)
      : repository = repository,
        loader = repository.getAnalyzerLoader(),
        options = options;

  Iterable<JavaFile> compile(BuildUnit unit) {
    AnalysisContext context = loader.context;
    var errors = <AnalysisError>[];
    var librariesToCompile = <Library>[];

    loader.ensureLibraryIsLoaded(
        loader.getLibraryReference(loader.getDartCoreLibrary()));
    for (var sourcePath in unit.sources) {
      var sourceUri = Uri.parse(sourcePath);
      if (sourceUri.scheme == '') {
        sourceUri = path.toUri(path.absolute(sourcePath));
      }
      Library library = repository.getLibraryReference(sourceUri);
      loader.ensureLibraryIsLoaded(library);
      librariesToCompile.add(library);
      var source = context.sourceFactory.forUri2(sourceUri);
      errors.addAll(context.computeErrors(source));
    }
    // TODO(andrewkrieger): Ask kernel team if we can speed this up and reduce
    // memory by only loading kernel references for other libraries instead of
    // fully loading them.
    loader.loadEverything();

    sortErrors(context, errors);
    var messages = <String>[];
    for (var e in errors) {
      var m = formatError(context, e);
      if (m != null) messages.add(m);
    }

    if (!options.unsafeForceCompile &&
        errors.any((e) => errorSeverity(context, e) == ErrorSeverity.ERROR)) {
      return [new JavaFile.invalid(messages)];
    }

    // TODO (stanm): see if we need context and options.
    var codeGenerator = new CodeGenerator(options);
    var code = <JavaFile>[];
    for (var library in librariesToCompile) {
      code.addAll(codeGenerator.compile(library, messages));
    }
    return code;
  }
}

class CompilerOptions {
  /// Whether to force compilation of code with static errors.
  final bool unsafeForceCompile;
  final String dartSdkPath;

  const CompilerOptions({this.unsafeForceCompile: false, this.dartSdkPath});

  CompilerOptions.fromArguments(ArgResults args)
      : unsafeForceCompile = args['unsafe-force-compile'],
        dartSdkPath = args['dart-sdk'] ?? getSdkDir().path;

  static ArgParser addArguments(ArgParser parser) {
    return parser
      ..addOption('dart-sdk', help: 'Dart SDK Path', defaultsTo: null)
      ..addFlag('unsafe-force-compile',
          help: 'Compile code even if it has errors. ಠ_ಠ\n'
              'This has undefined behavior!',
          defaultsTo: false);
  }
}

/// A number of Dart sources to bundle into a Java package.
class BuildUnit {
  /// The package to generate (e.g. com.google.foo.bar).
  final String package;

  /// The list of sources in this module.
  ///
  /// The set of Dart files can be arbitrarily large, but it must contain
  /// complete libraries including all of their parts, as well as all libraries
  /// that are part of a library cycle.
  final List<String> sources;

  BuildUnit(this.package, this.sources);
}

/// TODO(stanm): Designed after the [JSModuleFile].
class JavaFile {
  /// The filename (should be the same as the class declared in this file)
  final String name;

  /// The list of messages (errors and warnings).
  final List<String> errors;

  /// The Java code for this module.
  final String code;

  JavaFile(this.name, this.errors, this.code);
  JavaFile.invalid(this.errors)
      : name = null,
        code = null;

  /// True if this library was successfully compiled.
  bool get isValid => code != null;
}
