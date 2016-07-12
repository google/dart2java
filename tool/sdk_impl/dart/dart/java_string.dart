part of dart._runtime;

@JavaClass("java.lang.String")
class JavaString implements String {
  
  @JavaCall("dart._runtime.helpers.StringHelper.operatorAt")
  external String operator [](int index);

  @JavaCall("dart._runtime.helpers.StringHelper.operatorPlus")
  external String operator +(String other);

  @JavaCall("dart._runtime.helpers.StringHelper.getLength")
  external int get length;

}