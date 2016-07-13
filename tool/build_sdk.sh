#!/bin/bash
set -e

DART_SDK_LIBS="dart:_internal dart:core"

# Switch to the root directory of dart2java.
cd $( dirname "${BASH_SOURCE[0]}" )/..

mkdir -p gen/compiled_sdk

tool/patch_sdk.sh

# Compile SDK implementation
dart -c bin/dart2java.dart \
  --dart-sdk=gen/patched_sdk \
  --output-dir=gen/compiled_sdk \
  --package-prefix=dart \
  $DART_SDK_LIBS

# Copy helpers to gen directory
cp -R tool/sdk_impl/java/* gen/compiled_sdk
find gen/compiled_sdk -name "packages" -delete

# Compile all Java SDK files
find gen/compiled_sdk -name "*.java" -print0 | xargs -0 javac
jar cf gen/compiled_sdk.jar gen/compiled_sdk

echo "Generated compiled SDK: gen/compiled_sdk.jar"
