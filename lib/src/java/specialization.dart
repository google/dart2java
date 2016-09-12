import 'types.dart';

/// Builds a list of all possible specializations according to the
/// configuration (static field) in [TypeSpecialization].
Iterable<TypeSpecialization> buildAllSpecializations(int numTypes) {
  if (numTypes > TypeSpecialization.specializationThreshold) {
    // Only generate the fully generic case
    return [new TypeSpecialization.fullyGeneric(numTypes)];
  }

  var result = [<PrimitiveType>[]];

  for (int i = 0; i < numTypes; i++) {
    var newResult = <List<PrimitiveType>>[];

    for (var spec in result) {
      for (var type in TypeSpecialization.specializedTypes) {
        List<JavaType> newSpec = spec.toList(); // create a copy
        newSpec.add(type);
        newResult.add(newSpec);
      }

      // Unspecialized (null) case
      List<JavaType> newSpec = spec.toList(); // create a copy
      newSpec.add(null);
      newResult.add(newSpec);
    }

    result = newResult;
  }

  return result.map((l) => new TypeSpecialization.fromTypes(l));
}

class TypeSpecialization {
  /// Types for which specializations are generated.
  static final List<PrimitiveType> specializedTypes = [
    JavaType.int_,
    JavaType.boolean,
    JavaType.double_
  ];

  /// Maximum number of generic type parameters for which specializations
  /// are generated.
  static final specializationThreshold = 2;

  /// A list of primitive Java types for this specialization. [null] indicates
  /// an unspecialized "generic" (Object) type parameter.
  ///
  /// For example:
  /// Map<int, bool>        [int_, boolean]
  /// Map<Object, bool>     [null, boolean]
  /// Map<String, double>   [null, double_]
  /// Map<Object, String>   [null, null]
  /// Map<bool, T>          [bool, *] (depends on what T is)
  List<PrimitiveType> types;

  TypeSpecialization.fromTypes(this.types);

  TypeSpecialization.fullyGeneric(int numTypes) {
    this.types = new List.filled(numTypes, null);
  }

  /// Determines if a specialization is fully generic, i.e., all types are
  /// unspecialized ([null] in [types]).
  bool get isFullyGeneric => types.every((t) => t == null);

  /// Generates the suffix that should be appended to all class/interface
  /// names. Fully generic classes have an empty suffix.
  ///
  /// For example:
  /// Map<int, bool>        Map__int_boolean
  /// Map<Object, bool>     Map__gen_boolean
  /// Map<String, double>   Map__gen_double
  /// Map<Object, String>   Map
  /// Map<bool, T>          Map__boolean_* (depends on what T)
  String get classNameSuffix => isFullyGeneric
      ? ""
      : "__" + types.map((t) => t == null ? "generic" : t.toString()).join("_");

  String toString() {
    // Only for debug purposes
    return "<<${specializedTypes.join(", ")}>>";
  }
}
