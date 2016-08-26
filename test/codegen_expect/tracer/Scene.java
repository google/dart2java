package tracer;

public class Scene extends dart._runtime.base.DartObject
{
    public static dart._runtime.types.simple.InterfaceTypeInfo dart2java$typeInfo = new dart._runtime.types.simple.InterfaceTypeInfo("tracer.Scene");
    static {
      tracer.Scene.dart2java$typeInfo.superclass = new dart._runtime.types.simple.InterfaceTypeExpr(dart._runtime.helpers.ObjectHelper.dart2java$typeInfo);
    }
    public java.lang.Object camera;
    public java.lang.Object shapes;
    public java.lang.Object lights;
    public java.lang.Object background;
  
    public Scene(dart._runtime.types.simple.Type type)
    {
      super((dart._runtime.helpers.ConstructorHelper.EmptyConstructorMarker) null, type);
      this._constructor();
    }
    public Scene(dart._runtime.helpers.ConstructorHelper.EmptyConstructorMarker arg, dart._runtime.types.simple.Type type)
    {
      super(arg, type);
    }
  
    protected void _constructor()
    {
      final dart._runtime.types.simple.TypeEnvironment dart2java$localTypeEnv = this.dart2java$type.env;
      super._constructor();
      this.setCamera(new tracer.Camera(dart2java$localTypeEnv.evaluate(new dart._runtime.types.simple.InterfaceTypeExpr(tracer.Camera.dart2java$typeInfo)), new tracer.Vector(dart2java$localTypeEnv.evaluate(new dart._runtime.types.simple.InterfaceTypeExpr(tracer.Vector.dart2java$typeInfo)), 0.0, 0.0, dart._runtime.helpers.DoubleHelper.operatorUnaryMinus(0.5)), new tracer.Vector(dart2java$localTypeEnv.evaluate(new dart._runtime.types.simple.InterfaceTypeExpr(tracer.Vector.dart2java$typeInfo)), 0.0, 0.0, 1.0), new tracer.Vector(dart2java$localTypeEnv.evaluate(new dart._runtime.types.simple.InterfaceTypeExpr(tracer.Vector.dart2java$typeInfo)), 0.0, 1.0, 0.0)));
      this.setShapes(dart._runtime.base.DartList.Generic.newInstance(java.lang.Object.class, 0));
      this.setLights(dart._runtime.base.DartList.Generic.newInstance(java.lang.Object.class, 0));
      this.setBackground(new tracer.Background(dart2java$localTypeEnv.evaluate(new dart._runtime.types.simple.InterfaceTypeExpr(tracer.Background.dart2java$typeInfo)), new tracer.Color(dart2java$localTypeEnv.evaluate(new dart._runtime.types.simple.InterfaceTypeExpr(tracer.Color.dart2java$typeInfo)), 0.0, 0.0, 0.5), 0.2));
    }
    public java.lang.Object getCamera()
    {
      return this.camera;
    }
    public java.lang.Object getShapes()
    {
      return this.shapes;
    }
    public java.lang.Object getLights()
    {
      return this.lights;
    }
    public java.lang.Object getBackground()
    {
      return this.background;
    }
    public java.lang.Object setCamera(java.lang.Object value)
    {
      this.camera = value;
      return value;
    }
    public java.lang.Object setShapes(java.lang.Object value)
    {
      this.shapes = value;
      return value;
    }
    public java.lang.Object setLights(java.lang.Object value)
    {
      this.lights = value;
      return value;
    }
    public java.lang.Object setBackground(java.lang.Object value)
    {
      this.background = value;
      return value;
    }
}
