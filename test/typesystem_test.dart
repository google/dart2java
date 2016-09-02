#!/usr/bin/env dart
// Copyright (c) 2016, the Dart project authors.  Please see the AUTHORS file
// for details. All rights reserved. Use of this source code is governed by a
// BSD-style license that can be found in the LICENSE file.

/// Tests the type system.
import 'dart:io' show Directory, File, Process, ProcessResult;
import 'package:args/args.dart' show ArgParser, ArgResults;
import 'package:path/path.dart' as path;
import 'package:test/test.dart' show test, expect, isZero;
import 'testing.dart' show repoDirectory, testDirectory;

/// The `test/typesystem` directory.
final testDir = path.join(testDirectory, 'typesystem');

final String junitJar =
    path.join(repoDirectory, 'third_party', 'junit-4.12', 'junit-4.12.jar');
final String hamcrestJar = path.join(
    repoDirectory, 'third_party', 'hamcrest-1.3', 'hamcrest-core-1.3.jar');
final String compiledSdkDir = path.join(repoDirectory, 'gen', 'compiled_sdk');
final String typeSystemDir =
    path.join(compiledSdkDir, 'dart', '_runtime', 'types');

final String classpath =
    [junitJar, hamcrestJar, compiledSdkDir, testDir].join(':');
final List<String> defaultArgs = ['-cp', classpath];

/// Config file containing the list of tests expected not to pass.
final expectedToFailConfig =
    path.join(testDirectory, 'typesystem_failures.txt');

main(List<String> arguments) {
  if (arguments == null) arguments = [];

  ArgParser parser = new ArgParser();
  parser.addFlag('force',
      abbr: 'f', help: 'Forcibly run tests marked as "skipped".');
  ArgResults args = parser.parse(arguments);

  Set<String> expectedToFail = _loadExpectedToFail();

  List<String> testFiles = listFiles(testDir, recursive: true, ext: '.java');
  // Compile the type system implementation and the test files.
  test('typesystem compilation', () {
    var filesToCompile = testFiles.toList()
      ..addAll(
          listFiles(typeSystemDir, recursive: true, ext: '.java').toList());

    ProcessResult result =
        Process.runSync('javac', defaultArgs.toList()..addAll(filesToCompile));
    expect(result.exitCode, isZero, reason: result.stderr);
  });

  // Run each test file
  for (String filepath in testFiles) {
    if (path.basename(filepath) == 'Util.java') {
      continue;
    }

    runTest(filepath,
        skip: !args['force'] &&
            expectedToFail.contains(filepathToName(filepath)));
  }
}

void runTest(String filepath, {bool skip: false}) {
  String name = filepathToName(filepath);
  String skipMsg = skip ? "Test expected to fail." : null;

  test('typesystem $name', () {
    ProcessResult compileResult =
        Process.runSync('javac', defaultArgs.toList()..add(filepath));
    expect(compileResult.exitCode, isZero, reason: compileResult.stderr);

    ProcessResult testResult = Process.runSync('java',
        defaultArgs.toList()..addAll(["org.junit.runner.JUnitCore", name]));
    expect(testResult.exitCode, isZero, reason: testResult.stdout);
  }, skip: skipMsg);
}

Iterable<String> listFiles(String dir, {bool recursive: false, String ext}) {
  return new Directory(dir)
      .listSync(recursive: recursive)
      .where((entry) =>
          entry is File && (ext == null || path.extension(entry.path) == ext))
      .map((entry) => entry.path);
}

String filepathToName(String filepath) {
  String relativePath = path.relative(filepath, from: testDir);
  return path.split(path.withoutExtension(relativePath)).join('.');
}

Set<String> _loadExpectedToFail() {
  return new File(expectedToFailConfig)
      .readAsLinesSync()
      .map((line) => line.split("//")[0].trim()) // Remove all comments.
      .where((line) => line.isNotEmpty) // Remove empty lines.
      .toSet();
}
