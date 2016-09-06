package specialized_list;

public class __TopLevel
{
  
  
  
  
    public static void main(String[] args)
    {
      final dart._runtime.types.simple.TypeEnvironment dart2java$localTypeEnv = dart._runtime.types.simple.TypeEnvironment.ROOT;
      dart.core.__TopLevel.print("Expect output 5, 7, 9");
      dart.core.List_interface<java.lang.Integer> intList = ((dart.core.List_interface) dart._runtime.base.DartList.Generic._fromArguments(java.lang.Integer.class, 5, 7, 9));
      for (int i = 0; (i < intList.getLength()); i = (i + 1))
      {
        dart.core.__TopLevel.print(intList.operatorAt(i));
      }
    }
}
