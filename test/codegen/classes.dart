class Base<A, B, C> {}
// class Der1<D, E, F> extends Base<D, E, F> {}  -- Not yet supported, since
//     supertype uses type parameters
// class Der2<D, E> extends Base<D, E, Base<D, E, D>> {}  -- Not yet supported,
//     since supertype uses type parameters
class Der3 extends Base<int, String, Map<Object, double>> {}

void main() {}
