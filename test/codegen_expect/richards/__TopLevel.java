package richards;

public class __TopLevel
{
  
  
  
  
    public static void main(String[] args)
    {
      final dart._runtime.types.simple.TypeEnvironment dart2java$localTypeEnv = dart._runtime.types.simple.TypeEnvironment.ROOT;
      dart.core.__TopLevel.print("Running benchmark...");
      new richards.Richards(dart2java$localTypeEnv.evaluate(new dart._runtime.types.simple.InterfaceTypeExpr(richards.Richards.dart2java$typeInfo))).report();
      dart.core.__TopLevel.print("Done.");
    }
}
