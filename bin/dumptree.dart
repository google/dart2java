#!/usr/bin/env dart
// Copyright (c) 2016, the Dart project authors.  Please see the AUTHORS file
// for details. All rights reserved. Use of this source code is governed by a
// BSD-style license that can be found in the LICENSE file.

/// Command line entry point for kernel tree dumper.

import 'package:cli_util/cli_util.dart' show getSdkDir;
import 'package:dart2java/src/compiler/debug_printer.dart' show DebugPrinter;
import 'package:kernel/kernel.dart';
import 'package:kernel/analyzer/analyzer_repository.dart'
    show AnalyzerRepository;
import 'package:kernel/analyzer/loader.dart' show AnalyzerLoader;
import 'package:path/path.dart' as path;

void main(List<String> args) {
  AnalyzerRepository repository = new AnalyzerRepository(sdk: getSdkDir().path,
      strongMode: true);
  AnalyzerLoader loader = repository.getAnalyzerLoader();
  List<Library> librariesToDump = <Library>[];

  loader.ensureLibraryIsLoaded(
      loader.getLibraryReference(loader.getDartCoreLibrary()));
  for (var sourcePath in args) {
    var sourceUri = Uri.parse(sourcePath);
    if (sourceUri.scheme == '') {
      sourceUri = path.toUri(path.absolute(sourcePath));
    }
    Library library = repository.getLibraryReference(sourceUri);
    loader.ensureLibraryIsLoaded(library);
    librariesToDump.add(library);
  }
  if (librariesToDump.isEmpty) {
    print('No files specified. Exiting.');
    return;
  }

  loader.loadEverything();
  for (var l in librariesToDump) {
    print('***** Library ${l.importUri} *****');
    l.accept(new DebugPrinter());
    print('');
  }
}

