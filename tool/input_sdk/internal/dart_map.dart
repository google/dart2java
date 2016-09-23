part of dart._internal;
 
class LinkedHashMap<K, V> implements Map<K, V> {
  List<K> keys = new List<K>();
  List<V> values = new List<V>();

  LinkedHashMap();

  factory LinkedHashMap.newInstance() {
    return new LinkedHashMap<K, V>();
  }

  bool containsValue(Object value) {
    return values.contains(value);
  }

  bool containsKey(Object key) {
    return keys.contains(key);
  }

  V operator [](Object key) {
    for (int i = 0; i < keys.length; i++) {
      if (keys[i] == key) {
        return values[i];
      }
    }

    throw "Key not found";
  }

  void operator []=(K key, V value) {
    for (int i = 0; i < keys.length; i++) {
      if (keys[i] == key) {
        values[i] = value;
        return;
      }
    }

    keys.add(key);
    values.add(value);
  }

  V remove(Object key) {
    for (int i = 0; i < keys.length; i++) {
      if (keys[i] == key) {
        keys.removeAt(i);
        return values.removeAt(i);
      }
    }

    throw "Key not found";
  }

  void clear() {
    keys.clear();
    values.clear();
  }

  int get length => keys.length;

  bool get isEmpty => keys.isEmpty;

  bool get isNotEmpty => keys.isNotEmpty;
}