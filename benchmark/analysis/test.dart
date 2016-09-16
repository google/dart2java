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

import 'measure.dart';
import 'package:test/test.dart';

main() {
  test_mean();
  test_variance();
}

void test_mean() {
  test('simple', ()
  {
    List<num> data = [1, 2, 3, 4, 5, 6];
    num m = mean(data);
    num e = 3.5;
    expect(m, equals(e));
  });

  test('zero-offset', () {
    List<num> data = [0, 0, 0, 1, 2, 3, 4, 5, 6];
    num m = mean(data);
    num e = 21 / 9;
    expect(m, equals(e));
  });

  test('negative', () {
    List<num> data = [-6, -5, -4, 0, 0, 0, 1, 2, 3];
    num m = mean(data);
    num e = -1;
    expect(m, equals(e));
  });

  test('repeating', () {
    List<num> data = [3, 3, 3];
    num m = mean(data);
    num e = 3;
    expect(m, equals(e));
  });

  test('empty-list', () {
    List<num> data = [];
    num m = mean(data);
    num e = 0;
    expect(m, equals(e));
  });

  test('one-element-list', () {
    List<num> data = [42];
    num m = mean(data);
    num e = 42;
    expect(m, equals(e));
  });
}

void test_variance() {
  test('simple', () {
    List<num> data = [1, 2, 3, 4, 5, 6];
    num v = variance(data);
    num e = 3.5;
    expect(v, equals(e));
  });

  test('zero-offset', () {
    List<num> data = [0, 0, 0, 1, 2, 3, 4, 5, 6];
    num v = variance(data);
    num e = 5.25;
    expect(v, equals(e));
  });

  test('negative', () {
    List<num> data = [-6, -5, -4, 0, 0, 0, 1, 2, 3];
    num v = variance(data);
    num e = 10.25;
    expect(v, equals(e));
  });

  test('repeating', () {
    List<num> data = [3, 3, 3];
    num v = variance(data);
    num e = 0;
    expect(v, equals(e));
  });

  test('empty-list', () {
    List<num> data = [];
    num v = variance(data);
    num e = 0;
    expect(v, equals(e));
  });

  test('one-element-list', () {
    List<num> data = [42];
    num v = variance(data);
    num e = 0;
    expect(v, equals(e));
  });
}
