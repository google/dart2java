package deltablue;

public class Strength extends dart._runtime.base.DartObject implements deltablue.Strength_interface
{
    public static dart._runtime.types.simple.InterfaceTypeInfo dart2java$typeInfo = new dart._runtime.types.simple.InterfaceTypeInfo(deltablue.Strength.class, deltablue.Strength_interface.class);
    static {
      deltablue.Strength.dart2java$typeInfo.superclass = new dart._runtime.types.simple.InterfaceTypeExpr(dart._runtime.helpers.ObjectHelper.dart2java$typeInfo);
    }
    public int value;
    public java.lang.String name;
  
    public Strength(dart._runtime.types.simple.Type type, int value, java.lang.String name)
    {
      super(((dart._runtime.helpers.ConstructorHelper.EmptyConstructorMarker) null), type);
      this._constructor(value, name);
    }
    public Strength(dart._runtime.helpers.ConstructorHelper.EmptyConstructorMarker arg, dart._runtime.types.simple.Type type)
    {
      super(arg, type);
    }
  
    protected void _constructor(int value, java.lang.String name)
    {
      final dart._runtime.types.simple.TypeEnvironment dart2java$localTypeEnv = this.dart2java$type.env;
      this.value = value;
      this.name = name;
      super._constructor();
    }
    public deltablue.Strength_interface nextWeaker()
    {
      final dart._runtime.types.simple.TypeEnvironment dart2java$localTypeEnv = this.dart2java$type.env;
      return dart._runtime.base.DartList.Generic._fromArguments(deltablue.Strength_interface.class, deltablue.__TopLevel.STRONG_PREFERRED, deltablue.__TopLevel.PREFERRED, deltablue.__TopLevel.STRONG_DEFAULT, deltablue.__TopLevel.NORMAL, deltablue.__TopLevel.WEAK_DEFAULT, deltablue.__TopLevel.WEAKEST).operatorAt(this.getValue());
    }
    public static boolean stronger(deltablue.Strength_interface s1, deltablue.Strength_interface s2)
    {
      final dart._runtime.types.simple.TypeEnvironment dart2java$localTypeEnv = dart._runtime.types.simple.TypeEnvironment.ROOT;
      return (s1.getValue() < s2.getValue());
    }
    public static boolean weaker(deltablue.Strength_interface s1, deltablue.Strength_interface s2)
    {
      final dart._runtime.types.simple.TypeEnvironment dart2java$localTypeEnv = dart._runtime.types.simple.TypeEnvironment.ROOT;
      return (s1.getValue() > s2.getValue());
    }
    public static deltablue.Strength_interface weakest(deltablue.Strength_interface s1, deltablue.Strength_interface s2)
    {
      final dart._runtime.types.simple.TypeEnvironment dart2java$localTypeEnv = dart._runtime.types.simple.TypeEnvironment.ROOT;
      return (deltablue.Strength.weaker(s1, s2)) ? (s1) : (s2);
    }
    public static deltablue.Strength_interface strongest(deltablue.Strength_interface s1, deltablue.Strength_interface s2)
    {
      final dart._runtime.types.simple.TypeEnvironment dart2java$localTypeEnv = dart._runtime.types.simple.TypeEnvironment.ROOT;
      return (deltablue.Strength.stronger(s1, s2)) ? (s1) : (s2);
    }
    public int getValue()
    {
      return this.value;
    }
    public java.lang.String getName()
    {
      return this.name;
    }
}
