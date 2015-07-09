dart_library.library('dart/js', null, /* Imports */[
  "dart_runtime/dart",
  'dart/core',
  'dart/collection',
  'dart/_js_helper'
], /* Lazy imports */[
], function(exports, dart, core, collection, _js_helper) {
  'use strict';
  let dartx = dart.dartx;
  dart.defineLazyProperties(exports, {
    get context() {
      return dart.as(_wrapToDart(dart.global), JsObject);
    }
  });
  let _jsObject = Symbol('_jsObject');
  class JsObject extends core.Object {
    _fromJs(jsObject) {
      this[_jsObject] = jsObject;
      dart.assert(this[_jsObject] != null);
    }
    static new(constructor, arguments$) {
      if (arguments$ === void 0)
        arguments$ = null;
      let ctor = constructor[_jsObject];
      if (arguments$ == null) {
        return dart.as(_wrapToDart(new ctor()), JsObject);
      }
      return dart.as(_wrapToDart(new ctor(...arguments$)), JsObject);
    }
    static fromBrowserObject(object) {
      if (dart.is(object, core.num) || typeof object == 'string' || typeof object == 'boolean' || object == null) {
        throw new core.ArgumentError("object cannot be a num, string, bool, or null");
      }
      return dart.as(_wrapToDart(_convertToJS(object)), JsObject);
    }
    static jsify(object) {
      if (!dart.is(object, core.Map) && !dart.is(object, core.Iterable)) {
        throw new core.ArgumentError("object must be a Map or Iterable");
      }
      return dart.as(_wrapToDart(JsObject._convertDataTree(object)), JsObject);
    }
    static _convertDataTree(data) {
      let _convertedObjects = collection.HashMap.identity();
      function _convert(o) {
        if (dart.notNull(_convertedObjects.containsKey(o))) {
          return _convertedObjects.get(o);
        }
        if (dart.is(o, core.Map)) {
          let convertedMap = {};
          _convertedObjects.set(o, convertedMap);
          for (let key of dart.as(dart.dload(o, 'keys'), core.Iterable)) {
            convertedMap[key] = _convert(dart.dindex(o, key));
          }
          return convertedMap;
        } else if (dart.is(o, core.Iterable)) {
          let convertedList = [];
          _convertedObjects.set(o, convertedList);
          convertedList[dartx.addAll](dart.as(dart.dsend(o, 'map', _convert), core.Iterable));
          return convertedList;
        } else {
          return _convertToJS(o);
        }
      }
      dart.fn(_convert);
      return _convert(data);
    }
    get(property) {
      if (!(typeof property == 'string') && !dart.is(property, core.num)) {
        throw new core.ArgumentError("property is not a String or num");
      }
      return _convertToDart(this[_jsObject][property]);
    }
    set(property, value) {
      if (!(typeof property == 'string') && !dart.is(property, core.num)) {
        throw new core.ArgumentError("property is not a String or num");
      }
      this[_jsObject][property] = _convertToJS(value);
    }
    get hashCode() {
      return 0;
    }
    ['=='](other) {
      return dart.is(other, JsObject) && this[_jsObject] === dart.dload(other, _jsObject);
    }
    hasProperty(property) {
      if (!(typeof property == 'string') && !dart.is(property, core.num)) {
        throw new core.ArgumentError("property is not a String or num");
      }
      return property in this[_jsObject];
    }
    deleteProperty(property) {
      if (!(typeof property == 'string') && !dart.is(property, core.num)) {
        throw new core.ArgumentError("property is not a String or num");
      }
      delete this[_jsObject][property];
    }
    instanceof(type) {
      return this[_jsObject] instanceof _convertToJS(type);
    }
    toString() {
      try {
        return String(this[_jsObject]);
      } catch (e) {
        return super.toString();
      }

    }
    callMethod(method, args) {
      if (args === void 0)
        args = null;
      if (!(typeof method == 'string') && !dart.is(method, core.num)) {
        throw new core.ArgumentError("method is not a String or num");
      }
      if (args != null)
        args = core.List.from(args[dartx.map](_convertToJS));
      let fn = this[_jsObject][method];
      if (!(fn instanceof Function)) {
        throw new core.NoSuchMethodError(this[_jsObject], core.Symbol.new(dart.as(method, core.String)), args, dart.map());
      }
      return _convertToDart(fn.apply(this[_jsObject], args));
    }
  }
  dart.defineNamedConstructor(JsObject, '_fromJs');
  dart.setSignature(JsObject, {
    constructors: () => ({
      _fromJs: [JsObject, [dart.dynamic]],
      new: [JsObject, [JsFunction], [core.List]],
      fromBrowserObject: [JsObject, [dart.dynamic]],
      jsify: [JsObject, [dart.dynamic]]
    }),
    methods: () => ({
      get: [dart.dynamic, [dart.dynamic]],
      set: [dart.dynamic, [dart.dynamic, dart.dynamic]],
      hasProperty: [core.bool, [dart.dynamic]],
      deleteProperty: [dart.void, [dart.dynamic]],
      instanceof: [core.bool, [JsFunction]],
      callMethod: [dart.dynamic, [dart.dynamic], [core.List]]
    }),
    statics: () => ({_convertDataTree: [dart.dynamic, [dart.dynamic]]}),
    names: ['_convertDataTree']
  });
  class JsFunction extends JsObject {
    static withThis(f) {
      return new JsFunction._fromJs(function() {
        let args = [_convertToDart(this)];
        for (let arg of arguments) {
          args.push(_convertToDart(arg));
        }
        return _convertToJS(f(...args));
      });
    }
    _fromJs(jsObject) {
      super._fromJs(jsObject);
    }
    apply(args, opts) {
      let thisArg = opts && 'thisArg' in opts ? opts.thisArg : null;
      return _convertToDart(this[_jsObject].apply(_convertToJS(thisArg), args == null ? null : core.List.from(args[dartx.map](_convertToJS))));
    }
  }
  dart.defineNamedConstructor(JsFunction, '_fromJs');
  dart.setSignature(JsFunction, {
    constructors: () => ({
      withThis: [JsFunction, [core.Function]],
      _fromJs: [JsFunction, [dart.dynamic]]
    }),
    methods: () => ({apply: [dart.dynamic, [core.List], {thisArg: dart.dynamic}]})
  });
  let _checkIndex = Symbol('_checkIndex');
  let _checkInsertIndex = Symbol('_checkInsertIndex');
  let JsArray$ = dart.generic(function(E) {
    class JsArray extends dart.mixin(JsObject, collection.ListMixin$(E)) {
      JsArray() {
        super._fromJs([]);
      }
      from(other) {
        super._fromJs((() => {
          let _ = [];
          _[dartx.addAll](other[dartx.map](dart.as(_convertToJS, __CastType0)));
          return _;
        })());
      }
      _fromJs(jsObject) {
        super._fromJs(jsObject);
      }
      [_checkIndex](index) {
        if (typeof index == 'number' && (dart.notNull(index) < 0 || dart.notNull(index) >= dart.notNull(this.length))) {
          throw new core.RangeError.range(index, 0, this.length);
        }
      }
      [_checkInsertIndex](index) {
        if (typeof index == 'number' && (dart.notNull(index) < 0 || dart.notNull(index) >= dart.notNull(this.length) + 1)) {
          throw new core.RangeError.range(index, 0, this.length);
        }
      }
      static _checkRange(start, end, length) {
        if (dart.notNull(start) < 0 || dart.notNull(start) > dart.notNull(length)) {
          throw new core.RangeError.range(start, 0, length);
        }
        if (dart.notNull(end) < dart.notNull(start) || dart.notNull(end) > dart.notNull(length)) {
          throw new core.RangeError.range(end, start, length);
        }
      }
      get(index) {
        if (dart.is(index, core.num) && dart.equals(index, dart.dsend(index, 'toInt'))) {
          this[_checkIndex](dart.as(index, core.int));
        }
        return dart.as(super.get(index), E);
      }
      set(index, value) {
        dart.as(value, E);
        if (dart.is(index, core.num) && dart.equals(index, dart.dsend(index, 'toInt'))) {
          this[_checkIndex](dart.as(index, core.int));
        }
        super.set(index, value);
      }
      get length() {
        let len = this[_jsObject].length;
        if (typeof len === "number" && len >>> 0 === len) {
          return len;
        }
        throw new core.StateError('Bad JsArray length');
      }
      set length(length) {
        super.set('length', length);
      }
      add(value) {
        dart.as(value, E);
        this.callMethod('push', [value]);
      }
      addAll(iterable) {
        dart.as(iterable, core.Iterable$(E));
        let list = iterable instanceof Array ? iterable : core.List.from(iterable);
        this.callMethod('push', dart.as(list, core.List));
      }
      insert(index, element) {
        dart.as(element, E);
        this[_checkInsertIndex](index);
        this.callMethod('splice', [index, 0, element]);
      }
      removeAt(index) {
        this[_checkIndex](index);
        return dart.as(dart.dindex(this.callMethod('splice', [index, 1]), 0), E);
      }
      removeLast() {
        if (this.length == 0)
          throw new core.RangeError(-1);
        return dart.as(this.callMethod('pop'), E);
      }
      removeRange(start, end) {
        JsArray$()._checkRange(start, end, this.length);
        this.callMethod('splice', [start, dart.notNull(end) - dart.notNull(start)]);
      }
      setRange(start, end, iterable, skipCount) {
        dart.as(iterable, core.Iterable$(E));
        if (skipCount === void 0)
          skipCount = 0;
        JsArray$()._checkRange(start, end, this.length);
        let length = dart.notNull(end) - dart.notNull(start);
        if (length == 0)
          return;
        if (dart.notNull(skipCount) < 0)
          throw new core.ArgumentError(skipCount);
        let args = [start, length];
        args[dartx.addAll](iterable[dartx.skip](skipCount)[dartx.take](length));
        this.callMethod('splice', args);
      }
      sort(compare) {
        if (compare === void 0)
          compare = null;
        dart.as(compare, dart.functionType(core.int, [E, E]));
        this.callMethod('sort', compare == null ? [] : [compare]);
      }
    }
    dart.defineNamedConstructor(JsArray, 'from');
    dart.defineNamedConstructor(JsArray, '_fromJs');
    dart.setSignature(JsArray, {
      constructors: () => ({
        JsArray: [JsArray$(E), []],
        from: [JsArray$(E), [core.Iterable$(E)]],
        _fromJs: [JsArray$(E), [dart.dynamic]]
      }),
      methods: () => ({
        [_checkIndex]: [dart.dynamic, [core.int]],
        [_checkInsertIndex]: [dart.dynamic, [core.int]],
        get: [E, [dart.dynamic]],
        set: [dart.void, [dart.dynamic, E]],
        add: [dart.void, [E]],
        addAll: [dart.void, [core.Iterable$(E)]],
        insert: [dart.void, [core.int, E]],
        removeAt: [E, [core.int]],
        removeLast: [E, []],
        setRange: [dart.void, [core.int, core.int, core.Iterable$(E)], [core.int]],
        sort: [dart.void, [], [dart.functionType(core.int, [E, E])]]
      }),
      statics: () => ({_checkRange: [dart.dynamic, [core.int, core.int, core.int]]}),
      names: ['_checkRange']
    });
    dart.defineExtensionMembers(JsArray, [
      'get',
      'set',
      'add',
      'addAll',
      'insert',
      'removeAt',
      'removeLast',
      'removeRange',
      'setRange',
      'sort',
      'length',
      'length'
    ]);
    return JsArray;
  });
  let JsArray = JsArray$();
  function _isBrowserType(o) {
    return o instanceof Blob || o instanceof Event || window.KeyRange && o instanceof KeyRange || o instanceof ImageData || o instanceof Node || window.TypedData && o instanceof TypedData || o instanceof Window;
  }
  dart.fn(_isBrowserType, core.bool, [dart.dynamic]);
  let _dartObj = Symbol('_dartObj');
  class _DartObject extends core.Object {
    _DartObject(dartObj) {
      this[_dartObj] = dartObj;
    }
  }
  dart.setSignature(_DartObject, {
    constructors: () => ({_DartObject: [_DartObject, [dart.dynamic]]})
  });
  function _convertToJS(o) {
    if (o == null || typeof o == 'string' || dart.is(o, core.num) || typeof o == 'boolean' || dart.notNull(_isBrowserType(o))) {
      return o;
    } else if (dart.is(o, core.DateTime)) {
      return _js_helper.Primitives.lazyAsJsDate(o);
    } else if (dart.is(o, JsObject)) {
      return dart.dload(o, _jsObject);
    } else if (dart.is(o, core.Function)) {
      return _putIfAbsent(exports._jsProxies, o, _wrapDartFunction);
    } else {
      return _putIfAbsent(exports._jsProxies, o, dart.fn(o => new _DartObject(o), _DartObject, [dart.dynamic]));
    }
  }
  dart.fn(_convertToJS);
  function _wrapDartFunction(f) {
    let wrapper = function() {
      let args = Array.prototype.map.call(arguments, _convertToDart);
      return _convertToJS(f(...args));
    };
    dart.dsetindex(exports._dartProxies, wrapper, f);
    return wrapper;
  }
  dart.fn(_wrapDartFunction);
  function _convertToDart(o) {
    if (o == null || typeof o == "string" || typeof o == "number" || typeof o == "boolean" || dart.notNull(_isBrowserType(o))) {
      return o;
    } else if (o instanceof Date) {
      let ms = o.getTime();
      return new core.DateTime.fromMillisecondsSinceEpoch(ms);
    } else if (dart.is(o, _DartObject)) {
      return dart.dload(o, _dartObj);
    } else {
      return _putIfAbsent(exports._dartProxies, o, _wrapToDart);
    }
  }
  dart.fn(_convertToDart, core.Object, [dart.dynamic]);
  function _wrapToDart(o) {
    if (typeof o == "function") {
      return new JsFunction._fromJs(o);
    }
    if (o instanceof Array) {
      return new JsArray._fromJs(o);
    }
    return new JsObject._fromJs(o);
  }
  dart.fn(_wrapToDart);
  dart.defineLazyProperties(exports, {
    get _dartProxies() {
      return new WeakMap();
    },
    get _jsProxies() {
      return new WeakMap();
    }
  });
  function _putIfAbsent(weakMap, o, getValue) {
    let value = weakMap.get(o);
    if (value == null) {
      value = dart.dcall(getValue, o);
      weakMap.set(o, value);
    }
    return value;
  }
  dart.fn(_putIfAbsent, core.Object, [dart.dynamic, dart.dynamic, dart.functionType(dart.dynamic, [dart.dynamic])]);
  let __CastType0$ = dart.generic(function(E) {
    let __CastType0 = dart.typedef('__CastType0', () => dart.functionType(dart.dynamic, [E]));
    return __CastType0;
  });
  let __CastType0 = __CastType0$();
  // Exports:
  exports.JsObject = JsObject;
  exports.JsFunction = JsFunction;
  exports.JsArray$ = JsArray$;
  exports.JsArray = JsArray;
});