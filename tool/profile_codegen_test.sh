#! /bin/bash
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

# Used to profile a codegen test with HPROF.
# Usage: tool/profile_codegen_test.sh test/codegen/TEST_FILE.dart OUTPUT_DIR
#   Runs the given codegen TEST_FILE with HPROF enabled, and produces
#   files like "cpu-samples.hprof.1" and "cpu-times.hprof.1" in OUTPUT_DIR.

# Switch to the root directory of dart2java.
cd "$(dirname "${BASH_SOURCE[0]}")/.."

# Simple argument validation; this should catch most bad invocations.
if [ "$#" -ne 2 -o "${1%/*}" != "test/codegen" -o ! -f "$1" -o ! -d "$2" ]; then
  echo "Usage: $0 test/codegen/<test_file>.dart <output-dir>"
  exit 1
fi

# The output directory may contain earlier results, like "cpu-times.hprof.#",
# where "#" is a positive integer. Find the largest "#" part, and add one to it.
NUM="$(\
  find "$2" -maxdepth 1 -regex '[^.]+\.[^.]+\.[0-9]+' -printf '%P\0' |\
  tr '\0\n' '\n\0' |\
  sort -t'.' -k3 -nr |\
  head -n1 |\
  tr '\0\n' '\n\0' |\
  cut -d'.' -f3\
  )"
NUM=$((NUM + 1))

# Extract TEST_NAME from the TEST_FILE.
TEST_NAME=${1#test/codegen/}
TEST_NAME=${TEST_NAME%.dart}

# Compile the test.
echo "Compiling test $TEST_NAME..."
test/codegen_test.dart --compile-only "$TEST_NAME" >/dev/null 2>&1

# Profile with cpu=times.
echo "Test compiled. Profiling with cpu=times; this may be slow."
java -cp gen/compiled_sdk:gen/codegen_output \
  "-agentlib:hprof=cpu=times,file=$2/cpu-times.hprof.$NUM" \
  "$TEST_NAME".__TopLevel 2>&1 >/dev/null 2>&1

# Profile with cpu=samples.
echo "Profiling with cpu=samples."
java -cp gen/compiled_sdk:gen/codegen_output \
  "-agentlib:hprof=cpu=samples,interval=1,depth=100,file=$2/cpu-sample.hprof.$NUM" \
  "$TEST_NAME".__TopLevel >/dev/null 2>&1

echo "Done!"
