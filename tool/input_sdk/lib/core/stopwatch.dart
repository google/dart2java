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

part of dart.core;

/**
 * A simple stopwatch interface to measure elapsed time.
 */
class Stopwatch {
  /**
   * Frequency of the elapsed counter in Hz.
   */
  int get frequency => _frequency;

  // The _start and _stop fields capture the time when [start] and [stop]
  // are called respectively.
  // If _start is 0, then the [Stopwatch] has not been started yet.
  // If _stop is 0, then the [Stopwatch] has not been stopped yet, or is
  // running.
  int _start;
  int _stop;

  /**
   * Creates a [Stopwatch] in stopped state with a zero elapsed count.
   *
   * The following example shows how to start a [Stopwatch]
   * immediately after allocation.
   *
   *     Stopwatch stopwatch = new Stopwatch()..start();
   */
  Stopwatch() {
    _initTicker();
  }

  /**
   * Starts the [Stopwatch].
   *
   * The [elapsed] count is increasing monotonically. If the [Stopwatch] has
   * been stopped, then calling start again restarts it without resetting the
   * [elapsed] count.
   *
   * If the [Stopwatch] is currently running, then calling start does nothing.
   */
  void start() {
    if (isRunning) return;
    if (_start == 0) {
      // This stopwatch has never been started.
      _start = _now();
    } else {
      // Restart this stopwatch. Prepend the elapsed time to the current
      // start time.
      _start = _now() - (_stop - _start);
      _stop = 0;
    }
  }

  /**
   * Stops the [Stopwatch].
   *
   * The [elapsedTicks] count stops increasing after this call. If the
   * [Stopwatch] is currently not running, then calling this method has no
   * effect.
   */
  void stop() {
    if (!isRunning) return;
    _stop = _now();
  }

  /**
   * Resets the [elapsed] count to zero.
   *
   * This method does not stop or start the [Stopwatch].
   */
  void reset() {
    if (_start == 0) return;
    // If [_start] is not 0, then the stopwatch had already been started. It
    // may running right now.
    _start = _now();
    if (_stop != 0) {
      // The watch is not running. So simply set the [_stop] to [_start] thus
      // having an elapsed time of 0.
      _stop = _start;
    }
  }

  /**
   * Returns the elapsed number of clock ticks since calling [start] while the
   * [Stopwatch] is running.
   *
   * Returns the elapsed number of clock ticks between calling [start] and
   * calling [stop].
   *
   * Returns 0 if the [Stopwatch] has never been started.
   *
   * The elapsed number of clock ticks increases by [frequency] every second.
   */
  int get elapsedTicks {
    if (_start == 0) {
      return 0;
    }
    return (_stop == 0) ? (_now() - _start) : (_stop - _start);
  }

  /**
   * Returns the [elapsedTicks] counter converted to a [Duration].
   */
  // TODO(springerm:) get elapsed

  /**
   * Returns the [elapsedTicks] counter converted to microseconds.
   */
  int get elapsedMicroseconds {
    return ((elapsedTicks / frequency) * 1000000).round();
  }

  /**
   * Returns the [elapsedTicks] counter converted to milliseconds.
   */
  int get elapsedMilliseconds {
    return ((elapsedTicks / frequency) * 1000).round();
  }


  /**
   * Returns wether the [StopWatch] is currently running.
   */
  bool get isRunning => _start != 0 && _stop == 0;

  /**
   * Cached frequency of the system. Must be initialized in [_initTicker];
   */
  static int _frequency;

  /**
   * Initializes the time-measuring system. *Must* initialize the [_frequency]
   * variable.
   */
  external static void _initTicker();
  external static int _now();
}
