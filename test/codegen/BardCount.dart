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

// package com.google.pipeline.flume.fj.examples.codelab;

// These classes don't generate anything; they are just used for type
// resolution.

// import com.google.common.base.Pair;
class Pair<T, U> {
  external T getFirst();
  external U getSecond();
}

// import com.google.common.flags.Flag;
class Flag<T> {
  // TODO(stanm): external constructor?
  external Flag.value(T);
  external T get();
}

// import com.google.common.flags.FlagSpec;
// TODO(stanm): this is an interesting one... we have no way of generating
// **Java** annotations from Dart code.
// @FlagSpec(help = "Which Shakespeare plays to read")

// import com.google.common.flags.Flags;
class Flags {
  external static List<String> parseAndReturnLeftovers(List<String> args);
}

// import com.google.pipeline.flume.fj.FJ;
class FJ {
  // TODO(stanm) todo_label A, ref B, ref C: PSource is not explicitly used in
  // the Java code, but we need it to define this interface. Also, in B and C,
  // TextSource<T> is used, which implements the PSource<T, CT> interface.
  external static PCollection/*<T>*/ read/*<T, CT extends PCollection<T>>*/(
      PSource/*<T, CT>*/ source);
}

class PSource<T, CT> {}

// import com.google.pipeline.flume.fj.FJ.DoFn;
class DoFn<T, O> {
  // TODO(stanm): this is a virtual function.
  external void process(String line, EmitFn<String> emitFn);
}

// import com.google.pipeline.flume.fj.FJ.EmitFn;
class EmitFn<O> {
  // TODO(stanm): this is a virtual function.
  external void emit(O outputElem);
}

// import com.google.pipeline.flume.fj.FJ.MapFn;
// TODO(stanm): Java classes extending Java classes.
class MapFn<T, O> extends DoFn<T, O> {
  external O map(T elem);
}

// import com.google.pipeline.flume.fj.FlumeJava;
class FlumeJava {
  external static void init();
  external static void done();
  external static void cleanUp();
}

// import com.google.pipeline.flume.fj.PCollection;
class PCollection<T> {
  // TODO(stanm): Although the Java parallelDo takes a DoFn as an argument, the
  // pattern is to instantiate an annonymous class that implements [an interface
  // that extends DoFn] and overrides one of it's methods.
  //
  // For this reason, the actual Dart interface takes the function that is to be
  // overridden and the call site should include an annotation specifying which
  // interface in the DoFn is being used and which of its methods is being
  // overridden.
  //
  // Annotation: <O> PCollection<O> parallelDo(String stageName, DoFn<? super T, O> doFn)
  external PCollection/*<O>*/ parallelDo/*<O>*/(
      String stageName, Function method);
  external PTable<T, Long> count(String stageName);
  external dynamic /* =R*/ writeTo/*<R extends PValue>*/(PSink<T, R> sink);
}

// TODO(stanm): ideally, we wouldn't need to define this and use int instead.
class Long {}

class PValue {}

class PSink<T, CT> {}

// import com.google.pipeline.flume.fj.PTable;
class PTable<K, V> extends PCollection<Pair<K, V>> {}

// import com.google.pipeline.flume.fj.TextIO;
class TextIO {
  // TODO(stanm) todo_label B, ref A, ref C: TextSource is not explicitly used
  // in the Java code, but we need it to define this interface.
  external static TextSource<String> source(String filepattern);
  external static TextSink<String> sink(String filepattern);
}

class TextSource<T> extends PSource<T, TextSource<T>> {}

class TextSink<T> extends PSink<T, TextSink<T>> {}

// @FlagSpec(help = "Which Shakespeare plays to read")
final Flag<String> plays = new Flag.value("romeoandjuliet");

final String playLocation = "/cns/pa-d/home/flume/text/shakespeare/";

void main(List<String> args) {
  // Process the command-line flags.
  args = Flags.parseAndReturnLeftovers(args);

  try {
    // Initialize FlumeJava.
    FlumeJava.init();

    // Read in the specified Shakespeare plays line by line.
    PCollection<String> lines =
        // TODO(stanm) todo_label C, ref A, ref B: TextIO.source returns a type
        // different than what FJ.read accepts (but implements it instead). This
        // needs a solution.
        // TODO(stanm): resolve analyzer warning about second generic argument.
        FJ.read(TextIO.source(playLocation + plays.get()));

    PTable<String, Long> wordCounts = bardCount(lines);

    // Construct formatted output by mapping each Pair(word, count) to
    // a String "word: count".
    PCollection<String> formattedOutput = wordCounts.parallelDo(
        "FormatOutput",
        // TODO(stanm): annotate:
        // Annotation: String MapFn<Pair<String, Long>, String>().map(Pair<String, Long>)
        (pair) => "${pair.getFirst()}: ${pair.getSecond()}");

    // Write the results to the output file.

    // TODO(stanm): resolve analyzer warning about second generic argument.
    formattedOutput.writeTo(TextIO.sink("ShakespeareWords.txt"));

    // Finish using FlumeJava.
    FlumeJava.done();
  } finally {
    // Invoke the optional clean-up in the finally block.
    FlumeJava.cleanUp();
  }
}

PTable<String, Long> bardCount(PCollection<String> lines) {
  // Process each line and extract the individual words.
  PCollection<String> words = lines.parallelDo("ExtractWords",
      // TODO(stanm): annotate? annotate DoFn as a "function object"?
      // void DoFn<String, String>().process(String line, EmitFn<String> emitFn)
      (line, emitFn) {
    // TODO(stanm): How do we map regex?
    // TODO(stanm): we should inform analyzer using our annotations which would
    // eliminate warning caused from it not knowing the type of line.
    List<String> words = line.split(new RegExp(r"[^a-zA-Z']+"));
    words.forEach((word) {
      if (word.isNotEmpty) {
        emitFn.emit(word.toLowerCase());
      }
    });
  });

  // Compute a frequency count of each word.
  PTable<String, Long> wordCounts = words.count("CountWords");

  return wordCounts;
}
