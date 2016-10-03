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
import dart._runtime.types.simple.InterfaceType;
import dart._runtime.types.simple.InterfaceTypeExpr;
import dart._runtime.types.simple.TopType;
import dart._runtime.types.simple.Type;
import dart._runtime.types.simple.TypeEnvironment;
import dart._runtime.types.simple.TypeExpr;

/**
* A specialized implementation of DartList for ints. 
*
* This implementation avoids boxing integers and is more efficient. It
* implements DartList, but references should be statically typed to the
* specialized implementation and __int methods should then be used
* instead of interface methods for efficiency reasons.
*/
public class DartList__int 
    extends dart.core.Iterable__int 
    implements dart.core.List_interface__int, List<Integer> {
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

  public int operatorAt_List__int(int index) {
    return array[index];
  }

  public void operatorAtPut_List__int(int index, int value) {
    if (size <= index || index < 0) {
      // TODO(springerm): Dart exceptions
      throw new RuntimeException("RangeError: out of bounds");
    }

    array[index] = value;
  }

  public int getLength_List__int() {
    return size;
  }

  public void setLength_List__int(int newLength) {
    // TODO(springerm): Check semantics (null values)
    size = newLength;
    array = Arrays.copyOf(array, size);
  }

  public boolean add_List__int(int value) {
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

  public int indexOf_List__int(int element, int start) {
    for (int i = start; i < size; i++) {
      if (array[i] == element) {
        return i;
      }
    }

    return -1;
  }

  // TODO(springerm): lastIndexOf

  public void clear_List__int() {
    size = 0;
    array = new int[DEFAULT_SIZE];
  }


  public void insert_List__int(int index, int element) {
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
  public boolean remove_List__int(Object value) {
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

  public int removeAt_List__int(int index) {
    int element = operatorAt_List__int(index);

    for (int i = index; i < size - 1; i++) {
      array[i] = array[i + 1];
    }

    size--;
    array[size] = 0;

    return element;
  }

  public int removeLast_List__int() {
    int element = array[size - 1];
    array[size - 1] = 0;

    size--;
    return element;
  }

  public dart.core.List_interface__int sublist_List__int(int start, int end) {
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
  public boolean contains_Iterable(Object element) {
    return contains_Iterable__int(element);
  }

  public dart.core.Iterator_interface__int getIterator_Iterable__int() {
    Type iteratorType = dart2java$type.env.evaluate(new InterfaceTypeExpr(
      dart.core.Iterator.dart2java$typeInfo, 
      new TypeExpr[] { dart.core.List.dart2java$typeInfo.typeVariables[0] }));

    return new dart.core.Iterator__int(
        (ConstructorHelper.EmptyConstructorMarker) null, iteratorType) {
      int nextIndex = -1;

      public boolean moveNext_Iterator__int() {
        if (nextIndex < size - 1) {
          nextIndex++;
          return true;
        } else {
          return false;
        }
      }

      public int getCurrent_Iterator__int() {
        return array[nextIndex];
      }
    };
  }

  public dart.core.Iterator_interface__int getIterator_Iterable() {
    return getIterator_Iterable__int();
  }

  // TODO(springerm): map
  // TODO(springerm): where
  // TODO(springerm): expand

  // Always boxed integer here
  public boolean contains_Iterable__int(Object element) {
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

  public boolean isEmpty_Iterable__int() {
    return size == 0;
  }

  public boolean isNotEmpty_Iterable__int() {
    return size != 0;
  }

  // TODO(springerm): take
  // TODO(springerm): takeWhile
  // TODO(springerm): skip
  // TODO(springerm): skipWhile

  public int getFirst_Iterable__int() {
    if (size == 0) {
      // TODO(springerm): Dart exceptions
      throw new RuntimeException("StateError: List is empty");
    }

    return array[0];
  }

  public int getLast_Iterable__int() {
    if (size == 0) {
      // TODO(springerm): Dart exceptions
      throw new RuntimeException("StateError: List is empty");
    }

    return array[size - 1];
  }

  public int getSingle_Iterable__int() {
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
  public int getHashCode_Object() {
    return this.hashCode();
  }

  public boolean operatorEqual_Object(Object other) {
    return this == other;
  }


  // --- Additional methods defined in java.util.List ---

  public void add(int index, Integer element) {
    insert_List(index, element);
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

  public boolean contains(Object value) {
    return contains_Iterable__int(value);
  }

  public boolean containsAll(Collection<?> c) {
    for (Object element : c) {
      if (!contains_Iterable__int(c)) {
        return false;
      }
    }

    return true;
  }

  public Integer get(int index) {
    return operatorAt_List(index);
  }

  public int indexOf(Object element) {
    if (!(element instanceof Integer)) {
      return -1;
    }

    return indexOf_List__int((Integer) element, 0);
  }

  public boolean isEmpty() {
    return isEmpty_Iterable__int();
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
    return removeAt_List(index);
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
    Integer oldValue = operatorAt_List(index);
    operatorAtPut_List(index, element);
    return oldValue;
  }

  public int size() {
    return getLength_List();
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


  // --- Delegator Methods for Dynamic Method Calls ---

  // Note: Delegator methods for fully generic specialization (ending with "_List") are no longer
  // contained in this class but in the automatically-generated interface (as default methods).

  public java.lang.Integer operatorAt(int index)
  {
    return this.operatorAt_List__int(index);
  }

  public void operatorAtPut(int index, java.lang.Integer value)
  {
    this.operatorAtPut_List__int(index, value);
  }

  public int getLength()
  {
    return this.getLength_List__int();
  }

  public void setLength(int newLength)
  {
    this.setLength_List__int(newLength);
  }

  public boolean add(java.lang.Integer value)
  {
    return this.add_List__int(value);
  }

  public int indexOf(java.lang.Integer element, int start)
  {
    return this.indexOf_List__int(element, start);
  }

  public void clear()
  {
    this.clear_List__int();
  }

  public void insert(int index, java.lang.Integer element)
  {
    this.insert_List__int(index, element);
  }

  public boolean remove(java.lang.Object value)
  {
    return this.remove_List__int(value);
  }

  public java.lang.Integer removeAt(int index)
  {
    return this.removeAt_List__int(index);
  }

  public java.lang.Integer removeLast()
  {
    return this.removeLast_List__int();
  }

  public dart.core.List_interface__int sublist(int start, int end)
  {
    return this.sublist_List__int(start, end);
  }
}