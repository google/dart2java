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

# Some tests require being run from the package root.
cd $( dirname "${BASH_SOURCE[0]}" )/..

# Delete codegen expectation files to be sure that if a test fails to compile
# we don't erroneously pick up the old version.
rm -rf test/codegen_expect/
rm -rf gen/codegen_output
rm -rf gen/codegen_tests
rm -rf gen/unit_output
rm -rf gen/unit_tests

dart test/all_tests.dart || exit

# Check benchmark 'run.stdout' files. If the result changed by 15% or less,
# checkout the old version.

# this function is meant to be used as part of a pipeline.
parse_run_stdout() {
  # -n                 Don't print lines by default.
  # /[rR]un[tT]ime/    Match lines containing the word "RunTime" or "runtime".
  # s/.../.../         Extract the numeric part.
  # p                  Print the matched line.
  sed -n '/[rR]un[tT]ime/s/^.*:\s*\([0-9.]\+\).*$/\1/p'
}

for benchmark in deltablue havlak richards tracer; do
  # Check for an "old version". If we can't find it, do nothing.
  # The output format of git ls-tree is:
  #     <mode> SP <type> SP <object> TAB <file>
  # so the tr/cut commands single out the hash.
  OLD_HASH="$(git ls-tree HEAD "test/codegen_expect/$benchmark/run.stdout" |
      tr '\t' ' ' | cut -d' ' -f3)"
  if [ -n "$OLD_HASH" ]; then
    OLD_TIME="$(git cat-file blob "$OLD_HASH" | parse_run_stdout)"
    NEW_TIME="$(parse_run_stdout <"test/codegen_expect/$benchmark/run.stdout")"
    if [ -n "$OLD_TIME" -a -n "$NEW_TIME" ]; then
      AAA="$(echo "0.85 * $OLD_TIME < $NEW_TIME" | bc)"
      BBB="$(echo "1.15 * $OLD_TIME > $NEW_TIME" | bc)"
      echo "Benchmark $benchmark: old=$OLD_TIME new=$NEW_TIME A=$AAA B=$BBB"
      if [ "$AAA" = "1" -a "$BBB" = "1" ]; then
        echo "Benchmark $benchmark had no significant change; reverting."
        git checkout HEAD test/codegen_expect/$benchmark/run.stdout
      else
        echo "Benchmark $benchmark changed significantly; not reverting."
      fi
    fi
  fi
done
