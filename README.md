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
