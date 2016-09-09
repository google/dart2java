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

import 'package:analyzer/analyzer.dart' show AnalysisError;
import 'package:analyzer/src/generated/engine.dart' show AnalysisContext;
import 'package:kernel/analyzer/loader.dart' show AnalyzerLoader;
import 'package:kernel/core_types.dart' show CoreTypes;
import 'package:kernel/kernel.dart' show Library, Repository;
import 'package:path/path.dart' as path;

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
  final Repository repository;
  final AnalyzerLoader _loader;

  /// Initializes an instance of [Loader] which loads libraries into an
  /// [AnalyzerRepository].
  ///
  /// This involves loading the Dart core libraries. The current implementation
  /// takes several seconds to parse and load these core libraries.
  Loader(Repository repository)
      : repository = repository,
        _loader = new AnalyzerLoader(repository, strongMode: true) {
    // Load the core libraries
    load('dart:_internal');
    load('dart:core');
    load('dart:async');
    CoreTypes.instance = new CoreTypes.fromLibraries(repository.libraries);
  }

  /// Provides access to the underlying [AnalyzerLoader]'s [AnalysisContext].
  AnalysisContext get context => _loader.context;

  /// Loads a library by URI.
  LoaderResult loadUri(Uri sourceUri) {
    Library library = repository.getLibraryReference(sourceUri);
    _loader.ensureLibraryIsLoaded(library);

    // Compute errors. The type of source is not annotated because it's an
    // Analyzer internal class (Source, in analyzer/src/generated/source.dart)
    var source = context.sourceFactory.forUri2(sourceUri);
    List<AnalysisError> errors = context.computeErrors(source);
    return new LoaderResult(library, errors);
  }

  /// Loads a Dart source file.
  LoaderResult load(String source) {
    Uri sourceUri = Uri.parse(source);
    if (sourceUri.scheme == '') {
      sourceUri = path.toUri(path.absolute(source));
    }
    return loadUri(sourceUri);
  }

  /// Recursively loads everything referenced by the libraries that have already
  /// been loaded.
  ///
  /// Eventually, this will be replaced by loading signatures from .sob files
  /// and falling back on analyzer to generate signatures if needed.
  void loadEverything() => _loader.loadEverything();
}
