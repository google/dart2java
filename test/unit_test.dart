#!/usr/bin/env dart
// Copyright (c) 2016, the Dart project authors.  Please see the AUTHORS file
// for details. All rights reserved. Use of this source code is governed by a
// BSD-style license that can be found in the LICENSE file.

/// Tests code generation.
///
/// Runs Dart Dev Compiler on all input in the `unit` directory and runSync
/// corresponding JUnit test cases.
import 'dart:io' show Directory, File, Process, ProcessResult;
import 'package:args/args.dart' show ArgParser, ArgResults;
import 'package:path/path.dart' as path;
import 'package:test/test.dart' show test, expect, isZero;
import 'testing.dart' show repoDirectory, testDirectory;

import 'package:dart2java/src/compiler/compiler.dart'
    show CompilerOptions, ModuleCompiler;

/// The `test/unit` directory.
final unitDir = path.join(testDirectory, 'unit');

/// The `gen` directory.
final genDir = path.join(repoDirectory, 'gen');

/// The generated directory where tests and test support libraries are copied
/// to.
///
/// The tests sometimes import utility libraries using a relative path.
final unitTestDir = path.join(genDir, 'unit_tests');

/// The generated directory where tests and packages compiled to Java are
/// output.
final unitOutputDir = path.join(genDir, 'unit_output');

/// Config file containing the list of tests expected not to pass.
final expectedToFailConfig = path.join(testDirectory, 'unit_failures.txt');

/// The path to the Dart SDK compiled to a .jar file.
final compiledSdkJar = path.join(genDir, 'compiled_sdk.jar');

/// JUnit dependencies (JAR files)
final junitJars = path.join(
        repoDirectory, 'third_party/junit-4.12/junit-4.12.jar') +
    ":" +
    path.join(repoDirectory, 'third_party/hamcrest-1.3/hamcrest-core-1.3.jar');

/// The path to the patched SDK
final patchedSdkDir = path.join(genDir, 'patched_sdk');

final compilerArgParser = CompilerOptions.addArguments(new ArgParser());

// Our default compiler options. Individual tests can override these.
final defaultOptions = ['--dart-sdk', patchedSdkDir];

void main(List<String> arguments) {
  if (arguments == null) arguments = [];

  var parser = new ArgParser();
  parser.addFlag('force',
      abbr: 'f', help: 'Forcibly run tests marked as "skipped".');
  ArgResults args = parser.parse(arguments);

  var dirPattern = new RegExp(args.rest.length > 0 ? args.rest[0] : '.');

  // Copy all of the test files to gen/unit_tests. We'll compile from there.
  List<String> testDirs = _setUpTests(dirPattern);

  Set<String> expectedToFail = _loadExpectedToFail(dirPattern);

  // Compile each test file to Java and put the result in gen/unit_output.
  for (String testDir in testDirs) {
    runTest(testDir, skip: !args['force'] &&
        expectedToFail.contains(testDirToName(testDir)));
  }
}

void runTest(String testDir, {bool skip:false}) {
  String relativePath = path.relative(testDir, from: unitTestDir);

  String name = testDirToName(testDir);
  String skipMsg = skip ? "Test expected to fail." : null;
  test('dart2java $name', () {
    String outDir = path.join(unitOutputDir, relativePath);
    _ensureDirectory(outDir);

    String scenarioFile = path.join(testDir, "scenario.dart");

    var args = defaultOptions.toList();
    args.addAll(['--build-root', testDir]);
    args.addAll(['--output-dir', outDir]);

    // Check if we need to use special compile options.
    var contents = new File(scenarioFile).readAsStringSync();
    var match = new RegExp(r'// compile options: (.*)').matchAsPrefix(contents);
    if (match != null) {
      args.addAll(match.group(1).split(' '));
    }
    var options =
        new CompilerOptions.fromArguments(compilerArgParser.parse(args));

    // Compile Dart to Java
    Set<File> files = new ModuleCompiler(options).compile([scenarioFile]);

    // Copy over JUnit test
    new Directory(testDir)
        .listSync(recursive: false, followLinks: false)
        .forEach((entry) {
      if (entry.path.endsWith(".java")) {
        var fileName = path.relative(entry.path, from: testDir);
        var targetFileName = path.join(outDir, fileName);
        new File(entry.path).copySync(targetFileName);
        files.add(new File(targetFileName));
      }
    });

    for (var file in files) {
      _javaCompile(file, outDir);
    }
    _run(name, outDir);
  }, skip: skipMsg);
}

String testDirToName(String testDir) {
  String relativePath = path.relative(testDir, from: unitTestDir);
  return path.withoutExtension(relativePath);
}

List<String> _setUpTests(RegExp dirPattern) {
  // Prune 'packages' symlinks from unit test directory
  Process.runSync('find', [unitDir, '-name', 'packages', '-delete']);
  _ensureDirectory(unitTestDir);

  var testDirs = <String>[];

  for (var dir in _listDirs(unitDir, dirPattern)) {
    var relativePath = path.relative(dir, from: unitDir);
    var outputPath = path.join(unitTestDir, relativePath);

    new Directory(outputPath).createSync();
    new Directory(dir)
        .listSync(recursive: false, followLinks: false)
        .forEach((entry) {
      var fileName = path.relative(entry.path, from: dir);
      new File(entry.path).copySync(path.join(outputPath, fileName));
    });

    testDirs.add(outputPath);
  }

  return testDirs;
}

Set<String> _loadExpectedToFail(RegExp dirPattern) {
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

/// Lists all of the directories within [dir] that match [dirPattern].
Iterable<String> _listDirs(String dir, RegExp dirPattern) {
  return new Directory(dir)
      .listSync(recursive: false, followLinks: false)
      .where((entry) {
    if (entry is! Directory) return false;

    var dirPath = entry.path;
    if (!dirPattern.hasMatch(dirPath)) return false;

    return true;
  }).map((dir) => dir.path);
}

/// Compiles a .java [File] and returns the .class [File].
File _javaCompile(File javaFile, String classPathRoot) {
  var args = [
    '-cp',
    compiledSdkJar + ':' + junitJars + ":${classPathRoot}",
    javaFile.path
  ];
  ProcessResult result =
      Process.runSync('javac', args, workingDirectory: unitOutputDir);
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

void _run(String testName, String classPathRoot) {
  var junitClass = "org.junit.runner.JUnitCore";
  var classPath = compiledSdkJar + ':' + junitJars + ":${classPathRoot}";
  var args = <String>['-cp', classPath, junitClass, "Tests"];
  ProcessResult result =
      Process.runSync('java', args, workingDirectory: unitOutputDir);

  String outDir = path.join(unitOutputDir, testName);
  var output = _writeResult(outDir, "run", result);

  // Print result of JUnit tests
  print(new File(output.stdout).readAsStringSync());
  expect(result.exitCode, isZero,
      reason: 'Reason: java failed.\n'
          '  stdout: ${output.stdout}\n'
          '  stderr: ${output.stderr}\n');
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
