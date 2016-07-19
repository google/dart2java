#!/usr/bin/env dart
// Copyright (c) 2016, the Dart project authors.  Please see the AUTHORS file
// for details. All rights reserved. Use of this source code is governed by a
// BSD-style license that can be found in the LICENSE file.

/// Meta-test that runs all tests we have written.
import 'package:test/test.dart';

import 'codegen_test.dart' as codegen_test;

void main(List<String> argv) {
  group('codegen', () => codegen_test.main(argv));
}
