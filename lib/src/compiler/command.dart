// Copyright (c) 2016, the Dart project authors.  Please see the AUTHORS file
// for details. All rights reserved. Use of this source code is governed by a
// BSD-style license that can be found in the LICENSE file.

import 'package:args/command_runner.dart';

import 'compiler.dart' show CompilerOptions, ModuleCompiler;

typedef void MessageHandler(Object message);

/// The command for invoking the modular compiler.
class CompileCommand extends Command {
  get name => 'compile';
  get description => 'Compile a set of Dart files into a JavaScript module.';
  final MessageHandler messageHandler;

  CompileCommand({MessageHandler messageHandler})
      : this.messageHandler = messageHandler ?? print {
    CompilerOptions.addArguments(argParser);
  }

  @override
  void run() {
    var compilerOptions = new CompilerOptions.fromArguments(argResults);
    var compiler = new ModuleCompiler(compilerOptions);
    List<String> sources = argResults.rest;
    compiler.compile(sources);
  }
}

/// Thrown when the input source code has errors.
class CompileErrorException implements Exception {
  final String message;

  CompileErrorException(this.message);

  toString() => message;
}
