package barnsleyfern;

public class __TopLevel
{
    private static dart._runtime.types.simple.InterfaceTypeExpr dart2java$typeExpr_Fractal = new dart._runtime.types.simple.InterfaceTypeExpr(barnsleyfern.Fractal.dart2java$typeInfo);
  
  
  
    public static void main(String[] args)
    {
      final dart._runtime.types.simple.TypeEnvironment dart2java$localTypeEnv = dart._runtime.types.simple.TypeEnvironment.ROOT;
      dart.core.__TopLevel.print("Running benchmark...");
      barnsleyfern.Fractal._new(dart2java$localTypeEnv.evaluate(dart2java$typeExpr_Fractal)).report();
      dart.core.__TopLevel.print("Done.");
    }
}
