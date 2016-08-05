import 'dart:_internal';

@JavaClass("java.util.HashMap")
class JavaHashMap {
  external static HashMap();

  @JavaMethod("get")
  external Object at(Object key);

  external Object put(Object key, Object value);
}

class VariableHolder {
  static JavaHashMap javaMap = new JavaHashMap();

  static void setValue(Object key, Object value) {
    javaMap.put(key, value);
  }

  static Object getValue(Object key) {
    return javaMap.at(key);
  }
}

bool testCase() {
  // TODO(springerm): Make this work for types other than Object
  var key = new Object();
  var value = new Object();

  VariableHolder.setValue(key, value);
  return VariableHolder.getValue(key) == value;
}