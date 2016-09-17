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

fail() {
  printf "\n\n\e[31m*** Presubmit failed. ***\e[0m\n"
  exit 1
}

cd "$(dirname "${BASH_SOURCE[0]}")/.."
tool/format.sh || fail
tool/analyze.sh || fail
tool/build_sdk.sh || fail
tool/test.sh || fail

printf "\n\n\e[32m*** Presubmit finished successfully! ***\e[0m\n"
