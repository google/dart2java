#!/bin/bash
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
