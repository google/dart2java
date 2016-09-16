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

import 'dart:io';

import 'package:collection/collection.dart';
import 'package:path/path.dart' as path;

// [ ] Produce summary table.

main() async {
  List<List<DataRow>> allData = files.map(loadDataFile).toList();

  printComparisons(allData);

  var _comparisons = computeAllComparisons(allData);
  comparisons.forEach((ComparisonGroup cg) {
    var data = cg.comparisons.map((Comparison c) {
      return _comparisons[files.indexOf(c.numerator)]
          [files.indexOf(c.denominator)];
    }).toList();
    var out = new File(path.join(resultsDir, "${cg.name}-cmp.csv"));
    if (out.existsSync()) out.deleteSync();
    for (int j = 0; j < data[0].length; ++j) {
      String line = "${data[0][j]}";
      for (int i = 1; i < data.length; ++i) {
        line += ",${data[i][j]}";
      }
      out.writeAsStringSync("$line\n", mode: FileMode.APPEND);
    }
  });
}

/// The [fileName] should not include extension as that is always .csv.
List<DataRow> loadDataFile(String fileName) {
  List<String> lines =
      new File(path.join(resultsDir, fileName + ".csv")).readAsLinesSync();
  return lines.sublist(1, lines.length).map((l) => new DataRow.fromCsvLine(l));
}

void printComparisons(List<List<DataRow>> allData) {
  var out = new File(path.join(resultsDir, "results-cmp.csv"));
  var averages = averageAllData(allData);
  out.writeAsStringSync((files.toList()..insert(0, "column/row")).join(","),
      mode: FileMode.APPEND);
  var comparisons = allToAllComparison(averages).toList();
  for (int i = 0; i < comparisons.length; ++i) {
    out.writeAsStringSync(
        (comparisons[i].map((n) => n.toStringAsFixed(2)).toList()
              ..insert(0, files[i]))
            .join(","),
        mode: FileMode.APPEND);
  }
}

List<num> averageAllData(List<List<DataRow>> data) {
  return data.map((l) => l.fold(0, (v, e) => v + e.mean) / l.length).toList()
      as List<num>;
}

List<num> averageJitComparisons(List<List<num>> data) {
  return data.map((l) => l.reduce((v, e) => v + e) / l.length).toList();
}

List<List<List<num>>> computeAllComparisons(List<List<DataRow>> data) {
  return data
      .map((f) => data
          .map((s) => new IterableZip([f, s])
              .map((pair) => pair[0].mean / pair[1].mean)
              .toList())
          .toList())
      .toList();
}

Iterable<Iterable<num>> allToAllComparison(List<num> averages) {
  return averages.map((f) => averages.map((s) => s / f));
}

class DataRow {
  num mean;
  num stdev;

  DataRow.fromCsvLine(String line) {
    List<num> parts = line.split(',').map(num.parse).toList();
    mean = parts[0];
    stdev = parts[1];
  }

  String toString() {
    return "$mean,$stdev";
  }
}

class Comparison {
  final String numerator;
  final String denominator;

  const Comparison(this.numerator, this.denominator);

  @override
  String toString() {
    return "$numerator over $denominator";
  }
}

class ComparisonGroup {
  final String name;
  final List<Comparison> comparisons;

  const ComparisonGroup(this.name, this.comparisons);
}

const resultsDir = "results";

const files = const [
  "is",
  "iv1a",
  "iv1b",
  "iv1c",
  "iv2",
  "ii1a",
  "ii1b",
  "ii1c",
  "ii2",
];

const List<ComparisonGroup> comparisons = const [
  const ComparisonGroup("iv", const [
    const Comparison("iv1b", "iv1a"),
    const Comparison("iv1c", "iv1a"),
    const Comparison("iv2", "iv1a"),
  ]),
  const ComparisonGroup("ii", const [
    const Comparison("ii1b", "ii1a"),
    const Comparison("ii1c", "ii1a"),
    const Comparison("ii2", "ii1a"),
  ]),
  const ComparisonGroup("1a", const [
    const Comparison("ii1a", "is"),
    const Comparison("iv1a", "is"),
    const Comparison("ii1a", "iv1a"),
  ]),
  const ComparisonGroup("1b", const [const Comparison("ii1b", "iv1b"),]),
  const ComparisonGroup("1c", const [const Comparison("ii1c", "iv1c"),]),
  const ComparisonGroup("min-max", const [const Comparison("iv1c", "is")]),
];
