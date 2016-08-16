package dart._runtime.types.simple;

/**
 * Representation of a Dart interface type.
 */
public class InterfaceType extends Type {
  final InterfaceTypeInfo info;
  final InterfaceType supertype;
  final InterfaceType mixin;
  final InterfaceType[] interfaces;
  final Type[] actualTypeParams;

  /**
   * The type environment for instances of this type.
   * <p>
   * This {@link TypeEnvironment} can resolve the type variables for this class, as well as those of
   * its superclass and mixin (if any).
   */
  public final TypeEnvironment env;

  InterfaceType(InterfaceTypeInfo info, InterfaceType supertype, InterfaceType mixin,
      InterfaceType[] interfaces, Type[] actualTypeParams) {
    this.info = info;
    this.supertype = supertype;
    this.mixin = mixin;
    this.interfaces = interfaces;
    this.actualTypeParams = actualTypeParams;
    TypeEnvironment superEnv = supertype != null ? supertype.env : TypeEnvironment.ROOT;
    if (this.mixin != null) {
      superEnv = superEnv.extend(this.mixin.info.typeVariables, this.mixin.actualTypeParams);
    }
    this.env = superEnv.extend(this.info.typeVariables, this.actualTypeParams);
  }

  @Override
  protected boolean isSubtypeOfInterfaceType(InterfaceType other) {
    // TODO(andrewkrieger): Implement this.
    return false;
  }
}
