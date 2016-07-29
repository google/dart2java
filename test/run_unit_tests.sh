#!/bin/bash

# This script runs all unit tests which are part of the test suite

# Switch to the root directory of dart2java.
cd $( dirname "${BASH_SOURCE[0]}" )/..

# Compile SDK
tool/build_sdk.sh

echo "Compiling scenarios..."
dart -c bin/dart2java.dart \
  --dart-sdk=gen/patched_sdk \
  --output-dir=gen/compiled_sdk \
  --build-root=test/unit \
  test/unit/dart/test/scenario/scenario_basic.dart
find gen/compiled_sdk/dart/test -name "*.java" -print0 | xargs -0 javac
echo "Done"

# Dependency for JUnit
JAVA_DEPS="third_party/hamcrest-1.3/hamcrest-core-1.3.jar:third_party/junit-4.12/junit-4.12.jar"

echo "Compiling JUnit test cases..."
cp -R test/unit/java/test gen/compiled_sdk
find gen/compiled_sdk/test -name "*.java" -print0 | 
	xargs -0 javac -cp gen/compiled_sdk:$JAVA_DEPS
echo "Done"

echo "Running JUnit test cases..."
java -cp gen/compiled_sdk:$JAVA_DEPS org.junit.runner.JUnitCore test.TestSuite

