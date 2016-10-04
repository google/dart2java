package tracer;

public class Solid extends tracer.Materials implements tracer.Solid_interface
{
    public static dart._runtime.types.simple.InterfaceTypeInfo dart2java$typeInfo = new dart._runtime.types.simple.InterfaceTypeInfo(tracer.Solid.class, tracer.Solid_interface.class);
    private static dart._runtime.types.simple.InterfaceTypeExpr dart2java$typeExpr_double$0 = new dart._runtime.types.simple.InterfaceTypeExpr(dart._runtime.helpers.DoubleHelper.dart2java$typeInfo);
    private static dart._runtime.types.simple.InterfaceTypeExpr dart2java$typeExpr_Materials = new dart._runtime.types.simple.InterfaceTypeExpr(tracer.Materials.dart2java$typeInfo);
    static {
      tracer.Solid.dart2java$typeInfo.superclass = dart2java$typeExpr_Materials;
    }
    public tracer.Color_interface color;
  
    public Solid(dart._runtime.helpers.ConstructorHelper.EmptyConstructorMarker arg, dart._runtime.types.simple.Type type)
    {
      super(arg, type);
    }
  
    public tracer.Color_interface getColor_(java.lang.Number u, java.lang.Number v)
    {
      final dart._runtime.types.simple.TypeEnvironment dart2java$localTypeEnv = this.dart2java$type.env;
      return this.getColor();
    }
    public void _constructor(tracer.Color_interface color, java.lang.Object reflection, java.lang.Object refraction, java.lang.Object transparency, java.lang.Object gloss)
    {
      final dart._runtime.types.simple.TypeEnvironment dart2java$localTypeEnv = this.dart2java$type.env;
      this.color = color;
      super._constructor(((double) dart2java$localTypeEnv.evaluate(dart2java$typeExpr_double$0).check(reflection)), ((double) dart2java$localTypeEnv.evaluate(dart2java$typeExpr_double$0).check(transparency)), ((double) dart2java$localTypeEnv.evaluate(dart2java$typeExpr_double$0).check(gloss)), ((double) dart2java$localTypeEnv.evaluate(dart2java$typeExpr_double$0).check(refraction)), false);
    }
    public tracer.Color_interface getColor()
    {
      return this.color;
    }
    public static tracer.Solid_interface _new_Solid$(dart._runtime.types.simple.Type type, tracer.Color_interface color, java.lang.Object reflection, java.lang.Object refraction, java.lang.Object transparency, java.lang.Object gloss)
    {
      tracer.Solid result;
      result = new tracer.Solid(((dart._runtime.helpers.ConstructorHelper.EmptyConstructorMarker) null), type);
      result._constructor(color, reflection, refraction, transparency, gloss);
      return result;
    }
}
