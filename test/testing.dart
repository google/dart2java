// Copyright (c) 2016, the Dart project authors.  Please see the AUTHORS file
// for details. All rights reserved. Use of this source code is governed by a
// BSD-style license that can be found in the LICENSE file.

import 'dart:mirrors';
import 'package:path/path.dart' as path;

final String testDirectory =
  path.dirname((reflectClass(_TestUtils).owner as LibraryMirror).uri.path);

/// The local path to the root directory of the dart2java repo.
final String repoDirectory = path.dirname(testDirectory);

class _TestUtils {}
