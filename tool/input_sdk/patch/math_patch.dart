import 'dart:_internal' show JavaCall, JavaMethod;

@patch
@JavaCall("dart._runtime.helpers.MathHelper.atan2")
external double atan2(num a, num b);

@patch
@JavaCall("dart._runtime.helpers.MathHelper.pow")
external num pow(num x, num exponent);

@patch
@JavaCall("dart._runtime.helpers.MathHelper.sin")
external double sin(num x);

@patch
@JavaCall("dart._runtime.helpers.MathHelper.cos")
external double cos(num x);

@patch
@JavaCall("dart._runtime.helpers.MathHelper.tan")
external double tan(num x);

@patch
@JavaCall("dart._runtime.helpers.MathHelper.acos")
external double acos(num x);

@patch
@JavaCall("dart._runtime.helpers.MathHelper.asin")
external double asin(num x);

@patch
@JavaCall("dart._runtime.helpers.MathHelper.atan")
external double atan(num x);

@patch
@JavaCall("dart._runtime.helpers.MathHelper.sqrt")
external double sqrt(num x);

@patch
@JavaCall("dart._runtime.helpers.MathHelper.exp")
external double exp(num x);

@patch
@JavaCall("dart._runtime.helpers.MathHelper.log")
external double log(num x);

@patch
@JavaCall("dart._runtime.helpers.MathHelper.min")
external double min(num a, num b);

@patch
@JavaCall("dart._runtime.helpers.MathHelper.max")
external double max(num a, num b);
