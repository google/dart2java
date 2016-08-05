#!/bin/bash
cd $(dirname "$0")
rm -rf out
cd src
javac *.java
cd ..
# Simulate IntelliJ's output structure.
OUT_DIR=out/production/java-invokes
mkdir -p $OUT_DIR
mv src/*.class $OUT_DIR
