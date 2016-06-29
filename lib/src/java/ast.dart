// Copyright (c) 2016, the Dart project authors.  Please see the AUTHORS file
// for details. All rights reserved. Use of this source code is governed by a
// BSD-style license that can be found in the LICENSE file.

/// A simple AST designed for generating a single Java source file. It is
/// assumed that errors are caught before generating this AST, so there are no
/// designated "error" or "invalid" nodes.

import 'visitor.dart';

/// Java access specifier
enum Access {
  Public,
  Protected,
  Private,

  /// Package-private or default access. In Java code, this is written by
  /// omitting a specifier (e.g. `static void foo() {}` instead of
  /// `public static void foo() {}`).
  Package,
}

/// Convert access specifier to string.
///
/// Since this will often be inserted into a list of modifiers (along with
/// keywords like `abstract` or `static`, the default behavior is to add a
/// single space at the end (like "public "), except for the value
/// `Access.Package` which is always converted to the empty string. To override
/// this behavior (so that the return value never ends with a space, e.g.
/// "public"), pass `trailingSpace: false`.
String accessToString(Access access, {bool trailingSpace: true}) {
  switch (access) {
    case Access.Public:
      return trailingSpace ? "public " : "public";
    case Access.Protected:
      return trailingSpace ? "protected " : "protected";
    case Access.Private:
      return trailingSpace ? "private " : "private";
    case Access.Package:
      return "";
    default:
      throw new Exception("Unrecognized Access value: $access");
  }
}

/// The root class for nodes in the Java AST.
abstract class Node {
  Node parent;

  /*=R*/ accept/*<R>*/(Visitor/*<R>*/ v);
  Iterable/*<R>*/ visitChildren/*<R>*/(Visitor/*<R>*/ v);

  /// [toString] is only meant for debugging purposes. For Java code generation,
  /// use an AST visitor.
  String toString() => '[$runtimeType]';
}

/// A Java class.
///
/// Each AST should be rooted at a Java class (since we produce exactly one
/// class per source file).  Of course, Java also allows for nested and local
/// classes, so this node may appear deeper within the tree as well.
class Class extends Node {
  /// Java package. May not be null, and must contain at least one identifier.
  String package;

  /// Class name; may be null for anonymous classes.
  String name;

  Access access;

  Class(this.package, this.name, this.access);

  /*=R*/ accept/*<R>*/(Visitor/*<R>*/ v) => v.visitClass(this);

  /*=Iterable<R>*/ visitChildren/*<R>*/(Visitor/*<R>*/ v) {
    return new Iterable<R>.empty();
  }

  String toString() => '${accessToString(access)} class $name';
}
