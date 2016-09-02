Dart2Java dev compiler
=========================

The Dart2Java dev compiler is a transpiler from Dart to Java source. It is based
on [DDC](https://github.com/dart-lang/dev_compiler) and [Kernel
IR](https://github.com/dart-lang/kernel), and its main goal is to investigate
which features of the Dart language make it particularly hard or easy for AOT
compilation to a new language. Ideally, Dart2Java will enable interoperability
between Dart and Java programs, and automatic translation of Dart programs to
Java source.

# Compiling the SDK

The easy way to build the SDK is to run the `tool/build_sdk.sh` script.

The script executes the following steps:

 * Patch the SDK: `tool/patch_sdk.sh`
 * Compile the SDK: `dart2java --dart-sdk=gen/patched_sdk --output=gen/compiled_sdk --package-prefix=dart dart:_internal ...`
 * Compile the generated `.java` files with `javac`
 * Archive the generated `.class` files with `jar`

# Disclaimer

This is not an official Google product.
