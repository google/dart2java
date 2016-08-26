#!/usr/bin/env dart
// Copyright (c) 2016, the Dart project authors.  Please see the AUTHORS file
// for details. All rights reserved. Use of this source code is governed by a
// BSD-style license that can be found in the LICENSE file.

/// Tests code generation.
///
/// Runs Dart Dev Compiler on all input in the `codegen` directory and checks
/// that the output is what we expected.
import 'dart:io' show Directory, File, Process, ProcessResult;
import 'package:args/args.dart' show ArgParser, ArgResults;
import 'package:path/path.dart' as path;
import 'package:test/test.dart' show test, expect, isZero;
import 'testing.dart' show repoDirectory, testDirectory;

import 'package:dart2java/src/compiler/compiler.dart'
    show CompilerOptions, ModuleCompiler;

/// The `test/codegen` directory.
final codegenDir = path.join(testDirectory, 'codegen');

/// The `test/codegen_expect` directory.
final codegenExpectDir = path.join(testDirectory, 'codegen_expect');

/// The `gen` directory.
final genDir = path.join(repoDirectory, 'gen');

/// The generated directory where tests and test support libraries are copied
/// to.
///
/// The tests sometimes import utility libraries using a relative path.
final codegenTestDir = path.join(genDir, 'codegen_tests');

/// The generated directory where tests and packages compiled to Java are
/// output.
final codegenOutputDir = path.join(genDir, 'codegen_output');

/// Config file containing the list of tests expected not to pass.
final expectedToFailConfig = path.join(testDirectory, 'codegen_failures.txt');

/// The path to the Dart SDK compiled to a .jar file.
final compiledSdkJar = path.join(genDir, 'compiled_sdk.jar');

/// The path to the patched SDK
final patchedSdkDir = path.join(genDir, 'patched_sdk');

final compilerArgParser = CompilerOptions.addArguments(new ArgParser());

// Our default compiler options. Individual tests can override these.
final defaultOptions = [
  '--dart-sdk',
  patchedSdkDir,
  '--build-root',
  codegenTestDir,
  '--output-dir',
  codegenOutputDir
];


void main(List<String> arguments) {
  if (arguments == null) arguments = [];

  var parser = new ArgParser();
  parser.addFlag('force',
      abbr: 'f', help: 'Forcibly run tests marked as "skipped".');
  parser.addFlag('parallel',
      help: 'Run tests in parallel (not yet implemented).');
  ArgResults args = parser.parse(arguments);

  var filePattern = new RegExp(args.rest.length > 0 ? args.rest[0] : '.');

  // Copy all of the test files to gen/codegen_tests. We'll compile from there.
  List<String> testFiles = _setUpTests(filePattern);

  Set<String> expectedToFail = _loadExpectedToFail();

  // Compile each test file to Java and put the result in gen/codegen_output.
  for (String testFile in testFiles) {
    runTest(testFile, skip: !args['force'] &&
        expectedToFail.contains(testFileToName(testFile)));
  }
}

void runTest(String testFile, {bool skip: false}) {
  String relativePath = path.relative(testFile, from: codegenTestDir);

  String name = testFileToName(testFile);
  String skipMsg = skip ? "Test expected to fail." : null;
  test('dart2java $name', () {
    String relativeDir = path.dirname(relativePath);
    String outDir = path.join(codegenOutputDir, relativeDir);
    String expectDir = path.join(codegenExpectDir, relativeDir);
    _ensureDirectory(outDir);
    _ensureDirectory(expectDir);

    // Check if we need to use special compile options.
    var contents = new File(testFile).readAsStringSync();
    var match = new RegExp(r'// compile options: (.*)').matchAsPrefix(contents);

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

    files.forEach((f) => _javaCompile(f, codegenOutputDir));
    _run(name);
  }, skip: skipMsg);
}

String testFileToName(String testFile) {
  String relativePath = path.relative(testFile, from: codegenTestDir);
  return path.withoutExtension(relativePath);
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

Set<String> _loadExpectedToFail() {
  return new File(expectedToFailConfig)
      .readAsLinesSync()
      .map((line) => line.split("//")[0].trim()) // Remove all comments.
      .where((line) => line.isNotEmpty) // Remove empty lines.
      .toSet();
}

/// Recursively creates [dir] if it doesn't exist.
void _ensureDirectory(String dir) {
  new Directory(dir).createSync(recursive: true);
}

/// Compiles a .java [File] and returns the .class [File].
File _javaCompile(File javaFile, String classPathRoot) {
  var args = ['-cp', compiledSdkJar + ":${classPathRoot}", javaFile.path];
  ProcessResult result =
      Process.runSync('javac', args, workingDirectory: codegenOutputDir);
  expect(result.exitCode, isZero,
      reason: 'Reason: javac failed.\n'
          '  stdout: ${result.stdout}\n'
          '  stderr: ${result.stderr}\n');

  String name = path.withoutExtension(javaFile.path);
  File classFile = new File('$name.class');
  if (!classFile.existsSync()) {
    throw "Error: $javaFile failed to compile to $classFile.";
  }
  return classFile;
}

void _run(String testName) {
  var javaTopLevelClass = "$testName.__TopLevel";
  var args = <String>['-cp', "$compiledSdkJar:.", javaTopLevelClass];
  ProcessResult result =
      Process.runSync('java', args, workingDirectory: codegenOutputDir);

  String expectDir = path.join(codegenExpectDir, testName);
  var output = _writeResult(expectDir, "run", result);
  expect(result.exitCode, isZero,
      reason: 'Reason: java failed.\n'
          '  stderr: ${output.stderr}\n');
  print(result.stdout);
}

class _StdOutErr {
  final String stdout;
  final String stderr;

  _StdOutErr(this.stdout, this.stderr);
}

/// Write `.stdout` and `.stderr` files for the given [result] into directory
/// [dir], using filename `basename.std[out|err]`.
///
/// For convenience, returns the actual file paths written, wrapped in a
/// simple object.
_StdOutErr _writeResult(String dir, String basename, ProcessResult result) {
  String stdout = path.join(dir, '$basename.stdout');
  String stderr = path.join(dir, '$basename.stderr');
  new File(stdout).writeAsStringSync(result.stdout);
  new File(stderr).writeAsStringSync(result.stderr);
  return new _StdOutErr(stdout, stderr);
}
