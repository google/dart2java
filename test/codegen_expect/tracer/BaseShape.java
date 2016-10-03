package tracer;

public class BaseShape extends dart._runtime.base.DartObject implements tracer.BaseShape_interface
{
    public static dart._runtime.types.simple.InterfaceTypeInfo dart2java$typeInfo = new dart._runtime.types.simple.InterfaceTypeInfo(tracer.BaseShape.class, tracer.BaseShape_interface.class);
    private static dart._runtime.types.simple.InterfaceTypeExpr dart2java$typeExpr_Object = new dart._runtime.types.simple.InterfaceTypeExpr(dart._runtime.helpers.ObjectHelper.dart2java$typeInfo);
    static {
      tracer.BaseShape.dart2java$typeInfo.superclass = dart2java$typeExpr_Object;
    }
    public java.lang.Object position;
    public java.lang.Object material;
  
    public BaseShape(dart._runtime.helpers.ConstructorHelper.EmptyConstructorMarker arg, dart._runtime.types.simple.Type type)
    {
      super(arg, type);
    }
  
    public java.lang.String toString()
    {
      final dart._runtime.types.simple.TypeEnvironment dart2java$localTypeEnv = this.dart2java$type.env;
      return "BaseShape";
    }
    public void _constructor(java.lang.Object position, java.lang.Object material)
    {
      final dart._runtime.types.simple.TypeEnvironment dart2java$localTypeEnv = this.dart2java$type.env;
      this.position = position;
      this.material = material;
      super._constructor();
    }
    public java.lang.Object getPosition()
    {
      return this.position;
    }
    public java.lang.Object getMaterial()
    {
      return this.material;
    }
    public static tracer.BaseShape_interface _new(dart._runtime.types.simple.Type type, java.lang.Object position, java.lang.Object material)
    {
      tracer.BaseShape result;
      result = new tracer.BaseShape(((dart._runtime.helpers.ConstructorHelper.EmptyConstructorMarker) null), type);
      result._constructor(position, material);
      return result;
    }
}
