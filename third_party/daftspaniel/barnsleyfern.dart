// https://github.com/daftspaniel/dartbarnsleyfern
/*
* Copyright (c) 2013, Davy Mitchell
* All rights reserved.
* 
* Redistribution and use in source and binary forms, with or without modification,
* are permitted provided that the following conditions are met:
* 
*   Redistributions of source code must retain the above copyright notice, this
*   list of conditions and the following disclaimer.
* 
*   Redistributions in binary form must reproduce the above copyright notice, this
*   list of conditions and the following disclaimer in the documentation and/or
*   other materials provided with the distribution.
* 
* THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND
* ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED
* WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
* DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE LIABLE FOR
* ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES
* (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES;
* LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON
* ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
* (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
* SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
*/

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
  new Fractal().report();
  print("Done.");
}

/// Benchmark class required to report results.
class Fractal extends BenchmarkBase {

  const Fractal() : super("Barnsley Fern Fractal");

  void run() {
    drawBarnsleyFern();
  }

  int drawBarnsleyFern() {
    int checksum = 0;

    // CanvasElement surface = query("#surface");
    // CanvasRenderingContext2D crc = surface.getContext("2d");
    double x = 0.0;
    double y = 0.0;
    double nextx = 0.0;
    double nexty = 0.0;  
    double plotDecider = 0.0;
    Random rng = new Random(1337);
    
    x = rng.nextDouble();
    y = rng.nextDouble();
    // crc.fillStyle = "#000000";
    // crc.fillRect(0, 0, 999, 999);
    for (int i=0;i<50000;i++){
      
      plotDecider = rng.nextDouble();
      
      if (plotDecider<0.01)
      {
        x = 0.0;
        y = 0.16 * y;
      }
      else if (plotDecider < 0.86)
      {
        nextx = (0.85 * x) + (0.04 * y);
        nexty = (0.04 * x) + (0.85 * y) + 1.6;
        x = nextx;
        y = nexty;
      }
      else if (plotDecider < 0.92) {
        nextx = (0.2 * x) - (0.26 * y);
        nexty = (0.23 * x) + (0.22 * y) + 1.6;
        x = nextx;
        y = nexty;
      }
      else{
        nextx = (-0.15 * x) + (0.28 * y);
        nexty = (0.26 * x) + (0.24 * y) + 0.44;
        x = nextx;
        y = nexty;
                  
      }
      
      int col = 100 + rng.nextInt(143);
      // crc.fillStyle = "rgb(0,$col,00)";
      checksum += (100 + (x*50).toInt() + 500 - (y*40).toInt()) % 9971;
    }

    return checksum;
  }
}
