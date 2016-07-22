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
/// * Dart files in the test's subdirectory, to be compiled. Otherwise, why
///   bother? ;)
/// * A text file in the test's subdirectory named `test.meta`, with the
///   following lines (in any order):
///     * Optionally, a line starting with the word `skip` (then, after a space,
///       an optional comment). If present, the test will be skipped unless the
///       --force option is passed to the test runner.
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
///       `codegen_expect/multifle/<testname>/compiled/`.
///     * Zero or more lines starting with either `java` or `java[<id>]`,
///       followed by a space-separated list of arguments to pass to the `java`
///       executable. Similar to the above, stdout and stderr are captured in
///       files `codegen_expect/<testname>/java_<id>.std[out|err]`. `java` will
///       be invoked with the `codegen_expect/multifle/<testname>/compiled/`
///       directory in its classpath, so the files compiled in the test will be
///       found automatically. Note that the test runner calls `javac` before
///       calling `java`, so there should be `.class` files in the
///       `codegen_expect/multifle/<testname>/compiled/` directory (assuming the
///       tests passed!)
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
import 'dart:io' show Directory, File, Process, ProcessResult;
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
    test('dart2java-multifle ${t.name}', () {
      String expectDir = path.join(multifileExpectDir, t.name);
      String compiledDir = path.join(expectDir, 'compiled');
      _ensureDirectory(expectDir);
      _ensureDirectory(compiledDir);

      // Invoke dart2java.
      for (ProgramInvocation d2j in t.dart2javaInvocations) {
        var args = ['--output-dir', compiledDir]..addAll(d2j.args);
        ProcessResult result = Process.runSync('dart2java', args,
            workingDirectory: path.join(multifileTestDir, t.name));
        var output = _writeResult(expectDir, 'dart2java_${d2j.id}', result);
        expect(result.exitCode, isZero, reason: 'dart2java failed.\n'
            '  stdout: ${output.stdout}\n'
            '  stderr: ${output.stderr}\n');
      }

      // Invoke javac.
      for (String javaPath in _findJavaFiles(compiledDir)) {
        String outputDir = path.dirname(javaPath);
        ProcessResult result = Process.runSync('javac', [javaPath]);
        var output = _writeResult(outputDir,
            'javac_${path.basenameWithoutExtension(javaPath)}', result);
        expect(result.exitCode, isZero, reason: 'javac failed.\n'
            '  stdout: ${output.stdout}\n'
            '  stderr: ${output.stderr}\n');
      }

      // Invoke java.
      for (ProgramInvocation java in t.javaInvocations) {
        var autoClassPaths = [
          path.join(repoDirectory, 'gen', 'compiled_sdk.jar'),
          compiledDir
        ];
        var args =
            _mergeClassPath(['-cp', autoClassPaths.join(':')], java.args);
        ProcessResult result = Process.runSync('java', args);
        var output = _writeResult(expectDir, 'java_${java.id}', result);
        expect(result.exitCode, isZero, reason: 'java failed.\n'
            '  stdout: ${output.stdout}\n'
            '  stderr: ${output.stderr}\n');
      }
    }, skip: skip);
  }
}

class ProgramInvocation {
  /// Currently supported: `dart2java` and `java`.
  String program;

  /// May be null if no explicit id was specified.
  String id;

  /// Arguments (already split at spaces).
  List<String> args;

  ProgramInvocation(String line) {
    List<String> words = line.split(new RegExp(r'\s'));
    List<String> programParts = words.first.split(new RegExp(r'\[|\]'));
    program = programParts.first;
    if (programParts.length == 2) {
      id = programParts[1];
    } else {
      assert(programParts.length == 1);
      id = null;
    }
    args = words.skip(1).toList();
  }
}

class MultiFileTestMeta {
  String name;

  /// If non-null, skip the test with this comment. If null, do not skip.
  String skipComment;

  List<ProgramInvocation> dart2javaInvocations = [];

  List<ProgramInvocation> javaInvocations = [];

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
      } else {
        throw new Exception('Malformed file ${metaFile.path}.\n'
            'Unrecognized line: $line');
      }
    }
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

    return true;
  }).map((dir) => dir.path);
}

final _javaFilePattern = new RegExp(r'.java$');

/// Returns a list of all `*.java` files in [dir], recursviely.
Iterable<String> _findJavaFiles(String dir) {
  return new Directory(dir)
      .listSync(recursive: true)
      .where((entry) => entry is File && _javaFilePattern.hasMatch(entry.path))
      .map((entry) => entry.path);
}

/// Given two lists of arguments to `java`, join the lists together; if both
/// lists specify a value for -classpath, merge the -classpath values.
List<String> _mergeClassPath(Iterable<String> a, Iterable<String> b) {
  List<String> aa = a.toList(), bb = b.toList();
  List<String> classPath = _removeClassPath(aa).toList();
  classPath.addAll(_removeClassPath(bb));
  return ['-cp', classPath.join(':')]..addAll(aa)..addAll(bb);
}

/// If [javaArgs] contains a `-classpath` option, remove it and return the list
/// of files named in the `-classpath` option.
Iterable<String> _removeClassPath(List<String> javaArgs) {
  for (int i = 0; i < javaArgs.length - 1; i++) {
    if (javaArgs[i] == '-cp' || javaArgs[i] == '-classpath') {
      var result = javaArgs[i + 1].split(':');
      javaArgs.removeRange(i, i + 2);
      return result;
    }
  }
  return const [];
}

class _StdOutErr {
  final String stdout;
  final String stderr;

  _StdOutErr(this.stdout, this.stderr);
}

_StdOutErr _writeResult(String dir, String basename, ProcessResult result) {
  String stdout = path.join(dir, '$basename.stdout');
  String stderr = path.join(dir, '$basename.stderr');
  new File(stdout).writeAsStringSync(result.stdout);
  new File(stderr).writeAsStringSync(result.stderr);
  return new _StdOutErr(stdout, stderr);
}
