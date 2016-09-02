#!/usr/bin/env dart
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

/// Meta-test that runs all tests we have written.
import 'package:test/test.dart';

import 'codegen_test.dart' as codegen_test;
import 'multifile_codegen_test.dart' as multifile_codegen_test;
import 'unit_test.dart' as unit_test;
import 'typesystem_test.dart' as typesystem_test;

void main(List<String> argv) {
  group('codegen', () => codegen_test.main(argv));
  group('codegen', () => multifile_codegen_test.main(argv));
  group('unit', () => unit_test.main(argv));
  group('typesystem', () => typesystem_test.main(argv));
}
