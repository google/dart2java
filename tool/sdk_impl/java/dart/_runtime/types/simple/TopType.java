package dart._runtime.types.simple;

class TopType extends Type {
  private TopType() {}

  static final TopType INSTANCE = new TopType();

  @Override
  protected boolean isSubtypeOfInterfaceType(InterfaceType other) {
    return false;
  }
}
