part of dart.core;

abstract class String {
  String operator [](int index);
  String operator +(String other);
  int get length;
}
