package tracer;

public class Scene extends dart._runtime.base.DartObject implements tracer.Scene_interface
{
    public static dart._runtime.types.simple.InterfaceTypeInfo dart2java$typeInfo = new dart._runtime.types.simple.InterfaceTypeInfo(tracer.Scene.class, tracer.Scene_interface.class);
    private static dart._runtime.types.simple.InterfaceTypeExpr dart2java$typeExpr_Vector = new dart._runtime.types.simple.InterfaceTypeExpr(tracer.Vector.dart2java$typeInfo);
    private static dart._runtime.types.simple.InterfaceTypeExpr dart2java$typeExpr_Camera = new dart._runtime.types.simple.InterfaceTypeExpr(tracer.Camera.dart2java$typeInfo);
    private static dart._runtime.types.simple.TypeExpr dart2java$typeExpr_dynamic = dart._runtime.types.simple.TopType.EXPR;
    private static dart._runtime.types.simple.InterfaceTypeExpr dart2java$typeExpr_Color = new dart._runtime.types.simple.InterfaceTypeExpr(tracer.Color.dart2java$typeInfo);
    private static dart._runtime.types.simple.InterfaceTypeExpr dart2java$typeExpr_Background = new dart._runtime.types.simple.InterfaceTypeExpr(tracer.Background.dart2java$typeInfo);
    private static dart._runtime.types.simple.InterfaceTypeExpr dart2java$typeExpr_Object = new dart._runtime.types.simple.InterfaceTypeExpr(dart._runtime.helpers.ObjectHelper.dart2java$typeInfo);
    static {
      tracer.Scene.dart2java$typeInfo.superclass = dart2java$typeExpr_Object;
    }
    public java.lang.Object camera;
    public java.lang.Object shapes;
    public java.lang.Object lights;
    public java.lang.Object background;
  
    public Scene(dart._runtime.helpers.ConstructorHelper.EmptyConstructorMarker arg, dart._runtime.types.simple.Type type)
    {
      super(arg, type);
    }
  
    public void _constructor()
    {
      final dart._runtime.types.simple.TypeEnvironment dart2java$localTypeEnv = this.dart2java$type.env;
      super._constructor();
      this.setCamera(tracer.Camera._new(dart2java$localTypeEnv.evaluate(dart2java$typeExpr_Camera), tracer.Vector._new(dart2java$localTypeEnv.evaluate(dart2java$typeExpr_Vector), 0.0, 0.0, (-0.5)), tracer.Vector._new(dart2java$localTypeEnv.evaluate(dart2java$typeExpr_Vector), 0.0, 0.0, 1.0), tracer.Vector._new(dart2java$localTypeEnv.evaluate(dart2java$typeExpr_Vector), 0.0, 1.0, 0.0)));
      this.setShapes(((dart.core.List_interface<java.lang.Object>) dart.core.List.<java.lang.Object>factory$(dart2java$localTypeEnv.extend(dart.core.List.factory$$typeInfo.typeVariables, new dart._runtime.types.simple.Type[] {dart2java$localTypeEnv.evaluate(dart2java$typeExpr_dynamic)}), 0)));
      this.setLights(((dart.core.List_interface<java.lang.Object>) dart.core.List.<java.lang.Object>factory$(dart2java$localTypeEnv.extend(dart.core.List.factory$$typeInfo.typeVariables, new dart._runtime.types.simple.Type[] {dart2java$localTypeEnv.evaluate(dart2java$typeExpr_dynamic)}), 0)));
      this.setBackground(tracer.Background._new(dart2java$localTypeEnv.evaluate(dart2java$typeExpr_Background), tracer.Color._new(dart2java$localTypeEnv.evaluate(dart2java$typeExpr_Color), 0.0, 0.0, 0.5), 0.2));
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
    public static tracer.Scene_interface _new(dart._runtime.types.simple.Type type)
    {
      tracer.Scene result;
      result = new tracer.Scene(((dart._runtime.helpers.ConstructorHelper.EmptyConstructorMarker) null), type);
      result._constructor();
      return result;
    }
}
