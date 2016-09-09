package tracer;

public class Solid extends tracer.Materials implements tracer.Solid_interface
{
    public static dart._runtime.types.simple.InterfaceTypeInfo dart2java$typeInfo = new dart._runtime.types.simple.InterfaceTypeInfo(tracer.Solid.class, tracer.Solid_interface.class);
    static {
      tracer.Solid.dart2java$typeInfo.superclass = new dart._runtime.types.simple.InterfaceTypeExpr(tracer.Materials.dart2java$typeInfo);
    }
    public tracer.Color_interface color;
  
    public Solid(dart._runtime.types.simple.Type type, tracer.Color_interface color, java.lang.Object reflection, java.lang.Object refraction, java.lang.Object transparency, java.lang.Object gloss)
    {
      super(((dart._runtime.helpers.ConstructorHelper.EmptyConstructorMarker) null), type);
      this._constructor(color, reflection, refraction, transparency, gloss);
    }
    public Solid(dart._runtime.helpers.ConstructorHelper.EmptyConstructorMarker arg, dart._runtime.types.simple.Type type)
    {
      super(arg, type);
    }
  
    protected void _constructor(tracer.Color_interface color, java.lang.Object reflection, java.lang.Object refraction, java.lang.Object transparency, java.lang.Object gloss)
    {
      final dart._runtime.types.simple.TypeEnvironment dart2java$localTypeEnv = this.dart2java$type.env;
      this.color = color;
      super._constructor(((double) dart2java$localTypeEnv.evaluate(new dart._runtime.types.simple.InterfaceTypeExpr(dart._runtime.helpers.DoubleHelper.dart2java$typeInfo)).check(reflection)), ((double) dart2java$localTypeEnv.evaluate(new dart._runtime.types.simple.InterfaceTypeExpr(dart._runtime.helpers.DoubleHelper.dart2java$typeInfo)).check(transparency)), ((double) dart2java$localTypeEnv.evaluate(new dart._runtime.types.simple.InterfaceTypeExpr(dart._runtime.helpers.DoubleHelper.dart2java$typeInfo)).check(gloss)), ((double) dart2java$localTypeEnv.evaluate(new dart._runtime.types.simple.InterfaceTypeExpr(dart._runtime.helpers.DoubleHelper.dart2java$typeInfo)).check(refraction)), false);
    }
    public tracer.Color_interface getColor_(java.lang.Number u, java.lang.Number v)
    {
      final dart._runtime.types.simple.TypeEnvironment dart2java$localTypeEnv = this.dart2java$type.env;
      return this.getColor();
    }
    public tracer.Color_interface getColor()
    {
      return this.color;
    }
}
