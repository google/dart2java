package tracer;

public interface Chessboard_interface extends tracer.Materials_interface
{
  tracer.Color_interface getColor_(java.lang.Number u, java.lang.Number v);
  tracer.Color_interface getColorEven();
  tracer.Color_interface getColorOdd();
  java.lang.Double getDensity();
}
