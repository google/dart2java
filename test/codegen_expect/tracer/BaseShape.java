package tracer;

public class BaseShape extends dart._runtime.base.DartObject
{
    public static dart._runtime.types.simple.InterfaceTypeInfo dart2java$typeInfo = new dart._runtime.types.simple.InterfaceTypeInfo("file:///usr/local/google/home/springerm/ddc-java/gen/codegen_tests/tracer.dart", "BaseShape");
    static {
      tracer.BaseShape.dart2java$typeInfo.superclass = new dart._runtime.types.simple.InterfaceTypeExpr(dart._runtime.helpers.ObjectHelper.dart2java$typeInfo);
    }
    public java.lang.Object position;
    public java.lang.Object material;
  
    public BaseShape(java.lang.Object position, java.lang.Object material)
    {
      super((dart._runtime.helpers.ConstructorHelper.EmptyConstructorMarker) null);
      this._constructor(position, material);
    }
    public BaseShape(dart._runtime.helpers.ConstructorHelper.EmptyConstructorMarker arg)
    {
      super(arg);
    }
  
    protected void _constructor(java.lang.Object position, java.lang.Object material)
    {
      this.position = position;
      this.material = material;
      super._constructor();
    }
    public java.lang.String toString()
    {
      return "BaseShape";
    }
    public java.lang.Object getPosition()
    {
      return this.position;
    }
    public java.lang.Object getMaterial()
    {
      return this.material;
    }
}
