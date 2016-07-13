part of dart._internal;

@JavaClass("java.lang.Integer")
class JavaInteger implements int {
  
  @JavaCall("dart._runtime.helpers.IntegerHelper.operatorMinusUnary")
  external int operator -();

  @JavaCall("dart._runtime.helpers.IntegerHelper.operatorMinus")
  external int operator -(int other);

  @JavaCall("dart._runtime.helpers.IntegerHelper.operatorPlus")
  external int operator +(int other);

  @JavaCall("dart._runtime.helpers.IntegerHelper.operatorStar")
  external int operator *(int other);

  @JavaCall("dart._runtime.helpers.IntegerHelper.operatorLess")
  external bool operator <(int other);

  @JavaCall("dart._runtime.helpers.IntegerHelper.operatorEqual")
  external bool operator ==(int other);
  

  @JavaCall("dart._runtime.helpers.IntegerHelper.toString")
  external String toString();

  int get sign {
    if (this < 0) {
      return -1;
    }
    else if (this == 0) {
      return 0;
    }
    else {
      return 1;
    }
  }

  int abs() {
    return sign * this;
  }

}
