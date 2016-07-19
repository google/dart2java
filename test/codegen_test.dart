#!/usr/bin/env dart
// Copyright (c) 2016, the Dart project authors.  Please see the AUTHORS file
// for details. All rights reserved. Use of this source code is governed by a
// BSD-style license that can be found in the LICENSE file.

/// Tests code generation.
///
/// Runs Dart Dev Compiler on all input in the `codegen` directory and checks
/// that the output is what we expected.
import 'dart:io' show Directory, File;
import 'package:args/args.dart' show ArgParser, ArgResults;
import 'package:path/path.dart' as path;
import 'package:test/test.dart' show test;
import 'testing.dart' show repoDirectory, testDirectory;

import 'package:dart2java/src/compiler/compiler.dart'
    show CompilerOptions, ModuleCompiler;

/// The `test/codegen` directory.
final codegenDir = path.join(testDirectory, 'codegen');

/// The `test/codegen_expect` directory.
final codegenExpectDir = path.join(testDirectory, 'codegen_expect');

/// The generated directory where tests and test support libraries are copied
/// to.
///
/// The tests sometimes import utility libraries using a relative path.
final codegenTestDir = path.join(repoDirectory, 'gen', 'codegen_tests');

/// The generated directory where tests and packages compiled to Java are
/// output.
final codegenOutputDir = path.join(repoDirectory, 'gen', 'codegen_output');

final expectedToFailConfig = path.join(testDirectory, 'codegen_failures.txt');

main(List<String> arguments) {
  if (arguments == null) arguments = [];

  var parser = new ArgParser();
  parser.addFlag('force',
      abbr: 'f', help: 'Forcibly run tests marked as "skipped".');
  ArgResults args = parser.parse(arguments);

  var filePattern = new RegExp(args.rest.length > 0 ? args.rest[0] : '.');

  // Copy all of the test files to gen/codegen_tests. We'll compile from there.
  List<String> testFiles = _setUpTests(filePattern);

  // Our default compiler options. Individual tests can override these.
  var defaultOptions = [
    '--build-root',
    codegenTestDir,
    '--output-dir',
    codegenOutputDir
  ];
  var compilerArgParser = CompilerOptions.addArguments(new ArgParser());

  List<String> expectedToFail = _loadExpectedToFail(filePattern);

  // Compile each test file to Java and put the result in gen/codegen_output.
  for (String testFile in testFiles) {
    String relativePath = path.relative(testFile, from: codegenTestDir);

    String name = path.withoutExtension(relativePath);
    String skip = (!args['force'] && expectedToFail.contains(name))
        ? "Test expected to fail."
        : null;
    test('dart2java $name', () {
      String relativeDir = path.dirname(relativePath);
      String outDir = path.join(codegenOutputDir, relativeDir);
      String expectDir = path.join(codegenExpectDir, relativeDir);
      _ensureDirectory(outDir);
      _ensureDirectory(expectDir);

      // Check if we need to use special compile options.
      var contents = new File(testFile).readAsStringSync();
      var match =
          new RegExp(r'// compile options: (.*)').matchAsPrefix(contents);

      var args = defaultOptions.toList();
      if (match != null) {
        args.addAll(match.group(1).split(' '));
      }
      var options =
          new CompilerOptions.fromArguments(compilerArgParser.parse(args));

      Set<File> files = new ModuleCompiler(options).compile([testFile]);
      for (var file in files) {
        var relativePath = path.relative(file.path, from: codegenOutputDir);
        var newPath = path.join(codegenExpectDir, relativePath);
        _ensureDirectory(path.dirname(newPath));
        file.copySync(newPath);
      }
    }, skip: skip);
  }
}

List<String> _setUpTests(RegExp filePattern) {
  _ensureDirectory(codegenTestDir);

  var testFiles = <String>[];

  for (var file in _listFiles(codegenDir, filePattern)) {
    var relativePath = path.relative(file, from: codegenDir);
    var outputPath = path.join(codegenTestDir, relativePath);

    new File(file).copySync(outputPath);
    if (file.endsWith(".dart")) {
      testFiles.add(outputPath);
    }
  }

  return testFiles;
}

List<String> _loadExpectedToFail(RegExp filePattern) {
  return new File(expectedToFailConfig)
      .readAsLinesSync()
      .map((line) => line.split("//")[0].trim()) // Remove all comments.
      .where((line) => line.isNotEmpty) // Remove empty lines.
      .toList();
}

/// Recursively creates [dir] if it doesn't exist.
void _ensureDirectory(String dir) {
  new Directory(dir).createSync(recursive: true);
}

/// Lists all of the files within [dir] that match [filePattern].
Iterable<String> _listFiles(String dir, RegExp filePattern,
    {bool recursive: false}) {
  return new Directory(dir)
      .listSync(recursive: recursive, followLinks: false)
      .where((entry) {
    if (entry is! File) return false;

    var filePath = entry.path;
    if (!filePattern.hasMatch(filePath)) return false;

    return true;
  }).map((file) => file.path);
}
