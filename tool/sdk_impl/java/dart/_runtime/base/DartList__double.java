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
* This implementation avoids boxing Doubles and is more efficient. It
* implements DartList, but references should be statically typed to the
* specialized implementation and __double methods should then be used
* instead of interface methods for efficiency reasons.
*/
public class DartList__double 
    extends dart.core.Iterable__double 
    implements dart.core.List_interface__double, List<Double> {
  static final int DEFAULT_SIZE = 16;
  static final float GROW_FACTOR = 1.5F;

  int size;

  double[] array;

  public DartList__double(ConstructorHelper.EmptyConstructorMarker arg, Type type)
  {
    super(arg, type);
  }

  DartList__double(Type type, int parameterSize) {
    super((ConstructorHelper.EmptyConstructorMarker) null, type);

    if (parameterSize == 0) {
      // No parameter given ("null")
      this.array = new double[DEFAULT_SIZE];
      this.size = 0;
    } else {
      this.array = new double[parameterSize];
      this.size = parameterSize;
    }
  }
  
  private void increaseSize() {
    array = Arrays.copyOf(array, (int) (array.length * GROW_FACTOR) + 1);
  }

  private boolean isArrayFull() {
    return size == array.length;
  }

  public void _constructor__double() {

  }

  // --- Methods defined in List ---

  public double operatorAt_List__double(int index) {
    return array[index];
  }

  public void operatorAtPut_List__double(int index, double value) {
    if (size <= index || index < 0) {
      // TODO(springerm): Dart exceptions
      throw new RuntimeException("RangeError: out of bounds");
    }

    array[index] = value;
  }

  public int getLength_List__double() {
    return size;
  }

  public void setLength_List__double(int newLength) {
    // TODO(springerm): Check semantics (null values)
    size = newLength;
    array = Arrays.copyOf(array, size);
  }

  public boolean add_List__double(double value) {
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

  public int indexOf_List__double(double element, int start) {
    for (int i = start; i < size; i++) {
      if (array[i] == element) {
        return i;
      }
    }

    return -1;
  }

  // TODO(springerm): lastIndexOf

  public void clear_List__double() {
    size = 0;
    array = new double[DEFAULT_SIZE];
  }


  public void insert_List__double(int index, double element) {
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

  // Must be boxed Double here
  public boolean remove_List__double(Object value) {
    int index;
    boolean found = false;

    if (!(value instanceof Double)) {
      return false;
    }
    double doubleValue = (Double) value;

    // find
    for (index = 0; index < size; index++) {
      if (array[index] == doubleValue) {
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

  public double removeAt_List__double(int index) {
    double element = operatorAt_List__double(index);

    for (int i = index; i < size - 1; i++) {
      array[i] = array[i + 1];
    }

    size--;
    array[size] = 0;

    return element;
  }

  public double removeLast_List__double() {
    double element = array[size - 1];
    array[size - 1] = 0;

    size--;
    return element;
  }

  public dart.core.List_interface__double sublist_List__double(int start, int end) {
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
    return contains_Iterable__double(element);
  }

  public dart.core.Iterator_interface__double getIterator_Iterable__double() {
    Type iteratorType = dart2java$type.env.evaluate(new InterfaceTypeExpr(
      dart.core.Iterator.dart2java$typeInfo, 
      new TypeExpr[] { dart.core.List.dart2java$typeInfo.typeVariables[0] }));

    return new dart.core.Iterator__double(
        (ConstructorHelper.EmptyConstructorMarker) null, iteratorType) {
      int nextIndex = -1;

      public boolean moveNext_Iterator__double() {
        if (nextIndex < size - 1) {
          nextIndex++;
          return true;
        } else {
          return false;
        }
      }

      public double getCurrent_Iterator__double() {
        return array[nextIndex];
      }
    };
  }

  public dart.core.Iterator_interface__double getIterator_Iterable() {
    return getIterator_Iterable__double();
  }

  // TODO(springerm): map
  // TODO(springerm): where
  // TODO(springerm): expand

  // Always boxed Double here
  public boolean contains_Iterable__double(Object element) {
    if (!(element instanceof Double)) {
      return false;
    }
    double doubleValue = (Double) element;
    
    for (int i = 0; i < size; i++) {
      if (array[i] == doubleValue) {
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

  public boolean isEmpty_Iterable__double() {
    return size == 0;
  }

  public boolean isNotEmpty_Iterable__double() {
    return size != 0;
  }

  // TODO(springerm): take
  // TODO(springerm): takeWhile
  // TODO(springerm): skip
  // TODO(springerm): skipWhile

  public double getFirst_Iterable__double() {
    if (size == 0) {
      // TODO(springerm): Dart exceptions
      throw new RuntimeException("StateError: List is empty");
    }

    return array[0];
  }

  public double getLast_Iterable__double() {
    if (size == 0) {
      // TODO(springerm): Dart exceptions
      throw new RuntimeException("StateError: List is empty");
    }

    return array[size - 1];
  }

  public double getSingle_Iterable__double() {
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


  // --- Methods defined in java.util.List ---
  public void add(int index, Double element) {
    insert(index, element);
  }

  public boolean addAll(Collection<? extends Double> c) {
    for (Double element : c) {
      add(element);
    }

    return true;
  }

  public boolean addAll(int index, Collection<? extends Double> c) {
    // TODO(springerm): Implement
    return false;
  }

  public boolean containsAll(Collection<?> c) {
    for (Object element : c) {
      if (!contains_Iterable__double(c)) {
        return false;
      }
    }

    return true;
  }

  public Double get(int index) {
    return operatorAt(index);
  }

  public int indexOf(Object element) {
    if (!(element instanceof Double)) {
      return -1;
    }

    return indexOf_List__double((Double) element, 0);
  }

  public boolean isEmpty() {
    return isEmpty_Iterable__double();
  }

  public Iterator<Double> iterator() {
    return new Iterator() {
      int nextIndex = 0;

      public boolean hasNext() {
        return nextIndex < size;
      }

      public Double next() {
        if (!hasNext()) {
          throw new NoSuchElementException();
        }
        return array[nextIndex++];
      }
    };
  }

  public int lastIndexOf(Object o) {
    if (!(o instanceof Double)) {
      return -1;
    }

    for (int i = size - 1; i > -1; i--) {
      if (array[i] == (Double) o) {
        return i;
      }
    }

    return -1;
  }

  public ListIterator<Double> listIterator() {
    // TODO(springerm): Implement
    return null;
  }

  public ListIterator<Double> listIterator(int index) {
    // TODO(springerm): Implement
    return null;
  }

  public Double remove(int index) {
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

  public Double set(int index, Double element) {
    Double oldValue = operatorAt(index);
    operatorAtPut(index, element);
    return oldValue;
  }

  public int size() {
    return getLength();
  }

  public List<Double> subList(int fromIndex, int toIndex) {
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


  // --- Delegator Methods ---
  public java.lang.Double operatorAt(int index)
  {
    return this.operatorAt_List__double(index);
  }

  public void operatorAtPut(int index, java.lang.Double value)
  {
    this.operatorAtPut_List__double(index, value);
  }

  public int getLength()
  {
    return this.getLength_List__double();
  }

  public void setLength(int newLength)
  {
    this.setLength_List__double(newLength);
  }

  public boolean add(java.lang.Double value)
  {
    return this.add_List__double(value);
  }

  public int indexOf(java.lang.Double element, int start)
  {
    return this.indexOf_List__double(element, start);
  }

  public void clear()
  {
    this.clear_List__double();
  }

  public void insert(int index, java.lang.Double element)
  {
    this.insert_List__double(index, element);
  }

  public boolean remove(java.lang.Object value)
  {
    return this.remove_List__double(value);
  }

  public java.lang.Double removeAt(int index)
  {
    return this.removeAt_List__double(index);
  }

  public java.lang.Double removeLast()
  {
    return this.removeLast_List__double();
  }

  public dart.core.List_interface__double sublist(int start, int end)
  {
    return this.sublist_List__double(start, end);
  }
}