package tracer;

public interface Engine_interface extends dart._runtime.base.DartObject_interface
{
  void setPixel(int x, int y, tracer.Color_interface color);
  void renderScene(tracer.Scene_interface scene, java.lang.Object canvas);
  tracer.Color_interface getPixelColor(tracer.Ray_interface ray, tracer.Scene_interface scene);
  tracer.IntersectionInfo_interface testIntersection(tracer.Ray_interface ray, tracer.Scene_interface scene, tracer.BaseShape_interface exclude);
  tracer.Ray_interface getReflectionRay(tracer.Vector_interface P, tracer.Vector_interface N, tracer.Vector_interface V);
  tracer.Color_interface rayTrace(tracer.IntersectionInfo_interface info, tracer.Ray_interface ray, tracer.Scene_interface scene, int depth);
  java.lang.String toString();
  int getCanvasWidth();
  int getCanvasHeight();
  int getPixelWidth();
  int getPixelHeight();
  boolean getRenderDiffuse();
  boolean getRenderShadows();
  boolean getRenderHighlights();
  boolean getRenderReflections();
  int getRayDepth();
  java.lang.Object getCanvas();
  int setCanvasWidth(int value);
  int setCanvasHeight(int value);
  int setPixelWidth(int value);
  int setPixelHeight(int value);
  boolean setRenderDiffuse(boolean value);
  boolean setRenderShadows(boolean value);
  boolean setRenderHighlights(boolean value);
  boolean setRenderReflections(boolean value);
  int setRayDepth(int value);
  java.lang.Object setCanvas(java.lang.Object value);
  void _constructor(int canvasWidth, int canvasHeight, int pixelWidth, int pixelHeight, boolean renderDiffuse, boolean renderShadows, boolean renderHighlights, boolean renderReflections, int rayDepth);
}
