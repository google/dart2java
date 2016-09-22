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

package dart._runtime.base;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.NoSuchElementException;

import dart._runtime.helpers.ConstructorHelper;
import dart._runtime.types.simple.Type;

/**
* A specialized implementation of DartList for ints. 
*
* This implementation avoids boxing integers and is more efficient. It
* implements DartList, but references should be statically typed to the
* specialized implementation and __int methods should then be used
* instead of interface methods for efficiency reasons.
*/
public class DartList__int extends dart.core.List__int implements List<Integer> {
  static final int DEFAULT_SIZE = 16;
  static final float GROW_FACTOR = 1.5F;

  int size;

  int[] array;

  public DartList__int(ConstructorHelper.EmptyConstructorMarker arg, Type type)
  {
    super(arg, type);
  }

  DartList__int(Type type, int parameterSize) {
    super((ConstructorHelper.EmptyConstructorMarker) null, type);

    if (parameterSize == 0) {
      // No parameter given ("null")
      this.array = new int[DEFAULT_SIZE];
      this.size = 0;
    } else {
      this.array = new int[parameterSize];
      this.size = parameterSize;
    }
  }
  
  private void increaseSize() {
    array = Arrays.copyOf(array, (int) (array.length * GROW_FACTOR) + 1);
  }

  private boolean isArrayFull() {
    return size == array.length;
  }

  public void _constructor__int() {

  }

  // --- Methods defined in List ---

  public int operatorAt__int(int index) {
    return array[index];
  }

  public void operatorAtPut__int(int index, int value) {
    if (size <= index || index < 0) {
      // TODO(springerm): Dart exceptions
      throw new RuntimeException("RangeError: out of bounds");
    }

    array[index] = value;
  }

  public int getLength__int() {
    return size;
  }

  public void setLength__int(int newLength) {
    // TODO(springerm): Check semantics (null values)
    size = newLength;
    array = Arrays.copyOf(array, size);
  }

  public boolean add__int(int value) {
    if (isArrayFull()) {
      increaseSize();
    }

    array[size] = value;
    size++;

    return true;
  }

  // TODO(springerm): addAll
  // TODO(springerm): reversed
  // TODO(springerm): sort
  // TODO(springerm): shuffle

  public int indexOf__int(int element, int start) {
    for (int i = start; i < size; i++) {
      if (array[i] == element) {
        return i;
      }
    }

    return -1;
  }

  // TODO(springerm): lastIndexOf

  public void clear__int() {
    size = 0;
    array = new int[DEFAULT_SIZE];
  }


  public void insert__int(int index, int element) {
    if (isArrayFull()) {
      increaseSize();
    }

    for (int i = size; i > index; i--) {
      array[i] = array[i - 1];
    }

    array[index] = element;
  }

  // TODO(springerm): insertAll
  // TODO(springerm): setAll

  // Must be boxed integer here
  public boolean remove__int(Object value) {
    int index;
    boolean found = false;

    if (!(value instanceof Integer)) {
      return false;
    }
    int intValue = (Integer) value;

    // find
    for (index = 0; index < size; index++) {
      if (array[index] == intValue) {
        found = true;
        break;
      }
    }

    // shift
    if (found) {
      for ( ; index < size - 1; index++) {
        array[index] = array[index + 1];
      }

      size--;
      array[size] = 0;
    }

    return found;
  }

  public int removeAt__int(int index) {
    int element = operatorAt__int(index);

    for (int i = index; i < size - 1; i++) {
      array[i] = array[i + 1];
    }

    size--;
    array[size] = 0;

    return element;
  }

  public int removeLast__int() {
    int element = array[size - 1];
    array[size - 1] = 0;

    size--;
    return element;
  }

  public dart.core.List_interface__int sublist__int(int start, int end) {
    throw new RuntimeException("Not implemented yet.");
  }

  // TODO(springerm): removeWhere
  // TODO(springerm): retainWhere
  // TODO(springerm): sublist
  // TODO(springerm): getRange
  // TODO(springerm): setRange
  // TODO(springerm): removeRange
  // TODO(springerm): fillRange
  // TODO(springerm): replaceRange
  // TODO(springerm): asMap


  // --- Methods defined in Iterable ---
  public boolean contains(Object element) {
    return contains__int(element);
  }

  // TODO(springerm): getIterator
  // TODO(springerm): map
  // TODO(springerm): where
  // TODO(springerm): expand

  // Always boxed integer here
  public boolean contains__int(Object element) {
    if (!(element instanceof Integer)) {
      return false;
    }
    int intValue = (Integer) element;
    
    for (int i = 0; i < size; i++) {
      if (array[i] == intValue) {
        return true;
      }
    }

    return false;
  }

  // TODO(springerm): forEach
  // TODO(springerm): reduce
  // TODO(springerm): fold
  // TODO(springerm): every
  // TODO(springerm): join
  // TODO(springerm): any
  // TODO(springerm): toList
  // TODO(springerm): toSet

  public boolean isEmpty__int() {
    return size == 0;
  }

  public boolean isEmpty() {
    return isEmpty();
  }

  public boolean isNotEmpty__int() {
    return size != 0;
  }

  // TODO(springerm): take
  // TODO(springerm): takeWhile
  // TODO(springerm): skip
  // TODO(springerm): skipWhile

  public int getFirst__int() {
    if (size == 0) {
      // TODO(springerm): Dart exceptions
      throw new RuntimeException("StateError: List is empty");
    }

    return array[0];
  }

  public int getLast__int() {
    if (size == 0) {
      // TODO(springerm): Dart exceptions
      throw new RuntimeException("StateError: List is empty");
    }

    return array[size - 1];
  }

  public int getSingle__int() {
    if (size != 1) {
      // TODO(springerm): Dart exceptions
      throw new RuntimeException("StateError: Expected exactly one element");
    }

    return array[0];
  }

  // TODO(springerm): firstWhere
  // TODO(springerm): lastWhere
  // TODO(springerm): singleWhere
  // TODO(springerm): elementAt
  // TODO(springerm): toString


  // --- Methods defined in Object ---
  // TODO(springerm): Proper implementations for Object methods
  public int getHashCode__int() {
    return this.hashCode();
  }

  public boolean operatorEqual__int(Object other) {
    return this == other;
  }


  // --- Methods defined in java.util.List ---
  public void add(int index, Integer element) {
    insert(index, element);
  }

  public boolean addAll(Collection<? extends Integer> c) {
    for (Integer element : c) {
      add(element);
    }

    return true;
  }

  public boolean addAll(int index, Collection<? extends Integer> c) {
    // TODO(springerm): Implement
    return false;
  }

  public boolean containsAll(Collection<?> c) {
    for (Object element : c) {
      if (!contains__int(c)) {
        return false;
      }
    }

    return true;
  }

  public Integer get(int index) {
    return operatorAt(index);
  }

  public int indexOf(Object element) {
    if (!(element instanceof Integer)) {
      return -1;
    }

    return indexOf__int((Integer) element, 0);
  }

  public Iterator<Integer> iterator() {
    return new Iterator() {
      int nextIndex = 0;

      public boolean hasNext() {
        return nextIndex < size;
      }

      public Integer next() {
        if (!hasNext()) {
          throw new NoSuchElementException();
        }
        return array[nextIndex++];
      }
    };
  }

  public int lastIndexOf(Object o) {
    if (!(o instanceof Integer)) {
      return -1;
    }

    for (int i = size - 1; i > -1; i--) {
      if (array[i] == (Integer) o) {
        return i;
      }
    }

    return -1;
  }

  public ListIterator<Integer> listIterator() {
    // TODO(springerm): Implement
    return null;
  }

  public ListIterator<Integer> listIterator(int index) {
    // TODO(springerm): Implement
    return null;
  }

  public Integer remove(int index) {
    return removeAt(index);
  }

  public boolean removeAll(Collection<?> c) {
    boolean changed = false;
    for (Object element : c) {
      changed = remove(element) || changed;
    }
    return changed;
  }

  public boolean retainAll(Collection<?> c) {
    // TODO(springerm): Implement
    return false;
  }

  public Integer set(int index, Integer element) {
    Integer oldValue = operatorAt(index);
    operatorAtPut(index, element);
    return oldValue;
  }

  public int size() {
    return getLength();
  }

  public List<Integer> subList(int fromIndex, int toIndex) {
    // TODO(springerm): Implement
    return null;
  }
  
  public Object[] toArray() {
    Object[] result = new Object[size];
    for (int i = 0; i < size; i++) {
      result[i] = array[i];
    }
    return result;
  }

  public <E> E[] toArray(E[] a) {
    return Arrays.copyOf(toArray(), size, (Class<E[]>) a.getClass());
  }
}