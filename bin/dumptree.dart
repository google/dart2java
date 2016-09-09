#!/usr/bin/env dart
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

/// Command line entry point for kernel tree dumper.

import 'package:cli_util/cli_util.dart' show getSdkDir;
import 'package:dart2java/src/compiler/debug_printer.dart' show DebugPrinter;
import 'package:kernel/analyzer/loader.dart' show AnalyzerLoader;
import 'package:kernel/core_types.dart' show CoreTypes;
import 'package:kernel/kernel.dart';
import 'package:path/path.dart' as path;

void main(List<String> args) {
  Repository repository = new Repository(sdk: getSdkDir().path);
  AnalyzerLoader loader = new AnalyzerLoader(repository, strongMode: true);
  List<Library> librariesToDump = <Library>[];

  if (args.isEmpty) {
    print('No files specified. Exiting.');
    return;
  }

  loader.ensureLibraryIsLoaded(repository.getLibrary('dart:core'));
  loader.ensureLibraryIsLoaded(repository.getLibrary('dart:async'));
  loader.ensureLibraryIsLoaded(repository.getLibrary('dart:_internal'));
  for (var sourcePath in args) {
    var sourceUri = Uri.parse(sourcePath);
    if (sourceUri.scheme == '') {
      sourceUri = path.toUri(path.absolute(sourcePath));
    }
    Library library = repository.getLibraryReference(sourceUri);
    loader.ensureLibraryIsLoaded(library);
    librariesToDump.add(library);
  }

  CoreTypes.instance = new CoreTypes.fromRepository(repository);
  loader.loadEverything();
  for (var l in librariesToDump) {
    print('***** Library ${l.importUri} *****');
    l.accept(new DebugPrinter());
    print('');
  }
}
