package tracer;

public interface Chessboard_interface extends tracer.Materials_interface
{
  tracer.Color_interface getColor_(java.lang.Number u, java.lang.Number v);
  tracer.Color_interface getColorEven();
  tracer.Color_interface getColorOdd();
  double getDensity();
  void _constructor(tracer.Color_interface colorEven, tracer.Color_interface colorOdd, java.lang.Object reflection, java.lang.Object transparency, java.lang.Object gloss, double density);
}
