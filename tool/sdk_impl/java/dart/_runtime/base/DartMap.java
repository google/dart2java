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

import java.util.Collection;
import java.util.Map;
import java.util.HashMap;
import java.util.Set;

import dart._runtime.types.simple.TypeEnvironment;

public interface DartMap<K, V> 
  extends Map<K, V>, dart.core.Map_interface<K, V> {

  public static dart.core.Map_interface factory$(TypeEnvironment env) {
    return null;
  }

  // TODO(springerm): Maybe this should be called LinkedHashMap?
  public class Generic<K, V> extends DartObject implements DartMap<K, V> {
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


    // --- Methods defined in Map ---

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

    public V remove(Object key) {
      return map.remove(key);
    }

    public void clear() {
      map.clear();
    }

    // TODO(springerm): Implement forEach
    // TODO(springerm): Implement getKeys
    // TODO(springerm): Implement getValues

    public int getLength() {
      return map.size();
    }

    public boolean getIsEmpty() {
      return map.isEmpty();
    }

    public boolean getIsNotEmpty() {
      return !map.isEmpty();
    }


    // --- Methods defined in Object ---

    public int getHashCode() {
      return map.hashCode();
    }


    // --- Additional methods defined in java.util.Map ---

    public Set<Map.Entry<K, V>> entrySet() {
      return map.entrySet();
    }

    public V get(Object key) {
      return map.get(key);
    }
    
    public int hashCode() {
      return map.hashCode();
    }

    public boolean isEmpty() {
      return map.isEmpty();
    }

    public Set<K> keySet() {
      return map.keySet();
    }

    public V put(K key, V value) {
      operatorAtPut(key, value);
      return value;
    }

    public void putAll(Map<? extends K, ? extends V> m) {
      map.putAll(m);
    }

    public int size() {
      return map.size();
    }

    public Collection<V> values() {
      return map.values();
    }
  }

}
