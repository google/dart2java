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
* The generic implementation of DartList. 
*/
public class DartList<T> 
    extends dart.core.Iterable<T> 
    implements dart.core.List_interface<T>, List<T> {
  static final int DEFAULT_SIZE = 16;
  static final float GROW_FACTOR = 1.5F;

  int size;

  T[] array;

  Class<T> genericType;

  Class<T[]> genericArrayType;

  public DartList(ConstructorHelper.EmptyConstructorMarker arg, Type type)
  {
    super(arg, type);
  }

  DartList(Type type, Class<T> genericType, int parameterSize) {
    super((ConstructorHelper.EmptyConstructorMarker) null, type);

    this.genericType = genericType;
    this.genericArrayType = (Class<T[]>) Array.newInstance(genericType, 0)
      .getClass();
    
    if (parameterSize == 0) {
      // No size argument given ("null")
      this.array = (T[]) Array.newInstance(genericType, DEFAULT_SIZE);
      this.size = 0;
    } else {
      this.array = (T[]) Array.newInstance(genericType, parameterSize);
      this.size = parameterSize;
    }
  }

  public static <E> dart.core.List_interface<E> newInstance$(
      TypeEnvironment dart2java$localTypeEnv, int length)
  {
    Type type = dart2java$localTypeEnv.evaluate(
      new InterfaceTypeExpr(
        dart.core.List.dart2java$typeInfo, 
        new TypeExpr[] {dart.core.List.factory$$typeInfo.typeVariables[0]}));
    Type innerType = type.env.evaluate(
      dart.core.List.dart2java$typeInfo.typeVariables[0]);

    // Create instance of correct specialization
    // TODO(springerm): Specializations for bool, double missing
    if (innerType == dart._runtime.helpers.IntegerHelper.type)
    {
      return (dart.core.List_interface) (new DartList__int(type, length));
    } else {
      InterfaceType reifiedType = (InterfaceType) type;
      Type firstTypeArg = reifiedType.actualTypeParams[0];

      Class javaClassObj;
      if (firstTypeArg instanceof InterfaceType) {
        javaClassObj = ((InterfaceType) reifiedType.actualTypeParams[0])
          .getJavaType();
      } else if (firstTypeArg instanceof TopType) {
        // Type of list is "dynamic"
        javaClassObj = Object.class;
      } else {
        throw new RuntimeException("Unknown generic type: " 
          + firstTypeArg.toString());
      }
      
      return new DartList<E>(type, javaClassObj, length);
    }
  }

  public static <T> dart.core.List_interface<T> factory$fromArguments(
      Type type, Class<T> javaClassObj, T... elements) {
    Type innerType = type.env.evaluate(
        dart.core.List.dart2java$typeInfo.typeVariables[0]);

    // Create instance of correct specialization
    // TODO(springerm): Specializations for bool, double missing
    if (innerType == dart._runtime.helpers.IntegerHelper.type)
    {
      DartList__int instance = new DartList__int(type, elements.length);

      for (int i = 0; i < elements.length; i++) {
        instance.operatorAtPut_List__int(i, (Integer) elements[i]);
      }
      return (dart.core.List_interface) instance;
    } else {
      InterfaceType reifiedType = (InterfaceType) type;
      InterfaceType firstGenericType = (InterfaceType) reifiedType.actualTypeParams[0];
      DartList<T> instance = new DartList<T>(
        type, (Class) firstGenericType.getJavaType(), elements.length);

      for (int i = 0; i < elements.length; i++) {
        instance.operatorAtPut_List(i, elements[i]);
      }
      return instance;
    }
  }
  
  private void increaseSize() {
    array = Arrays.copyOf(array, (int) (array.length * GROW_FACTOR) + 1, 
      genericArrayType);
  }

  private boolean isArrayFull() {
    return size == array.length;
  }


  // --- Methods defined in List ---

  public T operatorAt_List(int index) {
    if (size <= index || index < 0) {
      // TODO(springerm): Dart exceptions
      throw new RuntimeException("RangeError: out of bounds");
    }

    return array[index];
  }

  public T operatorAt(int index) {
    return operatorAt_List(index);
  }

  public void operatorAtPut_List(int index, T value) {
    if (size <= index || index < 0) {
      // TODO(springerm): Dart exceptions
      throw new RuntimeException("RangeError: out of bounds");
    }

    array[index] = value;
  }

  public void operatorAtPut(int index, T value) {
    operatorAtPut_List(index, value);
  }

  public int getLength_List() {
    return size;
  }

  public int getLength() {
    return getLength_List();
  }

  public void setLength_List(int newLength) {
    // TODO(springerm): Check semantics (null values)
    size = newLength;
    array = Arrays.copyOf(array, size, genericArrayType);
  }

  public void setLength(int newLength) {
    setLength_List(newLength);
  }

  public boolean add_List(T value) {
    if (isArrayFull()) {
      increaseSize();
    }

    array[size] = value;
    size++;

    return true;
  }

  public boolean add(T value) {
    return add_List(value);
  }

  // TODO(springerm): addAll
  // TODO(springerm): reversed
  // TODO(springerm): sort
  // TODO(springerm): shuffle

  public int indexOf_List(T element, int start) {
    for (int i = start; i < size; i++) {
      if (array[i].equals(element)) {
        return i;
      }
    }

    return -1;
  }

  public int indexOf(T element, int start) {
    return indexOf_List(element, start);
  }

  // TODO(springerm): lastIndexOf

  public void clear_List() {
    size = 0;
    array = (T[]) Array.newInstance(genericType, DEFAULT_SIZE);
  }

  public void clear() {
    clear_List();
  }

  public void insert_List(int index, T element) {
    if (isArrayFull()) {
      increaseSize();
    }

    for (int i = size; i > index; i--) {
      array[i] = array[i - 1];
    }

    array[index] = element;
  }

  public void insert(int index, T element) {
    insert_List(index, element);
  }

  // TODO(springerm): insertAll
  // TODO(springerm): setAll

  public boolean remove_List(Object value) {
    int index;
    boolean found = false;

    // find
    for (index = 0; index < size; index++) {
      if (array[index] == value) {
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
      array[size] = null;
    }

    return found;
  }

  public boolean remove(Object value) {
    return remove_List(value);
  }

  public T removeAt_List(int index) {
    T element = operatorAt_List(index);

    for (int i = index; i < size - 1; i++) {
      array[i] = array[i + 1];
    }

    size--;
    array[size] = null;

    return element;
  }

  public T removeAt(int index) {
    return removeAt_List(index);
  }

  public T removeLast_List() {
    T element = array[size - 1];
    array[size - 1] = null;

    size--;
    return element;
  }

  public T removeLast() {
    return removeLast_List();
  }

  // TODO(springerm): removeWhere
  // TODO(springerm): retainWhere

  public DartList<T> sublist_List(int start, int end) {
    throw new RuntimeException("Not implemented yet.");
  }

  public DartList<T> sublist(int start, int end) {
    return sublist_List(start, end);
  }

  // TODO(springerm): getRange
  // TODO(springerm): setRange
  // TODO(springerm): removeRange
  // TODO(springerm): fillRange
  // TODO(springerm): replaceRange
  // TODO(springerm): asMap


  // --- Methods defined in Iterable ---

  public dart.core.Iterator_interface<T> getIterator_Iterable() {
    Type iteratorType = dart2java$type.env.evaluate(new InterfaceTypeExpr(
      dart.core.Iterator.dart2java$typeInfo, 
      new TypeExpr[] { dart.core.List.dart2java$typeInfo.typeVariables[0] }));

    return new dart.core.Iterator<T>(
        (ConstructorHelper.EmptyConstructorMarker) null, iteratorType) {
      int nextIndex = -1;

      public boolean moveNext_Iterator() {
        if (nextIndex < size - 1) {
          nextIndex++;
          return true;
        } else {
          return false;
        }
      }

      public boolean moveNext() {
        return moveNext_Iterator();
      }

      public T getCurrent_Iterator() {
        return (T) array[nextIndex];
      }

      public T getCurrent() {
        return getCurrent_Iterator();
      }
    };
  }

  // TODO(springerm): map
  // TODO(springerm): where
  // TODO(springerm): expand

  public boolean contains_Iterable(Object element) {
    for (int i = 0; i < size; i++) {
      if (array[i] == element) {
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

  public boolean isEmpty_Iterable() {
    return size == 0;
  }

  public boolean isNotEmpty_Iterable() {
    return size != 0;
  }

  // TODO(springerm): take
  // TODO(springerm): takeWhile
  // TODO(springerm): skip
  // TODO(springerm): skipWhile

  public T getFirst_Iterable() {
    if (size == 0) {
      // TODO(springerm): Dart exceptions
      throw new RuntimeException("StateError: List is empty");
    }

    return array[0];
  }

  public T getLast_Iterable() {
    if (size == 0) {
      // TODO(springerm): Dart exceptions
      throw new RuntimeException("StateError: List is empty");
    }

    return array[size - 1];
  }

  public T getSingle_Iterable() {
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
  // TODO(springerm): Proper implementations for methods defined in Object


  // --- Additional methods defined in java.util.List ---

  public void add(int index, T element) {
    insert_List(index, element);
  }

  public boolean addAll(Collection<? extends T> c) {
    for (T element : c) {
      add(element);
    }

    return true;
  }

  public boolean addAll(int index, Collection<? extends T> c) {
    // TODO(springerm): Implement
    return false;
  }

  public boolean contains(Object element) {
    return contains_Iterable(element);
  }

  public boolean containsAll(Collection<?> c) {
    for (Object element : c) {
      if (!contains(c)) {
        return false;
      }
    }

    return true;
  }

  public T get(int index) {
    return operatorAt_List(index);
  }

  public int indexOf(Object element) {
    return indexOf_List((T) element, 0);
  }

  public boolean isEmpty() {
    return isEmpty_Iterable();
  }

  public Iterator<T> iterator() {
    return new Iterator() {
      int nextIndex = 0;

      public boolean hasNext() {
        return nextIndex < size;
      }

      public T next() {
        if (!hasNext()) {
          throw new NoSuchElementException();
        }
        return array[nextIndex++];
      }
    };
  }

  public int lastIndexOf(Object o) {
    for (int i = size - 1; i > -1; i--) {
      if (array[i] == o) {
        return i;
      }
    }

    return -1;
  }

  public ListIterator<T> listIterator() {
    // TODO(springerm): Implement
    return null;
  }

  public ListIterator<T> listIterator(int index) {
    // TODO(springerm): Implement
    return null;
  }

  public T remove(int index) {
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

  public T set(int index, T element) {
    T oldValue = operatorAt_List(index);
    operatorAtPut_List(index, element);
    return oldValue;
  }

  public int size() {
    return getLength_List();
  }

  public List<T> subList(int fromIndex, int toIndex) {
    // TODO(springerm): Implement
    return null;
  }
  
  public Object[] toArray() {
    return Arrays.copyOf(array, size, 
      (Class<Object[]>) Array.newInstance(Object.class, 0).getClass());
  }

  public <E> E[] toArray(E[] a) {
    return Arrays.copyOf(array, size, (Class<E[]>) a.getClass());
  }
}