// Copyright (c) 2016, the Dart project authors.  Please see the AUTHORS file
// for details. All rights reserved. Use of this source code is governed by a
// BSD-style license that can be found in the LICENSE file.

import 'package:analyzer/analyzer.dart' show AnalysisError, ErrorSeverity;
import 'package:analyzer/src/generated/engine.dart' show AnalysisContext;
import 'package:args/args.dart' show ArgParser, ArgResults;
import 'package:cli_util/cli_util.dart' show getSdkDir;
import 'package:kernel/analyzer/analyzer_repository.dart'
    show AnalyzerRepository;
import 'package:kernel/ast.dart' show Library;

import 'code_generator.dart' show CodeGenerator;
import 'runner.dart' show CompileErrorException;
import 'error_helpers.dart' show errorSeverity, formatError, sortErrors;
import 'loader.dart' show Loader;
import 'writer.dart' show FileWriter;

/// Compiles a set of Dart files into Java source files.
///
/// The compiler takes in a list of Dart sources and produce multiple output
/// files, including one Java source file per top-level Java class and summary
/// files.
///
/// This class loads the Dart sources, builds kernel IR, and passes each kernel
/// [Library] from the given Dart sources to a [CodeGenerator]. The
/// [CodeGenerator] reads the Kernel AST and uses a [FileWriter] to actually
/// save output files.
///
/// This class has access to an [AnalysisContext] through a [Loader]. This might
/// be useful during early development. But, we should try to only interact with
/// analyzer through [Loader] to keep the interactions in one place.
class ModuleCompiler {
  final AnalyzerRepository repository;
  final Loader loader;
  final CompilerOptions options;

  factory ModuleCompiler(CompilerOptions options) {
    var repository =
        new AnalyzerRepository(sdk: options.dartSdkPath, strongMode: true);
    return new ModuleCompiler.withRepository(repository, options);
  }

  ModuleCompiler.withRepository(
      AnalyzerRepository repository, CompilerOptions options)
      : repository = repository,
        loader = new Loader(repository),
        options = options;

  /// Compile a list of [sources] to a set of Java files each.
  ///
  /// Returns the union of all sets of Java files.
  Set<File> compile(List<String> sources) {
    AnalysisContext context = loader.context;
    var errors = <AnalysisError>[];
    var librariesToCompile = <Library>[];

    for (var sourcePath in sources) {
      var loaderResult = loader.load(sourcePath);
      librariesToCompile.add(loaderResult.library);
      errors.addAll(loaderResult.errors);
    }
    loader.loadEverything();

    sortErrors(context, errors);
    var messages = <String>[];
    for (var e in errors) {
      var m = formatError(context, e);
      if (m != null) print(m);
    }

    if (!options.unsafeForceCompile &&
        errors.any((e) => errorSeverity(context, e) == ErrorSeverity.ERROR)) {
      throw new CompileErrorException(
          '\nPlease fix all errors before compiling (warnings are okay).');
    }

    var codeGenerator = new CodeGenerator(options, new FileWriter(options));
    return librariesToCompile
        .map((library) => codeGenerator.compile(library))
        .fold(new Set<File>(), (files, newFiles) => files..addAll(newFiles));
  }
}

class CompilerOptions {
  /// Whether to force compilation of code with static errors.
  final bool unsafeForceCompile;
  final String dartSdkPath;

  /// Where to generate Java output files.
  final String outputDir;

  /// The base directory for source code.
  ///
  /// The Java package to use for generated classes is based (in part) on the
  /// relative path of the original dart source file under this directory.
  final String buildRoot;

  /// Added to the package of each generated Java class.
  final String packagePrefix;

  const CompilerOptions(
      {this.unsafeForceCompile: false,
      this.dartSdkPath,
      this.outputDir: '.',
      this.buildRoot: '.',
      this.packagePrefix: ''});

  CompilerOptions.fromArguments(ArgResults args)
      : unsafeForceCompile = args['unsafe-force-compile'],
        dartSdkPath = args['dart-sdk'] ?? getSdkDir().path,
        outputDir = args['output-dir'],
        buildRoot = args['build-root'],
        packagePrefix = args['package-prefix'];

  static ArgParser addArguments(ArgParser parser) {
    return parser
      ..addOption('dart-sdk', help: 'Dart SDK Path', defaultsTo: null)
      ..addFlag('unsafe-force-compile',
          help: 'Compile code even if it has errors. ಠ_ಠ\n'
              'This has undefined behavior!',
          defaultsTo: false)
      ..addOption('output-dir',
          abbr: 'o', help: 'Output directory.', defaultsTo: '.')
      ..addOption('build-root',
          abbr: 'r',
          help: 'Build root (usually the lib/ directory of a pub package).\n'
              'The relative path of a Dart source file under this directory\n'
              'is used to determine the package of generated Java classes.',
          defaultsTo: '.')
      ..addOption('package-prefix',
          abbr: 'p',
          help: 'Prefix added to the package of each generated Java class.\n'
              'For example: --package-prefix="org.example.my_project"',
          defaultsTo: '');
  }
}
