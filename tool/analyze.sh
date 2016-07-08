#!/bin/bash
set -e

# Switch to the root directory of dart2java.
cd $( dirname "${BASH_SOURCE[0]}" )/..

function fail {
  echo -e "[31mAnalyzer found problems[0m"
  return 1
}

echo "Running dartanalyzer to check for errors/warnings/hints..."
dartanalyzer --strong --fatal-warnings --package-warnings bin/dart2java.dart \
    | grep -v "\[info\] TODO" | (! grep $PWD) || fail
