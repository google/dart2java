// Copyright 2009 Google Inc. All Rights Reserved.

package com.google.pipeline.flume.fj.examples.codelab;

import com.google.common.base.Pair;
import com.google.common.flags.Flag;
import com.google.common.flags.FlagSpec;
import com.google.common.flags.Flags;
import com.google.pipeline.flume.fj.FJ;
import com.google.pipeline.flume.fj.FJ.DoFn;
import com.google.pipeline.flume.fj.FJ.EmitFn;
import com.google.pipeline.flume.fj.FJ.MapFn;
import com.google.pipeline.flume.fj.FlumeJava;
import com.google.pipeline.flume.fj.PCollection;
import com.google.pipeline.flume.fj.PTable;
import com.google.pipeline.flume.fj.TextIO;

/**
 * An example FlumeJava program that computes a frequency count of words
 * in Shakespeare plays, formats the results, and prints them to a file.
 *
 * <P>
 * This is a simplified version of
 * com.google.pipeline.flume.fj.examples.WordCount
 * for use in the FlumeJava codelab.
 *
 * @author fjp@google.com (Frances Perry)
 */
public class BardCount {

  @FlagSpec(help = "Which Shakespeare plays to read")
  static final Flag<String> plays = Flag.value("romeoandjuliet");

  // Do not instantiate.
  private BardCount() { }

  static final String playLocation = "/cns/pa-d/home/flume/text/shakespeare/";

  public static void main(String[] args) {
    // Process the command-line flags.
    args = Flags.parseAndReturnLeftovers(args);

    try {
      // Initialize FlumeJava.
      FlumeJava.init();

      // Read in the specified Shakespeare plays line by line.
      PCollection<String> lines =
          FJ.read(TextIO.source(playLocation + plays.get()));

      PTable<String, Long> wordCounts = bardCount(lines);

      // Construct formatted output by mapping each Pair(word, count) to
      // a String "word: count".
      PCollection<String> formattedOutput = wordCounts.parallelDo(
          "FormatOutput",
          new MapFn<Pair<String, Long>, String>() {
            public String map(Pair<String, Long> pair) {
              return pair.getFirst() + ": " + pair.getSecond();
            }
          });

      // Write the results to the output file.
      formattedOutput.writeTo(TextIO.sink("ShakespeareWords.txt"));

      // Finish using FlumeJava.
      FlumeJava.done();

    } finally {
      // Invoke the optional clean-up in the finally block.
      FlumeJava.cleanUp();
    }
  }

  /**
   * Given a PCollection of lines of text, count the number of times each
   * word occurs and return a PTable mapping unique words to counts.
   */
  static PTable<String, Long> bardCount(PCollection<String> lines) {
    // Process each line and extract the individual words.
    PCollection<String> words = lines.parallelDo(
        "ExtractWords",
        new DoFn<String, String>() {
          public void process(String line, EmitFn<String> emitFn) {
            String[] words = line.split("[^a-zA-Z']+");
            for (String word : words) {
              if (!word.isEmpty()) {
                emitFn.emit(word.toLowerCase());
              }
            }
          }
        });

    // Compute a frequency count of each word.
    PTable<String, Long> wordCounts = words.count("CountWords");

    return wordCounts;
  }
}
