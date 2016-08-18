package dart._runtime.base;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.NoSuchElementException;

/** 
 * An interface for Dart lists. 
 *
 * This interface is implemented by both generic lists and specialized lists.
 * Specialized lists are more efficient implementations for primitive types.
 */
public interface DartList<T> extends List<T> {

  // For interoperability: extends Java List Interface
  // TODO(springerm): Replace by compiled interface from dart:core
  
  // Dart List Interface
  T operatorAt(int index);
  void operatorAtPut(int index, T value);
  int getLength();

  // Return type must be boolean as per java.util.List
  boolean add(T value);
  // Parameter must be Object as per java.util.List
  int indexOf(Object element);
  int indexOf(Object element, int start);

  void clear();
  void insert(int index, T element);
  boolean remove(Object value);
  T removeAt(int index);
  T removeLast();
  boolean contains(Object element);
  boolean isEmpty();
  boolean isNotEmpty();
  T getFirst();
  T getLast();
  T getSingle();


  /**
  * The generic implementation of DartList. 
  *
  * This implementation is used most of the time. It should only be used for
  * instantiation. Variable should be of type DartList.
  */
  public static class Generic<T> implements DartList<T> {
    static final int DEFAULT_SIZE = 16;
    static final float GROW_FACTOR = 1.5F;

    int size;

    T[] array;

    Class<T> genericType;

    Class<T[]> genericArrayType;

    Generic(Class<T> genericType) {
      this.genericType = genericType;
      this.genericArrayType = (Class<T[]>) Array.newInstance(genericType, 0)
        .getClass();
      
      this.array = (T[]) Array.newInstance(genericType, DEFAULT_SIZE);
      size = 0;
    }

    public static <T> Generic<T> newInstance(Class<T> genericType) {
      return new Generic<T>(genericType);
    }

    public static <T> Generic<T> _fromArguments(
      Class<T> genericType, T... elements) {
      Generic<T> instance = newInstance(genericType);

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

    public boolean add(T value) {
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

    public int indexOf(Object element) {
      return indexOf(element, 0);
    }

    public int indexOf(Object element, int start) {
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

      return array[size - 1];
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


    // --- Methods defined in java.util.List ---
    public void add(int index, T element) {
      insert(index, element);
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

    public boolean containsAll(Collection<?> c) {
      for (Object element : c) {
        if (!contains(c)) {
          return false;
        }
      }

      return true;
    }

    public T get(int index) {
      return operatorAt(index);
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
          return array[nextIndex];
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

    public T set(int index, T element) {
      T oldValue = operatorAt(index);
      operatorAtPut(index, element);
      return oldValue;
    }

    public int size() {
      return getLength();
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

  /**
  * A specialized implementation of DartList for ints. 
  *
  * This implementation avoids boxing integers and is more efficient. It
  * implements DartList, but references should be statically typed to the
  * specialized implementation and _primitive methods should then be used
  * instead of interface methods for efficiency reasons.
  */
  public class _int implements DartList<Integer> {
    static final int DEFAULT_SIZE = 16;
    static final float GROW_FACTOR = 1.5F;

    int size;

    int[] array;

    _int() {     
      this.array = new int[DEFAULT_SIZE];
      size = 0;
    }

    // Generic type is not necessary, but convention is to pass type arguments
    // as first parameters.
    public static _int newInstance(Class<Integer> genericType) {
      return new _int();
    }

    public static _int _fromArguments(Class<Integer> genericType, 
      int... elements) {
      _int instance = newInstance(genericType);

      for (int i = 0; i < elements.length; i++) {
        instance.add(elements[i]);
      }
      return instance;
    }
    
    private void increaseSize() {
      array = Arrays.copyOf(array, (int) (array.length * GROW_FACTOR));
    }

    private boolean isArrayFull() {
      return size == array.length;
    }


    // --- Methods defined in List ---

    public int operatorAt_primitive(int index) {
      return array[index];
    }

    public Integer operatorAt(int index) {
      return operatorAt_primitive(index);
    }

    public void operatorAtPut_primitive(int index, int value) {
      if (size <= index || index < 0) {
        // TODO(springerm): Dart exceptions
        throw new RuntimeException("RangeError: out of bounds");
      }

      array[index] = value;
    }

    public void operatorAtPut(int index, Integer value) {
      operatorAtPut_primitive(index, value);
    }

    public int getLength_primitive() {
      return size;
    }

    public int getLength() {
      return getLength_primitive();
    }

    public void setLength_primitive(int newLength) {
      // TODO(springerm): Check semantics (null values)
      size = newLength;
      array = Arrays.copyOf(array, size);
    }

    public void setLength(int newLength) {
      setLength_primitive(newLength);
    }

    public boolean add_primitive(int value) {
      if (isArrayFull()) {
        increaseSize();
      }

      array[size] = value;
      size++;

      return true;
    }

    public boolean add(Integer value) {
      return add_primitive(value);
    }

    // TODO(springerm): addAll
    // TODO(springerm): reversed
    // TODO(springerm): sort
    // TODO(springerm): shuffle

    public int indexOf_primitive(int element) {
      return indexOf_primitive(element, 0);
    }

    public int indexOf(Object element) {
      if (!(element instanceof Integer)) {
        return -1;
      }

      return indexOf_primitive((Integer) element);
    }

    public int indexOf_primitive(int element, int start) {
      for (int i = start; i < size; i++) {
        if (array[i] == element) {
          return i;
        }
      }

      return -1;
    }

    public int indexOf(Object element, int start) {
      if (!(element instanceof Integer)) {
        return -1;
      }

      return indexOf_primitive((Integer) element, start);
    }

    // TODO(springerm): lastIndexOf

    public void clear_primitive() {
      size = 0;
      array = new int[DEFAULT_SIZE];
    }

    public void clear() {
      clear_primitive();
    }

    public void insert_primitive(int index, int element) {
      if (isArrayFull()) {
        increaseSize();
      }

      for (int i = size; i > index; i--) {
        array[i] = array[i - 1];
      }

      array[index] = element;
    }

    public void insert(int index, Integer element) {
      insert_primitive(index, element);
    }

    // TODO(springerm): insertAll
    // TODO(springerm): setAll

    // Must be boxed integer here
    public boolean remove_primitive(Object value) {
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

    public boolean remove(Object value) {
      return remove_primitive(value);
    }

    public int removeAt_primitive(int index) {
      int element = operatorAt_primitive(index);

      for (int i = index; i < size - 1; i++) {
        array[i] = array[i + 1];
      }

      size--;
      array[size] = 0;

      return element;
    }

    public Integer removeAt(int index) {
      return removeAt_primitive(index);
    }

    public int removeLast_primitive() {
      int element = array[size - 1];
      array[size - 1] = 0;

      size--;
      return element;
    }

    public Integer removeLast() {
      return removeLast_primitive();
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

    // Always boxed integer here
    public boolean contains_primitive(Object element) {
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

    public boolean contains(Object element) {
      return contains_primitive(element);
    }

    // TODO(springerm): forEach
    // TODO(springerm): reduce
    // TODO(springerm): fold
    // TODO(springerm): every
    // TODO(springerm): join
    // TODO(springerm): any
    // TODO(springerm): toList
    // TODO(springerm): toSet

    public boolean isEmpty_primitive() {
      return size == 0;
    }

    public boolean isEmpty() {
      return isEmpty_primitive();
    }

    public boolean isNotEmpty_primitive() {
      return size != 0;
    }

    public boolean isNotEmpty() {
      return isNotEmpty_primitive();
    }

    // TODO(springerm): take
    // TODO(springerm): takeWhile
    // TODO(springerm): skip
    // TODO(springerm): skipWhile

    public int getFirst_primitive() {
      if (size == 0) {
        // TODO(springerm): Dart exceptions
        throw new RuntimeException("StateError: List is empty");
      }

      return array[0];
    }

    public Integer getFirst() {
      return getFirst_primitive();
    }

    public int getLast_primitive() {
      if (size == 0) {
        // TODO(springerm): Dart exceptions
        throw new RuntimeException("StateError: List is empty");
      }

      return array[size - 1];
    }

    public Integer getLast() {
      return getLast_primitive();
    }

    public int getSingle_primitive() {
      if (size != 1) {
        // TODO(springerm): Dart exceptions
        throw new RuntimeException("StateError: Expected exactly one element");
      }

      return array[0];
    }

    public Integer getSingle() {
      return getSingle_primitive();
    }

    // TODO(springerm): firstWhere
    // TODO(springerm): lastWhere
    // TODO(springerm): singleWhere
    // TODO(springerm): elementAt
    // TODO(springerm): toString


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
        if (!contains(c)) {
          return false;
        }
      }

      return true;
    }

    public Integer get(int index) {
      return operatorAt(index);
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
}