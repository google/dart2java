part of dart.core;

abstract class int {
  int operator -();
  int operator -(int other);
  int operator +(int other);
  int operator *(int other);
  bool operator <(int other);
  bool operator ==(int other);
  String toString();
  int abs();
  int get sign;
}
