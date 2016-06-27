// Copyright (c) 2016, the Dart project authors.  Please see the AUTHORS file
// for details. All rights reserved. Use of this source code is governed by a
// BSD-style license that can be found in the LICENSE file.

import 'package:analyzer/analyzer.dart' show AnalysisError;
import 'package:analyzer/src/generated/engine.dart' show AnalysisContext;
import 'package:kernel/analyzer/analyzer_repository.dart'
    show AnalyzerRepository;
import 'package:kernel/analyzer/loader.dart' show AnalyzerLoader;
import 'package:kernel/kernel.dart' show Library;

/// The results returned by loader: a Kernel IR [Library] and a list of Analyzer
/// [AnalysisError]s.
class LoaderResult {
  final Library library;
  final List<AnalysisError> errors;

  LoaderResult(this.library, this.errors);
}

/// The class responsible for loading Kernel IR from Dart source and
/// (eventually) from summary files.
///
/// Currently, this just calls [AnalyzerLoader] to load everything.
class Loader {
  final AnalyzerRepository repository;
  final AnalyzerLoader _loader;

  Loader(AnalyzerRepository repository)
      : repository = repository,
        _loader = repository.getAnalyzerLoader();

  AnalysisContext get context => _loader.context;

  void loadDartCoreLibrary() {
    _loader.ensureLibraryIsLoaded(
        _loader.getLibraryReference(_loader.getDartCoreLibrary()));
  }

  LoaderResult load(Uri sourceUri) {
    Library library = repository.getLibraryReference(sourceUri);
    _loader.ensureLibraryIsLoaded(library);

    // Compute errors. The type of source is not annotated because it's an
    // Analyzer internal class (Source, in analyzer/src/generated/source.dart)
    var source = context.sourceFactory.forUri2(sourceUri);
    List<AnalysisError> errors = context.computeErrors(source);
    return new LoaderResult(library, errors);
  }

  /// Recursively loads everything referenced by the libraries that have already
  /// been loaded.
  ///
  /// Eventually, this will be replaced by loading signatures from .sob files
  /// and falling back on analyzer to generate signatures if needed.
  void loadEverything() => _loader.loadEverything();
}
