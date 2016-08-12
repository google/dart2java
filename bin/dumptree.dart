#!/usr/bin/env dart
// Copyright (c) 2016, the Dart project authors.  Please see the AUTHORS file
// for details. All rights reserved. Use of this source code is governed by a
// BSD-style license that can be found in the LICENSE file.

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

