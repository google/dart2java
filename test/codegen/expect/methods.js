var methods = dart.defineLibrary(methods, {});
var core = dart.import(core);
(function(exports, core) {
  'use strict';
  let _c = Symbol('_c');
  class A extends core.Object {
    A() {
      this[_c] = 3;
    }
    x() {
      return 42;
    }
    y(a) {
      return a;
    }
    z(b) {
      if (b === void 0)
        b = null;
      return b;
    }
    zz(b) {
      if (b === void 0)
        b = 0;
      return b;
    }
    w(a, opts) {
      let b = opts && 'b' in opts ? opts.b : null;
      return dart.notNull(a) + dart.notNull(b);
    }
    ww(a, opts) {
      let b = opts && 'b' in opts ? opts.b : 0;
      return dart.notNull(a) + dart.notNull(b);
    }
    get a() {
      return this.x();
    }
    set b(b) {}
    get c() {
      return this[_c];
    }
    set c(c) {
      this[_c] = c;
    }
  }
  dart.setSignature(A, {
    methods: () => ({
      x: dart.functionType(core.int, []),
      y: dart.functionType(core.int, [core.int]),
      z: dart.functionType(core.int, [], [core.num]),
      zz: dart.functionType(core.int, [], [core.int]),
      w: dart.functionType(core.int, [core.int], {b: core.num}),
      ww: dart.functionType(core.int, [core.int], {b: core.int})
    })
  });
  class Bar extends core.Object {
    call(x) {
      return core.print(`hello from ${x}`);
    }
  }
  dart.setSignature(Bar, {
    methods: () => ({call: dart.functionType(dart.dynamic, [dart.dynamic])})
  });
  class Foo extends core.Object {
    Foo() {
      this.bar = new Bar();
    }
  }
  dart.setSignature(Foo, {});
  function test() {
    let f = new Foo();
    dart.dcall(f.bar, "Bar's call method!");
    let a = new A();
    let g = a.x.bind(a);
    let aa = new A();
    let h = dart.dload(aa, 'x');
  }
  dart.fn(test);
  // Exports:
  exports.A = A;
  exports.Bar = Bar;
  exports.Foo = Foo;
  exports.test = test;
})(methods, core);
