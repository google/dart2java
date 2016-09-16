package generics;

public class Foo1<T> extends dart._runtime.base.DartObject implements generics.Foo1_interface<T>
{
    public static dart._runtime.types.simple.InterfaceTypeInfo dart2java$typeInfo = new dart._runtime.types.simple.InterfaceTypeInfo(new java.lang.String[] {"T"}, generics.Foo1.class, generics.Foo1_interface.class);
    private static dart._runtime.types.simple.InterfaceTypeExpr dart2java$typeExpr_Object = new dart._runtime.types.simple.InterfaceTypeExpr(dart._runtime.helpers.ObjectHelper.dart2java$typeInfo);
    static {
      generics.Foo1.dart2java$typeInfo.superclass = dart2java$typeExpr_Object;
    }
    public T variable;
  
    public Foo1(dart._runtime.helpers.ConstructorHelper.EmptyConstructorMarker arg, dart._runtime.types.simple.Type type)
    {
      super(arg, type);
    }
  
    public T foo(T t)
    {
      final dart._runtime.types.simple.TypeEnvironment dart2java$localTypeEnv = this.dart2java$type.env;
      return t;
    }
    public void _constructor()
    {
      final dart._runtime.types.simple.TypeEnvironment dart2java$localTypeEnv = this.dart2java$type.env;
      super._constructor();
    }
    public T getVariable()
    {
      return this.variable;
    }
    public T setVariable(T value)
    {
      this.variable = value;
      return value;
    }
    public static generics.Foo1_interface _new(dart._runtime.types.simple.Type type)
    {
      generics.Foo1_interface result;
      dart._runtime.types.simple.Type cached_0_int = null;
      dart._runtime.types.simple.Type cached_0_boolean = null;
      dart._runtime.types.simple.Type cached_0_double = null;
      if ((true && ((((cached_0_int == null)) ? (cached_0_int = type.env.evaluate(generics.Foo1.dart2java$typeInfo.typeVariables[0])) : (cached_0_int)) == dart._runtime.helpers.IntegerHelper.type)))
      {
        result = new generics.Foo1__int(((dart._runtime.helpers.ConstructorHelper.EmptyConstructorMarker) null), type);
        result._constructor();
        return result;
      }
      if ((true && ((((cached_0_boolean == null)) ? (cached_0_boolean = type.env.evaluate(generics.Foo1.dart2java$typeInfo.typeVariables[0])) : (cached_0_boolean)) == dart._runtime.helpers.BoolHelper.type)))
      {
        result = new generics.Foo1__boolean(((dart._runtime.helpers.ConstructorHelper.EmptyConstructorMarker) null), type);
        result._constructor();
        return result;
      }
      if ((true && ((((cached_0_double == null)) ? (cached_0_double = type.env.evaluate(generics.Foo1.dart2java$typeInfo.typeVariables[0])) : (cached_0_double)) == dart._runtime.helpers.DoubleHelper.type)))
      {
        result = new generics.Foo1__double(((dart._runtime.helpers.ConstructorHelper.EmptyConstructorMarker) null), type);
        result._constructor();
        return result;
      }
      result = new generics.Foo1(((dart._runtime.helpers.ConstructorHelper.EmptyConstructorMarker) null), type);
      result._constructor();
      return result;
    }
}
