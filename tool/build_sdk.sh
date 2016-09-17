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

set -e

DART_SDK_LIBS="dart:_internal dart:core dart:async dart:math"

# Switch to the root directory of dart2java.
cd $( dirname "${BASH_SOURCE[0]}" )/..

rm -rf gen/compiled_sdk
mkdir -p gen/compiled_sdk

tool/patch_sdk.sh

# Clean the gen directory
rm -rf gen/compiled_sdk

# Compile SDK implementation
dart -c bin/dart2java.dart \
  --dart-sdk=gen/patched_sdk \
  --output-dir=gen/compiled_sdk \
  $DART_SDK_LIBS

# Copy helpers to gen directory
cp -R tool/sdk_impl/java/dart gen/compiled_sdk

# Remove any `packages` symlinks created by pub
find gen/compiled_sdk -name "packages" -delete

# Compile all Java SDK files
find gen/compiled_sdk -name "*.java" -print0 | xargs -0 javac

# Generate the jar file
jar cf gen/compiled_sdk.jar -C gen/compiled_sdk dart

echo "Generated compiled SDK: gen/compiled_sdk.jar"
