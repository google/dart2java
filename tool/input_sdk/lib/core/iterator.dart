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
 * An interface for getting items, one at a time, from an object.
 *
 * The for-in construct transparently uses `Iterator` to test for the end
 * of the iteration, and to get each item (or _element_).
 *
 * If the object iterated over is changed during the iteration, the
 * behavior is unspecified.
 *
 * The `Iterator` is initially positioned before the first element.
 * Before accessing the first element the iterator must thus be advanced using
 * [moveNext] to point to the first element.
 * If no element is left, then [moveNext] returns false, [current]
 * returns `null`, and all further calls to [moveNext] will also return false.
 *
 * A typical usage of an Iterator looks as follows:
 *
 *     var it = obj.iterator;
 *     while (it.moveNext()) {
 *       use(it.current);
 *     }
 *
 * **See also:**
 * [Iteration](http://www.dartlang.org/docs/dart-up-and-running/contents/ch03.html#iteration)
 * in the [library tour](http://www.dartlang.org/docs/dart-up-and-running/contents/ch03.html)
 */
abstract class Iterator<E> {
  /**
   * Moves to the next element.
   *
   * Returns true if [current] contains the next element.
   * Returns false if no elements are left.
   *
   * It is safe to invoke [moveNext] even when the iterator is already
   * positioned after the last element.
   * In this case [moveNext] returns false again and has no effect.
   *
   * A call to `moveNext` may throw if iteration has been broken by
   * changing the underlying collection.
   */
  bool moveNext();

  /**
   * Returns the current element.
   *
   * Returns `null` if the iterator has not yet been moved to the first
   * element, or if the iterator has been moved past the last element of the
   * [Iterable].
   *
   * The `current` getter should keep its value until the next call to
   * [moveNext], even if an underlying collection changes.
   * After a successful call to `moveNext`, the user doesn't need to cache
   * the current value, but can keep reading it from the iterator.
   */
  E get current;
}
