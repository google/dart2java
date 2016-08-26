package dart._runtime.base;

import java.util.Map;
import java.util.HashMap;

// TODO(andrewkrieger, springerm): Extend/implement DartObject.
public interface DartMap<K, V> {
  
  boolean containsValue(Object value);
  boolean containsKey(Object key);
  V operatorAt(Object key);
  void operatorAtPut(K key, V value);
  void clear();
  int getLength();
  boolean isEmpty();
  boolean isNotEmpty();
  
  // TODO(springerm): Maybe this should be called LinkedHashMap?
  public class Generic<K, V> implements DartMap<K, V> {
    Class<K> keyType;

    Class<V> valueType;

    Map<K, V> map;

    public static <K, V> Generic<K, V> newInstance(
      Class<K> keyType, Class<V> valueType) {
      return new Generic<K, V>(keyType, valueType);
    }

    public Generic(Class<K> keyType, Class<V> valueType) {
      this.keyType = keyType;
      this.valueType = valueType;
      this.map = new HashMap<K, V>();
    }

    public boolean containsValue(Object value) {
      return map.containsValue(value);
    }

    public boolean containsKey(Object key) {
      return map.containsKey(key);
    }

    public V operatorAt(Object key) {
      return map.get(key);
    }

    public void operatorAtPut(K key, V value) {
      map.put(key, value);
    }

    // TODO(springerm): Implement putIfAbsent
    // TODO(springerm): Implement addAll

    public void clear() {
      map.clear();
    }

    // TODO(springerm): Implement forEach
    // TODO(springerm): Implement getKeys
    // TODO(springerm): Implement getValues

    public int getLength() {
      return map.size();
    }

    public boolean isEmpty() {
      return map.isEmpty();
    }

    public boolean isNotEmpty() {
      return !map.isEmpty();
    }
  }

}
