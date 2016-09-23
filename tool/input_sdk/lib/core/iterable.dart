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

abstract class Iterable<E> {
  /**
   * Returns a new `Iterator` that allows iterating the elements of this
   * `Iterable`.
   *
   * Iterable classes may specify the iteration order of their elements
   * (for example [List] always iterate in index order),
   * or they may leave it unspecified (for example a hash-based [Set]
   * may iterate in any order).
   *
   * Each time `iterator` is read, it returns a new iterator,
   * which can be used to iterate through all the elements again.
   * The iterators of the same iterable can be stepped through independently,
   * but should return the same elements in the same order,
   * as long as the underlying collection isn't changed.
   *
   * Modifying the collection may cause new iterators to produce
   * different elements, and may change the order of existing elements.
   * A [List] specifies its iteration order precisely,
   * so modifying the list changes the iteration order predictably.
   * A hash-based [Set] may change its iteration order completely
   * when adding a new element to the set.
   *
   * Modifying the underlying collection after creating the new iterator
   * may cause an error the next time [Iterator.moveNext] is called
   * on that iterator.
   * Any *modifiable* iterable class should specify which operations will
   * break iteration.
   */
  Iterator<E> get iterator;

  bool contains(Object element) {
    for (E e in this) {
      if (e == element) return true;
    }
    return false;
  }

  /**
   * Returns `true` if there are no elements in this collection.
   *
   * May be computed by checking if `iterator.moveNext()` returns `false`.
   */
  bool get isEmpty => !iterator.moveNext();

  /**
   * Returns true if there is at least one element in this collection.
   *
   * May be computed by checking if `iterator.moveNext()` returns `true`.
   */
  bool get isNotEmpty => !isEmpty;
}