part of dart._internal;
 
class LinkedHashMap<K, V> implements Map<K, V> {
  List<K> _keys = new List<K>();
  List<V> _values = new List<V>();

  LinkedHashMap();

  factory LinkedHashMap.newInstance() {
    return new LinkedHashMap<K, V>();
  }

  bool containsValue(Object value) {
    return _values.contains(value);
  }

  bool containsKey(Object key) {
    return _keys.contains(key);
  }

  V operator [](Object key) {
    for (int i = 0; i < _keys.length; i++) {
      if (_keys[i] == key) {
        return _values[i];
      }
    }

    throw "Key not found";
  }

  void operator []=(K key, V value) {
    for (int i = 0; i < _keys.length; i++) {
      if (_keys[i] == key) {
        _values[i] = value;
        return;
      }
    }

    _keys.add(key);
    _values.add(value);
  }

  V remove(Object key) {
    for (int i = 0; i < _keys.length; i++) {
      if (_keys[i] == key) {
        _keys.removeAt(i);
        return _values.removeAt(i);
      }
    }

    throw "Key not found";
  }

  void clear() {
    _keys.clear();
    _values.clear();
  }

  int get length => _keys.length;

  bool get isEmpty => _keys.isEmpty;

  bool get isNotEmpty => _keys.isNotEmpty;

  Iterable<K> get keys => _keys;

  Iterable<V> get values => _values;
}