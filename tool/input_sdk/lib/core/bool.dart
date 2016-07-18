part of dart.core;

class bool {
  String toString() {
    // TODO(andrewkrieger): Change back to ternary expression once the compiler
    // supports ternary expressions.
    if (this)
      return "true";
    else
      return "false";
  }
}
