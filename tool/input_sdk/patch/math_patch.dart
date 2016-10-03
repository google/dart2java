// Copyright 2016, the Dart project authors.
// 
// Licensed under the Apache License, Version 2.0 (the "License");
// you may not use this file except in compliance with the License.
// You may obtain a copy of the License at
// 
//     http://www.apache.org/licenses/LICENSE-2.0
// 
// Unless required by applicable law or agreed to in writing, software
// distributed under the License is distributed on an "AS IS" BASIS,
// WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// See the License for the specific language governing permissions and
// limitations under the License.

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

@patch
abstract class Random {
  @patch
  @JavaCall("dart._runtime.helpers.MathHelper.newRandom")
  external factory Random([int seed = 17]);

  @patch
  @JavaCall("dart._runtime.helpers.MathHelper.newSecureRandom")
  external factory Random.secure();
}
