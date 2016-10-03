// Implementation taken from:
// https://github.com/daftspaniel/dartdiamondsquare
// Copyright (c) 2013 Davy Mitchell

// Code slightly modified to make it run in dart2java

// https://github.com/dart-lang/ton80/blob/master/lib/src/common/dart/BenchmarkBase.dart
// Copyright 2011 Google Inc. All Rights Reserved.

import 'dart:math' show Random;

class Expect {
  static void equals(var expected, var actual) {
    if (expected != actual) {
      print("Values not equal: ");
      print(expected);
      print("vs ");
      print(actual);
    }
  }

  static void listEquals(List expected, List actual) {
    if (expected.length != actual.length) {
      print("Lists have different lengths: ");
      print(expected.length);
      print("vs ");
      print(actual.length);
    }
    for (int i = 0; i < actual.length; i++) {
      equals(expected[i], actual[i]);
    }
  }

  void fail(String message) {
    print(message);
  }
}


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
    // Warmup for at least 100ms. Discard result.
    measureForWarumup(100);
    // Run the benchmark for at least 2000ms.
    double result = measureForExercise(2 * 1000);
    teardown();
    return result;
  }

  void report() {
    double score = measure();
    print("$name(RunTime): $score us.");
  }

}


void main() {
  print("Running benchmark...");
  new DiamondSquare().report();
  print("Done.");
}

/// Benchmark class required to report results.
class DiamondSquare extends BenchmarkBase {

  const DiamondSquare() : super("DiamondSquare");

  void run() {
    var w = new World();
    w.Base();
    w.Generate();
    w.Smooth();
  }

}


/***
 * Simple terrain generator inspired by:
 * http://en.wikipedia.org/wiki/Diamond-square_algorithm
 */
class World {
  int Width = 64;
  int Length = 64;
  Random rng = new Random();
  int Iterations = 6;
  List<List<int>> map_data = [];

  ///Ctor
  World() {
    Reset();
  }

  ///Creates a flat land.
  void Reset() {
    var row = <int>[];

    for (int y = 0; y < Length; y++) {
      row = <int>[];
      for (int x = 0; x < Width; x++) {
        row.add(0);
      }
      map_data.add(row);
    }
  }

  /// Starting point - random terrain.
  /// Some of the magic numbers in here should be configurable.
  void Base() {
    for (int y = 0; y < Length; y++) {
      for (int x = 0; x < Width; x++) {
        map_data[x][y] = rng.nextInt(55) + 200;
      }
    }
  }

  /// This method performs the random midpoint displacement.
  void SetCorners(int x, int y, int w, int h, [List<int> v = null]) {
    if (w < 1) return;

    if (v != null) {
      map_data[x][y] = v[0];
      map_data[x + w][y] = v[1];
      map_data[x][y + h] = v[2];
      map_data[x + w][y + h] = v[3];
    }

    int hw = (w / 2).floor();
    int hh = (h / 2).floor();
    int total = map_data[x][y] +
        map_data[x + w][y] +
        map_data[x][y + h] +
        map_data[x + w][y + h] +
        rng.nextInt(4 + h) +
        4;

    map_data[x + hw][y + hh] = (total / 4.0).floor();

    ///Subdivide
    SetCorners(x, y, hw, hh);
    SetCorners(x + hw, y, hw, hh);
    SetCorners(x, y + hh, hw, hh);
    SetCorners(x + hw, y + hh, hw, hh);
  }

  /// This is where the real terrain is formed.
  /// Some of the magic numbers in here should be configurable.
  void Generate() {
    int w = Width - 1;
    int h = Length - 1;
    SetCorners(0, 0, w, h, [155, 155, 155, 155]);
    SetCorners(rng.nextInt(5) + 14, 0, 15, 15);
  }

  /// Smooth out the terrain for a more natural look.
  void Smooth() {
    int average = 0;
    for (int yl = 1; yl < (Length - 1); yl++) {
      for (int xl = 1; xl < (Width - 1); xl++) {
        average = (1.1 *
                ((map_data[xl][yl] +
                        map_data[xl + 1][yl] +
                        map_data[xl][yl + 1] +
                        map_data[xl + 1][yl + 1]) /
                    4))
            .floor();
        map_data[xl][yl] = average;
        map_data[xl + 1][yl] = average;
        map_data[xl + 1][yl + 1] = average;
        map_data[xl][yl + 1] = average;
        map_data[xl - 1][yl] = average;
        map_data[xl - 1][yl - 1] = average;
        map_data[xl][yl - 1] = average;
      } //x loop
    } //y loop
  }
}
