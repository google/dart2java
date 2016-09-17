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

# Switch to the root directory of dev_compiler
cd $( dirname "${BASH_SOURCE[0]}" )/..

# Run formatter in rewrite mode on all files that are part of the project.
# This checks that all files are committed first to git, so no state is lost.
# The formatter ignores:
#   * local files that have never been added to git,
#   * subdirectories of test/ and tool/, unless explicitly added. Those dirs
#     contain a lot of generated or external source we should not reformat.
files="$(git ls-files 'bin/*.dart' 'lib/*.dart')" 
[[ -n "$(git status -s $files)" ]] \
  && { echo "Did not run the formatter, please commit edited files first."; exit 1; } \
  || { echo "Running dart formatter" ; pub run dart_style:format -w $files; }
