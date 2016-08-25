package tracer;

public abstract class Materials extends dart._runtime.base.DartObject
{
    public static dart._runtime.types.simple.InterfaceTypeInfo dart2java$typeInfo = new dart._runtime.types.simple.InterfaceTypeInfo("file:///usr/local/google/home/andrewkrieger/ddc-java/gen/codegen_tests/tracer.dart", "Materials");
    static {
      tracer.Materials.dart2java$typeInfo.superclass = new dart._runtime.types.simple.InterfaceTypeExpr(dart._runtime.helpers.ObjectHelper.dart2java$typeInfo);
    }
    public java.lang.Double gloss;
    public java.lang.Double transparency;
    public java.lang.Double reflection;
    public java.lang.Double refraction;
    public java.lang.Boolean hasTexture;
  
    public Materials(dart._runtime.types.simple.Type type, java.lang.Double reflection, java.lang.Double transparency, java.lang.Double gloss, java.lang.Double refraction, java.lang.Boolean hasTexture)
    {
      super((dart._runtime.helpers.ConstructorHelper.EmptyConstructorMarker) null, type);
      this._constructor(reflection, transparency, gloss, refraction, hasTexture);
    }
    public Materials(dart._runtime.helpers.ConstructorHelper.EmptyConstructorMarker arg, dart._runtime.types.simple.Type type)
    {
      super(arg, type);
    }
  
    protected void _constructor(java.lang.Double reflection, java.lang.Double transparency, java.lang.Double gloss, java.lang.Double refraction, java.lang.Boolean hasTexture)
    {
      final dart._runtime.types.simple.TypeEnvironment dart2java$localTypeEnv = this.dart2java$type.env;
      this.reflection = reflection;
      this.transparency = transparency;
      this.gloss = gloss;
      this.refraction = refraction;
      this.hasTexture = hasTexture;
      super._constructor();
    }
    public abstract tracer.Color getColor_(java.lang.Number u, java.lang.Number v);
    public java.lang.Object wrapUp(java.lang.Object t)
    {
      final dart._runtime.types.simple.TypeEnvironment dart2java$localTypeEnv = this.dart2java$type.env;
      t = dart._runtime.helpers.DynamicHelper.invoke("operatorModulus", t, 2.0);
      if ((java.lang.Boolean) dart._runtime.helpers.DynamicHelper.invoke("operatorLess", t, (-1)))
      {
        t = dart._runtime.helpers.DynamicHelper.invoke("operatorPlus", t, 2.0);
      }
      if ((java.lang.Boolean) dart._runtime.helpers.DynamicHelper.invoke("operatorGreaterEqual", t, 1))
      {
        t = dart._runtime.helpers.DynamicHelper.invoke("operatorMinus", t, 2.0);
      }
      return t;
    }
    public java.lang.Double getGloss()
    {
      return this.gloss;
    }
    public java.lang.Double getTransparency()
    {
      return this.transparency;
    }
    public java.lang.Double getReflection()
    {
      return this.reflection;
    }
    public java.lang.Double getRefraction()
    {
      return this.refraction;
    }
    public java.lang.Boolean getHasTexture()
    {
      return this.hasTexture;
    }
}
