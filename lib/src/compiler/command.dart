// Copyright (c) 2016, the Dart project authors.  Please see the AUTHORS file
// for details. All rights reserved. Use of this source code is governed by a
// BSD-style license that can be found in the LICENSE file.

import 'dart:io';
import 'package:args/command_runner.dart';
import 'package:analyzer/src/generated/source.dart' show Source;
import 'package:analyzer/src/summary/package_bundle_reader.dart'
    show InSummarySource;
import 'compiler.dart'
    show BuildUnit, CompilerOptions, JavaFile, ModuleCompiler;
import 'package:path/path.dart' as path;

typedef void MessageHandler(Object message);

/// The command for invoking the modular compiler.
class CompileCommand extends Command {
  get name => 'compile';
  get description => 'Compile a set of Dart files into a JavaScript module.';
  final MessageHandler messageHandler;

  CompileCommand({MessageHandler messageHandler})
      : this.messageHandler = messageHandler ?? print {
    argParser.addOption('output-dir', abbr: 'o',
        help: 'Output directory.\n'
            'Generated source files are relative to this root.');
    argParser.addOption('package-name', abbr: 'p',
        help: 'Package name (e.g. "com.google.foo.bar").\n'
            'Generated Java classes are placed in this package.\n'
            'Source files are placed in a subdirectory like\n'
            '"\${output-dir}/\${package-name}/" as per Java conventions.');
    CompilerOptions.addArguments(argParser);
  }

  @override
  void run() {
    var compilerOptions = new CompilerOptions.fromArguments(argResults);
    var compiler = new ModuleCompiler(compilerOptions);

    var outputDir = argResults['output-dir'] as String;
    if (outputDir != null) {
      outputDir = path.absolute(outputDir);
    } else {
      outputDir = Directory.current.path;
    }

    // Technically, Java allows any Unicode letter as part of an identifier,
    // including as part of a package name. For now, we only allow ASCII.
    RegExp javaIdentifier = new RegExp(r"^[A-Za-z_$][A-Za-z_$0-9]*$");

    var packageName = argResults['package-name'] as String;
    if (packageName != null) {
      var packageParts = packageName.split('.');

      // Validate the package parts
      for (var part in packageParts) {
        if (!javaIdentifier.hasMatch(part)) {
          usageException('package-name invalid - each part must start with a '
              'letter or underscore and should contain only letters, digits, '
              'and underscores (only ASCII letters and digits are supported)');
        }
        outputDir = path.join(outputDir, part);
      }
    }

    var unit = new BuildUnit(packageName, argResults.rest);

    var files = compiler.compile(unit);
    new Directory(outputDir).createSync(recursive: true);
    for (var file in files) {
      file.errors.forEach(messageHandler);

      if (!file.isValid) throw new CompileErrorException();

      new File(path.join(outputDir, file.name)).writeAsStringSync(file.code);
    }
  }
}

/// Thrown when the input source code has errors.
class CompileErrorException implements Exception {
  toString() => '\nPlease fix all errors before compiling (warnings are okay).';
}
