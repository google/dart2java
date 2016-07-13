Dart2Java dev compiler
=========================

The Dart2Java dev compiler is a transplier from Dart to Java source. It is based
on [DDC](https://github.com/dart-lang/dev_compiler) and [Kernel
IR](https://github.com/dart-lang/kernel), and its main goal is to investigate
which features of the Dart language make it particularly hard or easy for AOT
compilation to a new language. Java source was picked as the target of
compilation, as a large part of the internal Google code is written in Java, and
it is also the language of choice for Android development. Ideally, Dart2Java
will enable interopability between Dart and Java programs, among automatic
translation of Dart programs to Java source.

# Compiling the SDK
The easy way to build the SDK is to run the `tool/build_sdk.sh` script.

Here are the full steps, for those who are interested:
    * Patch the SDK: `tool/patch_sdk.sh`
    * Compile the SDK: `dart2java --dart-sdk=gen/patched_sdk --output=gen/compiled_sdk --package-prefix=dart dart:_internal ...`
    * Compile the generated `.java` files with `javac`
    * Archive the generated `.class` files with `jar`
