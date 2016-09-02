// Copyright 2016, the Dart project authors.
// 
// Licensed under the Apache License, Version 2.0 (the "License");
// you may not use this file except in compliance with the License.
// You may obtain a copy of the License at
// 
//     http://www.apache.org/licenses/LICENSE-2.0
// 
// Unless required by applicable law or agreed to in writing, software
// distributed under the License is distributed on an "AS IS" BASIS,
// WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// See the License for the specific language governing permissions and
// limitations under the License.

part of dart.core;

/**
 * The reserved words [:true:] and [:false:] denote objects that are the only
 * instances of this class.
 *
 * It is a compile-time error for a class to attempt to extend or implement
 * bool.
 */
class bool {
  /**
   * Returns the boolean value of the environment declaration [name].
   *
   * The boolean value of the declaration is `true` if the declared value is
   * the string `"true"`, and `false` if the value is `"false"`.
   *
   * In all other cases, including when there is no declaration for `name`,
   * the result is the [defaultValue].
   *
   * The result is the same as would be returned by:
   *
   *     (const String.fromEnvironment(name) == "true")
   *         ? true
   *         : (const String.fromEnvironment(name) == "false")
   *             ? false
   *             : defaultValue
   *
   * Example:
   *
   *     const loggingFlag = const bool.fromEnvironment("logging");
   *
   * If you want to use a different truth-string than `"true"`, you can use the
   * [String.fromEnvironment] constructor directly:
   *
   *     const isLoggingOn = (const String.fromEnvironment("logging") == "on");
   */
  // TODO(springerm): Implementation missing
  // external const factory bool.fromEnvironment(String name,
  //                                             {bool defaultValue: false});

  /**
   * Returns [:"true":] if the receiver is [:true:], or [:"false":] if the
   * receiver is [:false:].
   */
  String toString() {
    return this ? "true" : "false";
  }
}
