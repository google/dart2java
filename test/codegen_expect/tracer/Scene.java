package tracer;

public class Scene extends dart._runtime.base.DartObject
{
    public static dart._runtime.types.simple.InterfaceTypeInfo dart2java$typeInfo = new dart._runtime.types.simple.InterfaceTypeInfo("file:///usr/local/google/home/springerm/ddc-java/gen/codegen_tests/tracer.dart", "Scene");
    static {
      tracer.Scene.dart2java$typeInfo.superclass = new dart._runtime.types.simple.InterfaceTypeExpr(dart._runtime.helpers.ObjectHelper.dart2java$typeInfo);
    }
    public java.lang.Object camera;
    public java.lang.Object shapes;
    public java.lang.Object lights;
    public java.lang.Object background;
  
    public Scene()
    {
      super((dart._runtime.helpers.ConstructorHelper.EmptyConstructorMarker) null);
      this._constructor();
    }
    public Scene(dart._runtime.helpers.ConstructorHelper.EmptyConstructorMarker arg)
    {
      super(arg);
    }
  
    protected void _constructor()
    {
      super._constructor();
      this.setCamera(new tracer.Camera(new tracer.Vector(0.0, 0.0, dart._runtime.helpers.DoubleHelper.operatorUnaryMinus(0.5)), new tracer.Vector(0.0, 0.0, 1.0), new tracer.Vector(0.0, 1.0, 0.0)));
      this.setShapes(dart._runtime.base.DartList.Generic.newInstance(java.lang.Object.class, 0));
      this.setLights(dart._runtime.base.DartList.Generic.newInstance(java.lang.Object.class, 0));
      this.setBackground(new tracer.Background(new tracer.Color(0.0, 0.0, 0.5), 0.2));
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
