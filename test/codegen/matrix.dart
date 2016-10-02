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

// https://github.com/dart-lang/ton80/blob/master/lib/src/common/dart/BenchmarkBase.dart
// Copyright 2011 Google Inc. All Rights Reserved.
class BenchmarkBase {
  final String name;

  // Empty constructor.
  const BenchmarkBase(String name) : this.name = name;

  static const int iters = 1000;

  // The benchmark code.
  // This function is not used, if both [warmup] and [exercise] are overwritten.
  void run() { }

  // Runs a short version of the benchmark. By default invokes [run] once.
  void warmup() {
    run();
  }

  // Exercices the benchmark. By default invokes [run] 10 times.
  void exercise() {
    for (int i = 0; i < iters; i++) {
      run();
    }
  }

  // Not measured setup code executed prior to the benchmark runs.
  void setup() { }

  // Not measures teardown code executed after the benchark runs.
  void teardown() { }

  // Measures the score for this benchmark by executing it repeately until
  // time minimum has been reached.
  double measureForWarumup(int timeMinimum) {
    int time = 0;
    int iter = 0;
    Stopwatch watch = new Stopwatch();
    watch.start();
    int elapsed = 0;
    while (elapsed < timeMinimum) {
      warmup();
      elapsed = watch.elapsedMilliseconds;
      iter++;
    }
    return (1000.0 * elapsed / iter) / iters;
  }

  // Measures the score for this benchmark by executing it repeately until
  // time minimum has been reached.
  double measureForExercise(int timeMinimum) {
    int time = 0;
    int iter = 0;
    Stopwatch watch = new Stopwatch();
    watch.start();
    int elapsed = 0;
    while (elapsed < timeMinimum) {
      exercise();
      elapsed = watch.elapsedMilliseconds;
      iter++;
    }
    return (1000.0 * elapsed / iter) / iters;
  }

  // Measures the score for the benchmark and returns it.
  double measure() {
    setup();
    // Warmup for at least 1000ms. Discard result.
    measureForWarumup(1000);
    // Run the benchmark for at least 50000ms.
    double result = measureForExercise(10000);
    teardown();
    return result;
  }

  void report() {
    double score = measure();
    print("$name(RunTime): $score us.");
  }

}


// Matrix-matrix multiplication
void main() {
  print("Running benchmark...");
  new Matrix().report();
  print("Done.");
}

/// Benchmark class required to report results.
class Matrix extends BenchmarkBase {

  const Matrix() : super("Matrix");

  void run() {
    int size = 35;

    List<List<int>> left = new List<List<int>>(size);
    for (int i = 0; i < left.length; i++) {
      left[i] = new List<int>(size);
    }

    List<List<int>> right = new List<List<int>>(size);
    for (int i = 0; i < right.length; i++) {
      right[i] = new List<int>(size);
    }

    List<List<int>> result = new List<List<int>>(size);
    for (int i = 0; i < result.length; i++) {
      result[i] = new List<int>(size);
    }

    for (int i = 0; i < left.length; i++) {
      for (int j = 0; j < left.length; j++) {
        left[i][j] = i * j;
      }
    }

    // Identity matrix
    for (int i = 0; i < right.length; i++) {
      for (int j = 0; j < right.length; j++) {
        if (i == j) {
          right[i][j] = 1;
        } else {
          right[i][j] = 0;
        }
      }
    }

    for (int i = 0; i < result.length; i++) {
      for (int j = 0; j < result.length; j++) {
        result[i][j] = 0;
      }
    }

    for (int i = 0; i < left.length; i++) {
      for (int j = 0; j < left[i].length; j++) {
        for (int k = 0; k < right.length; k++) {
          result[i][j] = result[i][j] + left[i][k] * right[k][j];
        }
      }
    }

    for (int i = 0; i < result.length; i++) {
      for (int j = 0; j < result.length; j++) {
        if (result[i][j] != i * j) {
          print("ERROR: Expected ${i * j}, but found ${result[i][j]}");
        }
      }
    }
  }
}
