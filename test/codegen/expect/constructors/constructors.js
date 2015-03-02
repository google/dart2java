var constructors;
(function(exports) {
  'use strict';
  class A extends dart.Object {
  }
  class B extends dart.Object {
    B() {
    }
  }
  class C extends dart.Object {
    C$named() {
    }
  }
  dart.defineNamedConstructor(C, 'named');
  class C2 extends C {
    C2$named() {
      super.C$named();
    }
  }
  dart.defineNamedConstructor(C2, 'named');
  class D extends dart.Object {
    D() {
    }
    D$named() {
    }
  }
  dart.defineNamedConstructor(D, 'named');
  class E extends dart.Object {
    E(name) {
      this.name = name;
    }
  }
  class F extends E {
    F(name) {
      super.E(name);
    }
  }
  class G extends dart.Object {
    G(p1) {
      if (p1 === void 0)
        p1 = null;
    }
  }
  class H extends dart.Object {
    H(opt$) {
      let p1 = opt$.p1 === void 0 ? null : opt$.p1;
    }
  }
  class I extends dart.Object {
    I() {
      this.name = 'default';
    }
    I$named(name) {
      this.name = name;
    }
  }
  dart.defineNamedConstructor(I, 'named');
  class J extends dart.Object {
    J() {
      this.initialized = true;
      this.nonInitialized = null;
    }
  }
  class K extends dart.Object {
    K() {
      this.s = 'a';
    }
    K$withS(s) {
      this.s = s;
    }
  }
  dart.defineNamedConstructor(K, 'withS');
  class L extends dart.Object {
    L(foo) {
      this.foo = foo;
    }
  }
  class M extends L {
    M$named(x) {
      super.L(x + 42);
    }
  }
  dart.defineNamedConstructor(M, 'named');
  class N extends M {
    N$named(y) {
      super.M$named(y + 100);
    }
  }
  dart.defineNamedConstructor(N, 'named');
  class P extends N {
    P(z) {
      super.N$named(z + 9000);
    }
    P$foo(x) {
      this.P(x + 42);
    }
    P$bar() {
      this.P$foo(1);
    }
  }
  dart.defineNamedConstructor(P, 'foo');
  dart.defineNamedConstructor(P, 'bar');
  // Exports:
  exports.A = A;
  exports.B = B;
  exports.C = C;
  exports.C2 = C2;
  exports.D = D;
  exports.E = E;
  exports.F = F;
  exports.G = G;
  exports.H = H;
  exports.I = I;
  exports.J = J;
  exports.K = K;
  exports.L = L;
  exports.M = M;
  exports.N = N;
  exports.P = P;
})(constructors || (constructors = {}));
