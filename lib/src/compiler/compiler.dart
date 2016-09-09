// Copyright 2016, the Dart project authors.
//
// Licensed under the Apache License, Version 2.0 (the "License");
// you may not use this file except in compliance with the License.
// You may obtain a copy of the License at
//
//     http://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing, software
// distributed under the License is distributed on an "AS IS" BASIS,
// WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// See the License for the specific language governing permissions and
// limitations under the License.

import 'dart:io';

import 'package:analyzer/analyzer.dart' show AnalysisError, ErrorSeverity;
import 'package:args/src/usage_exception.dart' show UsageException;
import 'package:analyzer/src/generated/engine.dart' show AnalysisContext;
import 'package:args/args.dart' show ArgParser, ArgResults;
import 'package:cli_util/cli_util.dart' show getSdkDir;
import 'package:kernel/kernel.dart' show Repository;
import 'package:kernel/ast.dart' as dart;

import 'code_generator.dart' show CodeGenerator;
import 'compiler_state.dart' show CompilerState;
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
/// [dart.Library] from the given Dart sources to a [CodeGenerator]. The
/// [CodeGenerator] reads the Kernel AST and uses a [FileWriter] to actually
/// save output files.
///
/// This class has access to an [AnalysisContext] through a [Loader]. This might
/// be useful during early development. But, we should try to only interact with
/// analyzer through [Loader] to keep the interactions in one place.
class ModuleCompiler {
  final Repository repository;
  final Loader loader;
  final CompilerOptions options;

  factory ModuleCompiler(CompilerOptions options) {
    var repository = new Repository(sdk: options.dartSdkPath);
    return new ModuleCompiler.withRepository(repository, options);
  }

  ModuleCompiler.withRepository(Repository repository, CompilerOptions options)
      : repository = repository,
        loader = new Loader(repository),
        options = options;

  /// Compile a list of [sources] to a set of Java files each.
  ///
  /// Returns the union of all sets of Java files.
  Set<File> compile(List<String> sources) {
    AnalysisContext context = loader.context;
    var errors = <AnalysisError>[];
    var librariesToCompile = <dart.Library>[];

    for (var sourcePath in sources) {
      if (!sourcePath.startsWith('dart:') &&
          !new File(sourcePath).existsSync()) {
        throw new UsageException(
            'Given file "$sourcePath" does not exist.',
            'You need to pass at least one existing .dart file as an'
            ' argument.');
      }
      var loaderResult = loader.load(sourcePath);
      librariesToCompile.add(loaderResult.library);
      errors.addAll(loaderResult.errors);
    }
    loader.loadEverything();

    sortErrors(context, errors);
    for (var e in errors) {
      var m = formatError(context, e);
      if (m != null) print(m);
    }

    if (!options.unsafeForceCompile &&
        errors.any((e) => errorSeverity(context, e) == ErrorSeverity.ERROR)) {
      throw new CompileErrorException(
          '\nPlease fix all errors before compiling (warnings are okay).');
    }

    var compilerState = new CompilerState(
        options, repository, getAllClasses(librariesToCompile));

    var codeGenerator =
        new CodeGenerator(new FileWriter(options), compilerState);
    return librariesToCompile
        .map(codeGenerator.compile)
        .expand((f) => f)
        .toSet();
  }

  Iterable<dart.Class> getAllClasses(List<dart.Library> libraries) {
    return libraries.expand((lib) => lib.classes);
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

  /// Package-prefix used for `file:` imports and for generated classes.
  final String filePackagePrefix;

  const CompilerOptions(
      {this.unsafeForceCompile: false,
      this.dartSdkPath,
      this.outputDir: '.',
      this.buildRoot: '.',
      this.filePackagePrefix: ''});

  CompilerOptions.fromArguments(ArgResults args)
      : unsafeForceCompile = args['unsafe-force-compile'],
        dartSdkPath = args['dart-sdk'] ?? getSdkDir().path,
        outputDir = args['output-dir'],
        buildRoot = args['build-root'],
        filePackagePrefix = args['package-prefix'];

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
