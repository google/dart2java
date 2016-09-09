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

import 'package:args/args.dart' show ArgParser, ArgResults;
import 'package:args/src/usage_exception.dart' show UsageException;

import 'compiler.dart' show CompilerOptions, ModuleCompiler;

/// A class for invoking the compiler based on raw command-line arguments.
class Runner {
  /// The name of the executable being run.
  ///
  /// Used for error reporting.
  static const String executableName = 'dart2java';

  /// A short description of this executable.
  static const String description = 'Dart To Java Development Compiler';

  static final argParser = new ArgParser();

  /// Parses [args] and invokes the compiler.
  ///
  /// This will throw a [UsageException] if [args] is invalid.
  static void run(Iterable<String> args) {
    argParser.addFlag('help',
        abbr: 'h', negatable: false, help: 'Print this usage information.');
    CompilerOptions.addArguments(argParser);

    try {
      // Throws FormatException.
      ArgResults argResults = argParser.parse(args);

      if (argResults['help']) {
        usage();
      }

      List<String> sources = argResults.rest;

      if (sources.isEmpty) {
        usageException('No input files.');
      }

      var compilerOptions = new CompilerOptions.fromArguments(argResults);
      var compiler = new ModuleCompiler(compilerOptions);
      compiler.compile(sources);
    } on FormatException catch (error) {
      usageException(error.message);
    }
  }

  /// Throws a standard [UsageException] displaying the usage information for
  /// the executable.
  ///
  /// This includes usage for the arguments.
  static void usage() => usageException(description);

  /// Throws a [UsageException] with a specific [message].
  static void usageException(String message) =>
      throw new UsageException(message, _usageWithoutDescription);

  /// Returns [usage] with [description] removed from the beginning.
  static String get _usageWithoutDescription {
    var buffer = new StringBuffer()
      ..writeln('Usage: $executableName [arguments] <input-files>')
      ..writeln()
      ..writeln('Available arguments:')
      ..writeln(argParser.usage);

    return buffer.toString();
  }
}

// TODO(stanm): move to a separate file or in compiler.dart.
/// Thrown when the input source code has errors.
class CompileErrorException implements Exception {
  final String message;

  CompileErrorException(this.message);

  toString() => "[error] $message";
}
