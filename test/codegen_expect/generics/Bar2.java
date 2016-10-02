package generics;

public class Bar2<A, B> extends dart._runtime.base.DartObject implements generics.Bar2_interface<A, B>
{
    public static dart._runtime.types.simple.InterfaceTypeInfo dart2java$typeInfo = new dart._runtime.types.simple.InterfaceTypeInfo(new java.lang.String[] {"A", "B"}, generics.Bar2.class, generics.Bar2_interface.class);
    public static dart._runtime.types.simple.FunctionTypeInfo factory$newBar$typeInfo = new dart._runtime.types.simple.FunctionTypeInfo("generics.Bar2::factory$newBar", new java.lang.String[] {"A", "B"});
    private static dart._runtime.types.simple.TypeExpr dart2java$typeExpr_Bar2$A = generics.Bar2.dart2java$typeInfo.typeVariables[0];
    private static dart._runtime.types.simple.TypeExpr dart2java$typeExpr_Bar2$B = generics.Bar2.dart2java$typeInfo.typeVariables[1];
    private static dart._runtime.types.simple.InterfaceTypeExpr dart2java$typeExpr_Object = new dart._runtime.types.simple.InterfaceTypeExpr(dart._runtime.helpers.ObjectHelper.dart2java$typeInfo);
    static {
      generics.Bar2.dart2java$typeInfo.superclass = dart2java$typeExpr_Object;
    }
    public A varA;
    public B varB;
  
    public Bar2(dart._runtime.helpers.ConstructorHelper.EmptyConstructorMarker arg, dart._runtime.types.simple.Type type)
    {
      super(arg, type);
    }
  
    public A bar_Bar2(A a, B b)
    {
      final dart._runtime.types.simple.TypeEnvironment dart2java$localTypeEnv = this.dart2java$type.env;
      dart2java$localTypeEnv.evaluate(dart2java$typeExpr_Bar2$B).check(b);
      dart2java$localTypeEnv.evaluate(dart2java$typeExpr_Bar2$A).check(a);
      return this.getVarA_Bar2();
    }
    public void _constructor()
    {
      final dart._runtime.types.simple.TypeEnvironment dart2java$localTypeEnv = this.dart2java$type.env;
      super._constructor();
    }
    public A getVarA_Bar2()
    {
      return this.varA;
    }
    public B getVarB_Bar2()
    {
      return this.varB;
    }
    public A setVarA_Bar2(A value)
    {
      this.varA = value;
      return value;
    }
    public B setVarB_Bar2(B value)
    {
      this.varB = value;
      return value;
    }
    public A getVarA()
    {
      return this.getVarA_Bar2();
    }
    public B getVarB()
    {
      return this.getVarB_Bar2();
    }
    public A setVarA(A value)
    {
      return this.setVarA_Bar2(((A) value));
    }
    public B setVarB(B value)
    {
      return this.setVarB_Bar2(((B) value));
    }
    public A bar(A a, B b)
    {
      return this.bar_Bar2(((A) a), ((B) b));
    }
    public static <A, B> generics.Bar2_interface<A, B> factory$newBar(dart._runtime.types.simple.TypeEnvironment dart2java$localTypeEnv, A a, B b)
    {
      dart2java$localTypeEnv.evaluate(generics.Bar2.factory$newBar$typeInfo.typeVariables[1]).check(b);
      dart2java$localTypeEnv.evaluate(generics.Bar2.factory$newBar$typeInfo.typeVariables[0]).check(a);
      generics.Bar2_interface<A, B> result = ((generics.Bar2_interface) generics.Bar2._new_Bar2$(dart2java$localTypeEnv.evaluate(new dart._runtime.types.simple.InterfaceTypeExpr(generics.Bar2.dart2java$typeInfo, new dart._runtime.types.simple.TypeExpr[] {generics.Bar2.factory$newBar$typeInfo.typeVariables[0], generics.Bar2.factory$newBar$typeInfo.typeVariables[1]}))));
      result.setVarA_Bar2(a);
      result.setVarB_Bar2(b);
      return ((generics.Bar2_interface) result);
    }
    public static <A, B> generics.Bar2_interface _new_Bar2$(dart._runtime.types.simple.Type type)
    {
      dart._runtime.types.simple.Type cached_0_int = null;
      dart._runtime.types.simple.Type cached_0_boolean = null;
      dart._runtime.types.simple.Type cached_0_double = null;
      dart._runtime.types.simple.Type cached_1_int = null;
      dart._runtime.types.simple.Type cached_1_boolean = null;
      dart._runtime.types.simple.Type cached_1_double = null;
      generics.Bar2 result;
      result = new generics.Bar2(((dart._runtime.helpers.ConstructorHelper.EmptyConstructorMarker) null), type);
      result._constructor();
      return result;
    }
}
