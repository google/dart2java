import 'dart:math';

// Game of Life Implementation taken from:
// https://github.com/daftspaniel/dart-conwaysgameoflife
// Copyright (c) Davy Mitchell


// Code slightly modifed to make it run in dart2java

// https://github.com/dart-lang/ton80/blob/master/lib/src/common/dart/BenchmarkBase.dart
// Copyright 2011 Google Inc. All Rights Reserved.
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
    measureForWarumup(1000);
    // Run the benchmark for at least 2000ms.
    double result = measureForExercise(10000);
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
  new GameOfLife().report();
  print("Done.");
}

/// Benchmark class required to report results.
class GameOfLife extends BenchmarkBase {

  const GameOfLife() : super("GameOfLife");

  void run() {
    gameOfLife();
  }
}

void gameOfLife() {
  var iterations = 10;

  var ce;
  var c2d;
  Random rng = new Random(1337);

  var redCells = new Culture();
  var greenCells = new Culture();
  var blueCells = new Culture();
  var yellowCells = new Culture();
  var cultures = [redCells, greenCells, blueCells, yellowCells];

  int cells = 699 + rng.nextInt(99);

  redCells.initPopulation(cells);
  greenCells.initPopulation(cells ~/ 1.1);
  blueCells.initPopulation(cells ~/ 1.2);
  yellowCells.initPopulation(cells ~/ 1.3);

  for (int i = 0; i < iterations; i++) {
    for (var culture in cultures) {
      culture.update();
    }
  }
}

// The World in which the cells live.
class Culture {
  Map<String, Cell> cellDish = new Map<String, Cell>();
  int width = 10;
  var rng = new Random();

  Culture() {
    //growth
    for (int x = 0; x < width; x++) {
      for (int y = 0; y < width; y++) {
        cellDish["$x-$y"] = new Cell(x, y, cellDish);
      }
    }
  }

  // Set population - ignoring repeats.
  void initPopulation(int Pop) {
    for (int i = 0; i < Pop; i++) {
      add(rng.nextInt(width), rng.nextInt(width));
    }
  }

  void add(x, y) {
    Cell c = cellDish["$x-$y"];
    c.state = 1;
  }

  void update() {
    // (springerm): Replaced forEach methods
    for (var k in cellDish.keys) {
      cellDish[k].update();
    }
    for (var k in cellDish.keys) {
      cellDish[k].commit();
    }
  }
}

// The Cellular Level Object.
class Cell {
  Cell(this.x, this.y, this.Environment);
  int x = 0;
  int y = 0;

  // (springerm): Added types here
  Map<String, Cell> Environment;
  int state = 0;
  int nextState = -1;
  int age = 0;
  int updates = 0;

  void update() {
    int n = getNeighbours();
    updates++;
    if ((state == 1 && n == 2) || n == 3) {
      nextState = 1;
      age++;
    } else {
      nextState = 0;
      age = 0;
    }
  }

  void commit() {
    state = nextState;
  }

  int getNeighbours() {
    int n = 0;

    if (isNeighbourPopulated(x - 1, y - 1)) n++;
    if (isNeighbourPopulated(x, y - 1)) n++;
    if (isNeighbourPopulated(x + 1, y - 1)) n++;

    if (isNeighbourPopulated(x - 1, y)) n++;
    if (isNeighbourPopulated(x + 1, y)) n++;

    if (isNeighbourPopulated(x - 1, y + 1)) n++;
    if (isNeighbourPopulated(x, y + 1)) n++;
    if (isNeighbourPopulated(x + 1, y + 1)) n++;

    //print("$n");
    return n;
  }

  bool isNeighbourPopulated(int nx, int ny) {
    // (springerm): Check for containsKey explicitly since semantics of 
    // Map is different in dart2java
    if (!Environment.containsKey("$nx-$ny")) {
      return false;
    }

    var t = Environment["$nx-$ny"];
    
    if (t.state == 1) {
      return true;
    } else {
      return false;
    }
  }
}
