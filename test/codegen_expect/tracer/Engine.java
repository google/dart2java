package tracer;

public class Engine extends dart._runtime.base.DartObject implements tracer.Engine_interface
{
    public static dart._runtime.types.simple.InterfaceTypeInfo dart2java$typeInfo = new dart._runtime.types.simple.InterfaceTypeInfo("tracer.Engine");
    static {
      tracer.Engine.dart2java$typeInfo.superclass = new dart._runtime.types.simple.InterfaceTypeExpr(dart._runtime.helpers.ObjectHelper.dart2java$typeInfo);
    }
    public int canvasWidth;
    public int canvasHeight;
    public int pixelWidth;
    public int pixelHeight;
    public boolean renderDiffuse;
    public boolean renderShadows;
    public boolean renderHighlights;
    public boolean renderReflections;
    public int rayDepth;
    public java.lang.Object canvas;
  
    public Engine(dart._runtime.types.simple.Type type, int canvasWidth, int canvasHeight, int pixelWidth, int pixelHeight, boolean renderDiffuse, boolean renderShadows, boolean renderHighlights, boolean renderReflections, int rayDepth)
    {
      super((dart._runtime.helpers.ConstructorHelper.EmptyConstructorMarker) null, type);
      this._constructor(canvasWidth, canvasHeight, pixelWidth, pixelHeight, renderDiffuse, renderShadows, renderHighlights, renderReflections, rayDepth);
    }
    public Engine(dart._runtime.helpers.ConstructorHelper.EmptyConstructorMarker arg, dart._runtime.types.simple.Type type)
    {
      super(arg, type);
    }
  
    protected void _constructor(int canvasWidth, int canvasHeight, int pixelWidth, int pixelHeight, boolean renderDiffuse, boolean renderShadows, boolean renderHighlights, boolean renderReflections, int rayDepth)
    {
      final dart._runtime.types.simple.TypeEnvironment dart2java$localTypeEnv = this.dart2java$type.env;
      this.canvasWidth = 0;
      this.canvasHeight = 0;
      this.pixelWidth = 0;
      this.pixelHeight = 0;
      this.rayDepth = 0;
      this.canvasWidth = canvasWidth;
      this.canvasHeight = canvasHeight;
      this.pixelWidth = pixelWidth;
      this.pixelHeight = pixelHeight;
      this.renderDiffuse = renderDiffuse;
      this.renderShadows = renderShadows;
      this.renderHighlights = renderHighlights;
      this.renderReflections = renderReflections;
      this.rayDepth = rayDepth;
      super._constructor();
      this.setCanvasHeight(dart._runtime.helpers.IntegerHelper.operatorTruncatedDivide(this.getCanvasHeight(), this.getPixelHeight()));
      this.setCanvasWidth(dart._runtime.helpers.IntegerHelper.operatorTruncatedDivide(this.getCanvasWidth(), this.getPixelWidth()));
    }
    public void setPixel(int x, int y, tracer.Color_interface color)
    {
      final dart._runtime.types.simple.TypeEnvironment dart2java$localTypeEnv = this.dart2java$type.env;
      java.lang.Object pxW = null;
      java.lang.Object pxH = null;
      pxW = this.getPixelWidth();
      pxH = this.getPixelHeight();
      if ((!dart._runtime.helpers.ObjectHelper.operatorEqual(this.getCanvas(), null)))
      {
        dart._runtime.helpers.DynamicHelper.invoke("setFillStyle", this.getCanvas(), dart._runtime.helpers.ObjectHelper.toString(color));
        dart._runtime.helpers.DynamicHelper.invoke("fillRect", this.getCanvas(), dart._runtime.helpers.IntegerHelper.operatorStar(x, (java.lang.Number) pxW), dart._runtime.helpers.IntegerHelper.operatorStar(y, (java.lang.Number) pxH), pxW, pxH);
      }
      else
      {
        tracer.__TopLevel.checkNumber = dart._runtime.helpers.DynamicHelper.invoke("operatorPlus", tracer.__TopLevel.checkNumber, color.brightness());
      }
    }
    public void renderScene(tracer.Scene_interface scene, java.lang.Object canvas)
    {
      final dart._runtime.types.simple.TypeEnvironment dart2java$localTypeEnv = this.dart2java$type.env;
      tracer.__TopLevel.checkNumber = 0;
      this.setCanvas((dart._runtime.helpers.ObjectHelper.operatorEqual(canvas, null)) ? (null) : (dart._runtime.helpers.DynamicHelper.invoke("getContext", canvas, "2d")));
      int canvasHeight = this.getCanvasHeight();
      int canvasWidth = this.getCanvasWidth();
      for (int y = 0; (y < canvasHeight); y = (y + 1))
      {
        for (int x = 0; (x < canvasWidth); x = (x + 1))
        {
          double yp = dart._runtime.helpers.DoubleHelper.operatorMinus(dart._runtime.helpers.DoubleHelper.operatorStar(dart._runtime.helpers.IntegerHelper.operatorDivide(y, canvasHeight), 2), 1);
          double xp = dart._runtime.helpers.DoubleHelper.operatorMinus(dart._runtime.helpers.DoubleHelper.operatorStar(dart._runtime.helpers.IntegerHelper.operatorDivide(x, canvasWidth), 2), 1);
          java.lang.Object ray = dart._runtime.helpers.DynamicHelper.invoke("getRay", scene.getCamera(), xp, yp);
          this.setPixel(x, y, this.getPixelColor((tracer.Ray_interface) ray, scene));
        }
      }
      if ((dart._runtime.helpers.ObjectHelper.operatorEqual(canvas, null) && (!dart._runtime.helpers.ObjectHelper.operatorEqual(tracer.__TopLevel.checkNumber, 55545))))
      {
        throw new RuntimeException("Scene rendered incorrectly");
      }
    }
    public tracer.Color_interface getPixelColor(tracer.Ray_interface ray, tracer.Scene_interface scene)
    {
      final dart._runtime.types.simple.TypeEnvironment dart2java$localTypeEnv = this.dart2java$type.env;
      tracer.IntersectionInfo_interface info = this.testIntersection(ray, scene, null);
      if (info.getIsHit())
      {
        tracer.Color_interface color = this.rayTrace(info, ray, scene, 0);
        return color;
      }
      return (tracer.Color_interface) dart._runtime.helpers.DynamicHelper.invoke("getColor", scene.getBackground());
    }
    public tracer.IntersectionInfo_interface testIntersection(tracer.Ray_interface ray, tracer.Scene_interface scene, tracer.BaseShape_interface exclude)
    {
      final dart._runtime.types.simple.TypeEnvironment dart2java$localTypeEnv = this.dart2java$type.env;
      int hits = 0;
      tracer.IntersectionInfo_interface best = new tracer.IntersectionInfo(dart2java$localTypeEnv.evaluate(new dart._runtime.types.simple.InterfaceTypeExpr(tracer.IntersectionInfo.dart2java$typeInfo)));
      best.setDistance(2000.0);
      for (int i = 0; dart._runtime.helpers.IntegerHelper.operatorLess(i, (java.lang.Number) dart._runtime.helpers.DynamicHelper.invoke("getLength", scene.getShapes())); i = (i + 1))
      {
        java.lang.Object shape = dart._runtime.helpers.DynamicHelper.invoke("operatorAt", scene.getShapes(), i);
        if ((!dart._runtime.helpers.ObjectHelper.operatorEqual(shape, exclude)))
        {
          tracer.IntersectionInfo_interface info = (tracer.IntersectionInfo_interface) dart._runtime.helpers.DynamicHelper.invoke("intersect", shape, ray);
          if ((((boolean) info.getIsHit() && (boolean) dart._runtime.helpers.DynamicHelper.invoke("operatorGreaterEqual", info.getDistance(), 0)) && (boolean) dart._runtime.helpers.DynamicHelper.invoke("operatorLess", info.getDistance(), best.getDistance())))
          {
            best = info;
            hits = (hits + 1);
          }
        }
      }
      best.setHitCount(hits);
      return best;
    }
    public tracer.Ray_interface getReflectionRay(tracer.Vector_interface P, tracer.Vector_interface N, tracer.Vector_interface V)
    {
      final dart._runtime.types.simple.TypeEnvironment dart2java$localTypeEnv = this.dart2java$type.env;
      double c1 = (-N.dot(V));
      tracer.Vector_interface R1 = N.multiplyScalar(dart._runtime.helpers.IntegerHelper.operatorStar(2, c1)).operatorPlus(V);
      return new tracer.Ray(dart2java$localTypeEnv.evaluate(new dart._runtime.types.simple.InterfaceTypeExpr(tracer.Ray.dart2java$typeInfo)), P, R1);
    }
    public tracer.Color_interface rayTrace(tracer.IntersectionInfo_interface info, tracer.Ray_interface ray, tracer.Scene_interface scene, int depth)
    {
      final dart._runtime.types.simple.TypeEnvironment dart2java$localTypeEnv = this.dart2java$type.env;
      tracer.Color_interface color = (tracer.Color_interface) dart._runtime.helpers.DynamicHelper.invoke("multiplyScalar", info.getColor(), dart._runtime.helpers.DynamicHelper.invoke("getAmbience", scene.getBackground()));
      tracer.Color_interface oldColor = color;
      java.lang.Number shininess = dart.math.__TopLevel.pow(10.0, (java.lang.Number) dart._runtime.helpers.DynamicHelper.invoke("operatorPlus", dart._runtime.helpers.DynamicHelper.invoke("getGloss", dart._runtime.helpers.DynamicHelper.invoke("getMaterial", info.getShape())), 1.0));
      for (int i = 0; dart._runtime.helpers.IntegerHelper.operatorLess(i, (java.lang.Number) dart._runtime.helpers.DynamicHelper.invoke("getLength", scene.getLights())); i = (i + 1))
      {
        java.lang.Object light = dart._runtime.helpers.DynamicHelper.invoke("operatorAt", scene.getLights(), i);
        java.lang.Object v = dart._runtime.helpers.DynamicHelper.invoke("normalize", dart._runtime.helpers.DynamicHelper.invoke("operatorMinus", dart._runtime.helpers.DynamicHelper.invoke("getPosition", light), info.getPosition()));
        if (this.getRenderDiffuse())
        {
          java.lang.Object L = dart._runtime.helpers.DynamicHelper.invoke("dot", v, info.getNormal());
          if ((boolean) dart._runtime.helpers.DynamicHelper.invoke("operatorGreater", L, 0.0))
          {
            color = color.operatorPlus((tracer.Color_interface) dart._runtime.helpers.DynamicHelper.invoke("operatorStar", info.getColor(), dart._runtime.helpers.DynamicHelper.invoke("multiplyScalar", dart._runtime.helpers.DynamicHelper.invoke("getColor", light), L)));
          }
        }
        if ((depth <= this.getRayDepth()))
        {
          if (((boolean) this.getRenderReflections() && (boolean) dart._runtime.helpers.DynamicHelper.invoke("operatorGreater", dart._runtime.helpers.DynamicHelper.invoke("getReflection", dart._runtime.helpers.DynamicHelper.invoke("getMaterial", info.getShape())), 0.0)))
          {
            tracer.Ray_interface reflectionRay = this.getReflectionRay((tracer.Vector_interface) info.getPosition(), (tracer.Vector_interface) info.getNormal(), (tracer.Vector_interface) ray.getDirection());
            tracer.IntersectionInfo_interface refl = this.testIntersection(reflectionRay, scene, (tracer.BaseShape_interface) info.getShape());
            if (((boolean) refl.getIsHit() && (boolean) dart._runtime.helpers.DynamicHelper.invoke("operatorGreater", refl.getDistance(), 0.0)))
            {
              refl.setColor(this.rayTrace(refl, reflectionRay, scene, (depth + 1)));
            }
            else
            {
              refl.setColor(dart._runtime.helpers.DynamicHelper.invoke("getColor", scene.getBackground()));
            }
            color = color.blend((tracer.Color_interface) refl.getColor(), (double) dart._runtime.helpers.DynamicHelper.invoke("getReflection", dart._runtime.helpers.DynamicHelper.invoke("getMaterial", info.getShape())));
          }
        }
        tracer.IntersectionInfo_interface shadowInfo = new tracer.IntersectionInfo(dart2java$localTypeEnv.evaluate(new dart._runtime.types.simple.InterfaceTypeExpr(tracer.IntersectionInfo.dart2java$typeInfo)));
        if (this.getRenderShadows())
        {
          tracer.Ray_interface shadowRay = new tracer.Ray(dart2java$localTypeEnv.evaluate(new dart._runtime.types.simple.InterfaceTypeExpr(tracer.Ray.dart2java$typeInfo)), info.getPosition(), v);
          shadowInfo = this.testIntersection(shadowRay, scene, (tracer.BaseShape_interface) info.getShape());
          if ((shadowInfo.getIsHit() && (!dart._runtime.helpers.ObjectHelper.operatorEqual(shadowInfo.getShape(), info.getShape()))))
          {
            tracer.Color_interface vA = color.multiplyScalar(0.5);
            double dB = (double) dart._runtime.helpers.DoubleHelper.operatorStar(0.5, dart.math.__TopLevel.pow((java.lang.Number) dart._runtime.helpers.DynamicHelper.invoke("getTransparency", dart._runtime.helpers.DynamicHelper.invoke("getMaterial", shadowInfo.getShape())), 0.5));
            color = vA.addScalar(dB);
          }
        }
        if ((((boolean) this.getRenderHighlights() && (!(boolean) shadowInfo.getIsHit())) && (boolean) dart._runtime.helpers.DynamicHelper.invoke("operatorGreater", dart._runtime.helpers.DynamicHelper.invoke("getGloss", dart._runtime.helpers.DynamicHelper.invoke("getMaterial", info.getShape())), 0.0)))
        {
          java.lang.Object Lv = dart._runtime.helpers.DynamicHelper.invoke("normalize", dart._runtime.helpers.DynamicHelper.invoke("operatorMinus", dart._runtime.helpers.DynamicHelper.invoke("getPosition", info.getShape()), dart._runtime.helpers.DynamicHelper.invoke("getPosition", light)));
          java.lang.Object E = dart._runtime.helpers.DynamicHelper.invoke("normalize", dart._runtime.helpers.DynamicHelper.invoke("operatorMinus", dart._runtime.helpers.DynamicHelper.invoke("getPosition", scene.getCamera()), dart._runtime.helpers.DynamicHelper.invoke("getPosition", info.getShape())));
          java.lang.Object H = dart._runtime.helpers.DynamicHelper.invoke("normalize", dart._runtime.helpers.DynamicHelper.invoke("operatorMinus", E, Lv));
          java.lang.Number glossWeight = dart.math.__TopLevel.pow(dart.math.__TopLevel.max((java.lang.Number) dart._runtime.helpers.DynamicHelper.invoke("dot", info.getNormal(), H), 0.0), shininess);
          color = (tracer.Color_interface) dart._runtime.helpers.DynamicHelper.invoke("operatorPlus", dart._runtime.helpers.DynamicHelper.invoke("multiplyScalar", dart._runtime.helpers.DynamicHelper.invoke("getColor", light), glossWeight), color);
        }
      }
      return color.limit();
    }
    public java.lang.String toString()
    {
      final dart._runtime.types.simple.TypeEnvironment dart2java$localTypeEnv = this.dart2java$type.env;
      return (((("Engine [canvasWidth: " + this.getCanvasWidth()) + ", canvasHeight: ") + this.getCanvasHeight()) + "]");
    }
    public int getCanvasWidth()
    {
      return this.canvasWidth;
    }
    public int getCanvasHeight()
    {
      return this.canvasHeight;
    }
    public int getPixelWidth()
    {
      return this.pixelWidth;
    }
    public int getPixelHeight()
    {
      return this.pixelHeight;
    }
    public boolean getRenderDiffuse()
    {
      return this.renderDiffuse;
    }
    public boolean getRenderShadows()
    {
      return this.renderShadows;
    }
    public boolean getRenderHighlights()
    {
      return this.renderHighlights;
    }
    public boolean getRenderReflections()
    {
      return this.renderReflections;
    }
    public int getRayDepth()
    {
      return this.rayDepth;
    }
    public java.lang.Object getCanvas()
    {
      return this.canvas;
    }
    public int setCanvasWidth(int value)
    {
      this.canvasWidth = value;
      return value;
    }
    public int setCanvasHeight(int value)
    {
      this.canvasHeight = value;
      return value;
    }
    public int setPixelWidth(int value)
    {
      this.pixelWidth = value;
      return value;
    }
    public int setPixelHeight(int value)
    {
      this.pixelHeight = value;
      return value;
    }
    public boolean setRenderDiffuse(boolean value)
    {
      this.renderDiffuse = value;
      return value;
    }
    public boolean setRenderShadows(boolean value)
    {
      this.renderShadows = value;
      return value;
    }
    public boolean setRenderHighlights(boolean value)
    {
      this.renderHighlights = value;
      return value;
    }
    public boolean setRenderReflections(boolean value)
    {
      this.renderReflections = value;
      return value;
    }
    public int setRayDepth(int value)
    {
      this.rayDepth = value;
      return value;
    }
    public java.lang.Object setCanvas(java.lang.Object value)
    {
      this.canvas = value;
      return value;
    }
}
