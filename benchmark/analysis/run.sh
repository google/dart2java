#!/bin/bash
# Copyright 2016, the Dart project authors.
#
# Licensed under the Apache License, Version 2.0 (the "License");
# you may not use this file except in compliance with the License.
# You may obtain a copy of the License at
#
#     http://www.apache.org/licenses/LICENSE-2.0
#
# Unless required by applicable law or agreed to in writing, software
# distributed under the License is distributed on an "AS IS" BASIS,
# WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
# See the License for the specific language governing permissions and
# limitations under the License.

# Rule of thumb: Reps=100 Res=120 takes about 8 hours on a Goobuntu machine.
# Increasing them increases runtime roughly linearly (time grows a bit slower).
REPETITIONS=50
RESOLUTION=240
LOG_FILE="results/log.txt"
cd $(dirname "$0")

if [ -d "results" ] ; then
  X=$RANDOM
  mv results results_$X
  echo "#######"
  echo "WARNING: moved old results dir to results_$X"
  echo "#######"
fi
mkdir results

echo ""
echo "Running Java invoke performance benchmark"
echo ""
echo "Repetitions: $REPETITIONS; Resolution: $RESOLUTION" | tee -a $LOG_FILE

echo "Machine: `hostname`" >> $LOG_FILE
echo "Processor: `cat /proc/cpuinfo | grep 'model name' | head -1`" >> $LOG_FILE
echo "Memory: `cat /proc/meminfo | grep 'MemTotal'`" >> $LOG_FILE
java -version 2>> $LOG_FILE

echo "[`date`] Start" >> $LOG_FILE

DIR=../java-invokes/out/production/java-invokes/

echo "invoke static (1/10)"
echo "[`date`] Measuring invokestatic... (warm-up run)" >> $LOG_FILE
dart measure.dart $DIR InvokeStatic $REPETITIONS $RESOLUTION > results/is.csv

echo "[`date`] Measuring invokeinterface type 1a..." >> $LOG_FILE
echo "invoke interface 1a (2/10)"
dart measure.dart $DIR InvokeInterface1A $REPETITIONS $RESOLUTION > results/ii1a.csv
echo "[`date`] Measuring invokeinterface type 1b..." >> $LOG_FILE
echo "invoke interface 1b (3/10)"
dart measure.dart $DIR InvokeInterface1B $REPETITIONS $RESOLUTION > results/ii1b.csv
echo "[`date`] Measuring invokeinterface type 1c..." >> $LOG_FILE
echo "invoke interface 1c (4/10)"
dart measure.dart $DIR InvokeInterface1C $REPETITIONS $RESOLUTION > results/ii1c.csv
echo "[`date`] Measuring invokeinterface type 2..." >> $LOG_FILE
echo "invoke interface 2 (5/10)"
dart measure.dart $DIR InvokeInterface2 $REPETITIONS $RESOLUTION > results/ii2.csv

echo "[`date`] Measuring invokeinterface type 1a..." >> $LOG_FILE
echo "invoke virtual 1a (6/10)"
dart measure.dart $DIR InvokeVirtual1A $REPETITIONS $RESOLUTION > results/iv1a.csv
echo "[`date`] Measuring invokeinterface type 1b..." >> $LOG_FILE
echo "invoke virtual 1b (7/10)"
dart measure.dart $DIR InvokeVirtual1B $REPETITIONS $RESOLUTION > results/iv1b.csv
echo "[`date`] Measuring invokeinterface type 1c..." >> $LOG_FILE
echo "invoke virtual 1c (8/10)"
dart measure.dart $DIR InvokeVirtual1C $REPETITIONS $RESOLUTION > results/iv1c.csv
echo "[`date`] Measuring invokeinterface type 2..." >> $LOG_FILE
echo "invoke virtual 2 (9/10)"
dart measure.dart $DIR InvokeVirtual2 $REPETITIONS $RESOLUTION > results/iv2.csv

echo "[`date`] Measuring invokestatic..." >> $LOG_FILE
echo "invoke static (10/10)"
dart measure.dart $DIR InvokeStatic $REPETITIONS $RESOLUTION > results/is.csv

echo "[`date`] End" >> $LOG_FILE

gnuplot plot.gnuplot

echo "Log in $LOG_FILE"
echo "Raw data in results/*.csv" | tee -a $LOG_FILE
echo "Performance plots in results/*.png" | tee -a $LOG_FILE
