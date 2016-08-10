package dart._runtime.base;

import java.lang.reflect.Array;
import java.util.Arrays;

public class DartList<T> {
  static final int DEFAULT_SIZE = 16;
  static final float GROW_FACTOR = 1.5F;

  int size;

  T[] array;

  Class<T> genericType;

  Class<T[]> genericArrayType;

  DartList(Class<T> genericType) {
    this.genericType = genericType;
    this.genericArrayType = (Class<T[]>) Array.newInstance(genericType, 0)
      .getClass();
    
    this.array = (T[]) Array.newInstance(genericType, DEFAULT_SIZE);
    size = 0;
  }

  public static <T> DartList<T> newInstance(Class<T> genericType) {
    return new DartList<T>(genericType);
  }

  public static <T> DartList<T> _fromArguments(
    Class<T> genericType, T... elements) {
    DartList<T> instance = newInstance(genericType);

    for (int i = 0; i < elements.length; i++) {
      instance.add(elements[i]);
    }
    return instance;
  }
  
  private void increaseSize() {
    array = Arrays.copyOf(array, (int) (array.length * GROW_FACTOR), 
      genericArrayType);
  }

  private boolean isArrayFull() {
    return size == array.length;
  }


  // --- Methods defined in List ---

  public T operatorAt(int index) {
    if (size <= index || index < 0) {
      // TODO(springerm): Dart exceptions
      throw new RuntimeException("RangeError: out of bounds");
    }

    return array[index];
  }

  public void operatorAtPut(int index, T value) {
    if (size <= index || index < 0) {
      // TODO(springerm): Dart exceptions
      throw new RuntimeException("RangeError: out of bounds");
    }

    array[index] = value;
  }

  public int getLength() {
    return size;
  }

  public void setLength(int newLength) {
    // TODO(springerm): Check semantics (null values)
    size = newLength;
    array = Arrays.copyOf(array, size, genericArrayType);
  }

  public void add(T value) {
    if (isArrayFull()) {
      increaseSize();
    }

    array[size] = value;
    size++;
  }

  // TODO(springerm): addAll
  // TODO(springerm): reversed
  // TODO(springerm): sort
  // TODO(springerm): shuffle

  public int indexOf(T element) {
    return indexOf(element, 0);
  }

  public int indexOf(T element, int start) {
    for (int i = start; i < size; i++) {
      if (array[i].equals(element)) {
        return i;
      }
    }

    return -1;
  }

  // TODO(springerm): lastIndexOf

  public void clear() {
    size = 0;
    array = (T[]) Array.newInstance(genericType, DEFAULT_SIZE);
  }

  public void insert(int index, T element) {
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

  public boolean remove(Object value) {
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

  public T removeAt(int index) {
    T element = operatorAt(index);

    for (int i = index; i < size - 1; i++) {
      array[i] = array[i + 1];
    }

    size--;
    array[size] = null;

    return element;
  }

  public T removeLast() {
    T element = array[size - 1];
    array[size - 1] = null;

    size--;
    return element;
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

  // TODO(springerm): getIterator
  // TODO(springerm): map
  // TODO(springerm): where
  // TODO(springerm): expand

  public boolean contains(Object element) {
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

  public boolean isEmpty() {
    return size == 0;
  }

  public boolean isNotEmpty() {
    return size != 0;
  }

  // TODO(springerm): take
  // TODO(springerm): takeWhile
  // TODO(springerm): skip
  // TODO(springerm): skipWhile

  public T getFirst() {
    if (size == 0) {
      // TODO(springerm): Dart exceptions
      throw new RuntimeException("StateError: List is empty");
    }

    return array[0];
  }

  public T getLast() {
    if (size == 0) {
      // TODO(springerm): Dart exceptions
      throw new RuntimeException("StateError: List is empty");
    }

    return array[array.length - 1];
  }

  public T getSingle() {
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
}