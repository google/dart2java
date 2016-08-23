package tracer;

public class Solid extends tracer.Materials
{
    public static dart._runtime.types.simple.InterfaceTypeInfo dart2java$typeInfo = new dart._runtime.types.simple.InterfaceTypeInfo("file:///usr/local/google/home/springerm/ddc-java/gen/codegen_tests/tracer.dart", "Solid");
    static {
      tracer.Solid.dart2java$typeInfo.superclass = new dart._runtime.types.simple.InterfaceTypeExpr(tracer.Materials.dart2java$typeInfo);
    }
    public tracer.Color color;
  
    public Solid(tracer.Color color, java.lang.Object reflection, java.lang.Object refraction, java.lang.Object transparency, java.lang.Object gloss)
    {
      super((dart._runtime.helpers.ConstructorHelper.EmptyConstructorMarker) null);
      this._constructor(color, reflection, refraction, transparency, gloss);
    }
    public Solid(dart._runtime.helpers.ConstructorHelper.EmptyConstructorMarker arg)
    {
      super(arg);
    }
  
    protected void _constructor(tracer.Color color, java.lang.Object reflection, java.lang.Object refraction, java.lang.Object transparency, java.lang.Object gloss)
    {
      this.color = color;
      super._constructor((java.lang.Double) reflection, (java.lang.Double) transparency, (java.lang.Double) gloss, (java.lang.Double) refraction, false);
    }
    public tracer.Color getColor_(java.lang.Number u, java.lang.Number v)
    {
      return this.getColor();
    }
    public tracer.Color getColor()
    {
      return this.color;
    }
}
