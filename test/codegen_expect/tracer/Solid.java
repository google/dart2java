package tracer;

public class Solid extends tracer.Materials implements tracer.Solid_interface
{
    public static dart._runtime.types.simple.InterfaceTypeInfo dart2java$typeInfo = new dart._runtime.types.simple.InterfaceTypeInfo("tracer.Solid");
    static {
      tracer.Solid.dart2java$typeInfo.superclass = new dart._runtime.types.simple.InterfaceTypeExpr(tracer.Materials.dart2java$typeInfo);
    }
    public tracer.Color color;
  
    public Solid(dart._runtime.types.simple.Type type, tracer.Color color, java.lang.Object reflection, java.lang.Object refraction, java.lang.Object transparency, java.lang.Object gloss)
    {
      super((dart._runtime.helpers.ConstructorHelper.EmptyConstructorMarker) null, type);
      this._constructor(color, reflection, refraction, transparency, gloss);
    }
    public Solid(dart._runtime.helpers.ConstructorHelper.EmptyConstructorMarker arg, dart._runtime.types.simple.Type type)
    {
      super(arg, type);
    }
  
    protected void _constructor(tracer.Color color, java.lang.Object reflection, java.lang.Object refraction, java.lang.Object transparency, java.lang.Object gloss)
    {
      final dart._runtime.types.simple.TypeEnvironment dart2java$localTypeEnv = this.dart2java$type.env;
      this.color = color;
      super._constructor((java.lang.Double) reflection, (java.lang.Double) transparency, (java.lang.Double) gloss, (java.lang.Double) refraction, false);
    }
    public tracer.Color getColor_(java.lang.Number u, java.lang.Number v)
    {
      final dart._runtime.types.simple.TypeEnvironment dart2java$localTypeEnv = this.dart2java$type.env;
      return this.getColor();
    }
    public tracer.Color getColor()
    {
      return this.color;
    }
}
