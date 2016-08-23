package tracer;

public class __TopLevel
{
    public static java.lang.Object checkNumber;
  
  
  
    public static void main(String[] args)
    {
      dart.core.__TopLevel.print("Running benchmark...");
      new tracer.TracerBenchmark().report();
      dart.core.__TopLevel.print("Done.");
    }
    public static void renderScene(java.lang.Object event)
    {
      tracer.Scene scene = new tracer.Scene();
      scene.setCamera(new tracer.Camera(new tracer.Vector(0.0, 0.0, dart._runtime.helpers.DoubleHelper.operatorUnaryMinus(15.0)), new tracer.Vector(dart._runtime.helpers.DoubleHelper.operatorUnaryMinus(0.2), 0.0, 5.0), new tracer.Vector(0.0, 1.0, 0.0)));
      scene.setBackground(new tracer.Background(new tracer.Color(0.5, 0.5, 0.5), 0.4));
      tracer.Sphere sphere = new tracer.Sphere(new tracer.Vector(dart._runtime.helpers.DoubleHelper.operatorUnaryMinus(1.5), 1.5, 2.0), 1.5, new tracer.Solid(new tracer.Color(0.0, 0.5, 0.5), 0.3, 0.0, 0.0, 2.0));
      tracer.Sphere sphere1 = new tracer.Sphere(new tracer.Vector(1.0, 0.25, 1.0), 0.5, new tracer.Solid(new tracer.Color(0.9, 0.9, 0.9), 0.1, 0.0, 0.0, 1.5));
      tracer.Plane plane = new tracer.Plane(new tracer.Vector(0.1, 0.9, dart._runtime.helpers.DoubleHelper.operatorUnaryMinus(0.5)).normalize(), 1.2, new tracer.Chessboard(new tracer.Color(1.0, 1.0, 1.0), new tracer.Color(0.0, 0.0, 0.0), 0.2, 0.0, 1.0, 0.7));
      dart._runtime.helpers.DynamicHelper.invoke("add", scene.getShapes(), plane);
      dart._runtime.helpers.DynamicHelper.invoke("add", scene.getShapes(), sphere);
      dart._runtime.helpers.DynamicHelper.invoke("add", scene.getShapes(), sphere1);
      tracer.Light light = new tracer.Light(new tracer.Vector(5.0, 10.0, dart._runtime.helpers.DoubleHelper.operatorUnaryMinus(1.0)), new tracer.Color(0.8, 0.8, 0.8), 10.0);
      tracer.Light light1 = new tracer.Light(new tracer.Vector(dart._runtime.helpers.DoubleHelper.operatorUnaryMinus(3.0), 5.0, dart._runtime.helpers.DoubleHelper.operatorUnaryMinus(15.0)), new tracer.Color(0.8, 0.8, 0.8), 100.0);
      dart._runtime.helpers.DynamicHelper.invoke("add", scene.getLights(), light);
      dart._runtime.helpers.DynamicHelper.invoke("add", scene.getLights(), light1);
      int imageWidth = 0;
      int imageHeight = 0;
      int pixelSize = 0;
      java.lang.Boolean renderDiffuse = null;
      java.lang.Boolean renderShadows = null;
      java.lang.Boolean renderHighlights = null;
      java.lang.Boolean renderReflections = null;
      java.lang.Object canvas = null;
      if (dart._runtime.helpers.ObjectHelper.operatorEqual(event, null))
      {
        imageWidth = 100;
        imageHeight = 100;
        pixelSize = 5;
        renderDiffuse = true;
        renderShadows = true;
        renderHighlights = true;
        renderReflections = true;
        canvas = null;
      }
      else
      {
        
      }
      int rayDepth = 2;
      tracer.Engine raytracer = new tracer.Engine(imageWidth, imageHeight, pixelSize, pixelSize, renderDiffuse, renderShadows, renderHighlights, renderReflections, rayDepth);
      raytracer.renderScene(scene, canvas);
    }
}
