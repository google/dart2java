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

/**
 * Simple implementation of Dart type system.
 * <p>
 * This implementation attempts to focus on correctness first, without worrying much about
 * performance characteristics. Many results are cached, but we do not attempt to tune the caches.
 * The type data (such as class supertype declarations) are stored in straightforward formats, from
 * which it is obvious how to compute subtype results. However, there are no attempts made to
 * optimize these computations. The intention is to later add one or more alternate packages, such
 * as (say) {@code dart._runtime.types.optimized}, that are profiled, more performant, and
 * API-compatible with this package. Once an optimized replacement is implemented and tested,
 * switching to it should be as easy as changing the {@code import} declarations in generated Java
 * source files.
 */
package dart._runtime.types.simple;
