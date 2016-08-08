import 'dart:io';
import 'dart:math';

main(List<String> argv) {
  if (argv.length < 4) {
    var name = Platform.executable;
    stderr.writeln('Usage: $name <benchmark-subdir> <benchmark-java-class>'
        ' <repetitions> <resolution>');
    return;
  }
  int repeatRun = int.parse(argv[2]);
  Directory.current = new Directory(Directory.current.path + "/" + argv[0]);

  print("pre-JIT mean,post-JIT mean,pre-JIT 2 sigma,post-JIT 2 sigma");

  int resolution = int.parse(argv[3]);
  for (int i = 0; i < resolution; ++i) {
    int innerReps = getInnerReps(i, resolution);
    int outerReps = getOuterReps(i, resolution);

    List<int> jitIters = []; // At which iteration does the JIT kick-in?
    List<num> preJitMeans = []; // What is the mean exec time pre-JIT?
    List<num> postJitMeans = []; // What is the mean exec time post-JIT?
    List<num> jitSpeedups = []; // How much is the speedup from JIT?

    stderr.writeln("resolution: $i out of $resolution");
    stderr.write("out of $repeatRun: ");
    for (int i = 0; i < repeatRun; ++i) {
      List<int> data;

      // Repeat failed measurements.
      int jitIter = 1;
      int giveUp = 100;

      {
        stderr.write("$i, ");
        ProcessResult results =
            Process.runSync('java', [argv[1], "$outerReps", "$innerReps"]);

        // Warning: assuming no errors.
        String output = results.stdout;
        List<String> lines = output.split('\n');

        // Remove data header and empty element from splitting on last new-line.
        lines = lines.sublist(1, lines.length - 1);

        data = lines.map((s) => num.parse(s.split(',')[1])).toList();

        jitIter = findJitIter(data);
      }

      if (giveUp > 0) {
        num preJitMean = mean(data.sublist(0, jitIter));
        num postJitMean = mean(data.sublist(jitIter));
        num jitSpeedup = preJitMean / postJitMean;

        jitIters.add(jitIter);
        preJitMeans.add(preJitMean);
        postJitMeans.add(postJitMean);
        jitSpeedups.add(jitSpeedup);
      }
    }
    stderr.writeln("");

    // Print means of means for meta-analysis.
    print("${mean(preJitMeans)},"
        "${mean(postJitMeans)},"
        "${2 * sqrt(variance(preJitMeans))},"
        "${2 * sqrt(variance(postJitMeans))}");

    innerReps *= 2;
  }
}

int exponentIntSeries(int start, int end, int steps, int index) {
  return (start * pow(end / start, index / (steps - 1))).round();
}

// Growing exponentially in [samples] steps from 8 to 9232.
int getInnerReps(int index, int samples) {
  return exponentIntSeries(8, 9232, samples, index);
}

// Decreasing exponentially in [samples] steps from 5000 to 80.
int getOuterReps(int index, int samples) {
  return exponentIntSeries(5000, 80, samples, index);
}

num mean(List<num> data) {
  // Simple base-case, just to avoid crashes; not math-sound.
  if (data.length < 1) return 0;
  // Warning: potentially large intermediate sum. TODO(stanm): consider overflow
  return data.fold(0, (x, y) => x + y) / data.length;
}

// (stanm): optional parameter brings time from 37s to 24s for inputs
// outerRep = 50000, innerRep = 1
num variance(List<num> data, [num m]) {
  // Simple base-case, just to avoid crashes; not math-sound.
  if (data.length < 2) return 0;
  // Warning: potentially large intermediate sum. TODO(stanm): consider overflow
  m ??= mean(data);
  return data.fold(0, (x, y) => x + (y - m) * (y - m)) / (data.length - 1);
}

num stdevDivMu(List<num> data, [num mu]) {
  mu ??= mean(data);
  num sigma = sqrt(variance(data, mu));
  return sigma / mu;
}

int findJitIter(List<int> data) {
  num minleft = 1 << 30;
  num minright = 1 << 30;
  int minansw = -1;
  for (int answ = 2; answ < data.length - 1; ++answ) {
    num left = stdevDivMu(data.sublist(0, answ));
    num right = stdevDivMu(data.sublist(answ));
    if (minleft + minright > left + right) {
      minleft = left;
      minright = right;
      minansw = answ;
    }
  }
  return minansw;
}
