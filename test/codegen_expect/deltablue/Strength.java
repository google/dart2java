package deltablue;

public class Strength extends dart._runtime.base.DartObject
{
    public static dart._runtime.types.simple.InterfaceTypeInfo dart2java$typeInfo = new dart._runtime.types.simple.InterfaceTypeInfo("file:///usr/local/google/home/andrewkrieger/ddc-java/gen/codegen_tests/deltablue.dart", "Strength");
    static {
      deltablue.Strength.dart2java$typeInfo.superclass = new dart._runtime.types.simple.InterfaceTypeExpr(dart._runtime.helpers.ObjectHelper.dart2java$typeInfo);
    }
    public java.lang.Integer value = null;
    public java.lang.String name = null;
  
    public Strength(java.lang.Integer value, java.lang.String name)
    {
      super((dart._runtime.helpers.ConstructorHelper.EmptyConstructorMarker) null);
      this._constructor(value, name);
    }
    public Strength(dart._runtime.helpers.ConstructorHelper.EmptyConstructorMarker arg)
    {
      super(arg);
    }
  
    protected void _constructor(java.lang.Integer value, java.lang.String name)
    {
      this.value = value;
      this.name = name;
      super._constructor();
    }
    public deltablue.Strength nextWeaker()
    {
      return dart._runtime.base.DartList.Generic._fromArguments(deltablue.Strength.class, deltablue.__TopLevel.STRONG_PREFERRED, deltablue.__TopLevel.PREFERRED, deltablue.__TopLevel.STRONG_DEFAULT, deltablue.__TopLevel.NORMAL, deltablue.__TopLevel.WEAK_DEFAULT, deltablue.__TopLevel.WEAKEST).operatorAt(this.getValue());
    }
    public static java.lang.Boolean stronger(deltablue.Strength s1, deltablue.Strength s2)
    {
      return dart._runtime.helpers.IntegerHelper.operatorLess(s1.getValue(), s2.getValue());
    }
    public static java.lang.Boolean weaker(deltablue.Strength s1, deltablue.Strength s2)
    {
      return dart._runtime.helpers.IntegerHelper.operatorGreater(s1.getValue(), s2.getValue());
    }
    public static deltablue.Strength weakest(deltablue.Strength s1, deltablue.Strength s2)
    {
      return (deltablue.Strength.weaker(s1, s2)) ? (s1) : (s2);
    }
    public static deltablue.Strength strongest(deltablue.Strength s1, deltablue.Strength s2)
    {
      return (deltablue.Strength.stronger(s1, s2)) ? (s1) : (s2);
    }
    public java.lang.Integer getValue()
    {
      return this.value;
    }
    public java.lang.String getName()
    {
      return this.name;
    }
}
