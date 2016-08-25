package specialized_list;

public class __TopLevel
{
  
  
  
  
    public static void main(String[] args)
    {
      final dart._runtime.types.simple.TypeEnvironment dart2java$localTypeEnv = dart._runtime.types.simple.TypeEnvironment.ROOT;
      dart.core.__TopLevel.print("Expect output 5, 7, 9");
      dart._runtime.base.DartList._int intList = dart._runtime.base.DartList._int._fromArguments(int.class, 5, 7, 9);
      for (int i = 0; (i < intList.getLength()); i = (i + 1))
      {
        dart.core.__TopLevel.print(intList.operatorAt_primitive(i));
      }
    }
}
