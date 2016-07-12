#!/bin/bash
set -e

# Switch to the root directory of dart2java.
cd $( dirname "${BASH_SOURCE[0]}" )/..

mkdir -p gen/compiled_sdk

# TODO(andrewkrieger): Patch Dart files

# Compile SDK implementation
dart -c bin/dart2java.dart --build-root=tool/sdk_impl/dart \
  --output-dir=gen/compiled_sdk\
  tool/sdk_impl/dart/dart/_runtime.dart

# Copy helpers to gen directory
cp -R tool/sdk_impl/java/* gen/compiled_sdk
find gen/compiled_sdk -name "packages" -print0 | xargs -0 rm

# Compile all Java SDK files
find gen/compiled_sdk -name "*.java" -print0 | xargs -0 javac
jar cf gen/compiled_sdk.jar gen/compiled_sdk

echo "Generated compiled SDK: gen/compiled_sdk.jar"