#!/bin/bash
cd $(dirname "$0")

DIR=../java-invokes/out/production/java-invokes/

echo "invoke static (1/10)"
time dart measure.dart $DIR InvokeStatic > is.csv

echo "invoke interface 1a (2/10)"
time dart measure.dart $DIR InvokeInterface1A > ii1a.csv
echo "invoke interface 1b (3/10)"
time dart measure.dart $DIR InvokeInterface1B > ii1b.csv
echo "invoke interface 1c (4/10)"
time dart measure.dart $DIR InvokeInterface1C > ii1c.csv
echo "invoke interface 2 (5/10)"
time dart measure.dart $DIR InvokeInterface2 > ii2.csv

echo "invoke virtual 1a (6/10)"
time dart measure.dart $DIR InvokeVirtual1A > iv1a.csv
echo "invoke virtual 1b (7/10)"
time dart measure.dart $DIR InvokeVirtual1B > iv1b.csv
echo "invoke virtual 1c (8/10)"
time dart measure.dart $DIR InvokeVirtual1C > iv1c.csv
echo "invoke virtual 2 (9/10)"
time dart measure.dart $DIR InvokeVirtual2 > iv2.csv

echo "invoke static (10/10)"
time dart measure.dart $DIR InvokeStatic > is.csv
