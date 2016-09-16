package tracer;

public interface Solid_interface extends tracer.Materials_interface
{
  tracer.Color_interface getColor_(java.lang.Number u, java.lang.Number v);
  tracer.Color_interface getColor();
  void _constructor(tracer.Color_interface color, java.lang.Object reflection, java.lang.Object refraction, java.lang.Object transparency, java.lang.Object gloss);
}
