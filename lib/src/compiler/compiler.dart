// Copyright (c) 2016, the Dart project authors.  Please see the AUTHORS file
// for details. All rights reserved. Use of this source code is governed by a
// BSD-style license that can be found in the LICENSE file.

import 'dart:collection' show HashSet;
import 'package:analyzer/analyzer.dart'
    show
        AnalysisError,
        CompilationUnit,
        CompileTimeErrorCode,
        ErrorSeverity,
        StaticWarningCode;
import 'package:analyzer/src/generated/engine.dart' show AnalysisContext;
import 'package:analyzer/src/generated/java_engine.dart' show AnalysisException;
import 'package:analyzer/src/generated/source_io.dart' show Source, SourceKind;
import 'package:args/args.dart' show ArgParser, ArgResults;
import 'package:cli_util/cli_util.dart' show getSdkDir;
import 'package:func/func.dart' show Func1;
import 'package:kernel/kernel.dart' show Repository;
import 'package:kernel/analyzer/analyzer_repository.dart'
    show AnalyzerRepository;
import 'package:kernel/analyzer/loader.dart' show AnalyzerLoader;
import 'package:path/path.dart' as path;

import 'code_generator.dart' show CodeGenerator;
import 'error_helpers.dart' show errorSeverity, formatError, sortErrors;

/// Compiles a set of Dart files into a single JavaScript module.
///
/// For a single [BuildUnit] definition, this will produce a [JSModuleFile].
/// Those objects are record types that record the data consumed and produced
/// for a single compile.
///
/// This class exists to cache global state associated with a single in-memory
/// AnalysisContext, such as information about extension types in the Dart SDK.
/// It can be used once to produce a single module, or reused to save warm-up
/// time. (Currently there is no warm up, but there may be in the future.)
///
/// The SDK source code is assumed to be immutable for the life of this class.
///
/// For all other files, it is up to the [AnalysisContext] to decide whether or
/// not any caching is performed. By default an analysis context will assume
/// sources are immutable for the life of the context, and cache information
/// about them.
class ModuleCompiler {
  final Repository repository;
  final CompilerOptions options;

  ModuleCompiler(CompilerOptions options)
      : repository = new AnalyzerRepository(sdk: options.dartSdkPath,
          strongMode: true),
        options = options;

  Iterable<JavaFile> compile(BuildUnit unit) {
    var loader = repository.getAnalyzerLoader();
    var context = loader.context;
    var errors = <AnalysisError>[];

    for (var sourcePath in unit.sources) {
      var sourceUri = Uri.parse(sourcePath);
      if (sourceUri.scheme == '') {
        sourceUri = path.toUri(path.absolute(sourcePath));
      }
      var source = context.sourceFactory.forUri2(sourceUri);
      var library = repository.getLibraryReference(sourceUri);
      loader.ensureLibraryIsLoaded(library);
      errors.addAll(context.computeErrors(source));
    }
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
    return codeGenerator.compile(repository, messages);
  }
}

class CompilerOptions {
  /// Whether to force compilation of code with static errors.
  final bool unsafeForceCompile;
  final String dartSdkPath;

  const CompilerOptions({
    this.unsafeForceCompile: false,
    this.dartSdkPath
  });

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

/// A number of Dart sources to bundle into a Java package
class BuildUnit {
  /// The package to generate (e.g. com.google.foo.bar)
  final String package;

  /// The list of sources in this module.
  ///
  /// The set of Dart files can be arbitrarily large, but it must contain
  /// complete libraries including all of their parts, as well as all libraries
  /// that are part of a library cycle.
  final List<String> sources;

  BuildUnit(this.package,  this.sources);
}

/// TODO(stanm): Designed after the [JSModuleFile].
class JavaFile {
  /// The filename (should be the same as the class declared in this file)
  final String name;

  /// The list of messages (errors and warnings)
  final List<String> errors;

  /// The Java code for this module.
  final String code;

  JavaFile(this.name, this.errors, this.code);
  JavaFile.invalid(this.errors) : name = null, code = null;

  /// True if this library was successfully compiled.
  bool get isValid => code != null;
}
