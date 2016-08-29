// https://github.com/dart-lang/ton80/blob/master/lib/src/common/dart/BenchmarkBase.dart
// Copyright 2011 Google Inc. All Rights Reserved.

import 'dart:math';

class Expect {
  static void equals(var expected, var actual) {
    if (expected != actual) {
      print("Values not equal: ");
      print(expected);
      print("vs ");
      print(actual);
    }
  }

  static void listEquals(List expected, List actual) {
    if (expected.length != actual.length) {
      print("Lists have different lengths: ");
      print(expected.length);
      print("vs ");
      print(actual.length);
    }
    for (int i = 0; i < actual.length; i++) {
      equals(expected[i], actual[i]);
    }
  }

  void fail(String message) {
    print(message);
  }
}


class BenchmarkBase {
  final String name;

  // Empty constructor.
  const BenchmarkBase(String name) : this.name = name;

  static const int iters = 1000;

  // The benchmark code.
  // This function is not used, if both [warmup] and [exercise] are overwritten.
  void run() { }

  // Runs a short version of the benchmark. By default invokes [run] once.
  void warmup() {
    run();
  }

  // Exercices the benchmark. By default invokes [run] 10 times.
  void exercise() {
    for (int i = 0; i < iters; i++) {
      run();
    }
  }

  // Not measured setup code executed prior to the benchmark runs.
  void setup() { }

  // Not measures teardown code executed after the benchark runs.
  void teardown() { }

  // Measures the score for this benchmark by executing it repeately until
  // time minimum has been reached.
  double measureForWarumup(int timeMinimum) {
    int time = 0;
    int iter = 0;
    Stopwatch watch = new Stopwatch();
    watch.start();
    int elapsed = 0;
    while (elapsed < timeMinimum) {
      warmup();
      elapsed = watch.elapsedMilliseconds;
      iter++;
    }
    return (1000.0 * elapsed / iter) / iters;
  }

  // Measures the score for this benchmark by executing it repeately until
  // time minimum has been reached.
  double measureForExercise(int timeMinimum) {
    int time = 0;
    int iter = 0;
    Stopwatch watch = new Stopwatch();
    watch.start();
    int elapsed = 0;
    while (elapsed < timeMinimum) {
      exercise();
      elapsed = watch.elapsedMilliseconds;
      iter++;
    }
    return (1000.0 * elapsed / iter) / iters;
  }

  // Measures the score for the benchmark and returns it.
  double measure() {
    setup();
    // Warmup for at least 100ms. Discard result.
    measureForWarumup(100);
    // Run the benchmark for at least 2000ms.
    double result = measureForExercise(10 * 1000);
    teardown();
    return result;
  }

  void report() {
    double score = measure();
    print("$name(RunTime): $score us.");
  }

}

// The ray tracer code in this file is written by Adam Burmister. It
// is available in its original form from:
//
//   http://labs.flog.co.nz/raytracer/
//
// Ported from the v8 benchmark suite by Google 2012.

// Dummy HTML definition.

class TracerBenchmark extends BenchmarkBase {
  const TracerBenchmark() : super("Tracer");

  void warmup() {    
    renderScene(null);
  }

  void exercise() {
    renderScene(null);
  }
}

void main() {
  print("Running benchmark...");
  new TracerBenchmark().report();
  print("Done.");
}

class Light {
  final position;
  final color;
  final intensity;

  const Light(this.position, this.color, [this.intensity = 10.0]);
}


// 'event' null means that we are benchmarking
void renderScene(event) {
  var scene = new Scene();
  scene.camera = new Camera(const Vector(0.0, 0.0, -15.0),
                            const Vector(-0.2, 0.0, 5.0),
                            const Vector(0.0, 1.0, 0.0));
  scene.background = const Background(const Color(0.5, 0.5, 0.5), 0.4);

  var sphere = const Sphere(
      const Vector(-1.5, 1.5, 2.0),
      1.5,
      const Solid(
          const Color(0.0, 0.5, 0.5),
          0.3,
          0.0,
          0.0,
          2.0
      )
  );

  var sphere1 = const Sphere(
      const Vector(1.0, 0.25, 1.0),
      0.5,
      const Solid(
          const Color(0.9,0.9,0.9),
          0.1,
          0.0,
          0.0,
          1.5
      )
  );

  var plane = new Plane(
      new Vector(0.1, 0.9, -0.5).normalize(),
      1.2,
      const Chessboard(
          const Color(1.0, 1.0, 1.0),
          const Color(0.0, 0.0, 0.0),
          0.2,
          0.0,
          1.0,
          0.7
      )
  );

  scene.shapes.add(plane);
  scene.shapes.add(sphere);
  scene.shapes.add(sphere1);

  var light = const Light(
      const Vector(5.0, 10.0, -1.0),
      const Color(0.8, 0.8, 0.8)
  );

  var light1 = const Light(
      const Vector(-3.0, 5.0, -15.0),
      const Color(0.8, 0.8, 0.8),
      100.0
  );

  scene.lights.add(light);
  scene.lights.add(light1);

  int imageWidth = 0, imageHeight = 0, pixelSize = 0;
  bool renderDiffuse = false, renderShadows = false, renderHighlights = false, renderReflections = false;
  var canvas;
  if (event == null) {
    imageWidth = 100;
    imageHeight = 100;
    pixelSize = 5;
    renderDiffuse = true;
    renderShadows = true;
    renderHighlights = true;
    renderReflections = true;
    canvas = null;
  } else {
    // Removed
  }
  int rayDepth = 2;

  var raytracer = new Engine(imageWidth,
                             imageHeight,
                             pixelSize,
                             pixelSize,
                             renderDiffuse,
                             renderShadows,
                             renderHighlights,
                             renderReflections,
                             rayDepth
                             );

  raytracer.renderScene(scene, canvas);
}

class Color {
  final double red;
  final double green;
  final double blue;

  const Color(this.red, this.green, this.blue);

  Color limit() {
    var r = (red > 0.0) ? ((red > 1.0) ? 1.0 : red) : 0.0;
    var g = (green > 0.0) ? ((green > 1.0) ? 1.0 : green) : 0.0;
    var b = (blue > 0.0) ? ((blue > 1.0) ? 1.0 : blue) : 0.0;
    return new Color(r, g, b);
  }

  Color operator +(Color c2) {
    return new Color(red + c2.red, green + c2.green, blue + c2.blue);
  }

  Color addScalar(double s){
    var result = new Color(red + s, green + s, blue + s);
    result.limit();
    return result;
  }

  Color operator *(Color c2) {
    var result = new Color(red * c2.red, green * c2.green, blue * c2.blue);
    return result;
  }

  Color multiplyScalar(double f) {
    var result = new Color(red * f, green * f, blue * f);
    return result;
  }

  Color blend(Color c2, double w) {
    var result = multiplyScalar(1.0 - w) + c2.multiplyScalar(w);
    return result;
  }

  int brightness() {
    var r = (red * 255).toInt();
    var g = (green * 255).toInt();
    var b = (blue * 255).toInt();
    return (r * 77 + g * 150 + b * 29) >> 8;
  }

  String toString() {
    var r = (red * 255).toInt();
    var g = (green * 255).toInt();
    var b = (blue * 255).toInt();

    return 'rgb($r,$g,$b)';
  }
}

// Variable used to hold a number that can be used to verify that
// the scene was ray traced correctly.
var checkNumber;

class IntersectionInfo {
  bool isHit = false;
  int hitCount = 0;
  var shape, position, normal, color, distance;

  IntersectionInfo() {
    color = const Color(0.0, 0.0, 0.0);
  }

  String toString() => 'Intersection [$position]';
}


class Engine {
  int canvasWidth = 0;
  int canvasHeight = 0;
  int pixelWidth = 0, pixelHeight = 0;
  bool renderDiffuse, renderShadows, renderHighlights, renderReflections;
  int rayDepth = 0;
  var canvas;

  Engine([this.canvasWidth = 100, this.canvasHeight = 100,
          this.pixelWidth = 2, this.pixelHeight = 2,
          this.renderDiffuse = false, this.renderShadows = false,
          this.renderHighlights = false, this.renderReflections = false,
          this.rayDepth = 2]) {
    canvasHeight = canvasHeight ~/ pixelHeight;
    canvasWidth = canvasWidth ~/ pixelWidth;
  }

  void setPixel(int x, int y, Color color){
    var pxW, pxH;
    pxW = pixelWidth;
    pxH = pixelHeight;

    if (canvas != null) {
      canvas.fillStyle = color.toString();
      canvas.fillRect(x * pxW, y * pxH, pxW, pxH);
    } else {
      checkNumber += color.brightness();
    }
  }

  // 'canvas' can be null if raytracer runs as benchmark
  void renderScene(Scene scene, canvas) {
    checkNumber = 0;
    /* Get canvas */
    this.canvas = (canvas == null) ? null : canvas.getContext("2d");

    var canvasHeight = this.canvasHeight;
    var canvasWidth = this.canvasWidth;

    for(var y = 0; y < canvasHeight; y++){
      for(var x = 0; x < canvasWidth; x++){
        var yp = y / canvasHeight * 2 - 1;
        var xp = x / canvasWidth * 2 - 1;

        var ray = scene.camera.getRay(xp, yp);
        setPixel(x, y, getPixelColor(ray, scene));
      }
    }
    if ((canvas == null) && (checkNumber != 55545)) {
      // Used for benchmarking.
      throw "Scene rendered incorrectly";
    }
  }

  Color getPixelColor(Ray ray, Scene scene){
    var info = testIntersection(ray, scene, null);
    if(info.isHit){
      var color = rayTrace(info, ray, scene, 0);
      return color;
    }
    return scene.background.color;
  }

  IntersectionInfo testIntersection(Ray ray, Scene scene, BaseShape exclude) {
    int hits = 0;
    IntersectionInfo best = new IntersectionInfo();
    best.distance = 2000.0;

    for(var i=0; i < scene.shapes.length; i++){
      var shape = scene.shapes[i];

      if(shape != exclude){
        IntersectionInfo info = shape.intersect(ray);
        if ((info.isHit as bool) &&
            ((info.distance >= 0) as bool) &&
            ((info.distance < best.distance) as bool)){
          best = info;
          hits++;
        }
      }
    }
    best.hitCount = hits;
    return best;
  }

  Ray getReflectionRay(Vector P, Vector N, Vector V){
    var c1 = -N.dot(V);
    var R1 = N.multiplyScalar(2*c1) + V;
    return new Ray(P, R1);
  }

  Color rayTrace(IntersectionInfo info, Ray ray, Scene scene, int depth) {
    // Calc ambient
    Color color = info.color.multiplyScalar(scene.background.ambience);
    var oldColor = color;
    var shininess = pow(10.0, info.shape.material.gloss + 1.0);

    for(var i = 0; i < scene.lights.length; i++) {
      var light = scene.lights[i];

      // Calc diffuse lighting
      var v = (light.position - info.position).normalize();

      if (renderDiffuse) {
        var L = v.dot(info.normal);
        if ((L > 0.0) as bool) {
          color = color + info.color * light.color.multiplyScalar(L);
        }
      }

      // The greater the depth the more accurate the colours, but
      // this is exponentially (!) expensive
      if (depth <= rayDepth) {
        // calculate reflection ray
        if ((renderReflections as bool) && ((info.shape.material.reflection > 0.0) as bool)) {
          var reflectionRay = getReflectionRay(info.position,
                                               info.normal,
                                               ray.direction);
          var refl = testIntersection(reflectionRay, scene, info.shape);

          if ((refl.isHit as bool) && ((refl.distance > 0.0) as bool)) {
            refl.color = rayTrace(refl, reflectionRay, scene, depth + 1);
          } else {
            refl.color = scene.background.color;
          }

          color = color.blend(refl.color, info.shape.material.reflection);
        }
        // Refraction
        /* TODO */
      }
      /* Render shadows and highlights */

      IntersectionInfo shadowInfo = new IntersectionInfo();

      if (renderShadows) {
        var shadowRay = new Ray(info.position, v);

        shadowInfo = testIntersection(shadowRay, scene, info.shape);
        if (shadowInfo.isHit &&
            shadowInfo.shape != info.shape
            /*&& shadowInfo.shape.type != 'PLANE'*/) {
          var vA = color.multiplyScalar(0.5);
          var dB = (0.5 * pow(shadowInfo.shape.material.transparency, 0.5)) as double;
          color = vA.addScalar(dB);
        }
      }
      // Phong specular highlights
      if ((renderHighlights as bool) &&
          !(shadowInfo.isHit as bool) &&
          ((info.shape.material.gloss > 0.0) as bool)) {
        var Lv = (info.shape.position - light.position).normalize();

        var E = (scene.camera.position - info.shape.position).normalize();

        var H = (E - Lv).normalize();

        var glossWeight = pow(max(info.normal.dot(H), 0.0), shininess);
        color = light.color.multiplyScalar(glossWeight) + color;
      }
    }
    return color.limit();
  }

  String toString() {
    return 'Engine [canvasWidth: $canvasWidth, canvasHeight: $canvasHeight]';
  }
}

abstract class Materials {
  final double gloss;             // [0...infinity] 0 = matt
  final double transparency;      // 0=opaque
  final double reflection;        // [0...infinity] 0 = no reflection
  final double refraction;
  final bool hasTexture;

  const Materials(this.reflection,
                  this.transparency,
                  this.gloss,
                  this.refraction,
                  this.hasTexture);

  Color getColor_(num u, num v);

  wrapUp(t) {
    t = t % 2.0;
    if((t < -1) as bool) t += 2.0;
    if((t >= 1) as bool) t -= 2.0;
    return t;
  }
}


class Chessboard extends Materials {
  final Color colorEven, colorOdd;
  final double density;

  const Chessboard(this.colorEven,
                   this.colorOdd,
                   reflection,
                   transparency,
                   gloss,
                   this.density)
      : super(reflection, transparency, gloss, 0.5, true);

  Color getColor_(num u, num v) {
    var t = wrapUp(u * density) * wrapUp(v * density);

    if ((t < 0.0) as bool) {
      return colorEven;
    } else {
      return colorOdd;
    }
  }
}


class Solid extends Materials {
  final Color color;

  const Solid(this.color, reflection, refraction, transparency, gloss)
      : super(reflection, transparency, gloss, refraction, false);

  Color getColor_(num u, num v) {
    return color;
  }
}

class Ray {
  final position;
  final direction;

  Ray(this.position, this.direction);
  String toString() {
    return 'Ray [$position, $direction]';
  }
}


class Camera {
  final position;
  final lookAt;
  final up;
  var equator, screen;

  Camera(this.position, this.lookAt, this.up) {
    equator = lookAt.normalize().cross(up);
    screen = position + lookAt;
  }

  Ray getRay(double vx, double vy) {
    var pos = screen - (equator.multiplyScalar(vx) - up.multiplyScalar(vy));
    pos = pos.negateY();
    var dir = pos - position;
    var ray = new Ray(pos, dir.normalize());
    return ray;
  }

  toString() {
    return 'Camera []';
  }
}


class Background {
  final Color color;
  final double ambience;

  const Background(this.color, this.ambience);
}


class Scene {
  var camera;
  var shapes;
  var lights;
  var background;
  Scene() {
    camera = new Camera(const Vector(0.0, 0.0, -0.5),
                        const Vector(0.0, 0.0, 1.0),
                        const Vector(0.0, 1.0, 0.0));
    shapes = new List();
    lights = new List();
    background = const Background(const Color(0.0, 0.0, 0.5), 0.2);
  }
}

class BaseShape {
  final position;
  final material;

  const BaseShape(this.position, this.material);

  String toString() {
    return 'BaseShape';
  }
}


class Plane extends BaseShape {
  final d;

  Plane(pos, this.d, material) : super(pos, material);

  IntersectionInfo intersect(Ray ray) {
    var info = new IntersectionInfo();

    var Vd = position.dot(ray.direction);
    if (Vd == 0) return info; // no intersection

    var t = -(position.dot(ray.position) + d) / Vd;
    if ((t <= 0) as bool) return info;

    info.shape = this;
    info.isHit = true;
    info.position = ray.position + ray.direction.multiplyScalar(t);
    info.normal = position;
    info.distance = t;

    if(material.hasTexture as bool){
      var vU = new Vector(position.y, position.z, -position.x);
      var vV = vU.cross(position);
      var u = info.position.dot(vU);
      var v = info.position.dot(vV);
      info.color = material.getColor_(u,v);
    } else {
      info.color = material.getColor_(0,0);
    }

    return info;
  }

  String toString() {
    return 'Plane [$position, d=$d]';
  }
}


class Sphere extends BaseShape {
  final double radius;
  const Sphere(pos, this.radius, material) : super(pos, material);

  IntersectionInfo intersect(Ray ray){
    var info = new IntersectionInfo();
    info.shape = this;

    var dst = ray.position - position;

    var B = dst.dot(ray.direction);
    var C = dst.dot(dst) - (radius * radius);
    var D = (B * B) - C;

    if ((D > 0) as bool) { // intersection!
      info.isHit = true;
      info.distance = (-B) - sqrt(D);
      info.position = ray.position +
          ray.direction.multiplyScalar(info.distance);
      info.normal = (info.position - position).normalize();

      info.color = material.getColor_(0,0);
    } else {
      info.isHit = false;
    }
    return info;
  }

  String toString() {
    return 'Sphere [position=$position, radius=$radius]';
  }
}

class Vector {
  final double x, y, z;
  const Vector(this.x, this.y, this.z);

  Vector normalize() {
    var m = magnitude();
    return new Vector(x / m, y / m, z / m);
  }

  Vector negateY() {
    return new Vector(x, -y, z);
  }

  double magnitude() {
    return sqrt((x * x) + (y * y) + (z * z));
  }

  Vector cross(Vector w) {
    return new Vector(-z * w.y + y * w.z,
                      z * w.x - x * w.z,
                      -y * w.x + x * w.y);
  }

  double dot(Vector w) {
    return x * w.x + y * w.y + z * w.z;
  }

  Vector operator +(Vector w) {
    return new Vector(w.x + x, w.y + y, w.z + z);
  }

  Vector operator -(Vector w) {
    return new Vector(x - w.x, y - w.y, z - w.z);
  }

  Vector operator *(Vector w) {
    return new Vector(x * w.x, y * w.y, z * w.z);
  }

  Vector multiplyScalar(double w) {
    return new Vector(x * w, y * w, z * w);
  }

  String toString() {
    return 'Vector [$x, $y ,$z ]';
  }
}
