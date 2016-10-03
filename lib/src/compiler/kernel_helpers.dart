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

import 'package:kernel/ast.dart' as dart;
import 'package:kernel/type_algebra.dart' as dart_ts;

/// The result of a static lookup.
///
/// This class is used when looking up a member for a (dynamic) method
/// invocation or PropertyGet/PropertySet using the receiver's static type.
/// The class contains a reference to the member and the generically fully
/// instantiated class.
///
/// For example:
/// class Bar<A, B> {
///   void foo(A a, B b) { ... }
/// }
/// class Foo<C> extends Bar<int, C> { }
///
/// The lookup for `foo` on an object of static type Foo<double> returns
/// LookupResult(Procedure for foo, InterfaceType for Bar<int, double>)
///
/// Also see [findMemberInClassHierarchy].
class LookupResult<M> {
  M member;

  dart.InterfaceType receiverType;

  dart.Class receiverClass;

  LookupResult(this.member, this.receiverType, this.receiverClass);
}

/// Finds a [dart.Procedure] in a class and its superclasses.
/// Returns a list of all matchings (most specific one first).
List<LookupResult<dart.Procedure>> findAllProceduresInClassHierarchy(
    String name, dart.InterfaceType receiverType,
    {dart.ProcedureKind kind, bool onlySuper: false}) {
  return findMemberInClassHierarchy(
      receiverType,
      (c) => c.procedures.where(
          (p) => p.name.name == name && (kind == null || p.kind == kind)),
      onlySuper: onlySuper);
}

/// Finds a [dart.Procedure] in a class and its superclasses.
LookupResult<dart.Procedure> findProcedureInClassHierarchy(
    String name, dart.InterfaceType receiverType,
    {dart.ProcedureKind kind, bool onlySuper: false}) {
  List<LookupResult<dart.Procedure>> result = findAllProceduresInClassHierarchy(
      name, receiverType,
      kind: kind, onlySuper: onlySuper);

  return result.isNotEmpty ? result.first : null;
}

/// Finds a [dart.Field] in a class and its superclasses.
/// Returns a list of all matchings (most specific one first).
List<LookupResult<dart.Field>> findAllFieldsInClassHierarchy(
    String name, dart.InterfaceType receiverType,
    {bool onlySuper: false}) {
  return findMemberInClassHierarchy(
      receiverType, (c) => c.fields.where((f) => f.name.name == name),
      onlySuper: onlySuper);
}

/// Finds a [dart.Field] in a class and its superclasses.
LookupResult<dart.Field> findFieldInClassHierarchy(
    String name, dart.InterfaceType receiverType,
    {bool onlySuper: false}) {
  List<LookupResult<dart.Field>> result =
      findAllFieldsInClassHierarchy(name, receiverType, onlySuper: onlySuper);

  return result.isNotEmpty ? result.first : null;
}

/// Finds a member in a class, its superclasses and its declared interfaces.
/// Returns a list of all matchings (most specific one first).
///
/// For more documentation, see comment of [LookupResult].
// TODO(springerm): Check mixins
List<LookupResult<M>> findMemberInClassHierarchy/*<M>*/(
    dart.InterfaceType receiverType, Iterable<M> query(dart.Class class_),
    {bool onlySuper: false}) {
  dart.Class nextClass = receiverType.classNode;
  dart.InterfaceType nextReceiverType = receiverType;

  var result = new List<LookupResult<M>>();
  bool isFirstClass = true;

  do {
    if (!onlySuper || !isFirstClass) {
      // Check class
      Iterable<M> matches = query(nextClass);
      if (matches.isNotEmpty) {
        result.add(
            new LookupResult<M>(matches.single, nextReceiverType, nextClass));
      }
    }

    isFirstClass = false;

    // TODO(springerm): The following code does type inference: When looking
    // for a method with a certain name we also want to know the class's
    // type, i.e., the type of the class where the method is defined. All
    // type arguments should be expressed in terms of the type arguments in
    // [receiverType]. This step might be obsolute when using a newer version
    // of Kernel.

    // Check implemented interfaces
    for (var implementedInterface in nextClass.implementedTypes) {
      List<dart.DartType> superTypeArguments = implementedInterface
          .typeArguments
          .map((t) => nextClass.typeParameters
                  .contains(t) // of form "class Foo<T> extends Bar<T>"
              ? nextReceiverType.typeArguments[
                  nextClass.typeParameters.indexOf(t as dart.TypeParameter)]
              : t)
          .toList();
      var interfaceClass = implementedInterface.classNode;
      var interfaceType = dart_ts.substitutePairwise(interfaceClass.thisType,
          interfaceClass.typeParameters, superTypeArguments);

      var recursiveResult =
          findMemberInClassHierarchy/*<M>*/(interfaceType, query);
      if (recursiveResult != null) {
        result.addAll(recursiveResult);
      }
    }

    // Check next class
    if (nextClass.supertype == null) {
      return result;
    }

    List<dart.DartType> superTypeArguments = nextClass.supertype.typeArguments
        .map((t) => nextClass.typeParameters
                .contains(t) // of form "class Foo<T> extends Bar<T>"
            ? nextReceiverType.typeArguments[
                nextClass.typeParameters.indexOf(t as dart.TypeParameter)]
            : t)
        .toList();
    nextClass = nextClass.superclass;
    nextReceiverType = dart_ts.substitutePairwise(
        nextClass.thisType, nextClass.typeParameters, superTypeArguments);
  } while (true);
}
