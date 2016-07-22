#!/usr/bin/env dart

// Copyright (c) 2016, the Dart project authors.  Please see the AUTHORS file
// for details. All rights reserved. Use of this source code is governed by a
// BSD-style license that can be found in the LICENSE file.

/// Tests code generation on test cases involving multiple files or multiple
/// compilations.
///
/// Tests are loaded from the `codegen/multifile` directory. A test consists of:
/// * A subdirectory of codegen/multifile, which contains all files related
///   to the test. The name of this subdirectory is used as the name of the
///   test. The examples in this comment use the name `multifile-example`, so
///   the full path of this subdirectory (relative to the project root) should
///   be:
///       test/codegen/multifile/multifile-example
///   You should not create a test named `packages` (which would conflict with
///   the `packages` symlink that pub likes to create).
/// * Dart files in the test's subdirectory, to be compiled. Otherwise, why
///   bother? ;)
/// * A text file in the test's subdirectory named `test.meta`, with the
///   following lines (in any order):
///     * Optionally, a line starting with the word `skip` (then, after a space,
///       an optional comment). If present, the test will be skipped unless the
///       --force option is passed to the test runner.
///     * Zero or more lines starting with the word `classpath`, followed by a
///       space, then a path to a Java jar file or to a directory. The named jar
///       file or directory will be added to the classpath when compiling or
///       running all Java code in the test. One jar file or directory per
///       `classpath` line. Paths are relative to the test directory (i.e
///       /test/codegen/multifile/multifile-example/ in the example). Note that
///       the classpath will always include the Dart SDK and the dart2java
///       output directory, so you should only need to add `classpath` lines if
///       you're importing a non-core Dart package or interoperating with Java.
///     * One or more lines starting with either the word `dart2java` or with
///      `dart2java[<id>]`, where <id> is an alphanumeric string. The rest of
///       the line should be a space-separated list of arguments to pass to
///       dart2java. For each such line, the compiler will be invoked with the
///       specified arguments, which should *not* include --output-dir. The
///       compiler's stdout and stderr streams are captured in files
///       `codegen_expect/multifile/<testname>/dart2java_<id>.std[out|err]`.
///       If `<id>` is not specified in the `dart2java` entry, it defaults to
///       the 1-based index of the entry in the list of all `dart2java` entries
///       in the file. The compiled files will be copied into
///       `codegen_expect/multifile/<testname>/compiled/`.
///     * Zero or more lines starting with either `java` or `java[<id>]`,
///       followed by a space-separated list of arguments to pass to the `java`
///       executable. Similar to the above, stdout and stderr are captured in
///       files `codegen_expect/<testname>/java_<id>.std[out|err]`. `java` will
///       be invoked with the `codegen_expect/multifile/<testname>/compiled/`
///       directory in its classpath, so the files compiled in the test will be
///       found automatically. Note that the test runner calls `javac` before
///       calling `java`, so there will be `.class` files in the
///       `codegen_expect/multifile/<testname>/compiled/` directory (assuming the
///       tests passed!). Do not pass '-classpath' as an argument. Use a
///       `classpath` line instead, so that the classpath is also set correctly
///       for javac.
///     * Blank lines or whole-line comments starting with '#' are allowed
///       anywhere. *Note that '#' does not start a comment unless it is the
///       first non-whitespace character on a line.*
///
/// A sample test:
///
///     /test/codegen/multifile/multifile-example/
///       test.meta:
///         dart2java[foo] --package-prefix=org.example.fooproj --build-root=fooproj fooproj/foo.dart
///         dart2java[bar] --package-prefix=com.google.barproj --build-root=barproj barproj/bar1.dart barproj/bar2.dart
///         dart2java[main] --package-prefix=my.awesome.project --build-root=mainproj mainproj/main.dart
///         java[main] my.awesome.project.main.MainClass
///         java[other] my.awesome.project.main.OtherMainClass
///       fooproj/
///         foo.dart
///       barproj/
///         bar1.dart
///         bar2.dart
///       mainproj/
///         main.dart
///
/// The generated files:
///
///     /test/codegen_expect/multifile/multifile-example/
///       dart2java_foo.stdout
///       dart2java_foo.stderr
///       dart2java_bar.stdout
///       dart2java_bar.stderr
///       dart2java_main.stdout
///       dart2java_main.stderr
///       java_main.stdout
///       java_main.stderr
///       java_other.stdout
///       java_other.stderr
///       compiled/
///         org/example/fooproj/foo/
///           <some .java, .class, and javac_<filename>.std[out|err] files>
///         com/google/barproj/bar1/
///           <some .java, .class, and javac_<filename>.std[out|err] files>
///           <some .java files and .class files>
///         com/google/barproj/bar2/
///           <some .java, .class, and javac_<filename>.std[out|err] files>
///           <some .java files and .class files>
///         my/awesome/project/main/
///           <some .java, .class, and javac_<filename>.std[out|err] files>
///           <some .java files and .class files>

import 'dart:io'
    show
        Directory,
        File,
        Process,
        ProcessResult,
        FileSystemEntity,
        FileSystemEntityType;
import 'package:args/args.dart' show ArgParser, ArgResults;
import 'package:path/path.dart' as path;
import 'package:test/test.dart' show test, expect, isZero;
import 'testing.dart' show repoDirectory, testDirectory;

/// The `test/codegen/multifile` directory.
final multifileTestDir = path.join(testDirectory, 'codegen', 'multifile');

/// The generated directory where tests and packages compiled to Java are
/// output.
final multifileExpectDir =
    path.join(testDirectory, 'codegen_expect', 'multifile');

main(List<String> arguments) {
  var parser = new ArgParser();
  parser.addFlag('force',
      abbr: 'f', help: 'Forcibly run tests marked as "skipped".');
  ArgResults args = parser.parse(arguments ?? const []);

  var testPattern = new RegExp(args.rest.length > 0 ? args.rest[0] : '.');

  List<MultiFileTestMeta> tests =
      _listDirectories(multifileTestDir, testPattern)
          .map((String dir) => new MultiFileTestMeta(dir))
          .toList();

  for (var t in tests) {
    String skip = (!args['force'] && t.skipComment != null)
        ? "Test expected to fail: ${t.skipComment}"
        : null;
    test('dart2java-multifile ${t.name}', () {
      String expectDir = path.join(multifileExpectDir, t.name);
      String compiledDir = path.join(expectDir, 'compiled');
      _ensureDirectory(expectDir);
      _ensureDirectory(compiledDir);

      var baseClassPath = ([
        path.join(repoDirectory, 'gen', 'compiled_sdk.jar'),
        compiledDir
      ]..addAll(t.classpath))
          .join(':');

      // Invoke dart2java.
      for (ProgramInvocation d2j in t.dart2javaInvocations) {
        var args = ['--output-dir', compiledDir]..addAll(d2j.args);
        ProcessResult result = Process.runSync('dart2java', args,
            workingDirectory: path.join(multifileTestDir, t.name));
        var output = _writeResult(expectDir, 'dart2java_${d2j.id}', result);
        expect(result.exitCode, isZero,
            reason: 'Reason: dart2java failed.\n'
                '  stdout: ${output.stdout}\n'
                '  stderr: ${output.stderr}\n');
      }

      // Invoke javac.
      for (String javaPath in _findJavaFiles(compiledDir)) {
        String outputDir = path.dirname(javaPath);
        var args = ['-cp', baseClassPath, javaPath];
        ProcessResult result = Process.runSync('javac', args);
        var output = _writeResult(outputDir,
            'javac_${path.basenameWithoutExtension(javaPath)}', result);
        expect(result.exitCode, isZero,
            reason: 'Reason: javac failed.\n'
                '  stdout: ${output.stdout}\n'
                '  stderr: ${output.stderr}\n');
      }

      // Invoke java.
      for (ProgramInvocation java in t.javaInvocations) {
        var args = ['-cp', baseClassPath]..addAll(java.args);
        ProcessResult result = Process.runSync('java', args);
        var output = _writeResult(expectDir, 'java_${java.id}', result);
        expect(result.exitCode, isZero,
            reason: 'Reason: java failed.\n'
                '  stdout: ${output.stdout}\n'
                '  stderr: ${output.stderr}\n');
      }
    }, skip: skip);
  }
}

class ProgramInvocation {
  static final RegExp programPattern =
      new RegExp(r'^([a-zA-Z0-9_-]+)(?:\[([a-zA-Z0-9_-]+)\])?$');

  /// Currently supported: `dart2java` and `java`.
  String program;

  /// May be null if no explicit id was specified.
  String id;

  /// Arguments (already split at spaces).
  List<String> args;

  ProgramInvocation(String line) {
    List<String> words = line.split(new RegExp(r'\s'));
    var programMatch = programPattern.matchAsPrefix(words.first);
    if (programMatch == null) {
      throw new Exception('Error: Invalid program invocation "$line"\n'
          'Program name "${words.first}" does not match $programPattern.');
    }
    program = programMatch.group(1);
    id = programMatch.group(2);
    args = words.skip(1).toList();
  }
}

class MultiFileTestMeta {
  String name;

  /// If non-null, skip the test with this comment. If null, do not skip.
  String skipComment;

  List<ProgramInvocation> dart2javaInvocations = [];

  List<ProgramInvocation> javaInvocations = [];

  List<String> classpath = [];

  MultiFileTestMeta(String testDir) {
    name = path.basename(testDir);
    var metaFile = new File(path.join(testDir, 'test.meta'));
    for (String line in metaFile.readAsLinesSync()) {
      line = line.trim();
      if (line.isEmpty || line.startsWith("#")) {
        continue;
      } else if (line.startsWith("skip")) {
        if (line.startsWith("skip ")) {
          skipComment = line.substring("skip ".length);
        } else {
          skipComment = "";
        }
      } else if (line.startsWith("dart2java")) {
        dart2javaInvocations.add(new ProgramInvocation(line));
        dart2javaInvocations.last.id ??= '${dart2javaInvocations.length}';
      } else if (line.startsWith("java")) {
        javaInvocations.add(new ProgramInvocation(line));
        javaInvocations.last.id ??= '${javaInvocations.length}';
      } else if (line.startsWith("classpath")) {
        var newClasspath = line.substring("classpath ".length);
        if (path.isRelative(newClasspath)) {
          newClasspath = path.join(testDir, newClasspath);
        }
        _verifyValidClasspathEntry(newClasspath);
        classpath.add(newClasspath);
      } else {
        throw new Exception('Error: Malformed file ${metaFile.path}.\n'
            'Unrecognized line: $line');
      }
    }

    _verifyInvocations(dart2javaInvocations, metaFile.path);
    _verifyInvocations(javaInvocations, metaFile.path);
  }
}

/// Check a list of invocations (assumed to be invocations of the same program),
/// report any non-fatal warnings, and throw on fatal errors.
void _verifyInvocations(List<ProgramInvocation> invocations, String metaPath) {
  String invocationName;
  int numUnlabeled = 0;
  Set<String> seenIds = new Set();
  for (var invocation in invocations) {
    invocationName ??= invocation.program;
    if (invocation.id == null ||
        int.parse(invocation.id, onError: (s) => null) != null) {
      numUnlabeled++;
    }
    if (!seenIds.add(invocation.id)) {
      throw new Exception(
          'Error: duplicate ID `$invocationName[${invocation.id}]` in file\n'
          '  $metaPath');
    }
  }
  if (invocations.length > 1 && numUnlabeled > 0) {
    print('Warning: you have multiple `$invocationName` lines in file\n'
        '  $metaPath\n'
        ' but you don\'t specify an ID for $numUnlabeled of them.\n'
        'Consider adding IDs, e.g. `$invocationName[foo]`. See the comment\n'
        'in test/multifile_codegen_test.dart for more information.');
  }
}

/// Recursively creates [dir] if it doesn't exist.
void _ensureDirectory(String dir) {
  new Directory(dir).createSync(recursive: true);
}

/// Lists all of the directories within [dir] that match [filePattern].
Iterable<String> _listDirectories(String dir, RegExp dirPattern) {
  return new Directory(dir).listSync().where((entry) {
    if (entry is! Directory) return false;

    var dirPath = entry.path;
    if (!dirPattern.hasMatch(dirPath)) return false;

    // Skip the 'packages' symlink that pub likes to generate.
    if (path.basename(dirPath) == 'packages' &&
        FileSystemEntity.typeSync(dirPath, followLinks: false) ==
            FileSystemEntityType.LINK) {
      return false;
    }

    return true;
  }).map((dir) => dir.path);
}

final _javaFilePattern = new RegExp(r'.java$');

/// Returns a list of all `*.java` files in [dir], recursively.
Iterable<String> _findJavaFiles(String dir) {
  return new Directory(dir)
      .listSync(recursive: true)
      .where((entry) => entry is File && _javaFilePattern.hasMatch(entry.path))
      .map((entry) => entry.path);
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

/// Verify that a file path makes sense as a classpath entry; throw on errors.
_verifyValidClasspathEntry(String classpath) {
  FileSystemEntityType entityType = FileSystemEntity.typeSync(classpath);
  switch (entityType) {
    case FileSystemEntityType.FILE:
      // Assume that if the file ends in '.jar', then it is a jar file.
      // Verifying that it's a valid jar file is overkill.
      if (path.extension(classpath).toLowerCase() != '.jar') {
        throw new Exception('Error: Invalid classpath entry "$classpath" '
            '(filename does not end in .jar)');
      }
      return;
    case FileSystemEntityType.DIRECTORY:
      // Assume that a directory is a valid classpath entry.
      return;
    default:
      throw new Exception(
          'Error: Invalid classpath entry "$classpath" (file not found).');
  }
}
